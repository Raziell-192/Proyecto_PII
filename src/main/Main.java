package main;

import Views.Login;
import Views.NewLogin;
import javax.swing.UIManager;

/**
 *
 * @author jakim
 */
public class Main {

    public static void main(String[] args) {
//        Login login = new Login();
//        login.setVisible(true);

//        NewLogin login = new NewLogin();
//        login.setVisible(true);
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            NewLogin login = new NewLogin();
            login.setVisible(true);
        });

    }
}
