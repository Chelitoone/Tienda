package tienda;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import tienda.base.Producto;

public class OperacionesTienda {
    private String decisionTxt;
    private int decision;
    private Tienda tienda = new Tienda();
    private int numProductos = 0;

    public OperacionesTienda() {
    }

    public void menuOpciones() {
        JOptionPane.showMessageDialog(null, "Bienvenido a la Tienda de Autopartes Chelo");
        decisionTxt = JOptionPane.showInputDialog("1: Agregar Producto \n" +
                        "2: Consultar precio de producto por serial\n" +
                        "3: Consultar dinero en activos de la tienda\n");
        decision = Integer.parseInt(decisionTxt);
        menu(decision);
    }
    
    private void menu(int num) {
        switch (num) {
            case 1:
                numProductos += 1;
                String serialString = JOptionPane.showInputDialog("Ingresa el serial del producto\n");
                String descripcion = JOptionPane.showInputDialog("Ingresa la descripción del producto\n");
                String precioStr = JOptionPane.showInputDialog("Ingresa el precio del producto\n");
                double precio = Double.parseDouble(precioStr);
                String cantidadStr = JOptionPane.showInputDialog("Ingresa la cantidad de productos\n");
                int cantidad = Integer.parseInt(cantidadStr);
                
                // Crear una conexión con la base de datos
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tienda", "postgres", "Colombia2024")) {
                    System.out.println("Conexión exitosa");

                    Statement statement = connection.createStatement();
                    boolean seAgrego = tienda.adicionarProducto(serialString, descripcion, precio, cantidad, statement);
                    
                    if (seAgrego) {
                        JOptionPane.showMessageDialog(null, "Producto agregado con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el producto");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage());
                }
                break;
            
            case 2:
                String serialABuscar = JOptionPane.showInputDialog("Ingresa el serial del producto a buscar\n");
                Producto productoBuscado = tienda.buscarProducto(serialABuscar);
                if (productoBuscado != null) {
                    JOptionPane.showMessageDialog(null, "Descripcion: " + productoBuscado.getDescripcion() + "\n"
                                                        + "Precio: " + productoBuscado.getPrecio() + "\n"
                                                        + "Cantidad: " + productoBuscado.getCantidad());
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado");
                }
                break;

            case 3:
                double totalDineroTienda = tienda.consultarTotalDinero();
                JOptionPane.showMessageDialog(null, "El total de dinero en activos de la tienda es: " + totalDineroTienda);
                break;

            default:
                break;
        }
    }
}
