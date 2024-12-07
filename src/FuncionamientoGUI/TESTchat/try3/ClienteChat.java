package FuncionamientoGUI.TESTchat.try3;
import java.io.*;
import java.net.*;

public class ClienteChat {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    public ClienteChat(String servidor, int puerto) throws IOException {
        socket = new Socket(servidor, puerto);
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public void escucharMensajes(ListenerMensaje listener) {
        new Thread(() -> {
            String mensaje;
            try {
                while ((mensaje = entrada.readLine()) != null) {
                    listener.mensajeRecibido(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void cerrar() throws IOException {
        socket.close();
    }

    public interface ListenerMensaje {
        void mensajeRecibido(String mensaje);
    }
}
