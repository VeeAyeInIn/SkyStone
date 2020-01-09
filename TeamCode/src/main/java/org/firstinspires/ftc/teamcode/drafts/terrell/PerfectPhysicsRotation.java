package org.firstinspires.ftc.teamcode.drafts.terrell;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Locale;

@Autonomous(name = "ROTATE", group = "Linear OpMode")
public class PerfectPhysicsRotation extends LinearOpMode {

    private static final double ROBOT_WIDTH_INCHES = 16.5;
    private static final double ROBOT_LENGTH_INCHES = 18.0;
    private static final double WHEEL_RADIUS = 2.0;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * Math.PI * 2;
    private static final double TICKS_PER_ROTATION = 1440;
    private static final double TICKS_PER_INCH = TICKS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
    private static final double ROTATION_CIRCUMFERENCE = 12.20911544707 * Math.PI;

    private ElapsedTime runtime;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor leftGear;
    private DcMotor rightGear;
    private DcMotor arm;

    private CRServo latch;
    private CRServo wrist;

    private Servo gate;
    private Servo tray;

    @Override
    public void runOpMode() throws InterruptedException {

        this.setup();
        this.waitForStart();

        runtime.reset();

        this.pause(1);
        this.move(63);
    }

    private void setup() {

        runtime = new ElapsedTime();

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");
        arm = hardwareMap.dcMotor.get("arm");
        wrist = hardwareMap.crservo.get("wrist");
        latch = hardwareMap.crservo.get("latch");
        tray = hardwareMap.servo.get("tray");
        gate = hardwareMap.servo.get("gate");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftGear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightGear.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void move(double inches) {

        leftFront.setTargetPosition((int) (TICKS_PER_INCH * inches));
        rightFront.setTargetPosition((int) (TICKS_PER_INCH * inches));
        leftRear.setTargetPosition((int) (TICKS_PER_INCH * inches));
        rightRear.setTargetPosition((int) (TICKS_PER_INCH * inches));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0.5);
        rightFront.setPower(0.5);
        leftRear.setPower(0.5);
        rightRear.setPower(0.5);

        double start = runtime.seconds();
        while (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy()) {
            telemetry.addData("MOTOR LF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftFront.getTargetPosition(), leftFront.getPower()));
            telemetry.addData("MOTOR RF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightFront.getTargetPosition(), rightFront.getPower()));
            telemetry.addData("MOTOR LR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftRear.getTargetPosition(), leftRear.getPower()));
            telemetry.addData("MOTOR RR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightRear.getTargetPosition(), rightRear.getPower()));
            telemetry.update();
            idle();
        }

        telemetry.addData("Movement Time", (runtime.seconds() - start) + " seconds");
        telemetry.update();

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    private void rotate(double degrees){

        leftFront.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE/degrees));
        rightFront.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE/degrees));
        leftRear.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE/degrees));
        rightRear.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE/degrees));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(-0.5);
        rightFront.setPower(0.5);
        leftRear.setPower(-0.5);
        rightRear.setPower(0.5);

        double start = runtime.seconds();
        while (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy()) {
            telemetry.addData("MOTOR LF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftFront.getTargetPosition(), leftFront.getPower()));
            telemetry.addData("MOTOR RF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightFront.getTargetPosition(), rightFront.getPower()));
            telemetry.addData("MOTOR LR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftRear.getTargetPosition(), leftRear.getPower()));
            telemetry.addData("MOTOR RR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightRear.getTargetPosition(), rightRear.getPower()));
            telemetry.update();
            idle();
        }

        telemetry.addData("Movement Time", (runtime.seconds() - start) + " seconds");
        telemetry.update();

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    private void pause(double seconds) throws InterruptedException {
        Thread.sleep((long) seconds * 1000);
    }
}
