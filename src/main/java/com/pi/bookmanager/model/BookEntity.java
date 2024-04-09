package com.pi.bookmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity {

    @Id
    @Column(name = "book_id")
    private UUID id;

    private String nome;

    private String area;

    private String curso;

    private String autor;

    @Column(name = "nome_aluno")
    private String nomeAluno;

    @Column(name = "ra_aluno")
    private Long raAluno;

    private boolean disponivel;

    @Column(name = "data_registro")
    @DateTimeFormat(pattern = "pattern = dd/mm/yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date dataRegistro;

    @Column(name = "data_emprestimo")
    @DateTimeFormat(pattern = "pattern = dd/mm/yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataEmprestimo;

    @Column(name = "data_devolucao_prevista")
    @DateTimeFormat(pattern = "pattern = dd/mm/yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataDevolucaoPrevista;

    @Column(name = "data_devolucao_efetiva")
    @DateTimeFormat(pattern = "pattern = dd/mm/yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataDevolucaoEfetiva;
}
