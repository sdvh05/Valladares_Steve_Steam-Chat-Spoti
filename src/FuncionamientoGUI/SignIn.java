/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class SignIn extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JCheckBox showPasswordCheckBox;
    private Administrador mas;

    public SignIn(Administrador mas) {
        this.mas = mas;
        setTitle("Crear Cuenta");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 400); 
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        // UserField
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Usuario"));
        panel.add(usernameField);

        //PasswordFields
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        panel.add(passwordField);

        //Show Pass
        showPasswordCheckBox = new JCheckBox("Mostrar contraseña");
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); 
                } else {
                    passwordField.setEchoChar('*'); 
                }
            }
        });
        panel.add(showPasswordCheckBox);

        // Checkbox Admin
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
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Login LG = new Login (mas);
                    LG.setVisible(true);
                });
                dispose();
            }
        });

        // Crear Cuenta
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean isAdmin = adminCheckBox.isSelected();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, rellena todas las casillas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        boolean crear = mas.CreateUser(username, password, isAdmin);

                        if (crear) {
                            SwingUtilities.invokeLater(() -> {
                                MiPerfil miPerfil = new MiPerfil(mas);
                                miPerfil.setVisible(true);
                            });
                            dispose(); 
                        } else {
                            JOptionPane.showMessageDialog(null, "El username ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al crear el usuario. Inténtalo de nuevo.", "Error de I/O", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, "Error al crear el usuario", ex);
                    }
                }
            }
        });
        
        //ENTER da click al boton
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButton.doClick(); 
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButton.doClick(); 
            }
        });

        adminCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si el usuario selecciona cualquier opción en el combo, simula clic en el botón "Crear Cuenta"
                createButton.doClick();
            }
        });
        
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si el usuario selecciona cualquier opción en el combo, simula clic en el botón "Crear Cuenta"
                createButton.doClick();
            }
        });

        //Return
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Login(mas));
                dispose();
            }
        });
        
        usernameField.requestFocusInWindow();
        setVisible(true);
    }
    
}
