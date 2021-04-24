/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.sql.PreparedStatement;
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

    private static StudentConnection instance;

    private StudentConnection() {
    }

    public static synchronized StudentConnection getInstance() {
        if (instance == null) {
            instance = new StudentConnection();
        }
        return instance;
    }

    @Override
    public Vector<Student> getAllStudent(Integer skip, Integer limit) { 
        Vector<Student> list = new Vector<>();
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String strsql = "SELECT * FROM HocSinh";
            ResultSet rs = st.executeQuery(strsql);
            while (rs.next()) {
                Student std = new Student();
                std.mhs = rs.getInt("MHS");
                std.name = rs.getString(2);
                std.score = rs.getDouble("Diem");
                std.avatar = rs.getString(4);
                std.address = rs.getString(5);
                std.note = rs.getString(6);
                list.add(std);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public boolean addStudent(Student std) {
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String sql = "INSERT INTO HocSinh (TenHS, Diem, HinhAnh,DiaChi,GhiChu) VALUES (?,?,?,?,?)";
            PreparedStatement stm = ConnectionUtils.getConnect().prepareStatement(sql);
            stm.setString(1, std.name);
            stm.setDouble(2, std.score);
            stm.setString(3, std.avatar);
            stm.setString(4, std.address);
            stm.setString(5, std.note);

            int iAffectedRecord = stm.executeUpdate();
            if (iAffectedRecord <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateStudent(Student std) {
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String sql = "UPDATE HocSinh SET TenHS = ?, Diem = ?, HinhAnh = ?, DiaChi = ?, GhiChu = ? WHERE MHS = ?";
            PreparedStatement stm = ConnectionUtils.getConnect().prepareStatement(sql);
            stm.setString(1, std.name);
            stm.setDouble(2, std.score);
            stm.setString(3, std.avatar);
            stm.setString(4, std.address);
            stm.setString(5, std.note);
            stm.setInt(6, std.mhs);

            int iAffectedRecord = stm.executeUpdate();
            if (iAffectedRecord <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteStudent(Integer id) {
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String sql = "DELETE FROM HocSinh WHERE MHS = ?";
            PreparedStatement stm = ConnectionUtils.getConnect().prepareStatement(sql);
            stm.setString(1, id.toString());
            int iAffectedRecord = stm.executeUpdate();
            if (iAffectedRecord <= 0) {
                return false;
            }

        } catch (SQLException ex) {
            return false;
        }
        return true;
    }
}
