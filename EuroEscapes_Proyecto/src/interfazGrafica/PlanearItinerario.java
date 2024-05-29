package interfazGrafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import baseDatos.BaseDatos;
import entidades.Itinerario;
import entidades.Pais;

import javax.swing.JComboBox;

/**
 * Representa la interfaz gráfica para planear el itinerario
 */
public class PlanearItinerario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTextField duracionTexto;
	private JButton botonItinerario;
	private JComboBox<Pais> comboBoxPaises;
	private JComboBox<String> comboBoxTransporte;

	/**
	 * Constructor de la clase PlanearItinerario
	 */

	public PlanearItinerario() {

		// Ajustes comunes
		setTitle("Planear itinerario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBounds(100, 100, 700, 500);

		// Panel principal
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
		panelPrincipal.setBackground(Color.decode("#F6DDCC"));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);

		// Icono de la ventana
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/plan.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("PLANEAR ITINERARIO");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		titulo.setBounds(10, 64, 666, 44);
		panelPrincipal.add(titulo);

		// Otros elementos del panel principal
		JLabel pais = new JLabel("Elija su país: ");
		pais.setHorizontalAlignment(SwingConstants.CENTER);
		pais.setBounds(191, 152, 116, 24);
		panelPrincipal.add(pais);

		comboBoxPaises = new JComboBox<Pais>();
		comboBoxPaises.setBounds(317, 154, 161, 21);
		panelPrincipal.add(comboBoxPaises);

		JLabel transporte = new JLabel("Elija el medio de transporte que desea usar:");
		transporte.setHorizontalAlignment(SwingConstants.CENTER);
		transporte.setBounds(200, 220, 300, 24);
		panelPrincipal.add(transporte);

		comboBoxTransporte = new JComboBox<String>();
		comboBoxTransporte.setBounds(255, 256, 193, 21);
		panelPrincipal.add(comboBoxTransporte);

		JLabel duracion = new JLabel("Duración en días:");
		duracion.setHorizontalAlignment(SwingConstants.CENTER);
		duracion.setBounds(240, 322, 116, 24);
		panelPrincipal.add(duracion);

		duracionTexto = new JTextField();
		duracionTexto.setColumns(10);
		duracionTexto.setBounds(358, 325, 96, 19);
		panelPrincipal.add(duracionTexto);

		// Carga de los dos metodos a la ventana
		cargarPaises();
		cargarMediosDeTransporte();

		botonItinerario = new JButton("Planear y ver mi itinerario");
		botonItinerario.setBounds(240, 386, 222, 21);
		panelPrincipal.add(botonItinerario);

		botonItinerario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Pais paisSeleccionado = (Pais) comboBoxPaises.getSelectedItem();
					String medioTransporte = (String) comboBoxTransporte.getSelectedItem();
					int duracion = Integer.parseInt(duracionTexto.getText());

					Itinerario itinerario = new Itinerario(paisSeleccionado, medioTransporte, duracion);
					BaseDatos.insertarItinerario(itinerario);

					// Abre la ventana VerItinerario y cierra este
					VerItinerario verItinerario = new VerItinerario();
					verItinerario.setVisible(true);
					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(PlanearItinerario.this,
							"Por favor, ingrese una duración válida" + ex.getMessage());
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(PlanearItinerario.this,
							"Error al guardar el itinerario: " + ex.getMessage());
				}
			}
		});

	}

	/**
	 * Carga de los paises desde la base de datos y los añade al comboBox
	 */
	private void cargarPaises() {
		try {
			List<Pais> paises = BaseDatos.obtenerPaises();
			for (Pais pais : paises) {
				comboBoxPaises.addItem(pais);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar los países: " + ex.getMessage());
		}
	}

	/**
	 * Carga de los medios de transporte y los añade al comboBox
	 */
	private void cargarMediosDeTransporte() {
		String[] mediosDeTransporte = { "Avión", "Barco", "Tren", "Autobús", "Coche", "Bicicleta", "Moto", "Metro",
				"Tranvía", "Helicóptero" };
		for (String medio : mediosDeTransporte) {
			comboBoxTransporte.addItem(medio);
		}
	}
}
