package com.keyin;

import com.keyin.cli.service.ApiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class MainTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;
    @Mock
    private ApiService mockApiService;

    @Mock
    private Scanner mockScanner;

    @InjectMocks
    private Main mainInstance;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {

        closeable = MockitoAnnotations.openMocks(this);


        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        mainInstance = new Main(mockApiService, mockScanner);
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        closeable.close();
    }

    @Test
    void testViewAirportsByCityOption() {
        when(mockScanner.nextInt()).thenReturn(1).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockApiService, times(1)).getAirportsByCity();
        verifyNoMoreInteractions(mockApiService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("===== Airport CLI ====="));
        assertTrue(actualOutput.contains("Choose an option:"));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }

    @Test
    void testViewAircraftByPassengerOption() {
        when(mockScanner.nextInt()).thenReturn(2).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockApiService, times(1)).getAircraftByPassenger();
        verifyNoMoreInteractions(mockApiService);
    }

    @Test
    void testInvalidChoice() {
        when(mockScanner.nextInt()).thenReturn(99).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verifyNoInteractions(mockApiService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid choice."));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }

    @Test
    void testInputMismatchExceptionHandling() {
        when(mockScanner.nextInt())
                .thenThrow(new java.util.InputMismatchException())
                .thenReturn(0);
        when(mockScanner.next()).thenReturn("abc");
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verifyNoInteractions(mockApiService);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid input. Please enter a number."));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }
}
