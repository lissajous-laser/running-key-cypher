package com.lissajouxlaser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for TextUI class.
 */
public class TextUITest {

    @Test
    public void correctKeyboardInputsWorks() {
        try (Scanner scanner = new Scanner(Paths.get("SimKeyboardInput.txt"))) {
            assertEquals("QVENKSNAHCUMFUJJPFUB", new TextUI(scanner).start());
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }

    // Simulates keyboard input that makes different types of
    // input errors.
    @Test
    public void keyboardInputWithRetriesWorks() {
        try (Scanner scanner = new Scanner(Paths.get("SimKeyboardInputRetries.txt"))) {
            assertEquals("BEWARETHEIDESOFMARCH", new TextUI(scanner).start());
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }

    // @Test
    // public void inputtedOutOfBoundsIndexForFileSkipHandled() {
    //     try (Scanner scanner = new Scanner(Paths.get("SimKeyboardInput3.txt"))) {
    //         assertEquals("LXFOPVEFRNHR", new TextUI(scanner).start());
    //     } catch (IOException e) {
    //         System.out.println("Error occurred");
    //     }
    // }
}
