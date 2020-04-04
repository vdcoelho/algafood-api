package com.algaworks.algafood.teste;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class CozinhaCadastroTeste {

	public static void main(String[] args) {
		//pegar o contexto da aplicação
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//pegar o bean
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = cozinhaRepository.findAll();
		
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}

	}

}
