/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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


    

    public void AddMeFile(String Biblioteca, String namefile) throws IOException {
        Path sourcePath = Paths.get(Biblioteca);
        Path destinationPath = Paths.get(UserMusic());
        
        try {
        // Copiar el archivo a la nueva ubicación (duplicarlo)
            Files.copy(sourcePath, destinationPath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Se ha Añadido la Musica a Tu Biblioteca");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
    
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            
            if (Files.exists(path)) {
                Files.delete(path);
                JOptionPane.showMessageDialog(null, "Archivo eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no existe.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el archivo: " + e.getMessage());
        }
    }

    
    
    //Funciones Chat
    
}

