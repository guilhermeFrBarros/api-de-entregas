package com.algaworks.algalog.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


															// essa classe serviu para configurar o modelMapper assim, permitindo que o spring o reconheça como um elemento, possibilitando a sua instanciação 
@Configuration
public class ModelMapperConfig {

	@Bean 													//essa anotção esta indicando que esse medoto ( modelMapper()) instancia, inicializa e configura um bean q sera gerenciado pelo spring  
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
