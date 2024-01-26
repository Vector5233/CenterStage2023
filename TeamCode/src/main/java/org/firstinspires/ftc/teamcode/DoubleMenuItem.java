package org.firstinspires.ftc.teamcode;

public class DoubleMenuItem implements MenuItem {
    double value;
    double max;
    double min;
    double increment;
    String label;

    public DoubleMenuItem(String label, double def, double max, double min, double increment) {
        this.label = label;
        this.max = max;
        this.min = min;
        this.increment = increment;
        this.value = def;
    }

    public void up() {
        value += increment;
        if (value > max) {
            value = max;
        }
    }

    public void down() {
        value -= increment;
        if (value < min) {
            value = min;
        }
    }

    public String getLabel() {
        return label;
    }

    public String getRepr() {
        return getLabel() + ": " + value;
    }

    public double getDoubleValue() {
        return value;
    }
}
