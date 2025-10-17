package com.habits.habittracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habits.habittracker.dto.request.HabitRequest;
import com.habits.habittracker.model.Habit;
import com.habits.habittracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HabitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HabitRepository habitRepository;

    @BeforeEach
    void setUp() {
        habitRepository.deleteAll(); // Limpia la tabla antes de cada prueba
    }

    @Test
    void testCreateHabit() throws Exception {
        HabitRequest request = new HabitRequest();
        request.setNombre("Beber agua");
        request.setCategoria("Salud");
        request.setFrecuencia("Diaria");
        request.setHora("08:00");

        mockMvc.perform(post("/api/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Beber agua"))
                .andExpect(jsonPath("$.categoria").value("Salud"));
    }

    @Test
    void testGetAllHabits() throws Exception {
        Habit habit = new Habit();
        habit.setNombre("Dormir bien");
        habit.setCategoria("Salud");
        habit.setFrecuencia("Diaria");
        habit.setHora("22:00");
        habitRepository.save(habit);

        mockMvc.perform(get("/api/habits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Dormir bien"));
    }

    @Test
    void testUpdateHabit() throws Exception {
        Habit habit = new Habit();
        habit.setNombre("Leer");
        habit.setCategoria("Personal");
        habit.setFrecuencia("Diaria");
        habit.setHora("21:00");
        habitRepository.save(habit);

        HabitRequest update = new HabitRequest();
        update.setNombre("Leer 30 minutos");
        update.setCategoria("Personal");
        update.setFrecuencia("Diaria");
        update.setHora("21:30");

        mockMvc.perform(put("/api/habits/" + habit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Leer 30 minutos"));
    }

    @Test
    void testDeleteHabit() throws Exception {
        Habit habit = new Habit();
        habit.setNombre("Meditar");
        habit.setCategoria("Bienestar");
        habit.setFrecuencia("Diaria");
        habit.setHora("07:00");
        habitRepository.save(habit);

        mockMvc.perform(delete("/api/habits/" + habit.getId()))
                .andExpect(status().isNoContent());

        List<Habit> remaining = habitRepository.findAll();
        assertThat(remaining).isEmpty();
    }
}