package br.senac.sp.projetopoo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Veiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "modelo", columnDefinition = "varchar(100)", 
			nullable = false)
	private String modelo;	
	private double preco;
	private int ano;
	private int km;
	private String combustivel;
	@Column(columnDefinition = "mediumblob")
	private byte[] imagem;
	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;
}
