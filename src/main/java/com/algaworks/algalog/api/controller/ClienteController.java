package com.algaworks.algalog.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalagoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor																	//diz que o componente abaixo é um controller REST
@RestController  																	// essa anotaição serve para mostrar para o spring que essa classe/metodo é um componente spring
@RequestMapping("/clientes")                                                        // o  @RequestMapping serve para indidicar que tosdo os metodos da classe abaixo comeca sua rota com /clientes 
public class ClienteController {
		
																					// @Autowired  // injeta na classe uma intancia em tempo de execução  
	private ClienteRepository clienteReposytory;
	private CatalagoClienteService catalogoClienteService;
	
	@GetMapping																		//quando a rota /clientes for chamanda atraves de uma  requisição o metodo abaixo sera chamado 
	public List<Cliente> listar() {
		return clienteReposytory.findAll(); 
	}
	
	@GetMapping("/{clienteId}")														// ResponseEntity serve para representar a resposta, mas também para manipular os status 			
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {   		// @PathVariable vincula o parametro do metodo a varivel do caminho(/clientes/{clienteId}), ou seja sera passado os mesmo valor para o metodo e para o caminho
		return clienteReposytory.findById(clienteId)
				.map(cliente -> ResponseEntity.ok(cliente))							// = map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)												//so muda o status retornado na res que antes era 200, agora é 201, q é o status de criação de um recurso
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) { 				// @RequestBody serve para transformar o corpo da requesição em um objeto (no caso cliente)
																					// @Valid serve para força o elemento a ser validao antes de entrar no metodo
		return catalogoClienteService.salvar(cliente);
	}																			
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, 
				@Valid @RequestBody Cliente cliente){
			if (!clienteReposytory.existsById(clienteId)) {
				return ResponseEntity.notFound().build();
			}
			
			cliente.setId(clienteId);
			cliente = catalogoClienteService.salvar(cliente);
			
			return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {         	// retorna um ResponseEntity com um corpo vazio
		if (!clienteReposytory.existsById(clienteId)) {								// 
			return ResponseEntity.notFound().build();
		}
		
		catalogoClienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
}







//Optional<Cliente> cliente = clienteReposytory.findById(clienteId);		// o findById retorna um optional, que um tipo q tem pode ou não conter algo

//if ( cliente.isPresent()) {                                             // isPresente esta perguntando se tem alguma coisa dentro do cliente
//	return 	ResponseEntity.ok(cliente.get());							// retorna o objeto buscado e o status 200 	  // nessa linha o orElse esta dizendo para retorno oq tem na varivel se tiver algo, se não retorna null
//}

//return ResponseEntity.notFound().build();                               // retorna o status 400