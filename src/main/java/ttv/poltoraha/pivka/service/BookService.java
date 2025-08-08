package ttv.poltoraha.pivka.service;

import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;

public interface BookService {
    public void updateAvgRating(Integer bookId);
    public List<Book> getTopBooksByTag(String tag, int count);
}
