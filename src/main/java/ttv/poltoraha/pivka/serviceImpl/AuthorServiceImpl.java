package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.dao.request.AuthorRequestDto;
import ttv.poltoraha.pivka.dao.response.AuthorResponseDto;
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
    private final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    // todo как будто надо насрать всякими мапперами
    @Override
    public void create(AuthorRequestDto authorRequestDto) {
        logger.info("Creating author with data: {}", authorRequestDto);
        if (authorRepository.findByFullName(authorRequestDto.getFullName()).isPresent()) {
            throw new EntityExistsException(String.format("Author with full name \"%s\" already exist", authorRequestDto.getFullName())); // Добавил проверочку на уникальность
        }
        // Насрал маппером
        Author mappedAuthor = MappingUtil.fromRequestDto(authorRequestDto);
        authorRepository.save(mappedAuthor);
        logger.info("Author successfully created: {}", mappedAuthor);
    }

    // Немного исправил метод, для исключения ошибки
    @Override
    public void delete(Integer id) {
        logger.info("Deleting author with id: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
        authorRepository.delete(author);
        logger.info("Author with id {} deleted", id);
    }

    @Override
    public void addBooks(Integer id, List<Book> books) {
        logger.info("Adding books to author with id {}: {}", id, books);
        val author = getOrThrow(id);

        author.getBooks().addAll(books);
        logger.info("Books successfully added to author with id {}", id);
    }

    @Override
    public void addBook(Integer id, Book book) {
        logger.info("Adding single book to author with id {}: {}", id, book);
        val author = getOrThrow(id);

        author.getBooks().add(book);
        logger.info("Book successfully added to author with id {}", id);
    }

    @Override
    public List<AuthorResponseDto> getAuthorList() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(MappingUtil::toResponseDto)
                .toList();
    }

    @Override
    public List<Author> getTopAuthorsByTag(String tag, int count) {
        logger.info("Fetching top {} authors with tag: {}", count, tag);
        Pageable pageable = PageRequest.of(0, count);
        val authors = authorRepository.findTopAuthorsByTag(tag);
        logger.info("Found {} authors", authors.size());
        return authorRepository.findTopAuthorsByTag(tag, pageable);
    }

    private Author getOrThrow(Integer id) {
        logger.debug("Looking for author with id: {}", id);
        val optionalAuthor = authorRepository.findById(id);
        val author = optionalAuthor.orElse(null);

        if (author == null) {
            logger.warn("Author with id {} not found", id);
            throw new RuntimeException("Author with id = " + id + " not found");
        }

        return author;
    }
}
