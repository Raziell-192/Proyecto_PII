package controllers;

import Views.Login;
import Views.NewLogin;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jakim
 */
public class LoginController implements ActionListener {

    //Login login_view;
    NewLogin new_login_view;

    public LoginController(NewLogin new_login_view) {
//        this.login_view = login_view;
//        this.login_view.btnIngresar.addActionListener(this);
        this.new_login_view = new_login_view;
        this.new_login_view.btnIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String contrasenyaAdmin = "admin", contrasenyaUser = "123", usuarioAdmin = "Administrador", usuarioUser = "Usuario";

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
    }
}
