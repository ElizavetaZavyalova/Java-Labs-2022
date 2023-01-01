package laba3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class ReaderFile {
    TextToWrite text;
    static private ExecutorService executors=null;
    ReaderFile(String ResultFilePath, String substring,
               Integer countOfStringsBefore, Integer countOfStringsAfter) {
        try {
            AnalyzerOfText.setAnalyzerInformation(substring, ResultFilePath);
        } catch (IOException e) {
            System.out.println("Can't open file " + ResultFilePath);
        }

        text=new TextToWrite(countOfStringsBefore, countOfStringsAfter);
    }
    public static void SetExecutor(ExecutorService executors){
        ReaderFile.executors = executors;
    }
    private void startAnalyser(String line)  {
        text.AddStringToText(line);
        if(text.getCurrentString()!=null){
            if(executors!=null) {
                executors.execute(new AnalyzerOfText(new TextToWrite(text)));
            }
            else{
               new Thread( new AnalyzerOfText(new TextToWrite(text))).start();
            }
        }
    }
    public void TryReadeFile(String filePath) throws IOException {
        try {
            ReadeFile(filePath);
        } catch (IOException e) {
            System.out.println("Error reading");
        }
    }
    public void ReadeFile(String filePath) throws IOException {
        BufferedReader reader= new BufferedReader(new FileReader(filePath));
        String line;
        AnalyzerOfText.startAnalyzer();
        while ((line = reader.readLine())!=null) {
            startAnalyser(line);
        }
        while(text.getQueueOfStringAfter().size()>0){
            startAnalyser(line);
        }
        reader.close();
    }
}

