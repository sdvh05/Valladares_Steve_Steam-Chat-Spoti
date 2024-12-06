package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewMusic extends JFrame {
    private JTextField tituloField;
    private JTextField artistaField;
    private JTextField albumField;
    private JTextField duracionField;
    private JTextField rutaArchivoField;
    private JTextField rutaImagenField;
    private JLabel coverPreview;
    private JLabel discoPreview;
    private JTextField diaField;
    private JTextField anioField;
    private JComboBox<Mes> mesComboBox;
     private Administrador mas;
    
    public enum Mes {
        Enero, Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Octubre, Noviembre, Diciembre
    }

    public NewMusic(Administrador mas) {
        this.mas=mas;
        setTitle("Añadir Nueva Música");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(new BorderLayout());
        

        // Panel izquierdo Datos Nueva Música
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Ordenar y formato vertical
        formPanel.add(createLabeledField("Título:", tituloField = new JTextField()));
        formPanel.add(createLabeledField("Artista:", artistaField = new JTextField()));
        formPanel.add(createLabeledField("Álbum:", albumField = new JTextField()));
        formPanel.add(createLabeledField("Duración (segundos):", duracionField = new JTextField()));

        // Fecha de lanzamiento
        JPanel fechaPanel = new JPanel();
        fechaPanel.setLayout(new BoxLayout(fechaPanel, BoxLayout.Y_AXIS));
        fechaPanel.setBorder(BorderFactory.createTitledBorder("Fecha de Lanzamiento"));

        JPanel fechaInputsPanel = new JPanel();
        fechaInputsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        diaField = new JTextField(2);
        mesComboBox = new JComboBox<>(Mes.values());
        anioField = new JTextField(4);

        fechaInputsPanel.add(new JLabel("Día:"));
        fechaInputsPanel.add(diaField);
        fechaInputsPanel.add(Box.createHorizontalStrut(10));
        fechaInputsPanel.add(new JLabel("Mes:"));
        fechaInputsPanel.add(mesComboBox);
        fechaInputsPanel.add(Box.createHorizontalStrut(10));
        fechaInputsPanel.add(new JLabel("Año:"));
        fechaInputsPanel.add(anioField);

        fechaPanel.add(fechaInputsPanel);
        formPanel.add(fechaPanel);

        // Ruta de archivo
        JPanel archivoPanel = new JPanel(new BorderLayout());
        archivoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rutaArchivoField = new JTextField();
        rutaArchivoField.setEditable(false);
        archivoPanel.add(new JLabel("Ruta de la Cancion:"), BorderLayout.NORTH);
        archivoPanel.add(rutaArchivoField, BorderLayout.CENTER);
        JButton seleccionarArchivoBtn = new JButton("Seleccionar Archivo");
        seleccionarArchivoBtn.addActionListener(e -> seleccionarArchivo(rutaArchivoField));
        archivoPanel.add(seleccionarArchivoBtn, BorderLayout.SOUTH);
        formPanel.add(archivoPanel);

        // Ruta de imagen
        JPanel imagenPanel = new JPanel(new BorderLayout());
        imagenPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rutaImagenField = new JTextField();
        rutaImagenField.setEditable(false);
        imagenPanel.add(new JLabel("Carátula (Cover):"), BorderLayout.NORTH);
        imagenPanel.add(rutaImagenField, BorderLayout.CENTER);
        JButton seleccionarImagenBtn = new JButton("Seleccionar Imagen");
        seleccionarImagenBtn.addActionListener(e -> seleccionarArchivo(rutaImagenField));
        imagenPanel.add(seleccionarImagenBtn, BorderLayout.SOUTH);
        formPanel.add(imagenPanel);

        add(formPanel, BorderLayout.CENTER);

        // Panel Decoración
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Label Decoración
        discoPreview = new JLabel();
        discoPreview.setHorizontalAlignment(JLabel.CENTER);
        discoPreview.setVerticalAlignment(JLabel.CENTER);
        discoPreview.setPreferredSize(new Dimension(200, 200));

        // Cargar Imagen Disco
        ImageIcon discoImage = new ImageIcon("src/Imagenes/Disco.png");
        if (discoImage.getIconWidth() > 0) {
            Image scaledImage = discoImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            discoPreview.setIcon(new ImageIcon(scaledImage));
        } else {
            discoPreview.setText("Disco");
        }
        rightPanel.add(discoPreview);

        // Espacio para separarlo del preview
        rightPanel.add(Box.createVerticalStrut(20));

        // Preview de la carátula seleccionada
        coverPreview = new JLabel();
        coverPreview.setHorizontalAlignment(JLabel.CENTER);
        coverPreview.setVerticalAlignment(JLabel.CENTER);
        coverPreview.setPreferredSize(new Dimension(200, 200));
        coverPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        rightPanel.add(coverPreview);

        add(rightPanel, BorderLayout.EAST);

        // Botón para guardar
        JButton guardarBtn = new JButton("Guardar Música");
        guardarBtn.addActionListener(e -> guardarMusica());
        JPanel botonPanel = new JPanel();
        botonPanel.add(guardarBtn);
        add(botonPanel, BorderLayout.SOUTH);

        setVisible(true);
        
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

    // Método para crear campos con etiquetas en vertical
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    // Método para seleccionar archivos
    private void seleccionarArchivo(JTextField textField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getAbsolutePath());

            // Si es imagen, actualizamos el preview
            if (textField == rutaImagenField) {
                ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                coverPreview.setIcon(new ImageIcon(img));
            }
        }
    }

    private void guardarMusica() {
        String titulo = tituloField.getText();
        String artista = artistaField.getText();
        String album = albumField.getText();
        String duracionStr = duracionField.getText();
        String diaStr = diaField.getText();
        Mes mes = (Mes) mesComboBox.getSelectedItem();
        String anioStr = anioField.getText();
        String rutaArchivo = rutaArchivoField.getText();
        String rutaImagen = rutaImagenField.getText();

        try {
            int duracion = Integer.parseInt(duracionStr);
            int dia = Integer.parseInt(diaStr);
            int anio = Integer.parseInt(anioStr);

            if (titulo.isEmpty() || artista.isEmpty() || album.isEmpty() || rutaArchivo.isEmpty() || rutaImagen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar datos ingresados
                Administrador mas= new Administrador();
            JOptionPane.showMessageDialog(this, "Música Guardada:\n" +
                    "Título: " + titulo + "\n" +
                    "Artista: " + artista + "\n" +
                    "Álbum: " + album + "\n" +
                    "Duración: " + duracion + " segundos\n" +
                    "Fecha de Lanzamiento: " + dia + " " + mes + " " + anio + "\n" +
                    "Ruta de Archivo: " + rutaArchivo + "\n" +
                    "Carátula: " + rutaImagen, "Información Guardada", JOptionPane.INFORMATION_MESSAGE);
            mas.AddLibraryMusic(titulo, artista, album, duracion, rutaImagen, rutaImagen);
            
    
        }catch(Exception e){
            e.printStackTrace();;
        }
    }
}

