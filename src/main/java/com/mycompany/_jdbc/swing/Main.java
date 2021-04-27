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
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ConnectionGui cn = new ConnectionGui();
            cn.createAndShowGUI();
        });

    }
}
