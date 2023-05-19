package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblUsuario;
	private JLabel lblUser;
	JButton btnUsuarios;
	private JButton btnNewButton;
	private JButton btnClientes;
	private JButton btnNewButton_1;
	private JButton btnReletorios;
	private JLabel lblRodape;
	private JPanel panelUsuario;
	private JTextField txtUsurios;
	private JTextField txtAbrirOs;
	private JTextField txtRelatrios;
	private JTextField txtCadClientes;
	private JTextField txtPe;
	private JTextField txtSobre;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				lblRodape.setText(formatador.format(data));
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Café Consertos");
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBounds(100, 100, 1600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setIcon(new ImageIcon(Main.class.getResource("/img/user_button2.png")));
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios ();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setBounds(40, 57, 228, 228);
		getContentPane().add(btnUsuarios);
		
		btnClientes = new JButton("");
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorderPainted(false);
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Main.class.getResource("/img/client_button2.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes ();
				clientes.setVisible(true);
			}
		});
		btnClientes.setBounds(40, 382, 228, 228);
		getContentPane().add(btnClientes);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setIcon(new ImageIcon(Main.class.getResource("/img/report_button2.png")));
		btnNewButton_2.setToolTipText("Ordem de Serviço");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrdemServico ordemservico = new OrdemServico ();
				ordemservico.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(434, 57, 228, 228);
		getContentPane().add(btnNewButton_2);
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/img/about_button2.png")));
		btnNewButton.setToolTipText("Sobre");
		btnNewButton.setBounds(831, 382, 228, 228);
		getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setToolTipText("Pesquisar OS");
		btnNewButton_1.setIcon(new ImageIcon(Main.class.getResource("/img/search_button2.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PesquisaOs pesquisaos = new PesquisaOs();
				pesquisaos.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(434, 382, 228, 228);
		getContentPane().add(btnNewButton_1);
		
		btnReletorios = new JButton("");
		btnReletorios.setContentAreaFilled(false);
		btnReletorios.setBorderPainted(false);
		btnReletorios.setToolTipText("Relatórios");
		btnReletorios.setIcon(new ImageIcon(Main.class.getResource("/img/reports_button2.png")));
		btnReletorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios ();
				relatorios.setVisible(true);
				
			}
			
		});
		btnReletorios.setBounds(831, 57, 228, 228);
		getContentPane().add(btnReletorios);
		
		panelUsuario = new JPanel();
		panelUsuario.setBackground(new Color(0, 51, 102));
		panelUsuario.setBounds(0, 722, 1600, 50);
		getContentPane().add(panelUsuario);
		panelUsuario.setLayout(null);
		
		lblUser = new JLabel("Usuário:");
		lblUser.setBounds(25, 18, 82, 14);
		panelUsuario.add(lblUser);
		lblUser.setFont(new Font("Verdana", Font.BOLD, 12));
		lblUser.setForeground(new Color(255, 255, 255));
		
		lblUsuario = new JLabel("");
		lblUsuario.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblUsuario.setBounds(88, 18, 282, 14);
		panelUsuario.add(lblUsuario);
		lblUsuario.setForeground(new Color(255, 255, 255));
		
		lblRodape = new JLabel("New label");
		lblRodape.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblRodape.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRodape.setForeground(SystemColor.window);
		lblRodape.setBounds(1329, 20, 259, 13);
		panelUsuario.add(lblRodape);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(380, 18, 107, 14);
		panelUsuario.add(lblNewLabel);
		
		txtUsurios = new JTextField();
		txtUsurios.setBorder(null);
		txtUsurios.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsurios.setForeground(new Color(0, 51, 102));
		txtUsurios.setText("Usuários");
		txtUsurios.setFont(new Font("Dialog", Font.BOLD, 20));
		txtUsurios.setBounds(95, 304, 128, 66);
		getContentPane().add(txtUsurios);
		txtUsurios.setColumns(10);
		
		txtAbrirOs = new JTextField();
		txtAbrirOs.setText("Criar OS");
		txtAbrirOs.setHorizontalAlignment(SwingConstants.CENTER);
		txtAbrirOs.setForeground(new Color(0, 51, 102));
		txtAbrirOs.setFont(new Font("Dialog", Font.BOLD, 20));
		txtAbrirOs.setColumns(10);
		txtAbrirOs.setBorder(null);
		txtAbrirOs.setBounds(485, 307, 128, 66);
		getContentPane().add(txtAbrirOs);
		
		txtRelatrios = new JTextField();
		txtRelatrios.setText("Relatórios");
		txtRelatrios.setHorizontalAlignment(SwingConstants.CENTER);
		txtRelatrios.setForeground(new Color(0, 51, 102));
		txtRelatrios.setFont(new Font("Dialog", Font.BOLD, 20));
		txtRelatrios.setColumns(10);
		txtRelatrios.setBorder(null);
		txtRelatrios.setBounds(881, 297, 128, 66);
		getContentPane().add(txtRelatrios);
		
		txtCadClientes = new JTextField();
		txtCadClientes.setText("Cad. Clientes");
		txtCadClientes.setHorizontalAlignment(SwingConstants.CENTER);
		txtCadClientes.setForeground(new Color(0, 51, 102));
		txtCadClientes.setFont(new Font("Dialog", Font.BOLD, 20));
		txtCadClientes.setColumns(10);
		txtCadClientes.setBorder(null);
		txtCadClientes.setBounds(71, 642, 152, 52);
		getContentPane().add(txtCadClientes);
		
		txtPe = new JTextField();
		txtPe.setText("Pesquisa da OS");
		txtPe.setHorizontalAlignment(SwingConstants.CENTER);
		txtPe.setForeground(new Color(0, 51, 102));
		txtPe.setFont(new Font("Dialog", Font.BOLD, 18));
		txtPe.setColumns(10);
		txtPe.setBorder(null);
		txtPe.setBounds(461, 642, 180, 52);
		getContentPane().add(txtPe);
		
		txtSobre = new JTextField();
		txtSobre.setText("Sobre");
		txtSobre.setHorizontalAlignment(SwingConstants.CENTER);
		txtSobre.setForeground(new Color(0, 51, 102));
		txtSobre.setFont(new Font("Dialog", Font.BOLD, 20));
		txtSobre.setColumns(10);
		txtSobre.setBorder(null);
		txtSobre.setBounds(881, 642, 128, 52);
		getContentPane().add(txtSobre);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Main.class.getResource("/img/logo_cafe_consertor_vertical.png")));
		lblNewLabel_1.setBounds(1279, 226, 200, 320);
		getContentPane().add(lblNewLabel_1);

	}
}
