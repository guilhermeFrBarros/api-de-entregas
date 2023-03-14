package com.algaworks.algalog.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

																	//@Table(name = "Cliente")  // nomeia  a tabela que vai ser criada no banco, sem isso a tabela se chamaria o nome da classe
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 					//apenas as propriedades q são incluidas de forma explicitas 
@Getter
@Setter
@Entity 															// serve para mapear a classe para o banco de dados, ou seja, a tabela no banco é representa pela classe, onde cada atributo é uma coluna                    
public class Cliente {
	
																	// Todas as valições tem o  (groups = Default.class) implicito por validação		
	@EqualsAndHashCode.Include	            						// propriedade explicitada
	@Id																// Serve para identificar o Id = primary key da table
	@GeneratedValue(strategy = GenerationType.IDENTITY) 			//aqui vc esta espicicando que o modo que o id é criado é o modo do banco, ou seja no caso sera auto incrementado pelo banco
	private Long id;
	
	@NotBlank														// essa anotação avisa para o sistema que o campo nome não aceita null e nem ""
	@Size(max = 60)													// Definindo a quantidade maxima de caracteres 
	private String nome;
	
	@NotBlank
	@Email															// verifica seo formato e do email, se tem @, .com
	@Size(max = 255)
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "fone") 											//serve para inditacar que o atributo referes a coluna com o nome = fone, não foi usado nos outros atributos, pois esses tem os nomes identicos com as colunas no banco.
	private String telefone;

	
}





/*
 	@Override
 	public int hashCode() {
	return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Cliente other = (Cliente) obj;
	return Objects.equals(id, other.id);
}
*/