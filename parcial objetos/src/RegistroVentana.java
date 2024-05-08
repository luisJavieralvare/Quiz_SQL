import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroVentana extends JFrame {
    private JTextField nombreField, apellidoField, usuarioField;
    private JPasswordField contraseñaField;
    private JComboBox<String> generoComboBox, consumoComboBox;
    private JComboBox<Integer> edadComboBox;
    private JTextArea historiaTextArea;

    public RegistroVentana() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font font = new Font("Arial", Font.CENTER_BASELINE, 15);

        addComponent(panel, new JLabel("Nombre:", JLabel.RIGHT), nombreField = new JTextField(), font);
        addComponent(panel, new JLabel("Apellido:", JLabel.RIGHT), apellidoField = new JTextField(), font);
        addComponent(panel, new JLabel("Género:", JLabel.RIGHT), generoComboBox = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otros"}), font);
        addComponent(panel, new JLabel("Edad:", JLabel.RIGHT), edadComboBox = new JComboBox<>(), font);
        for (int i = 10; i <= 99; i++) {
            edadComboBox.addItem(i);
        }
        addComponent(panel, new JLabel("Usuario:", JLabel.RIGHT), usuarioField = new JTextField(), font);
        addComponent(panel, new JLabel("Contraseña:", JLabel.RIGHT), contraseñaField = new JPasswordField(), font);
        addComponent(panel, new JLabel("Consumo:", JLabel.RIGHT), consumoComboBox = new JComboBox<>(new String[]{"No consume", "Consume medianamente", "Consume"}), font);
        addComponent(panel, new JLabel("Historia de Vida:", JLabel.RIGHT), historiaTextArea = new JTextArea(5, 20), font);
        JScrollPane scrollPane = new JScrollPane(historiaTextArea);
        panel.add(scrollPane);

        JButton registrar = new JButton("Registrar");
        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverLogin();
            }
        });
        registrar.setBackground(Color.ORANGE);
        cancelarButton.setBackground(Color.CYAN);

        registrar.setForeground(Color.BLACK);
        cancelarButton.setForeground(Color.BLACK);

        registrar.setFont(new Font("Cascadia Code", Font.HANGING_BASELINE, 17));
        cancelarButton.setFont(new Font("Arial", Font.ITALIC, 17));

        panel.add(registrar);
        panel.add(cancelarButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, JLabel label, JComponent component, Font font) {
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        component.setFont(font);
        if (component instanceof JTextField) {
            ((JTextField) component).setHorizontalAlignment(JTextField.CENTER);
        }
        panel.add(label);
        panel.add(component);
    }

    private void registrarUsuario() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String genero = (String) generoComboBox.getSelectedItem();
        int edad = (int) edadComboBox.getSelectedItem();
        String usuario = usuarioField.getText();
        String contraseña = new String(contraseñaField.getPassword());
        String consumo = (String) consumoComboBox.getSelectedItem();
        String historia = historiaTextArea.getText();

        if (usuario.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || historia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos");
            return;
        }

        guardarUsuarioContraseña(usuario, contraseña);

        guardarDatosEnArchivo(nombre, apellido, genero, edad, consumo, historia);

        JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");

        volverLogin();
    }

    private void guardarUsuarioContraseña(String usuario, String contraseña) {
        String url = "jdbc:mysql://127.0.0.1:3306/login?user=root&password=12345";
        String user = "root";
        String password = "12345";

        String sql = "INSERT INTO logininc (usuario, contraseña) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario y contraseña guardados exitosamente en la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar usuario y contraseña en la base de datos");
        }
    }


    private void guardarDatosEnArchivo(String nombre, String apellido, String genero, int edad, String consumo, String historia) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("perfil.txt", true))) {
            writer.write("Nombre: " + nombre);
            writer.newLine();
            writer.write("Apellido: " + apellido);
            writer.newLine();
            writer.write("Género: " + genero);
            writer.newLine();
            writer.write("Edad: " + edad);
            writer.newLine();
            writer.write("Consumo: " + consumo);
            writer.newLine();
            writer.write("Historia de Vida: " + historia);
            writer.newLine();
            writer.write("\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los datos en el archivo");
        }
    }

    private void volverLogin() {
        dispose();
        LoginVentana loginVentana = new LoginVentana();
        loginVentana.setVisible(true);
    }

    public static void regis(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegistroVentana ventana = new RegistroVentana();
                ventana.setVisible(true);
            }
        });
    }
}
