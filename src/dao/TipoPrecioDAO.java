package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.TipoPrecio;

/**
 * DAO encargado de gestionar el tipo de precios.
 *
 * @author Jakim
 */
public class TipoPrecioDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<TipoPrecio> listarTiposPrecio() {
        List<TipoPrecio> lista = new ArrayList<>();
        String query = "SELECT * FROM tipos_precio";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                TipoPrecio tipo = new TipoPrecio();
                tipo.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setCodigo(rs.getString("codigo"));
                lista.add(tipo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista;
    }
}
