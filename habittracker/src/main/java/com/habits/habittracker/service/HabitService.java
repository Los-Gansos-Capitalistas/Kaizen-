package com.habits.habittracker.service;

import com.habits.habittracker.dto.request.HabitRequest;
import com.habits.habittracker.model.Habit;
import com.habits.habittracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Optional<Habit> getHabitById(Long id) {
        return habitRepository.findById(id);
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, HabitRequest habitDetails) {
        return habitRepository.findById(id).map(habit -> {
            habit.setNombre(habitDetails.getNombre());
            habit.setCategoria(habitDetails.getCategoria());
            habit.setFrecuencia(habitDetails.getFrecuencia());
            habit.setHora(habitDetails.getHora());
            return habitRepository.save(habit);
        }).orElseThrow(() -> new RuntimeException("Hábito no encontrado con id: " + id));
    }

    public void deleteHabit(Long id) {
        if (!habitRepository.existsById(id)) {
            throw new RuntimeException("Hábito no encontrado con id: " + id);
        }
        habitRepository.deleteById(id);
    }
}