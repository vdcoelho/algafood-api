package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		return cr.todas();
	}

	public Cidade porId(Long id) {
		Cidade cidade = cr.porId(id);

		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}

		return cidade;
	}

	public Cidade adicionar(Cidade cidade) {
		return cr.adicionar(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidade) {
		Cidade cidadeAtual = cr.porId(id);

		if (cidadeAtual == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}

		BeanUtils.copyProperties(cidade, cidadeAtual, "id", "estado.nome");

		return cr.adicionar(cidadeAtual);
	}

	public void remover(Long id) {
		Cidade cidade = cr.porId(id);

		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %d não existe.", id));
		}
		
		try {
			cr.remover(cidade);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cidade %d: %s não pode ser removida pois está em uso.", id, cidade.getNome()));
		}
	}

}
