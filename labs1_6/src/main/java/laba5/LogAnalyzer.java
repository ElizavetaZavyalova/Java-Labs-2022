package laba5;


import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public abstract class LogAnalyzer {
    static class TimeLast{
        boolean IsAnalyzed;
        Long TimeRunning;
        TimeLast(Long TimeRunning,boolean IsAnalyzed){
            this.IsAnalyzed=IsAnalyzed;
            this.TimeRunning=TimeRunning;
        }
    }
    protected Map<Integer, TimeLast> requests= new TreeMap<>();
    private boolean CanAddRequest(Integer id, Calendar data){
        if(requests.containsKey(id)){
            return false;
        }
        requests.put(id, new TimeLast(data.getTimeInMillis(), false));
        return true;
    }
    private boolean CanFoundTimeRunning(Integer id,Calendar data){
        if(requests.containsKey(id)){
            if(!requests.get(id).IsAnalyzed) {
                long TimeRunning = data.getTimeInMillis() - requests.get(id).TimeRunning;
                if(TimeRunning>=0) {
                    requests.put(id, new TimeLast(TimeRunning, true));
                    return true;
                }
            }
        }
        return false;
    }
    public boolean TryAddResult(Integer id,Calendar data, String StartOrEnd){
        if(StartOrEnd.equals("")){
            return CanAddRequest(id,data);
        }
        return CanFoundTimeRunning(id,data);
    }
    public abstract void ShowAnalyzeResult();
}
