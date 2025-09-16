package br.com.vida_pregressa.model.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private Date dataInicioMoradia;
    private Date dataFimMoradia;

    @ManyToOne
    @JoinColumn(name = "candidatos_id", nullable = false)
    @JsonBackReference
    private Candidato candidato;
}
