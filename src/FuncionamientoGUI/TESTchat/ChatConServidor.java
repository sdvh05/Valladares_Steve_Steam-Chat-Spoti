package FuncionamientoGUI.TESTchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class ChatConServidor extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private ServerSocket serverSocket;
    private List<ObjectOutputStream> clientOutputs = new ArrayList<>();
    private Socket clientSocket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public ChatConServidor() {
        setTitle("Chat con Servidor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear elementos de la interfaz
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        sendButton = new JButton("Enviar");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Iniciar servidor en segundo plano
        iniciarServidor();

        // Eventos
        sendButton.addActionListener(e -> enviarMensaje());
        messageField.addActionListener(e -> enviarMensaje());

        // Escuchar mensajes en segundo plano
        new Thread(this::escucharMensajes).start();
    }

    private void iniciarServidor() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12345);
                chatArea.append("Servidor iniciado en el puerto 12345\n");

                while (true) {
                    clientSocket = serverSocket.accept();
                    chatArea.append("Nuevo cliente conectado.\n");
                    entrada = new ObjectInputStream(clientSocket.getInputStream());
                    salida = new ObjectOutputStream(clientSocket.getOutputStream());
                    synchronized (clientOutputs) {
                        clientOutputs.add(salida);
                    }
                    new Thread(() -> escucharMensajesCliente(entrada)).start();
                }
            } catch (IOException e) {
                chatArea.append("Error al iniciar el servidor: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void escucharMensajesCliente(ObjectInputStream entradaCliente) {
        try {
            String mensaje;
            while ((mensaje = (String) entradaCliente.readObject()) != null) {
                chatArea.append("Cliente: " + mensaje + "\n");
                enviarMensajeGlobal("Cliente: " + mensaje);
            }
        } catch (IOException | ClassNotFoundException e) {
            chatArea.append("Cliente desconectado.\n");
        }
    }

    private void escucharMensajes() {
        try {
            String mensaje;
            while ((mensaje = (String) entrada.readObject()) != null) {
                chatArea.append("Servidor: " + mensaje + "\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            chatArea.append("Conexión perdida.\n");
        }
    }

    private void enviarMensaje() {
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            chatArea.append("Tú: " + mensaje + "\n");
            enviarMensajeGlobal("Servidor: " + mensaje);
            messageField.setText("");
        }
    }

    private void enviarMensajeGlobal(String mensaje) {
        synchronized (clientOutputs) {
            for (ObjectOutputStream salidaCliente : clientOutputs) {
                try {
                    salidaCliente.writeObject(mensaje);
                    salidaCliente.flush();
                } catch (IOException e) {
                    chatArea.append("Error al enviar mensaje a un cliente.\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatConServidor chatConServidor = new ChatConServidor();
            chatConServidor.setVisible(true);
        });
    }
}
