package FuncionamientoGUI.TESTchat.try3;

import java.io.*;
import java.net.*;

public class ClienteChat {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    public ClienteChat(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void escucharMensajes(MensajeRecibidoListener listener) {
        new Thread(() -> {
            try {
                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    listener.onMensajeRecibido(mensaje);
                }
            } catch (IOException e) {
                System.err.println("Error al leer mensajes: " + e.getMessage());
            }
        }).start();
    }

    public void enviarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            salida.println(mensaje);
        }
    }

    public interface MensajeRecibidoListener {
        void onMensajeRecibido(String mensaje);
    }
}
