package br.senac.sp.projetopoo.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.senac.sp.projetopoo.dao.EMFactory;
import br.senac.sp.projetopoo.dao.InterfaceDao;
import br.senac.sp.projetopoo.dao.MarcaDaoHib;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.tablemodel.MarcaTableModel;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class FrameMarca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfNome;
	private Marca marca;
	private InterfaceDao<Marca> dao;
	private JFileChooser chooser;
	private FileFilter imageFilter;
	private JLabel lbLogo;
	private File selecionado;
	private JTable tbMarcas;
	private List<Marca> marcas;
	private MarcaTableModel tableModel;
	private JButton btnSalvar;
	private JTextField tfBusca;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameMarca frame = new FrameMarca();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public FrameMarca() {
		// dao = new MarcaDAO(ConnectionFactory.getConexao());
		dao = new MarcaDaoHib(EMFactory.getEntityManager());

		try {
			marcas = dao.listar();
		} catch (Exception  e) {
			JOptionPane.showMessageDialog(FrameMarca.this, "Erro: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		tableModel = new MarcaTableModel(marcas);

		chooser = new JFileChooser();
		imageFilter = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());

		setTitle("Cadastro de Marcas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 424, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 46, 17);
		contentPane.add(lblNewLabel);

		JLabel lblNome = new JLabel("NOME:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 45, 46, 17);
		contentPane.add(lblNome);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfId.setBounds(66, 11, 51, 20);
		contentPane.add(tfId);
		tfId.setColumns(10);

		tfNome = new JTextField();
		tfNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfNome.setColumns(10);
		tfNome.setBounds(66, 44, 267, 20);
		contentPane.add(tfNome);

		lbLogo = new JLabel("");
		lbLogo.setToolTipText("Clique para inserir uma imagem");
		lbLogo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					chooser.setFileFilter(imageFilter);
					if (chooser.showOpenDialog(FrameMarca.this) == JFileChooser.APPROVE_OPTION) {
						selecionado = chooser.getSelectedFile();
						try {
							BufferedImage bufImg = ImageIO.read(selecionado);
							Image imagem = bufImg.getScaledInstance(lbLogo.getWidth(), lbLogo.getHeight(),
									Image.SCALE_SMOOTH);
							ImageIcon imgLabel = new ImageIcon(imagem);
							lbLogo.setIcon(imgLabel);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});
		lbLogo.setBackground(UIManager.getColor("Button.background"));
		lbLogo.setBounds(343, 11, 51, 53);
		lbLogo.setOpaque(true);
		contentPane.add(lbLogo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfNome.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(FrameMarca.this, "Informe o nome", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					tfNome.requestFocus();
				} else {
					if (marca == null) {
						marca = new Marca();
					}
					marca.setNome(tfNome.getText().trim());
					try {
						if (selecionado != null) {
							byte[] imagemBytes = Files.readAllBytes(selecionado.toPath());
							marca.setLogo(imagemBytes);							
						}
						if (marca.getId() == 0) {
							dao.inserir(marca);
						} else {
							dao.alterar(marca);
						}
						marcas = dao.listar();
						tableModel.setLista(marcas);
						tableModel.fireTableDataChanged();
						limpar();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(FrameMarca.this, e1.getMessage(), "Erro",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		btnSalvar.setMnemonic('s');
		btnSalvar.setBounds(10, 72, 74, 29);
		contentPane.add(btnSalvar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (marca != null) {
					if (JOptionPane.showConfirmDialog(FrameMarca.this,
							"Deseja excluir a marca " + marca.getNome()) == JOptionPane.YES_OPTION) {
						try {
							dao.excluir(marca.getId());
							marcas = dao.listar();
							tableModel.setLista(marcas);
							tableModel.fireTableDataChanged();
							limpar();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(FrameMarca.this, "Selecione uma marca para excluí-la");
				}
			}
		});
		btnExcluir.setMnemonic('e');
		btnExcluir.setBounds(94, 72, 74, 29);
		contentPane.add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setMnemonic('l');
		btnLimpar.setBounds(178, 72, 74, 29);
		contentPane.add(btnLimpar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 112, 388, 310);
		contentPane.add(scrollPane);

		tbMarcas = new JTable(tableModel);
		tbMarcas.setToolTipText("Selecione um item para alterar ou excluir");
		tbMarcas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbMarcas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        int linha = tbMarcas.getSelectedRow();
		        if (linha >= 0) {
		            try {

		                marca = marcas.get(linha);
		                tfId.setText(String.valueOf(marca.getId()));
		                tfNome.setText(marca.getNome());

		                if (marca.getLogo() != null) {
		                    byte[] imagemBytes = marca.getLogo();
		                    ImageIcon icon = new ImageIcon(imagemBytes);
		                    Image img = icon.getImage().getScaledInstance(lbLogo.getWidth(), lbLogo.getHeight(), Image.SCALE_SMOOTH);
		                    lbLogo.setIcon(new ImageIcon(img));
		                } else {
		                    lbLogo.setIcon(null); 
		                }
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(FrameMarca.this, "Erro ao carregar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		                ex.printStackTrace();
		            }
		        }
		    }
		});
		
		tfBusca = new JTextField();
		tfBusca.setToolTipText("");
		tfBusca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfBusca.setBounds(283, 75, 115, 26); 
		contentPane.add(tfBusca);
		tfBusca.setColumns(10);

		tfBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    @Override
		    public void insertUpdate(javax.swing.event.DocumentEvent e) {
		        filtrarTabela();
		    }

		    @Override
		    public void removeUpdate(javax.swing.event.DocumentEvent e) {
		        filtrarTabela();
		    }

		    @Override
		    public void changedUpdate(javax.swing.event.DocumentEvent e) {
		        filtrarTabela();
		    }
		});

		scrollPane.setViewportView(tbMarcas);
		
		JLabel lbLupa = new JLabel("");
		lbLupa.setForeground(SystemColor.textHighlight);
		lbLupa.setBackground(SystemColor.textHighlight);
		lbLupa.setBounds(262, 75, 22, 26);
		lbLupa.setIcon(new ImageIcon(new ImageIcon("src/main/resources/lupa.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); 
		contentPane.add(lbLupa);
				
	}
	
	private void filtrarTabela() {
	    String termoBusca = tfBusca.getText().toLowerCase();
	    List<Marca> marcasFiltradas = marcas.stream()
	        .filter(marca -> marca.getNome().toLowerCase().contains(termoBusca)).toList();

	    tableModel.setLista(marcasFiltradas);
	    tableModel.fireTableDataChanged();
	}

	private void limpar() {
		tfId.setText("");
		tfNome.setText("");
		marca = null;
		lbLogo.setIcon(null);
		tfNome.requestFocus();
	}
}
