/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class StudentConnection implements IStudentConnection {

    @Override
    public Vector<Student> getAllStudent(Integer skip, Integer limit) {
        Vector<Student> list = new Vector<>();
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String strsql = "SELECT * FROM Student";
            ResultSet rs = st.executeQuery(strsql);
            while (rs.next()) {
                Student std = new Student();
                std.MSH = rs.getInt("MHS");
                std.TenHS = rs.getString(2);
                std.Diem = rs.getDouble("Diem");
                std.HinhAnh = rs.getString(4);
                std.DiaChi = rs.getString(5);
                std.GhiChu = rs.getString(6);
                list.add(std);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
