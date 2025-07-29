package ttv.poltoraha.pivka.app.model;

import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.dao.request.ReviewRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Review;

import java.util.ArrayList;

import static ttv.poltoraha.pivka.app.util.TestConst.*;

public class Models {
    public static Book getBook() {
        return Book.builder()
                .id(1)
                .genre("Роман")
                .build();
    }

    public static ReviewRequestDto getReviewRequestDto(Integer bookId) {
        return ReviewRequestDto.builder()
                .readerUsername(USERNAME)
                .text(REVIEW_TEXT)
                .rating(5)
                .bookId(bookId)
                .build();
    }

    public static Review getReview(Book book) {
        return Review.builder()
                .readerUsername(USERNAME)
                .rating(5)
                .text(REVIEW_TEXT)
                .book(book)
                .build();
    }

    // А хули нет?
    public static AuthorRequestDto getAuthorRequestDto() {
        return AuthorRequestDto.builder()
                .fullName(AuthorFullName)
                .build();
    }

    public static Author getAuthor() {
        return Author.builder()
                .fullName(AuthorFullName)
                .books(new ArrayList<>())
                .build();
    }
}
