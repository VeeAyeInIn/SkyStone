package org.firstinspires.ftc.teamcode.archive.util;

public enum Direction {

    FORWARD("forward"),
    BACKWARD("backward"),
    RIGHT("right"),
    LEFT("left");

    private final String name;

    Direction(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Direction: " + name;
    }
}