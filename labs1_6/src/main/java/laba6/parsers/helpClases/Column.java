package laba6.parsers.helpClases;

import laba6.dateBase.enums.TablesName;

public class Column {

    public TablesName getTableName() {
        return tableName;
    }

    public void setTableName(TablesName tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;
    private TablesName tableName;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Column column = (Column) obj;
        return columnName.equals(column.columnName) && tableName.equals(column.tableName);
    }
}
