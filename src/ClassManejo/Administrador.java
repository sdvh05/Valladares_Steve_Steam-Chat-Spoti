/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Hp
 */

/*
    -Admin
        - C Usuario
            -Musica.ext
            -Juego.ext

    -Admin
        -C Usuario
            -C Musica
                -musica1.mp3
                -musica2.mp3
            -C Juego
                -juego1.gfc
                -juego2.gfc


*/
public class Administrador{
    
    protected static String Path="AdminProyect";
    public Usuario UserAct;
    public String UserLog;
    private RandomAccessFile users; 
     
    public Administrador() {

        try {
            //Existe Folder ADMIN
            File mf = new File(Path);
            mf.mkdir();
            //2-Instanciar RAFs dentro de Company
            users = new RandomAccessFile(Path+"/Usuarios.p2p", "rw");

        } catch (IOException e) {

        }
    }
     
    //Funcion HeredarPath
     public String getParentPath(){
         return Path;
     }
     
    private boolean ExisteUsername(String Username) throws IOException {
        users.seek(0);
        while (users.getFilePointer() < users.length()) {
            long pos = users.getFilePointer();
            String name = users.readUTF();
            

            if (name == Username) {
                users.seek(pos); //deja el puntero para usar al ReadUtf encuentre el Username (pos: justo antes del Username que devolvio la funcion como true)
                return true; //true: existe un archivo con ese nombre.
            }
        }
        return false;
    }
     
   public boolean CreateUser(String Username) throws IOException{
       if (!ExisteUsername(Username)) {
          users.seek(users.length()); //puntero al final
          users.writeUTF(Username); //escribir el Usuario
 
          Usuario newuser=new Usuario(Username);
           return true;
       }
       return false;
   }
   
   public boolean Login(String Username) throws IOException{
        if (ExisteUsername(Username)) {
          
            return true;
       }
        
       return false;
   }
     
        //Funciones Juego:
        
         
         
}
