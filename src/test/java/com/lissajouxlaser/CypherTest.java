package com.lissajouxlaser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Cypher class.
 */
public class CypherTest {
    @Test
    public void sanitiseRemovesNonLetterBytes() {
        Cypher cypher = new Cypher("Hello, World!");
        assertTrue(cypher.toString().equals("HELLOWORLD"));
    }

    @Test
    public void encryptWorks() {
        Cypher cypher = new Cypher("attack at dawn");
        assertEquals("LXFOPVEFRNHR", cypher.encrypt("lemonlemonlemon"));
    }
}
