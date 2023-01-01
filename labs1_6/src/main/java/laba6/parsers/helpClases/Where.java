package laba6.parsers.helpClases;

import laba6.dateBase.enums.TablesName;

public class Where {

    public TablesName getTableName() {
        return tableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    private String columnName = "";
    private String value = "";
    private String operator = "AND";
    private TablesName tableName;

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    private Integer firstIndex=0;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;

        Where where = (Where) o;
        return where.tableName.equals(this.tableName);
    }
}
