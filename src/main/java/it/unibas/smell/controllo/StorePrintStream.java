package it.unibas.smell.controllo;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class StorePrintStream extends PrintStream {

    public static List<String> printList = new LinkedList<String>();

    public StorePrintStream(PrintStream org) {
        super(org);
    }

    @Override
    public void println(String line) {
        printList.add(line);
        super.println(line);
    }

    public void println(int line) {
        this.println(String.valueOf(line));
    }

    // And so on for double etc..
}
