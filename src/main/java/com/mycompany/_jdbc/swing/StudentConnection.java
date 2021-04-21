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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class StudentConnection implements IStudentConnection {

    @Override
    public ArrayList<Student> getAllStudent(Integer skip, Integer limit) {
        ArrayList<Student> list = new ArrayList<>();
        try {
            Statement st = ConnectionUtils.getConnect().createStatement();
            String strsql = "SELECT * FROM Student";
            ResultSet rs = st.executeQuery(strsql);
            while (rs.next()) {
                int iID = rs.getInt("MSH");
                String strName = rs.getString(2);
                String strDesc = rs.getString(3);

                System.out.println("ID: " + iID + " - Name: " + strName + " - Desc: " + strDesc);

            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
