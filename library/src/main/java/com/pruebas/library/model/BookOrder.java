package com.pruebas.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="book_orders")
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_order_id_seq")
    private Long id;

    @OneToMany(mappedBy = "bookOrder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BookOrderQuantity> quantities = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
