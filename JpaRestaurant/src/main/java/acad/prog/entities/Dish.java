package acad.prog.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Getter
@Setter
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "discount")
    private Boolean discount;

    public Dish(String title, Double price, Integer weight, Boolean discount) {
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public Dish() {

    }
}