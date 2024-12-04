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

//Cuenta Admin.... solo añadir cosas a la biblioteca... parametros Name Pass bool admin true yes false no, y que tenga permisos Especiales

 */
public class Administrador {

    protected static String Path = "AdminProyect";
    public Usuario UserAct;
    public String UserLog;
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
            users.skipBytes(1);

            if (name == Username) {
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

            Usuario newuser = new Usuario(username, pass, admin);
            return true;
        }
        return false;
    }

    public boolean Login(String Username, String Pass, boolean Admin) throws IOException {
        if (verificarLog(Username, Pass)) {

            return true;
        }

        return false;
    }

    private boolean verificarLog(String Username, String Password) throws IOException {
        users.seek(0);
        while (users.getFilePointer() < users.length()) {
            long pos = users.getFilePointer();
            String name = users.readUTF();
            String pass = users.readUTF();
            users.skipBytes(1);

            if (name.equals(Username) && pass.equals(Password)) {
                users.seek(pos);
                return true;
            }
        }
        return false;
    }
//--------------------------------------------------------------------------------------------------------------------------------------

    
  //Agregar a Biblioteca
//---------------------------------------------------------------------------------------------------------------------------------------
    public RandomAccessFile AddLibraryMusic(String titulo, String artista, String album, int duracion, String rutaMusica,String rutaImagen) throws IOException {
        newSong = new RandomAccessFile(UserSteam() + "/" + titulo + ".mp3", "rw");
        newSong.writeUTF(titulo);
        newSong.writeUTF(artista);
        newSong.writeUTF(album);
        newSong.writeInt(duracion);
        newSong.writeUTF(rutaMusica);
        newSong.writeUTF(rutaImagen);
        return newSong;
    }
    
    public RandomAccessFile AddLibraryGame(String name, String genero, String desarrollador, String releaseDate, String rutagame, String rutaImagen)throws IOException{
        newGame=new RandomAccessFile(UserMusic()+ "/"+ name +".gfc", "rw");
        newGame.writeUTF(name);
        newGame.writeUTF(genero);
        newGame.writeUTF(desarrollador);
        newGame.writeUTF(releaseDate);
        newGame.writeUTF(rutagame);
        newGame.writeUTF(rutaImagen);
        return newGame;
        
    }
}
