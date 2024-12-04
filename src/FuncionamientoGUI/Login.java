/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Aumenta el tamaño del frame
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con padding
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Log In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel);

        // Campo de usuario
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Usuario"));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(usernameField);

        // Campo de contraseña
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(passwordField);

        // Checkbox para mostrar la contraseña
        showPasswordCheckBox = new JCheckBox("Mostrar contraseña");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(showPasswordCheckBox);

        // Acción para mostrar u ocultar la contraseña
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Muestra la contraseña
                } else {
                    passwordField.setEchoChar('*'); // Oculta la contraseña
                }
            }
        });

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton loginButton = new JButton("Login");
        JButton signInButton = new JButton("Crear Cuenta");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(loginButton);
        buttonPanel.add(signInButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);

        // Acción del botón Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Aquí puedes agregar lógica para validar el login
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil();
                    miPerfil.setVisible(true);
                });
            }
        });

        // Acción del botón Crear Cuenta
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new SignIn());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
