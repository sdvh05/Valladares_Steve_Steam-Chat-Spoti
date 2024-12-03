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

/**
 *
 * @author Hp
 */
public class Musica {
    private RandomAccessFile Mus;

    
    public Musica(String titulo, String artista,String album, double duracion,String rutaMusica){
    
 
    }
    
    public String PrintList(RandomAccessFile musics)throws IOException{
        musics.seek(0);
        
        while(musics.getFilePointer()<musics.length()){
            String titulo=musics.readUTF();
            String artista=musics.readUTF();
            String album= musics.readUTF();
            double duracion=musics.readDouble();
            musics.readUTF();
            
              //String builder que me genere Todos Mis Juegos y me devuelva todo:
            System.out.println("--------------------------------------------------------");
            System.out.println(titulo+" | "+artista+" | "+album+" | "+duracion+" | "); 
            System.out.println("--------------------------------------------------------");
        }
        
      return "StringBuilder";
    }
    

}
