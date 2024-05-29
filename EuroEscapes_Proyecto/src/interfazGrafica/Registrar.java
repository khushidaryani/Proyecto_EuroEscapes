package interfazGrafica;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import baseDatos.ControlErrores;
import baseDatos.BaseDatos;
import entidades.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Representa la interfaz gráfica para registrar
 */
public class Registrar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTextField dniTexto;
	private JTextField nombreTexto;
	private JTextField primerApellidoTexto;
	private JTextField segundoApellidotexto;
	private JTextField contactoTexto;
	private JPasswordField passwordField;
	private JButton botonCrearCuenta;

	/**
	 * Constructor de la clase Registrar
	 */

	public Registrar() {

		// Ajustes comunes
		setTitle("Registrar");
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
		ImageIcon imagen = new ImageIcon(getClass().getResource("/recursos/registro.png"));
		this.setIconImage(imagen.getImage());

		// Titulo
		JLabel titulo = new JLabel("REGISTRAR");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 50));
		titulo.setBounds(171, 55, 348, 44);
		panelPrincipal.add(titulo);

		// Otros elementos del panel principal
		JLabel dni = new JLabel("DNI:");
		dni.setBounds(234, 152, 116, 24);
		panelPrincipal.add(dni);

		dniTexto = new JTextField();
		dniTexto.setBounds(360, 155, 96, 19);
		panelPrincipal.add(dniTexto);
		dniTexto.setColumns(10);

		JLabel password = new JLabel("Contraseña:");
		password.setBounds(234, 186, 116, 24);
		panelPrincipal.add(password);

		passwordField = new JPasswordField();
		passwordField.setBounds(360, 189, 96, 19);
		passwordField.setColumns(10);
		panelPrincipal.add(passwordField);

		JLabel nombre = new JLabel("Nombre:");
		nombre.setBounds(234, 220, 116, 24);
		panelPrincipal.add(nombre);

		nombreTexto = new JTextField();
		nombreTexto.setColumns(10);
		nombreTexto.setBounds(360, 223, 96, 19);
		panelPrincipal.add(nombreTexto);

		JLabel primerApellido = new JLabel("Primer apellido:");
		primerApellido.setBounds(234, 254, 116, 24);
		panelPrincipal.add(primerApellido);

		primerApellidoTexto = new JTextField();
		primerApellidoTexto.setColumns(10);
		primerApellidoTexto.setBounds(360, 257, 96, 19);
		panelPrincipal.add(primerApellidoTexto);

		JLabel segundoApellido = new JLabel("Segundo apellido:");
		segundoApellido.setBounds(234, 288, 116, 24);
		panelPrincipal.add(segundoApellido);

		segundoApellidotexto = new JTextField();
		segundoApellidotexto.setColumns(10);
		segundoApellidotexto.setBounds(360, 291, 96, 19);
		panelPrincipal.add(segundoApellidotexto);

		JLabel contacto = new JLabel("Contacto:");
		contacto.setBounds(234, 322, 116, 24);
		panelPrincipal.add(contacto);

		contactoTexto = new JTextField();
		contactoTexto.setColumns(10);
		contactoTexto.setBounds(360, 325, 96, 19);
		panelPrincipal.add(contactoTexto);

		botonCrearCuenta = new JButton("Crear cuenta");
		botonCrearCuenta.setBounds(215, 385, 116, 21);
		panelPrincipal.add(botonCrearCuenta);

		JButton botonVolver = new JButton("Volver atrás");
		botonVolver.setBounds(352, 385, 116, 21);
		panelPrincipal.add(botonVolver);

		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre la ventana Inicio y cierra este
				Inicio paginaInicio = new Inicio();
				paginaInicio.setVisible(true);
				dispose();
			}
		});

		botonCrearCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String dni = dniTexto.getText();
				String password = new String(passwordField.getPassword());
				String nombre = nombreTexto.getText();
				String primerApellido = primerApellidoTexto.getText();
				String segundoApellido = segundoApellidotexto.getText();
				String contacto = contactoTexto.getText();

				// Validacion de los campos
				if (!ControlErrores.validarDNI(dni) || !ControlErrores.validarPassword(password)
						|| !ControlErrores.validarNombre(nombre) || !ControlErrores.validarApellido(primerApellido)
						|| !ControlErrores.validarApellido(segundoApellido)
						|| !ControlErrores.validarContacto(contacto)) {
					return; // Si hay errores de validación, sale del método
				}

				Cliente cliente = new Cliente(dni, password, nombre, primerApellido, segundoApellido, contacto);

				try {
					if (BaseDatos.dniExiste(dni)) {
						JOptionPane.showMessageDialog(null, "Este usuario ya existe");
						dniTexto.setText("");
					} else {
						BaseDatos.insertarCliente(cliente);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
						// Abre la ventana IniciarSesion y cierra este
						IniciarSesion iniciarSesion = new IniciarSesion();
						iniciarSesion.setVisible(true);
						dispose();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al registrar el cliente: " + ex.getMessage());
				}
			}
		});
	}
}