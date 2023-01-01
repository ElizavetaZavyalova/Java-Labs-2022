package laba6.parsers;

import laba6.parsers.helpClases.CorrectStringInformation;

import java.util.ArrayDeque;
import java.util.ArrayList;

class BooleanParser{
    ArrayDeque<Boolean> Stack = new ArrayDeque();
    ArrayList<CorrectStringInformation> resultIndex = new ArrayList<CorrectStringInformation>();
    public void parse(String operator,  Boolean value, Integer index) throws Throwable{
        //Stack1.push(value);
        if(operator.equals("AND")){
            Boolean lastValue=Stack.peek();
            Stack.pop();
           Boolean newValue=lastValue&value;
            if(resultIndex.size()!=0) {
                CorrectStringInformation newInfo = resultIndex.get(resultIndex.size() - 1);
                newInfo.booleanValue = newValue;
                resultIndex.remove(resultIndex.size() - 1);
                resultIndex.add(newInfo);
            }
            resultIndex.add(new CorrectStringInformation(value,index));
            Stack.push(newValue);
        }
        else{
            resultIndex.add(new CorrectStringInformation(value,index));
            Stack.push(value);
        }
    }
    BooleanParser(){
        Stack.push(true);
    }
    boolean isCorrectResult()throws Throwable{
        Boolean result=false;
        for(Boolean value:Stack){
            result|=value;
        }
        return result;
    }
    BooleanParser(BooleanParser parser) throws Throwable{
        this.Stack = new ArrayDeque(parser.Stack);
        this.resultIndex = new ArrayList(parser.resultIndex);
    }
}