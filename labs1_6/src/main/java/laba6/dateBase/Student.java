package laba6.dateBase;



import laba6.dateBase.enums.StudentColumnsName;
import laba6.dateBase.enums.TablesName;

import java.io.IOException;

public class Student extends Table {
    TablesName tablesName= TablesName.STUDENT;
    public Student(String filePath)  throws IOException {
        super(filePath);
    }
    @Override
    int getColumnIndexByName(String columnName)  throws Throwable{
        return StudentColumnsName.getValue(columnName).getNum();
    }
}
