package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service																			//essa anotação serve para torna classe em um componente do spring, que representa os serviços q vão ser executados  
public class CatalagoClienteService {
	
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Long ClienteId)  {
		return clienteRepository.findById(ClienteId)
				.orElseThrow(() -> new NegocioException("Clientem não encontrado"));
	}
	
	@Transactional																	// essa anotação declara q o metodo vai ser executado dentro de uma transação, ou seja, se algo que estiver sendo executado dentro do metodo ter errado, todas a operações dentro dessa transação   
	public Cliente salvar(Cliente cliente) {										// esse metodo serve tanto para salvar quanto para add dados no dataBase
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
		}
		
		return clienteRepository.save(cliente);										// delegando para o repositorio para salvar
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
