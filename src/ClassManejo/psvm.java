package ClassManejo;

import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class psvm {
    public Administrador mas;
    
    public static void main(String[] args) throws IOException {

        Administrador ad=new Administrador();
        //si creo el Folder Administrador
        boolean crear=ad.CreateUser("Ioni");
        String a = ad.getParentPath();
        
        System.out.println(a);
    }
}
