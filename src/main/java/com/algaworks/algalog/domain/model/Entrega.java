package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.ValidationGroups;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
																			// o @Valid aqui serve para validar o objeto cliente
																			//@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class) // essa anotação serve par quando ocorrer a validação usar outro group inves do group padrão, ou seja o gruop.default.class   
																			//@NotNull	 essa anotação obriga ao elemento valida (valid) a não ser null, ou seja é uma validação no servidor antes de chegar no banco de dados
	@ManyToOne																// essa anotação serve para mapear um tipo de relação: muitos para um, ou seja muitas entregas para um cliente
	private Cliente cliente;
	
	@Embedded																// essa anotação é ultilizada quando é criada uma abstração de uma classe dentro de uma tabela, sera criadao todas as cracteristicas de destinatario dentro da tabela entregas
	private Destinatario destinatario;
	
	private BigDecimal taxa;										
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)										// serve para mapear a relação, onde ela esta em ralação com entrega
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
																			//@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)											// esta dizendo que os dados que seram armazenados na coluna de status(no dataBase) sera a String que representa o numero e não um int. se fosse origin no lugar de string seria armazenado os numeros(1, 2 e 3) e não as palavras (pendente, finalizado)
	private StatusEntrega status;
	
	private OffsetDateTime dataPedido;
	
																			//@JsonProperty(access = Access.READ_ONLY) // essa anotação serve para blequear qualquer tentativa de escrita dela no banco, ou seja ela so pode ser lida, o cliente não altera, so o servidor
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
	
		return ocorrencia;
	}

	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("entrega não pode ser finalizada");
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
}
