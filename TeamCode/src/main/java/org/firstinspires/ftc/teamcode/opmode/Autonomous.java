package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.util.Log;

import java.util.Arrays;
import java.util.Calendar;

/**
 * A framework for creating a customized Autonomous class, this class will give you the means to do
 * so, however its up to you for the specifics.
 */
public abstract class Autonomous extends LinearOpMode {

    // We can load TFOD assets through this
    public static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    public static final String TFOD_STONE_ASSET = "Stone";
    public static final String TFOD_SKYSTONE_ASSET = "Skystone";

    // Vuforia
    private final VuforiaLocalizer.Parameters vuforiaParameters;
    private final VuforiaTrackables vuforiaTrackables;
    private final VuforiaLocalizer vuforiaLocalizer;

    // Tensor Flow Object Detection
    private final TFObjectDetector tfod;

    // Keep track of time
    private final ElapsedTime elapsedTime;

    /**
     * Create the framework using the Vuforia key to initialize creation.
     *
     * @param vuforiaKey the key being used by the program
     */
    public Autonomous(String vuforiaKey) {
        super();

        elapsedTime = new ElapsedTime();

        vuforiaParameters = new VuforiaLocalizer.Parameters();
        vuforiaParameters.vuforiaLicenseKey = vuforiaKey;
        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(vuforiaParameters);
        vuforiaTrackables = vuforiaLocalizer.loadTrackablesFromAsset("SkyStone");

        // Tensor Flow Object Detector Initialization
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforiaLocalizer);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, TFOD_STONE_ASSET, TFOD_SKYSTONE_ASSET);
        } else {
            // Identify error to the class
            severe("Error creating Tensor Flow Object Detector");
            tfod = null; // We can't do anything else, but we MUST assign a value to it
        }
    }

    /**
     * Sets the power for each of the defined motors.
     *
     * @param power  the power from 0 to 1 which the motors are set to
     * @param motors the motors to set the power of
     */
    public void setPower(double power, DcMotor... motors) {

        // Loop through each motor
        for (DcMotor motor : motors) {

            // If power is not more than 0 and less than 1, it will cap it at 0 or 1 respectively
            motor.setPower(power < 0 ? 0 : power > 1 ? 1 : power);
        }
    }

    /**
     * Sets the mode for each of the defined motors.
     *
     * @param runMode the mode to set the motor to
     * @param motors  the motors which are having their modes set
     */
    public void setMode(DcMotor.RunMode runMode, DcMotor... motors) {

        // Loop through each motor
        for (DcMotor motor : motors) {

            // Update the RunMode for each motor
            motor.setMode(runMode);
        }
    }

    /**
     * Logs data to telemetry instance on the phone. There are several ways to do so:
     * <li>{@link #info(Object[])}</li>
     * <li>{@link #error(Object[])}</li>
     * <li>{@link #warning(Object[])}</li>
     * <li>{@link #severe(Object[])}</li>
     * <li>{@link #debug(Object[])}</li>
     *
     * @param log  the type of logging to do
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item log(Log log, T... data) {
        if (data == null) {
            // We want to make sure we don't get a NullPointerException
            return telemetry.addData("<" + Calendar.getInstance().getTime().toString() + "> " + log.name(), "NULL");
        } else if (data.length == 1) {
            // If it is only one entry, then send it as a singular object
            return telemetry.addData("<" + Calendar.getInstance().getTime().toString() + "> " + log.name(), data[0].toString());
        } else {
            // Send it as an array
            return telemetry.addData("<" + Calendar.getInstance().getTime().toString() + "> " + log.name(), Arrays.toString(data));
        }
    }

    /**
     * Sends informational data to the telemetry.
     *
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item info(T... data) {
        return log(Log.INFO, data);
    }

    /**
     * Sends error information to the telemetry.
     *
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item error(T... data) {
        return log(Log.ERROR, data);
    }

    /**
     * Sends warnings to the telemetry.
     *
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item warning(T... data) {
        return log(Log.WARNING, data);
    }

    /**
     * Sends severe warnings to the telemetry.
     *
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item severe(T... data) {
        return log(Log.SEVERE, data);
    }

    /**
     * Sends debug information to the telemetry.
     *
     * @param data what is being logged
     * @param <T>  inherently calls <i>T extends Object</i> meaning <i>T... data</i> is an array of Objects
     * @return the telemetry item that will be displayed, needs to be updated with {@link #updateTelemetry(Telemetry)}
     */
    public <T> Telemetry.Item debug(T... data) {
        return log(Log.DEBUG, data);
    }

    /**
     * @return current instance of the Vuforia parameters
     */
    public VuforiaLocalizer.Parameters getVuforiaParameters() {
        return vuforiaParameters;
    }

    /**
     * @return current instance of the Vuforia trackables
     */
    public VuforiaTrackables getVuforiaTrackables() {
        return vuforiaTrackables;
    }

    /**
     * @return current instance of the Vuforia localizer
     */
    public VuforiaLocalizer getVuforiaLocalizer() {
        return vuforiaLocalizer;
    }

    /**
     * @return current instance of the Tensor Flow Object Detection
     */
    public TFObjectDetector getTfod() {
        return tfod;
    }

    /**
     * @return current instance of the elapsed time
     */
    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }
}