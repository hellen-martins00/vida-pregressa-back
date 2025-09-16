package br.com.vida_pregressa.model.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "concursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String descricao;
    private Date dataInicio;
    private Date dataTermino;
    private String status;
    private Date dataAlteracao;
    private String banca;

    @OneToMany(mappedBy = "concurso", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Candidato> candidatos;
}
