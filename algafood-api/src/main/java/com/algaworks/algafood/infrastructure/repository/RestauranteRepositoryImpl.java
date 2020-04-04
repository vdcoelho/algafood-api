package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteCustomRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteCustomRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> listarRestaurantesDaCozinha(Long cozinhaId) {
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante where cozinha.id = :cozinhaId", Restaurante.class);
		
		query.setParameter("cozinhaId", cozinhaId);
		
		return query.getResultList();
	}

}
