package com.gama.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.gama.model.Carro;

public class CarroJpaRepository extends Repository {
	
	public List<Carro> listarPorMarca(String marca){
		return listar("marca", marca);
	}
	
	public List<Carro> listarPorModelo(String modelo){
		return listar("modelo", modelo);
	}
	
	public List<Carro> listar(String campo, Object valor){
		Query query = entityManager.createQuery("SELECT e FROM Carro e WHERE e." + campo + " = :param1 ");
		query.setParameter("param1",valor );
		return query.getResultList();
	}
	
	public Carro buscar(String placa){
		Query query = entityManager.createQuery("SELECT e FROM Carro e WHERE e.placa = :placa");
		query.setParameter("placa",placa );
		
		Carro carro = null;
		try {
			carro=(Carro) query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}catch (NonUniqueResultException e) {
			throw new RuntimeException("Desculpe, Mas já temos um veículo com esta placa em nossa base de dados : " + placa);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return (Carro) query.getSingleResult();
	}
	
	
	public void incluir(Carro carro) {
		entityManager.getTransaction().begin();
		entityManager.persist(carro); 
		entityManager.getTransaction().commit();
	}
	
	public Carro buscar(Integer id) {
		return entityManager.find(Carro.class,id);
	}
	
	public List<Carro> listar() {
		Query query = entityManager.createQuery("SELECT e FROM Carro e "); //JQPL
		return query.getResultList();
	}

	public void alterar(Carro carro) {
		entityManager.getTransaction().begin();
		entityManager.merge(carro);
		entityManager.getTransaction().commit();
	}
	
	
}
