package laba5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogFileReader {
    private StringsParse parse;

    LogFileReader(Integer param, char TimeValue) throws IllegalArgumentException {
        LogAnalyzer logAnalyzer=new TimeDeviationLogAnalyzer(param, TimeValue);
        parse=new StringsParse(logAnalyzer);
    }
    LogFileReader(){
        LogAnalyzer logAnalyzer=new DefaultLogAnalyzer();
        parse=new StringsParse(logAnalyzer);
    }
    private void ReadingAndParse(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line= null;
        while ((line = bufferedReader.readLine()) != null) {
            if(!parse.IsCorrectString(line)){
                System.out.println("log problems: "+ line);
            }
        }
        bufferedReader.close();
        parse.runLogAnalyzer();
    }
    public void TryReadingAndParse(String filePath){
        try {
            ReadingAndParse(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    };
}
