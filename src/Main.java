import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final ClienteDAO CLIENTES = new ClienteDAO();
    private static final OrdenDAO ORDENES = new OrdenDAO();

    public static void main(String[] args) {
        System.out.println("Sistema de Gestión - Servicio Técnico (prototipo AP1)");
        while (true) {
            System.out.println("\n1) Alta de cliente  2) Listar clientes  3) Registrar equipo y orden  4) Listar órdenes  5) Actualizar estado  0) Salir");
            System.out.print("> ");
            String op = IN.nextLine().trim();
            try {
                switch (op) {
                    case "1" -> altaCliente();
                    case "2" -> CLIENTES.listar();
                    case "3" -> altaOrden();
                    case "4" -> ORDENES.listarOrdenes();
                    case "5" -> cambiarEstado();
                    case "0" -> { return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
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
        int idEquipo = ORDENES.registrarEquipo(idCliente, tipo, marca, modelo, serie);
        int idOrden = ORDENES.crearOrden(idEquipo, falla);
        System.out.println("Orden creada con ID " + idOrden);
    }

    private static void cambiarEstado() throws SQLException {
        System.out.print("ID orden: "); int idOrden = Integer.parseInt(IN.nextLine());
        System.out.print("Estado [RECIBIDO/EN_DIAGNOSTICO/PRESUPUESTADO/EN_REPARACION/LISTO/ENTREGADO/CANCELADO]: ");
        String estado = IN.nextLine().trim().toUpperCase();
        ORDENES.actualizarEstado(idOrden, estado);
        System.out.println("Estado actualizado.");
    }
}
