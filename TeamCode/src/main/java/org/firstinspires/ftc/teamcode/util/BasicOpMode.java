package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is an advanced form of the OpMode provided by the FTC SDK, with more utility methods for
 * reading and writing data. You can simply use this like a normal OpMode, and do the following:
 * <i>YourOpMode extends AdvancedOpMode</i> to use it.
 */

// Prevent the warning "unused" from displaying
@SuppressWarnings("unused")

// Necessary to be seen as a proper instruction for the robot
@TeleOp(name = "The name of your OpMode", group = "The group that the OpMode is in")

// Prevents the TeleOp from being seen
@Disabled

public abstract class BasicOpMode extends OpMode {

    // Expansion Hub 1
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftRear;
    private final DcMotor rightRear;

    // Expansion Hub 2
    // private final DcMotor arm;
    // private final DcMotor leftGear;
    // private final DcMotor rightGear;

    // Utility
    private final ElapsedTime elapsedTime;

    /**
     * This is for instructions that will be run on instantiation, in order to allow for some things
     * to run before the robot is initialized, as the user may want to customize the initialization
     * process via {@link Override}.
     */
    public BasicOpMode() {

        // Technically not needed, however good coding habit to remember that this is a subclass,
        // and a superclass exists in which this one inherits from.
        super( /* If the parent constructor has parameters, put them in here */);

        // Assign on initialization of the class itself instead of the initialization of the robot
        // in order to let the subclass of this manipulate the motors without assigning them.
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        // arm = hardwareMap.dcMotor.get("arm");
        // leftGear = hardwareMap.dcMotor.get("leftGear");
        // rightGear = hardwareMap.dcMotor.get("rightGear");

        // It's a good idea to keep track of time for debugging
        elapsedTime = new ElapsedTime();
    }

//================================================================================================//
//   This deals with the DcMotors so the subclass doesn't have to, save for reading the values    //
//================================================================================================//

    /**
     * Sets the universal power for each wheel.
     *
     * @param power the power of each wheels
     */
    public void setWheelPower(final double power) {
        setWheelPower(power, power, power, power);
    }

    /**
     * Sets the power for each individual wheel.
     *
     * @param leftFrontPower  the power of the left front wheel
     * @param rightFrontPower the power of the right front wheel
     * @param leftRearPower   the power of the left rear wheel
     * @param rightRearPower  the power of the right rear wheel
     */
    public void setWheelPower(final double leftFrontPower, final double rightFrontPower,
                              final double leftRearPower, final double rightRearPower) {
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftRear.setPower(leftRearPower);
        rightRear.setPower(rightRearPower);
    }

    /**
     * Sets the mode for each of the wheels.
     *
     * @param mode the wheel motor mode
     */
    public void setWheelMode(DcMotor.RunMode mode) {
        leftFront.setMode(mode);
        rightFront.setMode(mode);
        leftRear.setMode(mode);
        rightRear.setMode(mode);
    }

//================================================================================================//
// Telemetry is a useful tool for debugging, and writing data to the phone so the user can see it //
//================================================================================================//

    /**
     * Logs data under the specified log type, to the phone.
     *
     * @param type    what kind of log is it
     * @param content what is being logged
     */
    public void log(Log type, String content) {
        telemetry.log().add("%s %.2f: %s", type.name(), elapsedTime.seconds(), content);
    }

    /**
     * Logs info to the phone.
     *
     * @param content what is being logged
     */
    public void info(String content) {
        log(Log.INFO, content);
    }

    /**
     * Logs debug data to the phone.
     *
     * @param content what is being logged
     */
    public void debug(String content) {
        log(Log.DEBUG, content);
    }

    /**
     * Logs warnings to the phone.
     *
     * @param content what is being logged
     */
    public void warning(String content) {
        log(Log.WARNING, content);
    }

    /**
     * Logs errors to the phone.
     *
     * @param content what is being logged
     */
    public void error(String content) {
        log(Log.ERROR, content);
    }

    /**
     * Logs severe warnings/errors to the phone.
     *
     * @param content what is being logged
     */
    public void severe(String content) {
        log(Log.SEVERE, content);
    }

    /**
     * Getter for the left front wheel motor.
     *
     * @return the left front wheel motor
     */
    public DcMotor getLeftFront() {
        return leftFront;
    }

//================================================================================================//
// The below methods are simply getters for the individual motors, they have no real significance //
//================================================================================================//

    /**
     * Getter for the right front wheel motor.
     *
     * @return the right front wheel motor
     */
    public DcMotor getRightFront() {
        return rightFront;
    }

    /**
     * Getter for the left rear wheel motor.
     *
     * @return the left rear wheel motor
     */
    public DcMotor getLeftRear() {
        return leftRear;
    }

    /**
     * Getter for the right rear wheel motor.
     *
     * @return the right rear wheel motor
     */
    public DcMotor getRightRear() {
        return rightRear;
    }

    /**
     * Getter for the elapsed time.
     *
     * @return elapsed time
     */
    public ElapsedTime getElapsedTime() {
        return elapsedTime;
    }

//================================================================================================//
//               Nested classes, enumerators, interfaces, and anything of that type               //
//================================================================================================//

    /**
     * For determining what type of data will be recorded. Names are relatively self-explanatory,
     * such as INFO for information, DEBUG for debugging data, etc.
     * <p>
     * Examples may include...
     * INFO - Status of the robot
     * DEBUG - Encoder positions; What has recently booted
     * WARNING - Can something go wrong
     * ERROR - Something did go wrong
     * SEVERE - The robot is physically inhibited and/or at risk of breaking or not working
     * <p>
     * Obviously, the types are not limited to the examples provided, but merely a blueprint.
     */
    /* package-private */ enum Log {
        INFO,
        DEBUG,
        WARNING,
        ERROR,
        SEVERE
    }
}