package org.firstinspires.ftc.teamcode.drafts.karpovich;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Locale;

@Autonomous (name = "SkystoneAutov2", group = "DanielAuto")
public class SkystoneAuto_v2 extends LinearOpMode {

    private ElapsedTime runtime;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor leftGear;
    private DcMotor rightGear;
    private DcMotor arm;

    private Servo latch;
    private CRServo wrist;

    private Servo gate;
    private Servo tray;

    private static final double ROBOT_WIDTH_INCHES = 16.5;
    private static final double ROBOT_LENGTH_INCHES = 18.0;
    private static final double WHEEL_RADIUS = 2.0;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * Math.PI * 2;
    private static final double TICKS_PER_ROTATION = 1440;
    private static final double TICKS_PER_INCH = TICKS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
    private static final double ROTATION_CIRCUMFERENCE = 49.17370311729;
    private static final double MOVEMENT_MODIFIER = 3969.0 / 4864.0;
    private static final double ROTATION_MODIFIER = MOVEMENT_MODIFIER * 1.1;

    @Override
    public void runOpMode() throws InterruptedException {
        runtime = new ElapsedTime();
/*
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");
        arm = hardwareMap.dcMotor.get("arm");
        wrist = hardwareMap.crservo.get("wrist");
        latch = hardwareMap.servo.get("latch");
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
*/
        this.setup();
        waitForStart();
        runtime.reset();

        //Test movement
        this.move(10.0);
        this.pause(1.0);
        this.right(10.0);
        this.pause(2.0);
        this.rotate(90);
        this.pause(1.0);
        leftGear.setPower(.5);
        rightGear.setPower(.5);
        this.pause(1.0);
        leftGear.setPower(0);
        rightGear.setPower(0);
        this.pause(1.0);
        leftGear.setPower(-.5);
        rightGear.setPower(-.5);
        this.pause(1.0);
        leftGear.setPower(0);
        rightGear.setPower(0);

        //Actual Movement
/*
        //Move Forward ~47 inches to the blocks
        this.move(47.0); //Should be close to right
        //Intake
        leftGear.setPower(-1.0); //Could be reversed
        rightGear.setPower(1.0); //Could be reversed
        this.pause(2.0);
        leftGear.setPower(0);
        rightGear.setPower(0);
        //Move backwards
        this.back(35.0); //Placeholder value
        //Strafe over line
        this.right(20); //Placeholder Value
        //Outtake block
        leftGear.setPower(1.0); //Could be reversed
        rightGear.setPower(-1.0); //Could be reversed
        this.pause(2.0);
        leftGear.setPower(0);
        rightGear.setPower(0);
        //Strafe to line
        this.left(10);
*/
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
        latch = hardwareMap.servo.get("latch");
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

    //Albuquerque move method
    private void move(double inches) {

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int) (TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        rightFront.setTargetPosition((int) (-TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        leftRear.setTargetPosition((int) (TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        rightRear.setTargetPosition((int) (-TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));

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
    //Reverse move method (old)
    private void back(double inches) {

        leftFront.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));
        rightFront.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));
        leftRear.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));
        rightRear.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0.5);
        rightFront.setPower(0.5);
        leftRear.setPower(0.5);
        rightRear.setPower(0.5);

        while (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy()) {
            telemetry.addData("MOTOR LF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftFront.getTargetPosition(), leftFront.getPower()));
            telemetry.addData("MOTOR RF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightFront.getTargetPosition(), rightFront.getPower()));
            telemetry.addData("MOTOR LR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftRear.getTargetPosition(), leftRear.getPower()));
            telemetry.addData("MOTOR RR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightRear.getTargetPosition(), rightRear.getPower()));
            telemetry.update();
            idle();
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }
    //rotate (old)
    private void turn(double inches) {

        leftFront.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));
        rightFront.setTargetPosition((int) (TICKS_PER_INCH * inches * 63/76));
        leftRear.setTargetPosition((-1) * (int) (TICKS_PER_INCH * inches * 63/76));
        rightRear.setTargetPosition((int) (TICKS_PER_INCH * inches * 63/76));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(0.5);
        rightFront.setPower(0.5);
        leftRear.setPower(0.5);
        rightRear.setPower(0.5);

        while (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy()) {
            telemetry.addData("MOTOR LF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftFront.getTargetPosition(), leftFront.getPower()));
            telemetry.addData("MOTOR RF", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightFront.getTargetPosition(), rightFront.getPower()));
            telemetry.addData("MOTOR LR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", leftRear.getTargetPosition(), leftRear.getPower()));
            telemetry.addData("MOTOR RR", String.format(Locale.ENGLISH, "POS: %s, POW: %s", rightRear.getTargetPosition(), rightRear.getPower()));
            telemetry.update();
            idle();
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    //Right maybe?
    private void right(double inches) {

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int) (-TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        rightFront.setTargetPosition(-(int) (TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        leftRear.setTargetPosition((int) (TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));
        rightRear.setTargetPosition((int) (-TICKS_PER_INCH * inches * MOVEMENT_MODIFIER));

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

    private void rotate(double degrees) {

        degrees %= 360;

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE * degrees / 360 * ROTATION_MODIFIER));
        rightFront.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE * degrees / 360 * ROTATION_MODIFIER));
        leftRear.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE * degrees / 360 * ROTATION_MODIFIER));
        rightRear.setTargetPosition((int) (TICKS_PER_INCH * ROTATION_CIRCUMFERENCE * degrees / 360 * ROTATION_MODIFIER));

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

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        telemetry.addData("Movement Time", (runtime.seconds() - start) + " seconds");
        telemetry.update();

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //Wait
    private void pause(double seconds) throws InterruptedException {
        Thread.sleep((long) seconds * 1000);
    }
}
