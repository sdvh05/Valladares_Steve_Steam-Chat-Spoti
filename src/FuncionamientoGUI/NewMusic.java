package FuncionamientoGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMusic extends JFrame {
    private JTextField tituloField;
    private JTextField artistaField;
    private JTextField albumField;
    private JTextField duracionField;
    private JTextField rutaArchivoField;
    private JTextField rutaImagenField;
    private JLabel coverPreview;
    private JLabel discoPreview;

    public NewMusic() {
        setTitle("Añadir Nueva Música");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(new BorderLayout());

        // Panel izquierdo Datos Nueva Musica
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //Ordenar y formato vertical
        formPanel.add(createLabeledField("Título:", tituloField = new JTextField()));
        formPanel.add(createLabeledField("Artista:", artistaField = new JTextField()));
        formPanel.add(createLabeledField("Álbum:", albumField = new JTextField()));
        formPanel.add(createLabeledField("Duración (segundos):", duracionField = new JTextField()));

        //Ruta de archivo
        JPanel archivoPanel = new JPanel(new BorderLayout());
        archivoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rutaArchivoField = new JTextField();
        rutaArchivoField.setEditable(false);
        archivoPanel.add(new JLabel("Ruta de Archivo:"), BorderLayout.NORTH);
        archivoPanel.add(rutaArchivoField, BorderLayout.CENTER);
        JButton seleccionarArchivoBtn = new JButton("Seleccionar Archivo");
        seleccionarArchivoBtn.addActionListener(e -> seleccionarArchivo(rutaArchivoField));
        archivoPanel.add(seleccionarArchivoBtn, BorderLayout.SOUTH);
        formPanel.add(archivoPanel);

        //Ruta de imagen
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

        //Pannel Decoracion
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Espaciado exterior
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        //Label Decoracion
        discoPreview = new JLabel();
        discoPreview.setHorizontalAlignment(JLabel.CENTER);
        discoPreview.setVerticalAlignment(JLabel.CENTER);
        discoPreview.setPreferredSize(new Dimension(200, 200));

        //Cargar Imagen Disco
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
        guardarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMusica();
            }
        });
        JPanel botonPanel = new JPanel();
        botonPanel.add(guardarBtn);
        add(botonPanel, BorderLayout.SOUTH);

        setVisible(true);
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
        String rutaArchivo = rutaArchivoField.getText();
        String rutaImagen = rutaImagenField.getText();

        try {
            int duracion = Integer.parseInt(duracionStr);

            if (titulo.isEmpty() || artista.isEmpty() || album.isEmpty() || rutaArchivo.isEmpty() || rutaImagen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar datos ingresados
            JOptionPane.showMessageDialog(this, "Música Guardada:\n" +
                    "Título: " + titulo + "\n" +
                    "Artista: " + artista + "\n" +
                    "Álbum: " + album + "\n" +
                    "Duración: " + duracion + " segundos\n" +
                    "Ruta de Archivo: " + rutaArchivo + "\n" +
                    "Carátula: " + rutaImagen, "Información Guardada", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewMusic::new);
    }
}
