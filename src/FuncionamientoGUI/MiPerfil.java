/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import ClassManejo.Administrador;
import FuncionamientoGUI.TESTchat.ChatCliente;
import FuncionamientoGUI.TESTchat.ChatEnVivoo;
import FuncionamientoGUI.TESTchat.try3.ServidorChat;
import javax.swing.*;
import java.awt.*;
import javax.swing.SwingUtilities;

 // Enum para opciones de música
enum OpcionMusica {
    Reproductor, Agregar_Biblioteca, Mi_Musica;
}

// Enum para opciones de juegos
enum OpcionJuegos {
    Ver_mis_Juegos, Añadir_Juegos
}

public class MiPerfil extends JFrame {
    private Administrador mas;

    public MiPerfil(Administrador mas) {
        this.mas = mas;
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

        // Iniciar el servidor en segundo plano
        //iniciarServidor();

        // Listeners
        botonMusica.addActionListener(e -> {
            OpcionMusica[] opcionesMusica = {OpcionMusica.Reproductor, OpcionMusica.Agregar_Biblioteca, OpcionMusica.Mi_Musica};
            int seleccionMusica = JOptionPane.showOptionDialog(
                    null,
                    "¿Qué acción desea realizar con la música?",
                    "Opciones de Música",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesMusica,
                    opcionesMusica[0]);

            if (seleccionMusica >= 0 && seleccionMusica < opcionesMusica.length) {
                OpcionMusica opcionSeleccionadaMusica = opcionesMusica[seleccionMusica];
                switch (opcionSeleccionadaMusica) {
                    case Reproductor:
                        SwingUtilities.invokeLater(() -> {
                            MusicPlayer frame = new MusicPlayer(mas);
                            frame.setVisible(true);
                            this.dispose();
                        });
                        break;
                    case Agregar_Biblioteca:
                        if (mas.Permisos) {
                            SwingUtilities.invokeLater(() -> {
                                NewMusic frame = new NewMusic(mas);
                                frame.setVisible(true);
                                this.dispose();
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "No tienes Permisos de Administrador");
                        }
                        break;
                    case Mi_Musica:
                        SwingUtilities.invokeLater(() -> {
                            AgregarMiMusica frame = new AgregarMiMusica(mas);
                            frame.setVisible(true);
                        });
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción para música.");
            }
        });

        botonJuego.addActionListener(e -> {
            OpcionJuegos[] opcionesJuegos = {OpcionJuegos.Ver_mis_Juegos, OpcionJuegos.Añadir_Juegos};
            int seleccionJuegos = JOptionPane.showOptionDialog(
                    null,
                    "¿Qué acción desea realizar con los juegos?",
                    "Opciones de Juegos",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcionesJuegos,
                    opcionesJuegos[0]);

            if (seleccionJuegos >= 0 && seleccionJuegos < opcionesJuegos.length) {
                OpcionJuegos opcionSeleccionadaJuegos = opcionesJuegos[seleccionJuegos];
                switch (opcionSeleccionadaJuegos) {
                    case Ver_mis_Juegos:
                        SwingUtilities.invokeLater(() -> {
                            MiSteam frame = new MiSteam(mas);
                            frame.setVisible(true);
                            this.dispose();
                        });
                        break;
                    case Añadir_Juegos:
                        if (mas.Permisos) {
                            SwingUtilities.invokeLater(() -> {
                                NewJuego frame = new NewJuego(mas);
                                frame.setVisible(true);
                                this.dispose();
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "No tienes Permisos de Administrador");
                        }
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción para juegos.");
            }
        });

        botonChat.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                ChatCliente chat = new ChatCliente(mas, mas.UserLog, "localhost", 12345);
                chat.setVisible(true);
                this.dispose();
            });
        });

        cerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar sesión?", "Confirmar Cierre de Sesión", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    Login frame = new Login(mas);
                    frame.setVisible(true);
                    this.dispose();
                });
            }
        });
    }

//    private void iniciarServidor() {
//        mas.openServer();
//        Thread servidorThread = new Thread(() -> {
//            try {
//                if (mas.open) {
//                    ServidorChat.main(new String[0]);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        servidorThread.start();
//    }
}
