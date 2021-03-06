package org.firstinspires.ftc.teamcode.archive.trash;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Iterative crap that I cannot stand because nothing is working", group = "communism")
public class IterativeOpMode extends OpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor arm;
    private DcMotor leftGear;
    private DcMotor rightGear;

    @Override
    public void init() {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        arm = hardwareMap.dcMotor.get("arm");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");

        // Start running the encoders
        setUniversalMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // info("Robot has initialized");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

        // Start the time back at zero
        // getElapsedTime().reset();

        // info("Robot has started");
    }

    @Override
    public void loop() {

        // Give priority to rotation instead of movement, they can not happen at the same time
        if (gamepad1.right_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            // Choose the large value, then if right, positive, else if left, then negative
            double rotate = gamepad1.right_trigger > gamepad1.left_trigger
                    ? gamepad1.right_trigger : -gamepad1.left_trigger;
            setWheelPower(rotate, rotate,
                    rotate, rotate);
        } else {
            double x = Math.abs(gamepad1.left_stick_x) > 0.2 ? gamepad1.left_stick_x : 0;
            double y = Math.abs(gamepad1.left_stick_y) > 0.2 ? gamepad1.left_stick_y : 0;

            // Give priority to linear forward movement
            if (x == 0) {
                setWheelPower(y, -y,
                        y, -y);
            } else {
                setWheelPower(x, x,
                        -x, -x);
            }
        }

        // Now we need to deal with the second controller
        if (gamepad2.right_trigger > 0.1 || gamepad2.left_trigger > 0.1) {
            setArmPower(gamepad2.right_trigger > gamepad2.left_trigger
                    ? gamepad2.right_trigger : -gamepad2.left_trigger);
        }

        // Handle the gears if not in the dead-zone
        setGearPower(Math.abs(gamepad2.left_stick_y) > 0.1 ? gamepad2.left_stick_y : 0,
                Math.abs(gamepad2.right_stick_y) > 0.1 ? gamepad2.right_stick_y : 0);
    }

    @Override
    public void stop() {

        // Stop the encoders
        setUniversalMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // info("Robot has stopped");
    }

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
     * Sets the universal power for each gear.
     *
     * @param power the power of each gear
     */
    public void setGearPower(final double power) {
        setGearPower(power, power);
    }

    /**
     * Sets the power for each individual gear.
     *
     * @param leftGearPower  the power of the left gear
     * @param rightGearPower the power of the right gear
     */
    public void setGearPower(final double leftGearPower, final double rightGearPower) {
        leftGear.setPower(leftGearPower);
        rightGear.setPower(rightGearPower);
    }

    /**
     * Sets the power of the arm.
     *
     * @param power the power of the arm
     */
    public void setArmPower(final double power) {
        arm.setPower(power);
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

    /**
     * Sets the mode for each of the gears.
     *
     * @param mode the gear motor mode
     */
    public void setGearMode(DcMotor.RunMode mode) {
        leftGear.setMode(mode);
        rightGear.setMode(mode);
    }

    /**
     * Sets the mode for the arm.
     *
     * @param mode the arm motor mode
     */
    public void setArmMode(DcMotor.RunMode mode) {
        arm.setMode(mode);
    }

    /**
     * Sets the universal mode for all of the motors.
     *
     * @param mode the universal motor mode
     */
    public void setUniversalMode(DcMotor.RunMode mode) {
        setWheelMode(mode);
        setGearMode(mode);
        setArmMode(mode);
    }
}
