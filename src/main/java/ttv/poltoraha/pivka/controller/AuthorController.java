package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.metrics.CustomMetrics;
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
    private final CustomMetrics appMetrics;

    @PostMapping("/create")
    public void createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        logger.info("POST /author/create — Creating author: {}", authorRequestDto);

        appMetrics.increment("api.request.count", "controller", "author", "action", "create");
        var sample = appMetrics.startTimer();

        authorService.create(authorRequestDto);

        appMetrics.stopTimer(sample, "api.db.timer", "controller", "author", "action", "create");
    }

    @PostMapping("/delete")
    public void deleteAuthorById(@RequestParam Integer id) {
        logger.info("POST /author/delete — Deleting author with id: {}", id);

        appMetrics.increment("api.request.count", "controller", "author", "action", "delete");
        var sample = appMetrics.startTimer();

        authorService.delete(id);

        appMetrics.stopTimer(sample, "api.db.timer", "controller", "author", "action", "delete");
    }

    @PostMapping("/add/books")
    public void addBooksToAuthor(@RequestParam Integer id, @RequestBody List<Book> books) {
        logger.info("POST /author/add/books — Adding books to author with id: {}. Books: {}", id, books);

        appMetrics.increment("api.request.count", "controller", "author", "action", "add_books");
        var sample = appMetrics.startTimer();

        authorService.addBooks(id, books);

        appMetrics.stopTimer(sample, "api.db.timer", "controller", "author", "action", "add_books");
    }
}
