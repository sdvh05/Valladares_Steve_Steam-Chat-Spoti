/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Hp
 */
public abstract class Molde {
   
    protected String Nombre;
    protected String RutaFile;

    public Molde() {

    }
    

    
    public abstract String getData();
    
    public abstract void Save(RandomAccessFile file)throws IOException;

}
