package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public LoginUI() {
        setTitle("Login");
        setSize(320, 190);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtUsuario = new JTextField();
        txtClave = new JPasswordField();
        JButton btnIngresar = new JButton("Ingresar");

        btnIngresar.addActionListener(e -> login());

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Clave:"));
        panel.add(txtClave);
        panel.add(new JLabel());
        panel.add(btnIngresar);

        add(panel);
    }

    private void login() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if ("admin".equals(usuario) && "admin".equals(clave)) {
            JOptionPane.showMessageDialog(this, "Bienvenido admin");
            new MenuPrincipalUI().setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
    }
}
