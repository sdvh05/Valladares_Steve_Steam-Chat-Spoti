/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassManejo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class ChatServer {
    private ServerSocket serverSocket;

    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket=serverSocket;
    }
    
    public void startServer(){
        
        try{
         
            while(!serverSocket.isClosed()){
                
               Socket socket= serverSocket.accept();
                System.out.println("Se ha Concectado un Cliente");
                ChatManejo clientHandler= new ChatManejo(socket);
                
                Thread thread = new Thread(clientHandler);
                thread.start();
                
            }
                    
                    
        }catch(IOException e){
            
        }
         
    }
    
    public void closeServerSocket(){
        try{
            if (serverSocket !=null) {
                serverSocket.close();
            }
        }catch(IOException e){
            
        }
    }

    /* psvm tioe
    ServerSocket serverSocket = new ServerSocket(1234);
        //1234 numero especifico del servidor donde se van a conectar los usuarios
    Server server = new Server(serverSocket);
    server.startServer();
    
     */
    public static void main(String[] args) throws IOException {
            ServerSocket serverSocket = new ServerSocket(1234);
        //1234 numero especifico del servidor donde se van a conectar los usuarios
            ChatServer server = new ChatServer(serverSocket);
            server.startServer();
    }
   
}
    

