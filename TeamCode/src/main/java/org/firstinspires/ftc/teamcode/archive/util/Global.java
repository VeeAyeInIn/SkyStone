package org.firstinspires.ftc.teamcode.archive.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * @author Connor Vann
 */
public final class Global {

    private static OpMode opMode;

    /**
     * Initializes the global values that can be accessed by others.
     *
     * @param opMode get data from this {@link OpMode}
     */
    public static void init(OpMode opMode) {
        Global.opMode = opMode;
    }

    /**
     * Flushes the data from the global values.
     */
    public static void flush() {
        opMode = null;
    }

    public static void getRobotPosition() {
        // TODO implement a getter for the robot position
    }

    public static void getRobotStatus() {
        // TODO implement a getter to retrieve a string from telemetry
    }

    /**
     * Checks if the global values were initialized.
     *
     * @return initialization status
     */
    public static boolean isInitialized() {
        return opMode != null;
    }
}
