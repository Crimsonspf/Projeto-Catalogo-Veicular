package br.senac.sp.projetopoo.dao;

import java.util.List;

public interface InterfaceDao<T> {
	public void inserir(T objeto) throws Exception;
	public void alterar(T objeto) throws Exception;
	public T buscar(int id) throws Exception;
	public void excluir(int id) throws Exception;
	public List<T> listar() throws Exception;
}
