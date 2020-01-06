package org.firstinspires.ftc.teamcode.drafts.terrell;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="why are we here", group="opmode")

public class AutonomousByTime extends LinearOpMode {

    // Self-explanatory, speed to drive forward and turn
    private static final double FORWARD_SPEED = 0.6;
    private static final double TURN_SPEED = 0.5;

    // New idea: type actions and time for actions in an array (could use a 2d list but I don't really know how to do that)
    private static final int[] PATH_TIME = new int[]{1, 2};
    private static final String[] PATH_ACTIONS = new String[]{"Forward", "Backward"};
    // TO DO: PATH_ACTIONS implementation

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

        telemetry.addData("Status:", "Ready"); // Updating Telemetry status
        telemetry.update();

        waitForStart();
        for(int i = 0; i < PATH_TIME.length; i++){ // Go through each of the legs of the journey
            leftFront.setPower(FORWARD_SPEED);
            rightFront.setPower(FORWARD_SPEED);
            leftRear.setPower(FORWARD_SPEED);
            rightRear.setPower(FORWARD_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < PATH_TIME[i])) { // Run for PATH_TIME amount of time
                telemetry.addData("Path", "Leg %d: %2.5f S Elapsed", i, runtime.seconds());
                telemetry.update();
            }
        }
        telemetry.addData("Status:", "Completed");
        telemetry.update();
    }
}
