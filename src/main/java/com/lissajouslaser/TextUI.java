package com.lissajouslaser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Provides the user interface.
 */
public class TextUI {
    private Scanner keyboardScan;
    private Cypher cypher;
    // Holds either "encrypt" or "decrpyt", to determine what
    // operation is performed on the text.
    private String operation;
    private int charNumber; // Number of characters to skip in text file.

    public TextUI(Scanner keyboardScan) {
        this.keyboardScan = keyboardScan;
    }

    public TextUI() {
        this(new Scanner(System.in));
    }

    /**
     * Starting point of UI.
     */
    public String start() {
        makeCypher();
        setOperation();
        return fileHandling();
    }

    private void makeCypher() {
        String input = "";

        while (true) {
            System.out.println("Type in the text to be encrypted/decrypted. "
                    + "Press Enter when done.\n");
            input = keyboardScan.nextLine();

            if (!input.isEmpty()) {
                cypher = new Cypher(input);
                break;
            }
            System.out.println("No text was entered. Please retry.");
        }
    }

    private void setOperation() {
        String input = "";
        while (true) {
            System.out.println("\nSelect and enter an option:");
            System.out.println("1. Encrypt text.");
            System.out.println("2. Decrypt text.");

            input = keyboardScan.nextLine().trim();
            if ("1".equals(input)) {
                operation = "encrypt";
                break;
            } else if ("2".equals(input)) {
                operation = "decrypt";
                break;
            }
            System.out.println("Invalid Entry.");
        }
    }

    // IO exceptions loop back to file prompt so user can
    // retry.
    private String fileHandling() {
        while (true) {
            System.out.println("\nEnter the filename containing key "
                    + "(file must be unformatted UTF-8 text)");
            String input = keyboardScan.nextLine().trim();
            String output;

            try (FileInputStream fileInput = new FileInputStream(input)) {
                setCharNumber(fileInput);

                if ("encrypt".equals(operation)) {
                    output = cypher.encrypt(fileInput, charNumber);
                } else {
                    output = cypher.decrypt(fileInput, charNumber);
                }
                // Cypher.encrypt() and Cypher.decrypt() returns null
                // if key is exhausted before all text characters
                // have been processed.
                if (output == null) {
                    throw new NoSuchElementException();
                }
                System.out.println(output);
                return output;

            } catch (FileNotFoundException e) {
                System.out.println("File does not exist. Please retry.");
            } catch (IOException e) {
                System.out.println("A file error has occurred. Please retry.");
            } catch (NoSuchElementException e) {
                System.out.println("The key is not long enough. Please retry.");
            }
        }
    }

    // Asks user to input the starting positing of key in the
    // file and performs input validation.
    private void setCharNumber(FileInputStream fileInput) throws IOException {
        System.out.println("Enter how many characters into the file "
                + "the key starts.");

        while (true) {
            String input = keyboardScan.nextLine().trim();

            if (input.matches("\\d+")) {
                charNumber = Integer.valueOf(input);
                break;
            } else {
                System.out.println("Not a valid number. Please retry.");
            }
        }
    }
}
