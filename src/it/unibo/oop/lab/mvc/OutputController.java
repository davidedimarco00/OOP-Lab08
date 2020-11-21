package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;

public final class OutputController implements Controller {

    private final List<String> strings;
    private String currentString;

    public OutputController() {
        this.strings = new ArrayList<>();
    }

    public void setStringToPrint(final String out) {
        if (out != null) {
            this.strings.add(out);
            currentString = out;
            return;
        }
    }

    public String getStringToPrint() {
        return currentString;
    }

    public List<String> getStringsList() {
        return List.copyOf(strings);
    }

    public void printCurrentString() {
        if (currentString != null) {
            System.out.println(currentString);
        }
    }
}
