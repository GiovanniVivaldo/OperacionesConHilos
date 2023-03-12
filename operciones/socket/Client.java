import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // Crear socket para conectar con el servidor en el puerto 8888
            Socket socket = new Socket("localhost", 8888);
            System.out.println("Conectado al servidor en el puerto 8888...");

            // Crear canal de entrada y salida
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Leer operación y números del usuario
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Operación (+, -, *, /): ");
            String operation = userInput.readLine();
            System.out.print("Primer número: ");
            double num1 = Double.parseDouble(userInput.readLine());
            System.out.print("Segundo número: ");
            double num2 = Double.parseDouble(userInput.readLine());

            // Enviar operación y números al servidor
            out.println(operation + " " + num1 + " " + num2);

            // Leer respuesta del servidor y mostrarla al usuario
            String result = in.readLine();
            System.out.println("Resultado: " + result);

            // Cerrar canal de entrada y salida y el socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
