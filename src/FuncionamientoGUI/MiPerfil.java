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

 // Enum para opciones de música
    enum OpcionMusica {
        Reproductor, Agregar_Biblioteca
    }

    // Enum para opciones de juegos
    enum OpcionJuegos {
        Ver_mis_Juegos, Añadir_Juegos
    }

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
            OpcionMusica[] opcionesMusica = {OpcionMusica.Reproductor, OpcionMusica.Agregar_Biblioteca};
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
                            MusicPlayer frame = new MusicPlayer();
                            frame.setVisible(true);
                        });
                        break;
                    case Agregar_Biblioteca:
                        SwingUtilities.invokeLater(() -> {
                            NewMusic frame = new NewMusic();
                            frame.setVisible(true);
                            this.dispose();
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
                        // Abir "Ver mis Juegos"
                        break;
                    case Añadir_Juegos:
                        SwingUtilities.invokeLater(() -> {
                            NewJuego frame = new NewJuego();
                            frame.setVisible(true);
                            this.dispose();
                        });
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción para juegos.");
            }
        });

        botonChat.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                ChatEnVivo frame = new ChatEnVivo();
                frame.setVisible(true);
                this.dispose();
            });
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

