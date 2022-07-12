package com.lissajouxlaser;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Cypher class.
 */
public class CypherTest {
    @Test
    public void sanitiseRemovesNonLetterBytes() {
        Cypher cypher = new Cypher("Hello, World!");
        assertEquals("HELLOWORLD", cypher.toString());
    }

    @Test
    public void encryptWorks() {
        Cypher cypher = new Cypher("Attack at Dawn");
        try {
            assertEquals("LXFOPVEFRNHR", cypher.encrypt(new FileInputStream("lemon.txt"), 0));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void decryptWorks() {
        Cypher cypher = new Cypher("LXFO PVEF RNHR");
        try {
            assertEquals("ATTACKATDAWN", cypher.decrypt(new FileInputStream("lemon.txt"), 0));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void encryptReturnsEmptyWhenKeyTooShort() {
        Cypher cypher = new Cypher("Attack at Dawn");
        try {
            FileInputStream fileInput = new FileInputStream("lemon.txt");
            assertEquals("", cypher.encrypt(fileInput, (int) fileInput.getChannel().size() - 1));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }

    @Test
    public void decryptReturnsEmptyWhenKeyTooShort() {
        Cypher cypher = new Cypher("LXFOPVEFRNHR");
        try {
            FileInputStream fileInput = new FileInputStream("lemon.txt");
            assertEquals("", cypher.decrypt(fileInput, (int) fileInput.getChannel().size() - 1));
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e);
        }
    }
}
