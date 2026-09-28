import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final ClienteDAO CLIENTES = new ClienteDAO();
    private static final OrdenDAO ORDENES = new OrdenDAO();
    private static final ProcesoDAO PROCESO = new ProcesoDAO();

    public static void main(String[] args) {
        System.out.println("Sistema de Gestión - Servicio Técnico (prototipo AP2)");
        while (true) {
            System.out.println("\n1) Alta cliente  2) Listar clientes  3) Ingresar equipo y orden  4) Listar órdenes");
            System.out.println("5) Diagnosticar  6) Presupuestar  7) Decidir presupuesto  8) Finalizar reparación");
            System.out.println("9) Usar repuesto  10) Entregar  11) Historial del equipo  0) Salir");
            System.out.print("> ");
            String op = IN.nextLine().trim();
            try {
                switch (op) {
                    case "1" -> altaCliente();
                    case "2" -> CLIENTES.listar();
                    case "3" -> altaOrden();
                    case "4" -> ORDENES.listarOrdenes();
                    case "5" -> { PROCESO.diagnosticar(numero("ID orden"), numero("ID técnico"), linea("Diagnóstico")); ok(); }
                    case "6" -> { PROCESO.presupuestar(numero("ID orden"), new BigDecimal(linea("Monto")), linea("Detalle")); ok(); }
                    case "7" -> { PROCESO.decidirPresupuesto(numero("ID orden"), decision()); ok(); }
                    case "8" -> { PROCESO.finalizarReparacion(numero("ID orden"), numero("ID técnico"), linea("Detalle de reparación")); ok(); }
                    case "9" -> { PROCESO.usarRepuesto(numero("ID orden"), numero("ID repuesto"), numero("Cantidad")); ok(); }
                    case "10" -> { PROCESO.entregar(numero("ID orden")); ok(); }
                    case "11" -> PROCESO.historial(numero("ID equipo"));
                    case "0" -> { return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println("Dato o transición inválida: " + e.getMessage());
            }
        }
    }

    private static void altaCliente() throws SQLException {
        System.out.print("Nombre: "); String nombre = IN.nextLine();
        System.out.print("Teléfono: "); String tel = IN.nextLine();
        System.out.print("Email: "); String email = IN.nextLine();
        int id = CLIENTES.crear(nombre, tel, email);
        System.out.println("Cliente creado con ID " + id);
    }

    private static void altaOrden() throws SQLException {
        System.out.print("ID cliente: "); int idCliente = Integer.parseInt(IN.nextLine());
        System.out.print("Tipo de equipo: "); String tipo = IN.nextLine();
        System.out.print("Marca: "); String marca = IN.nextLine();
        System.out.print("Modelo: "); String modelo = IN.nextLine();
        System.out.print("Nro. de serie: "); String serie = IN.nextLine();
        System.out.print("Falla informada: "); String falla = IN.nextLine();
        String accesorios = linea("Accesorios recibidos");
        int idOrden = ORDENES.ingresar(idCliente, tipo, marca, modelo, serie, falla, accesorios);
        System.out.println("Orden creada con ID " + idOrden);
    }

    private static String linea(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return IN.nextLine().trim();
    }
    private static int numero(String etiqueta) {
        return Integer.parseInt(linea(etiqueta));
    }
    private static boolean decision() {
        String respuesta = linea("¿Aceptado? (S/N)");
        if (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"))
            throw new IllegalArgumentException("Responder S o N.");
        return respuesta.equalsIgnoreCase("S");
    }
    private static void ok() { System.out.println("Operación registrada."); }
}
