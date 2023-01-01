package laba6;

import laba6.dateBase.DateBase;
import laba6.parsers.RequestMaker;

import java.io.*;
import java.util.*;

public class Console {
    Calendar dateStart;
    RequestMaker requestMaker;
    DateBase dateBase;
    TreeMap<String, Request> cashOfRequests=new TreeMap<String,Request>();
    void openDateBase(String markPath, String studentPath, String subjectPath, String groupPath) throws IOException {
        dateBase=new DateBase(markPath, studentPath, subjectPath, groupPath);
    }
    Request createRequest(String request)throws Throwable{
        requestMaker=new RequestMaker(dateBase);
        Request result = requestMaker.createRequestResult(request);
        addRequest(result);
        return result;
    }
    Console(String markPath, String studentPath, String subjectPath, String groupPath) throws IOException {
         dateStart=Calendar.getInstance();
         readFromCashFile();
         cleanCashOfRequest(markPath, studentPath, subjectPath, groupPath);
         openDateBase(markPath, studentPath, subjectPath, groupPath);

    }
    void addRequest(Request request) throws Throwable{
        request.setDateStart(dateStart);
        cashOfRequests.put(request.requestName,request);
    }
    Calendar getLastDate(String... paths){
        Calendar lastDateChange=this.dateStart;
        for(String path: paths) {
            Calendar dateChange=Calendar.getInstance();
            dateChange.setTimeInMillis(new File(path).lastModified());
            if(dateChange.getTimeInMillis()<lastDateChange.getTimeInMillis()){
                lastDateChange =dateChange;
            }
        }
        return lastDateChange;
    }
    void cleanCashOfRequest(String... paths){
        Calendar lastDateChange=getLastDate(paths);
        for(Map.Entry<String, Request> request : cashOfRequests.entrySet()){
             if(request.getValue().getDateStart().getTimeInMillis()<lastDateChange.getTimeInMillis()){
                 cashOfRequests.remove(request.getKey());
             }
        }
    }
    void getRequest(String request) throws Throwable{
        if(cashOfRequests.containsKey(request)){
            cashOfRequests.get(request).writeResult();
            return;
        }
        createRequest(request).writeResult();
        writeToCashFile();
    }
    private void writeToCashFile() throws Throwable{
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("temp.txt");
            ObjectOutputStream objectOutputStream= objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cashOfRequests);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Cash write error: " + e.getMessage());
        }
    }
    private void readFromCashFile(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("temp.txt");
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            cashOfRequests =  (TreeMap<String,Request>)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Cash read error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("File: "+Request.class.toString()+"was change" + e.getMessage());
        }
    }

}
