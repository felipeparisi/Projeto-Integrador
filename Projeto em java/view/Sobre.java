package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		setResizable(false);
		getContentPane().setForeground(new Color(255, 255, 255));
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/Sobre64x64.png")));
		setTitle("Sobre");
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 700, 500);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("App criado para projeto do curso Técnico Do SENAC Tatuapé");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 192, 684, 42);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Equipe Responsável pelo desenvlvimento:");
		lblNewLabel_1.setForeground(new Color(0, 51, 102));
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 245, 684, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Higor Ribeiro");
		lblNewLabel_2.setForeground(new Color(0, 51, 102));
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(0, 274, 684, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Giovanna Gil");
		lblNewLabel_3.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_3.setForeground(new Color(0, 51, 102));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 299, 684, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Felipe Parisi");
		lblNewLabel_4.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(new Color(0, 51, 102));
		lblNewLabel_4.setBounds(0, 325, 684, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setIcon(new ImageIcon(Sobre.class.getResource("/img/licensemit.png")));
		lblNewLabel_5.setBounds(549, 363, 113, 87);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Licença:");
		lblNewLabel_6.setFont(new Font("Verdana", Font.BOLD, 13));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(new Color(0, 51, 102));
		lblNewLabel_6.setBounds(549, 350, 113, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Versão 1.2 - 27/03/2023");
		lblNewLabel_7.setForeground(new Color(0, 51, 102));
		lblNewLabel_7.setFont(new Font("Verdana", Font.ITALIC, 12));
		lblNewLabel_7.setBounds(20, 418, 203, 23);
		getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setIcon(new ImageIcon(Sobre.class.getResource("/img/logo_cafe_consertos_pequeno.png")));
		lblNewLabel_8.setBounds(0, 27, 684, 150);
		getContentPane().add(lblNewLabel_8);

	}
}
