/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.awt.BorderLayout;
import java.awt.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class StudentManagementGui {

    public StudentManagementGui() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new TableStudent());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class TableStudent extends JPanel {

    JTable table;

    public TableStudent() {
        Vector<String> columnNames = new Vector<String>();

        columnNames.add("Ma hoc sinh");
        columnNames.add("Ten");
        columnNames.add("Diem");
        columnNames.add("Hinh anh");
        columnNames.add("Dia chi");
        columnNames.add("Ghi chu");

        String[] columnNames1 = {"Ma hoc sinh", "Ten", "Diem", "Hinh anh", "Dia chi", "Ghi chu"};

        Vector<Student> list = new StudentConnection().getAllStudent(0, 0);
        Vector<Vector<Student>> l = new Vector<Vector<Student>>();
        table = new JTable();

        AbstractTableModel tokenmodel = new AbstractTableModel() {

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    return (list.get(rowIndex).MSH);
                } else if (columnIndex == 1) {
                    return (list.get(rowIndex).TenHS);
                } else if (columnIndex == 2) {
                    return (list.get(rowIndex).Diem);
                } else if (columnIndex == 3) {
                    return (list.get(rowIndex).HinhAnh);
                } else if (columnIndex == 4) {
                    return (list.get(rowIndex).DiaChi);
                } else {
                    return (list.get(rowIndex).GhiChu);
                }
            }

            @Override
            public int getRowCount() {
                return list.size();
            }

            @Override
            public int getColumnCount() {
                return 6;
            }

            @Override//Rewrite the function to get column names
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Ma hoc sinh";
                    case 1:
                        return "Ten";
                    case 2:
                        return "Diem";
                    case 3:
                        return "Hinh anh";
                    case 4:
                        return "Dia chi";
                    default:
                        return "Ghi chu";
                }
            }
        };

        table.setModel(tokenmodel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane);
    }
}
