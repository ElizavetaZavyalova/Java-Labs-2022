package laba6.dateBase;


//import laba6.dateBase.enums.;
import laba6.dateBase.enums.GroupColumnsName;
import laba6.dateBase.enums.TablesName;

import java.io.IOException;

public class Group extends Table {
    TablesName tablesName= TablesName.GROUP;
    public Group(String filePath) throws IOException {
        super(filePath);
    }
    @Override
    int getColumnIndexByName(String columnName)throws Throwable{
            return GroupColumnsName.getValue(columnName).getNum();
    }
}
