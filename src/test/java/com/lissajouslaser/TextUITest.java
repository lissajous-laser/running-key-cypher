package com.lissajouslaser;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TextUI class.
 */
public class TextUITest {

    @Test
    public void correctKeyboardInputsWorks() {
        String simKeyboardInput =
                "Beware the Ides of March.\n"
                + "1\n"
                + "BromidePrinting.txt\n"
                + "2500\n";
        Scanner scanner = new Scanner(simKeyboardInput);
        assertEquals("QVENKSNAHCUMFUJJPFUB", new TextUI(scanner).start());
    }

    // Simulates keyboard input that makes different types of
    // input errors.
    @Test
    public void keyboardInputWithRetriesWorks() {
        String simKeyboardInput =
                "QVEN|KSNA/HCUM FUJJPFUB|\n"
                + "-1\n" // Invalid entry
                + "2\n"
                + "isthisafile.txt\n" // Invalid entry
                + "BromidePrinting.txt\n"
                + "-1\n" // Invalid entry
                + "2500\n";
        Scanner scanner = new Scanner(simKeyboardInput);
        assertEquals("BEWARETHEIDESOFMARCH", new TextUI(scanner).start());
    }

    @Test
    public void exceptionFromOutOfBoundsIndexForFileCaught() {
        String simKeyboardInput =
                "Attack at Dawn.\n"
                + "1\n"
                + "lemons.txt\n"
                // Index does not give a long enough key.
                + "5\n"
                + "lemons.txt\n" // Re-enter filename.
                + "0\n"; // Retryed valid index.
        Scanner scanner = new Scanner(simKeyboardInput);
        assertEquals("LXFOPVEFRNHR", new TextUI(scanner).start());
    }
}
