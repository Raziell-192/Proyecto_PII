package views;

import dao.UsuariosDAO;
import models.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para crear el primer usuario SuperAdministrador cuando el sistema se
 * ejecuta por primera vez.
 *
 * @author Jakim
 */
public class DialogCrearSuperAdmin extends JDialog {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnCrear;

    private UsuariosDAO usuarioDAO = new UsuariosDAO();

    public DialogCrearSuperAdmin(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Configuración inicial del sistema");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {

        // Colores
        Color AZUL_PRINCIPAL = new Color(18, 52, 86);
        Color AZUL_CLARO = new Color(220, 235, 247);
        Color AZUL_BOTON = new Color(30, 90, 140);

        // Fuentes
        Font fontProyecto = new Font("Lucida Calligraphy", Font.BOLD, 22);
        Font fontTitulo = new Font("Rockwell", Font.BOLD, 16);
        Font fontTexto = new Font("Nunito", Font.PLAIN, 13);
        Font fontBoton = new Font("Nunito", Font.BOLD, 14);

        JPanel panel = new JPanel();
        panel.setBackground(AZUL_CLARO);
        panel.setLayout(null);

        // Nombre del sistema
        JLabel lblProyecto = new JLabel("UnsiSmile");
        lblProyecto.setFont(fontProyecto);
        lblProyecto.setForeground(AZUL_PRINCIPAL);
        lblProyecto.setBounds(140, 15, 200, 30);
        panel.add(lblProyecto);

        // Título
        JLabel lblTitulo = new JLabel("Configuración inicial");
        lblTitulo.setFont(fontTitulo);
        lblTitulo.setForeground(AZUL_PRINCIPAL);
        lblTitulo.setBounds(140, 50, 200, 25);
        panel.add(lblTitulo);

        // Username
        JLabel lblUsuario = new JLabel("Nombre de usuario");
        lblUsuario.setFont(fontTexto);
        lblUsuario.setBounds(70, 105, 150, 20);
        panel.add(lblUsuario);

        txtUsername = new JTextField();
        txtUsername.setFont(fontTexto);
        txtUsername.setBounds(220, 100, 160, 28);
        panel.add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(fontTexto);
        lblPassword.setBounds(70, 150, 150, 20);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fontTexto);
        txtPassword.setBounds(220, 145, 160, 28);
        panel.add(txtPassword);

        // Botón
        btnCrear = new JButton("Crear SuperAdministrador");
        btnCrear.setFont(fontBoton);
        btnCrear.setBackground(AZUL_BOTON);
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFocusPainted(false);
        btnCrear.setBounds(120, 210, 220, 35);
        panel.add(btnCrear);

        btnCrear.addActionListener(e -> crearSuperAdmin());

        add(panel);
    }

    /**
     * Crea el usuario SuperAdministrador inicial del sistema.
     */
    private void crearSuperAdmin() {

        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (usuarioDAO.existeUsername(username)) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre de usuario ya existe",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Usuario superAdmin = new Usuario();
        superAdmin.setNombre("Super");
        superAdmin.setApellido("Administrador");
        superAdmin.setNombreDeUsuario(username);
        superAdmin.setEmail("superadmin@sistema.local");
        superAdmin.setContrasenya(password);
        superAdmin.setRol("SuperAdmin");

        if (usuarioDAO.registrarUsuarioQuery(superAdmin)) {
            JOptionPane.showMessageDialog(
                    this,
                    "SuperAdministrador creado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
            new NewLogin().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al crear el SuperAdministrador",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Método main solo para pruebas:
//    public static void main(String[] args) {
//        DialogCrearSuperAdmin dialog = new DialogCrearSuperAdmin(null, true);
//        dialog.setVisible(true);
//    }
}
