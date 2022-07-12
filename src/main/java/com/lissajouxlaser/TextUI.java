package com.lissajouxlaser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Provides the user interface.
 */
public class TextUI {
    private Scanner keyboardScan;
    private Cypher cypher;
    private String operation;
    private int startingChar;

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

            input = keyboardScan.nextLine();
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

    // File related exceptions lead to recursion so user can
    // retry.
    private String fileHandling() {
        while (true) {
            System.out.println("\nEnter the filename containing key "
                    + "(file must be unformatted UTF-8 text)");
            String input = keyboardScan.nextLine();

            try (FileInputStream fileInput = new FileInputStream(input)) {
                setCharNumber(fileInput);

                if ("encrypt".equals(operation)) {
                    String output = cypher.encrypt(fileInput, startingChar);
                    System.out.println(output);
                    return output;
                } else {
                    String output = cypher.decrypt(fileInput, startingChar);
                    System.out.println(output);
                    return output;
                }
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist. Please retry.");
            } catch (IOException e) {
                System.out.println("A file error has occurred. Please retry.");
            }
        }
    }

    // Asks user to input the starting positing of key in the
    // file and performs in put validation.
    private void setCharNumber(FileInputStream fileInput) throws IOException {
        System.out.println("Enter how many characters into the file "
                + "the key starts.");

        while (true) {
            String input = keyboardScan.nextLine();
            if (input.matches("\\d+")) {

                if (Integer.valueOf(input) < fileInput.getChannel().size()) {
                    startingChar = Integer.valueOf(input);
                    break;
                } else {
                    System.out.println("The key starting position exceeds "
                            + "the file length. Please retry.");
                    setCharNumber(fileInput);
                }
            } else {
                System.out.println("Not a valid number. Please retry.");
            }
        }
    }
}
