package laba6.dateBase;

import laba6.dateBase.enums.SubjectColumnsName;
import laba6.dateBase.enums.TablesName;

import java.io.IOException;



public class Subject extends Table {
    TablesName tablesName= TablesName.SUBJECT;
    public Subject(String filePath) throws IOException {
        super(filePath);
    }
    @Override
    int getColumnIndexByName(String columnName)  throws Throwable{
        return SubjectColumnsName.getValue(columnName).getNum();
    }

}
