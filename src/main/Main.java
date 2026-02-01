package main;

import dao.UsuariosDAO;
import views.DialogCrearSuperAdmin;
import views.NewLogin;
//import views.DialogCrearSuperAdmin;

/**
 * Clase principal
 * <p>
 * Main decide qué ventana abrir:
 * <ul>
 * <li>Si NO hay usuarios → abrir JDialog para crear SuperAdmin</li>
 * <li>Si SÍ hay usuarios → abrir Login</li>
 * </ul>
 * </p>
 *
 * @author Jakim
 */
public class Main {

    public static void main(String[] args) {

        configurarLookAndFeel();

        java.awt.EventQueue.invokeLater(() -> {

            UsuariosDAO usuarioDAO = new UsuariosDAO();

            if (!usuarioDAO.existenUsuarios()) {
                // Primer arranque: crear SUPERADMIN
                DialogCrearSuperAdmin dialog = new DialogCrearSuperAdmin(null, true);
                dialog.setVisible(true);
            } else {
                // Arranque normal
                new NewLogin().setVisible(true);
            }
        });
    }

    private static void configurarLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(
                            info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
