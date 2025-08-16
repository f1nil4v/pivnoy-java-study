package ttv.poltoraha.pivka.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.response.BookResponseDto;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.entity.Review;
import ttv.poltoraha.pivka.mapping.MappingUtil;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.service.BookService;
import util.MyUtility;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public void updateAvgRating(Integer bookId) {
        Book book = MyUtility.findEntityById(bookRepository.findById(bookId), "book", bookId.toString());
        Double updatedAvgRaiting = book.getReviews()
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        book.setRating(updatedAvgRaiting);

        bookRepository.save(book);
    }

    public List<Book> getTopBooksByTag(String tag, int count) {
        Pageable pageable = PageRequest.of(0, count);
        val books = bookRepository.findTopBooksByTag(tag);
        return bookRepository.findTopBooksByTag(tag, pageable);

    public List<BookResponseDto> findBooksByAuthorSurname(String surname) {
        List<Book> books = bookRepository.findBooksByAuthorSurname(surname);
        return books.stream().map(MappingUtil::toResponseDto).toList();
    }
}
