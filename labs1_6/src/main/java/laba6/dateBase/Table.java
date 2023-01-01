package laba6.dateBase;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    List<String[]> table;
    public Table(String filePath) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        table = reader.readAll();
        reader.close();
    }
    ArrayList<Boolean> getColumn(String columnName, String value)throws Throwable{
        ArrayList<Boolean> column = new ArrayList<Boolean>();
        int columnValuesIndex=0;
        if(!columnName.equals("")){
            columnValuesIndex = getColumnIndexByName(columnName);
        }
        for(int columnIndex=0; columnIndex<table.size(); columnIndex++){
            if(!value.equals("")) {
                column.add(table.get(columnIndex)[columnValuesIndex].equals(value));
            }
            else{
                column.add(true);
            }
        }
        return column;
    }
    String getValueByIndex(String columnName, int index)throws Throwable{
        return table.get(index)[getColumnIndexByName(columnName)];
    }
    abstract int getColumnIndexByName(String columnName) throws Throwable;

}
