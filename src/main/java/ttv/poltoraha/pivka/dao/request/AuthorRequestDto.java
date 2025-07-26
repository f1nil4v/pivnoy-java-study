package ttv.poltoraha.pivka.dao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequestDto {
    private String fullName;
    // Нахуй авг рейтинг не нужен. Потом ИРЛ добавляться должен
}
