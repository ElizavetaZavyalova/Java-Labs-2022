package laba3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AnalyzerOfText implements Runnable{
    static volatile String substring="";
    static volatile String ResultFilePath="";
    static AtomicInteger AnalyzedStrings=new AtomicInteger(1);
    static AtomicInteger FindStrings=new AtomicInteger(1);
    static AtomicInteger ReaderStrings=new AtomicInteger(1);
    volatile static long timeStart;
    private  TextToWrite text;
    @Override
    public void run(){
        writeInformationAfterAnalyze("Start");
       // long time = System.currentTimeMillis();
        if(text.getCurrentString().contains(substring)){
            try {
                writeInFile();
            } catch (IOException e) {
                System.out.println(Thread.currentThread().getName() + "Writing in file error"+
                        "FileName"+ResultFilePath);
            }
            FindStrings.incrementAndGet();
        }
        AnalyzedStrings.incrementAndGet();
        writeInformationAfterAnalyze("End");

    }

    private static synchronized void writeInformationAfterAnalyze(String string)  {
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println(Thread.currentThread().getName() + ": "+ string+" Analyze");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("-----------------------------------------------------");
        System.out.println("Time has passed: "+(System.currentTimeMillis()-timeStart));
        long Approximately=(System.currentTimeMillis()-timeStart)/(AnalyzedStrings.get()) *
                (ReaderStrings.get()-AnalyzedStrings.get());
        System.out.println("Approximately time: "+Approximately);
        System.out.println("Rows parsed "+ (AnalyzedStrings.get()-1)+
                " from "+(ReaderStrings.get()-1)+" read");
        System.out.println("Rows found: "+ (FindStrings.get()-1));
        System.out.println("-----------------------------------------------------");
    }
    AnalyzerOfText(TextToWrite text){
        this.text= text;
        ReaderStrings.incrementAndGet();
    }
    private synchronized void writeInFile() throws IOException{
        BufferedWriter writer= new  BufferedWriter(new FileWriter(ResultFilePath, true));
        for(String line : text.getQueueOfStringBefore()){
            writer.write(line+"\n");
        }
        writer.write(text.getCurrentString()+"\n");
        for(String line : text.getQueueOfStringAfter()){
            writer.write(line+"\n");
        }
        writer.write("---------------------------------------------------------------------------");
        writer.write("---------------------------------------------------------------------------\n");
        writer.close();
    }
    public static void setAnalyzerInformation(String substring,String ResultFilePath) throws IOException {
        AnalyzerOfText.substring=substring;
        AnalyzerOfText.ResultFilePath=ResultFilePath;
        BufferedWriter writer= new  BufferedWriter(new FileWriter(ResultFilePath, false));
        writer.close();
    }
    public static void startAnalyzer(){
        timeStart=System.currentTimeMillis();
    }
}