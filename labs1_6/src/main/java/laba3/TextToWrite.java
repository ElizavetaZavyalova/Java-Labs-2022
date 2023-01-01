package laba3;

import java.util.LinkedList;
import java.util.Queue;

public class TextToWrite{
    private Integer countOfStringsBefore;
    private Integer countOfStringsAfter;
    private Queue<String> queueOfStringAfter;
    private Queue<String> queueOfStringBefore;

    private String currentString;
    private Integer getCountOfStringsBefore() {
        return countOfStringsBefore;
    }

    private Integer getCountOfStringsAfter() {
        return countOfStringsAfter;
    }
    TextToWrite(Integer countOfStringsBefore, Integer countOfStringsAfter){
        assert(countOfStringsBefore<0);
        assert(countOfStringsAfter<0);
        this.countOfStringsBefore = countOfStringsBefore;
        this.countOfStringsAfter = countOfStringsAfter;
        queueOfStringAfter=new LinkedList<String>();
        queueOfStringBefore=new LinkedList<String>();
    }
    public TextToWrite(TextToWrite other) {
        this.countOfStringsAfter=other.getCountOfStringsAfter();
        this.countOfStringsBefore=other.getCountOfStringsBefore();
        this.currentString= other.getCurrentString();
        this.queueOfStringAfter=new LinkedList<String>(other.getQueueOfStringAfter());
        this.queueOfStringBefore=new LinkedList<String>(other.getQueueOfStringBefore());
    }
    public Queue<String> getQueueOfStringAfter() {
        return queueOfStringAfter;
    }
    private String RemoveExtraString( Queue<String> queueOfString,Integer countOfStrings) {
        String resultOfRemoving=null;
        if (queueOfString.size() > (countOfStrings)) {
            resultOfRemoving = queueOfString.iterator().next();
            queueOfString.remove();
        }
        return resultOfRemoving;
    }

    public void AddStringToText(String string) {
        if(string!=null) {
            queueOfStringAfter.add(string);
        }else{
            countOfStringsAfter--;
        }
        if(currentString!=null) {
            queueOfStringBefore.add(currentString);
            RemoveExtraString(queueOfStringBefore,countOfStringsBefore);
        }
        currentString=RemoveExtraString(queueOfStringAfter,countOfStringsAfter);
    }


    public Queue<String> getQueueOfStringBefore() {
        return queueOfStringBefore;
    }
    public String getCurrentString() {
        return currentString;
    }
}