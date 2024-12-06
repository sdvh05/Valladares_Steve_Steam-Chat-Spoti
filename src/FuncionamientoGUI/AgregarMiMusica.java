/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    private JList<String> libraryList;
    private JList<String> addedList;
    private DefaultListModel<String> libraryListModel;
    private DefaultListModel<String> addedListModel;
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
            libraryListModel.addElement(file.getName());
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
                addedListModel.addElement(file.getName());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "La carpeta de destino no existe o no es un directorio.");
    }
}


    // Listener para el botón "Añadir"
    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedFile = libraryList.getSelectedValue();
            if (selectedFile != null) {
                Path sourcePath = Paths.get(sourceFolderPath, selectedFile);
                Path destinationPath = Paths.get(destinationFolderPath, selectedFile);

                try {
                    // Copiar el archivo de la biblioteca a la carpeta de destino
                    Files.copy(sourcePath, destinationPath);
                    addedListModel.addElement(selectedFile);
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
            String selectedFile = addedList.getSelectedValue();
            if (selectedFile != null) {
                Path destinationPath = Paths.get(destinationFolderPath, selectedFile);

                try {
                    Files.delete(destinationPath);
                    addedListModel.removeElement(selectedFile);
                    JOptionPane.showMessageDialog(null, "Archivo eliminado correctamente.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un archivo para eliminar.");
            }
        }
    }


}
