package laba5;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsParse {
    private LogAnalyzer logAnalyzer=null;
    private static final Pattern pattern = Pattern.compile(
               "^\\s*[a-zA-Z]\\w*\\.\\s*"               +    "(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})" +
                     "\\s*-\\s*INFO\\s*-\\s* "                +    "(RESULT|)" +
                     "\\s*QUERY\\s*FOR\\s*ID\\s*=\\s*"        +    "(\\d+)$"
    );
    public boolean IsCorrectString(String string){
        Matcher m = pattern.matcher(string);
        if (m.find()) {
            Integer id = Integer.parseInt(m.group(3));
            SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Calendar calendar= Calendar.getInstance();
            try {
                calendar.setTime(simpleDataFormat.parse(m.group(1)));
            } catch (ParseException e) {
                System.out.println("Parse error");
            }
            return logAnalyzer.TryAddResult(id, calendar, m.group(2));
        }
        return false;

    }
    StringsParse(LogAnalyzer logAnalyzer){
        this.logAnalyzer = logAnalyzer;
    }
    public void runLogAnalyzer(){
        logAnalyzer.ShowAnalyzeResult();
    }

}
