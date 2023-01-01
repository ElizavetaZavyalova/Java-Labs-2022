package laba5;
import java.util.Map;

public class DefaultLogAnalyzer extends LogAnalyzer {
    protected long timeDeviation=0;
    @Override
    public void ShowAnalyzeResult(){
        MakeTimeDeviation();
        timeDeviation+= timeDeviation/ ((long) requests.size() *requests.size()+1);
        writeAnalyzeResult();
    }
    protected void MakeTimeDeviation(){
        timeDeviation=0;
        for (Map.Entry<Integer, TimeLast> integerTimeLastEntry : this.requests.entrySet()) {
            if ((integerTimeLastEntry.getValue()).IsAnalyzed) {
                timeDeviation += (integerTimeLastEntry.getValue()).TimeRunning;
            }

        }
        if ((long) requests.size()>0){
            timeDeviation/=((long) requests.size());
        }
    }
    protected void writeAnalyzeResult(){
        for (Map.Entry<Integer, TimeLast> integerTimeLastEntry : this.requests.entrySet()) {
            if (!(integerTimeLastEntry.getValue()).IsAnalyzed) {
                System.out.println("QUERY FOR ID = " + integerTimeLastEntry.getKey() +
                        ": Still not completed");
            }
            else if ((integerTimeLastEntry.getValue()).TimeRunning > timeDeviation) {
                System.out.println("QUERY FOR ID = " + integerTimeLastEntry.getKey() +
                        ": Lasts too long");
            }
        }
    }
}
