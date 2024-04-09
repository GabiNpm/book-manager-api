package com.pi.bookmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestarRequestDTO {

        private String nomeAluno;

        private Long raAluno;

        private Integer diasEmprestimo;
}
