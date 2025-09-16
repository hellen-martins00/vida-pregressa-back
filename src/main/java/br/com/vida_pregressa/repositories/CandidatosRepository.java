package br.com.vida_pregressa.repositories;

import br.com.vida_pregressa.model.entities.Candidato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatosRepository extends CrudRepository<Candidato, Integer> {

    // busca por nome
    public List<Candidato> findByNome(String nome);

    // usando SQL puro
    @Query(value = "SELECT * FROM vp.candidatos WHERE nome = :nome", nativeQuery = true)
    public List<Candidato> selectPeloNome(@Param("nome") String nome);

    // usando JPQL
    @Query("SELECT c.nome FROM Candidato c WHERE c.nome = :nome")
    public List<Candidato> selectPeloNomeJPQL(@Param("nome") String nome);
}
