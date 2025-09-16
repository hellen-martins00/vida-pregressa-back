package br.com.vida_pregressa.model.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "candidatos")
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String cpf;
    private Integer classificacao;
    private String nomeMae;

    @ManyToOne
    @JoinColumn(name = "concursos_id", nullable = false )
    @JsonBackReference
    private Concurso concurso;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Endereco> enderecos;
}

