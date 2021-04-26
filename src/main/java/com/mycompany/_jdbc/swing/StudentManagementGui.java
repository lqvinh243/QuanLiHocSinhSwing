/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Admin
 */
@SuppressWarnings("unchecked")
public class StudentManagementGui implements ActionListener {

    FormStudent frmStudent;
    ActionStudent actStudent;
    TableStudent tStd;
    ActionSort actSort;
    ActionForm actForm;

    private boolean isUpdate = false;

    public StudentManagementGui() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //declare jpanel
        tStd = new TableStudent();
        frmStudent = new FormStudent();
        actStudent = new ActionStudent();
        actSort = new ActionSort();
        actForm = new ActionForm();
        TopContent top = new TopContent(tStd, actSort);
        FormContent frmContent = new FormContent(frmStudent, actForm);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        tStd.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tStd.table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Student std = new Student();
                    std.mhs = Integer.parseInt(tStd.table.getModel().getValueAt(row, 0).toString());
                    std.name = tStd.table.getModel().getValueAt(row, 1).toString();
                    std.score = Double.parseDouble(tStd.table.getModel().getValueAt(row, 2).toString());
                    std.avatar = tStd.table.getModel().getValueAt(row, 3).toString();
                    std.address = tStd.table.getModel().getValueAt(row, 4).toString();
                    std.note = tStd.table.getModel().getValueAt(row, 5).toString();
                    frmStudent.setFormValue(std);
                }
            }
        });

        actStudent.addStudent.addActionListener(this);
        actStudent.addStudent.setActionCommand("Add");

        actStudent.editStudent.addActionListener(this);
        actStudent.editStudent.setActionCommand("Update");

        actStudent.deleteStudent.addActionListener(this);
        actStudent.deleteStudent.setActionCommand("Delete");

        actStudent.exportExcel.addActionListener(this);
        actStudent.exportExcel.setActionCommand("Export_Excel");

        actStudent.importExcel.addActionListener(this);
        actStudent.importExcel.setActionCommand("Import_Excel");

        actSort.cbMHS.addActionListener(this);
        actSort.cbMHS.setActionCommand("SortMHS");

        actSort.cbScore.addActionListener(this);
        actSort.cbScore.setActionCommand("SortScore");

        actForm.btnConfirm.addActionListener(this);
        actForm.btnConfirm.setActionCommand("Confirm");

        actForm.btnCancel.addActionListener(this);
        actForm.btnCancel.setActionCommand("Cancel");

        frame.add(top, BorderLayout.NORTH);
        frame.add(frmContent, BorderLayout.CENTER);
        frame.add(actStudent, BorderLayout.EAST);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        TableStudentFilter filter = actSort.filter();
        switch (str) {
            case "Add": {
                EnabledFormContent();
                actStudent.DisableAction();
                frmStudent.clearForm();
                break;
            }
            case "Update": {
                if (tStd.GetSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Vui long chon hoc sinh de chinh sua!!");
                    break;
                }
                EnabledFormContent();
                actStudent.DisableAction();
                isUpdate = true;
                break;
            }
            case "Delete":
                if (tStd.GetSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Vui long chon hoc sinh de xoa!!");
                    break;
                }
                boolean deleteStatus = StudentConnection.getInstance().deleteStudent(frmStudent.getIdStudent());
                if (deleteStatus == true) {
                    JOptionPane.showMessageDialog(null, "Add student success!");
                    tStd.ReloadModelTable(filter);
                } else {
                    JOptionPane.showMessageDialog(null, "Add student fail!");
                }
                break;
            case "Export_Excel":
                var list = StudentConnection.getInstance().getAllStudent(filter.sortMHS, filter.sortScore);
                boolean hasSuccess;
                try {
                    hasSuccess = new ExcelHelper().writeExcel(list, "Studen.xlsx");
                    if (hasSuccess) {
                        JOptionPane.showMessageDialog(null, "Write file excel success!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Write file excel fail!");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Write file excel fail!");
                }
                break;
            case "Import_Excel":
                var listImport = new ExcelHelper().readExcel("Student.xlsx");
                var bulkActionResult = StudentConnection.getInstance().importData(listImport);
                String message = String.format("Co tong cong %d hoc sinh!Import thanh cong %d hoc sinh, that bai %d hoc sinh", bulkActionResult.getTotal(), bulkActionResult.getSuccess(), bulkActionResult.getFail());
                JOptionPane.showMessageDialog(null, message);
                break;
            case "SortMHS":
            case "SortScore":
                tStd.ReloadModelTable(filter);
                break;
            case "Confirm":
                if (isUpdate == true) {
                    Student std = frmStudent.getFormStudent();
                    boolean updateStatus = StudentConnection.getInstance().updateStudent(std);
                    if (updateStatus == true) {
                        JOptionPane.showMessageDialog(null, "Update student success!");
                        tStd.ReloadModelTable(filter);
                    } else {
                        JOptionPane.showMessageDialog(null, "Update student fail!");
                    }
                } else {
                    Student std = frmStudent.getFormStudent();
                    boolean addStatus = StudentConnection.getInstance().addStudent(std);
                    if (addStatus == true) {
                        JOptionPane.showMessageDialog(null, "Add student success!");
                        tStd.ReloadModelTable(filter);
                    } else {
                        JOptionPane.showMessageDialog(null, "Add student fail!");
                    }
                    frmStudent.clearForm();
                }
                DisableFormContent();
                actStudent.EnabledAction();
                frmStudent.clearForm();
                break;
            case "Cancel":
                DisableFormContent();
                actStudent.EnabledAction();
                frmStudent.clearForm();
                break;
            default:
                break;

        }
        tStd.ClearSelectionTable();
    }

    public void DisableFormContent() {
        frmStudent.DisableForm();
        actForm.DisableAction();
    }

    public void EnabledFormContent() {
        frmStudent.EnabledForm();
        actForm.EnabledAction();
    }
}

final class TopContent extends JPanel {

    public TopContent(TableStudent tbl, ActionSort act) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(act);
        this.add(tbl);
    }
}

final class TableStudentFilter {

    SortOption sortMHS;
    SortOption sortScore;

    public TableStudentFilter() {
        sortMHS = new SortOption();
        sortScore = new SortOption();
    }

    public TableStudentFilter(SortOption sortMHS, SortOption sortScore) {
        this.sortMHS = sortMHS;
        this.sortScore = sortScore;
    }
}

final class TableStudent extends JPanel {

    public JTable table;

    public TableStudent() {
        table = new JTable();
        TableStudentFilter filter = new TableStudentFilter();
        table.setModel(getModel(filter));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAlignmentX(CENTER_ALIGNMENT);
        add(scrollPane);
        this.centerContent();

    }

    public void centerContent() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public AbstractTableModel getModel(TableStudentFilter filter) {
        var list = StudentConnection.getInstance().getAllStudent(filter.sortMHS, filter.sortScore);
        AbstractTableModel tokenmodel = new AbstractTableModel() {

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return (list.get(rowIndex).mhs);
                    case 1:
                        return (list.get(rowIndex).name);
                    case 2:
                        return (list.get(rowIndex).score);
                    case 3:
                        return (list.get(rowIndex).avatar);
                    case 4:
                        return (list.get(rowIndex).address);
                    default:
                        return (list.get(rowIndex).note);
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
        return tokenmodel;
    }

    public void ReloadModelTable(TableStudentFilter filter) {
        table.setModel(this.getModel(filter));
        this.centerContent();
    }

    public int GetSelectedRow() {
        return table.getSelectedRow();
    }

    public void ClearSelectionTable() {
        table.clearSelection();
    }
}

final class FormContent extends JPanel {

    public FormContent(FormStudent frm, ActionForm act) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(frm);
        this.add(act);
    }
}

final class ActionForm extends JPanel {

    JButton btnConfirm;
    JButton btnCancel;

    public ActionForm() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnConfirm = new JButton("Confirm");
        add(btnConfirm);
        btnCancel = new JButton("Cancel");
        add(btnCancel);
        DisableAction();
    }

    public void DisableAction() {
        btnConfirm.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    public void EnabledAction() {
        btnConfirm.setEnabled(true);
        btnCancel.setEnabled(true);
    }
}

final class FormStudent extends JPanel {

    JTextField mhs;
    JTextField name;
    JTextField score;
    JTextField avatar;
    JTextField address;
    JTextField note;

    private final int top = 3, left = 3, bottom = 3, right = 3;
    private final Insets i = new Insets(top, left, bottom, right);
    private final Dimension dtm = new Dimension(200, 24);

    public FormStudent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = i;
        add(new JLabel("Ma"), gbc);
        gbc.gridx += 2;
        add(new JLabel("Ten"), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Diem"), gbc);
        gbc.gridx += 2;
        add(new JLabel("Hinh anh"), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Dia chi"), gbc);
        gbc.gridx += 2;
        add(new JLabel("Ghi chu"), gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;
        mhs = new JTextField(5);
        mhs.setEnabled(false);
        mhs.setPreferredSize(dtm);
        add(mhs, gbc);
        gbc.gridx += 2;
        name = new JTextField(20);
        name.setPreferredSize(dtm);
        add(name, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        score = new JTextField(10);
        score.setPreferredSize(dtm);
        add(score, gbc);
        gbc.gridx += 2;
        avatar = new JTextField(20);
        avatar.setPreferredSize(dtm);
        add(avatar, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        address = new JTextField(10);
        address.setPreferredSize(dtm);
        add(address, gbc);
        gbc.gridx += 2;
        note = new JTextField(20);
        note.setPreferredSize(dtm);
        add(note, gbc);
        DisableForm();

    }

    public void setFormValue(Student std) {
        mhs.setText(std.mhs.toString());
        name.setText(std.name);
        score.setText(std.score.toString());
        avatar.setText(std.avatar);
        address.setText(std.address);
        note.setText(std.note);
    }

    public void clearForm() {
        mhs.setText("");
        name.setText("");
        score.setText("");
        avatar.setText("");
        address.setText("");
        note.setText("");
    }

    public Integer getIdStudent() {
        return Integer.parseInt(mhs.getText());
    }

    public Student getFormStudent() {
        Student std = new Student();
        if (mhs.getText().length() > 0) {
            std.mhs = Integer.parseInt(mhs.getText());
        }
        std.name = name.getText();
        std.score = Double.parseDouble(score.getText());
        std.avatar = avatar.getText();
        std.address = address.getText();
        std.note = note.getText();

        return std;
    }

    public void DisableForm() {
        name.setEnabled(false);
        score.setEnabled(false);
        avatar.setEnabled(false);
        address.setEnabled(false);
        note.setEnabled(false);
    }

    public void EnabledForm() {
        name.setEnabled(true);
        score.setEnabled(true);
        avatar.setEnabled(true);
        address.setEnabled(true);
        note.setEnabled(true);
    }
}

final class ActionStudent extends JPanel {

    JButton addStudent;
    JButton editStudent;
    JButton deleteStudent;
    JButton exportExcel;
    JButton importExcel;

    private final int top = 3, left = 3, bottom = 3, right = 3;
    private final Insets i = new Insets(top, left, bottom, right);

    public ActionStudent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = i;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addStudent = new JButton("Them hoc sinh");
        add(addStudent, gbc);
        gbc.gridx++;
        editStudent = new JButton("Sua hoc sinh");
        add(editStudent, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        deleteStudent = new JButton("Xoa hoc sinh");
        add(deleteStudent, gbc);
        gbc.gridy++;
        exportExcel = new JButton("Xuat Excel");
        add(exportExcel, gbc);
        gbc.gridx++;
        importExcel = new JButton("Doc Excel");
        add(importExcel, gbc);
    }

    public void DisableAction() {
        addStudent.setEnabled(false);
        editStudent.setEnabled(false);
        deleteStudent.setEnabled(false);
    }

    public void EnabledAction() {
        addStudent.setEnabled(true);
        editStudent.setEnabled(true);
        deleteStudent.setEnabled(true);
    }
}

final class ActionSort extends JPanel {

    JComboBox cbMHS;
    JComboBox cbScore;

    public ActionSort() {
        var listSortMHS = new Vector<Object>();
        listSortMHS.add(new SortOption("Ma hoc sinh", SortType.NONE));
        listSortMHS.add(new SortOption("Ma hoc sinh: Tang dan", SortType.ASC));
        listSortMHS.add(new SortOption("Ma hoc sinh: Giam dan", SortType.DESC));
        cbMHS = new JComboBox(listSortMHS);
        add(cbMHS);

        var listSortScore = new Vector<SortOption>();
        listSortScore.add(new SortOption("Diem", SortType.NONE));
        listSortScore.add(new SortOption("Diem: Thap den cao", SortType.ASC));
        listSortScore.add(new SortOption("Diem: Cao den thap", SortType.DESC));
        cbScore = new JComboBox(listSortScore);
        add(cbScore);
    }

    public TableStudentFilter filter() {
        return new TableStudentFilter((SortOption) this.cbMHS.getSelectedItem(), (SortOption) this.cbScore.getSelectedItem());
    }

    public SortOption getValueMHS() {
        return (SortOption) this.cbMHS.getSelectedItem();
    }

    public SortOption getValueScore() {
        return (SortOption) this.cbScore.getSelectedItem();
    }
}
