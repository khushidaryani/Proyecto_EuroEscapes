package interfazGrafica;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import baseDatos.BaseDatos;
import entidades.Itinerario;

import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Representa la interfaz gráfica para ver el itinerario
 */
public class VerItinerario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTable tablaItinerario;
	private Itinerario ultimoItinerario;

	/**
	 * Constructor de la clase VerItinerario
	 */

	public VerItinerario() {

		// Ajustes comunes
		setTitle("Ver itinerario");
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
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/ver.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("SU ITINERARIO");
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setBounds(105, 80, 474, 53);
		panelPrincipal.add(titulo);

		// Creacion de un modelo de tabla con columnas y filas
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("País");
		model.addColumn("Medio de transporte");
		model.addColumn("Duración");

		// Creacion de la tabla con el modelo
		tablaItinerario = new JTable(model);
		tablaItinerario.setBounds(150, 140, 300, 130);
		panelPrincipal.add(tablaItinerario);

		// Centra los campos en medio de las celdas
		DefaultTableCellRenderer centrarRenderer = new DefaultTableCellRenderer();
		centrarRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tablaItinerario.getColumnModel().getColumn(0).setCellRenderer(centrarRenderer);
		tablaItinerario.getColumnModel().getColumn(1).setCellRenderer(centrarRenderer);
		tablaItinerario.getColumnModel().getColumn(2).setCellRenderer(centrarRenderer);

		// Añadir la tabla a un JScrollPane
		JScrollPane scrollPane = new JScrollPane(tablaItinerario);
		scrollPane.setBounds(107, 194, 472, 39);
		panelPrincipal.add(scrollPane);

		// Panel secundario
		JPanel panelSecundario = new JPanel();
		panelSecundario.setBounds(105, 328, 474, 40);
		panelSecundario.setBackground(Color.decode("#F6DDCC"));
		panelPrincipal.add(panelSecundario);

		// Carga el metodo a la ventana
		cargarUltimoItinerario(model);

		JButton botonModificar = new JButton("Modificar itinerario");
		panelSecundario.add(botonModificar);

		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Abre la ventana ModificarItinerario y cierra este
				ModificarItinerario modificarItinerario = new ModificarItinerario();
				modificarItinerario.setVisible(true);
				dispose();
			}

		});

		JButton botonEliminar = new JButton("Eliminar itinerario");
		panelSecundario.add(botonEliminar);

		botonEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BaseDatos.eliminarItinerario(ultimoItinerario);
					JOptionPane.showMessageDialog(VerItinerario.this, "Se ha eliminado el itinerario correctamente");
					// Abre la ventana PlanearItinerario y cierra este
					PlanearItinerario planearItinerario = new PlanearItinerario();
					planearItinerario.setVisible(true);
					dispose();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(VerItinerario.this,
							"Error al eliminar el itinerario: " + ex.getMessage());
				}
			}
		});

		JButton botonCerrarSesion = new JButton("Cerrar sesión");
		panelSecundario.add(botonCerrarSesion);

		botonCerrarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Abre la ventana Inicio y cierra este
				Inicio paginaInicio = new Inicio();
				paginaInicio.setVisible(true);
				dispose();
			}

		});

	}

	/**
	 * Carga del ultimo itinerario desde la base de datos
	 */
	private void cargarUltimoItinerario(DefaultTableModel model) {
		try {
			ultimoItinerario = BaseDatos.obtenerUltimoItinerario();

			if (ultimoItinerario != null) {
				model.addRow(new Object[] { ultimoItinerario.getPais().getNombre(),
						ultimoItinerario.getMedioTransporte(), ultimoItinerario.getDuracion() });
			} else {
				JOptionPane.showMessageDialog(this, "No se encontró ningún itinerario.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar el itinerario: " + ex.getMessage());
		}
	}
}