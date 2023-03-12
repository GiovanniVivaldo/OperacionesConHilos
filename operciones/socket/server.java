import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) throws IOException {
        int portNumber = 8888;

        System.out.println("Iniciando servidor en el puerto " + portNumber + "...");

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                Thread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor en el puerto " + portNumber);
            System.exit(1);
        }
    }

    private static class ClientThread extends Thread {
        private Socket clientSocket;

        public ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Cliente: " + inputLine);

                    String[] tokens = inputLine.split("\\s+");

                    if (tokens.length != 3) {
                        out.println("Operacion invalida. Use el formato: <operacion> <numero1> <numero2>");
                    } else {
                        String operacion = tokens[0];
                        double a = Double.parseDouble(tokens[1]);
                        double b = Double.parseDouble(tokens[2]);

                        Thread operacionThread = null;

                        switch (operacion) {
                            case "suma":
                                operacionThread = new AddThread(a, b, out);
                                break;
                            case "resta":
                                operacionThread = new SubThread(a, b);
                                break;
                            case "multiplicacion":
                                operacionThread = new MulThread(a, b);
                                break;
                            case "division":
                                operacionThread = new DivThread(a, b);
                                break;
                            default:
                                out.println("Operacion invalida");
                        }

                        if (operacionThread != null) {
                            operacionThread.start();
                            
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al manejar la conexion con el cliente.");
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar la conexion con el cliente.");
                }
            }
        }
    }

    private static class AddThread extends Thread {
        private double num1, num2;
        private double result;
        private PrintWriter out;

        public AddThread(double num1, double num2, PrintWriter out) {
            this.num1 = num1;
            this.num2 = num2;
            this.out = out;
        }

        public void run() {
            result = num1 + num2;
            // Enviar el resultado al cliente
            out.println(result);
        }
    }

    private static class SubThread extends Thread {
        private double a;
        private double b;

        public SubThread(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            double result = a - b;
            System.out.println("Resta: " + result);
        }
    }

    private static class MulThread extends Thread {
        private double a;
        private double b;

        public MulThread(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            double result = a * b;
            System.out.println("Multiplicacion: " + result);
        }
    }

    private static class DivThread extends Thread {
        private double a;
        private double b;

        public DivThread(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            if (b == 0) {
                System.out.println("Division por cero");
            } else {
                double result = a / b;
                System.out.println("Division: " + result);
            }
        }
    }
}
