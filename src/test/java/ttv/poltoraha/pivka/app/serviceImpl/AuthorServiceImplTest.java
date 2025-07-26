package ttv.poltoraha.pivka.app.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.serviceImpl.AuthorServiceImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ttv.poltoraha.pivka.app.model.Models.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class AuthorServiceImplTest {
    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Book book;
    private AuthorRequestDto authorRequestDto;
    private Author authorEntity;

    @BeforeEach
    public void setUp() {
        book = bookRepository.findById(1).get();
        authorRequestDto = getAuthorRequestDto();
        authorEntity = getAuthor();
    }


    // Как же долго я писал этот ебучий тест.
    // Сначала думал без DTO буду писать
    // Потом добавил его. Потом удалил. Потом снова добавил.
    // Пока разбирался с задачей, совсем забыл про то, что написано в задаче (там как раз и было сказано про DTO)
    // Крч, подпалил фителёк
    @Test
    public void testCreateAuthor_Succsess() {
        val beforeUpdateAuthorSize = authorRepository.findAll().size();
        authorService.create(authorRequestDto);
        val afterUpdateAuthorSize = authorRepository.findAll().size();
        assertNotEquals(beforeUpdateAuthorSize, afterUpdateAuthorSize);
    }

    // Тест на добавление существующего автора
    @Test
    public void testCreateAuthor_IsPresent() {
        assertThrows(EntityExistsException.class, () -> {
            authorService.create(AuthorRequestDto.builder()
                    .fullName("Пастернак Борис Леонидович")
                    .build());
        });
    }

    // Тест на удаление добавленного автора
    @Test
    public void testDeleteAuthor_Succsess() {
        val savedAuthor = authorRepository.save(authorEntity).getId();
        authorService.delete(savedAuthor);
        assertFalse(authorRepository.existsById(savedAuthor));
    }

    // Думаю этот тест имеет место быть, хоть пользователь и никогда не сможет удалить несуществующего автора
    // с индексом 999
    // Однако может произойти такое, что могут быть 2 админа, которые хотят удалить одного автора
    // Один удалит успешно, у второго ИРЛ страница врядли обновится, но выдасться ошибка, что такого автора нет (уже)
    // Вообще тесты как правило должны рассматривать два варианта поведения, поэтому я и сделал два теста, которые предусматривают
    // два варианта поведения
    @Test
    public void testDeleteAuthor_NotFound() {
        assertThrows(EntityNotFoundException.class, () -> authorService.delete(999));
    }

    @Test
    public void testAddBooksAuthor_Succsess() {
        Author savedAuthor = authorRepository.save(authorEntity);
        val authorID = savedAuthor.getId();

        List<Book> newBooks = new ArrayList<>(Arrays.asList(
                Book.builder()
                        .article("Новая книга 1")
                        .genre("Роман")
                        .rating(5.0)
                        .tags("классика, литература")
                        .author(savedAuthor)
                        .build(),
                Book.builder()
                        .article("Новая книга 2")
                        .genre("Фантастика")
                        .rating(4.0)
                        .tags("sci-fi, приключения")
                        .author(savedAuthor)
                        .build()
        ));

        authorService.addBooks(authorID, newBooks);
        Author updatedAuthor = authorRepository.findById(authorID).orElseThrow();
        assertEquals(newBooks.size(), updatedAuthor.getBooks().size());
    }

    @Test
    public void testAddBooksAuthor_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            authorService.addBooks(999, new ArrayList<>());
        });
    }

    @Test
    public void testAddBookAuthor_Succsess() {
        val authorID = authorRepository.save(authorEntity).getId();
        authorService.addBook(authorID, book);
        Author updatedAuthor = authorRepository.findById(authorID).orElseThrow();
        assertEquals(book, updatedAuthor.getBooks().get(0));
    }

    @Test
    public void testAddBookAuthor_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            authorService.addBook(999, book);
        });
    }

}
