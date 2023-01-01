package laba6;

import laba6.parsers.RequestMaker;

import java.io.IOException;

import static laba6.dateBase.enums.TablesName.*;
public class Main {
    public static void main(String[] args) {
        assert (5 != args.length);
        String request="SELECT STUDENT.FIO, SUBJECT.SUBJECT_ID "+
                       "FROM STUDENT, SUBJECT "+
                       "WHERE STUDENT.STUDENT_ID='1' OR STUDENT.STUDENT_ID='2' OR STUDENT.STUDENT_ID='3' OR STUDENT.STUDENT_ID='4';";
       try{
           Console console=new Console(args[MARK.getNum()],args[STUDENT.getNum()],
                                       args[SUBJECT.getNum()],args[GROUP.getNum()]);
          console.getRequest(request);
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }catch(Throwable e) {
           System.out.println("Not correct request: " + request);
       }

    }
}

