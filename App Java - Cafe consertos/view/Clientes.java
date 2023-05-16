package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.Cursor;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Clientes extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNome;
	private JTextField txtNome;
	private JTable table;
	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblEndereco;
	private JTextField txtEndereco;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JLabel lblComplemento;
	private JTextField txtComplemento;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblTelefone;
	private JTextField txtTelefone;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnAlterar;
	private JButton btnLimpar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		setResizable(false);
		setModal(true);
		getContentPane().setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/client_button.png")));
		setTitle("Clientes");
		setBounds(425, 100, 1000, 800);
		getContentPane().setLayout(null);

		lblNome = new JLabel("*Nome:");
		lblNome.setForeground(new Color(0, 51, 102));
		lblNome.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNome.setBounds(23, 32, 68, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.setForeground(new Color(0, 51, 102));
		txtNome.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarClientes();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ._";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNome.setBounds(87, 30, 236, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 71, 526, 80);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setForeground(new Color(0, 51, 102));
		table.setFont(new Font("Verdana", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCliente();
			}
		});
		scrollPane.setViewportView(table);

		lblId = new JLabel("* ID:");
		lblId.setForeground(new Color(0, 51, 102));
		lblId.setFont(new Font("Verdana", Font.BOLD, 12));
		lblId.setBounds(360, 32, 48, 14);
		getContentPane().add(lblId);

		txtId = new JTextField();
		txtId.setForeground(new Color(0, 51, 102));
		txtId.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtId.setEditable(false);
		txtId.setBounds(401, 30, 95, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JButton btnBuscarId = new JButton("");
		btnBuscarId.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarId.setBorderPainted(false);
		btnBuscarId.setContentAreaFilled(false);
		btnBuscarId.setToolTipText("Buscar ID");
		btnBuscarId.setIcon(new ImageIcon(Clientes.class.getResource("/img/Buscar48x48.png")));
		btnBuscarId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarId();
			}
		});
		btnBuscarId.setBounds(501, 11, 48, 48);
		getContentPane().add(btnBuscarId);

		lblEndereco = new JLabel("Endereço:");
		lblEndereco.setForeground(new Color(0, 51, 102));
		lblEndereco.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEndereco.setBounds(19, 219, 67, 14);
		getContentPane().add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setForeground(new Color(0, 51, 102));
		txtEndereco.setEditable(false);
		txtEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEndereco.setBounds(94, 217, 455, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		lblNumero = new JLabel("Número:");
		lblNumero.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNumero.setForeground(new Color(0, 51, 102));
		lblNumero.setBounds(23, 260, 67, 14);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setEditable(false);
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNumero.setBounds(93, 258, 101, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		lblComplemento = new JLabel("Complemento:");
		lblComplemento.setForeground(new Color(0, 51, 102));
		lblComplemento.setFont(new Font("Verdana", Font.BOLD, 12));
		lblComplemento.setBounds(222, 260, 113, 14);
		getContentPane().add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setForeground(new Color(0, 51, 102));
		txtComplemento.setEditable(false);
		txtComplemento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtComplemento.setBounds(329, 258, 219, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(0, 51, 102));
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEmail.setBounds(42, 384, 46, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.-!#$%¨&*(_@1234567890";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEmail.setBounds(94, 382, 455, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);

		lblTelefone = new JLabel("*Telefone:");
		lblTelefone.setForeground(new Color(0, 51, 102));
		lblTelefone.setFont(new Font("Verdana", Font.BOLD, 12));
		lblTelefone.setBounds(10, 342, 76, 14);
		getContentPane().add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setEditable(false);
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// valida��o (aceita somente os caracteres da String)
				String caracteres = "0123456789 -()";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtTelefone.setBounds(94, 340, 165, 20);
		getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/add_button.png")));
		btnAdicionar.setToolTipText("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarClientes();
			}
		});
		btnAdicionar.setBounds(301, 593, 128, 128);
		getContentPane().add(btnAdicionar);

		btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete_button.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirClientes();
			}
		});
		btnExcluir.setBounds(696, 593, 128, 128);
		getContentPane().add(btnExcluir);

		btnAlterar = new JButton("");
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorderPainted(false);
		btnAlterar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update_button.png")));
		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setEnabled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarClientes();
			}
		});
		btnAlterar.setBounds(482, 593, 128, 128);
		getContentPane().add(btnAlterar);

		btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/clear_button.png")));
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}

		});
		btnLimpar.setBounds(1036, 593, 128, 128);
		getContentPane().add(btnLimpar);

		
		

		JLabel lblNewLabel = new JLabel("*CEP:");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setBounds(40, 175, 45, 13);
		getContentPane().add(lblNewLabel);

		txtCep = new JTextField();
		txtCep.setForeground(new Color(0, 51, 102));
		txtCep.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCep.setEditable(false);
		txtCep.setBounds(94, 171, 122, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		btnPesquisarCep = new JButton("");
		btnPesquisarCep.setEnabled(false);
		btnPesquisarCep.setBorderPainted(false);
		btnPesquisarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por Favor, Informe o CEP de CLiente!");
				} else {
					buscarCep();
				}
			}
		});
		btnPesquisarCep.setContentAreaFilled(false);
		btnPesquisarCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/Buscar48x48.png")));
		btnPesquisarCep.setToolTipText("Buscar CEP");
		btnPesquisarCep.setBounds(223, 158, 48, 48);
		getContentPane().add(btnPesquisarCep);

		lblNewLabel_1 = new JLabel("* Cidade:");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_1.setBounds(255, 304, 80, 13);
		getContentPane().add(lblNewLabel_1);

		txtCidade = new JTextField();
		txtCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz  ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCidade.setEditable(false);
		txtCidade.setBounds(330, 301, 219, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		lblNewLabel_2 = new JLabel("*Bairro:");
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_2.setForeground(new Color(0, 51, 102));
		lblNewLabel_2.setBounds(23, 303, 68, 13);
		getContentPane().add(lblNewLabel_2);

		txtBairro = new JTextField();
		txtBairro.setForeground(new Color(0, 51, 102));
		txtBairro.setEditable(false);
		txtBairro.setBounds(94, 300, 138, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] { "", "SP" }));
		cboUf.setBounds(473, 340, 76, 20);
		getContentPane().add(cboUf);

		JLabel lblUf = new JLabel("* UF:");
		lblUf.setFont(new Font("Verdana", Font.BOLD, 12));
		lblUf.setBackground(new Color(240, 240, 240));
		lblUf.setForeground(new Color(0, 51, 102));
		lblUf.setBounds(427, 343, 45, 13);
		getContentPane().add(lblUf);
		RestrictedTextField validar = new RestrictedTextField(txtNome);
		validar.setLimit(50);
		RestrictedTextField validar1 = new RestrictedTextField(txtTelefone);
		validar1.setLimit(15);
		RestrictedTextField validar2 = new RestrictedTextField(txtEndereco);
		validar2.setLimit(60);
		RestrictedTextField validar3 = new RestrictedTextField(txtEmail);
		validar3.setLimit(60);
		RestrictedTextField validar4 = new RestrictedTextField(txtComplemento);
		validar4.setLimit(20);
		RestrictedTextField validar5 = new RestrictedTextField(txtNumero);
		validar5.setLimit(5);
		RestrictedTextField validar6 = new RestrictedTextField(txtCep);
		validar6.setLimit(10);
		RestrictedTextField validar7 = new RestrictedTextField(txtCidade);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Clientes.class.getResource("/img/logo_cafe_consertor_vertical.png")));
		lblNewLabel_3.setBounds(572, 116, 200, 320);
		getContentPane().add(lblNewLabel_3);
		validar7.setLimit(20);
		
	} // Fim do construtor

	DAO dao = new DAO();
	private JScrollPane scrollPane;
	private JTextField txtCep;
	private JLabel lblNewLabel_1;
	private JTextField txtCidade;
	private JLabel lblNewLabel_2;
	private JTextField txtBairro;
	private JComboBox cboUf;
	private JButton btnPesquisarCep;
	private JLabel lblNewLabel_3;

	// MÉTODO RESPONSAVEL POR PROCURAR O CLIENTE NO BANCO DE DADOS.
	private void pesquisarClientes() {
		String read2 = "select idcli as ID, nomecliente as Nome, emailcliente as Email, fonecliente as Telefone from tbclientes where nomecliente like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtNome.getText() + "%");
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO MÉTODO PESQUISAR CLIENTE.

	// MÉTODO RESPONSÁVEL POR BUSCAR AS INFORMAÇÕES DO CLIENTE NO BANCO DE DADOS.
	private void buscarId() {

		if (txtId.getText().isEmpty()) {
			int confirma = JOptionPane.showConfirmDialog(null, "Cliente não encontrado: Deseja cadastrar um novo ?",
					"ATENÇÃO!", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				txtCep.setEditable(true);
				btnAdicionar.setEnabled(true);
				txtEndereco.setEditable(true);
				txtNumero.setEditable(true);
				txtComplemento.setEditable(true);
				txtEmail.setEditable(true);
				txtTelefone.setEditable(true);
				txtBairro.setEditable(true);
				txtCidade.setEditable(true);
				btnPesquisarCep.setEnabled(true);
				
				
			} else if (confirma == JOptionPane.NO_OPTION) {
				limpar();
			}

		} else {
			String read = "select * from tbclientes where idcli = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtId.getText() + "%");
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					txtNome.setText(rs.getString(2));
					txtCep.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(4));
					txtNumero.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(6));
					txtBairro.setText(rs.getString(7));
					txtCidade.setText(rs.getString(8));
					cboUf.setSelectedItem(rs.getString(9));
					txtEmail.setText(rs.getString(10));
					txtTelefone.setText(rs.getString(11));

					txtEndereco.setEditable(true);
					txtNumero.setEditable(true);
					txtComplemento.setEditable(true);
					txtEmail.setEditable(true);
					txtTelefone.setEditable(true);
					txtCep.setEditable(true);
					txtCidade.setEditable(true);
					
					btnAdicionar.setEnabled(false);
					btnAlterar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnLimpar.setEnabled(true);
					btnPesquisarCep.setEnabled(true);

				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// FIM DO MÉTODO PESQUISAR ID

	// MÉTODO RESPONSAVEL POR BUSCAR O CEP DO CLIENTE
	private void buscarCep() {

		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {

					} else {
						JOptionPane.showMessageDialog(null, "cep não encontrado");
					}
				}
			}
			// Setar o campo endereço
			txtEndereco.setText(tipoLogradouro + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO MÉTODO BUSCAR CEP

	// MÉTODO RESPONSÁVEL POR SETAR O CLIENTE
	private void setarCliente() {
		int setar = table.getSelectedRow();
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
	} // FIM DO MÉTODO SETAR O CLIENTE

	private void adicionarClientes() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o NOME do cliente!!!");
			txtNome.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o NUMERO DE TELEFONE do cliente!!!");
			txtTelefone.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o cep do cliente");
			txtCep.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o numero do endereço do cliente");
			txtNumero.requestFocus();
		} else {
			
			String create = "insert into tbclientes (nomecliente, cep, enderecocliente, numero, complemento, bairro, cidade, uf, emailcliente, fonecliente)value (?,?,?,?,?,?,?,?,?,?)";

			try {

				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(2, txtCep.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, cboUf.getSelectedItem().toString());
				pst.setString(9, txtEmail.getText());
				pst.setString(10, txtTelefone.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					int confirma2 = JOptionPane.showConfirmDialog(null, 
							"Cliente Cadastrado com SUCESSO! " + "Deseja cadastrar uma nova OS? ", "INFORMATIVO!",
							JOptionPane.YES_NO_OPTION);
					if (confirma2 == JOptionPane.YES_OPTION) {
						OrdemServico ordemservico = new OrdemServico();
						ordemservico.setVisible(true);
					} else if (confirma2 == JOptionPane.NO_OPTION) {
						limpar();
					}
					limpar();
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "ERRO!!! Preencha todos os campos");
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(null, "Email ou Número de telefone ja cadastrado no sistema !!");
				txtTelefone.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void atualizarClientes() {
		String update = "update tbclientes set nomecliente = ?, cep = ?, enderecocliente = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, emailcliente = ?, fonecliente = ?"
				+ "where idcli = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtCep.getText());
			pst.setString(3, txtEndereco.getText());
			pst.setString(4, txtNumero.getText());
			pst.setString(5, txtComplemento.getText());
			pst.setString(6, txtBairro.getText());
			pst.setString(7, txtCidade.getText());
			pst.setString(9, txtEmail.getText());
			pst.setString(8, cboUf.getSelectedItem().toString());
			pst.setString(10, txtTelefone.getText());
			pst.setString(11, txtId.getText());
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Dados do cliente alterados com SUCESSO!");
				limpar();
			} else {
				JOptionPane.showMessageDialog(null, "ERRO!!! Os dados não foram atualizados!");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void excluirClientes() {
		int confirma = JOptionPane.showConfirmDialog(null,
				"Deseja realmente deletar o cadastro do cliente do banco de dados??", "ATENÇÃO!!!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String excluir = "delete from tbclientes where idcli = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(excluir);
				pst.setString(1, txtId.getText());
				int deletado = pst.executeUpdate();
				if (deletado == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário apagados com SUCESSO!");
					limpar();
				} else {
					JOptionPane.showMessageDialog(null, "ERRO!!! Os dados do usuário não foram apagados");
				}
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"ERRO - Usuário não pode ser excluído pois está vinculado a uma OS ou Orçamento!");

			} catch (Exception e2) {
				System.out.println(e2);

			}

		}

	}

	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtEmail.setText(null);
		txtCep.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem("");
		txtTelefone.setText(null);
		
		
		txtEndereco.setEditable(false);
		txtNumero.setEditable(false);
		txtComplemento.setEditable(false);
		txtEmail.setEditable(false);
		txtTelefone.setEditable(false);
		txtCep.setEditable(false);
		txtBairro.setEditable(false);
		txtCidade.setEditable(false);

		((DefaultTableModel) table.getModel()).setRowCount(0);

		btnAdicionar.setEnabled(false);
		btnAlterar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisarCep.setEnabled(false);
	}
} // Fim do código