package com.hebaibai.table;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableBulider {

    private Table table;

    protected TableBulider(Table table) {
        this.table = table;
    }

    public TableBulider addRow(List<Cell> cols) {
        if (table.maxColNum < cols.size()) {
            table.maxColNum = cols.size();
        }
        table.datas.add(cols);
        return this;
    }

    public TableBulider addRow(Cell... cols) {
        if (table.maxColNum < cols.length) {
            table.maxColNum = cols.length;
        }
        List<Cell> list = new ArrayList<>();
        for (Cell col : cols) {
            list.add(col);
        }
        table.datas.add(list);
        return this;
    }

    /**
     * 完成表格组装
     *
     * @return
     */
    public Table finish() {
        if (table.isOk) {
            return table;
        }
        //将每一行中，数据不足的进行补齐
        for (List<Cell> data : table.datas) {
            int dataSize = data.size();
            if (dataSize < table.maxColNum) {
                for (int i = 0; i < table.maxColNum - dataSize; i++) {
                    data.add(Cell.NULL());
                }
            }
        }
        //将每一列的数据向右补齐
        Map<Integer, Integer> colMaxLengthMap = colValMaxLength(table.datas);
        for (int i = 0; i < table.datas.size(); i++) {
            List<Cell> row = table.datas.get(i);
            for (int j = 0; j < row.size(); j++) {
                Cell cell = row.get(j);
                Integer maxLength = colMaxLengthMap.get(j);
                cell.format(maxLength);
            }
        }
        //去掉每一列中右边多余的空格
        Map<Integer, Integer> colMinBlankLengthMap = colValMinBlankLength(table.datas);
        for (int i = 0; i < table.datas.size(); i++) {
            List<Cell> row = table.datas.get(i);
            for (int j = 0; j < row.size(); j++) {
                Cell cell = row.get(j);
                Integer minBlankLength = colMinBlankLengthMap.get(j);
                cell.removeSpace(minBlankLength);
            }
        }
        //组装表格
        String line = "";
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < table.datas.size(); i++) {
            List<Cell> cells = table.datas.get(i);
            String row = getRow(cells, i);
            int length = row.length();
            if (line.length() < length) {
                line = StringUtils.repeat("-", length);
            }
            rows.add(row);
        }
        table.tableString.append("\n");
        for (String row : rows) {
            table.tableString.append(line).append("\n");
            table.tableString.append(row).append("\n");
        }
        table.tableString.append(line).append("\n");
        table.isOk = true;
        return table;
    }

    /**
     * 找到表中，每一列中字符串最长的长度
     *
     * @param formData
     * @return
     */
    private Map<Integer, Integer> colValMaxLength(List<List<Cell>> formData) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < formData.size(); i++) {
            //第几列
            int col = 0;
            //表中的每一行
            List<Cell> rows = formData.get(i);
            while (rows.size() > col) {
                String val = rows.get(col).getVal();
                int length = val.getBytes().length;
                if (!map.containsKey(col)) {
                    map.put(col, length);
                } else {
                    Integer integer = map.get(col);
                    if (integer < length) {
                        map.put(col, length);
                    }
                }
                col++;
            }
        }
        return map;
    }

    /**
     * 找到每一列从右开始最小的空格长度
     *
     * @param formData
     * @return
     */
    private Map<Integer, Integer> colValMinBlankLength(List<List<Cell>> formData) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < formData.size(); i++) {
            int col = 0;
            List<Cell> strings = formData.get(i);
            while (strings.size() > col) {
                String val = strings.get(col).getVal();
                int length = 0;
                for (int i1 = val.length() - 1; i1 >= 0; i1--) {
                    if (val.charAt(i1) == ' ') {
                        length++;
                    } else {
                        break;
                    }
                }
                Integer integer = map.get(col);
                if (integer == null) {
                    map.put(col, length);
                } else {
                    if (integer > length) {
                        map.put(col, length);
                    }
                }
                col++;
            }
        }
        return map;
    }

    private String getRow(List<Cell> cells, int rowNum) {
        StringBuilder row = new StringBuilder();
        String padding = StringUtils.repeat(" ", Cell.padding);
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            row.append("|").append(padding).append(cell).append(padding);
        }
        row.append("|");
        return row.toString();
    }

}