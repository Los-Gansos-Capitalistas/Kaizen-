package com.habits.habittracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitResponse {
    private Long id;
    private String nombre;
    private String categoria;
    private String frecuencia;
    private String hora;
}