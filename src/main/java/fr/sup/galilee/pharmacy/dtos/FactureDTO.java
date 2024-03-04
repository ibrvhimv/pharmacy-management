package fr.sup.galilee.pharmacy.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.sup.galilee.pharmacy.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class FactureDTO {
    private Long id;
    private float value;
    private Instant date;
    private User user;
    public interface FactureCreation{}
    public interface FactureUpdate{}
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    public Instant getDate() {
        return this.date;
    }
}
