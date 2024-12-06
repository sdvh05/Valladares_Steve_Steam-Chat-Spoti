package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

public class MusicPlayer extends JFrame {

    private Administrador mas;
    private JList<String> songList;
    private DefaultListModel<String> listModel;
    private JButton playPauseButton, nextButton, previousButton;
    private String carpetaMusica;
    private JLabel songName, albumName, artistName;
    private JLabel albumImage;
    private RandomAccessFile musicFile;

    public MusicPlayer(Administrador mas) {
        this.mas = mas;
        carpetaMusica = mas.MusicaUser;
        setTitle("Reproductor de Música");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Imagen de la Canción, Nombre y Álbum
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Imagen de la Canción
        albumImage = new JLabel(new ImageIcon("src/Imagenes/CancionNull.png"));
        albumImage.setHorizontalAlignment(JLabel.CENTER);
        panelSuperior.add(albumImage, BorderLayout.NORTH);

        // Nombre y Álbum
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        songName = new JLabel("Nombre de la Canción");
        songName.setFont(new Font("Arial", Font.BOLD, 18));
        artistName = new JLabel("Artista: Nombre del Artista");
        artistName.setFont(new Font("Arial", Font.PLAIN, 14));
        albumName = new JLabel("Álbum: Nombre del Álbum");
        albumName.setFont(new Font("Arial", Font.PLAIN, 14));

        infoPanel.add(songName);
        infoPanel.add(artistName);
        infoPanel.add(albumName);
        panelSuperior.add(infoPanel, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        
        //prueba
        

        // Panel Central: Slider de Progreso y Botones de Control
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BorderLayout());

        // Slider de Progreso
        JSlider slider = new JSlider(0, 100, 0);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        panelControles.add(slider, BorderLayout.NORTH);

        // Botones de Control
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        previousButton = new JButton("Anterior");
        playPauseButton = new JButton("Play/Pause");
        nextButton = new JButton("Siguiente");

        botonesPanel.add(previousButton);
        botonesPanel.add(playPauseButton);
        botonesPanel.add(nextButton);
        
//        ImageIcon nextIcon = new ImageIcon("src/Imagenes/Next.png"); // Ruta de la imagen
//        nextButton.setIcon(nextIcon);
        

        panelControles.add(botonesPanel, BorderLayout.CENTER);
        add(panelControles, BorderLayout.CENTER);

        // Panel Inferior: Lista de Canciones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        // Lista de Canciones
        listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSong = songList.getSelectedValue();
                loadSongDetails(selectedSong);
            }
        });
        JScrollPane songScrollPane = new JScrollPane(songList);

        panelInferior.add(songScrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar las canciones de la carpeta
        cargarCanciones(carpetaMusica);

        // Evento de cierre
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

    // Método para cargar las canciones de una carpeta
    private void cargarCanciones(String carpetaMusica) {
        File folder = new File(carpetaMusica);
        if (folder.exists() && folder.isDirectory()) {
            FilenameFilter mp3Filter = (dir, name) -> name.endsWith(".mp3");
            File[] mp3Files = folder.listFiles(mp3Filter);
            if (mp3Files != null) {
                for (File file : mp3Files) {
                    listModel.addElement(file.getName());
                }
            }
        }
    }

    // Método para cargar y mostrar la información de la canción
    private void loadSongDetails(String songName) {
        try {
            File songFile = new File(carpetaMusica, songName);
            musicFile = new RandomAccessFile(songFile, "r");

            //Datos de la Rola
            String title = musicFile.readUTF();  
            String artist = musicFile.readUTF();  
            String album = musicFile.readUTF();  
            int duration = musicFile.readInt(); 
            String musicPath = musicFile.readUTF(); //ruta de la cancion en formato mp3
            String imagePath = musicFile.readUTF();  

            //Cambiar JLabel
            this.songName.setText(title);
            artistName.setText(artist);
            albumName.setText(album);
            if (imagePath != null && !imagePath.isEmpty()) {
                albumImage.setIcon(new ImageIcon(imagePath));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


 

  