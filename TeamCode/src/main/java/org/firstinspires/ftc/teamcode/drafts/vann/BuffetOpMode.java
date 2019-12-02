package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Our robot has 2 expansion hubs, named 'Auxiliary' and 'Wheels.'
 * <p>
 * The Auxiliary hub consists of...
 * - the gears,  [DcMotor]
 * - the arm,    [DcMotor]
 * - the wrist,  [CRServo]
 * - the latch.  [Servo]
 * <p>
 * The Wheels hub consists of...
 * - the wheels, [DcMotor]
 * - the tray.   [CRServo]
 * <p>
 * Driving will mainly be dealt with using {@link #gamepad1}, while other movements for doing things
 * will be dealt with using {@link #gamepad2}, so the prior can concentrate on driving.
 * <p>
 * Physical attributes will be dealt with using {@link #hardwareMap}.
 */

@TeleOp(name = "Buffet OpMode", group = "Iterative OpMode")
public class BuffetOpMode extends OpMode {

    // Constants
    private static final double DEAD_ZONE = 0.1;

    // Dynamic Values
    private boolean synchronizedGears;
    private double speed;

    // Motors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor leftGear;
    private DcMotor rightGear;
    private DcMotor arm;

    // Continuous Rotational Servos
    private CRServo wrist;
    private CRServo tray;

    // Servos
    private Servo latch;

    @Override
    public void init() {

        // Assign all DcMotors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");
        arm = hardwareMap.dcMotor.get("arm");

        // Assign all CRServos
        wrist = hardwareMap.crservo.get("wrist");
        tray = hardwareMap.crservo.get("tray");

        // Assign all Servos
        latch = hardwareMap.servo.get("latch");

        // Assign other values
        synchronizedGears = true;
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

        // Start up all the motors
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

        // Always make sure it is running
        tray.setPower(1);

        // Toggle speed
        if (gamepad1.a) {
            speed = 1.00;
        } else if (gamepad1.x) {
            speed = 0.75;
        } else if (gamepad1.y) {
            speed = 0.50;
        } else if (gamepad1.b) {
            speed = 0.25;
        }

        // Toggle gear synchronization
        if (gamepad2.left_bumper) {
            // Enable synchronization on left bumper
            synchronizedGears = true;
        } else if (gamepad2.right_bumper) {
            // Disable synchronization on right bumper
            synchronizedGears = false;
        }

        // Movement: controlled using the left and right joystick of gamepad1
        double y = clip(gamepad1.left_stick_y);  // linear power
        double x = clip(gamepad1.left_stick_x);  // strafe power
        double r = clip(gamepad1.right_stick_x); // rotational power
        setWheelPowers(y - x - r, -y - x - r, y + x - r, -y + x - r);

        // Gears
        double max; // Hold a slot if we need to find the max value
        if (synchronizedGears) { // Synchronized or independent movements
            if ((max = Math.max(gamepad2.left_trigger, gamepad2.right_trigger)) > DEAD_ZONE) {
                // Expel the block
                if (gamepad2.left_trigger > gamepad2.right_trigger) {
                    leftGear.setPower(-max);
                    rightGear.setPower(max);
                } else /* else take in the block */ {
                    leftGear.setPower(max);
                    rightGear.setPower(-max);
                }
            } else {
                leftGear.setPower(0);
                rightGear.setPower(0);
            }
        } else {
            leftGear.setPower(clip(gamepad2.left_trigger));
            rightGear.setPower(-clip(gamepad2.right_trigger));
        }

        // Arm
        arm.setPower(-clip(gamepad2.left_stick_y));

        // Wrist
        wrist.setPower(clip(gamepad2.right_stick_x));

        // Latch
        if (gamepad2.dpad_up) {
            latch.setPosition(latch.getPosition() + 0.01);
        } else if (gamepad2.dpad_down) {
            latch.setPosition(latch.getPosition() - 0.01);
        }
    }

    @Override
    public void stop() {

        // Stop (and reset) all the motors
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setWheelPowers(double lf, double rf, double lr, double rr) {
        leftFront.setPower(lf * speed);
        rightFront.setPower(rf * speed);
        leftRear.setPower(lr * speed);
        rightRear.setPower(rr * speed);
    }

    private double clip(double check, double min, double max) {
        if (Math.abs(check) < min) {
            return 0;
        } else if (Math.abs(check) > max) {
            return max;
        } else {
            return check;
        }
    }

    private double clip(double check) {
        return clip(check, DEAD_ZONE, 1.0);
    }
}