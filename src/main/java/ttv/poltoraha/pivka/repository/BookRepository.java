package ttv.poltoraha.pivka.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.dao.response.BookResponseDto;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query(value = """
        SELECT b.*
        FROM book b
        JOIN author a ON b.author_id = a.id
        WHERE SUBSTRING(a.full_name, 1, LOCATE(' ', a.full_name) - 1) = :surname
          AND a.id = (
              SELECT id
              FROM author
              WHERE SUBSTRING(full_name, 1, LOCATE(' ', full_name) - 1) = :surname
              ORDER BY rating DESC
              LIMIT 1
        )
    """, nativeQuery = true)
    List<Book> findBooksByAuthorSurname(@Param("surname") String surname);
}
