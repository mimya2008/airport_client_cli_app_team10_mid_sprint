package com.keyin;

import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MainTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;
    @Mock
    private RESTClient mockRESTClient;

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
        mainInstance = new Main(mockRESTClient, mockScanner);
    }

    @AfterEach
    public void tearDown() throws Exception {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
        closeable.close();
    }

    @Test
    void testViewAllAirportsOption() {
        when(mockScanner.nextInt()).thenReturn(1).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getAllAirports();
        verifyNoMoreInteractions(mockRESTClient);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("===== Airport CLI ====="));
        assertTrue(actualOutput.contains("Choose an option:"));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }

    @Test
    void testViewPassengersWithAircraftOption() {
        when(mockScanner.nextInt()).thenReturn(2).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getPassengersWithAircraft();
        verifyNoMoreInteractions(mockRESTClient);
    }

    @Test
    void testViewAircraftWithAirportsOption() {
        when(mockScanner.nextInt()).thenReturn(3).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getAircraftWithAirports();
        verifyNoMoreInteractions(mockRESTClient);
    }

    @Test
    void testViewPassengerAirportUsageOption() {
        when(mockScanner.nextInt()).thenReturn(4).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getPassengerAirportUsage();
        verifyNoMoreInteractions(mockRESTClient);
    }

    @Test
    void testInvalidChoice() {
        when(mockScanner.nextInt()).thenReturn(99).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verifyNoInteractions(mockRESTClient);

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

        verifyNoInteractions(mockRESTClient);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid input. Please enter a number."));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }
    @Test
    void testMultipleValidChoicesInSequence() {
        when(mockScanner.nextInt()).thenReturn(1, 2, 3, 0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getAllAirports();
        verify(mockRESTClient, times(1)).getPassengersWithAircraft();
        verify(mockRESTClient, times(1)).getAircraftWithAirports();
        verifyNoMoreInteractions(mockRESTClient);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("View all airports"));
        assertTrue(actualOutput.contains("View passengers with their aircraft"));
        assertTrue(actualOutput.contains("View aircraft with their airports"));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }

    @Test
    void testInvalidThenValidChoiceSequence() {
        when(mockScanner.nextInt()).thenReturn(99, 4, 0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();

        verify(mockRESTClient, times(1)).getPassengerAirportUsage();
        verifyNoMoreInteractions(mockRESTClient);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Invalid choice."));
        assertTrue(actualOutput.contains("View passenger airport usage"));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }

    @Test
    void testExitImmediately() {
        when(mockScanner.nextInt()).thenReturn(0);
        when(mockScanner.nextLine()).thenReturn("");

        mainInstance.run();
        verifyNoInteractions(mockRESTClient);

        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("===== Airport CLI ====="));
        assertTrue(actualOutput.contains("Exiting Airport CLI. Goodbye!"));
    }
}
