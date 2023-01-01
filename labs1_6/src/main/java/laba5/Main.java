package laba5;

public class Main {

    public static void main(String[] args) {
             assert(args.length!=1&&args.length!=3);
             LogFileReader logFileReader=null;
             if(args.length==1){
                 logFileReader = new LogFileReader();
             }
            else if(args.length == 3) {
                try {
                    Integer countTime = Integer.parseInt(args[1]);
                    logFileReader = new LogFileReader(countTime, args[2].charAt(0));
                }
                catch (NumberFormatException e){
                    System.out.println("Argument error: " + args[1]);
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
            if(logFileReader!=null) {
                logFileReader.TryReadingAndParse(args[0]);
            }
    }
}