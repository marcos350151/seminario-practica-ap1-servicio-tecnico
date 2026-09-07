import java.sql.*;

public class ClienteDAO {
    public int crear(String nombre, String telefono, String email) throws SQLException {
        String sql = "INSERT INTO cliente(nombre, telefono, email) VALUES (?, ?, ?)";
        try (Connection cn = Database.connect();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, email);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo obtener el identificador del cliente.");
    }

    public void listar() throws SQLException {
        String sql = "SELECT id_cliente, nombre, telefono, email FROM cliente ORDER BY id_cliente DESC";
        try (Connection cn = Database.connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s%n",
                        rs.getInt("id_cliente"), rs.getString("nombre"),
                        rs.getString("telefono"), rs.getString("email"));
            }
        }
    }
}
