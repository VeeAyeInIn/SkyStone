package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Rewrite", group = "Iterative OpMode")
public class RewriteOpMode extends OpMode {

    // Constants
    private static final double DEAD_ZONE = 0.1;

    // Dynamic Values
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
        speed = 0.75; // Normal
    }

    @Override
    public void init_loop() {
        // nothing();
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

        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        tray.setDirection(DcMotorSimple.Direction.FORWARD);
        latch.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void loop() {

        // Always stay active
        // tray.setPower(1);

        // Update speed
        if (gamepad1.a) {
            speed = 1.00;
        } else if (gamepad1.x) {
            speed = 0.75;
        } else if (gamepad1.y) {
            speed = 0.50;
        } else if (gamepad1.b) {
            speed = 0.25;
        }

        // Movement and Rotation
        double x = gamepad1.left_stick_x;
        if (-0.1 < x && x < 0.1) x = 0;
        double y = gamepad1.left_stick_y;
        if (-0.1 < y && y < 0.1) y = 0;
        double r = gamepad1.right_stick_x;
        if (-0.1 < r && r < 0.1) r = 0;
        setWheelPowers(y - x - r, -y - x - r, y + x - r, -y + x - r);

        // Handle Left Gear
        if (gamepad2.left_trigger > DEAD_ZONE) {
            if (gamepad2.left_bumper) {
                leftGear.setPower(gamepad2.left_trigger);
            } else {
                leftGear.setPower(-gamepad2.left_trigger);
            }
        } else {
            leftGear.setPower(0);
        }

        // Handle Right Gear
        if (gamepad2.right_trigger > DEAD_ZONE) {
            if (gamepad2.right_bumper) {
                rightGear.setPower(-gamepad2.right_trigger);
            } else {
                rightGear.setPower(gamepad2.right_trigger);
            }
        } else {
            rightGear.setPower(0);
        }

        // Handle Arm
        if (!(-0.1 < gamepad2.left_stick_y && gamepad2.left_stick_y < 0.1)) {
            arm.setPower(gamepad2.left_stick_y / 2.5);
        } else {
            arm.setPower(0);
        }

        // Handle Wrist
        if (!(-0.1 < gamepad2.right_stick_x && gamepad2.right_stick_x < 0.1)) {
            wrist.setPower(gamepad2.right_stick_x);
        } else {
            wrist.setPower(0);
        }

        // Handle Latch
        if (!(-0.1 < gamepad2.right_stick_y && gamepad2.right_stick_y < 0.1)) {
            latch.setPosition(latch.getPosition() + (gamepad2.right_stick_y / 50));
        }


    }

    @Override
    public void stop() {

    }

    private void setWheelPowers(double lf, double rf, double lr, double rr) {
        leftFront.setPower(lf * speed);
        rightFront.setPower(rf * speed);
        leftRear.setPower(lr * speed);
        rightRear.setPower(rr * speed);
    }

    private void debug(String name, String caption) {

    }
}
