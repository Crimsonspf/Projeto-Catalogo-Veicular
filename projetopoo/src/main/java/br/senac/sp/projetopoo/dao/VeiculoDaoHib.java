package br.senac.sp.projetopoo.dao;

import java.util.List;

import br.senac.sp.projetopoo.modelo.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


public class VeiculoDaoHib implements InterfaceDao<Veiculo> {
	private EntityManager manager;
	
	public VeiculoDaoHib(EntityManager manager) {
		this.manager = manager;
	}

	@Override	
	public void inserir(Veiculo objeto) throws Exception {
		this.manager.persist(objeto);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();
	}

	@Override
	public void alterar(Veiculo objeto) throws Exception {
		this.manager.merge(objeto);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();		
	}

	@Override
	public Veiculo buscar(int id) throws Exception {
		return this.manager.find(Veiculo.class, id);
	}

	@Override
	public void excluir(int id) throws Exception {
		Veiculo veiculo = buscar(id);
		this.manager.remove(veiculo);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();		
	}

	@Override
	public List<Veiculo> listar() throws Exception {
		TypedQuery<Veiculo> query = 
				this.manager.createQuery("select v from Veiculo v order by v.modelo", Veiculo.class);
		return query.getResultList();
	}
	
	public Veiculo getVeiculoByNome(String modelo) throws Exception {
	    TypedQuery<Veiculo> query = this.manager.createQuery(
	            "SELECT v FROM Veiculo v WHERE v.modelo = :modelo", Veiculo.class
	        );
	        query.setParameter("modelo", modelo);
	        return query.getSingleResult();
	    }

}