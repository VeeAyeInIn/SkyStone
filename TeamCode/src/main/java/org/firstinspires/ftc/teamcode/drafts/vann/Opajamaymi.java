package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Locale;

@TeleOp(name = "Opajamaymi", group = "Iterative OpMode")
public class Opajamaymi extends OpMode {

    // Constants
    private static final double DEAD_ZONE = 0.1;

    // Dynamic Values
    private double speed = 0.75;
    private double velocity = 0;

    // Wheels
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    // Intake Gears
    private DcMotor leftGear;
    private DcMotor rightGear;

    // Arm (PID)
    private DcMotorEx arm;

    // Other
    private CRServo wrist;
    private Servo latch;
    private Servo gate;
    private Servo tray;

    @Override
    public void init() {

        // Find all devices
        for (HardwareDevice device : hardwareMap) {
            telemetry.addData(device.getDeviceName(), "has been detected.\n");
        }

        // Now update the info
        telemetry.update();

        // DcMotors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");

        // Arm (Special Behaviours)
        arm = (DcMotorEx) hardwareMap.dcMotor.get("arm");

        // Servos
        wrist = hardwareMap.crservo.get("wrist");
        latch = hardwareMap.servo.get("latch");
        gate = hardwareMap.servo.get("gate");
        tray = hardwareMap.servo.get("tray");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

        // DcMotors
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftGear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightGear.setDirection(DcMotorSimple.Direction.REVERSE);

        // Arm
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Servos
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        latch.setDirection(Servo.Direction.FORWARD);
        gate.setDirection(Servo.Direction.FORWARD);
        tray.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void loop() {

        /////////////////////
        //    Gamepad 1    //
        /////////////////////

        // Dynamic Speed
        if (gamepad1.a) {
            speed = 1.00;
        } else if (gamepad1.x) {
            speed = 0.75;
        } else if (gamepad1.y) {
            speed = 0.50;
        } else if (gamepad1.b) {
            speed = 0.25;
        }

        // Movement
        move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speed);


        /////////////////////
        //    Gamepad 2    //
        /////////////////////

        // Arm Handling
        if (gamepad2.left_stick_y < -DEAD_ZONE) {
            velocity = 150;
        } else {
            velocity = 0;
        }

        arm.setVelocity(velocity, AngleUnit.DEGREES);

        // Gears (Intake/Expulsion) Handling
        leftGear.setPower(clip(gamepad2.left_trigger * (gamepad2.left_bumper ? -1 : 1)));
        rightGear.setPower(clip(gamepad2.right_trigger * (gamepad2.right_bumper ? -1 : 1)));

        // Handle Wrist
        if (gamepad2.right_stick_x > 0.1) {
            wrist.setPower(-1);
        } else if (gamepad2.right_stick_x < -0.1) {
            wrist.setPower(1);
        } else {
            wrist.setPower(0);
        }

        // Handle Latch
        if (gamepad2.right_stick_y > 0.1) {
            latch.setPosition(latch.getPosition() - 0.05);
        } else if (gamepad2.right_stick_y < -0.1) {
            latch.setPosition(latch.getPosition() + 0.05);
        }

        // Handle Gate
        if (gamepad2.x) {
            gate.setDirection(Servo.Direction.FORWARD);
            gate.setPosition(gate.getPosition() + 0.1);
        }

        if (gamepad2.y) {
            gate.setDirection(Servo.Direction.REVERSE);
            gate.setPosition(gate.getPosition() + 0.1);
        }

        // Handle Tray
        if (gamepad2.dpad_up) {
            tray.setPosition(0);
        } else if (gamepad2.dpad_down) {
            tray.setPosition(1);
        }


        /////////////////////
        //      Debug      //
        /////////////////////

        stat(arm);
        stat(wrist);
        stat(latch);
        stat(tray);

        telemetry.addData("Velocity", velocity);
        telemetry.update();
    }

    @Override
    public void stop() {

    }

    /**
     * Handles movement for the four wheels.
     *
     * @param y power in the y-direction
     * @param x power in the x-direction
     * @param r rotational power
     * @see #move(double, double, double, double)
     */
    @Deprecated // Technically useless, however, also helpful to keep
    private void move(final double x, final double y, final double r) {
        this.move(x, y, r, 1.0);
    }

    /**
     * Handles movement for the four wheels. The local variables use 'd' as shorthand 'delta', in
     * order to signify change, i.e. with the variable 'multiplier' effecting values.
     *
     * @param y          power in the y-direction
     * @param x          power in the x-direction
     * @param r          rotational power
     * @param multiplier how much of the power should be used
     * @see #move(double, double, double)
     */
    private void move(final double x, final double y, final double r, final double multiplier) {

        double dx = clip(x) * multiplier;
        double dy = clip(y) * multiplier;
        double dr = clip(r) * multiplier;

        // Change the power of everything
        leftFront.setPower(dy - dx - dr);
        rightFront.setPower(dy + dx + dr);
        leftRear.setPower(dy + dx - dr);
        rightRear.setPower(dy - dx + dr);
    }

    /**
     * Functions similar to {@link com.qualcomm.robotcore.util.Range#clip(int, int, int)}, however,
     * instead of keeping it in constraints, it wants outliers.
     *
     * @param min   minimum value it cannot be
     * @param max   maximum value it cannot be
     * @param check the value to check
     * @return 0 if within bounds, else the check is returned
     */
    private double clip(final double min, final double max, final double check) {
        return min < check && check < max ? 0 : check;
    }

    /**
     * Uses the constant {@link #DEAD_ZONE} as the min (negative) and max (positive) values.
     *
     * @param check the value to check
     * @return 0 if within bounds, else the check if returned
     * @see #clip(double, double, double)
     */
    private double clip(final double check) {
        return clip(-DEAD_ZONE, DEAD_ZONE, check);
    }

    /**
     * Adds to the telemetry basic stats of a {@link HardwareDevice}, however only uses 3 main types
     * as others are not used. See {@link DcMotor}, {@link CRServo}, and {@link Servo}.
     *
     * @param device what device to assess
     * @see #telemetry
     */
    private void stat(HardwareDevice device) {
        if (device instanceof DcMotor) {
            telemetry.addData("DCMOTOR " + device.getDeviceName(), String.format(Locale.ENGLISH,
                    "Port: %d   Power: %.2f   Busy: %b",
                    ((DcMotor) device).getPortNumber(),
                    ((DcMotor) device).getPower(),
                    ((DcMotor) device).isBusy()));
        } else if (device instanceof CRServo) {
            telemetry.addData("CRSERVO " + device.getDeviceName(), String.format(Locale.ENGLISH,
                    "Port: %d   Power: %.2f",
                    ((CRServo) device).getPortNumber(),
                    ((CRServo) device).getPower()));
        } else if (device instanceof Servo) {
            telemetry.addData("SERVO " + device.getDeviceName(), String.format(Locale.ENGLISH,
                    "Port: %d   Position: %.2f",
                    ((Servo) device).getPortNumber(),
                    ((Servo) device).getPosition()));
        } else {
            telemetry.addData("UNKNOWN " + device.getDeviceName(), String.format(Locale.ENGLISH,
                    "Info: %s", device.getConnectionInfo()));
        }
    }
}
