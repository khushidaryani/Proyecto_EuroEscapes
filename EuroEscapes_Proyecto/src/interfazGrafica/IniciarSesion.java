package interfazGrafica;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import baseDatos.BaseDatos;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Representa la interfaz gráfica para iniciar sesión
 */
public class IniciarSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrinicipal;
	private static JTextField dniTexto;
	private JPasswordField passwordField;

	/**
	 * Constructor de la clase IniciarSesion
	 */

	public IniciarSesion() {

		// Ajustes comunes
		setTitle("Iniciar sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setBounds(100, 100, 700, 500);

		// Panel principal
		panelPrinicipal = new JPanel();
		panelPrinicipal.setBorder(new EmptyBorder(20, 20, 20, 20));
		panelPrinicipal.setBackground(Color.decode("#F6DDCC"));
		setContentPane(panelPrinicipal);

		GridBagLayout disposicionPanel = new GridBagLayout();
		disposicionPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		disposicionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		disposicionPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0 };
		disposicionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0,
				0.0, 0.0 };
		panelPrinicipal.setLayout(disposicionPanel);

		// Icono de la ventana
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/autenticacion.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("INICIAR SESIÓN");
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		GridBagConstraints ajustesTitulo = new GridBagConstraints();
		ajustesTitulo.insets = new Insets(50, 0, 30, 5);
		ajustesTitulo.gridx = 4;
		ajustesTitulo.gridy = 2;
		ajustesTitulo.gridwidth = -10;
		ajustesTitulo.anchor = GridBagConstraints.CENTER;
		panelPrinicipal.add(titulo, ajustesTitulo);

		// Panel de usuario
		JPanel panelUsuario = new JPanel(new GridBagLayout());
		panelUsuario.setBackground(Color.decode("#F6DDCC"));
		GridBagConstraints ajustesPanelUsuario = new GridBagConstraints();
		ajustesPanelUsuario.insets = new Insets(10, 0, 10, 5);
		ajustesPanelUsuario.gridx = 4;
		ajustesPanelUsuario.gridy = 4;
		ajustesPanelUsuario.gridwidth = -10;
		panelPrinicipal.add(panelUsuario, ajustesPanelUsuario);

		// Elementos del panel de usuario
		JLabel dni = new JLabel("DNI:");
		GridBagConstraints ajustesDni = new GridBagConstraints();
		ajustesDni.anchor = GridBagConstraints.EAST;
		ajustesDni.insets = new Insets(0, 0, 0, 10);
		panelUsuario.add(dni, ajustesDni);

		dniTexto = new JTextField();
		dniTexto.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints ajustesTextField = new GridBagConstraints();
		ajustesTextField.fill = GridBagConstraints.HORIZONTAL;
		ajustesTextField.gridx = 1;
		ajustesTextField.gridy = 0;
		ajustesTextField.gridwidth = 2;
		panelUsuario.add(dniTexto, ajustesTextField);
		dniTexto.setColumns(10);

		// Panel de contraseña
		JPanel panelContraseña = new JPanel(new GridBagLayout());
		panelContraseña.setBackground(Color.decode("#F6DDCC"));
		GridBagConstraints ajustesPanelContraseña = new GridBagConstraints();
		ajustesPanelContraseña.insets = new Insets(10, 0, 10, 5);
		ajustesPanelContraseña.gridx = 4;
		ajustesPanelContraseña.gridy = 6;
		ajustesPanelContraseña.gridwidth = -10;
		panelPrinicipal.add(panelContraseña, ajustesPanelContraseña);

		// Elementos del panel de contraseña
		JLabel password = new JLabel("Contraseña:");
		GridBagConstraints ajustesPassword = new GridBagConstraints();
		ajustesPassword.anchor = GridBagConstraints.EAST;
		ajustesPassword.insets = new Insets(0, 0, 0, 10);
		panelContraseña.add(password, ajustesPassword);

		passwordField = new JPasswordField();
		GridBagConstraints ajustesPasswordField = new GridBagConstraints();
		ajustesPasswordField.fill = GridBagConstraints.HORIZONTAL;
		ajustesPasswordField.gridx = 1;
		ajustesPasswordField.gridy = 0;
		ajustesPasswordField.gridwidth = 2;
		panelContraseña.add(passwordField, ajustesPasswordField);
		passwordField.setColumns(10);

		// Panel secundario
		JPanel panelSecundario = new JPanel();
		panelSecundario.setBackground(Color.decode("#F6DDCC"));
		GridBagConstraints ajustesPanelSecundario = new GridBagConstraints();
		ajustesPanelSecundario.insets = new Insets(0, 0, 5, 5);
		ajustesPanelSecundario.fill = GridBagConstraints.BOTH;
		ajustesPanelSecundario.gridx = 4;
		ajustesPanelSecundario.gridy = 10;
		panelPrinicipal.add(panelSecundario, ajustesPanelSecundario);

		JButton botonAutenticar = new JButton("Autenticarme");
		panelSecundario.add(botonAutenticar);

		botonAutenticar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dni = dniTexto.getText();
				String password = new String(passwordField.getPassword());

				try {
					if (!BaseDatos.dniExiste(dni)) {
						JOptionPane.showMessageDialog(null, "El usuario no está registrado");
						dniTexto.setText(""); // Limpia el campo de texto del DNI
						passwordField.setText(""); // Limpia el campo de contraseña
					} else {
						if (BaseDatos.verificarPassword(dni, password)) {
							JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
							// Abre la ventana PlanearItinerario y cierra este
							PlanearItinerario planearItinerario = new PlanearItinerario();
							planearItinerario.setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
							passwordField.setText(""); // Limpia el campo de contraseña
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al verificar el usuario: " + ex.getMessage());
				}
			}
		});

		JButton botonVolver = new JButton("Volver atrás");
		panelSecundario.add(botonVolver);
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre la ventana Inicio y cierra este
				Inicio paginaInicio = new Inicio();
				paginaInicio.setVisible(true);
				dispose();
			}
		});
	}

}
