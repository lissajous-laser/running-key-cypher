package com.lissajouxlaser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides functionality for encrytion and decryption of text.
 */
public class Cypher {
    private final int asciiA = (int) 'A';
    private final int asciiZ = (int) 'Z';
    private final int asciiRange = 26;
    private ArrayList<Integer> text; // text to be encrypted or decrypted

    Cypher(String text) {
        this.text = sanitiseString(text);
    }

    /**
     * Returns sanitised version of text.
     */
    @Override
    public String toString() {
        String str = "";
        for (Integer b : text) {
            str += (char) (int) b;
        }
        return str;
    }

    /**
     * Returns encrypted text.
     *
     * @return cyphertext as String. If the plaintext is longer
     *         than the key, an empty String is returned.
     */
    public String encrypt(FileInputStream fileInput, int startingChar)
            throws IOException {
        String cypherText = "";

        fileInput.skip(startingChar);

        for (int i = 0; i < text.size(); i++) {
            int sanitisedByte;

            while (true) {
                int byteFromKey = fileInput.read();
                // Test if end of file
                if (byteFromKey == -1) {
                    return "";
                }
                sanitisedByte = sanitiseByte((int) byteFromKey);
                // Test if byte representes a letter, discard
                // and read next byte if it isn't
                if (sanitisedByte != 1) {
                    break;
                }
            }

            int shiftedLetter = text.get(i) + (sanitisedByte - asciiA);

            if (shiftedLetter > asciiZ) {
                shiftedLetter -= asciiRange;
            }
            cypherText += (char) shiftedLetter;
        }
        return cypherText;
    }

    /**
     * Returns decrypted text.
     *
     * @return plaintext as String. If the cyphertext is longer
     *         than the key, an empty String is returned.
     */
    public String decrypt(FileInputStream fileInput, int startingChar)
            throws IOException {
        String plainText = "";

        fileInput.skip(startingChar);

        for (int i = 0; i < text.size(); i++) {
            int santisedByte;

            while (true) {
                int byteFromKey = fileInput.read();
                // Test if end of file
                if (byteFromKey == -1) {
                    return "";
                }
                santisedByte = sanitiseByte(byteFromKey);
                // Test if byte representes a letter, discard
                // and read next byte if it isn't
                if (santisedByte != 1) {
                    break;
                }
            }
            int shiftedLetter = text.get(i) - (santisedByte - asciiA);

            if (shiftedLetter < asciiA) {
                shiftedLetter += asciiRange;
            }
            plainText += (char) shiftedLetter;
        }
        return plainText;
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
    // Non-letter characters arg returns -1, otherwise
    // returns int representing uppercase letter.
    private int sanitiseByte(int b) {
        final int deltaUpperLowerCase = 32;

        if (b >= asciiA && b <= asciiZ) {
            return b;
        }
        if (b >= (int) 'a' && b <= (int) 'z') {
            return b - deltaUpperLowerCase;
        }
        return -1;
    }
}

// public String encrypt(String keyAsStr) {
// ArrayList<Byte> key = sanitise(keyAsStr);
// String cypherText = "";

// if (text.size() > key.size()) {
// return cypherText;
// }
// for (int i = 0; i < text.size(); i++) {
// int shiftedLetter = text.get(i) + (key.get(i) - asciiA);

// if (shiftedLetter > asciiZ) {
// shiftedLetter -= asciiRange;
// }
// cypherText += (char) shiftedLetter;
// }
// return cypherText;
// }

// public String decrypt(String keyAsStr) {
// ArrayList<Byte> key = sanitise(keyAsStr);
// String plainText = "";

// if (text.size() > key.size()) {
// return plainText;
// }
// for (int i = 0; i < text.size(); i++) {
// int shiftedLetter = text.get(i) - (key.get(i) - asciiA);

// if (shiftedLetter < asciiA) {
// shiftedLetter += asciiRange;
// }
// plainText += (char) shiftedLetter;
// }
// return plainText;
// }
