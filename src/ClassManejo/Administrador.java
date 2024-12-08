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
import javax.swing.JOptionPane;

/**
 *
 * @author Hp
 */

/*
    -Admin
        -C Usuario
            -C Musica
                -musica1.mp3
                -musica2.mp3
            -C Juego
                -juego1.gfc
                -juego2.gfc

 */
public class Administrador {

    protected static String Path = "AdminProyect";
    public int usuariosConectados = 0;
    public Usuario UserAct;
    public String UserLog, MusicaUser, GameUser; //Guargar lo que necesito, las carpetas y el User
    public boolean Permisos, open;
    
    private RandomAccessFile users, newSong, newGame;

    public Administrador() {

        try {
            //Existe Folder ADMIN
            File mf = new File(Path);
            mf.mkdir();
            File Us =new File(getParentPath()+"/Usuarios");
            Us.mkdir();
            CrearBibliotecas();
            //2-Instanciar RAFs dentro de Company
            users = new RandomAccessFile(Path + "/ListUsuarios.p2p", "rw");

        } catch (IOException e) {

        }
    }

    //Funcion HeredarPath
    public String getParentPath() {
        return Path;
    }

    //Crear Biblioteca Generales:
    protected String UserMusic() {
        return getParentPath() + "/BibliotecaMusical";
    }

    protected String UserSteam() {
        return getParentPath() + "/BibliotecaSteam";
    }
    
    private void CrearBibliotecas()throws IOException{
            File Bmusic = new File(UserMusic());
            Bmusic.mkdir();
            File Bgame = new File(UserSteam());
            Bgame.mkdir();
    }

    //Login-Signin
//-------------------------------------------------------------------------------------------------------------------------------------------
    private boolean ExisteUsername(String Username) throws IOException {
        users.seek(0);
        while (users.getFilePointer() < users.length()) {
            long pos = users.getFilePointer();
            String name = users.readUTF();
            users.readUTF();
            users.skipBytes(1); //bool

            if (name.equals(Username)) {
                users.seek(pos); //deja el puntero para usar al ReadUtf encuentre el Username (pos: justo antes del Username que devolvio la funcion como true)
                return true; //true: existe un archivo con ese nombre.
            }
        }
        return false;
    }

    public boolean CreateUser(String username, String pass, boolean admin) throws IOException {
        if (!ExisteUsername(username)) {
            users.seek(users.length()); //puntero al final
            users.writeUTF(username); //escribir datos
            users.writeUTF(pass);
            users.writeBoolean(admin);
            
            UserLog=username;
            Permisos=admin;
            if (Permisos) {
                MusicaUser=Path+"/Usuarios/ADMIN."+username+"/Musica";
                GameUser=Path+"/Usuarios/ADMIN."+username+"/Juegos";
            }else {
                MusicaUser=Path+"/Usuarios/USER."+username+"/Musica";
                GameUser=Path+"/Usuarios/USER."+username+"/Juegos"; 
            }

            Usuario newuser = new Usuario(username, pass, admin);
            JOptionPane.showMessageDialog(null, "SE HA REGISTRADO CORRECTAMENTE");
            this.usuariosConectados++;
            return true;
        }
        return false;
    }

    public boolean Login(String Username, String Pass) throws IOException {
        if (verificarLog(Username, Pass)) {
            UserLog=Username;
            Permisos=users.readBoolean();
            
            if (Permisos) {
                MusicaUser=Path+"/Usuarios/ADMIN."+Username+"/Musica";
                GameUser=Path+"/Usuarios/ADMIN."+Username+"/Juegos";
            }else {
                MusicaUser=Path+"/Usuarios/USER."+Username+"/Musica";
                GameUser=Path+"/Usuarios/USER."+Username+"/Juegos"; 
            }
            this.usuariosConectados++;
            return true;
        }

        return false;
    }

    private boolean verificarLog(String Username, String Password) throws IOException {
        users.seek(0);
        while (users.getFilePointer() < users.length()) {
            String name = users.readUTF();
            String pass = users.readUTF();
            long pos = users.getFilePointer();
            users.skipBytes(1);

            if (name.equals(Username) && pass.equals(Password)) {
                users.seek(pos); //puntero listo solo para leer boolean admin
                return true;
            }
        }
        return false;
    }
    
    private void Logout(){
        this.usuariosConectados--;
    }
//--------------------------------------------------------------------------------------------------------------------------------------
 
    
  //Agregar a Biblioteca
//---------------------------------------------------------------------------------------------------------------------------------------
    public RandomAccessFile AddLibraryMusic(String titulo, String artista, String album, int duracion, String rutaMusica,String rutaImagen) throws IOException {
        newSong = new RandomAccessFile(UserMusic() + "/" + titulo + ".mp3", "rw");
        newSong.writeUTF(titulo);
        newSong.writeUTF(artista);
        newSong.writeUTF(album);
        newSong.writeInt(duracion);
        newSong.writeUTF(rutaMusica);
        newSong.writeUTF(rutaImagen);
        return newSong;
    }
    
    public RandomAccessFile AddLibraryGame(String name, String genero, String desarrollador, String releaseDate, String rutagame, String rutaImagen)throws IOException{
        newGame=new RandomAccessFile(UserSteam()+ "/"+ name +".gfc", "rw");
        newGame.writeUTF(name);
        newGame.writeUTF(genero);
        newGame.writeUTF(desarrollador);
        newGame.writeUTF(releaseDate);
        newGame.writeUTF(rutagame);
        newGame.writeUTF(rutaImagen);
        return newGame;  
    }
//---------------------------------------------------------------------------------------------------------------------------------------

    //Server:
    public synchronized void incrementarContadorUsuariosConectados() {
        usuariosConectados++;
    }

    public synchronized void decrementarContadorUsuariosConectados() {
        if (usuariosConectados > 0) {
            usuariosConectados--;
        }
    }

    public synchronized int getUsuariosConectados() {
        return usuariosConectados;
    }
    
    public void openServer(){
        if (this.usuariosConectados==1 && this.open==false) {
            this.open=true;
        }else{
            this.open=false;
        }
    }
   


}
