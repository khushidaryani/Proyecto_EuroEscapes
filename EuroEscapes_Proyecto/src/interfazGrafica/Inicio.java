package interfazGrafica;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Representa la interfaz gráfica para la pagina de inicio
 */
public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;

	/**
	 * Constructor de la clase Inicio
	 */

	public Inicio() {

		// Ajustes comunes
		setTitle("¡Bienvenidas a EuroEscapes!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBounds(100, 100, 700, 500);

		// Panel principal
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
		panelPrincipal.setBackground(Color.decode("#F6DDCC"));
		setContentPane(panelPrincipal);

		GridBagLayout disposicionPanel = new GridBagLayout();
		disposicionPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		disposicionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		disposicionPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		disposicionPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panelPrincipal.setLayout(disposicionPanel);

		// Icono de la ventana
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/avion.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("EUROESCAPES");
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		GridBagConstraints ajustesTitulo = new GridBagConstraints();
		ajustesTitulo.insets = new Insets(0, 0, 5, 5);
		ajustesTitulo.gridx = 4;
		ajustesTitulo.gridy = 3;
		panelPrincipal.add(titulo, ajustesTitulo);

		// Descripcion
		JLabel descripcion = new JLabel("Con EuroEscapes, ¡vuela alto y descubre Europa en cada salto!");
		descripcion.setFont(new Font("Arial", Font.BOLD, 15));
		GridBagConstraints ajustesDescripcion = new GridBagConstraints();
		ajustesDescripcion.insets = new Insets(0, 0, 5, 5);
		ajustesDescripcion.gridx = 4;
		ajustesDescripcion.gridy = 5;
		panelPrincipal.add(descripcion, ajustesDescripcion);

		// Panel secundario
		JPanel panelSecundario = new JPanel();
		panelSecundario.setBackground(Color.decode("#F6DDCC"));
		GridBagConstraints ajustesPanelSecundario = new GridBagConstraints();
		ajustesPanelSecundario.insets = new Insets(0, 0, 5, 5);
		ajustesPanelSecundario.fill = GridBagConstraints.BOTH;
		ajustesPanelSecundario.gridx = 4;
		ajustesPanelSecundario.gridy = 8;
		panelPrincipal.add(panelSecundario, ajustesPanelSecundario);

		// Elementos del panel secundario
		JButton botonRegistrar = new JButton("Registrar");
		panelSecundario.add(botonRegistrar);
		botonRegistrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Registrar registrar = new Registrar();
				registrar.setVisible(true);
				dispose();
			}

		});

		JButton botonIniciarSesion = new JButton("Iniciar sesión");
		panelSecundario.add(botonIniciarSesion);
		botonIniciarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Abre la ventana IniciarSesion y cierra este
				IniciarSesion iniciarSesion = new IniciarSesion();
				iniciarSesion.setVisible(true);
				dispose();
			}

		});

	}
}
