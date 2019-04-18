package com.hebaibai.table;


public class TableBuliderTest {

    public static void main(String[] args) {
        System.out.println(
                Table.bulider()
                        .addRow(Cell.cell("123"), Cell.cell("123"), Cell.cell("123"))
                        .addRow(Cell.cell("haha"), Cell.cell("ajkhd<>《》"), Cell.cell("123"))
                        .addRow(Cell.cell("何嘉旋"), Cell.cell("null"), Cell.cell("null"))
                        .finish()
                        .tableString
        );
    }
}