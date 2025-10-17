package com.habits.habittracker.controller;

import com.habits.habittracker.dto.request.HabitRequest;
import com.habits.habittracker.dto.response.HabitResponse;
import com.habits.habittracker.model.Habit;
import com.habits.habittracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*")
public class HabitController {

    @Autowired
    private HabitService habitService;

    // GET all habits
    @GetMapping
    public List<HabitResponse> getAllHabits() {
        return habitService.getAllHabits()
                .stream()
                .map(habit -> new HabitResponse(
                        habit.getId(),
                        habit.getNombre(),
                        habit.getCategoria(),
                        habit.getFrecuencia(),
                        habit.getHora()
                ))
                .collect(Collectors.toList());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable Long id) {
        Habit habit = habitService.getHabitById(id)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado con id: " + id));

        HabitResponse response = new HabitResponse(
                habit.getId(),
                habit.getNombre(),
                habit.getCategoria(),
                habit.getFrecuencia(),
                habit.getHora()
        );
        return ResponseEntity.ok(response);
    }

    // POST
    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody HabitRequest request) {
        Habit habit = new Habit();
        habit.setNombre(request.getNombre());
        habit.setCategoria(request.getCategoria());
        habit.setFrecuencia(request.getFrecuencia());
        habit.setHora(request.getHora());

        Habit saved = habitService.createHabit(habit);
        HabitResponse response = new HabitResponse(
                saved.getId(),
                saved.getNombre(),
                saved.getCategoria(),
                saved.getFrecuencia(),
                saved.getHora()
        );
        return ResponseEntity.ok(response);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(@PathVariable Long id, @Valid @RequestBody HabitRequest request) {
        Habit updated = habitService.updateHabit(id, request);
        HabitResponse response = new HabitResponse(
                updated.getId(),
                updated.getNombre(),
                updated.getCategoria(),
                updated.getFrecuencia(),
                updated.getHora()
        );
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }
}