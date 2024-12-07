package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewJuego extends JFrame {

    private JTextField nombreField;
    private JTextField generoField;
    private JTextField desarrolladorField;
    private JTextField diaField;
    private JTextField anioField;
    private JComboBox<Mes> mesComboBox;
    private JTextField rutaInstalacionField;
    private JTextField rutaImagenField;
    private JLabel coverPreview;
    private JLabel discoPreview;

    private Administrador mas;

    public enum Mes {
        Enero, Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Octubre, Noviembre, Diciembre
    }

    public NewJuego(Administrador mas) {
        this.mas = mas;
        setTitle("Añadir Nuevo Juego");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel izquierdo (formulario)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Espaciado exterior

        // Crear elementos verticalmente
        formPanel.add(createLabeledField("Nombre:", nombreField = new JTextField()));
        formPanel.add(createLabeledField("Género:", generoField = new JTextField()));
        formPanel.add(createLabeledField("Desarrollador:", desarrolladorField = new JTextField()));

        // Fecha de lanzamiento
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fechaPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel fechaLabel = new JLabel("Fecha de Lanzamiento:");
        diaField = new JTextField(2);
        mesComboBox = new JComboBox<>(Mes.values());
        anioField = new JTextField(4);

        fechaPanel.add(fechaLabel);
        fechaPanel.add(new JLabel("Día:"));
        fechaPanel.add(diaField);
        fechaPanel.add(new JLabel("Mes:"));
        fechaPanel.add(mesComboBox);
        fechaPanel.add(new JLabel("Año:"));
        fechaPanel.add(anioField);

        formPanel.add(fechaPanel);

        // Ruta de instalación
        JPanel instalacionPanel = new JPanel(new BorderLayout());
        instalacionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rutaInstalacionField = new JTextField();
        rutaInstalacionField.setEditable(false);
        instalacionPanel.add(new JLabel("Ruta del Juego:"), BorderLayout.NORTH);
        instalacionPanel.add(rutaInstalacionField, BorderLayout.CENTER);
        JButton seleccionarArchivoBtn = new JButton("Seleccionar Carpeta");
        seleccionarArchivoBtn.addActionListener(e -> seleccionarArchivo(rutaInstalacionField));
        instalacionPanel.add(seleccionarArchivoBtn, BorderLayout.SOUTH);
        formPanel.add(instalacionPanel);

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

        // Panel derecho (imagen decorativa y preview)
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Espaciado exterior
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Imagen del disco decorativo
        discoPreview = new JLabel();
        discoPreview.setHorizontalAlignment(JLabel.CENTER);
        discoPreview.setVerticalAlignment(JLabel.CENTER);
        discoPreview.setPreferredSize(new Dimension(200, 200));

        // Cargar imagen decorativa
        ImageIcon discoImage = new ImageIcon("src/Imagenes/Steam.png");
        if (discoImage.getIconWidth() > 0) {
            Image scaledImage = discoImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            discoPreview.setIcon(new ImageIcon(scaledImage));
        } else {
            discoPreview.setText("Steam");
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

        // Botón para guardar
        JButton guardarBtn = new JButton("Guardar Juego");
        guardarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarJuego();
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
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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

    // Método para guardar el juego
    private void guardarJuego() {
        String nombre = nombreField.getText();
        String genero = generoField.getText();
        String desarrollador = desarrolladorField.getText();
        String dia = diaField.getText();
        Mes mes = (Mes) mesComboBox.getSelectedItem();
        String anio = anioField.getText();
        String fechaLanzamiento = dia + "/" + mes.ordinal() + "/" + anio;
        String rutaInstalacion = rutaInstalacionField.getText();
        String rutaImagen = rutaImagenField.getText();

        // Validaciones básicas
        if (nombre.isEmpty() || genero.isEmpty() || desarrollador.isEmpty() || dia.isEmpty() || anio.isEmpty()
                || rutaInstalacion.isEmpty() || rutaImagen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar datos ingresados
        JOptionPane.showMessageDialog(this, "Juego Guardado:\n"
                + "Nombre: " + nombre + "\n"
                + "Género: " + genero + "\n"
                + "Desarrollador: " + desarrollador + "\n"
                + "Fecha de Lanzamiento: " + fechaLanzamiento + "\n"
                + "Ruta de Instalación: " + rutaInstalacion + "\n"
                + "Carátula: " + rutaImagen, "Información Guardada", JOptionPane.INFORMATION_MESSAGE);

        try {
            mas.AddLibraryGame(nombre, genero, desarrollador, fechaLanzamiento, rutaInstalacion, rutaImagen);
            limpiarCampos();
        } catch (IOException ex) {
            Logger.getLogger(NewJuego.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void limpiarCampos() {
        nombreField.setText("");
        generoField.setText("");
        desarrolladorField.setText("");
        diaField.setText("");
        mesComboBox.setSelectedIndex(0); // Selecciona el primer mes
        anioField.setText("");
        rutaInstalacionField.setText("");
        rutaImagenField.setText("");
        coverPreview.setIcon(null); // Limpia la imagen de preview
    }

}
