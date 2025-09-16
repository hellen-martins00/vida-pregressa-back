package br.com.vida_pregressa.controllers;

import br.com.vida_pregressa.model.entities.Candidato;
import br.com.vida_pregressa.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(
        origins = {"*"}, // permite requisições de qualquer origem
        allowedHeaders = "*", // permite todos os cabeçalhos
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PATCH} // permite todos os métodos
)
@RestController
@RequestMapping("/api/candidatos")
public class CandidatosController {

    @Autowired
    CandidatoService candidatoService;

    @PostMapping
    public Candidato createCandidato(@RequestBody Candidato candidato) {
        return candidatoService.createCandidatoService(candidato);
    }

    // endpoint para listar os candidatos
    @GetMapping
    public Iterable<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatosService();
    }

    @GetMapping(path = "/{id}")
    public Optional<Candidato> obterCandidatoPorId(@PathVariable int id) {
        return candidatoService.obterCandidatoPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public void excluirCandidato(@PathVariable int id) {
        candidatoService.excluirCandidato(id);
    }

    @PutMapping("/alterarCandidato")
    public Candidato alterarCandidato(@Validated @RequestBody Candidato candidato) {
        return candidatoService.alterarCandidato(candidato);
    }
}
