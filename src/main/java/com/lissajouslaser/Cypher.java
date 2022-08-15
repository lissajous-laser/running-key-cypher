package com.lissajouslaser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides functionality for encrytion and decryption of text.
 */
public class Cypher {
    private final int utfA = (int) 'A';
    private final int utfZ = (int) 'Z';
    private final int utfRange = 26;
    private ArrayList<Integer> text; // text to be encrypted or decrypted

    Cypher(String text) {
        this.text = sanitiseString(text);
    }

    /**
     * Returns encrypted text.
     * If the plaintext is longer
     * than the key, null is returned.
     */
    public String encrypt(FileInputStream fileInput, int startingChar)
            throws IOException {
        StringBuilder cypherText = new StringBuilder();

        fileInput.skip(startingChar);

        for (int i = 0; i < text.size(); i++) {
            int sanitisedByte;

            while (true) {
                int byteFromKey = fileInput.read();
                // Test if end of file
                if (byteFromKey == -1) {
                    return null;
                }
                sanitisedByte = sanitiseByte((int) byteFromKey);
                // Test if byte representes a letter, discard
                // and read next byte if it isn't
                if (sanitisedByte != -1) {
                    break;
                }
            }

            int shiftedLetter = text.get(i) + (sanitisedByte - utfA);

            if (shiftedLetter > utfZ) {
                shiftedLetter -= utfRange;
            }
            cypherText.append((char) shiftedLetter);
        }
        return cypherText.substring(0);
    }

    /**
     * Returns encrypted text.
     * If the cyphertext is longer
     * than the key, null is returned.
     */
    public String decrypt(FileInputStream fileInput, int startingChar)
            throws IOException {
        StringBuilder plainText = new StringBuilder(text.size());

        fileInput.skip(startingChar);

        for (int i = 0; i < text.size(); i++) {
            int santisedByte;

            while (true) {
                int byteFromKey = fileInput.read();
                // Test if end of file
                if (byteFromKey == -1) {
                    return null;
                }
                santisedByte = sanitiseByte(byteFromKey);
                // Test if byte representes a letter, discard
                // and read next byte if it isn't
                if (santisedByte != -1) {
                    break;
                }
            }
            int shiftedLetter = text.get(i) - (santisedByte - utfA);

            if (shiftedLetter < utfA) {
                shiftedLetter += utfRange;
            }
            plainText.append((char) shiftedLetter);
        }
        return plainText.substring(0);
    }

    private ArrayList<Integer> sanitiseString(String str) {
        byte[] strAsBytes = str.getBytes();
        ArrayList<Integer> onlyLetters = new ArrayList<>();

        // add only bytes the represent letters
        for (byte b : strAsBytes) {
            int sanitisedByte = sanitiseByte(b);
            if (sanitisedByte != -1) {
                onlyLetters.add(sanitisedByte);
            }
        }
        return onlyLetters;
    }

    // Parameter is of type int because a byte read from
    // FileInputStream is returned as int.
    // Passing a non-letter character returns -1, otherwise
    // returns an int representing an uppercase letter.
    private int sanitiseByte(int b) {
        final int deltaUpperLowerCase = 32;

        if (b >= utfA && b <= utfZ) {
            return b;
        }
        if (b >= (int) 'a' && b <= (int) 'z') {
            return b - deltaUpperLowerCase;
        }
        return -1;
    }
}
