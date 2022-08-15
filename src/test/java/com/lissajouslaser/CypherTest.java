package com.lissajouslaser;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Cypher class.
 */
public class CypherTest {

    @Test
    public void encryptWorks() {
        Cypher cypher = new Cypher("Attack at Dawn");
        try {
            assertEquals("LXFOPVEFRNHR", cypher.encrypt(new FileInputStream("lemons.txt"), 0));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void decryptWorks() {
        Cypher cypher = new Cypher("LXFO PVEF RNHR");
        try {
            assertEquals("ATTACKATDAWN", cypher.decrypt(new FileInputStream("lemons.txt"), 0));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void encryptReturnsNullWhenKeyTooShort() {
        Cypher cypher = new Cypher("Attack at Dawn");
        try {
            FileInputStream fileInput = new FileInputStream("lemons.txt");
            assertEquals(null, cypher.encrypt(fileInput, (int) fileInput.getChannel().size() - 1));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void decryptReturnsNullWhenKeyTooShort() {
        Cypher cypher = new Cypher("LXFOPVEFRNHR");
        try {
            FileInputStream fileInput = new FileInputStream("lemons.txt");
            assertEquals(null, cypher.decrypt(fileInput, (int) fileInput.getChannel().size() - 1));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }
}
