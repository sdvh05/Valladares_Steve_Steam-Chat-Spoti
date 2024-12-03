/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

/**
 *
 * @author Hp
 */
import javax.swing.*; 
import java.awt.*;
import javax.swing.SwingUtilities;

public class MiPerfil extends JFrame {

    public MiPerfil() {
        setTitle("Mi Perfil");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Title
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(600, 100));
        JLabel titulo = new JLabel("Opciones de: ", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(titulo, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Main Panel
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // FlowLayout para botones en línea
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones principales
        JButton botonMusica = new JButton("Música");
        botonMusica.setPreferredSize(new Dimension(150, 50));
        botonMusica.setFont(new Font("Arial", Font.BOLD, 16));
        botonMusica.setBackground(Color.green);

        JButton botonJuego = new JButton("Juego");
        botonJuego.setPreferredSize(new Dimension(150, 50));
        botonJuego.setFont(new Font("Arial", Font.BOLD, 16));
        botonJuego.setBackground(Color.cyan);

        JButton botonChat = new JButton("Chat");
        botonChat.setPreferredSize(new Dimension(150, 50));
        botonChat.setFont(new Font("Arial", Font.BOLD, 16));
        botonChat.setBackground(Color.red);

        panelCentral.add(botonMusica);
        panelCentral.add(botonJuego);
        panelCentral.add(botonChat);
        add(panelCentral, BorderLayout.CENTER);


        // Panel cerrar Sesión
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JButton cerrarSesion = new JButton("Cerrar Sesión");
        panelInferior.add(cerrarSesion);
        add(panelInferior, BorderLayout.SOUTH);

        // Listeners
        botonMusica.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                MusicPlayer frame = new MusicPlayer();
                frame.setVisible(true);
            });
        });

        botonJuego.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Acción Juego ejecutada", "Juego", JOptionPane.INFORMATION_MESSAGE);
        });

        botonChat.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Acción Chat ejecutada", "Chat", JOptionPane.INFORMATION_MESSAGE);
        });

        cerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar sesión?", "Confirmar Cierre de Sesión", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    Login frame = new Login(); 
                    frame.setVisible(true);
                    this.dispose();
                });
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MiPerfil frame = new MiPerfil();
            frame.setVisible(true);
        });
    }
}

