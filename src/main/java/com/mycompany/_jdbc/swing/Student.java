/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

/**
 *
 * @author Admin
 */
public class Student {
    Integer MSH;
    String TenHS;
    Double Diem;
    String HinhAnh;
    String DiaChi;
    String GhiChu;
    
    public Student(Integer mhs,String tenHs,Double diem,String hinhAnh,String diaChi,String ghiChu){
        this.MSH = mhs;
        this.TenHS = tenHs;
        this.Diem = diem;
        this.HinhAnh = hinhAnh;
        this.DiaChi = diaChi;
        this.GhiChu = ghiChu;
    }
}
