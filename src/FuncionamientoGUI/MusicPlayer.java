package FuncionamientoGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MusicPlayer extends JFrame {

    public MusicPlayer() {
        setTitle("Reproductor de Música");
        setSize(600, 600); // Frame más ancho
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Parte Superior: Now Playing
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nowPlayingField = new JTextField("Ninguna canción");
        nowPlayingField.setEditable(false);
        nowPlayingField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel nowPlayingLabel = new JLabel("Now Playing:");
        nowPlayingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelSuperior.add(nowPlayingLabel, BorderLayout.WEST);
        panelSuperior.add(nowPlayingField, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel Central: Botones de Control
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BorderLayout());

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20)); 
        JButton previousButton = new JButton("Previous");
        JButton playPauseButton = new JButton("Play"); // Luego se reemplaza con una imagen
        JButton nextButton = new JButton("Next");

        //Tamaños
        playPauseButton.setPreferredSize(new Dimension(250, 200)); 
        previousButton.setPreferredSize(new Dimension(100, 50));
        nextButton.setPreferredSize(new Dimension(100, 50));

        botonesPanel.add(previousButton);
        botonesPanel.add(playPauseButton);
        botonesPanel.add(nextButton);

        panelControles.add(botonesPanel, BorderLayout.CENTER);

        // Slider de progreso
        JSlider slider = new JSlider(0, 100, 0);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        panelControles.add(slider, BorderLayout.SOUTH);

        add(panelControles, BorderLayout.CENTER);

        // Panel Inferior: Lista de canciones y botones de gestión
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        JPanel botonesInferioresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Eliminar");
        
        botonesInferioresPanel.add(addButton);
        botonesInferioresPanel.add(removeButton);

        JList<String> songList = new JList<>(new DefaultListModel<>());
        JScrollPane songScrollPane = new JScrollPane(songList);

        panelInferior.add(botonesInferioresPanel, BorderLayout.NORTH);
        panelInferior.add(songScrollPane, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

      
        //Hacer que el proyecto no termine con la X, sino que vuelva a mi PERFIL (Agregar DO_NOTHING_ON_CLOSE))
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Abrir Mi Perfil
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil();
                    miPerfil.setVisible(true);
                });
                dispose();
            }
        });
        
          // Listeners 
        addButton.addActionListener(e -> {
            String song = JOptionPane.showInputDialog("Ingrese el nombre de la canción:");
            if (song != null && !song.isEmpty()) {
                ((DefaultListModel<String>) songList.getModel()).addElement(song);
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = songList.getSelectedIndex();
            if (selectedIndex != -1) {
                ((DefaultListModel<String>) songList.getModel()).remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una canción para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        previousButton.addActionListener(e -> {
            // Acción para botón Previous
        });

        playPauseButton.addActionListener(e -> {
            // Acción para botón Play/Pause
        });

        nextButton.addActionListener(e -> {
            // Acción para botón Next
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicPlayer player = new MusicPlayer();
            player.setVisible(true);
        });
    }
}
