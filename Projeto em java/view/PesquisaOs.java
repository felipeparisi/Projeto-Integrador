package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.Rectangle;

public class PesquisaOs extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTable table;
	private JTextField txtProtocolo;
	private JTextField txtTipoEquipa;
	private JTextField txtNomeUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PesquisaOs dialog = new PesquisaOs();
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
	public PesquisaOs() {
		setBounds(new Rectangle(1200, 5000, 0, 0));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PesquisaOs.class.getResource("/img/search_button.png")));
		setBackground(new Color(0, 0, 0));
		setTitle("Pesquisa de Ordem de Serviço");
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Nome do Cliente:");
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(24, 23, 146, 20);
		getContentPane().add(lblNewLabel);
		
		txtNome = new JTextField();
		txtNome.setBackground(Color.WHITE);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscarClientes();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtNome.setBounds(188, 18, 439, 25);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setIcon(new ImageIcon(PesquisaOs.class.getResource("/img/Buscar48x48.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProtocolo();
			}
		});
		btnNewButton.setBounds(932, 12, 48, 48);
		getContentPane().add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(188, 57, 738, 133);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCliente();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("Status do atendimento:");
		lblNewLabel_2.setForeground(new Color(0, 51, 102));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setBounds(24, 283, 213, 25);
		getContentPane().add(lblNewLabel_2);

		cboStatus = new JComboBox<Object>();
		cboStatus.setEditable(true);
		cboStatus.setEnabled(false);
		cboStatus.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {

			}
		});
		cboStatus.setModel(
				new DefaultComboBoxModel(new String[] {"", "Aguardando disponibilidade técnica", "Aguardando peça", "Aguardando aprovação do cliente", "Em andamento", "Concluído"}));
		cboStatus.setBounds(255, 283, 352, 25);
		getContentPane().add(cboStatus);

		dataAbe = new JDateChooser();
		dataAbe.setEnabled(false);
		dataAbe.setBackground(Color.LIGHT_GRAY);
		dataAbe.setBounds(730, 202, 201, 25);
		getContentPane().add(dataAbe);

		JLabel lblNewLabel_3 = new JLabel("Abertura:");
		lblNewLabel_3.setForeground(new Color(0, 51, 102));
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setBounds(613, 202, 99, 19);
		getContentPane().add(lblNewLabel_3);

		txtProtocolo = new JTextField();
		txtProtocolo.setBackground(Color.LIGHT_GRAY);
		txtProtocolo.setEditable(false);
		txtProtocolo.setBounds(785, 21, 135, 25);
		getContentPane().add(txtProtocolo);
		txtProtocolo.setColumns(10);

		JLabel lblProtocolo = new JLabel("Protocolo:");
		lblProtocolo.setFont(new Font("Dialog", Font.BOLD, 15));
		lblProtocolo.setForeground(new Color(0, 51, 102));
		lblProtocolo.setBounds(645, 23, 122, 18);
		getContentPane().add(lblProtocolo);

		JLabel lblNewLabel_7 = new JLabel("Tipo de equipamento:");
		lblNewLabel_7.setForeground(new Color(0, 51, 102));
		lblNewLabel_7.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_7.setBounds(24, 325, 213, 20);
		getContentPane().add(lblNewLabel_7);

		txtTipoEquipa = new JTextField();
		txtTipoEquipa.setBackground(Color.WHITE);
		txtTipoEquipa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtTipoEquipa.setEditable(false);
		txtTipoEquipa.setBounds(266, 320, 675, 25);
		getContentPane().add(txtTipoEquipa);
		txtTipoEquipa.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Descrição do defeito :");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setForeground(new Color(0, 51, 102));
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_8.setBounds(24, 367, 402, 20);
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Criado Por:");
		lblNewLabel_9.setForeground(new Color(0, 51, 102));
		lblNewLabel_9.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_9.setBounds(24, 251, 109, 20);
		getContentPane().add(lblNewLabel_9);

		txtNomeUser = new JTextField();
		txtNomeUser.setBackground(Color.WHITE);
		txtNomeUser.setEditable(false);
		txtNomeUser.setBounds(171, 246, 439, 25);
		getContentPane().add(txtNomeUser);
		txtNomeUser.setColumns(10);

		btnAlterar = new JButton("");
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorderPainted(false);
		btnAlterar.setIcon(new ImageIcon(PesquisaOs.class.getResource("/img/update_button2.png")));
		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setEnabled(false);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarOs();
			}
		});
		btnAlterar.setBounds(439, 590, 110, 110);
		getContentPane().add(btnAlterar);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(new ImageIcon(PesquisaOs.class.getResource("/img/clear_button2.png")));
		btnNewButton_1.setToolTipText("Limpar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnNewButton_1.setBounds(785, 590, 110, 110);
		getContentPane().add(btnNewButton_1);

		txtDescri = new JTextArea();
		txtDescri.setBackground(Color.LIGHT_GRAY);
		txtDescri.addKeyListener(new KeyAdapter() {

		});
		txtDescri.setEditable(false);
		txtDescri.setBounds(24, 393, 395, 116);
		getContentPane().add(txtDescri);

		txtLaudo = new JTextArea();
		txtLaudo.setEnabled(false);
		txtLaudo.setBackground(Color.LIGHT_GRAY);
		txtLaudo.setBounds(546, 393, 395, 116);
		getContentPane().add(txtLaudo);

		JLabel lblNewLabel_8_1 = new JLabel("Observações:");
		lblNewLabel_8_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_8_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_8_1.setBounds(546, 354, 395, 30);
		getContentPane().add(lblNewLabel_8_1);

		JLabel lblNewLabel_1 = new JLabel("Valor a ser pago:");
		lblNewLabel_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setBounds(24, 540, 160, 25);
		getContentPane().add(lblNewLabel_1);

		txtPagamento = new JTextField();
		txtPagamento.setEnabled(false);
		txtPagamento.setBackground(Color.WHITE);
		txtPagamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "1234567890., ";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtPagamento.setBounds(188, 540, 270, 25);
		getContentPane().add(txtPagamento);
		txtPagamento.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Status do Pagamento:");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_5.setForeground(new Color(0, 51, 102));
		lblNewLabel_5.setBounds(499, 540, 221, 25);
		getContentPane().add(lblNewLabel_5);

		cboStatusP = new JComboBox();
		cboStatusP.setEditable(true);
		cboStatusP.setEnabled(false);
		cboStatusP.setModel(new DefaultComboBoxModel(new String[] { "", "Pago", "Pendente" }));
		cboStatusP.setBounds(727, 540, 199, 25);
		getContentPane().add(cboStatusP);

		JLabel lblNewLabel_6 = new JLabel("Tipo abertura:");
		lblNewLabel_6.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_6.setForeground(new Color(0, 51, 102));
		lblNewLabel_6.setBounds(24, 197, 135, 30);
		getContentPane().add(lblNewLabel_6);

		cboAbertura = new JComboBox();
		cboAbertura.setEditable(true);
		cboAbertura.setEnabled(false);
		cboAbertura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (cboStatus.getSelectedItem().equals("Concluído")) {

				}
			}
		});
		cboAbertura.setModel(new DefaultComboBoxModel(new String[] {"", "Orçamento", "Ordem de Serviço"}));
		cboAbertura.setBounds(184, 201, 293, 25);
		getContentPane().add(cboAbertura);

		btnImprimirOs = new JButton("");
		btnImprimirOs.setEnabled(false);
		btnImprimirOs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimirOs.setContentAreaFilled(false);
		btnImprimirOs.setBorderPainted(false);
		btnImprimirOs.setToolTipText("Imprimir");
		btnImprimirOs.setIcon(new ImageIcon(PesquisaOs.class.getResource("/img/printer_report2.png")));
		btnImprimirOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOs();

			}
		});
		btnImprimirOs.setBounds(146, 590, 110, 110);
		getContentPane().add(btnImprimirOs);

		JLabel lblNewLabel_7_1 = new JLabel("");
		lblNewLabel_7_1.setIcon(new ImageIcon(PesquisaOs.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		lblNewLabel_7_1.setBounds(995, 58, 150, 150);
		getContentPane().add(lblNewLabel_7_1);
		setBounds(100, 100, 1200, 800);

	}// Fim do construror

	DAO dao = new DAO();
	private JComboBox<Object> cboStatus;
	private JDateChooser dataAbe;
	private JTextArea txtDescri;
	private JButton btnAlterar;
	private JTextArea txtLaudo;
	private JComboBox<Object> cboStatusP;
	private JTextField txtPagamento;
	private JComboBox<Object> cboAbertura;
	private JButton btnImprimirOs;

	private void buscarClientes() {
		String read = "select protocolo,nomecliente, dataabe as data_de_abertura, nomeuser as nome_usuario  from tbos where nomecliente like ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, txtNome.getText() + "%");
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			btnAlterar.setEnabled(true);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarCliente() {
		int setar = table.getSelectedRow();
		txtProtocolo.setText(table.getModel().getValueAt(setar, 0).toString());
	}

	private void buscarProtocolo() {

		if (txtProtocolo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Preencha o campo de pesquisa com o nome do cliente que deseja achar a OS");
			txtNome.requestFocus();
		} else {

			String read2 = "select * from tbos where protocolo = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read2);
				pst.setString(1, txtProtocolo.getText());

				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					String setarData = rs.getString(2);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					dataAbe.setDate(dataFormatada);

					String setarData2 = rs.getString(3);
					if (setarData2 == null) {

					} else {

					}
					// System.out.println(rs.getString(5));

					txtTipoEquipa.setText(rs.getString(3));
					cboAbertura.setSelectedItem(rs.getString(4));
					txtDescri.setText(rs.getString(5));
					txtLaudo.setText(rs.getString(6));
					cboStatus.setSelectedItem(rs.getString(7));
					txtNomeUser.setText(rs.getString(8));
					txtPagamento.setText(rs.getString(10));
					cboStatusP.setSelectedItem(rs.getString(11));

					cboAbertura.setEnabled(true);
					cboStatus.setEnabled(true);
					cboStatusP.setEnabled(true);

					btnImprimirOs.setEnabled(true);
					txtTipoEquipa.setEnabled(true);
					txtDescri.setEditable(true);
					txtLaudo.setEnabled(true);
					txtPagamento.setEnabled(true);

					recuperarOS();
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void alterarOs() {
		if (txtProtocolo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o Nome do cliente pelo campo de pesquisa");
			txtNome.requestFocus();
		} else {
			String read3 = "update tbos set descricao = ?, statusos = ?,laudo = ?, valor = ?, statuspag = ?, tipoabertura = ? "
					+ "where protocolo = ?";
			try {

				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read3);

				pst.setString(1, txtDescri.getText());
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtLaudo.getText());
				pst.setString(4, txtPagamento.getText());
				pst.setString(5, cboStatusP.getSelectedItem().toString());
				pst.setString(6, cboAbertura.getSelectedItem().toString());
				pst.setString(7, txtProtocolo.getText());
				
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Ordem de Serviço alterada com sucesso");
					
				} else {
					JOptionPane.showMessageDialog(null, "ERRO!!!");
				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
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

	private void limpar() {
		txtNome.setText(null);
		txtProtocolo.setText(null);
		((DefaultTableModel) table.getModel()).setRowCount(0);
		cboStatus.setSelectedItem("");
		cboAbertura.setSelectedItem("");
		txtNomeUser.setText(null);
		dataAbe.setDate(null);

		txtTipoEquipa.setText(null);
		txtDescri.setText(null);
		btnAlterar.setEnabled(false);
		cboStatusP.setSelectedItem("");
		txtPagamento.setText(null);
		txtPagamento.setEnabled(false);
		txtLaudo.setText(null);
		btnImprimirOs.setEnabled(false);
		cboAbertura.setEnabled(false);
		cboStatus.setEnabled(false);
		cboStatusP.setEnabled(false);
		
		
	}
}// Fim do código
