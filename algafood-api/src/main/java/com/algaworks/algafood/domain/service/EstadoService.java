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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository er;

	public List<Estado> listar() {
		return er.findAll();
	}

	public Estado porId(Long id) {
		Optional<Estado> estado = er.findById(id);

		if (!estado.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}

		return estado.get();
	}

	public Estado adicionar(Estado estado) {
		return er.save(estado);
	}

	public Estado atualizar(Long id, Estado estado) {
		Optional<Estado> estadoAtual = er.findById(id);

		if (!estadoAtual.isPresent()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}

		BeanUtils.copyProperties(estado, estadoAtual.get(), "id");

		return er.save(estadoAtual.get());
	}

	public void remover(Long id) {
		
		try {
			er.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado %d não pode ser removido pois está em uso.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %d não existe.", id));
		}
	}

}
