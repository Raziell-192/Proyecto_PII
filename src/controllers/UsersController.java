package controllers;

import Views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.User;
import models.UsersConnection;
import static models.UsersConnection.rolUsuario;

/**
 *
 * @author jakim
 */
public class UsersController implements ActionListener {

    private User usuario;
    private UsersConnection usuarioConexion;
    private SystemViewResponsive vista;
    String rol = rolUsuario;
    private int idUsuarioEnEdicion = -1;

    public UsersController(User usuario, UsersConnection usuarioConexion, SystemViewResponsive vista) {
        this.usuario = usuario;
        this.usuarioConexion = usuarioConexion;
        this.vista = vista;

        // Agregar listeners
        this.vista.btnRegistrarUsuario.addActionListener(this);
        this.vista.btnBuscarUsuario.addActionListener(this); // Buscar
        this.vista.btnEditarUsuario.addActionListener(this); // Editar
        this.vista.btnEliminarUsuario.addActionListener(this); // Eliminar
        this.vista.btnMostrarUsuario.addActionListener(this);
        this.vista.btnActualizarUsuario.addActionListener(this);
        this.vista.btnActualizarUsuario.setVisible(false);

        // Cargar datos iniciales
        cargarUsuariosEnTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarUsuario) {
            registrarUsuario();
        } else if (e.getSource() == vista.btnEditarUsuario) {
            cargarUsuarioParaEdicion();
        } else if (e.getSource() == vista.btnActualizarUsuario) {
            actualizarUsuario();
        } else if (e.getSource() == vista.btnEliminarUsuario) {
            eliminarUsuario();
        } else if (e.getSource() == vista.btnBuscarUsuario) {
            buscarUsuarios();
        } else if (e.getSource() == vista.btnMostrarUsuario) {
            cargarUsuariosEnTabla();
        }
    }

    private void registrarUsuario() {
        if (validarCampos()) {
            usuario.setNombre(vista.txtNombreUsuario.getText().trim());
            usuario.setApellido(vista.txtApellidoUsuario.getText().trim());
            usuario.setNombreDeUsuario(vista.txtUsernameUsuario.getText().trim());
            usuario.setEmail(vista.txtEmailUsuario.getText().trim());
            usuario.setContrasenya(String.valueOf(vista.passwordUsuario.getPassword()));
            usuario.setRol(vista.cmbRolUsuario.getSelectedItem().toString());
            if (usuarioConexion.registrarUsuarioQuery(usuario)) {
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
                limpiarCampos();
                cargarUsuariosEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar usuario.");
            }
        }
    }

    private void buscarUsuarios() {
        String textoBusqueda = JOptionPane.showInputDialog("Ingrese ID, nombre o apellido a buscar:");
        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            List<User> usuarios = usuarioConexion.buscarUsuarios(textoBusqueda.trim());
            cargarUsuariosEnTabla(usuarios);
        }
    }

    private void cargarUsuarioParaEdicion() {

        int fila = vista.tblUsuarios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario.");
            return;
        }

        idUsuarioEnEdicion = (int) vista.tblUsuarios.getValueAt(fila, 0);
        User u = obtenerUsuarioPorId(idUsuarioEnEdicion);

        vista.txtNombreUsuario.setText(u.getNombre());
        vista.txtApellidoUsuario.setText(u.getApellido());
        vista.txtUsernameUsuario.setText(u.getNombreDeUsuario());
        vista.txtEmailUsuario.setText(u.getEmail());
        vista.passwordUsuario.setText(u.getContrasenya());
        vista.cmbRolUsuario.setSelectedItem(u.getRol());

        // Cambiar botones
        vista.btnRegistrarUsuario.setVisible(false);
        vista.btnActualizarUsuario.setVisible(true);
        
        JOptionPane.showMessageDialog(null, "Datos cargados para editar. Modifique y haga clic en «Actualizar» para guardar cambios.");
    }

    private void actualizarUsuario() {

        if (!validarCampos()) {
            return;
        }

        usuario.setId(idUsuarioEnEdicion);
        usuario.setNombre(vista.txtNombreUsuario.getText().trim());
        usuario.setApellido(vista.txtApellidoUsuario.getText().trim());
        usuario.setNombreDeUsuario(vista.txtUsernameUsuario.getText().trim());
        usuario.setEmail(vista.txtEmailUsuario.getText().trim());
        usuario.setContrasenya(String.valueOf(vista.passwordUsuario.getPassword()));
        usuario.setRol(vista.cmbRolUsuario.getSelectedItem().toString());

        if (usuarioConexion.actualizarUsuarioQuery(usuario)) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");

            limpiarCampos();
            cargarUsuariosEnTabla();

            // Restaurar botones
            vista.btnRegistrarUsuario.setVisible(true);
            vista.btnActualizarUsuario.setVisible(false);

            idUsuarioEnEdicion = -1;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
        }
    }

    private void editarUsuario() {
        int filaSeleccionada = vista.tblUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla.");
            return;
        }

        int idUsuario = (int) vista.tblUsuarios.getValueAt(filaSeleccionada, 0);
        User usuarioEditar = obtenerUsuarioPorId(idUsuario);
        usuarioConexion.eliminarUsuario(idUsuario);
        if (usuarioEditar.getId() != 0) {
            // Llenar los campos con los datos del usuario
            vista.txtNombreUsuario.setText(usuarioEditar.getNombre());
            vista.txtApellidoUsuario.setText(usuarioEditar.getApellido());
            vista.txtUsernameUsuario.setText(usuarioEditar.getNombreDeUsuario());
            vista.txtEmailUsuario.setText(usuarioEditar.getEmail());
            vista.cmbRolUsuario.setSelectedItem(usuarioEditar.getRol());
            vista.passwordUsuario.setText(usuarioEditar.getContrasenya());

            JOptionPane.showMessageDialog(null, "Datos cargados para editar. Modifique y haga clic en «Registrar» para actualizar.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo cargar los datos del usuario.");
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = vista.tblUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla.");
            return;
        }

        int idUsuario = (int) vista.tblUsuarios.getValueAt(filaSeleccionada, 0);
        String nombreUsuario = (String) vista.tblUsuarios.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de eliminar al usuario: " + nombreUsuario + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioConexion.eliminarUsuario(idUsuario)) {
                JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
                cargarUsuariosEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario.");
            }
        }
    }

    private void cargarUsuariosEnTabla() {
        List<User> usuarios = usuarioConexion.obtenerTodosLosUsuarios();
        cargarUsuariosEnTabla(usuarios);
    }

    private void cargarUsuariosEnTabla(List<User> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) vista.tblUsuarios.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (User emp : usuarios) {
            Object[] fila = {
                emp.getId(),
                emp.getNombre(),
                emp.getApellido(),
                emp.getNombreDeUsuario(),
                emp.getEmail(),
                emp.getRol()
            };
            modelo.addRow(fila);
        }
    }

    private boolean validarCampos() {
        if (vista.txtNombreUsuario.getText().trim().isEmpty()
                || vista.txtApellidoUsuario.getText().trim().isEmpty()
                || vista.txtUsernameUsuario.getText().trim().isEmpty()
                || vista.txtEmailUsuario.getText().trim().isEmpty()
                || vista.cmbRolUsuario.getSelectedItem().toString().isEmpty()
                || String.valueOf(vista.passwordUsuario.getPassword()).isEmpty()) {

            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }

    public User obtenerUsuarioPorId(int id) {
        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
        User usuario = new User();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Conexion conexion = new Conexion();
            con = conexion.conectar();

            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellido(rs.getString("apellidos"));
                usuario.setNombreDeUsuario(rs.getString("username"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                usuario.setContrasenya(rs.getString("password"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario: " + e.toString());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    private void limpiarCampos() {
        vista.txtNombreUsuario.setText("");
        vista.txtApellidoUsuario.setText("");
        vista.txtUsernameUsuario.setText("");
        vista.txtEmailUsuario.setText("");
        vista.passwordUsuario.setText("");
        vista.cmbRolUsuario.setSelectedIndex(0);
    }
}
