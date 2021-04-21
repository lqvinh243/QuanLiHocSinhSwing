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

    public Student() {
        this.MSH = 0;
        this.TenHS = "";
        this.Diem = 0.0;
        this.HinhAnh = "";
        this.DiaChi = "";
        this.GhiChu = "";
    }

    public Student(Integer mhs, String tenHs, Double diem, String hinhAnh, String diaChi, String ghiChu) {
        this.MSH = mhs;
        this.TenHS = tenHs;
        this.Diem = diem;
        this.HinhAnh = hinhAnh;
        this.DiaChi = diaChi;
        this.GhiChu = ghiChu;
    }

    public void PrintStudent() {
        System.out.println("Thong tin hoc sinh:");
        System.out.println("Ma hoc sinh : " + this.MSH);
        System.out.println("Ten hoc sinh : " + this.TenHS);
        System.out.println("Diem : " + this.Diem);
        System.out.println("Hinh anh : " + this.HinhAnh);
        System.out.println("Dia chi : " + this.DiaChi);
        System.out.println("Ghi chu : " + this.GhiChu);
    }
}
