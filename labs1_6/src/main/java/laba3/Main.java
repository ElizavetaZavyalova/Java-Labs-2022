package laba3;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        assert(args.length!=5);
        ExecutorService executors= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);
        try {
            Integer countOfStringsBefore = Integer.parseInt(args[3]);
            Integer countOfStringsAfter = Integer.parseInt(args[4]);
            ReaderFile reader =new  ReaderFile(args[1],args[2], countOfStringsBefore, countOfStringsAfter);
            ReaderFile.SetExecutor(executors);
            reader.ReadeFile(args[0]);
        }
        catch (NumberFormatException e){
            System.out.println("The last two arguments must be numbers");
        } catch (IOException e) {
            System.out.println("error of reading: "+args[0]);
        } finally{
            executors.shutdown();
        }
    }
}