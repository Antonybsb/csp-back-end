package com.api.csp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.api.csp.model.Cliente;
import com.api.csp.repository.ClienteRepository;

@RestController
@RequestMapping({"/api/clientes"}) //Url fixa (não muda).
@CrossOrigin("http://localhost:4200")
public class ClienteController {
	
	private ClienteRepository repository;
	
		ClienteController(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}
	
	@PostMapping
	//@ResponseStatus //(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente) {
		return repository.save(cliente);
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById((int) id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = {"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
		return repository.findById((int) id)
				.map(record -> {
					repository.deleteById((int) id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
				}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado ) {
		repository
		.findById(id)
		.map( cliente -> {
			cliente.setNome(clienteAtualizado.getNome());
			cliente.setCpf(clienteAtualizado.getCpf());
			return repository.save(cliente);
		})
		.orElseThrow();
//		.orElseThrow( ( -> new ResponseStatusException(HttpStatus.NOT_FOUND)) );
	}
	
//	@PutMapping(value="/{id}")
//	public ResponseEntity update(@PathVariable("id") long id,
//			@RequestBody Cliente cliente) {
//		return repository.findById(id)
//				.map(record -> {
//					record.setNome(contact.getName());
//					record.setCpf(contact.getCpf());
//					Cliente updated = repository.save(record);
//					return ResponseEntity.ok().body(updated);
//				}).orElse(ResponseEntity.notFound().build());
//					
//				
//	}
	
	
	

}
