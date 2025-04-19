package br.senac.sp.projetopoo.dao;

import java.util.List;

import br.senac.sp.projetopoo.modelo.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


public class MarcaDaoHib implements InterfaceDao<Marca> {
	private EntityManager manager;
	
	public MarcaDaoHib(EntityManager manager) {
		this.manager = manager;
	}

	@Override	
	public void inserir(Marca objeto) throws Exception {
		this.manager.persist(objeto);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();
	}

	@Override
	public void alterar(Marca objeto) throws Exception {
		this.manager.merge(objeto);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();		
	}

	@Override
	public Marca buscar(int id) throws Exception {
		return this.manager.find(Marca.class, id);
	}

	@Override
	public void excluir(int id) throws Exception {
		Marca marca = buscar(id);
		this.manager.remove(marca);
		this.manager.getTransaction().begin();
		this.manager.getTransaction().commit();		
	}

	@Override
	public List<Marca> listar() throws Exception {
		TypedQuery<Marca> query = 
				this.manager.createQuery("select m from Marca m order by m.nome", Marca.class);
		return query.getResultList();
	}
	
	public Marca getMarcaByNome(String nome) throws Exception {
	    TypedQuery<Marca> query = this.manager.createQuery(
	            "SELECT m FROM Marca m WHERE m.nome = :nome", Marca.class
	        );
	        query.setParameter("nome", nome);
	        return query.getSingleResult();
	    }

	
}
