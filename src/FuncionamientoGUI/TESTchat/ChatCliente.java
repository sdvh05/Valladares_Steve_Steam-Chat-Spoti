package FuncionamientoGUI.TESTchat;

import ClassManejo.Administrador;
import FuncionamientoGUI.MiPerfil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import javax.swing.BoxLayout;

//GUI BUENO

public class ChatCliente extends JFrame {
    private Administrador mas;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private JPanel panelChat; // Panel para manejar los mensajes
    private JTextField campoMensaje;
    private JButton botonEnviar;
    private String nombreUsuario;

    public ChatCliente(Administrador mas, String nombreUsuario, String host, int puerto) {
        this.mas = mas;
        this.nombreUsuario = nombreUsuario;
        inicializarInterfaz();
        conectarServidor(host, puerto);
    }

    private void inicializarInterfaz() {
        setTitle("Chat - " + nombreUsuario);
        setSize(500, 600);  
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Usamos un panel para mostrar los mensajes
        panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS)); // Alinea los mensajes verticalmente
        JScrollPane scroll = new JScrollPane(panelChat);
        scroll.setPreferredSize(new Dimension(500, 500)); // Aseguramos que el área de chat se ajuste correctamente

        campoMensaje = new JTextField();
        campoMensaje.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enviarMensaje();
                }
            }
        });

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(e -> enviarMensaje());

        // Panel que contiene el campo de texto y el botón
        JPanel panelMensajes = new JPanel(new BorderLayout());
        panelMensajes.add(campoMensaje, BorderLayout.CENTER);
        panelMensajes.add(botonEnviar, BorderLayout.EAST);

        // Layout principal con el panel de chat ocupando el centro y el panel de mensajes al fondo
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(panelMensajes, BorderLayout.SOUTH);
        
        //Hacer que el proyecto no termine con la X, sino que vuelva a mi PERFIL (Agregar DO_NOTHING_ON_CLOSE))
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Abrir Mi Perfil
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil(mas);
                    miPerfil.setVisible(true);
                });
                dispose();
            }
        });
    }

    private void conectarServidor(String host, int puerto) {
        try {
            socket = new Socket(host, puerto);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            salida.flush();

            // Hilo para recibir mensajes
            new Thread(this::recibirMensajes).start();
        } catch (IOException e) {
            mostrarError("No se pudo conectar al servidor. Verifica que está activo.\n" + e.getMessage());
        }
    }

    private void recibirMensajes() {
        try {
            while (true) {
                String mensaje = (String) entrada.readObject();
                SwingUtilities.invokeLater(() -> mostrarMensaje(mensaje));
            }
        } catch (IOException | ClassNotFoundException e) {
            mostrarError("Conexión con el servidor perdida.");
        } finally {
            cerrarConexion();
        }
    }

    private void mostrarMensaje(String mensaje) {
    // Crear el JLabel con el mensaje
    JLabel labelMensaje = new JLabel(mensaje);
    

    labelMensaje.setMaximumSize(new Dimension(500, 20)); 
    labelMensaje.setPreferredSize(new Dimension(200, 10)); 

    // Crear un panel para cada mensaje
    JPanel panelMensaje = new JPanel();
    
    //Boxlayout SOLUCION DE FORMATO
    panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.X_AXIS)); 
    
    //Espacion y tamaños
    panelMensaje.setMaximumSize(new Dimension(500, 20)); 
    panelMensaje.setPreferredSize(new Dimension(200, 10));

    //Colorcto
    if (mensaje.startsWith(nombreUsuario + ":")) {
        labelMensaje.setHorizontalAlignment(SwingConstants.RIGHT);
         panelMensaje.setBackground(new Color(0xFA8072));  // Rojo suave (Salmon)
        panelMensaje.setBorder(BorderFactory.createLineBorder(new Color(0xA0A0A0), 1, true));
        panelMensaje.add(Box.createHorizontalGlue()); 
        panelMensaje.add(labelMensaje);
    } else {
        labelMensaje.setHorizontalAlignment(SwingConstants.LEFT);
        panelMensaje.setBackground(new Color(0x9BBFDC)); // Azul suave
        panelMensaje.setBorder(BorderFactory.createLineBorder(new Color(0xA0A0A0), 1, true));
        panelMensaje.add(Box.createHorizontalStrut(10)); 
        panelMensaje.add(labelMensaje);
    }

    // Agregar panel mensaje 
    panelChat.add(panelMensaje);
    panelChat.revalidate();
    panelChat.repaint();
}






    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            try {
                salida.writeObject(nombreUsuario + ": " + mensaje);
                salida.flush();
                campoMensaje.setText("");  // Limpiar el campo solo si el mensaje se envió correctamente
            } catch (IOException e) {
                mostrarError("No se pudo enviar el mensaje: " + e.getMessage());
            }
        } else {
            // Mostrar mensaje de error si el campo está vacío
            mostrarError("El mensaje no puede estar vacío.");
        }
    }

    private void cerrarConexion() {
        try {
            if (socket != null) socket.close();
            if (salida != null) salida.close();
            if (entrada != null) entrada.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
