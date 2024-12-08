package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AgregarMiMusica extends JFrame {
    private JList<FileItem> libraryList;
    private JList<FileItem> addedList;
    private DefaultListModel<FileItem> libraryListModel;
    private DefaultListModel<FileItem> addedListModel;
    private String sourceFolderPath;
    private String destinationFolderPath;
    private Administrador mas;

    public AgregarMiMusica(Administrador mas) {
        this.mas = mas;
        this.sourceFolderPath = "AdminProyect/BibliotecaMusical";
        this.destinationFolderPath = mas.MusicaUser;

        setTitle("Gestor de Biblioteca de Música");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Izquierdo: Música de la Biblioteca
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Música de la Biblioteca"));

        libraryListModel = new DefaultListModel<>();
        libraryList = new JList<>(libraryListModel);
        libraryList.setCellRenderer(new FileItemRenderer());
        JScrollPane libraryScrollPane = new JScrollPane(libraryList);
        leftPanel.add(libraryScrollPane, BorderLayout.CENTER);

        // Botón Añadir
        JPanel addButtonPanel = new JPanel();
        JButton addButton = new JButton("Añadir");
        addButton.addActionListener(new AddButtonListener());
        addButtonPanel.add(addButton);
        leftPanel.add(addButtonPanel, BorderLayout.SOUTH);

        // Panel Derecho: Archivos Añadidos
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Archivos Añadidos"));

        addedListModel = new DefaultListModel<>();
        addedList = new JList<>(addedListModel);
        addedList.setCellRenderer(new FileItemRenderer());
        JScrollPane addedScrollPane = new JScrollPane(addedList);
        rightPanel.add(addedScrollPane, BorderLayout.CENTER);

        // Botón Eliminar
        JPanel removeButtonPanel = new JPanel();
        JButton removeButton = new JButton("Eliminar");
        removeButton.addActionListener(new RemoveButtonListener());
        removeButtonPanel.add(removeButton);
        rightPanel.add(removeButtonPanel, BorderLayout.SOUTH);

        // Agregar paneles a la ventana principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // Cargar archivos de la biblioteca
        loadLibraryFiles();
        loadAddedFiles();

        // Hacer que el proyecto no termine con la X, sino que vuelva a mi PERFIL
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

    // Cargar archivos desde la carpeta de origen (Biblioteca)
    private void loadLibraryFiles() {
        File sourceFolder = new File(sourceFolderPath);
        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            JOptionPane.showMessageDialog(null, "La ruta de la biblioteca no es válida.");
            return;
        }
        File[] files = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        if (files != null) {
            for (File file : files) {
                libraryListModel.addElement(readFileItem(file));
            }
        }
    }

    // Cargar archivos desde la carpeta de destino (Archivos Añadidos)
    private void loadAddedFiles() {
        if (destinationFolderPath == null) {
            JOptionPane.showMessageDialog(null, "La ruta de destino no está definida.");
            return;
        }

        File destinationFolder = new File(destinationFolderPath);
        if (destinationFolder.exists() && destinationFolder.isDirectory()) {
            File[] files = destinationFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null) {
                for (File file : files) {
                    addedListModel.addElement(readFileItem(file));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La carpeta de destino no existe o no es un directorio.");
        }
    }

    private FileItem readFileItem(File file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            String titulo = raf.readUTF();
            String artista = raf.readUTF();
            String album = raf.readUTF();
            int duracion = raf.readInt();
            String rutaMusica = raf.readUTF();
            String rutaImagen = raf.readUTF();

            return new FileItem(file, titulo, artista, album, rutaImagen);
        } catch (IOException e) {
            return new FileItem(file, "Error", "Desconocido", "Desconocido", null);
        }
    }

    // Listener para el botón "Añadir"
    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileItem selectedItem = libraryList.getSelectedValue();
            if (selectedItem != null) {
                Path sourcePath = selectedItem.getFile().toPath();
                Path destinationPath = Paths.get(destinationFolderPath, selectedItem.getFile().getName());

                try {
                    Files.copy(sourcePath, destinationPath);
                    addedListModel.addElement(selectedItem);
                    JOptionPane.showMessageDialog(null, "Archivo añadido correctamente.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al añadir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un archivo para añadir.");
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileItem selectedItem = addedList.getSelectedValue();
            if (selectedItem != null) {
                Path destinationPath = selectedItem.getFile().toPath();

                try {
                    Files.delete(destinationPath);
                    addedListModel.removeElement(selectedItem);
                    JOptionPane.showMessageDialog(null, "Archivo eliminado correctamente.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un archivo para eliminar.");
            }
        }
    }

    // Clase FileItem para representar los datos del archivo
    private static class FileItem {
        private final File file;
        private final String titulo;
        private final String artista;
        private final String album;
        private final String rutaImagen;

        public FileItem(File file, String titulo, String artista, String album, String rutaImagen) {
            this.file = file;
            this.titulo = titulo;
            this.artista = artista;
            this.album = album;
            this.rutaImagen = rutaImagen;
        }

        public File getFile() {
            return file;
        }

        public String getRutaImagen() {
            return rutaImagen;
        }

        @Override
        public String toString() {
            return titulo; // Solo muestra el título en la lista
        }
    }

    // Renderizador para mostrar imagen y detalles
    private static class FileItemRenderer extends JPanel implements ListCellRenderer<FileItem> {
        private final JLabel iconLabel;
        private final JLabel titleLabel;
        private final JLabel artistLabel;
        private final JLabel albumLabel;

        public FileItemRenderer() {
            setLayout(new BorderLayout(5, 5));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

            iconLabel = new JLabel();
            iconLabel.setPreferredSize(new Dimension(50, 50));
            add(iconLabel, BorderLayout.WEST);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new GridLayout(3, 1));

            // Configuración del título
            titleLabel = new JLabel();
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); 

            // Configuración del artista
            artistLabel = new JLabel();
            artistLabel.setFont(new Font("Arial", Font.PLAIN, 12)); 

            // Configuración del álbum
            albumLabel = new JLabel();
            albumLabel.setFont(new Font("Arial", Font.ITALIC, 12)); 

            textPanel.add(titleLabel);
            textPanel.add(artistLabel);
            textPanel.add(albumLabel);
            add(textPanel, BorderLayout.CENTER);
}

        @Override
        public Component getListCellRendererComponent(JList<? extends FileItem> list, FileItem value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.getRutaImagen() != null) {
                iconLabel.setIcon(new ImageIcon(new ImageIcon(value.getRutaImagen()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            } else {
                iconLabel.setIcon(null);
            }
            titleLabel.setText(value.titulo);
            artistLabel.setText(value.artista);
            albumLabel.setText(value.album);

            setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
            setForeground(Color.BLACK);
            return this;
        }
    }
}

