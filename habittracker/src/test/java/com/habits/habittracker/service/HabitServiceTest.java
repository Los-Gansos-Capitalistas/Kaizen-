package com.habits.habittracker.service;

import com.habits.habittracker.dto.request.HabitRequest;
import com.habits.habittracker.model.Habit;
import com.habits.habittracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitService habitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHabits() {
        Habit habit1 = new Habit();
        habit1.setNombre("Beber agua");
        Habit habit2 = new Habit();
        habit2.setNombre("Dormir 8 horas");

        when(habitRepository.findAll()).thenReturn(Arrays.asList(habit1, habit2));

        List<Habit> habits = habitService.getAllHabits();

        assertEquals(2, habits.size());
        assertEquals("Beber agua", habits.get(0).getNombre());
        verify(habitRepository, times(1)).findAll();
    }

    @Test
    void testCreateHabit() {
        Habit habit = new Habit();
        habit.setNombre("Ejercitarse");
        when(habitRepository.save(any(Habit.class))).thenReturn(habit);

        Habit saved = habitService.createHabit(habit);

        assertNotNull(saved);
        assertEquals("Ejercitarse", saved.getNombre());
        verify(habitRepository, times(1)).save(any(Habit.class));
    }

    @Test
    void testUpdateHabit_Success() {
        Habit habit = new Habit();
        habit.setId(1L);
        habit.setNombre("Beber agua");

        HabitRequest updateRequest = new HabitRequest();
        updateRequest.setNombre("Correr");
        updateRequest.setCategoria("Fitness");
        updateRequest.setFrecuencia("Diaria");
        updateRequest.setHora("07:00");

        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitRepository.save(any(Habit.class))).thenReturn(habit);

        Habit updated = habitService.updateHabit(1L, updateRequest);

        assertEquals("Correr", updated.getNombre());
        verify(habitRepository, times(1)).save(habit);
    }

    @Test
    void testUpdateHabit_NotFound() {
        HabitRequest request = new HabitRequest();
        request.setNombre("Correr");

        when(habitRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                habitService.updateHabit(99L, request)
        );

        assertEquals("Hábito no encontrado con id: 99", exception.getMessage());
    }

    @Test
    void testDeleteHabit_Success() {
        when(habitRepository.existsById(1L)).thenReturn(true);

        habitService.deleteHabit(1L);

        verify(habitRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteHabit_NotFound() {
        when(habitRepository.existsById(5L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                habitService.deleteHabit(5L)
        );

        assertEquals("Hábito no encontrado con id: 5", exception.getMessage());
    }
}