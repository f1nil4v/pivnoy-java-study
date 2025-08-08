package ttv.poltoraha.pivka.dao.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookResponseDto {
    private String article;
    private String genre;
    private Double rating;
    private String tags;
}
