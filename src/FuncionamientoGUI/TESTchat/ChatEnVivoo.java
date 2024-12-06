package FuncionamientoGUI.TESTchat;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatEnVivoo extends JFrame {
    private ServerSocket serverSocket;
    private HashMap<String, ObjectOutputStream> conexiones = new HashMap<>();
    private HashMap<String, JTextArea> chatPrivados = new HashMap<>();
    private JTextArea chatAreaGeneral;
    private JTextField messageField;
    private JLabel titleLabel;
    private JList<String> userList;
    private JScrollPane scrollPane;
    private Administrador mas;

    public ChatEnVivoo(Administrador mas) {
        this.mas = mas;
        inicializarGUI();
        configurarListeners();
    }

    private void inicializarGUI() {
        setTitle("Chat en Vivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        chatAreaGeneral = new JTextArea();
        chatAreaGeneral.setEditable(false);

        messageField = new JTextField();
        JButton sendButton = new JButton("Enviar");

        titleLabel = new JLabel("Chat General", JLabel.CENTER);

        userList = new JList<>(new DefaultListModel<>());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultListModel<String>) userList.getModel()).addElement("Chat General");

        scrollPane = new JScrollPane(chatAreaGeneral);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JLabel("Usuarios Conectados", JLabel.CENTER), BorderLayout.NORTH);
        userPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(userPanel, BorderLayout.WEST);
        add(messagePanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> enviarMensaje());
    }

    private void configurarListeners() {
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccion = userList.getSelectedValue();
                if ("Chat General".equals(seleccion)) {
                    titleLabel.setText("Chat General");
                    switchChatArea(chatAreaGeneral);
                } else if (chatPrivados.containsKey(seleccion)) {
                    titleLabel.setText("Chat con: " + seleccion);
                    switchChatArea(chatPrivados.get(seleccion));
                } else {
                    titleLabel.setText("Chat con: " + seleccion + " (No disponible)");
                }
            }
        });
    }

    private void switchChatArea(JTextArea newChatArea) {
        scrollPane.setViewportView(newChatArea);
        scrollPane.revalidate();
    }

    public void iniciarServidor() {
        new Thread(() -> {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    chatAreaGeneral.append("Servidor ya está en ejecución.\n");
                    return;
                }

                serverSocket = new ServerSocket(12345);
                chatAreaGeneral.append("Servidor iniciado en el puerto 12345.\n");

                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> manejarConexion(socket)).start();
                }
            } catch (IOException e) {
                chatAreaGeneral.append("Error al iniciar el servidor: " + e.getMessage() + "\n");
            }
        }).start();
    }

    private void manejarConexion(Socket socket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String username = (String) in.readObject();

            conexiones.put(username, out);
            chatPrivados.putIfAbsent(username, new JTextArea());
            actualizarListaUsuarios();

            String mensaje;
            while ((mensaje = (String) in.readObject()) != null) {
                if (mensaje.startsWith("@")) {
                    String destinatario = mensaje.split(" ")[0].substring(1);
                    String contenido = mensaje.substring(mensaje.indexOf(" ") + 1);
                    enviarMensajePrivado(username, destinatario, contenido);
                } else {
                    enviarMensajeGeneral(username, mensaje);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensajeGeneral(String remitente, String mensaje) {
        String mensajeFormateado = remitente + ": " + mensaje + "\n";
        chatAreaGeneral.append(mensajeFormateado);
        conexiones.values().forEach(out -> {
            try {
                out.writeObject(mensajeFormateado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void enviarMensajePrivado(String remitente, String destinatario, String mensaje) {
        String mensajeFormateado = "Privado de " + remitente + ": " + mensaje + "\n";
        JTextArea chat = chatPrivados.get(destinatario);
        if (chat != null) {
            chat.append(mensajeFormateado);
        }
        ObjectOutputStream out = conexiones.get(destinatario);
        if (out != null) {
            try {
                out.writeObject(mensajeFormateado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualizarListaUsuarios() {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> modelo = (DefaultListModel<String>) userList.getModel();
            modelo.removeAllElements();
            modelo.addElement("Chat General");
            conexiones.keySet().forEach(modelo::addElement);
        });
    }

    private void enviarMensaje() {
        String destinatario = userList.getSelectedValue();
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            if ("Chat General".equals(destinatario)) {
                enviarMensajeGeneral("Yo", mensaje);
            } else {
                enviarMensajePrivado("Yo", destinatario, mensaje);
            }
            messageField.setText("");
        }
    }
}

