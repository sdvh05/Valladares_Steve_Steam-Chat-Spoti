/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
  /*

        Formato users.emp
        int code
        String Name
        archivo musica.ext (.wav)
        archivo juegos.ext
        
     */

public class Usuario extends Administrador {
    
    public RandomAccessFile musics, games;
    protected String Name;
    
    private static final String ImageIcons = "Juegos/ImageIcons";
    
    
    public Usuario(String Username){
        try {
            this.Name=Username;
            createUserFolders();
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        @Override
    public String getParentPath() {
        return super.getParentPath()+"/Us."+Name; //solo para Herencia
    }
   
    

    
     private String UserFolder() {
        return super.getParentPath()+"/Us."+Name;
    }

    private void createUserFolders() throws IOException {
        //Crear folder user
        File edir = new File(UserFolder());
        edir.mkdir();
        
        //crear los archivos de Musica y Game
        musics= MusicFile(Name);
        games=GameFile(Name);
   
    }
    
    private RandomAccessFile MusicFile(String name) throws IOException {
        String dirPadre = UserFolder();
        String Path = dirPadre + "/Musica"+ ".ext";
        return new RandomAccessFile(Path, "rw");
    }
    
        private RandomAccessFile GameFile(String name) throws IOException {
        String dirPadre = UserFolder();
        String Path = dirPadre + "/Juegos" +".ext";
        return new RandomAccessFile(Path, "rw");
    }


    
    
    //Funciones Juego
    public String getGames() throws IOException{ //PrintList
       games.seek(0);
       
       while(games.getFilePointer()<games.length()){
            String name= games.readUTF();
            String genero=games.readUTF();
            String desarrollador=games.readUTF();
            String releasedate=games.readUTF();
            String ruta=games.readUTF();
         

            //String builder que me genere Todos Mis Juegos y me devuelva todo:
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println(name+" | "+genero+" | "+desarrollador+" | "+releasedate+" | ");
            System.out.println("----------------------------------------------------------------------------------------");
       }
       return "StringBuilder";
    }
    
    public void AddGame(String name, String genero, String desarrollador, String releaseDate, String rutagame) throws IOException {
            games.seek(games.length());
            
            games.writeUTF(name);
            games.writeUTF(genero);
            games.writeUTF(desarrollador);
            games.writeUTF(releaseDate);
            games.writeUTF(rutagame);
  
            //newJuego
    }


    
    
    //Funciones Music
    public String getMusic()throws IOException{
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
    
    public void AddMusic(String titulo, String artista,String album, double duracion,String rutaMusica) throws IOException{
        musics.seek(musics.length());
        musics.writeUTF(titulo);
        musics.writeUTF(artista);
        musics.writeDouble(duracion);
        musics.writeUTF(rutaMusica);
    }
    
    
    //Funciones Chat
    
}

