package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Bradley", group = "Linear OpMode")
public class Montgomery extends LinearOpMode {

    private static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    private static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    private static final double DRIVE_SPEED = 0.6;
    private static final double TURN_SPEED = 0.5;

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

        waitForStart();
        runtime.reset();

        move(DRIVE_SPEED, 12, 5);
        Thread.sleep(5000);
        rotate(TURN_SPEED, 90, 5);
        Thread.sleep(5000);
        strafe(DRIVE_SPEED, 12, 5);
    }

    private void move(double speed, double distance, double timeout) {

        final double runUntil = runtime.seconds() + timeout;

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));
        leftRear.setTargetPosition(leftRear.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));
        rightRear.setTargetPosition(rightRear.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftRear.setPower(speed);
        rightRear.setPower(speed);

        while ((runtime.seconds() < runUntil) && (leftFront.isBusy() || rightFront.isBusy() || leftRear.isBusy() || rightRear.isBusy())) {
            idle();
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void strafe(double speed, double distance, double timeout) {

        final double runUntil = runtime.seconds() + timeout;

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));
        rightFront.setTargetPosition(rightFront.getCurrentPosition() - (int) (distance * COUNTS_PER_INCH));
        leftRear.setTargetPosition(leftRear.getCurrentPosition() - (int) (distance * COUNTS_PER_INCH));
        rightRear.setTargetPosition(rightRear.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftRear.setPower(speed);
        rightRear.setPower(speed);

        while ((runtime.seconds() < runUntil) && (leftFront.isBusy() || rightFront.isBusy() || leftRear.isBusy() || rightRear.isBusy())) {
            idle();
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void rotate(double speed, double angle, double timeout) {

        final double runUntil = runtime.seconds() + timeout;

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + (int) (angle / 45 * COUNTS_PER_INCH));
        rightFront.setTargetPosition(rightFront.getCurrentPosition() - (int) (angle / 45 * COUNTS_PER_INCH));
        leftRear.setTargetPosition(leftRear.getCurrentPosition() + (int) (angle / 45 * COUNTS_PER_INCH));
        rightRear.setTargetPosition(rightRear.getCurrentPosition() - (int) (angle / 45 * COUNTS_PER_INCH));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftRear.setPower(speed);
        rightRear.setPower(speed);

        while ((runtime.seconds() < runUntil) && (leftFront.isBusy() || rightFront.isBusy() || leftRear.isBusy() || rightRear.isBusy())) {
            idle();
        }

        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setWheelPowers(double a, double b, double c, double d) {
        leftFront.setPower(a);
        rightFront.setPower(b);
        leftRear.setPower(c);
        rightRear.setPower(d);
    }
}
