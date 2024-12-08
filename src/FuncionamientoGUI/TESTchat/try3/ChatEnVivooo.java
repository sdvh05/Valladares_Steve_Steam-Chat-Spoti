package FuncionamientoGUI.TESTchat.try3;



import ClassManejo.Administrador;
import FuncionamientoGUI.MiPerfil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;


public class ChatEnVivooo extends JFrame {
    private Administrador mas;
    private ClienteChat cliente;

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JLabel titleLabel;
    private JList<String> userList;

    public ChatEnVivooo(Administrador mas) throws IOException {
        this.mas = mas;
        setTitle("Chat en Vivo");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Chat General");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Enviar");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Chat General");

        userList = new JList<>(listModel);
        userList.setFont(new Font("Arial", Font.PLAIN, 14));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setBorder(BorderFactory.createTitledBorder("Usuarios"));

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                messageField.setText("");
                String seleccion = userList.getSelectedValue();
                if ("Chat General".equals(seleccion)) {
                    titleLabel.setText("Chat General");
                } else {
                    titleLabel.setText("Chat con: " + seleccion);
                }
            }
        });

        sidePanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil(mas);
                    miPerfil.setVisible(true);
                });
                dispose();
            }
        });

        try {
            cliente = new ClienteChat("localhost", 12345);
            cliente.escucharMensajes(mensaje -> SwingUtilities.invokeLater(() -> {
                chatArea.append(mensaje + "\n");
            }));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        sendButton.addActionListener(e -> enviarMensaje());
        messageField.addActionListener(e -> enviarMensaje());
    }

    private void enviarMensaje() {
        String mensaje = messageField.getText().trim();
        if (!mensaje.isEmpty()) {
            chatArea.append(mas.UserLog + ": " + mensaje + "\n");
            cliente.enviarMensaje(mensaje);
            messageField.setText("");
        }
    }
}
