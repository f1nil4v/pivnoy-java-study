package ttv.poltoraha.pivka.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttv.poltoraha.pivka.entity.Quote;
import ttv.poltoraha.pivka.entity.Reader;
import ttv.poltoraha.pivka.entity.Reading;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.repository.QuoteRepository;
import ttv.poltoraha.pivka.repository.ReaderRepository;
import ttv.poltoraha.pivka.service.ReaderService;
import util.MyUtility;

@Service
@RequiredArgsConstructor
@Transactional
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final QuoteRepository quoteRepository; // переменная репозитория цитат

    @Override
    public void createQuote(String username, Integer book_id, String text) {
        val newQuote = new Quote();
        val reader = readerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Entity reader with id = " + username + " was not found"));
        val book = bookRepository.findById(book_id)
                .orElseThrow(() -> new EntityNotFoundException("Entity book with id = " + book_id + " was not found"));
        newQuote.setBook(book);
        newQuote.setText(text);
        newQuote.setReader(reader);

        // todo потенциально лучше сейвить quoteRepository. Чем меньше вложенностей у сохраняемой сущности - тем эффективнее это будет происходить.
        // Ну бля, тут уже есть ответ на вопрос чем лучше. Просто соглашусь. Мб дополню, что он при сохранении через репозиторий цитат работает с одной сущностью, а
        // через репозиторий читателей работает с несколькими сущностями (Book, reader, quote). Иногда (каюсь, подсмотрел) может произвести UPDATE, что не всегда может потребоваться.
        // Короче. Одна сущность - quoteRepository и в жопу не долбимся.
        quoteRepository.save(newQuote);
    }

    @Override
    public void addFinishedBook(String username, Integer bookId) {
        val reader = readerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Entity reader with id = " + username + " was not found"));

        val book = MyUtility.findEntityById(bookRepository.findById(bookId), "book", bookId.toString());

        val reading = new Reading();
        reading.setReader(reader);
        reading.setBook(book);

        reader.getReadings().add(reading);

        readerRepository.save(reader);
    }

    @Override
    public void createReader(String username, String password) {
        if (readerRepository.findByUsername(username).isPresent()) {
            throw new EntityExistsException(String.format("Reader with username \"%s\" already exist", username)); // Добавил проверочку на уникальность
        }
        val reader = new Reader();
        reader.setUsername(username);
        reader.setPassword(password);

        readerRepository.save(reader);
    }
}
