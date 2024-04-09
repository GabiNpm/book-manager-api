package com.pi.bookmanager.controller;

import com.pi.bookmanager.model.BookEntity;
import com.pi.bookmanager.model.dto.CreateRequestDTO;
import com.pi.bookmanager.model.dto.EmprestarRequestDTO;
import com.pi.bookmanager.model.dto.ProcurarRequestDTO;
import com.pi.bookmanager.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookEntity> create(final @RequestBody CreateRequestDTO dto){
        BookEntity book = bookService.create(dto);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PostMapping(path = "/pesquisar")
    public ResponseEntity<List<BookEntity>> pesquisarPorNome(final @RequestBody ProcurarRequestDTO dto){
        List<BookEntity> books = new ArrayList<>();
        try {
            books = bookService.pesquisarPorNome(dto);
        } catch (Exception e) {
            new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/emprestar")
    public ResponseEntity<BookEntity> emprestar(final @PathVariable UUID id, final @RequestBody EmprestarRequestDTO dto){
        BookEntity book = bookService.emprestar(id, dto);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}/devolver")
    public ResponseEntity<String> devolucao(final @PathVariable UUID id){
        Double debito = bookService.devolver(id);
        return new ResponseEntity<>(String.format("Debito no valor de: R$ %1$,.2f", debito), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BookEntity> deletar(final @PathVariable UUID id){
        bookService.deletar(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
