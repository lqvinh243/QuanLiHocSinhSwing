/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._jdbc.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class ConnectionGui extends JPanel implements ActionListener {

    JButton button;
    JTextField textField;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(textField.getText());
    }

    public void createAndShowGUI() {
        setBorder(new EmptyBorder(8, 8, 8, 8));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        NamePane namePane = new NamePane();
        namePane.setBorder(new CompoundBorder(new TitledBorder("Connection"), new EmptyBorder(4, 4, 4, 4)));
        add(namePane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        Button btn = new Button();
        add(btn, gbc);
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class Button extends JPanel implements ActionListener {

    public Button() {
        JButton btn = new JButton("Check");
        btn.setPreferredSize(new Dimension(60, 40));
        add(btn, BorderLayout.CENTER);
        btn.setActionCommand("OK");
        btn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ConnectionUtils.getConnect() != null) {
            new StudentManagementGui();
            ((Window) getRootPane().getParent()).dispose();
        }
    }

}

class NamePane extends JPanel {

    JTextField serverName;
    JTextField port;
    JTextField databaseName;
    JTextField username;
    JTextField password;
    private int top = 3, left = 3, bottom = 3, right = 3;
    private Insets i = new Insets(top, left, bottom, right);

    public NamePane() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = i;
        add(new JLabel("Server name:"), gbc);
        gbc.gridx += 2;
        add(new JLabel("Port:"), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Database:"), gbc);
        gbc.gridx += 2;
        add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;
        serverName = new JTextField(10);
        serverName.setText(ConnectionUtils.getServername());
        serverName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ConnectionUtils.setServername(serverName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ConnectionUtils.setServername(serverName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ConnectionUtils.setServername(serverName.getText());
            }
        });
        add(serverName, gbc);
        gbc.gridx += 2;
        port = new JTextField(10);
        port.setText(ConnectionUtils.getPort().toString());
        port.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ConnectionUtils.setPort(Integer.parseInt(port.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ConnectionUtils.setPort(Integer.parseInt(port.getText()));

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ConnectionUtils.setPort(Integer.parseInt(port.getText()));
            }
        });
        add(port, gbc);
        gbc.gridy++;
        gbc.gridx = 1;
        databaseName = new JTextField(10);
        databaseName.setText(ConnectionUtils.getDatabaseName());
        databaseName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ConnectionUtils.setDatabasename(databaseName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ConnectionUtils.setDatabasename(databaseName.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ConnectionUtils.setDatabasename(databaseName.getText());
            }
        });
        add(databaseName, gbc);
        gbc.gridx += 2;
        username = new JTextField(10);
        username.setText(ConnectionUtils.getUsername());
        username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ConnectionUtils.setUsername(username.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ConnectionUtils.setUsername(username.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ConnectionUtils.setUsername(username.getText());
            }
        });
        add(username, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        password = new JPasswordField(20);
        password.setText(ConnectionUtils.getPassword());
        password.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ConnectionUtils.setPassword(password.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ConnectionUtils.setPassword(password.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ConnectionUtils.setPassword(password.getText());
            }
        });
        add(password, gbc);
    }
}
