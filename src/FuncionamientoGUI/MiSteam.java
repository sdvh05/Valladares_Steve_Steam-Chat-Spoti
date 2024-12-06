package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MiSteam extends JFrame {

    private Administrador mas;
    private JList<String> libraryList; 
    
    private JList<String> addedList; 
    private DefaultListModel<String> libraryListModel; 
    private DefaultListModel<String> addedListModel; 
    private String sourceFolderPath; 
    private String destinationFolderPath; 
    private JPanel detailsPanel; 
    private JLabel gameNameLabel, genreLabel, developerLabel, releaseDateLabel, gamePathLabel;
    private JLabel gameImageLabel; 

    public MiSteam(Administrador mas) {
        this.mas = mas;
        this.sourceFolderPath = "AdminProyect/BibliotecaSteam";
        this.destinationFolderPath = mas.GameUser;

        setTitle("Library Home");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel izquierdo: Lista de juegos disponibles
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Juegos Disponibles"));

        libraryListModel = new DefaultListModel<>();
        libraryList = new JList<>(libraryListModel);
        libraryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        libraryList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedGame = libraryList.getSelectedValue();
                    if (selectedGame != null) {
                        loadGameDetails(selectedGame);
                    }
                }
            }
        });

        JScrollPane libraryScrollPane = new JScrollPane(libraryList);
        leftPanel.add(libraryScrollPane, BorderLayout.CENTER);

        // Panel derecho: Detalles del juego
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Juego"));

        gameNameLabel = new JLabel("Nombre: ");
        genreLabel = new JLabel("Género: ");
        developerLabel = new JLabel("Desarrollador: ");
        releaseDateLabel = new JLabel("Fecha de lanzamiento: ");
        gamePathLabel = new JLabel("Ruta del juego: ");
        gameImageLabel = new JLabel(); // Imagen del juego

        detailsPanel.add(gameNameLabel);
        detailsPanel.add(genreLabel);
        detailsPanel.add(developerLabel);
        detailsPanel.add(releaseDateLabel);
        detailsPanel.add(gamePathLabel);
        detailsPanel.add(gameImageLabel);

        // Panel derecho inferior: Mis Juegos
        JPanel myGamesPanel = new JPanel();
        myGamesPanel.setLayout(new BorderLayout());
        myGamesPanel.setBorder(BorderFactory.createTitledBorder("Mis Juegos"));

        addedListModel = new DefaultListModel<>();
        addedList = new JList<>(addedListModel);
        addedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addedList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedGame = addedList.getSelectedValue();
                    if (selectedGame != null) {
                        loadAddedGameDetails(selectedGame);
                    }
                }
            }
        });

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

        JScrollPane myGamesScrollPane = new JScrollPane(addedList);
        myGamesPanel.add(myGamesScrollPane, BorderLayout.CENTER);

        // Dividir el panel derecho en dos partes: Detalles y Mis Juegos
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailsPanel, myGamesPanel);
        rightSplitPane.setDividerLocation(300);

        // Botón para mover juegos a Mis Juegos
        JPanel moveButtonPanel = new JPanel();
        JButton moveButton = new JButton("Añadir a Mis Juegos");
        moveButton.addActionListener(e -> moveGameToAdded());
        moveButtonPanel.add(moveButton);

// Botón para eliminar juegos de Mis Juegos
        JButton deleteButton = new JButton("Eliminar de Mis Juegos");
        deleteButton.addActionListener(e -> deleteGameFromAdded());
        moveButtonPanel.add(deleteButton);

        leftPanel.add(moveButtonPanel, BorderLayout.SOUTH);

        // Dividir la ventana en dos partes: Juegos Disponibles y Panel Derecho
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightSplitPane);
        mainSplitPane.setDividerLocation(400);
        add(mainSplitPane, BorderLayout.CENTER);

        // Cargar juegos desde las carpetas
        loadLibraryGames();
        loadAddedGames();
    }

    // Cargar juegos disponibles
    private void loadLibraryGames() {
        File sourceFolder = new File(sourceFolderPath);
        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            JOptionPane.showMessageDialog(null, "La ruta de la biblioteca no es válida.");
            return;
        }

        File[] files = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".gfc"));
        if (files != null) {
            for (File file : files) {
                libraryListModel.addElement(file.getName());
            }
        }
    }

    // Cargar juegos añadidos
    private void loadAddedGames() {
        File destinationFolder = new File(destinationFolderPath);
        if (destinationFolder.exists() && destinationFolder.isDirectory()) {
            File[] files = destinationFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".gfc"));
            if (files != null) {
                for (File file : files) {
                    addedListModel.addElement(file.getName());
                }
            }
        }
    }

    // Mostrar detalles de un juego disponible
    private void loadGameDetails(String gameName) {
        Path gamePath = Paths.get(sourceFolderPath, gameName);
        if (Files.exists(gamePath)) {
            try (RandomAccessFile gameFile = new RandomAccessFile(gamePath.toFile(), "r")) {
                String name = gameFile.readUTF();
                String genre = gameFile.readUTF();
                String developer = gameFile.readUTF();
                String releaseDate = gameFile.readUTF();
                String gamePathStr = gameFile.readUTF();
                String imagePath = gameFile.readUTF();

                gameNameLabel.setText("Nombre: " + name);
                genreLabel.setText("Género: " + genre);
                developerLabel.setText("Desarrollador: " + developer);
                releaseDateLabel.setText("Fecha de lanzamiento: " + releaseDate);
                gamePathLabel.setText("Ruta del juego: " + gamePathStr);

                gameImageLabel.setIcon(new ImageIcon(imagePath));
                gameImageLabel.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo del juego.");
            }
        }
    }

    // Mostrar detalles de un juego añadido
    private void loadAddedGameDetails(String gameName) {
        Path gamePath = Paths.get(destinationFolderPath, gameName);
        if (Files.exists(gamePath)) {
            try (RandomAccessFile gameFile = new RandomAccessFile(gamePath.toFile(), "r")) {
                String name = gameFile.readUTF();
                String genre = gameFile.readUTF();
                String developer = gameFile.readUTF();
                String releaseDate = gameFile.readUTF();
                String gamePathStr = gameFile.readUTF();
                String imagePath = gameFile.readUTF();

                gameNameLabel.setText("Nombre: " + name);
                genreLabel.setText("Género: " + genre);
                developerLabel.setText("Desarrollador: " + developer);
                releaseDateLabel.setText("Fecha de lanzamiento: " + releaseDate);
                gamePathLabel.setText("Ruta del juego: " + gamePathStr);

                gameImageLabel.setIcon(new ImageIcon(imagePath));
                gameImageLabel.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo del juego.");
            }
        }
    }

    // Mover juego a Mis Juegos
    private void moveGameToAdded() {
        String selectedGame = libraryList.getSelectedValue();
        if (selectedGame != null) {
            Path sourcePath = Paths.get(sourceFolderPath, selectedGame);
            Path destinationPath = Paths.get(destinationFolderPath, selectedGame);

            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                addedListModel.addElement(selectedGame);
                JOptionPane.showMessageDialog(null, "Juego añadido a Mis Juegos.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al mover el juego.");
            }
        }
    }

    private void deleteGameFromAdded() {
        String selectedGame = addedList.getSelectedValue();
        if (selectedGame != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar \"" + selectedGame + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Path gamePath = Paths.get(destinationFolderPath, selectedGame);

                try {
                    Files.deleteIfExists(gamePath);
                    addedListModel.removeElement(selectedGame);
                    JOptionPane.showMessageDialog(null, "Juego eliminado de Mis Juegos.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el juego.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un juego para eliminar.");
        }
    }
}
