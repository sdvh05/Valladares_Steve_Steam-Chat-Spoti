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

/**
 *
 * @author Hp
 */
public class Juego extends GM{
    

    public Juego(String name, String genero, String desarrollador, String releaseDate, String rutagame, String rutaImagen) {

    }

    @Override
    public String PrintList(RandomAccessFile games) throws IOException {
        games.seek(0);

        while (games.getFilePointer() < games.length()) {
            String name = games.readUTF();
            String genero = games.readUTF();
            String desarrollador = games.readUTF();
            String releasedate = games.readUTF();
            String ruta = games.readUTF();
            String Imagen= games.readUTF();

            //String builder que me genere Todos Mis Juegos y me devuelva todo:
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(name + " | " + genero + " | " + desarrollador + " | " + releasedate + " | ");
            System.out.println("----------------------------------------------------------------------------------------");
        }
        return "StringBuilder";
    }
        
    
    

}
