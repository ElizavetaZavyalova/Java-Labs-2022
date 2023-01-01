package laba6.parsers;

import laba6.Request;
import laba6.dateBase.DateBase;
import laba6.parsers.helpClases.Column;
import laba6.parsers.helpClases.CorrectStringInformation;
import laba6.parsers.helpClases.Where;

import java.util.*;

public class RequestMaker {
    DateBase dateBase;
    List<Map.Entry<Integer,ArrayList<Boolean>>> columns= new ArrayList<Map.Entry<Integer,ArrayList<Boolean>>>();
    RequestParser request;
    public RequestMaker(DateBase dateBase)  {
        this.dateBase = dateBase;
    }
    void makeRequest(String requestString) throws Throwable{
        request = new RequestParser(requestString);
        request.parseRequest();
    }
    void makeWhereColumns()throws Throwable{
        for(Where where: request.wherePort) {
            try{
                ArrayList<Boolean> column = dateBase.getColumn(where.getTableName(), where.getColumnName(), where.getValue());
                columns.add(new java.util.AbstractMap.SimpleEntry<>(where.getFirstIndex(),column));
            }
            catch(IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }

    }
    void makeResult(BooleanParser parser, Integer columnIndex) throws Throwable {
        if (columnIndex.equals(columns.size())) {
            if(parser.isCorrectResult()){
                this.createResult(parser.resultIndex);
            }

            return;
        }
        for (Integer stringIndex = 0; stringIndex < columns.get(columnIndex).getValue().size(); stringIndex++) {
            if(stringIndex.equals(columnsGetKey(columns.get(columnIndex).getKey(),parser.resultIndex,stringIndex))) {
                BooleanParser newParser = new BooleanParser(parser);

                Boolean booleanValue = columns.get(columnIndex).getValue().get(stringIndex);
                newParser.parse(request.wherePort.get(columnIndex).getOperator(), booleanValue, stringIndex);

                makeResult(newParser, columnIndex + 1);
            }

        }
    }
    Integer columnsGetKey(Integer key,ArrayList<CorrectStringInformation> inf, Integer stringIndex) throws Throwable {
        if(inf.size()==0){
            return stringIndex;
        }
        if(inf.size()<=key){
            return stringIndex;
        }
        return inf.get(key).index;
    }
    public Request createRequestResult(String requestString) throws Throwable{
        makeRequest(requestString);
        makeWhereColumns();
        BooleanParser booleanParser=new BooleanParser();
        Integer index=0;
        //ArrayList<CorrectStringInformation> correctStrings=new ArrayList<>();
        makeResult(booleanParser,index);
        return request.getRequest();
    }
    public void createResult(ArrayList<CorrectStringInformation> correctString) throws Throwable{
        ArrayList<ArrayList<String>> stringsResult=new ArrayList<ArrayList<String>>();;
        for(Column select:request.selectPort){
            ArrayList<Integer> indexes=getIndex(request.fromPort.get(select.getTableName()),correctString);
            ArrayList<String> valueResults=new ArrayList<>();
            for (Integer index : indexes) {
                valueResults.add(dateBase.getValueByIndex(select.getTableName(), select.getColumnName(), index));
            }
            stringsResult.add(valueResults);
        }
        ArrayList<String> result= new ArrayList<>();
        addResult(stringsResult, 0,result);
    }
    public void addResult( ArrayList<ArrayList<String>> stringsResult, Integer index,ArrayList<String> result )throws Throwable{
        if(index.equals(stringsResult.size())){
            request.addToRequest(result);
            return;
        }
        for(int i=0; i< stringsResult.get(index).size(); i++){
            ArrayList<String> newResult = new ArrayList<String>(result);
            newResult.add(stringsResult.get(index).get(i));
            addResult(stringsResult,index+1, newResult);
        }
    }

    ArrayList<Integer> getIndex(ArrayList<Integer> indexes,ArrayList<CorrectStringInformation> correctString)throws Throwable {
        ArrayList<Integer> lastIndex= new ArrayList<>();
        for (Integer index : indexes) {
            if (correctString.get(index).booleanValue) {
                lastIndex.add(correctString.get(index).index);
            }
        }
        if(lastIndex.size()==0){
            lastIndex.add(correctString.get(0).index);
        }
        return lastIndex;
    }

 }
