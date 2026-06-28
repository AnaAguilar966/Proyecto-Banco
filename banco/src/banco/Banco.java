package banco;

import java.util.Scanner;

public class Banco {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ConsultasCliente clienteDAO = new ConsultasCliente();
        ConsultasCuentaA cuentaDAO  = new ConsultasCuentaA();
        ConsultasCredito creditoDAO = new ConsultasCredito();

        int opcion;

        do {
            System.out.println("\n===== BANCO =====");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Crear cuenta");
            System.out.println("4. Exportar cuentas en TXT");
            System.out.println("5. Salir");
            System.out.print("Elige una opcion: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Ingresa tu numero de cuenta: ");
                    String numeroCuenta = entrada.nextLine();

                    if (cuentaDAO.iniciarSesion(numeroCuenta)) {
                        int idCuenta = cuentaDAO.obtenerIdCuenta(numeroCuenta);
                        menuCuenta(entrada, cuentaDAO, creditoDAO, idCuenta);
                    } else {
                        System.out.println("La cuenta no existe.");
                    }
                    break;

                case 2:
                    System.out.print("Nombre del cliente: ");
                    String nombre = entrada.nextLine();

                    System.out.print("Telefono: ");
                    String telefono = entrada.nextLine();

                    System.out.print("Correo: ");
                    String correo = entrada.nextLine();

                    cliente cliente = new cliente(0, nombre, telefono, correo);
                    clienteDAO.registrarCliente(cliente);
                    break;

                case 3:
                    System.out.print("ID del cliente: ");
                    int idCliente = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Numero de cuenta: ");
                    String numCuenta = entrada.nextLine();

                    System.out.print("Saldo inicial: ");
                    double saldo = entrada.nextDouble();

                    cuentaDAO.crearCuenta(idCliente, numCuenta, saldo);
                    break;

                case 4:
                    cuentaDAO.exportarCuentasTXT();
                    break;

                case 5:
                    System.out.println("Saliendo");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 5);
    }

    public static void menuCuenta(Scanner entrada, ConsultasCuentaA cuenta, ConsultasCredito credito, int idCuenta) {

        int opcion;

        do {
            System.out.println("\n===== MENU DE CUENTA =====");
            System.out.println("1. Depositar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Solicitar credito");
            System.out.println("4. Pagar credito");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Eliminar cuenta");
            System.out.println("7. Salir");
            System.out.print("Elige una opcion: ");
            opcion = entrada.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("Monto a depositar: ");
                    double deposito = entrada.nextDouble();
                    cuenta.depositar(idCuenta, deposito);
                    break;

                case 2:
                    System.out.print("Monto a retirar: ");
                    double retiro = entrada.nextDouble();
                    cuenta.retirar(idCuenta, retiro);
                    break;

                case 3:
                    System.out.print("Monto del credito: ");
                    double monto = entrada.nextDouble();
                    credito.solicitarCredito(idCuenta, monto);
                    break;

                case 4:
                    System.out.print("Monto a pagar: ");
                    double pago = entrada.nextDouble();
                    credito.pagarCredito(idCuenta, pago);
                    break;

                case 5:
                    double saldo = cuenta.consultarSaldo(idCuenta);
                    System.out.println("Saldo actual: " + saldo);
                    break;

                case 6:
                    cuenta.eliminarCuenta(idCuenta);
                    opcion = 7;
                    break;

                case 7:
                    System.out.println("Regresar al menu principal");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 7);
    }
}