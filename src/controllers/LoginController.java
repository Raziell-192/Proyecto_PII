package controllers;

import views.NewLogin;
import views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Usuario;
import dao.UsuariosDAO;

/**
 *
 * @author jakim
 */
public class LoginController implements ActionListener {

    //Encapsular variables:
    private Usuario empleado;
    private UsuariosDAO empleado_conexion;
    private NewLogin new_login_view;

    public LoginController(Usuario empleado, UsuariosDAO empleado_conexion, NewLogin new_login_view) {
        this.empleado = empleado;
        this.empleado_conexion = empleado_conexion;
        this.new_login_view = new_login_view;
        this.new_login_view.btnIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == new_login_view.btnIngresar) {

            String username = new_login_view.txtUsuario.getText().trim();
            String password = String.valueOf(new_login_view.PasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campos obligatorios");
                return;
            }

            Usuario usuario = empleado_conexion.consultarUsuario(username, password);

            if (usuario.getNombreDeUsuario() != null) {
                SystemViewResponsive vista = new SystemViewResponsive(usuario);
                vista.setVisible(true);
                new_login_view.dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Usuario o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
