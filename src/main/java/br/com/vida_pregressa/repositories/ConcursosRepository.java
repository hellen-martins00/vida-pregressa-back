package br.com.vida_pregressa.repositories;

import br.com.vida_pregressa.model.entities.Concurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcursosRepository extends JpaRepository<Concurso, Integer> {

    public List<Concurso> findByNome(String nome);

    @Query(value = "SELECT * FROM vp.concursos where nome = :nome" , nativeQuery = true)
    public List<Concurso> selectPeloNome(@Param("nome") String nome);

    @Query(" SELECT c.nome  FROM Concurso c where c.nome = :nome")
    public List<Concurso> selectPeloNomeJPQL(@Param("nome") String nome);
}
