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
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                new Thread(new ManejadorCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            } finally {
                if (salida != null) {
                    clientes.remove(salida);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Cliente desconectado.");
            }
        }
    }
}
