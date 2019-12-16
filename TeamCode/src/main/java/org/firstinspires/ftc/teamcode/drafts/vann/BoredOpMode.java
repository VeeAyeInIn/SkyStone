package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "$", group = "Iterative OpMode")
public class BoredOpMode extends OpMode {

    // Constant Values
    private static final double DEAD_ZONE = 0.1;

    // Dynamic Values
    private double speed = 0.75; // Normal

    // DcMotors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor leftGear;
    private DcMotor rightGear;
    private DcMotor arm;

    // CRServos
    private CRServo wrist;
    private CRServo latch;
    private Servo gate;

    // Servos
    private Servo tray;

    @Override
    public void init() {

        // Find all devices
        for (HardwareDevice device : hardwareMap) {
            telemetry.addData(device.getDeviceName() + ": " + device.getConnectionInfo(),
                    "This device has been found!");
        }

        // Display everything previously shown
        telemetry.update();

        // Initialize DcMotors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");
        arm = hardwareMap.dcMotor.get("arm");

        // Initialize CRServos
        wrist = hardwareMap.crservo.get("wrist");
        latch = hardwareMap.crservo.get("latch");
        gate = hardwareMap.servo.get("gate");

        // Initialize Servos
        tray = hardwareMap.servo.get("tray");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

        // Setup DcMotor modes
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightGear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Setup DcMotor directions
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftGear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightGear.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        // Setup CRServo directions
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        latch.setDirection(DcMotorSimple.Direction.FORWARD);
        gate.setDirection(Servo.Direction.FORWARD);

        // Setup Servo directions
        tray.setDirection(Servo.Direction.FORWARD);

        // Arm Behaviour
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Handle Debugging
        telemetry.clearAll();
    }

    @Override
    public void loop() {

        // Dynamic Speed Updates
        if (gamepad1.a) {
            speed = 1.00; // Fast
        } else if (gamepad1.x) {
            speed = 0.75; // Normal
        } else if (gamepad1.y) {
            speed = 0.50; // Slow
        } else if (gamepad1.b) {
            speed = 0.25; // Very Slow
        }

        // Handle Movement
        this.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speed);

        // Handle Left Gear
        if (gamepad2.left_bumper) {
            leftGear.setPower(-clip(gamepad2.left_trigger));
        } else {
            leftGear.setPower(+clip(gamepad2.left_trigger));
        }

        // Handle Right Gear
        if (gamepad2.right_bumper) {
            rightGear.setPower(-clip(gamepad2.right_trigger));
        } else {
            rightGear.setPower(+clip(gamepad2.right_trigger));
        }

        // Handle Arm
        arm.setPower(Math.min(clip(-gamepad2.left_stick_y), 0));

        // Handle Wrist
        wrist.setPower(clip(gamepad2.right_stick_x));

        // Handle Latch
        latch.setPower(clip(gamepad2.right_stick_y));

        // Handle Tray
        if (gamepad2.dpad_up) {
            tray.setPosition(1);
        } else if (gamepad2.dpad_down) {
            tray.setPosition(0);
        }

        // Handle Gate
        if (gamepad2.x) {
            gate.setPosition(0.30);
        } else if (gamepad2.y) {
            gate.setPosition(0.85);
        }

        // Debug Data
        for (HardwareDevice device : hardwareMap) {
            if (device instanceof DcMotor) {
                telemetry.addData(hardwareMap.getNamesOf(device).toArray()[0].toString(),
                        "POW " + ((DcMotor) device).getPower());
            } else if (device instanceof CRServo) {
                telemetry.addData(hardwareMap.getNamesOf(device).toArray()[0].toString(),
                        "POW " + ((CRServo) device).getPower());
            } else if (device instanceof Servo) {
                telemetry.addData(hardwareMap.getNamesOf(device).toArray()[0].toString(),
                        "POS " + ((Servo) device).getPosition());
            }
        }
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
}
