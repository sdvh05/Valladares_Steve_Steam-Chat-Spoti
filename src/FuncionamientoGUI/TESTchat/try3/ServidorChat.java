package FuncionamientoGUI.TESTchat.try3;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServidorChat {
    private static final int PUERTO = 12345;
    private static CopyOnWriteArrayList<PrintWriter> clientes = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            while (true) {
                try {
                    Socket socket = servidor.accept();
                    System.out.println("Cliente conectado: " + socket.getInetAddress());
                    new Thread(new ManejadorCliente(socket)).start();
                } catch (IOException e) {
                    System.err.println("Error al aceptar cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static class ManejadorCliente implements Runnable {
        private Socket socket;
        private PrintWriter salida;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                salida = new PrintWriter(socket.getOutputStream(), true);
                clientes.add(salida);

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                    // Enviar el mensaje a todos los clientes
                    for (PrintWriter cliente : clientes) {
                        cliente.println(mensaje);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error en la comunicación con un cliente: " + e.getMessage());
            } finally {
                if (salida != null) {
                    clientes.remove(salida);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
                System.out.println("Cliente desconectado.");
            }
        }
    }
}
