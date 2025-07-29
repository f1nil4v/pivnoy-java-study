package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Контроллеры - это классы для создания внешних http ручек. Чтобы к нам могли прийти по http, например, через постман
// Или если у приложухи есть веб-морда, каждое действие пользователя - это http запросы
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping("/create")
    public void createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        logger.info("POST /author/create — Creating author: {}", authorRequestDto);
        authorService.create(authorRequestDto);
    }

    @PostMapping("/delete")
    public void deleteAuthorById(@RequestParam Integer id) {
        logger.info("POST /author/delete — Deleting author with id: {}", id);
        authorService.delete(id);
    }

    @PostMapping("/add/books")
    public void addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        logger.info("POST /author/add/books — Adding books to author with id: {}. Books: {}", id, books);
        authorService.addBooks(id, books);
    }
}
