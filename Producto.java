package tienda.base;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Producto {
    String serial;
    String descripcion;
    double precio;
    int cantidad;

    public Producto(String serial, String descripcion, double precio, int cantidad) {
        this.serial = serial;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getSerial() {
        return serial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean insertarProducto(Statement statement) {
        String sql = "INSERT INTO productos (serial, descripcion, precio, cantidad) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = statement.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, this.serial);
            preparedStatement.setString(2, this.descripcion);
            preparedStatement.setDouble(3, this.precio);
            preparedStatement.setInt(4, this.cantidad);
            
            // Ejecutamos la inserción
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                return true;  // La inserción fue exitosa
            } else {
                return false; // No se insertó ninguna fila
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Hubo un error al insertar
        }
    }
}
