package ttv.poltoraha.pivka.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT DISTINCT b FROM book b JOIN b.author a WHERE b.tags LIKE %:tag% ORDER BY a.avgRating DESC, a.id ASC")
    List<Book> findTopBooksByTag(@Param("tag") String tag);

    @Query("SELECT DISTINCT b FROM book b JOIN b.author a WHERE b.tags LIKE %:tag% ORDER BY a.avgRating DESC, a.id ASC")
    List<Book> findTopBooksByTag(@Param("tag") String tag,  Pageable pageable);
}
