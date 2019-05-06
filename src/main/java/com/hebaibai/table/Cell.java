package com.hebaibai.table;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一个单元格
 */
@Getter
public class Cell {

    public static int padding = 1;

    protected static final String CHINESE_REGEX = "[\u4e00-\u9fa5|。|，|—|！|《|》|～|（|）|“|”|？|、|…]";

    private String val;

    private int length;

    private int chineseLength;

    private Cell() {
    }

    public static Cell cell(String val) {
        if (val == null) {
            return Cell.NULL();
        }
        val = val.replace("\n", "\\\\n");
        Cell cell = new Cell();
        cell.setVal(val);
        return cell;
    }

    public static Cell NULL() {
        Cell cell = new Cell();
        cell.setVal("null");
        return cell;
    }

    public void setVal(String val) {
        this.val = val;
        this.chineseLength = getChineseNum(val);
        this.length = val.length();
    }

    /**
     * 根据最大单元格宽度，格式化单元格内容
     *
     * @param maxLength
     */
    protected void format(Integer maxLength) {
        Formatter formatter = new Formatter();
        String val = formatter.format("%-" + (maxLength - chineseLength) + "s", this.getVal()).toString();
        this.setVal(val);
    }

    /**
     * 去除多余的空格
     *
     * @param minBlankLength
     */
    protected void removeSpace(Integer minBlankLength) {
        String val = this.val.substring(0, length - minBlankLength);
        this.setVal(val);
    }

    @Override
    public String toString() {
        return val;
    }

    /**
     * 获取中文数量
     *
     * @param val
     * @return
     */
    private int getChineseNum(String val) {
        if (val == null) {
            return 0;
        }
        ArrayList<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(CHINESE_REGEX);
        Matcher matcher = pattern.matcher(val);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        int size = list.size();
        return size;
    }
}
