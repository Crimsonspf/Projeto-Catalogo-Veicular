package br.senac.sp.projetopoo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {
	private static final EntityManagerFactory factory = 
			Persistence.createEntityManagerFactory("senac");
	private static EntityManager manager;
	
	public static EntityManager getEntityManager() {
		if(manager == null) {
			manager = factory.createEntityManager();
		}
		return manager;
	}
}
