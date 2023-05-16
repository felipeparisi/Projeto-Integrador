package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JLabel lblStatus;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Café Consertos ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setToolTipText("Acessar");
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtLogin = new JTextField();
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "abcdefghijklmnopqrstuvwxyz-_. ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			
			}
		});
		txtLogin.setBounds(87, 37, 300, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		JButton btnAcessar = new JButton("");
		btnAcessar.setContentAreaFilled(false);
		btnAcessar.setBorderPainted(false);
		btnAcessar.setIcon(new ImageIcon(Login.class.getResource("/img/login_button.png")));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(196, 146, 82, 82);
		contentPane.add(btnAcessar);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(new Color(0, 51, 102));
		lblSenha.setFont(new Font("Verdana", Font.BOLD, 12));
		lblSenha.setBounds(30, 96, 46, 14);
		contentPane.add(lblSenha);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBackground(new Color(255, 255, 255));
		lblLogin.setForeground(new Color(0, 51, 102));
		lblLogin.setFont(new Font("Verdana", Font.BOLD, 12));
		lblLogin.setBounds(30, 39, 46, 14);
		contentPane.add(lblLogin);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/off_button.png")));
		lblStatus.setBounds(40, 183, 45, 45);
		contentPane.add(lblStatus);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(87, 94, 300, 20);
		contentPane.add(txtSenha);

		RestrictedTextField validar = new RestrictedTextField(txtLogin);
		validar.setLimit(20);
		RestrictedTextField validar1 = new RestrictedTextField(txtSenha);
		validar1.setLimit(15);
		
		getRootPane().setDefaultButton(btnAcessar);
		
		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(Login.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		lblNewLabel_7.setBounds(405, 57, 150, 150);
		contentPane.add(lblNewLabel_7);
		
	}// fim do construtor

	DAO dao = new DAO();
	private JLabel lblNewLabel_7;

	private void status() {
		try {
			Connection con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/off_button.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/on_button.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void logar() {
		
		String capturaSenha = new String(txtSenha.getPassword());
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a sua SENHA");
			txtSenha.requestFocus();
		} else {
			String read = "select * from tbusuarios where login = ? and senha = md5(?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					Main main = new Main();
					String perfil = rs.getString(6);
					if (perfil.equals("admin")) {
						main.setVisible(true);

						main.lblUsuario.setText(rs.getString(2));

						this.dispose();
					} else {
						main.setVisible(true);
						main.lblUsuario.setText(rs.getString(2));

						this.dispose();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha incorretos");

				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}
}// fim do codigo
