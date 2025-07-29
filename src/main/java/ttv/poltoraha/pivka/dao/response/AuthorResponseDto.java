package ttv.poltoraha.pivka.dao.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthorResponseDto {
    private String fullName;
    private List<BookResponseDto> books;
}
