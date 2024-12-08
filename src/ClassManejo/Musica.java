/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public final class Musica implements GM {
    
    private RandomAccessFile newGM;
    private String titulo;
    private String artista;
    private String album;
    private int duracion;
    private String rutaMusica;
    private String rutaImagen;

    public Musica(String titulo, String artista, String album, int duracion, String rutaMusica, String rutaImagen) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
        this.rutaMusica = rutaMusica;
        this.rutaImagen = rutaImagen;
        
        AddFiles();
         
    }

    public RandomAccessFile AddMusic(String titulo, String artista, String album, int duracion, String rutaMusica,String rutaImagen) throws IOException {
        newGM = new RandomAccessFile(titulo + ".mp3", "rw");
        newGM.writeUTF(titulo);
        newGM.writeUTF(artista);
        newGM.writeUTF(album);
        newGM.writeInt(duracion);
        newGM.writeUTF(rutaMusica);
        newGM.writeUTF(rutaImagen);
        return newGM;
    }


    public void AddFiles() {
        try {
            AddMusic(titulo, artista, album, duracion, rutaMusica, rutaImagen);
        } catch (IOException ex) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

}
