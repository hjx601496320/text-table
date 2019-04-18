package com.hebaibai.table;

import java.util.ArrayList;
import java.util.List;

public class Table {


    /**
     * 数据
     */
    protected List<List<Cell>> datas = new ArrayList<>();

    /**
     * 表格数据
     */
    protected StringBuilder tableString = new StringBuilder();

    /**
     * 最大列数
     */
    protected int maxColNum = 0;

    /**
     * 是否构建完成
     */
    protected boolean isOk = false;

    public static TableBulider bulider() {
        return new TableBulider(new Table());
    }

    private Table() {
    }

    @Override
    public String toString() {
        return tableString.toString();
    }
}
