/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;

    public SignIn() {
        setTitle("Crear Cuenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Tamaño ajustado
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10)); // GridLayout ajustado
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        // Campo de texto para el usuario
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Usuario"));
        panel.add(usernameField);

        // Campo de texto para la contraseña
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        panel.add(passwordField);

        // Checkbox para cuenta de administrador
        adminCheckBox = new JCheckBox("Cuenta Administrador");
        panel.add(adminCheckBox);

        // Botones
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Crear Cuenta");
        JButton returnButton = new JButton("Regresar");

        buttonPanel.add(createButton);
        buttonPanel.add(returnButton);
        panel.add(buttonPanel);

        add(panel);

        // Acción para el botón "Crear Cuenta"
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean isAdmin = adminCheckBox.isSelected();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, rellena todas las casillas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    boolean crear = true; //Crear
                    if (crear) {
                        SwingUtilities.invokeLater(() -> {
                            MiPerfil miPerfil = new MiPerfil();
                            miPerfil.setVisible(true);
                        });
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "El username ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción para el botón "Regresar"
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Login());
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignIn::new);
    }
}

