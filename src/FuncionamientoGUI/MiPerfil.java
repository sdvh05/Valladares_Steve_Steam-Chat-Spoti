/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import ClassManejo.Administrador;
import FuncionamientoGUI.TESTchat.ChatCliente;

import javax.swing.*;
import java.awt.*;

enum OpcionMusica {
    Reproductor, Agregar_Biblioteca, Mi_Musica;
}

enum OpcionJuegos {
    Ver_mis_Juegos, Añadir_Juegos;
}

enum OpcionChat{
    Ver_Logs, ChatEnVIVO;
}

public class MiPerfil extends JFrame {
    private Administrador mas;

    public MiPerfil(Administrador mas) {
        this.mas = mas;
        setTitle("Perfil de "+mas.UserLog);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(600, 100));
        JLabel titulo = new JLabel("Opciones de "+mas.UserLog, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelSuperior.add(titulo, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Botones en línea
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear botones con imágenes e íconos redimensionados
        JButton botonMusica = crearBotonConIcono(
                "Música",
                new ImageIcon("src/Imagenes/MusicGreen.jpg"),
                "Música"
        );

        JButton botonJuego = crearBotonConIcono(
                "Juego",
                new ImageIcon("src/Imagenes/Steammenu.jpeg"),
                "Juego"
        );

        JButton botonChat = crearBotonConIcono(
                "Chat",
                new ImageIcon("src/Imagenes/ChatLogo.jpg"),
                "Chat"
        );

        // Agregar botones al panel central
        panelCentral.add(botonMusica);
        panelCentral.add(botonJuego);
        panelCentral.add(botonChat);
        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botón de cerrar sesión
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JButton cerrarSesion = new JButton("Cerrar Sesión");
        panelInferior.add(cerrarSesion);
        add(panelInferior, BorderLayout.SOUTH);

        // Listeners de botones
        botonMusica.addActionListener(e -> mostrarOpcionesMusica());
        botonJuego.addActionListener(e -> mostrarOpcionesJuegos());
        botonChat.addActionListener(e -> mostrarOpcionesChat());

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

    private JButton crearBotonConIcono(String texto, ImageIcon icono, String tooltip) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setPreferredSize(new Dimension(150, 150));
        boton.setToolTipText(tooltip);

        // Redimensionar el ícono
        Image image = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        // Ícono redimensionado
        JLabel iconLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        boton.add(iconLabel, BorderLayout.CENTER);

        // Texto
        JLabel textLabel = new JLabel(texto, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 12));
        boton.add(textLabel, BorderLayout.SOUTH);

        return boton;
    }

    private void mostrarOpcionesMusica() {
        OpcionMusica[] opcionesMusica;
        if (mas.Permisos) {
            opcionesMusica = new OpcionMusica[]{OpcionMusica.Reproductor, OpcionMusica.Agregar_Biblioteca, OpcionMusica.Mi_Musica};
        } else {
            opcionesMusica = new OpcionMusica[]{OpcionMusica.Reproductor, OpcionMusica.Mi_Musica};
        }

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
                        this.dispose();
                    });
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción para música.");
        }
    }

    private void mostrarOpcionesJuegos() {
        OpcionJuegos[] opcionesJuegos;
        if (mas.Permisos) {
            opcionesJuegos = new OpcionJuegos[]{OpcionJuegos.Ver_mis_Juegos, OpcionJuegos.Añadir_Juegos};
        } else {
            opcionesJuegos = new OpcionJuegos[]{OpcionJuegos.Ver_mis_Juegos};
        }

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
    }
    
private void mostrarOpcionesChat() {
    
    SwingUtilities.invokeLater(() -> {
        ChatCliente chat = new ChatCliente(mas, mas.UserLog, "localhost", 12345);
        chat.setVisible(true);
        this.dispose();
    });
    
//    OpcionChat[] opcionesChat;
//
//    if (mas.Permisos) {
//        opcionesChat = OpcionChat.values(); 
//    } else {
//        opcionesChat = new OpcionChat[]{OpcionChat.ChatEnVIVO}; 
//    }
//
//    int seleccionChat = JOptionPane.showOptionDialog(
//            null,
//            "¿Qué acción desea realizar con el chat?",
//            "Opciones de Chat",
//            JOptionPane.DEFAULT_OPTION,
//            JOptionPane.QUESTION_MESSAGE,
//            null,
//            opcionesChat,
//            opcionesChat[0]);
//
//    if (seleccionChat >= 0 && seleccionChat < opcionesChat.length) {
//        OpcionChat opcionSeleccionadaChat = opcionesChat[seleccionChat];
//        switch (opcionSeleccionadaChat) {
//            case Ver_Logs:
//                SwingUtilities.invokeLater(() -> {
//                    VerLogsChat frame = new VerLogsChat(mas); 
//                    frame.setVisible(true);
//                    this.dispose();
//                });
//                break;
//            case ChatEnVIVO:
//                SwingUtilities.invokeLater(() -> {
//                    ChatCliente chat = new ChatCliente(mas, mas.UserLog, "localhost", 12345);
//                    chat.setVisible(true);
//                    this.dispose();
//                });
//                break;
//        }
//    } else {
//        JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción para el chat.");
//    }
}




}
