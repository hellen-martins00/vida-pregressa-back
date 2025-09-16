package br.com.vida_pregressa.service;

import br.com.vida_pregressa.model.entities.Candidato;
import br.com.vida_pregressa.repositories.CandidatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatosRepository candidatosRepository;

    public Candidato createCandidatoService(Candidato candidato) {
        // não pode ter candidato com o mesmo nome
        // validar se no banco existe um candidato com o mesmo nome
        verificarNomeDoCandidatoExistente(candidato);
        return candidatosRepository.save(candidato);
    }

    public Iterable<Candidato> listarCandidatosService() {
        return candidatosRepository.findAll();
    }

    public Optional<Candidato> obterCandidatoPorId(@PathVariable int id) {
        return candidatosRepository.findById(id);
    }

    public String excluirCandidato(@PathVariable int id) {
        candidatosRepository.deleteById(id);
        return "Excluído com sucesso";
    }

    public Candidato alterarCandidato(Candidato candidato) {
        return candidatosRepository.save(candidato);
    }

    public void verificarNomeDoCandidatoExistente(Candidato candidato) {
        if (!candidatosRepository.findByNome(candidato.getNome()).isEmpty()) {
            throw new RuntimeException("Candidato com o nome " + candidato.getNome() + " já existe.");
        }
    }
}
