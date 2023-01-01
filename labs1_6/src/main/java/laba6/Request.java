package laba6;

import laba6.parsers.helpClases.Column;

import java.io.*;
import java.util.*;

public class Request implements Serializable {
    public String requestName;
    Calendar dateStart;

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public String getRequestName() {
        return requestName;
    }
    static class MyComparator implements Comparator<ArrayList<String>>,  Serializable{
        @Override
        public int compare(ArrayList<String> o1, ArrayList<String> o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }
    public Set<ArrayList<String>> result=new TreeSet<>(new MyComparator());
    public void writeResult() {
        for (ArrayList<String> stringOfResult : result) {
            System.out.println(stringOfResult.toString());
        }
    }
    public Request(String request){
        this.requestName=request;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Request request = (Request) obj;
        return requestName.equals(request.requestName);
    }
}
