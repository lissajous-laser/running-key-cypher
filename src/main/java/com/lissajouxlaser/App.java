package com.lissajouxlaser;

/**
 * Class containing main().
 */
public final class App {

    private App() {}

    /**
     * A running key cypher.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.start();
    }
}
