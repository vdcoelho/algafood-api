package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;


@Repository
public interface RestauranteCustomRepository {
	
	List<Restaurante> listarRestaurantesDaCozinha(Long cozinhaId);

}
