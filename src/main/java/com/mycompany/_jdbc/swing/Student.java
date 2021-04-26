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

    Integer mhs;
    String name;
    Double score;
    String avatar;
    String address;
    String note;

    public Student() {
        this.mhs = 0;
        this.name = "";
        this.score = 0.0;
        this.avatar = "";
        this.address = "";
        this.note = "";
    }

    public Student(Integer mhs, String name, Double score, String avatar, String address, String note) {
    }

    public void setMHS(Integer mhs) {
        this.mhs = mhs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void PrintStudent() {
        System.out.println("Thong tin hoc sinh:");
        System.out.println("Ma hoc sinh : " + this.mhs);
        System.out.println("Ten hoc sinh : " + this.name);
        System.out.println("Diem : " + this.score);
        System.out.println("Hinh anh : " + this.avatar);
        System.out.println("Dia chi : " + this.address);
        System.out.println("Ghi chu : " + this.note);
    }
}
