/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public final class Juego implements GM{
    
    private RandomAccessFile newGM;
    private String name;
    private String genero;
    private String desarrollador;
    private String releaseDate;
    private String rutaGame;
    private String rutaImagen;

    public Juego(String name, String genero, String desarrollador, String releaseDate, String rutaGame, String rutaImagen) {
        this.name = name;
        this.genero = genero;
        this.desarrollador = desarrollador;
        this.releaseDate = releaseDate;
        this.rutaGame = rutaGame;
        this.rutaImagen = rutaImagen;
        
        AddFiles();
    }
    
    public RandomAccessFile AddGame(String name, String genero, String desarrollador, String releaseDate, String rutagame, String rutaImagen)throws IOException{
        newGM.writeUTF(name);
        newGM.writeUTF(genero);
        newGM.writeUTF(desarrollador);
        newGM.writeUTF(releaseDate);
        newGM.writeUTF(rutagame);
        newGM.writeUTF(rutaImagen);
        return newGM;  
    }

    @Override
    public void AddFiles() {
        try {
            AddGame(name,genero,desarrollador,releaseDate,rutaGame,rutaImagen);
        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

              
    

}
