package com.habits.habittracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "habitos")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private String frecuencia; // diaria, semanal, etc.
    private String hora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;
}