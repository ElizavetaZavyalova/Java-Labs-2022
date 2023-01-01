package laba5;

public class TimeDeviationLogAnalyzer extends DefaultLogAnalyzer {
    @Override
    public void ShowAnalyzeResult(){
        long deviation =this.timeDeviation;
        MakeTimeDeviation();
        this.timeDeviation += deviation;
        writeAnalyzeResult();
    }
    TimeDeviationLogAnalyzer(Integer timeDeviation,char TimeValue)throws IllegalArgumentException{
        assert(timeDeviation<0);
        this.timeDeviation=timeDeviation;
        switch (TimeValue) {
            default:
                throw new IllegalArgumentException("Not Correct Argument: "+ TimeValue);
            case ('Y'):
                this.timeDeviation*=12L;
            case ('M'):
                this.timeDeviation*=30L;
            case ('D'):
                this.timeDeviation*=24L;
            case ('h'):
                this.timeDeviation*=60L;
            case ('m'):
                this.timeDeviation*=60L;
            case ('s'):
                this.timeDeviation*=1000L;
        }
    }
}
