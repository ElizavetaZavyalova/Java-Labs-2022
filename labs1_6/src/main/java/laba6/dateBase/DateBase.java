package laba6.dateBase;

import laba6.dateBase.enums.TablesName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static laba6.dateBase.enums.TablesName;
import static laba6.dateBase.enums.TablesName.*;



public class DateBase {
    List<Table> tables=null;
    public DateBase(String markPath, String studentPath,String subjectPath, String groupPath) throws IOException {
        tables=new ArrayList<>();
        tables.add(new Mark(markPath));
        tables.add(new Student(studentPath));
        tables.add(new Subject(subjectPath));
        tables.add(new Group(groupPath));
    }
    public ArrayList<Boolean> getColumn(TablesName tableName, String columnName, String value)throws Throwable {
        switch (tableName){
            case GROUP:
               return tables.get(GROUP.getNum()).getColumn(columnName,value);
            case MARK:
                return tables.get(MARK.getNum()).getColumn(columnName,value);
            case STUDENT:
                return tables.get(STUDENT.getNum()).getColumn(columnName,value);
            case SUBJECT:
                return tables.get(SUBJECT.getNum()).getColumn(columnName,value);

        }
        throw new IllegalArgumentException("not correct orguments");

    }
    public String getValueByIndex(TablesName tableName,String columnName, int index)throws Throwable{
        return tables.get(tableName.getNum()).getValueByIndex(columnName,index);
    }

}
