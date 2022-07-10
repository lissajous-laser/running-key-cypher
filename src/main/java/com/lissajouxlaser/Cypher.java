package com.lissajouxlaser;

import java.util.ArrayList;

/**
 * Provides functions for encrytion and decryption of text.
 */
public class Cypher {
    private final int asciiA = (int) 'A';
    private final int asciiZ = (int) 'Z';
    private final int asciiRange = 26;
    private ArrayList<Byte> text; // text to be encrypted or decrypted    

    Cypher(String text) {
        this.text = sanitise(text);
    }

    public void setText(String text) {
        this.text = sanitise(text);
    }

    /**
     * Returns sanitised version of text.
     */
    @Override
    public String toString() {
        String str = "";
        for (Byte b: text) {
            str += (char) (int) b;
        }
        return str;
    }

    /**
     * Returns encrypted text from the provided key.
     * @param keyAsList
     * @return cyphertext as String. If the plaintext is longer
     * then the key, an empty string is returned.
     */
    public String encrypt(String keyAsList) {
        ArrayList<Byte> key = sanitise(keyAsList);
        String cypherText = "";

        if (text.size() > key.size()) {
            return cypherText;
        }
        for (int i = 0; i < text.size(); i++) {
            int shiftedLetter = text.get(i) + (key.get(i) - asciiA);

            if (shiftedLetter > asciiZ) {
                shiftedLetter -= asciiRange;
            }
            cypherText += (char) shiftedLetter;
        }
        return cypherText;
    }

    public ArrayList<Byte> decrypt(String key) {
        //
        return null;
    }

    private ArrayList<Byte> sanitise(String str) {
        byte[] strAsBytes = str.toUpperCase().getBytes();
        ArrayList<Byte> onlyLetters = new ArrayList<>();

        // add only bytes the represent letters
        for (byte b: strAsBytes) {
            if (b >= asciiA && b <= asciiZ) {
                onlyLetters.add(b);
            }
        }
        return onlyLetters;
    }

    public static void main(String[] args) {
        Cypher cypher = new Cypher("Hello, World!");
        System.out.println(cypher);
    }
}
// case insensitive
// if key is 'a', no change
// if key is 'b', shift 1 right
