import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PerfilUsuario extends JFrame {
    private JLabel nombreLabel, apellidoLabel, generoLabel, edadLabel, usuarioLabel, consumoLabel, historiaLabel;


    public PerfilUsuario() {
        setTitle("Perfil de Usuario");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        JLabel labelTitulo = new JLabel("Perfil de Usuario");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setHorizontalAlignment(JLabel.CENTER);
        labelTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel.add(labelTitulo, BorderLayout.NORTH);

        // Panel para la información del usuario
        JPanel datosPanel = new JPanel();
        datosPanel.setLayout(new BoxLayout(datosPanel, BoxLayout.Y_AXIS));
        datosPanel.setBackground(Color.white);
        datosPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        nombreLabel = new JLabel();
        apellidoLabel = new JLabel();
        generoLabel = new JLabel();
        edadLabel = new JLabel();
        usuarioLabel = new JLabel();
        consumoLabel = new JLabel();
        historiaLabel = new JLabel();

        datosPanel.add(nombreLabel);
        datosPanel.add(apellidoLabel);
        datosPanel.add(generoLabel);
        datosPanel.add(edadLabel);
        datosPanel.add(usuarioLabel);
        datosPanel.add(consumoLabel);
        datosPanel.add(historiaLabel);

        panel.add(datosPanel, BorderLayout.CENTER);

        getContentPane().add(panel);
    }

    public void setNombre(String nombre) {
        nombreLabel.setText("Nombre: " + nombre);
    }

    public void setApellido(String apellido) {
        apellidoLabel.setText("Apellido: " + apellido);
    }

    public void setGenero(String genero) {
        generoLabel.setText("Género: " + genero);
    }

    public void setEdad(String edad) {
        edadLabel.setText("Edad: " + edad);
    }

    public void setUsuario(String usuario) {
        usuarioLabel.setText("Usuario: " + usuario);
    }

    public void setConsumo(String consumo) {
        consumoLabel.setText("Consumo: " + consumo);
    }

    public void setHistoria(String historia) {
        historiaLabel.setText("Historia de Vida: " + historia);
    }

    public static void perUsuario(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PerfilUsuario perfilUsuario = new PerfilUsuario();
            perfilUsuario.setVisible(true);
        });
    }
}
