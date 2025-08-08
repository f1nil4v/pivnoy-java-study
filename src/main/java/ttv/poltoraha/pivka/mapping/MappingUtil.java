package ttv.poltoraha.pivka.mapping;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.dao.request.ReviewRequestDto;
import ttv.poltoraha.pivka.dao.response.AuthorResponseDto;
import ttv.poltoraha.pivka.dao.response.BookResponseDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.repository.MyUserRepository;

import java.util.List;

// Маппер - класс для того, чтобы превращать наши дто в Entity и наоборот
@Component
public class MappingUtil {
    public static Review fromRequestDto(ReviewRequestDto dto, Book book) {
        return Review.builder()
                .book(book)
                .text(dto.getText())
                .readerUsername(dto.getReaderUsername())
                .rating(dto.getRating())
                .build();
    }

    public static Review updateFromRequestDto(Review review, ReviewRequestDto dto) {
        review.setText(dto.getText());
        review.setRating(dto.getRating());

        return review;
    }

    // Сделал маппер
    public static Author fromRequestDto(AuthorRequestDto dto) {
        return Author.builder()
                .fullName(dto.getFullName())
                .build();
    }

    public static AuthorResponseDto toResponseDto(Author author) {
        return AuthorResponseDto.builder()
                .fullName(author.getFullName())
                .books(
                        author.getBooks().stream()
                                .map(book -> BookResponseDto.builder()
                                        .article(book.getArticle())
                                        .genre(book.getGenre())
                                        .rating(book.getRating())
                                        .tags(book.getTags().toString())
                                        .build())
                                .toList()
                )
                .build();
    }

    public static BookResponseDto toResponseDto(Book book) {
        return BookResponseDto.builder()
                .article(book.getArticle())
                .genre(book.getGenre())
                .rating(book.getRating())
                .tags(book.getTags().toString())
                .build();
    }
}
