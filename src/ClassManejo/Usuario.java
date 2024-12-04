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
    protected String Pass;
    protected boolean admin;
    
    
    
    public Usuario(String Username, String Password, boolean Admin){
        try {
            this.Name=Username;
            this.Pass=Password;
            this.admin=Admin;
            createUserFolders();
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        @Override
    public String getParentPath() {
        return super.getParentPath()+"/Usuarios"+getType()+Name; //Para Herencia y Crear
    }
   
    private String getType(){
        if (admin) {
            return "/ADMIN.";
        }
        return "/USER.";
    }

    
     private String UserFolder() {
        return this.getParentPath();
    }
     
    @Override
     protected String UserMusic(){
         return this.getParentPath()+"/Musica";
     }
     
    @Override
     protected String UserSteam(){
         return this.getParentPath()+"/Juegos";
     }

    private void createUserFolders() throws IOException {
        //Crear folder user
        File edir = new File(UserFolder());
        edir.mkdir();
        File msc = new File(UserMusic());
        msc.mkdir();
        File stm = new File(UserSteam());
        stm.mkdir();

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
       return "StringBuilder"; //ocupo q me lea la carpeta y retorne todos los archivos
    }
    
    public void AddGame(String name, String genero, String desarrollador, String releaseDate, String rutagame) throws IOException {

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
    
    public void AddMusic(String titulo, String artista,String album, int duracion,String rutaMusica) throws IOException{

    }
    
    
    //Funciones Chat
    
}

