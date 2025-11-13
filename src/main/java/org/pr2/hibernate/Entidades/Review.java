package org.pr2.hibernate.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
    private Game game;  // atributo singular corregido

    public Review() {
    }

    public Review(Game game, String reviewText) {
        this.game = game;
        this.reviewText = reviewText;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
