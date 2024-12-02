import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

import tienda.OperacionesTienda;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda";
        String user = "postgres";
        String password = "Colombia2024";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión exitosa");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");

            while (resultSet.next()) {
                // Acceder a los datos de cada fila
                int serial = resultSet.getInt("serial");
                String descripcion = resultSet.getString("descripcion");
                double precio = resultSet.getDouble("precio");
                int cantidad = resultSet.getInt("cantidad");

                // Imprimir los resultados
                System.out.println("Serial: " + serial + ", Descripcion: " + descripcion + ", Precio: " + precio + ", Cantidad: " + cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        OperacionesTienda operacionesTienda = new OperacionesTienda();
        operacionesTienda.menuOpciones();
    }
}

/*
 * SCRIP crear tabla en la base de datos
 * CREATE TABLE productos (
    serial VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(100),
    precio DOUBLE PRECISION,
    cantidad INTEGER
);

 */