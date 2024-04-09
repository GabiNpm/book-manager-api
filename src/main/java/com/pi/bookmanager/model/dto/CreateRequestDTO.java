package com.pi.bookmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDTO {

        private String nome;

        private String area;

        private String curso;

        private String autor;

        private String nomeAluno;

        private Long raAluno;

        private Date dataEmprestimo;

        private Date dataDevolucaoPrevista;

        private Date dataDevolucaoEfetiva;
}
