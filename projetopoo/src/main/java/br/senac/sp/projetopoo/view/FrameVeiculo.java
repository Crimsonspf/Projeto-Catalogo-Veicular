package br.senac.sp.projetopoo.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import br.senac.sp.projetopoo.dao.EMFactory;
import br.senac.sp.projetopoo.dao.InterfaceDao;
import br.senac.sp.projetopoo.dao.VeiculoDaoHib;
import br.senac.sp.projetopoo.modelo.Marca;
import br.senac.sp.projetopoo.modelo.Veiculo;
import br.senac.sp.projetopoo.tablemodel.VeiculoTableModel;

import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import br.senac.sp.projetopoo.dao.MarcaDaoHib;

public class FrameVeiculo extends JFrame {

	private JPanel contentPane;
	private JTextField tfId, tfModelo, tfPreco, tfAno, tfKm, tfCombustivel;
	private JComboBox<Marca> cbMarca;
	private InterfaceDao<Veiculo> veiculoDao;
	private List<Veiculo> veiculos;
	private JFileChooser chooser;
	private FileFilter imageFilter;
	private File selecionado;
	private JTable tbVeiculos;
	private Veiculo veiculo;
	private JLabel lbImagem;
	private JTextField tfBusca;
	private VeiculoTableModel tableModel;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameVeiculo frame = new FrameVeiculo();
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
	public FrameVeiculo() {
		setForeground(new Color(21, 27, 36));
		setBackground(new Color(21, 27, 36));
		veiculoDao = new VeiculoDaoHib(EMFactory.getEntityManager());
		try {
			veiculos = veiculoDao.listar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao listar veículos: " + e.getMessage());
			e.printStackTrace();
		}
		chooser = new JFileChooser();

		setTitle("Cadastro de Veículos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 771, 516);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(21, 27, 36));
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblId.setBounds(10, 10, 30, 20);
		contentPane.add(lblId);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(120, 12, 38, 20);
		contentPane.add(tfId);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModelo.setBounds(10, 40, 100, 20);
		contentPane.add(lblModelo);

		tfModelo = new JTextField();
		tfModelo.setBounds(120, 42, 106, 20);
		contentPane.add(tfModelo);

		JLabel lblPreco = new JLabel("Preço:");
		lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPreco.setBounds(10, 70, 100, 20);
		contentPane.add(lblPreco);

		tfPreco = new JTextField();
		tfPreco.setBounds(120, 72, 106, 20);
		contentPane.add(tfPreco);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAno.setBounds(263, 70, 100, 20);
		contentPane.add(lblAno);

		tfAno = new JTextField();
		tfAno.setBounds(373, 72, 100, 20);
		contentPane.add(tfAno);

		JLabel lblKm = new JLabel("Quilometragem:");
		lblKm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKm.setBounds(10, 101, 100, 20);
		contentPane.add(lblKm);

		tfKm = new JTextField();
		tfKm.setBounds(120, 101, 106, 20);
		contentPane.add(tfKm);

		JLabel lblCombustivel = new JLabel("Combustível:");
		lblCombustivel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCombustivel.setBounds(263, 101, 100, 20);
		contentPane.add(lblCombustivel);

		tfCombustivel = new JTextField();
		tfCombustivel.setBounds(373, 103, 100, 20);
		contentPane.add(tfCombustivel);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMarca.setBounds(263, 40, 100, 20);
		contentPane.add(lblMarca);

		cbMarca = new JComboBox();
		cbMarca.setBounds(373, 42, 100, 20);
		contentPane.add(cbMarca);

		popularBoxMarcas();

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (tfModelo.getText().trim().isEmpty()) {
		            JOptionPane.showMessageDialog(FrameVeiculo.this, "Informe o modelo", "Aviso",
		                    JOptionPane.INFORMATION_MESSAGE);
		            tfModelo.requestFocus();
		        } else {
		            if (veiculo == null) {
		                veiculo = new Veiculo();
		            }
		            veiculo.setModelo(tfModelo.getText().trim());
		            try {
		                veiculo.setPreco(Double.parseDouble(tfPreco.getText().trim()));
		                veiculo.setAno(Integer.parseInt(tfAno.getText().trim()));
		                veiculo.setKm(Integer.parseInt(tfKm.getText().trim()));
		                veiculo.setCombustivel(tfCombustivel.getText().trim());
		                
		                Marca marcaSelecionada = (Marca) cbMarca.getSelectedItem();
		                veiculo.setMarca(marcaSelecionada);

		                if (selecionado != null) {
		                    byte[] imagemBytes = Files.readAllBytes(selecionado.toPath());
		                    veiculo.setImagem(imagemBytes);
		                }

		                if (veiculo.getId() == 0) {
		                    veiculoDao.inserir(veiculo);
		                } else {
		                    veiculoDao.alterar(veiculo);
		                }

		                veiculos = veiculoDao.listar();
		                tableModel.setLista(veiculos);
		                tableModel.fireTableDataChanged();
		                limpar();
		            } catch (NumberFormatException nfe) {
		                JOptionPane.showMessageDialog(FrameVeiculo.this, "Informe valores válidos para os campos numéricos.",
		                        "Erro", JOptionPane.ERROR_MESSAGE);
		                nfe.printStackTrace();
		            } catch (Exception e1) {
		                JOptionPane.showMessageDialog(FrameVeiculo.this, e1.getMessage(), "Erro",
		                        JOptionPane.ERROR_MESSAGE);
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		btnSalvar.setMnemonic('s');
		btnSalvar.setBounds(10, 143, 100, 26);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(230, 143, 100, 26);
		contentPane.add(btnLimpar);
		
		tableModel = new VeiculoTableModel(veiculos);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 560, 277);
		contentPane.add(scrollPane);
		
		tbVeiculos = new JTable(tableModel);
		tbVeiculos.setToolTipText("Selecione um item para alterar ou excluir");
		tbVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        int linha = tbVeiculos.getSelectedRow();
		        if (linha >= 0) {
		            try {

		                veiculo = veiculos.get(linha);
		                tfId.setText(String.valueOf(veiculo.getId()));
		                tfModelo.setText(veiculo.getModelo());
		                cbMarca.setSelectedItem(veiculo.getMarca());
		                tfAno.setText(String.valueOf(veiculo.getAno()));
		                tfPreco.setText(String.valueOf(veiculo.getPreco()));
		                tfKm.setText(String.valueOf(veiculo.getKm()));
		                tfCombustivel.setText(veiculo.getCombustivel());
		                if (veiculo.getImagem() != null) {
		                    byte[] imagemBytes = veiculo.getImagem();
		                    ImageIcon icon = new ImageIcon(imagemBytes);
		                    Image img = icon.getImage().getScaledInstance(lbImagem.getWidth(), lbImagem.getHeight(), Image.SCALE_SMOOTH);
		                    lbImagem.setIcon(new ImageIcon(img));
		                } else {
		                	lbImagem.setIcon(null); 
		                }
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(FrameVeiculo.this, "Erro ao carregar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		                ex.printStackTrace();
		            }
		        }
		    }
		});

		tbVeiculos.setModel(tableModel); 
		scrollPane.setViewportView(tbVeiculos);

		lbImagem = new JLabel("");
		lbImagem.setToolTipText("Clique para inserir uma imagem");
		lbImagem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbImagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					chooser.setFileFilter(imageFilter);
					if (chooser.showOpenDialog(FrameVeiculo.this) == JFileChooser.APPROVE_OPTION) {
						selecionado = chooser.getSelectedFile();
						try {
							BufferedImage bufImg = ImageIO.read(selecionado);
							Image imagem = bufImg.getScaledInstance(lbImagem.getWidth(), lbImagem.getHeight(),
									Image.SCALE_SMOOTH);
							ImageIcon imgLabel = new ImageIcon(imagem);
							lbImagem.setIcon(imgLabel);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});
		lbImagem.setForeground(UIManager.getColor("Button.light"));
		lbImagem.setBackground(Color.WHITE);
		lbImagem.setBounds(499, 10, 216, 167);
		contentPane.add(lbImagem);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(120, 143, 100, 26);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (veiculo != null) {
					if (JOptionPane.showConfirmDialog(FrameVeiculo.this,
							"Deseja excluir o veículo " + veiculo.getModelo()) == JOptionPane.YES_OPTION) {
						try {
							veiculoDao.excluir(veiculo.getId());
							veiculos = veiculoDao.listar();
							tableModel.setLista(veiculos);
							tableModel.fireTableDataChanged();
							limpar();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(FrameVeiculo.this, "Selecione um veículo para excluí-lo");
				}
			}
		});
		btnExcluir.setMnemonic('e');
		contentPane.add(btnExcluir);
		
		tfBusca = new JTextField();
		tfBusca.setBounds(373, 145, 100, 26);
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
		
		JLabel lblLupa = new JLabel("");
		lblLupa.setBounds(351, 145, 22, 26);
		lblLupa.setIcon(new ImageIcon(new ImageIcon("src/main/resources/lupa.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); 
		contentPane.add(lblLupa);

	}
	
	private void filtrarTabela() {
	    String termoBusca = tfBusca.getText().toLowerCase();
	    List<Veiculo> veiculosFiltrados = veiculos.stream()
	        .filter(marca -> marca.getModelo().toLowerCase().contains(termoBusca)).toList();

	    tableModel.setLista(veiculosFiltrados);
	    tableModel.fireTableDataChanged();
	}

	private void popularBoxMarcas() {
		try {

			InterfaceDao<Marca> marcaDao = new MarcaDaoHib(EMFactory.getEntityManager());
			List<Marca> marcas = marcaDao.listar();

			for (Marca marca : marcas) {
				cbMarca.addItem(marca);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar veiculos: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void limpar() {
		tfModelo.setText("");
		tfId.setText("");
		tfPreco.setText("");
		tfAno.setText("");
		tfKm.setText("");
		tfCombustivel.setText("");
		lbImagem.setIcon(null);
		veiculo = null;
		tfModelo.requestFocus();
	}
}