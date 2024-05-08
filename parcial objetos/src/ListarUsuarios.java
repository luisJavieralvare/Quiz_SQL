import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ListarUsuarios extends JFrame {
    private JTextArea usuariosTextArea;

    public ListarUsuarios() {
        setTitle("Lista de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton verUsuariosRegistradosButton = new JButton("Ver Usuarios Registrados");
        verUsuariosRegistradosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuariosRegistrados();
            }
        });

        JButton proyectoIntegradorButton = new JButton("Proyecto Integrador");
        proyectoIntegradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProyectoIntegrador();
            }
        });

        JButton registrarUsuariosButton = new JButton("Registrar Usuarios");
        registrarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuarios();
            }
        });

        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });

        JButton perfilUsuarioButton = new JButton("Perfil de Usuario");
        perfilUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPerfilUsuario();
            }
        });

        panel.add(verUsuariosRegistradosButton);
        panel.add(proyectoIntegradorButton);
        panel.add(registrarUsuariosButton);
        panel.add(volverButton);
        panel.add(perfilUsuarioButton);

        usuariosTextArea = new JTextArea();
        usuariosTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(usuariosTextArea);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void registrarUsuarios() {
        String usuario, contraseña;
        do {
            usuario = JOptionPane.showInputDialog("Ingrese el usuario (o deje vacío para terminar):");
            if (usuario != null && !usuario.isEmpty()) {
                contraseña = JOptionPane.showInputDialog("Ingrese la contraseña para el usuario '" + usuario + "':");
                if (contraseña != null) {
                    agregarUsuario(usuario, contraseña);
                }
            }
        } while (usuario != null && !usuario.isEmpty());
    }

    private void agregarUsuario(String usuario, String contraseña) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(usuario + "," + contraseña);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void volver() {
        dispose();
    }

    private void mostrarUsuariosRegistrados() {
        StringBuilder usuariosRegistrados = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 2) {
                    usuariosRegistrados.append("Usuario: ").append(datos[0]).append(", Contraseña: ").append(datos[1]).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        usuariosTextArea.setText(usuariosRegistrados.toString());
    }

    private void mostrarProyectoIntegrador() {
        ProyectoIntegrador proyectoIntegrador = new ProyectoIntegrador();
        proyectoIntegrador.setVisible(true);
    }

    private void abrirPerfilUsuario() {
        try (BufferedReader reader = new BufferedReader(new FileReader("perfil.txt"))) {
            String line;
            PerfilUsuario perfilUsuario = new PerfilUsuario();
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(": ");
                switch (datos[0]) {
                    case "Nombre":
                        perfilUsuario.setNombre(datos[1]);
                        break;
                    case "Apellido":
                        perfilUsuario.setApellido(datos[1]);
                        break;
                    case "Género":
                        perfilUsuario.setGenero(datos[1]);
                        break;
                    case "Edad":
                        perfilUsuario.setEdad(datos[1]);
                        break;
                    case "Usuario":
                        perfilUsuario.setUsuario(datos[1]);
                        break;
                    case "Consumo":
                        perfilUsuario.setConsumo(datos[1]);
                        break;
                    case "Historia de Vida":
                        perfilUsuario.setHistoria(datos[1]);
                        break;
                }
            }
            perfilUsuario.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void list(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ListarUsuarios ventana = new ListarUsuarios();
                ventana.setVisible(true);
            }
        });
    }
}
