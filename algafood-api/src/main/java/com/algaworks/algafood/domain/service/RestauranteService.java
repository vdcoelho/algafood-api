package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository rr;

	@Autowired
	private CozinhaRepository cr;

	public Restaurante salvar(Restaurante restaurante) {

		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cr.findById(cozinhaId);

		if (!cozinha.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe a cozinha código %d", cozinhaId));
		}

		restaurante.setCozinha(cozinha.get());
		return rr.save(restaurante);
	}

	

}
