package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.entity.Author;
import ttv.poltoraha.pivka.entity.Book;
import ttv.poltoraha.pivka.mapping.MappingUtil;
import ttv.poltoraha.pivka.repository.AuthorRepository;
import ttv.poltoraha.pivka.service.AuthorService;

import java.util.List;

// Имплементации интерфейсов с бизнес-логикой
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    // todo как будто надо насрать всякими мапперами
    @Override
    public void create(AuthorRequestDto authorRequestDto) {
        if (authorRepository.findByFullName(authorRequestDto.getFullName()).isPresent()) {
            throw new EntityExistsException(String.format("Author with full name \"%s\" already exist", authorRequestDto.getFullName())); // Добавил проверочку на уникальность
        }
        // Насрал маппером
        Author mappedAuthor = MappingUtil.fromRequestDto(authorRequestDto);
        authorRepository.save(mappedAuthor);
    }

    // Немного исправил метод, для исключения ошибки
    @Override
    public void delete(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
        authorRepository.delete(author);
    }

    @Override
    public void addBooks(Integer id, List<Book> books) {
        val author = getOrThrow(id);

        author.getBooks().addAll(books);
    }

    @Override
    public void addBook(Integer id, Book book) {
        val author = getOrThrow(id);

        author.getBooks().add(book);
    }

    @Override
    public List<Author> getTopAuthorsByTag(String tag, int count) {
        Pageable pageable = PageRequest.of(0, count);
        val authors = authorRepository.findTopAuthorsByTag(tag);

        return authorRepository.findTopAuthorsByTag(tag, pageable);
    }

    private Author getOrThrow(Integer id) {
        val optionalAuthor = authorRepository.findById(id);
        val author = optionalAuthor.orElse(null);

        if (author == null) {
            throw new RuntimeException("Author with id = " + id + " not found");
        }

        return author;
    }
}
