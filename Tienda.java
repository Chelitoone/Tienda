package tienda;

import java.util.ArrayList;
import tienda.base.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tienda {
    String nombre;
    ArrayList<Producto> productos;

    public Tienda() {
        this.productos = new ArrayList<>();
    }

    public Producto buscarProducto(String serial) {
        for (Producto producto : this.productos) {
            if (producto.getSerial().equals(serial)) {
                return producto;
            }
        }
        return null;
    }

    public boolean adicionarProducto(String serial, String descripcion, double precio, int cantidad, Statement statement) {
        Producto productoExistente = this.buscarProducto(serial);
        
        // Verificamos si el producto ya existe
        if (productoExistente == null) {
            Producto nuevoProducto = new Producto(serial, descripcion, precio, cantidad);
            // Insertamos el producto en la base de datos
            boolean exito = nuevoProducto.insertarProducto(statement);
            if (exito) {
                // Si la inserción fue exitosa, agregamos el producto a la lista en memoria (opcional)
                this.productos.add(nuevoProducto);
            }
            return exito;
        } else {
            return false;
        }
    }

    public double consultarTotalDinero() {
        double total = 0;
        
        // Obtener los productos desde la base de datos
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tienda", "postgres", "Colombia2024")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productos");

            while (resultSet.next()) {
                double precio = resultSet.getDouble("precio");
                int cantidad = resultSet.getInt("cantidad");
                total += precio * cantidad;  // Sumamos el valor total de los productos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return total;
    }
}
