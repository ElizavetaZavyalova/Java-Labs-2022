package laba6.parsers;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import laba6.Request;
import laba6.dateBase.enums.TablesName;
import laba6.parsers.helpClases.Column;
import laba6.parsers.helpClases.Where;

public class RequestParser {
    Request request;

    public Request getRequest() {
        return request;
    }

    ArrayList<Column> selectPort= new ArrayList<Column>();
    Map<TablesName,ArrayList<Integer>> fromPort= new TreeMap<>();
    ArrayList<Where> wherePort= new ArrayList<Where>();
    Column groupByPort=null;

    void addToRequest(ArrayList<String> stringOfResult)throws Throwable{
        request.result.add(stringOfResult);
    }
    public RequestParser(String request)throws Throwable{
        this.request=new Request(request);
    }
    void parseRequest() throws Throwable{
        Pattern pattern = Pattern.compile(
                "SELECT\\s*(.*)\\s*" +
                        "FROM\\s*(.*?)\\s*" +
                        "(((WHERE\\s*(.*?))\\s*(GROUP\\s*BY\\s*(.*?))\\s*)|"+
                        "((WHERE\\s*(.*?))\\s*)|" +
                        "((GROUP\\s*BY\\s*(.*?))|))\\s*;"
        );
        Matcher matcher = pattern.matcher(request.getRequestName());
        if (matcher.find()) {

            parseFrom(matcher.group(2)+" ,");
            parseOptionalParameter(matcher.group(3));
            parseSelect(matcher.group(1)+" ,");

        }
    }
    void parseOptionalParameter(String optionalParameter)throws Throwable{
        String[]string = {"(WHERE\\s*(.*?))\\s*(GROUP\\s*BY\\s(.*))",
                "((WHERE\\s*(.*)))\\s*",
                "((GROUP\\s*BY\\s*(.*))|)\\s*"};
        for(int variant=0; variant<string.length; variant++){
            Pattern pattern = Pattern.compile(string[variant]);
            Matcher matcher = pattern.matcher(optionalParameter);
            if(matcher.find()) {
                switch(variant) {
                    case 0:
                        parseWhere(matcher.group(1));
                        parseGroupBy(matcher.group(3));
                        return;
                    case 1:
                        parseWhere(matcher.group(1));
                        return;
                    case 2:
                        parseWhere("");
                        parseGroupBy(matcher.group(2));
                        return;
                }

            }
        }

    }
    void parseSelect(String selectPorts)throws Throwable{
        Pattern pattern = Pattern.compile(
                "([a-zA-Z]\\w*)." +
                        "([a-zA-Z]\\w*)\\s*,\\s*"
        );
        Matcher matcher = pattern.matcher(selectPorts);
        while (matcher.find()) {
            Column select = new Column();
            select.setTableName(TablesName.getValue(matcher.group(1)));
            select.setColumnName( matcher.group(2));
            if (!select.equals(groupByPort)) {
                    selectPort.add(select);
            }
        }
    }
    void parseFrom(String fromPorts)throws Throwable{
        Pattern pattern = Pattern.compile(
                "([a-zA-Z]\\w*)\\s*,\\s*"
        );
        Matcher matcher = pattern.matcher(fromPorts);
        while (matcher.find()) {
            fromPort.put(TablesName.valueOf(matcher.group(1)),new ArrayList<Integer>());
        }
    }
    Integer makeParseWhere(String wherePorts,Integer requestIndex)throws Throwable{
        Pattern pattern = Pattern.compile(
                "(OR|AND)\\s*" +
                        "([a-zA-Z]\\w*?)." +
                        "([a-zA-Z]\\w*?)\\s*=\\s*" +
                        "\\'(.*?)\\'\\s*"
        );
        Matcher matcher = pattern.matcher(wherePorts);
        while (matcher.find()) {
            Where where = new Where();
            where.setOperator(matcher.group(1));
            where.setTableName(TablesName.getValue(matcher.group(2)));
            where.setColumnName(matcher.group(3));
            where.setValue(matcher.group(4));
            where.setFirstIndex(requestIndex);
            for (Where w : wherePort) {
                if (w.equals(where)) {
                    where.setFirstIndex(w.getFirstIndex());
                    break;
                }
            }
            wherePort.add(where);
            if (fromPort.containsKey(where.getTableName())) {
                fromPort.get(where.getTableName()).add(requestIndex);
            }
            requestIndex++;
        }
        return requestIndex;
    }
    void parseWhere(String wherePorts) throws Throwable{
        Integer requestIndex=0;
        if(!wherePorts.equals("")) {
            Pattern pattern = Pattern.compile(
                            "WHERE\\s*(.*)\\s*"
            );
            Matcher matcher = pattern.matcher(wherePorts);
            if (matcher.find()) {
                requestIndex=makeParseWhere(("AND "+matcher.group(1)), requestIndex);
            }
        }
        for (Map.Entry<TablesName, ArrayList<Integer>> from : fromPort.entrySet()) {
            if(from.getValue().size()==0) {
                from.getValue().add(requestIndex);
                Where where= new Where();
                where.setFirstIndex(requestIndex);
                where.setTableName(from.getKey());
                wherePort.add(where);
                requestIndex++;
            }
        }

    }
    void makeParseGroupBy(String groupByPorts)throws Throwable{
        Pattern pattern = Pattern.compile(
                "GROUP\\s*BY\\s*([a-zA-Z]\\w*)." +
                        "([a-zA-Z]\\w*)"
        );
        Matcher matcher = pattern.matcher(groupByPorts);
        if (matcher.find()) {
            groupByPort=new Column();
            groupByPort.setTableName(TablesName.getValue(matcher.group(1)));
            groupByPort.setColumnName(matcher.group(2));
            //groupByPort=selectPort.indexOf(column);
            if(fromPort.size()>1){
                throw new Throwable();
            }
            selectPort.add(groupByPort);
        }
    }
    void parseGroupBy(String groupByPorts)throws Throwable{
         if(!groupByPorts.equals("")){
             makeParseGroupBy(groupByPorts);
             return;
         }
    }

}
