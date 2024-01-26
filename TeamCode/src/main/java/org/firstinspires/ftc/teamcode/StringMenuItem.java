package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class StringMenuItem implements MenuItem {
    String label = "";
    int index = 0;
    ArrayList<String> items = new ArrayList<>();

    public StringMenuItem(String label, ArrayList<String> items, int def) {
        this.label = label;
        this.items = items;
        this.index = def;
    }

    public void up() {
        index += 1;
        if (index >= items.size()) {
            index = 0;
        }
    }

    public void down() {
        index -= 1;
        if (index < 0) {
            index = items.size() - 1;
        }
    }

    public String getLabel() {
        return label;
    }

    public String getRepr() {
        return getLabel() + ": " + getStringValue();
    }

    public String getStringValue() {
        return items.get(index);
    }
}
