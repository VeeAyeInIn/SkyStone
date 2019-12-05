package org.firstinspires.ftc.teamcode.archive.trash.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Locale;

@TeleOp(name = "Autonomous", group = "Linear OpMode")
public class Autonomous extends LinearOpMode {

    // Data
    private ElapsedTime time;

    // Wheels
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    // Intake Gears
    private DcMotor leftGear;
    private DcMotor rightGear;

    // Arm
    private DcMotor arm;

    // Other
    private CRServo wrist;
    private CRServo latch;
    private Servo tray;

    @Override
    public void runOpMode() {

        ///////////////////
        //     Debug     //
        ///////////////////

        // Find all devices
        for (HardwareDevice device : hardwareMap) {
            telemetry.addData(device.getDeviceName(), "has been detected.\n");
        }

        // Now update the info
        telemetry.update();


        ///////////////////
        //     Motor     //
        ///////////////////

        // DcMotors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");

        // Arm (Special Behaviours)
        arm = hardwareMap.dcMotor.get("arm");

        // Servos
        wrist = hardwareMap.crservo.get("wrist");
        latch = hardwareMap.crservo.get("latch");
        tray = hardwareMap.servo.get("tray");


        ///////////////////
        //     Modes     //
        ///////////////////

        // DcMotors
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightGear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftGear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightGear.setDirection(DcMotorSimple.Direction.FORWARD);

        // Arm
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Servos
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        latch.setDirection(DcMotorSimple.Direction.FORWARD);
        tray.setDirection(Servo.Direction.FORWARD);


        ///////////////////
        //     Setup     //
        ///////////////////

        // We finished setting up everything, but haven't started
        waitForStart();

        // We need to keep track of time
        time = new ElapsedTime();
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

        double dx = x * multiplier;
        double dy = y * multiplier;
        double dr = r * multiplier;

        // Change the power of everything
        leftFront.setPower(dy - dx + dr);
        rightFront.setPower(dy + dx - dr);
        leftRear.setPower(dy + dx + dr);
        rightRear.setPower(dy - dx - dr);
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
