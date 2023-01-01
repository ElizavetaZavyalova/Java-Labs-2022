package laba4;

import java.io.File;
import java.io.IOException;

public class AnalyzerDirectory {
    private File directory;
    private long folderSize(File directory){
        long length = 0L;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            if(file.isDirectory()) {
                length += folderSize(file);
            }
        }
        return length;
    }
    public long getDirectorySize(){
        return folderSize(directory);
    }
    AnalyzerDirectory(String directoryPath)throws IOException{
        directory = new File(directoryPath);
        if(!directory.exists()){
            throw new IOException("Directory does not exist: " + directoryPath);
        }
    }
}
