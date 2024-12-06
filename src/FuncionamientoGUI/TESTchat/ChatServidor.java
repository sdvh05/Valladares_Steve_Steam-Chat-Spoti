package FuncionamientoGUI.TESTchat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServidor {
    private static final int PUERTO = 12345;
    private static final Set<ObjectOutputStream> CLIENTES = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Iniciando servidor...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto " + PUERTO);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + clienteSocket.getInetAddress());
                new Thread(new ManejadorCliente(clienteSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ManejadorCliente implements Runnable {
        private final Socket socket;
        private ObjectOutputStream salida;
        private ObjectInputStream entrada;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                salida = new ObjectOutputStream(socket.getOutputStream());
                entrada = new ObjectInputStream(socket.getInputStream());
                CLIENTES.add(salida);

                while (true) {
                    Object mensaje = entrada.readObject();
                    System.out.println("Mensaje recibido: " + mensaje);
                    retransmitirMensaje(mensaje);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Cliente desconectado: " + socket.getInetAddress());
            } finally {
                CLIENTES.remove(salida);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void retransmitirMensaje(Object mensaje) {
            synchronized (CLIENTES) {
                for (ObjectOutputStream cliente : CLIENTES) {
                    try {
                        cliente.writeObject(mensaje);
                        cliente.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
