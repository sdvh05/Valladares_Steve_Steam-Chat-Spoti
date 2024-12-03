/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FuncionamientoGUI;

import ClassManejo.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;



public class Main {
    public Administrador mas;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}

  
   
//    public Inicio(Master mast) {
//        initComponents();
//        this.setTitle("Steve Valladares 22341344");
//        this.setLocationRelativeTo(null);
//        mas=mast;
//    }
//    
//    
//    public Inicio() {
//        initComponents();
//        this.setTitle("Steve Valladares 22341344");
//        this.setLocationRelativeTo(null);
//        mas=new Master();
//    }


