package view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Relatorios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnClientes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/reports_button.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Relatórios");
		setBounds(100, 100, 800, 650);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);

		btnClientes = new JButton("");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorderPainted(false);
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/client_report2.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setBounds(29, 37, 228, 228);
		getContentPane().add(btnClientes);

		JButton btnOsPendentes = new JButton("");
		btnOsPendentes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsPendentes.setBorderPainted(false);
		btnOsPendentes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/pendentes_report2.png")));
		btnOsPendentes.setToolTipText("OS Pendentes");
		btnOsPendentes.setContentAreaFilled(false);
		btnOsPendentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPendentes();
			}
		});
		btnOsPendentes.setBounds(338, 37, 228, 228);
		getContentPane().add(btnOsPendentes);

		JButton btnOsConcluidas = new JButton("");
		btnOsConcluidas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsConcluidas.setContentAreaFilled(false);
		btnOsConcluidas.setBorderPainted(false);
		btnOsConcluidas.setIcon(new ImageIcon(Relatorios.class.getResource("/img/concluido_report2.png")));
		btnOsConcluidas.setToolTipText("OS Concluídas");
		btnOsConcluidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioConcluidos();
			}
		});
		btnOsConcluidas.setBounds(29, 347, 228, 228);
		getContentPane().add(btnOsConcluidas);

		JLabel lblNewLabel = new JLabel("Clientes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(29, 277, 228, 23);
		getContentPane().add(lblNewLabel);

		JLabel lblOsPendentes = new JLabel("Pendentes");
		lblOsPendentes.setHorizontalAlignment(SwingConstants.CENTER);
		lblOsPendentes.setForeground(new Color(0, 51, 102));
		lblOsPendentes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblOsPendentes.setBounds(348, 277, 218, 23);
		getContentPane().add(lblOsPendentes);

		JLabel lblOsConcludas = new JLabel(" Concluídas");
		lblOsConcludas.setHorizontalAlignment(SwingConstants.CENTER);
		lblOsConcludas.setForeground(new Color(0, 51, 102));
		lblOsConcludas.setFont(new Font("Dialog", Font.BOLD, 16));
		lblOsConcludas.setBounds(29, 587, 228, 23);
		getContentPane().add(lblOsConcludas);

		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(Relatorios.class.getResource("/img/orcamento_report2.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioOrcamento();
			}
		});
		btnNewButton.setBounds(338, 347, 228, 228);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Orçamento");
		lblNewLabel_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setBounds(348, 582, 218, 23);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Relatorios.class.getResource("/img/logo_cafe_consertor_vertical.png")));
		lblNewLabel_2.setBounds(584, 179, 200, 320);
		getContentPane().add(lblNewLabel_2);

	} // Fim do construtor

	DAO dao = new DAO();

	// Método responsável pela impressão da relatórios de Clientes
	private void relatorioClientes() {
		// Teste
		// System.out.println("Teste");
		// Criar objeto para construir a p�gina pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// Gerar o documento pdf
		try {
			// Cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			// Gerar o conte�do do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add (adicionar um par�grafo)
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Relatório de Clientes"));
			document.add(new Paragraph(" "));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(5); // (5)colunas
			// Cabe�alho da tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Nome do Cliente"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Email"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Endereço"));
			tabela.addCell(col1); // .addCell(col11)adicionar uma c�lula
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			// Acessar o banco de dados
			String relClientes = "select idcli as ID, nomecliente as Nome, fonecliente as Telefone, emailcliente as Email, enderecocliente as Endereço from tbclientes";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				ResultSet rs = pst.executeQuery();
				// Enquanto houver dados na tabela do banco (obter valor) | While = Estrutura de
				// Repeticão infinita
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o, mesmo com erro
			document.close();
		}

		// Abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Método responsável pela impressão do relatório de OS Pendentes
	private void relatorioPendentes() {
		// Teste
		// System.out.println("Teste");
		// Criar objeto para construir a p�gina pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// Gerar o documento pdf
		try {
			// Cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("ospendentes.pdf"));
			document.open();
			// Gerar o conte�do do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add (adicionar um par�grafo)
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Relatório de OS Pendentes"));
			document.add(new Paragraph(" "));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(6); // (8)colunas
			// Cabe�alho da tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("Protocolo"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Equipamento"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Descrição"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Data de Abertura"));
			PdfPCell col6 = new PdfPCell(new Paragraph("Cliente"));
			tabela.addCell(col1); // .addCell(col11)adicionar uma c�lula
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);
			// Acessar o banco de dados
			String relPendentes = "select tbos.protocolo, tbos.statusos, tbos.tipoequipa, tbos.descricao, date_format(tbos.dataabe,'%d/%m/%Y - %H:%i') as data_protocolo, tbclientes.nomecliente from tbos inner join tbclientes on tbos.idcli = tbclientes.idcli where tbos.statusos = 'Aguardando peça' or tbos.statusos = 'Aguardando disponibilidade técnica'or tbos.statusos = 'Aguardando aprovação do cliente'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relPendentes);
				ResultSet rs = pst.executeQuery();
				// Enquanto houver dados na tabela do banco (obter valor) | While = Estrutura de
				// Repeticão infinita
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o, mesmo com erro
			document.close();
		}

		// Abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("ospendentes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Método responsável pela impressão do relatório de OS Concluídas
	private void relatorioConcluidos() {
		// Teste
		// System.out.println("Teste");
		// Criar objeto para construir a p�gina pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// Gerar o documento pdf
		try {
			// Cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("osconcluidas.pdf"));
			document.open();
			// Gerar o conte�do do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add (adicionar um par�grafo)
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Relatório de OS Concluídas"));
			document.add(new Paragraph(" "));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(7); // (5)colunas
			// Cabe�alho da tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("Protocolo"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Data de Abertura"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Nome do Cliente"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col6 = new PdfPCell(new Paragraph("Valor Pago"));
			PdfPCell col7 = new PdfPCell(new Paragraph("Laudo"));
			tabela.addCell(col1); // .addCell(col11)adicionar uma c�lula
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);
			tabela.addCell(col7);
			// Acessar o banco de dados
			String relConcluidas = "select tbos.protocolo, date_format(tbos.dataabe,'%d/%m/%Y - %H:%i') as data_protocolo, tbos.statusos as Status_OS,tbclientes.nomecliente as cliente, tbclientes.fonecliente as telefone, tbos.valor, tbos.laudo from tbos inner join tbclientes on tbos.idcli = tbclientes.idcli where tbos.statusos = 'Concluído'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relConcluidas);
				ResultSet rs = pst.executeQuery();
				// Enquanto houver dados na tabela do banco (obter valor) | While = Estrutura de
				// Repeticão infinita
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o, mesmo com erro
			document.close();
		}

		// Abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("osconcluidas.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Método responsável pela impressão do relatório de Orçamentos
	private void relatorioOrcamento() {
		// Teste
		// System.out.println("Teste");
		// Criar objeto para construir a p�gina pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// Gerar o documento pdf
		try {
			// Cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("osorcamento.pdf"));
			document.open();
			// Gerar o conte�do do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add (adicionar um par�grafo)
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Relatório de OS Orçamento"));
			document.add(new Paragraph(" "));
			// ... Demais conte�dos (imagem, tabela, gr�fico, etc)
			PdfPTable tabela = new PdfPTable(5);
			// Cabe�alho da tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("Protocolo"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Equipamento"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Descrição"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Cliente"));
			tabela.addCell(col1); // .addCell(col11)adicionar uma c�lula
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			// Acessar o banco de dados
			String relOrcamento = "select tbos.protocolo, tbos.tipoequipa, tbos.descricao, tbos.statusos, tbclientes.nomecliente from tbos inner join tbclientes on tbos.idcli = tbclientes.idcli where tipoabertura = 'Orçamento'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relOrcamento);
				ResultSet rs = pst.executeQuery();
				// Enquanto houver dados na tabela do banco (obter valor) | While = Estrutura de
				// Repeticão infinita
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o c�digo independente do resultado OK ou n�o, mesmo com erro
			document.close();
		}

		// Abrir o documento que foi gerado no leitor padr�o de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("osorcamento.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
} // Fim do código