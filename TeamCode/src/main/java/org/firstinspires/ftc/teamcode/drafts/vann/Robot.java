package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * @author Nerds of the North
 */
public abstract class Robot extends OpMode {

    private final ElapsedTime elapsed;

    private final DcMotor rightFront;
    private final DcMotor leftFront;
    private final DcMotor rightBack;
    private final DcMotor leftBack;

    /**
     * Default constructor for a Robot
     */
    public Robot() {

        // Required at the start of the constructor
        super();

        // Initialize all the final variables
        elapsed = new ElapsedTime();
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    }

    /*
     * Getter Methods
     */

    /**
     * Getter method for the elapsed time
     *
     * @return the elapsed time
     */
    public ElapsedTime getElapsed() {
        return elapsed;
    }

    /**
     * Getter method for the front right motor
     *
     * @return the front right motor
     */
    public DcMotor getRightFront() {
        return rightFront;
    }

    /**
     * Getter method for the front left motor
     *
     * @return the front left motor
     */
    public DcMotor getLeftFront() {
        return leftFront;
    }

    /**
     * Getter method for the back right motor
     *
     * @return the back right motor
     */
    public DcMotor getRightBack() {
        return rightBack;
    }

    /**
     * Getter method for the back left motor
     *
     * @return the back left motor
     */
    public DcMotor getLeftBack() {
        return leftBack;
    }

    /*
     * Utility Methods
     */

    /**
     * Sets a uniform power for each of the motors
     *
     * @param power the uniform power
     */
    protected void setPowers(double power) {
        setPowers(power, power, power, power);
    }

    /**
     * Sets a power for each of the motors within a single line
     *
     * @param frontRightPower the new front right power
     * @param frontLeftPower  the new front left power
     * @param backRightPower  the new back right power
     * @param backLeftPower   the new back left power
     */
    protected void setPowers(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        rightFront.setPower(frontRightPower);
        leftFront.setPower(frontLeftPower);
        rightBack.setPower(backRightPower);
        leftBack.setPower(backLeftPower);
    }

    /**
     * Sets a uniform mode for all the motors
     *
     * @param mode the new uniform mode
     */
    protected void setModes(DcMotor.RunMode mode) {
        rightFront.setMode(mode);
        leftFront.setMode(mode);
        rightBack.setMode(mode);
        leftBack.setMode(mode);
    }

    /**
     * Prints out an error to the phone along with the time it occurred
     *
     * @param error the issue that occurred
     *
     * @return the item added to the telemetry
     */
    protected Telemetry.Item error(String error) {
        return telemetry.addData("Error at " + elapsed.toString(), error);
    }

    /**
     * Prints out an error to the phone along with the time it occurred
     *
     * @param e the exception that occurred
     *
     * @return the item added to the telemetry
     */
    protected Telemetry.Item error(Exception e) {
        return error(e.getMessage());
    }

    /**
     * Makes the program wait for a set amount of time.
     *
     * @param milliseconds how many milliseconds to wait for
     */
    protected void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            error(e);
        }
    }

    protected Telemetry.Item updateStatus(String status) {
        return telemetry.addData("Status", status);
    }
}
