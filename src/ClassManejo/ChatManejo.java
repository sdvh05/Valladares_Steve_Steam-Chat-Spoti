package ClassManejo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Hp
 */
public class ChatManejo implements Runnable {
    
    public static ArrayList<ChatManejo> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader read;
    private BufferedWriter write;
    private String Username;
    
    public ChatManejo(Socket socket){
        try{
            this.socket=socket;
            this.write=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.read= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.Username=read.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server; "+Username+ "ha entrado al chat");
        }catch(IOException e){
            cerrarTodo(socket, read, write);
        }
    }
    
    @Override
    public void run() {
        String mensajeCliente;
        
        while(socket.isConnected()){
            try{
            mensajeCliente=read.readLine();
            broadcastMessage(mensajeCliente);
            
            } catch(IOException e){
                cerrarTodo(socket, read, write);
                break;
            }
        }
    }

    public void broadcastMessage(String Mensaje) {
        for (ChatManejo clientHandler : clientHandlers) {
            try {
                if (!clientHandler.Username.equals(Username)) {
                    clientHandler.write.write(Mensaje);
                    clientHandler.write.newLine();
                    clientHandler.write.flush();
                }
            } catch (IOException e) {
                cerrarTodo(socket, read, write);
            }
        }
    }
    
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("Servidor: "+Username+ "se ha desconectado");
    }
    
    public void cerrarTodo(Socket socket, BufferedReader read, BufferedWriter write){
        removeClientHandler();
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
    
}
