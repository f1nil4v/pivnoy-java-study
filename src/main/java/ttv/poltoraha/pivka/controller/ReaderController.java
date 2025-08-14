package ttv.poltoraha.pivka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.serviceImpl.ReaderServiceImpl;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderServiceImpl readerService;
    // RequestParam - это штука в запросе, которая выглядит так: localhost:8080/reader/create?username=value?password=value
    // Очевидно, что передавать пароли таким способом - небезопасно :)
    @PostMapping("/create")
    public ResponseEntity<String> createReader(@RequestParam String username, @RequestParam String password) {
        readerService.createReader(username, password);
        return ResponseEntity.ok("Reader created successfully");
    }
    @PostMapping("/addQuote")
    public ResponseEntity<String> addQuote(@RequestParam String username,
                                           @RequestParam Integer book_id,
                                           @RequestParam String text) {
        readerService.createQuote(username, book_id, text);
        return ResponseEntity.ok("Quote added successfully");
    }

    @PostMapping("/addFinishedBook")
    public ResponseEntity<String> addFinishedBook(@RequestParam String username,
                                                  @RequestParam Integer book_id) {
        readerService.addFinishedBook(username, book_id);
        return ResponseEntity.ok("Finished book added successfully");
    }

}
