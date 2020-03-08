package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository er;

	public List<Estado> listar() {
		return er.todas();
	}

	public Estado porId(Long id) {
		Estado estado = er.porId(id);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}

		return estado;
	}

	public Estado adicionar(Estado estado) {
		return er.adicionar(estado);
	}

	public Estado atualizar(Long id, Estado estado) {
		Estado estadoAtual = er.porId(id);

		if (estadoAtual == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}

		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return er.adicionar(estadoAtual);
	}

	public void remover(Long id) {
		Estado estado = er.porId(id);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}
		
		try {
			er.remover(estado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado %d: %s não pode ser removido pois está em uso.", id, estado.getNome()));
		}
	}

}
