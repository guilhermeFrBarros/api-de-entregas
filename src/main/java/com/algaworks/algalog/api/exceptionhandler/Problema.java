package com.algaworks.algalog.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(Include.NON_NULL)  					// essa anotação serve para quando é passada a resposta, so retorne os elementos que tem algo e não retorne os elementos NULL
@Getter
@Setter
public class Problema {
	
	private Integer status;
	private OffsetDateTime dataHora;               	// data e hora do erro
	private String Titulo;
	private List<Campo> campos;
	
	@AllArgsConstructor
	@Getter
	public static class Campo {
		private String nome;
		private String mensagem;
	}
	
}
