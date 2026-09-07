import java.sql.*;

public class OrdenDAO {
    public int registrarEquipo(int idCliente, String tipo, String marca, String modelo, String serie) throws SQLException {
        String sql = "INSERT INTO equipo(id_cliente,tipo,marca,modelo,nro_serie) VALUES (?,?,?,?,?)";
        try (Connection cn = Database.connect();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCliente);
            ps.setString(2, tipo);
            ps.setString(3, marca);
            ps.setString(4, modelo);
            ps.setString(5, serie);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo registrar el equipo.");
    }

    public int crearOrden(int idEquipo, String falla) throws SQLException {
        String sql = "INSERT INTO orden_servicio(id_equipo,falla_informada) VALUES (?,?)";
        try (Connection cn = Database.connect();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idEquipo);
            ps.setString(2, falla);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo crear la orden.");
    }

    public void actualizarEstado(int idOrden, String estado) throws SQLException {
        String sql = "UPDATE orden_servicio SET estado=?, fecha_cierre = CASE WHEN ?='ENTREGADO' THEN CURRENT_TIMESTAMP ELSE fecha_cierre END WHERE id_orden=?";
        try (Connection cn = Database.connect(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setString(2, estado);
            ps.setInt(3, idOrden);
            if (ps.executeUpdate() == 0) throw new SQLException("Orden inexistente.");
        }
    }

    public void listarOrdenes() throws SQLException {
        String sql = "SELECT o.id_orden,c.nombre,e.tipo,e.marca,e.modelo,o.estado,o.fecha_ingreso " +
                     "FROM orden_servicio o JOIN equipo e ON e.id_equipo=o.id_equipo " +
                     "JOIN cliente c ON c.id_cliente=e.id_cliente ORDER BY o.id_orden DESC";
        try (Connection cn = Database.connect(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("Orden %d | %s | %s %s %s | %s | %s%n",
                        rs.getInt("id_orden"), rs.getString("nombre"), rs.getString("tipo"),
                        rs.getString("marca"), rs.getString("modelo"), rs.getString("estado"),
                        rs.getTimestamp("fecha_ingreso"));
            }
        }
    }
}
