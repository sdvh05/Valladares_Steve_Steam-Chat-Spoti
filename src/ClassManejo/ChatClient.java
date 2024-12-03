/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Hp
 */
public class ChatClient {

    private Socket socket;
    private BufferedReader read;
    private BufferedWriter write;
    private String Username;

    public ChatClient(Socket socket, String Username) {
        try {
            this.socket = socket;
            this.write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.Username = Username;
        } catch (IOException e) {
            cerrarTodo(socket, read, write);
        }
    }
    
    public void MandarMensaje(){
        try{
            write.write(Username);
            write.newLine();
            write.flush();
            
            Scanner lea= new Scanner(System.in);
            while(socket.isConnected()){
                String Mensaje=lea.nextLine();
                write.write(Username+ ": "+Mensaje);
                write.newLine();
                write.flush();
            }
        }catch(IOException e){
            cerrarTodo(socket, read, write);
        }
    }
    
    public void EscucharMensaje(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String MensajeGrupo;
                
                while(socket.isConnected()){
                    try{
                         MensajeGrupo=read.readLine();
                         System.out.println(MensajeGrupo);
                    }catch(IOException e){
                        cerrarTodo(socket, read, write);
                    }
                }
            }
        }).start();
    }
    
    public void cerrarTodo(Socket socket, BufferedReader read, BufferedWriter write){
        try{
            if (read != null) {
                read.close();
            }
            if (write !=null) {
                write.close();
            }
            if (socket!=null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        Scanner lea= new Scanner(System.in);
        System.out.println("Enter username");
        String username=lea.next();
        Socket socket= new Socket("localhost",1234);
        ChatClient cliente=new ChatClient(socket, username);
        cliente.EscucharMensaje();
        cliente.MandarMensaje();
        
    }
}
