package FuncionamientoGUI.TESTchat;

import ClassManejo.Administrador; 
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ChatCliente extends JFrame {
    private Administrador mas; 
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private JTextArea areaChat;
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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaChat);

        campoMensaje = new JTextField();
        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(e -> enviarMensaje());

        JPanel panelMensajes = new JPanel(new BorderLayout());
        panelMensajes.add(campoMensaje, BorderLayout.CENTER);
        panelMensajes.add(botonEnviar, BorderLayout.EAST);

        add(scroll, BorderLayout.CENTER);
        add(panelMensajes, BorderLayout.SOUTH);
    }

    private void conectarServidor(String host, int puerto) {
    try {
        socket = new Socket(host, puerto);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());

        // Enviar nombre de usuario al servidor
        salida.writeObject(nombreUsuario);
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
                SwingUtilities.invokeLater(() -> areaChat.append(mensaje + "\n"));
            }
        } catch (IOException | ClassNotFoundException e) {
            mostrarError("Conexión con el servidor perdida.");
        } finally {
            cerrarConexion();
        }
    }

    private void enviarMensaje() {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            try {
                salida.writeObject(nombreUsuario + ": " + mensaje);
                salida.flush();
                campoMensaje.setText("");
            } catch (IOException e) {
                mostrarError("No se pudo enviar el mensaje: " + e.getMessage());
            }
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
