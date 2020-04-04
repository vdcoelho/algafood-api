package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository rr;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return rr.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> porId(@PathVariable(name = "id") Long id) {
		Optional<Restaurante> restaurante = rr.findById(id);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteAtual = rr.findById(id);
			
			if (!restauranteAtual.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
			
			Restaurante restauranteAtualizado = restauranteService.salvar(restauranteAtual.get());
			return ResponseEntity.ok(restauranteAtualizado);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable("id") Long id, @RequestBody Map<String, Object> campos) {
		
		Optional<Restaurante> restauranteAtual = rr.findById(id);
		
		if (!restauranteAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(id, restauranteAtual.get());
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
	@GetMapping("/da-cozinha")
	public List<Restaurante> listarRestaurantesDaCozinha(@RequestParam Long cozinhaId) {
		return rr.listarRestaurantesDaCozinha(cozinhaId);
		
	}

}
