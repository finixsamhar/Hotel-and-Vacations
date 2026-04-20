package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void vacationDateIsCorrect() {
// Mock data
        long start = 1714521600000L; // May 1, 2024
        long end = 1714435200000L; // April 30, 2024 (Invalid because it's before start)

// The test condition: end should NOT be before start
        assertTrue("End date should be after start date", end > start);
    }
}