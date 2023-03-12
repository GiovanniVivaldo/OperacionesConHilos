import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la operacion (suma/resta/multiplicacion/division): ");
        String operacion = scanner.nextLine();

        System.out.print("Ingrese el primer numero: ");
        double a = scanner.nextDouble();

        System.out.print("Ingrese el segundo numero: ");
        double b = scanner.nextDouble();

        Thread operacionThread = null;

        switch (operacion) {
            case "suma":
                operacionThread = new AddThread(a, b);
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
                System.out.println("Operacion invalida");
                System.exit(0);
        }

        operacionThread.start();
    }

    private static class AddThread extends Thread {
        private double a;
        private double b;

        public AddThread(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            double result = a + b;
            System.out.println("Suma: " + result);
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
            double result = a / b;
            System.out.println("Division: " + result);
        }
    }
}
