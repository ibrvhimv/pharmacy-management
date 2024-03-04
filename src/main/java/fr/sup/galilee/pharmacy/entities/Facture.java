package fr.sup.galilee.pharmacy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Facture
{
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "value", nullable = false)
    private float value;
    @Column(name = "date", nullable = false)
    private Instant date;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
