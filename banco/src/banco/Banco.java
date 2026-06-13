package banco;

import java.util.Scanner;

public class Banco {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        BancoDAO dao = new BancoDAO();

        int opcion;

        do {
            System.out.println("\n===== BANCO =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear cuenta");
            System.out.println("4. Exportar cuentas a TXT");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Ingresa tu número de cuenta: ");
                    String numeroCuenta = entrada.nextLine();

                    if (dao.iniciarSesion(numeroCuenta)) {
                        int idCuenta = dao.obtenerIdCuenta(numeroCuenta);
                        menuCuenta(entrada, dao, idCuenta);
                    } else {
                        System.out.println("La cuenta no existe.");
                    }
                    break;

                case 2:
                    System.out.print("Nombre del cliente: ");
                    String nombre = entrada.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = entrada.nextLine();

                    System.out.print("Correo: ");
                    String correo = entrada.nextLine();

                    cliente cliente = new cliente(0, nombre, telefono, correo);
                    dao.registrarCliente(cliente);
                    break;

                case 3:
                    System.out.print("ID del cliente: ");
                    int idCliente = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Número de cuenta: ");
                    String numCuenta = entrada.nextLine();

                    System.out.print("Saldo inicial: ");
                    double saldo = entrada.nextDouble();

                    dao.crearCuenta(idCliente, numCuenta, saldo);
                    break;

                case 4:
                    dao.exportarCuentasTXT();
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }

    public static void menuCuenta(Scanner entrada, BancoDAO dao, int idCuenta) {

        int opcion;

        do {
            System.out.println("\n===== MENÚ DE CUENTA =====");
            System.out.println("1. Depositar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Solicitar crédito");
            System.out.println("4. Pagar crédito");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Eliminar cuenta");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("Monto a depositar: ");
                    double deposito = entrada.nextDouble();
                    dao.depositar(idCuenta, deposito);
                    break;

                case 2:
                    System.out.print("Monto a retirar: ");
                    double retiro = entrada.nextDouble();
                    dao.retirar(idCuenta, retiro);
                    break;

                case 3:
                    System.out.print("Monto del crédito: ");
                    double credito = entrada.nextDouble();
                    dao.solicitarCredito(idCuenta, credito);
                    break;

                case 4:
                    System.out.print("Monto a pagar: ");
                    double pago = entrada.nextDouble();
                    dao.pagarCredito(idCuenta, pago);
                    break;

                case 5:
                    double saldo = dao.consultarSaldo(idCuenta);
                    System.out.println("Saldo actual: $" + saldo);
                    break;

                case 6:
                    dao.eliminarCuenta(idCuenta);
                    opcion = 7;
                    break;

                case 7:
                    System.out.println("Regresando al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 7);
    }
}