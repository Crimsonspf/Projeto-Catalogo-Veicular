package br.senac.sp.projetopoo.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInicial frame = new FrameInicial();
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
	public FrameInicial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 722);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 27, 36));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(124, 69, 249, 208);
		lblLogo.setIcon(new ImageIcon(new ImageIcon("src/main/resources/logo.png").getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH))); 
		contentPane.add(lblLogo);
		
		JButton btnMarcas = new JButton("Visualizar Marcas");
		btnMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameMarca telaMarca = new FrameMarca();
				telaMarca.setVisible(true);
			}
		});
		btnMarcas.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMarcas.setBounds(170, 361, 158, 41);
		contentPane.add(btnMarcas);
		
		JButton btnVeiculos = new JButton("Visualizar Veículos");
		btnVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameVeiculo telaVeiculo = new FrameVeiculo();
				telaVeiculo.setVisible(true);
			}
		});
		btnVeiculos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVeiculos.setBounds(170, 439, 158, 41);
		contentPane.add(btnVeiculos);
	}

}
