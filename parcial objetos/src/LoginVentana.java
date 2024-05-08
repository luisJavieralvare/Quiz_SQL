import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginVentana extends JFrame {
    private JTextField usuarioField, contraseñaField;
    private JLabel mensajeLabel;

    public LoginVentana() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JLabel titleLabel = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 27));
        panel.add(titleLabel);
        panel.add(new JLabel());

        JLabel iniciose = new JLabel("Usuario", SwingConstants.CENTER);
        iniciose.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel contra = new JLabel("Contraseña", SwingConstants.CENTER);
        contra.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(usuarioField = new JTextField());
        panel.add(contraseñaField = new JPasswordField());
        panel.add(iniciose);
        panel.add(contra);
        panel.add(new JLabel());

        mensajeLabel = new JLabel();
        mensajeLabel.setForeground(Color.RED);

        JPanel buttonPanel = new JPanel();
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCredenciales();
            }
        });

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistro();
            }
        });
        iniciarSesionButton.setBackground(Color.WHITE);
        registrarButton.setBackground(Color.BLACK);

        iniciarSesionButton.setForeground(Color.BLUE);
        registrarButton.setForeground(Color.WHITE);

        iniciarSesionButton.setFont(new Font("Cascadia Code", Font.BOLD, 17));
        registrarButton.setFont(new Font("Arial", Font.ITALIC, 17));

        buttonPanel.add(iniciarSesionButton);
        buttonPanel.add(registrarButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(mensajeLabel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void verificarCredenciales() {
        String usuario = usuarioField.getText();
        String contraseña = contraseñaField.getText();

        if (verificarEnBaseDeDatos(usuario, contraseña)) {
            mensajeLabel.setText("Inicio de sesión exitoso");
            mensajeLabel.setForeground(Color.GREEN);
            abrirVentanaListarUsuarios();
        } else {
            mensajeLabel.setText("Error: Usuario o contraseña incorrectos");
            mensajeLabel.setForeground(Color.RED);
        }
    }

    private boolean verificarEnBaseDeDatos(String usuario, String contraseña) {
        String url = "jdbc:mysql://127.0.0.1:3306/login?user=root&password=12345";
        String sql = "SELECT * FROM logininc WHERE usuario = ? AND contraseña = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResult = rs.next();
                System.out.println("INICIO DE SESION ECXITOSO");
                return hasResult;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void mostrarVentanaRegistro() {
        RegistroVentana ventanaRegistro = new RegistroVentana();
        ventanaRegistro.setVisible(true);
        dispose();
    }

    private void abrirVentanaListarUsuarios() {
        ListarUsuarios listarUsuarios = new ListarUsuarios();
        listarUsuarios.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginVentana ventana = new LoginVentana();
                ventana.setVisible(true);
            }
        });
    }
}
