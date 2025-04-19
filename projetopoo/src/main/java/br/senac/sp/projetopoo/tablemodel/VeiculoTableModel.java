package br.senac.sp.projetopoo.tablemodel;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import br.senac.sp.projetopoo.modelo.Veiculo;

public class VeiculoTableModel extends AbstractTableModel {
	private List<Veiculo> lista;
	private String[] cabecalho = { "Id", "Modelo", "Marca", "Ano", "Preço", "Km", "Combustivel" };

	public VeiculoTableModel(List<Veiculo> veiculos) {
		this.lista = veiculos;
	}

    public void setLista(List<Veiculo> lista) {
        this.lista = lista;
        fireTableDataChanged(); 
    }

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public int getColumnCount() {
		return cabecalho.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Veiculo v = lista.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return v.getId();
		case 1:
			return v.getModelo();
		case 2:
			return v.getMarca();
		case 3:
			return v.getAno();
		case 4:
			return v.getPreco();
		case 5:
			return v.getKm();
		case 6:
			return v.getCombustivel();
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return cabecalho[column];
	}

	public List<Veiculo> getLista() {
		return lista;
	}
	
	public ImageIcon getImageIcon(int row) {
	    Veiculo veiculo = lista.get(row);
	    if (veiculo.getImagem() != null) {
	        return new ImageIcon(veiculo.getImagem());
	    }
	    return null;
	}
	
	public void setListaFiltrada(List<Veiculo> listaFiltrada) {
	    this.lista = listaFiltrada; // Atualize a lista no modelo
	    fireTableDataChanged();    // Notifique a tabela que os dados mudaram
	}
	
}
