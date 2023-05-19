package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtLogin;
	private JLabel lblLogin;
	private JTextField txtTelefone;
	private JTextField txtNome;
	private JLabel lblNome;
	private JLabel lblTelefone;
	private JLabel lblSenha;
	private JLabel lblPerfil;
	private JComboBox cboPerfil;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 15));
		setModal(true);
		setForeground(new Color(0, 0, 0));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setForeground(new Color(0, 0, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/user_button.png")));
		setTitle("Usuários Administradores");
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setBounds(450, 100, 1200, 800);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);
		
		lblNome = new JLabel("*Nome:");
		lblNome.setForeground(new Color(0, 51, 102));
		lblNome.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNome.setBounds(23, 35, 69, 18);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarUsuario();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNome.setBounds(124, 32, 701, 25);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		lblTelefone = new JLabel("*Telefone:");
		lblTelefone.setForeground(new Color(0, 51, 102));
		lblTelefone.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTelefone.setBounds(23, 369, 95, 18);
		getContentPane().add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setEditable(false);
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "0987654321( )- ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtTelefone.setBounds(124, 366, 319, 25);
		getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);

		lblLogin = new JLabel("*Login:");
		lblLogin.setForeground(new Color(0, 51, 102));
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 15));
		lblLogin.setBounds(23, 415, 69, 14);
		getContentPane().add(lblLogin);

		txtLogin = new JTextField();
		txtLogin.setEditable(false);
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (aceita somente os caracteres da String)
				String caracteres = "abcdefghijklmnopqrstuvwxyz._";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLogin.setBounds(124, 410, 319, 25);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		lblSenha = new JLabel("*Senha:");
		lblSenha.setForeground(new Color(0, 51, 102));
		lblSenha.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSenha.setBounds(25, 462, 67, 14);
		getContentPane().add(lblSenha);

		lblPerfil = new JLabel("*Perfil:");
		lblPerfil.setForeground(new Color(0, 51, 102));
		lblPerfil.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPerfil.setBounds(23, 507, 69, 32);
		getContentPane().add(lblPerfil);

		cboPerfil = new JComboBox();
		cboPerfil.setEditable(true);
		cboPerfil.setEnabled(false);
		cboPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(122, 511, 321, 25);
		getContentPane().add(cboPerfil);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(719, 474, 34, 32);
		getContentPane().add(lblStatus);

		btnPesquisar = new JButton("");
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Buscar48x48.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarId();
			}
		});
		btnPesquisar.setBounds(288, 298, 48, 48);
		getContentPane().add(btnPesquisar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane.setBounds(124, 99, 701, 160);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarUsuario();
			}
		});
		scrollPane.setViewportView(table);

		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Dialog", Font.BOLD, 15));
		lblId.setForeground(new Color(0, 51, 102));
		lblId.setBounds(33, 312, 46, 18);
		getContentPane().add(lblId);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(124, 312, 136, 25);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		btnAtualizar = new JButton("");
		btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtualizar.setContentAreaFilled(false);
		btnAtualizar.setBorderPainted(false);
		btnAtualizar.setToolTipText("Atualizar");
		btnAtualizar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update_button2.png")));
		btnAtualizar.setEnabled(false);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// (!)significa SE NÃO - NOT
				if (chckSenha.isSelected()) {
					alterarUsuarioSenha();
				} else {
					atualizarUsuario();
				}

			}
		});
		btnAtualizar.setBounds(56, 614, 110, 110);
		getContentPane().add(btnAtualizar);

		btnDeletar = new JButton("");
		btnDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletar.setContentAreaFilled(false);
		btnDeletar.setBorderPainted(false);
		btnDeletar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete_button2.png")));
		btnDeletar.setToolTipText("Deletar");
		btnDeletar.setEnabled(false);
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarUsuario();
			}
		});
		btnDeletar.setBounds(292, 614, 110, 110);
		getContentPane().add(btnDeletar);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add_button2.png")));
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuarios();
			}
		});
		btnAdicionar.setBounds(509, 614, 110, 110);
		getContentPane().add(btnAdicionar);

		txtSenha = new JPasswordField();
		txtSenha.setEditable(false);
		txtSenha.addKeyListener(new KeyAdapter() {
		});
		txtSenha.setBounds(122, 460, 319, 25);
		getContentPane().add(txtSenha);

		chckSenha = new JCheckBox("Alterar Senha");
		chckSenha.setEnabled(false);
		chckSenha.setForeground(new Color(0, 0, 0));
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSenha.setEditable(true);
				txtSenha.setText(null);
				txtSenha.requestFocus();
				txtSenha.setBackground(Color.GRAY);
			}
		});
		chckSenha.setBounds(479, 456, 142, 27);
		getContentPane().add(chckSenha);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/clear_button2.png")));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(719, 614, 110, 110);
		getContentPane().add(btnLimpar);

		RestrictedTextField validar = new RestrictedTextField(txtNome);
		validar.setLimit(25);
		RestrictedTextField validar1 = new RestrictedTextField(txtTelefone);
		validar1.setLimit(15);
		RestrictedTextField validar2 = new RestrictedTextField(txtSenha);
		validar2.setLimit(15);
		RestrictedTextField validar3 = new RestrictedTextField(txtLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Usuarios.class.getResource("/img/logo_cafe_consertor_vertical.png")));
		lblNewLabel.setBounds(917, 219, 200, 320);
		getContentPane().add(lblNewLabel);
		validar3.setLimit(15);

	}// Fim do construtor

	DAO dao = new DAO();
	private JLabel lblStatus;
	private JButton btnPesquisar;
	private JTable table;
	private JLabel lblId;
	private JTextField txtId;
	private JButton btnAtualizar;
	private JButton btnDeletar;
	private JButton btnAdicionar;
	private JPasswordField txtSenha;
	private JCheckBox chckSenha;

	// Método Status de Conexão

	private void status() {
		try {
			Connection con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dboff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dbon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	} // Fim método Status

	private void pesquisarUsuario() {
		String read2 = "select iduser as ID, nomeuser as Nome, fone as Telefone, login as Login, perfil from tbusuarios where nomeuser like ?";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtNome.getText() + "%");
			btnPesquisar.setEnabled(true);
			ResultSet rs = pst.executeQuery();

			// Uso da Biblioteca rs2xml para preencher a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarUsuario() {
		int setar = table.getSelectedRow();
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
	}

	private void pesquisarId() {
		if (txtId.getText().isEmpty()) {
			int confirma = JOptionPane.showConfirmDialog(null, "Usuário não encontrado: Deseja cadastrar um novo ?",
					"ATENÇÃO!!", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				cboPerfil.setEnabled(true);
				btnAdicionar.setEnabled(true);
				txtTelefone.setEditable(true);
				txtSenha.setEditable(true);
				txtLogin.setEditable(true);
				cboPerfil.setEditable(true);
				txtNome.requestFocus();
			} else if (confirma == JOptionPane.NO_OPTION) {
				limpar();
			}

		} else {
			String read = " select * from tbusuarios where iduser = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtId.getText());
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtNome.setText(rs.getString(2));
					txtTelefone.setText(rs.getString(3));
					txtLogin.setText(rs.getString(4));
					txtSenha.setText(rs.getString(5));
					cboPerfil.setSelectedItem(rs.getString(6));
					
					btnAdicionar.setEnabled(false);
					txtTelefone.setEditable(true);
					txtSenha.setEditable(true);
					txtLogin.setEditable(true);
					cboPerfil.setEnabled(true);

					chckSenha.setEnabled(true);
					
					btnAtualizar.setEnabled(true);
					btnDeletar.setEnabled(true);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	@SuppressWarnings("deprecation")
	private void adicionarUsuarios() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o nome do Usuário!");
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o telefone do Usuário!");
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com a senha do Usuário!");
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com a login do Usuário!");
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha com o perfil do Usuário!");
		} else {
			String read = "insert into tbusuarios (nomeuser, fone, login, senha, perfil) values (?,?,?,md5(?),?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtTelefone.getText());
				pst.setString(3, txtLogin.getText());
				String capturaSenha = new String(txtSenha.getPassword());
				pst.setString(4, capturaSenha);
				pst.setString(5, cboPerfil.getSelectedItem().toString());
				int confirma = pst.executeUpdate();
				// validação
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Usuário cadastrado com SUCESSO !!!");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ERRO - Usuário n�o Cadastrado");
				}
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(null, "Login  existente, tente com outro nome!!!");
				txtLogin.requestFocus();

			
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

// Método deletar
	private void deletarUsuario() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a exclus�o deste usuario ?", "ATEN��O!!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from tbusuarios where iduser = ?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int excluido = pst.executeUpdate();
				if (excluido == 1) {
					JOptionPane.showMessageDialog(null, "Usuario EXCLUIDO com SUCESSO !!!");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ERRO !! - Erro ao excluir o usuario");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// Fim do método deletar
		// Inicio do método Atualizar

	private void atualizarUsuario() {
		String update = "update tbusuarios set nomeuser = ?, fone = ?, login = ?, perfil = ?" + "where iduser = ? ";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtTelefone.getText());
			pst.setString(3, txtLogin.getText());
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.setString(5, txtId.getText());

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Dados do Usuario Alterados com SUCESSO!! ");
				limpar();
			} else {
				JOptionPane.showMessageDialog(null,
						"ERRO !! Os dados não foram atualizados - preencha os campos obrigatorios");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void alterarUsuarioSenha() {
		String update = "update tbusuarios set nomeuser = ? , fone = ?, login = ?, senha = md5(?), perfil = ?"
				+ "where iduser = ? ";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtTelefone.getText());
			pst.setString(3, txtLogin.getText());
			String capturarSenha = new String(txtSenha.getPassword());
			pst.setString(4, capturarSenha);
			pst.setString(5, cboPerfil.getSelectedItem().toString());
			pst.setString(6, txtId.getText());

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Dados do Usuario Alterados com SUCESSO!! ");

			} else {
				JOptionPane.showMessageDialog(null,
						"ERRO !! Os dados n�o foram atualizados - preencha os campos obrigatorios");
			}
			limpar();
			con.close();
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Login Existente");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e2) {
			System.out.println(e2);

		}
	}

	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtTelefone.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem("");
		
		txtTelefone.setEditable(false);
		txtSenha.setEditable(false);
		txtLogin.setEditable(false);
		cboPerfil.setEnabled(false);
		chckSenha.setEnabled(false);
		
		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(false);
		btnDeletar.setEnabled(false);
		
	}
} // fim do codigo
