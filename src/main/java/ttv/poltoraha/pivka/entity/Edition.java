package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name="Edition")
@Data
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String format;

    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
