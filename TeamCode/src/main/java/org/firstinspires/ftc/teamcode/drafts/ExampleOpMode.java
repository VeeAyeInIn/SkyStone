package org.firstinspires.ftc.teamcode.drafts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * @author YOUR_NAME
 * <p>
 * This is a quick guide to making an OpMode for the Nerds of the North Robotics Team
 * <p>
 * An OpMode is the function / program being run that makes the robot function. Think of 'Operational
 * Mode' as in HOW the robot operates. There are two OpModes that we use, LinearOpMode, and OpMode.
 * LinearOpMode, as the name suggests, is a linear-progressing function. It does not take input, and
 * is basically what we use for the autonomous procedures. OpMode on the other hand, has a lot more
 * to it, such as loops, like {@link #loop()} and {@link #init_loop()}.
 * <p>
 * These are the primary methods which you should familiarize yourself with:
 * <ul>
 * <li>{@link #init()}</li>
 * <ul>
 * <li>When the robot is initialized. This is before properly running, and should be used as
 * a way to assign values and preparing motors for startup.</li>
 * </ul>
 * <li>{@link #init_loop()}</li>
 * <ul>
 * <li>A loop that occurs while the robot is in the initialization phase. This will stop
 * occuring once the program has been properly started.</li>
 * </ul>
 * <li>{@link #start()}</li>
 * <ul>
 * <li>What to do when the program starts. There is a difference between start and init,
 * primarily being that to start the robot, you have to manually select the 'start' button.
 * At this point, your robot is now running.</li>
 * </ul>
 * <li>{@link #loop()}</li>
 * <ul>
 * <li>Like the previous loop, this runs over and over AFTER the start. Movement will be
 * handled primarily in here, to make sure it constantly updates and checks if a button or
 * joystick is being held down, and to perform ana action if so.</li>
 * </ul>
 * <li>{@link #stop()}</li>
 * <ul>
 * <li>Finally this is what happens when the robot stops. Not too much should happen here,
 * except for possibly the finalization of data or shutting down certain aspects.</li>
 * </ul>
 * </ul>
 * <p>
 * <p>
 * <p>
 * Of course, if you haven't looked over the actual FTC SDK yet, then check out their GitHub Repository
 * here: https://github.com/FIRST-Tech-Challenge/SkyStone
 * <p>
 * Theres a few other things that I would get familiar with:
 *
 * <i>Telemetry</i> this is what we use to display data of the robot and send it to the phone. Mainly
 * things like 'Is SkyStone in sight' or 'Encoder Location'
 *
 * <i>Encoders</i> TODO
 *
 * <i>DcMotors</i> these are the objects that correspond with each physical motor we have. From this,
 * we can tell the motor how fast to go, albeit in a semi-inaccurate way due to the fluctuations of
 * the power levels messing with how fast it truly goes.
 *
 * <i>Vuforia</i> this is an image detection program, which we will use to orientate ourselves. In
 * the SkyStone challenge, we have 8 images on the wall. If we can see an image, we'll know what
 * direction we are facing depending on which image we see.
 *
 * <i>Tensor Flow Object Detection (TFOD)</i> while Vuforia focuses on images, TFOD focuses on the
 * 'models,' or the 3D aspect of it. With this, we can find out if an object is a SkyStone, and
 * some information like our angle compared to it, the space it takes up on the total camera area,
 * etc.
 */

@TeleOp(name = "Example OpMode", group = "OpMode") // Identification
// @Disabled // This means it is not a valid OpMode, and it will not be used
public class ExampleOpMode extends OpMode {

    // Keep track of how much time has elapsed
    private ElapsedTime elapsed;

    // Our functional motors
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor leftRear;

    @Override
    public void init() {

        // Now we can keep track of time, however we won't record until startup
        elapsed = new ElapsedTime();

        // Assigning all the motors from the hardware map
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");

        // Stop all the encoders and motors as a precaution
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Update with the new data
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        // More or less will remain empty
    }

    @Override
    public void start() {

        // Robot has started, keep track of time starting from zero
        elapsed.reset();

        // Start all the encoders and motors
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Update with the new data
        telemetry.addData("Status", "Started");
        telemetry.update();
    }

    @Override
    public strictfp void loop() {

        // Our values, where the joystick is
        double x = Math.abs(gamepad1.left_stick_x) > 0.1 ? gamepad1.left_stick_x : 0;
        double y = Math.abs(gamepad1.left_stick_y) > 0.1 ? gamepad1.left_stick_y : 0;

        // Rotation based on how much the triggers are pressed
        double rotate; // Wait to assign
        if (gamepad1.left_trigger > gamepad1.right_trigger) {
            rotate = gamepad1.left_trigger > 0.1 ? gamepad1.left_trigger : 0;
        } else {
            rotate = gamepad1.right_trigger > 0.1 ? gamepad1.right_trigger : 0;
        }

        move(x, 10);
        rotate(rotate, 10);

        // Display how much time has elapsed
        telemetry.addData("Elapsed Time", elapsed.toString());

        // Final loop method to ensure everything is updated
        telemetry.update();
    }

    @Override
    public void stop() {

        // Stop all the encoders and motors
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Update with the new data
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

    /**
     * Moves the robot either forward (positive power) or backward (negative power)
     *
     * @param power  how powerful to set the motors
     * @param timeMs how long (in milliseconds) to move
     */
    public void move(double power, long timeMs) {
        setPowers(power);

        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            error(e.getMessage());
        }

        setPowers(0);
    }

    /**
     * Rotates the robot in a given direction.
     *
     * @param power  how much to rotate and what direction. Positive is right, negative is left, and
     *               the absolute power is the general power to rotate at.
     * @param timeMs how long (in milliseconds) to rotate
     */
    public void rotate(double power, long timeMs) {

        // Don't waste time if it doesn't do anything
        if (power == 0) return;

        // Sets the motors up for rotation
        this.setPowers(power, -power, power, -power);

        try {
            // Wait timeMs, then stop
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            // Display the error on the telemetry
            error(e.getMessage());
        }

        // Stop rotating
        this.setPowers(0);
    }

    /**
     * Sets all the motors' powers to a equal value.
     *
     * @param power the new uniform power
     */
    public void setPowers(double power) {
        rightFront.setPower(power);
        leftFront.setPower(power);
        rightRear.setPower(power);
        leftRear.setPower(power);
    }

    /**
     * Sets the motors' powers based on the respective parameters.
     *
     * @param frontRightPower the new front right power
     * @param frontLeftPower  the new front left power
     * @param backRightPower  the new back right power
     * @param backLeftPower   the new back left power
     */
    public void setPowers(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        rightFront.setPower(frontRightPower);
        leftFront.setPower(frontLeftPower);
        rightRear.setPower(backRightPower);
        leftRear.setPower(backLeftPower);
    }

    /*
     * Utility methods
     *
     * Mainly for data and other sorts, not actual things like movement handling, Vuforia, TFOD, or
     * anything else like that.
     */

    /**
     * Displays the error on the telemetry.
     *
     * @param caption what the error will say
     * @return the item in telemetry
     */
    private Telemetry.Item error(String caption) {
        return telemetry.addData("Error at " + elapsed.toString(), caption);
    }
}