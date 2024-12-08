package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;

public class MusicPlayer extends JFrame {

    private Administrador mas;
    private JList<String> songList;
    private DefaultListModel<String> listModel;
    private JButton playPauseButton, nextButton, previousButton;
    private String carpetaMusica, musicPath;
    private JLabel songName, albumName, artistName;
    private JLabel albumImage;
    private RandomAccessFile musicFile;
    private String currentSongPath,artist,titlee;
    private boolean isPlaying;
    private Clip clip;
    private Player player;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;

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
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        // Separar imagen con la información
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(0, 30));
        panelSuperior.add(spacerPanel, BorderLayout.CENTER);
        panelSuperior.add(infoPanel, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Slider y Botones
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BorderLayout());

        // Slider
        JSlider slider = new JSlider(0, 100, 0);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        panelControles.add(slider, BorderLayout.NORTH);

        // Botones PPN
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        previousButton = new JButton();
        playPauseButton = new JButton();
        nextButton = new JButton();

        // Botón "Anterior"
        ImageIcon prevIcon = new ImageIcon("src/Imagenes/Previous.png");
        previousButton.setIcon(prevIcon);
        previousButton.setContentAreaFilled(false); // Poner Fondo transparente
        previousButton.setBorderPainted(false);    // Quitar borde

        // Play/Pause
        ImageIcon playIcon = new ImageIcon("src/Imagenes/Pause.png");
        playPauseButton.setIcon(playIcon);
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setBorderPainted(false);

        // Siguiente
        ImageIcon nextIcon = new ImageIcon("src/Imagenes/Next.png");
        nextButton.setIcon(nextIcon);
        nextButton.setContentAreaFilled(false);
        nextButton.setBorderPainted(false);

        // Añadir los botones
        botonesPanel.add(previousButton);
        botonesPanel.add(playPauseButton);
        botonesPanel.add(nextButton);

        panelControles.add(botonesPanel, BorderLayout.CENTER);
        add(panelControles, BorderLayout.CENTER);

        // Panel Lista de Canciones
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
        songList.setCellRenderer(new SongListCellRenderer());
        songList.setPreferredSize(new Dimension(400, 300));
        JScrollPane songScrollPane = new JScrollPane(songList);

        panelInferior.add(songScrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        cargarCanciones(carpetaMusica);

        // Que abra mi perfil al cerrarse
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    MiPerfil miPerfil = new MiPerfil(mas);
                    miPerfil.setVisible(true);
                });
                if (isPlaying) {
                    player.close();
                }

                dispose();
            }
        });

        // Acción para alternar entre Play y Pause
        playPauseButton.addActionListener(e -> togglePlayPause());
        
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSong = songList.getSelectedValue();
                loadSongDetails(selectedSong);
                if (isPlaying) {
                    player.close();
                }
                isPlaying=false;
                // Llamar a togglePlayPause() al seleccionar la canción
                togglePlayPause();
            }
        });
    }
    

    // Cargar mis Canciones a la Lista
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

    // Mostrar la info de la Canción
    private void loadSongDetails(String songName) {
        try {
            File songFile = new File(carpetaMusica, songName);
            musicFile = new RandomAccessFile(songFile, "r");

            // Datos de la Canción
            titlee = musicFile.readUTF();
            artist = musicFile.readUTF();
            String album = musicFile.readUTF();
            int duration = musicFile.readInt();
            musicPath = musicFile.readUTF(); // ruta de la canción en formato mp3
            String imagePath = musicFile.readUTF();

            //System.out.println("Ruta de la canción: " + musicPath);

            // Cambiar JLabel
            this.songName.setText(titlee);
            artistName.setText(artist);
            albumName.setText(album);
            if (imagePath != null && !imagePath.isEmpty()) {
                albumImage.setIcon(new ImageIcon(imagePath));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Alternar entre Play y Pause
    private void togglePlayPause() {
        if (musicPath != null) {
            if (isPlaying) {
                ImageIcon playIcon = new ImageIcon("src/Imagenes/Pause.png");
                playPauseButton.setIcon(playIcon);
                isPlaying=false;
                player.close();

            } else {
                ImageIcon playIcon = new ImageIcon("src/Imagenes/Playing.png");
                playPauseButton.setIcon(playIcon);
                reproducir(musicPath);                
            }
        }
    }
        

    // Reproducir la canción
    public void reproducir(String rutaArchivo) {
        if (musicPath != null) {
            isPlaying = true;
            ImageIcon playIcon = new ImageIcon("src/Imagenes/Playing.png");
            playPauseButton.setIcon(playIcon);
            try {
                File archivo = new File(rutaArchivo);
                if (!archivo.exists()) {
                    System.err.println("El archivo no existe: " + rutaArchivo);
                    return;
                }

                if (!rutaArchivo.endsWith(".mp3")) {
                    System.err.println("El archivo debe ser un MP3");
                    return;
                }

                fileInputStream = new FileInputStream(archivo);
                player = new Player(fileInputStream);

                new Thread(() -> {
                    try {
                        player.play();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (Exception e) {
                System.err.println("Error al reproducir: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private class SongListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String songName = (String) value;
            try {
                File songFile = new File(carpetaMusica, songName);
                RandomAccessFile musicFile = new RandomAccessFile(songFile, "r");

                // Cargar los detalles de la canción
                String title = musicFile.readUTF();
                String artist = musicFile.readUTF();

                // Formatear el texto
                setText("<html><b>" + title + "</b> | " + artist + "</html>");
                setFont(new Font("Arial", Font.PLAIN, 14));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}



