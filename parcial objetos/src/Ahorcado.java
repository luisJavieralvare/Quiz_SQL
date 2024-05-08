import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ahorcado extends JFrame {
    private List<String> palabras;
    private String palabraSeleccionada;
    private StringBuilder palabraOculta;
    private int intentosRestantes = 6;
    private JLabel lblPalabra, lblIntentos, lblDibujo;
    private JTextField txtLetra;
    private JButton btnIntentar;
    private JPanel panelDibujo;

    private static final Random random = new Random();

    public Ahorcado() {
        setTitle("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarAhorcado(g);
            }
        };
        panelDibujo.setPreferredSize(new Dimension(250, 250));

        cargarPalabrasDesdeArchivo("palabras.txt");
        inicializarJuego();

        lblPalabra = new JLabel(palabraOculta.toString(), SwingConstants.CENTER);
        lblIntentos = new JLabel("Intentos restantes: " + intentosRestantes, SwingConstants.CENTER);
        lblDibujo = new JLabel("", SwingConstants.CENTER);
        txtLetra = new JTextField(1);
        btnIntentar = new JButton("Intentar");

        btnIntentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letra = txtLetra.getText().toUpperCase();
                if (letra.length() == 1 && Character.isLetter(letra.charAt(0))) {
                    if (palabraOculta.indexOf(letra) == -1) {
                        actualizarPalabra(letra.charAt(0));
                    } else {
                        JOptionPane.showMessageDialog(null, "¡Ya has intentado esta letra antes!");
                    }
                    txtLetra.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Ingresa una sola letra válida.");
                }
            }
        });

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(lblPalabra, BorderLayout.NORTH);
        panelCentral.add(panelDibujo, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(lblIntentos, BorderLayout.WEST);
        panelInferior.add(txtLetra, BorderLayout.CENTER);
        panelInferior.add(btnIntentar, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void inicializarJuego() {
        palabraSeleccionada = palabras.get(random.nextInt(palabras.size()));
        palabraOculta = new StringBuilder("_".repeat(palabraSeleccionada.length()));
        intentosRestantes = 6;
        panelDibujo.repaint();
    }

    private void cargarPalabrasDesdeArchivo(String nombreArchivo) {
        palabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                palabras.add(linea.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarPalabra(char letra) {
        boolean acierto = false;
        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            if (palabraSeleccionada.charAt(i) == letra) {
                palabraOculta.setCharAt(i, letra);
                acierto = true;
            }
        }
        if (!acierto) {
            intentosRestantes--;
            panelDibujo.repaint();
        }
        lblIntentos.setText("Intentos restantes: " + intentosRestantes);
        lblPalabra.setText(palabraOculta.toString());
        if (intentosRestantes == 0 || palabraOculta.indexOf("_") == -1) {
            mostrarResultado();
        }
    }

    private void mostrarResultado() {
        String mensaje;
        if (intentosRestantes == 0) {
            mensaje = "¡Perdiste! La palabra era: " + palabraSeleccionada;
        } else {
            mensaje = "¡Ganaste! Has adivinado la palabra: " + palabraSeleccionada;
        }
        int opcion = JOptionPane.showOptionDialog(null, mensaje, "Resultado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (opcion == JOptionPane.OK_OPTION) {
            inicializarJuego();
        } else {
            System.exit(0);
        }
    }

    private void dibujarAhorcado(Graphics g) {
        int x = 50;
        int y = 200;
        int ancho = 150;
        int alto = 30;
        g.setColor(Color.BLACK);

        g.drawLine(x + ancho / 2, y, x + ancho / 2, y + alto / 4);
        g.drawLine(x + ancho / 2, y + alto / 4, x + ancho - alto / 4, y + alto / 4);
        g.drawLine(x + ancho - alto / 4, y + alto / 4, x + ancho - alto / 4, y);

        int pasos = 6 - intentosRestantes;
        if (pasos >= 1) {
            g.drawOval(x + ancho / 2 - ancho / 8, y - ancho / 4, ancho / 4, ancho / 4);
        }
        if (pasos >= 2) {
            g.drawLine(x + ancho / 2, y - ancho / 8, x + ancho / 2, y + alto);
        }
        if (pasos >= 3) {
            g.drawLine(x + ancho / 2, y - ancho / 8, x + ancho / 2 - ancho / 4, y - ancho / 6);
        }
        if (pasos >= 4) {
            g.drawLine(x + ancho / 2, y - ancho / 8, x + ancho / 2 + ancho / 4, y - ancho / 6);
        }
        if (pasos >= 5) {
            g.drawLine(x + ancho / 2, y + alto, x + ancho / 2 - ancho / 4, y + alto + ancho / 4);
        }
        if (pasos >= 6) {
            g.drawLine(x + ancho / 2, y + alto, x + ancho / 2 + ancho / 4, y + alto + ancho / 4);
        }
    }

    public static void Ahorca(String[] args) {
        SwingUtilities.invokeLater(() -> new Ahorcado());
    }
}
