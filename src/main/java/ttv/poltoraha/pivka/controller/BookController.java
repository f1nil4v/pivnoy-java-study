package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.dao.response.BookResponseDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.serviceImpl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/{surname}")
    public List<BookResponseDto> findBooksByAuthorSurname(@PathVariable String surname) {
        return bookService.findBooksByAuthorSurname(surname);
    }
}
