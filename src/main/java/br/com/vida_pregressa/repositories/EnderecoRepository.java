package br.com.vida_pregressa.repositories;

import br.com.vida_pregressa.model.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
