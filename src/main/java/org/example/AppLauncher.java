package org.example;


import org.example.ui.LoginUI;

import javax.swing.*;

public class AppLauncher {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
            new LoginUI().setVisible(true);
        });
    }
}