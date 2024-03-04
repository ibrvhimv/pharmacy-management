package fr.sup.galilee.pharmacy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductCart
{
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private  Product product;

}
