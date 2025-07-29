package ttv.poltoraha.pivka.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.serviceImpl.RecommendationServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationServiceImpl recommendationService;

    @GetMapping("/authors")
    public List<Author> getRecommendationAuthor(@RequestParam String username) {
        return recommendationService.recommendAuthor(username);
    }

    @GetMapping("/books")
    public List<Book> getRecommendationBook(@RequestParam String username) {
        return recommendationService.recommendBook(username);
    }

    @GetMapping("/quotes/{book_id}")
    public List<Quote> getRecommendQuoteByBook(@PathVariable Integer book_id) {
        return recommendationService.recommendQuoteByBook(book_id);
    }
}
