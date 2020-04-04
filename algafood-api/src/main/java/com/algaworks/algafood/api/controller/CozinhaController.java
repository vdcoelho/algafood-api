package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaRepository cr;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cr.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> porId(@PathVariable(name = "id") Long id) {
		Optional<Cozinha> cozinha = cr.findById(id);
		
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
			
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		
		return cozinhaService.salvar(cozinha);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("id") Long id, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtualizada = cr.findById(id);
		
		if (cozinhaAtualizada.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtualizada.get(), "id"); //ultimo parametro são as propriedades que não deve copiar
			
			cozinhaService.salvar(cozinhaAtualizada.get());
			
			return ResponseEntity.ok(cozinhaAtualizada.get());	
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		
		try {
			cozinhaService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	
	}

}
