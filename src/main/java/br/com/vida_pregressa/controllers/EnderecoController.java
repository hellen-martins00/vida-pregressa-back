package br.com.vida_pregressa.controllers;

import br.com.vida_pregressa.model.entities.Endereco;
import br.com.vida_pregressa.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    public Endereco createEndereco(@RequestBody Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
}
