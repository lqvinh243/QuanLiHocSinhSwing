/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public interface IStudentConnection {

    /**
     *
     * @param skip
     * @param limit
     * @return
     */
    Vector<Student> getAllStudent();

    Vector<Student> getAllStudent(SortOption sortMHS, SortOption sortScore);

    public boolean addStudent(Student std);

    public boolean addStudentWithMSH(Student std);

    public BulkActionResult importData(Vector<Student> std);

    public boolean updateStudent(Student std);

    public boolean deleteStudent(Integer id);
}
