package controllers;

import views_temp.Login;
import views_temp.NewLogin;
import views_temp.SystemViewResponsive;
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
    //Login login_view;
    private NewLogin new_login_view;

    public LoginController(Usuario empleado, UsuariosDAO empleado_conexion, NewLogin new_login_view) {
        this.empleado = empleado;
        this.empleado_conexion = empleado_conexion;
        this.new_login_view = new_login_view;
        this.new_login_view.btnIngresar.addActionListener(this);
    }

//    public LoginController(NewLogin new_login_view) {
//    //        this.login_view = login_view;
    ////        this.login_view.btnIngresar.addActionListener(this);
//        this.new_login_view = new_login_view;
//        this.new_login_view.btnIngresar.addActionListener(this);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == new_login_view.btnIngresar) {
            SystemViewResponsive admin = new SystemViewResponsive();
            admin.setVisible(true);
            this.new_login_view.dispose();
        }

//        String contrasenyaAdmin = "123", contrasenyaUser = "123", usuarioAdmin = "admin", usuarioUser = "user";
//
//        //Obtener los datos de la vista:
//        //        String usuario = String.valueOf(login_view.ComboBoxUsuario.getSelectedItem());
    

////        String contrasenya = String.valueOf(login_view.PasswordField.getPassword());
//        String usuario = new_login_view.txtUsuario.getText();
//        String contrasenya = String.valueOf(new_login_view.PasswordField.getPassword());
//
//        if (e.getSource() == new_login_view.btnIngresar) {
//            if (!contrasenya.isEmpty()) {
//                if (usuario.equals(usuarioAdmin)) {
//                    if (contrasenya.equals(contrasenyaAdmin)) {
//                        SystemViewResponsive  admin = new SystemViewResponsive ();
//                        admin.setVisible(true);
//                        this.new_login_view.dispose();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else if (usuario.equals(usuarioUser)) {
//                    if (contrasenya.equals(contrasenyaUser)) {
//                        SystemViewResponsive  aux = new SystemViewResponsive ();
//                        aux.setVisible(true);
//                        this.new_login_view.dispose();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                //this.login_view.dispose();
//            } else {
//                JOptionPane.showMessageDialog(null, "Campo vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
//            }
//        }

        //Obtener los datos de la vista:
//        String usuario = new_login_view.txtUsuario.getText();
//        String contrasenya = String.valueOf(new_login_view.PasswordField.getPassword());
//        if (e.getSource() == new_login_view.btnIngresar) {
//            if (!usuario.isBlank() || !contrasenya.isBlank()) {
//                empleado = empleado_conexion.consultarEmpleado(usuario, contrasenya);
//                if (empleado.getNombreDeUsuario() != null) {
//                    if (empleado.getRol().equals("Administrador")) {
//                        SystemViewResponsive  admin = new SystemViewResponsive ();
//                        admin.setVisible(true);
//                    } else {
//                        SystemViewResponsive  aux = new SystemViewResponsive ();
//                        aux.setVisible(true);
//                    }
//                    this.new_login_view.dispose();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Usuario o Password Incorrecto.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Campo vacío.");
//            }
//        }
    }
}
