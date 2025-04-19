package br.senac.sp.projetopoo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Marca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nome", columnDefinition = "varchar(100)", 
			nullable = false)
	private String nome;
	@Column(columnDefinition = "mediumblob")
	private byte[] logo;

	@Override
	public String toString() {
	    return this.nome; 
	}
}
