package com.pi.bookmanager.service;

import com.pi.bookmanager.model.BookEntity;
import com.pi.bookmanager.model.dto.CreateRequestDTO;
import com.pi.bookmanager.model.dto.EmprestarRequestDTO;
import com.pi.bookmanager.model.dto.ProcurarRequestDTO;
import com.pi.bookmanager.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity create(CreateRequestDTO dto) {
        BookEntity bookToSave = new BookEntity();
        bookToSave.setId(UUID.randomUUID());
        bookToSave.setNome(dto.getNome());
        bookToSave.setArea(dto.getArea());
        bookToSave.setCurso(dto.getCurso());
        bookToSave.setAutor(dto.getAutor());
        bookToSave.setDisponivel(true);
        bookToSave.setDataRegistro(new Date());
        return bookRepository.save(bookToSave);
    }


    public List<BookEntity> pesquisarPorNome(ProcurarRequestDTO dto) throws Exception {
        if (!Objects.equals(dto.getParametro(), "nome")) {
            throw new Exception("parametro invalido");
        }
        return bookRepository.findAllByNome(dto.getNome());
    }

    public BookEntity emprestar(UUID id, EmprestarRequestDTO dto) {
        BookEntity book = bookRepository.getReferenceById(id);

        Date dataDevolucao = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dataDevolucao);
        c.add(Calendar.DATE, dto.getDiasEmprestimo());
        dataDevolucao = c.getTime();

        book.setDisponivel(false);
        book.setNomeAluno(dto.getNomeAluno());
        book.setRaAluno(dto.getRaAluno());
        book.setDataEmprestimo(new Date());
        book.setDataDevolucaoPrevista(dataDevolucao);
        return bookRepository.save(book);
    }

    public void deletar(UUID id) {
        bookRepository.deleteById(id);
    }

    public Double devolver(UUID id) {
        BookEntity book = bookRepository.getReferenceById(id);
        book.setDataDevolucaoEfetiva(new Date());
        Double debito = calcularMulta(book);

        book.setDisponivel(true);
        book.setNomeAluno(null);
        book.setRaAluno(null);
        book.setDataDevolucaoEfetiva(null);
        book.setDataDevolucaoPrevista(null);
        book.setDataEmprestimo(null);
        bookRepository.save(book);

        return debito;
    }

    private Double calcularMulta(BookEntity book) {
        Period difference = Period.between(
                book.getDataDevolucaoPrevista().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                book.getDataDevolucaoEfetiva().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );
        if (difference.getDays()> 0) {
            return difference.getDays() * 1.50;
        } else {
            return 0.0;
        }
    }
}
