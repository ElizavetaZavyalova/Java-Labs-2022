package laba4;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        assert(args.length!=1);
        AnalyzerDirectory directory = null;
        try {
            directory = new AnalyzerDirectory(args[0]);
            long lengthInBytes = directory.getDirectorySize();
            System.out.println(args[0]+"--"+lengthInBytes+" Bytes/"
                    +lengthInBytes/(8L*1024*1024)+"Mb/"+
                    lengthInBytes/(8L*1024*1024*1024)+"Gb");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}