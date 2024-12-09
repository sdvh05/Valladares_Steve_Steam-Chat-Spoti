package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.RandomAccessFile;
import java.io.IOException;

public class VerLogsChat extends JFrame {

    private JPanel panelLogs;
    private Administrador mas;

    public VerLogsChat(Administrador mas) {
        this.mas = mas;
        setTitle("Logs del Chat");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarInterfaz();

        // Que abra mi perfil al cerrarse
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil(mas);
                    miPerfil.setVisible(true);
                });
                dispose();
            }
        });
    }

    private void inicializarInterfaz() {
        panelLogs = new JPanel();
        panelLogs.setLayout(new BoxLayout(panelLogs, BoxLayout.Y_AXIS));
        panelLogs.setBackground(new Color(0xF0F0F0));

        JScrollPane scroll = new JScrollPane(panelLogs);
        scroll.setPreferredSize(new Dimension(480, 560));

        cargarLogs(mas.AllLogs());

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
    }

    private void cargarLogs(String rutaArchivo) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            if (raf.length() == 0) {
                JLabel labelVacio = new JLabel("No hay mensajes en los logs.");
                labelVacio.setFont(new Font("Arial", Font.ITALIC, 14));
                labelVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelLogs.add(labelVacio);
            } else {
                while (raf.getFilePointer() < raf.length()) {
                    try {
                        String mensaje = raf.readUTF();
                        agregarMensajeAlPanel(mensaje);
                    } catch (IOException e) {
                        System.err.println("Error al leer un mensaje: " + e.getMessage());
                        break;
                    }
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los logs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            panelLogs.revalidate();
            panelLogs.repaint();
        
    }

    private void agregarMensajeAlPanel(String mensaje) {
        JLabel labelMensaje = new JLabel(mensaje);
        labelMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        labelMensaje.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelMensaje = new JPanel();
        panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.X_AXIS));
        panelMensaje.setBackground(new Color(0xB6D0E2));
        panelMensaje.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelMensaje.add(labelMensaje);

        panelLogs.add(panelMensaje);
    }
}


