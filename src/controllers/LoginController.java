package controllers;

import Views.Login;
import Views.NewLogin;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.User;
import models.UsersConnection;

/**
 *
 * @author jakim
 */
public class LoginController implements ActionListener {

    //Encapsular variables:
    private User empleado;
    private UsersConnection empleado_conexion;
    //Login login_view;
    private NewLogin new_login_view;

    public LoginController(User empleado, UsersConnection empleado_conexion, NewLogin new_login_view) {
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
        String contrasenyaAdmin = "123", contrasenyaUser = "123", usuarioAdmin = "admin", usuarioUser = "user";

        //Obtener los datos de la vista:
        //        String usuario = String.valueOf(login_view.ComboBoxUsuario.getSelectedItem());
//        String contrasenya = String.valueOf(login_view.PasswordField.getPassword());
        String usuario = new_login_view.txtUsuario.getText();
        String contrasenya = String.valueOf(new_login_view.PasswordField.getPassword());

        if (e.getSource() == new_login_view.btnIngresar) {
            if (!contrasenya.isEmpty()) {
                if (usuario.equals(usuarioAdmin)) {
                    if (contrasenya.equals(contrasenyaAdmin)) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                        this.new_login_view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (usuario.equals(usuarioUser)) {
                    if (contrasenya.equals(contrasenyaUser)) {
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                        this.new_login_view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o password incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //this.login_view.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Campo vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        //Obtener los datos de la vista:
//        String usuario = new_login_view.txtUsuario.getText();
//        String contrasenya = String.valueOf(new_login_view.PasswordField.getPassword());
//        if (e.getSource() == new_login_view.btnIngresar) {
//            if (!usuario.isBlank() || !contrasenya.isBlank()) {
//                empleado = empleado_conexion.consultarEmpleado(usuario, contrasenya);
//                if (empleado.getNombreDeUsuario() != null) {
//                    if (empleado.getRol().equals("Administrador")) {
//                        SystemView admin = new SystemView();
//                        admin.setVisible(true);
//                    } else {
//                        SystemView aux = new SystemView();
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
