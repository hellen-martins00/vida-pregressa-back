package br.com.vida_pregressa.service;

import br.com.vida_pregressa.model.entities.Candidato;
import br.com.vida_pregressa.model.entities.Concurso;
import br.com.vida_pregressa.repositories.ConcursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Optional;

@Service
public class ConcursoService {

    @Autowired
    private ConcursosRepository concursoRepository;

    public Concurso createConcursoService(Concurso concurso) {
        //não pode ter concurso com o mesmo nome
        //validar se no banco existe o concurso com o mesmo nome
        //consultar no banco where nome do concurso se é igual ao concurso que vai ser cadastrado
        verificarNomeDoConcursoExistente(concurso);

        return concursoRepository.save(concurso);
    }

    public Iterable<Concurso> listarConcursoService() {
        return concursoRepository.findAll();
    }

    public Optional<Concurso> obterConcursoPorId(@PathVariable int id) {
        return concursoRepository.findById(id);
    }

    public String excluirConcurso(@PathVariable int id) {
        concursoRepository.deleteById(id);
        return "Excluído com sucesso";
    }

    public Concurso alterarConcurso(Concurso concurso) {
        concurso.setDataAlteracao(new Date());
        return concursoRepository.save(concurso);
    }

    public Concurso atualizarConcurso(Concurso concurso) {
        return concursoRepository.save(concurso);
    }

    public void verificarNomeDoConcursoExistente(Concurso concurso){
       if (!concursoRepository.findByNome(concurso.getNome()).isEmpty()){
           throw  new RuntimeException("Concurso com o nome " + concurso.getNome() + " já existe.");
       }
    }
}

