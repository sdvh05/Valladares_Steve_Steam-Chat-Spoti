package ClassManejo;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Hp
 */
public abstract class GM {

    public GM() {
        
    }
    public abstract  String PrintList(RandomAccessFile raf)throws IOException;
}
