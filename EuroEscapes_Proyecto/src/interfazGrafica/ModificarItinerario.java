package interfazGrafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import baseDatos.BaseDatos;
import entidades.Itinerario;
import entidades.Pais;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Representa la interfaz gráfica para modificar el itinerario
 */
public class ModificarItinerario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JComboBox<Pais> comboBoxPaises;
	private JComboBox<String> comboBoxTransporte;
	private JTextField duracionTexto;
	private JButton botonItinerario;
	private Itinerario ultimoItinerario;

	/**
	 * Constructor de la clase ModificarItinerario
	 */

	public ModificarItinerario() {

		// Ajustes comunes
		setTitle("Modificar itinerario");
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
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/modificar.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("MODIFICAR ITINERARIO");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		titulo.setBounds(10, 64, 666, 44);
		panelPrincipal.add(titulo);

		// Otros elementos del panel principal
		JLabel pais = new JLabel("Elija su país: ");
		pais.setHorizontalAlignment(SwingConstants.CENTER);
		pais.setBounds(191, 152, 116, 24);
		panelPrincipal.add(pais);

		comboBoxPaises = new JComboBox<>();
		comboBoxPaises.setBounds(317, 154, 161, 21);
		panelPrincipal.add(comboBoxPaises);

		JLabel transporte = new JLabel("Elija el medio de transporte que desea usar:");
		transporte.setHorizontalAlignment(SwingConstants.CENTER);
		transporte.setBounds(191, 220, 300, 24);
		panelPrincipal.add(transporte);

		comboBoxTransporte = new JComboBox<>();
		comboBoxTransporte.setBounds(240, 256, 193, 21);
		panelPrincipal.add(comboBoxTransporte);

		JLabel duracion = new JLabel("Duración en días:");
		duracion.setHorizontalAlignment(SwingConstants.CENTER);
		duracion.setBounds(234, 322, 116, 24);
		panelPrincipal.add(duracion);

		duracionTexto = new JTextField();
		duracionTexto.setColumns(10);
		duracionTexto.setBounds(360, 325, 96, 19);
		panelPrincipal.add(duracionTexto);

		// Carga de los tres metodos a la ventana
		cargarPaises();
		cargarMediosDeTransporte();
		cargarUltimoItinerario();

		botonItinerario = new JButton("Modificar y ver mi itinerario");
		botonItinerario.setBounds(232, 385, 222, 21);
		panelPrincipal.add(botonItinerario);

		botonItinerario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Pais paisSeleccionado = (Pais) comboBoxPaises.getSelectedItem();
					String medioTransporte = (String) comboBoxTransporte.getSelectedItem();
					String duracionStr = duracionTexto.getText().trim();

					if (duracionStr.isEmpty()) {
						JOptionPane.showMessageDialog(ModificarItinerario.this, "Por favor, ingrese una duración.");
						return;
					}

					int duracion = Integer.parseInt(duracionStr);

					ultimoItinerario.setPais(paisSeleccionado);
					ultimoItinerario.setMedioTransporte(medioTransporte);
					ultimoItinerario.setDuracion(duracion);

					BaseDatos.actualizarItinerario(ultimoItinerario);
					
					// Abre la ventana VerItinerario y cierra este
					VerItinerario verItinerario = new VerItinerario();
					verItinerario.setVisible(true);
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(ModificarItinerario.this, "Por favor, ingrese una duración válida");
					ex.printStackTrace();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(ModificarItinerario.this,
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

	/**
	 * Carga del ultimo itinerario desde la base de datos
	 */
	private void cargarUltimoItinerario() {
		try {
			ultimoItinerario = BaseDatos.obtenerUltimoItinerario();
			if (ultimoItinerario != null) {
				comboBoxPaises.setSelectedItem(ultimoItinerario.getPais());
				comboBoxTransporte.setSelectedItem(ultimoItinerario.getMedioTransporte());
				duracionTexto.setText(String.valueOf(ultimoItinerario.getDuracion()));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el itinerario: " + ex.getMessage());
		}
	}
}
