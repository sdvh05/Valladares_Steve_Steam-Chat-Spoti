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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignIn extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private Administrador mas;

    public SignIn(Administrador mas) {
        this.mas = mas;
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

                // Verificar si los campos están vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, rellena todas las casillas", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        // Llamada al método CreateUser para crear el nuevo usuario
                        boolean crear = mas.CreateUser(username, password, isAdmin);

                        // Comprobar si la creación fue exitosa
                        if (crear) {
                            SwingUtilities.invokeLater(() -> {
                                MiPerfil miPerfil = new MiPerfil(mas);
                                miPerfil.setVisible(true);
                            });
                            dispose(); // Cerrar la ventana de registro
                        } else {
                            // Si el usuario ya existe
                            JOptionPane.showMessageDialog(null, "El username ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        // Manejo de excepciones: mostrar un mensaje de error si ocurre un problema de IO
                        JOptionPane.showMessageDialog(null, "Error al crear el usuario. Inténtalo de nuevo.", "Error de I/O", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, "Error al crear el usuario", ex);
                    }
                }
            }
        });

        // Acción para el botón "Regresar"
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Login(mas));
                dispose();
            }
        });

        setVisible(true);
    }

}
