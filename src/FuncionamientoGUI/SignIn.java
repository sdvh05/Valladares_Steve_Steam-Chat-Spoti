/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import FuncionamientoGUI.MiPerfil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignIn extends JFrame {
    private JTextField usernameField;

    public SignIn() {
        setTitle("SignIn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));  // Ajuste el GridLayout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Usuario"));
        panel.add(usernameField);

        JPanel buttonPanel = new JPanel();
        JButton Creatbtn = new JButton("Crear Cuenta");
        JButton Returnbtn = new JButton("Return");

        buttonPanel.add(Creatbtn);
        buttonPanel.add(Returnbtn);
        panel.add(buttonPanel);

        add(panel);

        
        Creatbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, rellena todas las casillas");
                } else {
                    boolean Crear=true; //= Funcion Crear de Admin
                    if (Crear) {
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "El username ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                //Abrir Mi Perfil
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil();
                    miPerfil.setVisible(true);
                });

            }
        });

        Returnbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JOptionPane.showMessageDialog(null, "Su Codigo de Usuario es: "+"\n Bienvenido al Proyecto");
                //Abrir CrearCuenta
                SwingUtilities.invokeLater(() -> new Login());
            }
        });

        setVisible(true);
    }

    private void VerficarLog() {
        String username = usernameField.getText();
        
//     
//        
//  if (username.isEmpty() || password.isEmpty()) {
//        JOptionPane.showMessageDialog(null, "Por favor, rellena todas las casillas");
//    } else {
//        if (password.length() != 5) {
//            JOptionPane.showMessageDialog(null, "La contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
//        } else {
//           
//            boolean crear = mas.CrearCuenta(username, password);
//            
//            if (crear) {
//                JOptionPane.showMessageDialog(null, "SE HA REGISTRADO CORRECTAMENTE");
//                new MainMenu(mas).setVisible(true);
//                this.dispose(); 
//                this.setLocationRelativeTo(null); 
//            } else {
//                JOptionPane.showMessageDialog(null, "El username ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
   }
  
    

}
