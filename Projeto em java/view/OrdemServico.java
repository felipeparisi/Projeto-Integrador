package view;

import java.awt.Cursor;
import java.awt.Desktop;
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
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jgoodies.forms.layout.CellConstraints.Alignment;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class OrdemServico extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txtNomeCli;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtIdUser;
	private JTextField txtNomeUser;
	private JTextField txtProtocolo;
	private JTextField txtTipoEquipamento;
	private JTextField txtIdCli;
	private JScrollPane scrollPane;
	private JComboBox<?> cboStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrdemServico dialog = new OrdemServico();
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
	public OrdemServico() {
		setResizable(false);
		setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
		setForeground(new Color(0, 51, 102));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(OrdemServico.class.getResource("/img/report_button.png")));
		getContentPane().setBackground(Color.WHITE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 15));
		setTitle("Abertura de Ordem de Serviço");
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBounds(450, 50, 1200, 800);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("*Nome Cliente:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(17, 15, 140, 23);
		getContentPane().add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {

		});
		scrollPane.setBounds(175, 58, 811, 85);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Verdana", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCliente();
			}
		});
		scrollPane.setViewportView(table);

		txtNomeCli = new JTextField();
		txtNomeCli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscarCliente();
			}
		});
		txtNomeCli.setBounds(175, 13, 521, 25);
		getContentPane().add(txtNomeCli);
		txtNomeCli.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("*Endereço:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setBounds(285, 233, 109, 19);
		getContentPane().add(lblNewLabel_1);

		txtEndereco = new JTextField();
		txtEndereco.setEditable(false);
		txtEndereco.setBounds(393, 230, 397, 25);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Número:");
		lblNewLabel_2.setForeground(new Color(0, 51, 102));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setBounds(808, 233, 93, 18);
		getContentPane().add(lblNewLabel_2);

		txtNumero = new JTextField();
		txtNumero.setEditable(false);
		txtNumero.setBounds(919, 230, 158, 25);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Complemento:");
		lblNewLabel_3.setForeground(new Color(0, 51, 102));
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setBounds(17, 270, 140, 23);
		getContentPane().add(lblNewLabel_3);

		txtComplemento = new JTextField();
		txtComplemento.setEditable(false);
		txtComplemento.setBounds(156, 269, 411, 25);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("*ID User:");
		lblNewLabel_4.setForeground(new Color(0, 51, 102));
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_4.setBounds(12, 191, 93, 28);
		getContentPane().add(lblNewLabel_4);

		txtIdUser = new JTextField();
		txtIdUser.setEnabled(false);
		txtIdUser.setBounds(123, 193, 172, 25);
		getContentPane().add(txtIdUser);
		txtIdUser.setColumns(10);

		btnBuscarUser = new JButton("");
		btnBuscarUser.setContentAreaFilled(false);
		btnBuscarUser.setBorderPainted(false);
		btnBuscarUser.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/Buscar48x48.png")));
		btnBuscarUser.setToolTipText("Buscar User");
		btnBuscarUser.setEnabled(false);
		btnBuscarUser.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnBuscarUser.setForeground(new Color(0, 51, 102));
		btnBuscarUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarIdUser();
			}
		});
		btnBuscarUser.setBounds(337, 173, 48, 48);
		getContentPane().add(btnBuscarUser);

		txtNomeUser = new JTextField();
		txtNomeUser.setEditable(false);
		txtNomeUser.setBounds(435, 193, 642, 25);
		getContentPane().add(txtNomeUser);
		txtNomeUser.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Protocolo:");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_5.setForeground(new Color(0, 51, 102));
		lblNewLabel_5.setBounds(675, 304, 124, 28);
		getContentPane().add(lblNewLabel_5);

		txtProtocolo = new JTextField();
		txtProtocolo.setEditable(false);
		txtProtocolo.setBounds(796, 306, 281, 25);
		getContentPane().add(txtProtocolo);
		txtProtocolo.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("*Tipo de Equipamento/Marca:");
		lblNewLabel_6.setForeground(new Color(0, 51, 102));
		lblNewLabel_6.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_6.setBounds(17, 343, 315, 19);
		getContentPane().add(lblNewLabel_6);

		txtTipoEquipamento = new JTextField();
		txtTipoEquipamento.setEnabled(false);
		txtTipoEquipamento.setBounds(323, 343, 754, 25);
		getContentPane().add(txtTipoEquipamento);
		txtTipoEquipamento.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("*Status:");
		lblNewLabel_8.setForeground(new Color(0, 51, 102));
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_8.setBounds(17, 309, 77, 18);
		getContentPane().add(lblNewLabel_8);

		cboStatus = new JComboBox();
		cboStatus.setEditable(true);
		cboStatus.setEnabled(false);
		cboStatus.setModel(new DefaultComboBoxModel(new String[] { "", "Aguardando disponibilidade técnica",
				"Aguardando peça", "Aguardando aprovação do cliente", "Em andamento", "Concluído" }));
		cboStatus.setBounds(171, 306, 397, 25);
		getContentPane().add(cboStatus);

		JLabel lblNewLabel_9 = new JLabel("*Descrição:");
		lblNewLabel_9.setForeground(new Color(0, 51, 102));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_9.setBounds(60, 379, 435, 19);
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_11 = new JLabel("ID:");
		lblNewLabel_11.setBackground(new Color(255, 255, 255));
		lblNewLabel_11.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_11.setForeground(new Color(0, 51, 102));
		lblNewLabel_11.setBounds(796, 18, 48, 14);
		getContentPane().add(lblNewLabel_11);

		txtIdCli = new JTextField();
		txtIdCli.setEditable(false);
		txtIdCli.setBounds(877, 13, 109, 25);
		getContentPane().add(txtIdCli);
		txtIdCli.setColumns(10);

		btnCriarOs = new JButton("");
		btnCriarOs.setContentAreaFilled(false);
		btnCriarOs.setBorderPainted(false);
		btnCriarOs.setToolTipText("Adicionar");
		btnCriarOs.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/add_button2.png")));
		btnCriarOs.setEnabled(false);
		btnCriarOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criarOs();
			}
		});
		btnCriarOs.setBounds(138, 587, 110, 110);
		getContentPane().add(btnCriarOs);

		btnNewButton_4 = new JButton("");
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/clear_button2.png")));
		btnNewButton_4.setToolTipText("Limpar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton_4.setBounds(702, 587, 110, 110);
		getContentPane().add(btnNewButton_4);

		btnBuscarCli = new JButton("");
		btnBuscarCli.setContentAreaFilled(false);
		btnBuscarCli.setBorderPainted(false);
		btnBuscarCli.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/Buscar48x48.png")));
		btnBuscarCli.setToolTipText("Buscar");
		btnBuscarCli.setForeground(new Color(0, 51, 102));
		btnBuscarCli.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnBuscarCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarIdCliente();
			}
		});
		btnBuscarCli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		btnBuscarCli.setBounds(730, 0, 48, 48);
		getContentPane().add(btnBuscarCli);

		getRootPane().setDefaultButton(btnBuscarCli);

		txtDescricao = new JTextArea();
		txtDescricao.setEnabled(false);
		txtDescricao.setBackground(Color.LIGHT_GRAY);
		txtDescricao.setBounds(60, 400, 435, 110);
		getContentPane().add(txtDescricao);

		txtLaudo = new JTextArea();
		txtLaudo.setEnabled(false);
		txtLaudo.setBackground(Color.LIGHT_GRAY);
		txtLaudo.setBounds(644, 400, 435, 110);
		getContentPane().add(txtLaudo);

		JLabel lblNewLabel_12 = new JLabel("*Observações");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_12.setForeground(new Color(0, 51, 102));
		lblNewLabel_12.setBounds(644, 375, 435, 23);
		getContentPane().add(lblNewLabel_12);

		btnImprimir = new JButton("");
		btnImprimir.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/printer_report2.png")));
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setContentAreaFilled(false);
		btnImprimir.setBorderPainted(false);
		btnImprimir.setEnabled(false);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOs();
			}

		});
		btnImprimir.setBounds(423, 587, 110, 110);
		getContentPane().add(btnImprimir);

		JLabel lblNewLabel_13 = new JLabel("*Valor a ser Pago:");
		lblNewLabel_13.setForeground(new Color(0, 51, 102));
		lblNewLabel_13.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_13.setBounds(17, 536, 172, 23);
		getContentPane().add(lblNewLabel_13);

		txtPagamento = new JTextField();
		txtPagamento.setEnabled(false);
		txtPagamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789., ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtPagamento.setBounds(195, 535, 300, 25);
		getContentPane().add(txtPagamento);
		txtPagamento.setColumns(10);

		JLabel lblNewLabel_14 = new JLabel("*Status do Pagamento:");
		lblNewLabel_14.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_14.setForeground(new Color(0, 51, 102));
		lblNewLabel_14.setBounds(544, 538, 243, 19);
		getContentPane().add(lblNewLabel_14);

		cboStatusPagamento = new JComboBox();
		cboStatusPagamento.setEnabled(false);
		cboStatusPagamento.setEditable(true);
		cboStatusPagamento.setModel(new DefaultComboBoxModel(new String[] { "", "Pago", "Pendente" }));
		cboStatusPagamento.setBounds(805, 535, 274, 25);
		getContentPane().add(cboStatusPagamento);

		txtBairro = new JTextField();
		txtBairro.setEditable(false);
		txtBairro.setBounds(702, 267, 373, 25);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		txtCep = new JTextField();
		txtCep.setEditable(false);
		txtCep.setBounds(60, 230, 207, 25);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		JLabel lblNewLabel_15 = new JLabel("CEP:");
		lblNewLabel_15.setForeground(new Color(0, 51, 102));
		lblNewLabel_15.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_15.setBounds(12, 231, 59, 23);
		getContentPane().add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel("Bairro:");
		lblNewLabel_16.setForeground(new Color(0, 51, 102));
		lblNewLabel_16.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_16.setBounds(597, 271, 70, 17);
		getContentPane().add(lblNewLabel_16);

		cboAbertura = new JComboBox();
		cboAbertura.setEditable(true);
		cboAbertura.setEnabled(false);
		cboAbertura.setModel(new DefaultComboBoxModel(new String[] { "", "Orçamento", "Ordem de Serviço " }));
		cboAbertura.setBounds(397, 156, 210, 25);
		getContentPane().add(cboAbertura);

		JLabel lblNewLabel_17 = new JLabel("Selecione Orçamento ou Ordem de Serviço:");
		lblNewLabel_17.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_17.setForeground(new Color(0, 51, 102));
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_17.setBounds(12, 156, 443, 23);
		getContentPane().add(lblNewLabel_17);

		RestrictedTextField validar = new RestrictedTextField(txtNomeCli);
		validar.setLimit(50);
		RestrictedTextField validar1 = new RestrictedTextField(txtEndereco);
		validar1.setLimit(50);
		RestrictedTextField validar2 = new RestrictedTextField(txtNumero);
		validar2.setLimit(5);
		RestrictedTextField validar3 = new RestrictedTextField(txtComplemento);
		validar3.setLimit(10);
		RestrictedTextField validar4 = new RestrictedTextField(txtTipoEquipamento);
		validar4.setLimit(30);
		RestrictedTextField validar6 = new RestrictedTextField(txtPagamento);

		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		lblNewLabel_7.setBounds(1038, 15, 150, 150);
		getContentPane().add(lblNewLabel_7);

		btnDeletar = new JButton("");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();

			}
		});
		btnDeletar.setIcon(new ImageIcon(OrdemServico.class.getResource("/img/delete_button2.png")));
		btnDeletar.setToolTipText("Deletar");
		btnDeletar.setEnabled(false);
		btnDeletar.setContentAreaFilled(false);
		btnDeletar.setBorderPainted(false);
		btnDeletar.setBounds(974, 587, 110, 110);
		getContentPane().add(btnDeletar);
		validar6.setLimit(6);

	} // Fim do construtor

	DAO dao = new DAO();
	private JButton btnCriarOs;
	private JButton btnNewButton_4;
	private JTextArea txtDescricao;
	private JTextField txtPagamento;
	private JTextField txtBairro;
	private JTextField txtCep;
	private JTextArea txtLaudo;
	private JComboBox cboStatusPagamento;
	private JButton btnImprimir;
	private JComboBox cboAbertura;
	private JButton btnBuscarUser;
	private JButton btnBuscarCli;
	private JLabel lblNewLabel_7;
	private JButton btnDeletar;

	private void buscarCliente() {
		String read1 = "select idcli as ID, nomecliente as Nome, emailcliente as Email, fonecliente as Fone from tbclientes where nomecliente like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read1);
			pst.setString(1, txtNomeCli.getText() + "%");
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarCliente() {
		int setar = table.getSelectedRow();
		txtIdCli.setText(table.getModel().getValueAt(setar, 0).toString());
	}

	private void buscarIdCliente() {

		/*
		 * Pesquisar como fazer para abrir a tela de clientes ao apertar Sim if
		 * (txtNomeCli.getText().isEmpty()) { int confirma =
		 * JOptionPane.showConfirmDialog(null,
		 * "Cliente não encontrado: Deseja cadastrar um novo?"); if (confirma ==
		 * JOptionPane.YES_OPTION) {
		 * 
		 * } }
		 */
		if (txtIdCli.getText().isEmpty()) {
			int confirma = JOptionPane.showConfirmDialog(null, "Cliente não encontrado, deseja cadastrar um novo ?",
					"ATENÇÃO!!!", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			} else if (confirma == JOptionPane.NO_OPTION) {
				btnCriarOs.setEnabled(true);
				limpar();
			}
		} else {

			cboStatus.setEnabled(true);
			cboAbertura.setEnabled(true);
			cboStatusPagamento.setEnabled(true);

			btnCriarOs.setEnabled(true);
			btnBuscarUser.setEnabled(true);

			txtDescricao.setEnabled(true);
			txtLaudo.setEnabled(true);
			txtIdUser.setEnabled(true);
			txtTipoEquipamento.setEnabled(true);
			txtPagamento.setEnabled(true);

			String read2 = "select * from tbclientes where idcli = ?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read2);
				pst.setString(1, txtIdCli.getText() + "%");
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtNomeCli.setText(rs.getString(2));
					txtCep.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(4));
					txtNumero.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(6));
					txtBairro.setText(rs.getString(7));

				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarIdUser() {
		String read3 = "select * from tbusuarios where iduser = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtIdUser.getText() + "%");
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtNomeUser.setText(rs.getString(2));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void criarOs() {

		String insert = "insert into tbos (tipoequipa,tipoabertura, descricao,nomeuser,nomecliente,cep, enderecocliente, "
				+ "numero, complemento, statusos, iduser, idcli, valor, laudo, statuspag)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(insert);

			pst.setString(1, txtTipoEquipamento.getText());
			pst.setString(2, cboAbertura.getSelectedItem().toString());
			pst.setString(3, txtDescricao.getText());
			pst.setString(4, txtNomeUser.getText());
			pst.setString(5, txtNomeCli.getText());
			pst.setString(6, txtCep.getText());
			pst.setString(7, txtEndereco.getText());
			pst.setString(8, txtNumero.getText());
			pst.setString(9, txtComplemento.getText());
			pst.setString(10, cboStatus.getSelectedItem().toString());
			pst.setString(11, txtIdUser.getText());
			pst.setString(12, txtIdCli.getText());
			pst.setString(13, txtPagamento.getText());
			pst.setString(14, txtLaudo.getText());
			pst.setString(15, cboStatusPagamento.getSelectedItem().toString());

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Ordem de Serviço criada com sucesso!");
				recuperarOS();
				btnImprimir.setEnabled(true);
				btnCriarOs.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "ERRO!");
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	private void imprimirOs() {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream("OS.pdf"));
			document.open();

			/*------------MODO PARA INSERIR O LOGO NA IMPRESSÃO------------------
			 * 
			 * */
			Image imagem = Image.getInstance(PesquisaOs.class.getResource("/img/logo_cafe_consertos_pequeno.png"));
			imagem.scaleToFit(200, 110);
			imagem.setAbsolutePosition(444, 720);
			document.add(imagem);

			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Ordem de Serviço"));
			document.add(new Paragraph(" "));

			String imprimirOs = "select protocolo, nomecliente, nomeuser, dataabe, tipoabertura, statusos, tipoequipa, laudo, descricao, valor, statuspag from tbos where protocolo = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(imprimirOs);
				pst.setString(1, txtProtocolo.getText());
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {

					document.add(new Paragraph(" "));
					document.add(new Paragraph(
							"---------------------------------------------------------------------------------------------------------------------------------"));
					Paragraph ordemdeservico = new Paragraph("Numero do Protocolo: " + rs.getString(1));
					ordemdeservico.setAlignment(Element.ALIGN_CENTER);
					document.add(ordemdeservico);
					document.add(new Paragraph(
							"---------------------------------------------------------------------------------------------------------------------------------"));
					document.add(new Paragraph(""));
					//

					Paragraph situacao = new Paragraph("Nome do Cliente:  " + rs.getString(2));
					situacao.setAlignment(Element.ALIGN_CENTER);
					document.add(situacao);

					//
					document.add(new Paragraph(" "));
					Paragraph processador = new Paragraph("Nome do Colaborador: " + rs.getString(3));
					processador.setAlignment(Element.ALIGN_LEFT);
					document.add(processador);
					//
					document.add(new Paragraph(" "));
					Paragraph memoria = new Paragraph("Data de Abertura: " + rs.getString(4));
					memoria.setAlignment(Element.ALIGN_LEFT);
					document.add(memoria);
					//
					document.add(new Paragraph(" "));
					Paragraph placa = new Paragraph("Tipo de Abertura (Os ou Orçamento): " + rs.getString(5));
					placa.setAlignment(Element.ALIGN_LEFT);
					document.add(placa);
					//
					document.add(new Paragraph(" "));
					Paragraph refri = new Paragraph("Status do Processo: " + rs.getString(6));
					refri.setAlignment(Element.ALIGN_LEFT);
					document.add(refri);
					//
					document.add(new Paragraph(" "));
					Paragraph video = new Paragraph("Tipo de Equipamento: " + rs.getString(7));
					video.setAlignment(Element.ALIGN_LEFT);
					document.add(video);
					//
					document.add(new Paragraph(" "));
					Paragraph fan = new Paragraph("Observações: " + rs.getString(8));
					fan.setAlignment(Element.ALIGN_LEFT);
					document.add(fan);
					//
					document.add(new Paragraph(" "));
					Paragraph gabinete = new Paragraph("Descrição do Problema: " + rs.getString(9));
					gabinete.setAlignment(Element.ALIGN_LEFT);
					document.add(gabinete);
					//
					document.add(new Paragraph(" "));
					Paragraph fonte = new Paragraph("Valor: R$" + rs.getString(10));
					fonte.setAlignment(Element.ALIGN_LEFT);
					document.add(fonte);
					//
					document.add(new Paragraph(" "));
					Paragraph obs = new Paragraph("Status de Pagamento: " + rs.getString(11));
					obs.setAlignment(Element.ALIGN_LEFT);
					document.add(obs);
					document.add(new Paragraph(" "));

					//
					document.add(new Paragraph(" "));
					Paragraph ass = new Paragraph(
							"Assinatura do Cliente: ______________________________ Data de Fechamento:___/___/___ ");
					obs.setAlignment(Element.ALIGN_RIGHT);
					document.add(ass);
					document.add(new Paragraph(" "));

				}

			} catch (Exception e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			document.close();
		}

		try {
			Desktop.getDesktop().open(new File("OS.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void recuperarOS() {
		String readOS = "select max(protocolo) from tbos";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(readOS);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtProtocolo.setText(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void deletar() {
		
	}

	private void limpar() {

		((DefaultTableModel) table.getModel()).setRowCount(0);

		txtTipoEquipamento.setText(null);
		txtDescricao.setText(null);
		txtNomeUser.setText(null);
		txtNomeCli.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		cboStatus.setSelectedItem("");
		txtIdUser.setText(null);
		txtIdCli.setText(null);
		txtLaudo.setText(null);
		txtPagamento.setText(null);
		txtIdUser.setText(null);

		txtDescricao.setEditable(false);
		txtLaudo.setEditable(false);
		txtIdUser.setEnabled(false);
		txtTipoEquipamento.setEnabled(false);
		txtPagamento.setEnabled(false);

		btnCriarOs.setEnabled(false);

		cboStatusPagamento.setSelectedItem("");
		cboAbertura.setSelectedItem("");

		cboAbertura.setEnabled(false);
		cboStatus.setEnabled(false);
		cboStatusPagamento.setEnabled(false);

	}
} // Fim do código
