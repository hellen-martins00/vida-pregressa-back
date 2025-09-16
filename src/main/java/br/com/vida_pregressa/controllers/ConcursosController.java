package br.com.vida_pregressa.controllers;

import br.com.vida_pregressa.model.entities.Concurso;
import br.com.vida_pregressa.service.ConcursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PATCH}
)
@RestController
@RequestMapping("/api/concursos")
public class ConcursosController {

    @Autowired
    ConcursoService concursoService;
    @PostMapping
    public Concurso createConcurso(@RequestBody Concurso concurso) {
        return concursoService.createConcursoService(concurso);
    }

    //endpoint p listar os concursos
    @GetMapping
    public Iterable<Concurso> listarConcurso() {
        return concursoService.listarConcursoService();
    }

    @GetMapping(path = "/{id}")
    public Optional<Concurso> obterConcursoPorId(@PathVariable int id) {
        return concursoService.obterConcursoPorId(id);
    }

    @DeleteMapping(path = "/{id}")
    public void excluirConcurso(@PathVariable int id) {
        concursoService.excluirConcurso(id);
    }

    @PutMapping("/alterarConcurso")
    public Concurso alterarConcurso(@Validated @RequestBody Concurso concurso) {
        return concursoService.alterarConcurso(concurso);
    }
}
