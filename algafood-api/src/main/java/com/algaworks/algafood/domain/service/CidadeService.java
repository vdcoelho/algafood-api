package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cr;

	public List<Cidade> listar() {
		return cr.findAll();
	}

	public Cidade porId(Long id) {
		Optional<Cidade> cidade = cr.findById(id);

		if (!cidade.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}

		return cidade.get();
	}

	public Cidade adicionar(Cidade cidade) {
		return cr.save(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidade) {
		Optional<Cidade> cidadeAtual = cr.findById(id);

		if (!cidadeAtual.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}

		BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id", "estado.nome");

		return cr.save(cidadeAtual.get());
	}

	public void remover(Long id) {
		
		try {
			cr.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cidade %d não pode ser removida pois está em uso.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}
	}

}
