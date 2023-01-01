package laba6.dateBase;


import laba6.dateBase.enums.MarkColumnsName;
import laba6.dateBase.enums.TablesName;

import java.io.IOException;

public class Mark extends Table {
    TablesName tablesName= TablesName.MARK;

    public Mark(String filePath)  throws IOException {
        super(filePath);
    }
    @Override
    int getColumnIndexByName(String columnName)  throws Throwable {
        return MarkColumnsName.getValue(columnName).getNum();
    }
}
