package org.firstinspires.ftc.teamcode.drafts.karpovich;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.HashMap;
import java.util.Map;

@TeleOp(name = "KarpovichOpMode", group = "TeleOp")
public class KarpovichOpMode extends OpMode {

    //Initialize variables
    private double x; // X position of the joystick
    private double y; // Y position of the joystick
    private double rotation; // Rotational value
    private double speedMultiplier; //Speed Multiplier
    private double armPower = 1.0; //Arm power
    private double aEMax = 10.0;
    private double aE;
    private double intakePower = 1.0;

    //Initialize wheels
    private DcMotor rightRear;
    private DcMotor leftRear;
    private DcMotor leftFront;
    private DcMotor rightFront;

    //Initialize other motors
    private DcMotor rightGear;
    private DcMotor leftGear;
    private DcMotor arm;



    /**
     * This method loops when the <i>init</i> button is activated.
     *
     * @see OpMode#init_loop()
     */
    @Override
    public void init_loop() {

    }

    /**
     * This method runs once when the <i>init</i> button is activated.
     *
     * @see OpMode#init()
     */
    @Override
    public void init() {
        // Assigning all the motors from the hardware map
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightGear = hardwareMap.get(DcMotor.class, "rightGear");
        leftGear = hardwareMap.get(DcMotor.class, "leftGear");
        arm = hardwareMap.get(DcMotor.class, "arm");

        // Stop all the encoders and motors as a precaution
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightGear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Update with the new data
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /**
     * This method loops once the <i>start</i> button is activated.
     *
     * @see OpMode#loop()
     */
    @Override
    public void loop() {

        //Set x and y to the joystick x and y -1.0 to 1.0
        x = gamepad1.left_stick_x; // Updated X Position
        y = gamepad1.left_stick_y; // Updated Y Position

        //Update Encoder Values
        aE = arm.getCurrentPosition();

        //Update Rotation Variable
        if (gamepad1.right_trigger > 0.3)
            rotation = 1 * gamepad1.right_trigger; // Set r positive with a buffer zone
        else if (gamepad1.left_trigger > 0.3)
            rotation = -1 * gamepad1.left_trigger; // Set r negative with a buffer zone

        if (Math.abs(x) < 0.1) x = 0; // Dead-Zone of 0.1
        if (Math.abs(y) < 0.1) y = 0; // Dead-Zone of 0.1

        if (0.5 < speedMultiplier && speedMultiplier < 1.5) {
            if (gamepad1.dpad_up) {
                speedMultiplier += 0.5;
            }
            if (gamepad1.dpad_down) {
                speedMultiplier -= 0.5;
            }
        }

        if (rotation != 0) {
            try {
                //rotate the robot
                rightFront.setPower(-rotation * speedMultiplier);
                rightRear.setPower(-rotation * speedMultiplier);
                leftFront.setPower(-rotation * speedMultiplier);
                leftRear.setPower(-rotation * speedMultiplier);
            } catch (NullPointerException ignored) { /* Actions if NPE */ }
        } else {
            //Move the robot
            rightFront.setPower(-y * speedMultiplier - x * speedMultiplier);
            rightRear.setPower(-y * speedMultiplier + x * speedMultiplier);
            leftFront.setPower(y * speedMultiplier - x * speedMultiplier);
            leftRear.setPower(y * speedMultiplier + x * speedMultiplier);
        }

        if (gamepad2.left_trigger > 0.2) {
            moveArm(-1);
        } else if (gamepad2.right_trigger > 0.2) {
            moveArm(1);
        }

        if (gamepad2.dpad_down) {
            intakeBlock(-1);
        }

        if (gamepad2.dpad_up) {
            intakeBlock(1);
        }

        if (gamepad2.left_bumper) {
            rotateArm(1);
        } else if (gamepad2.right_bumper) {
            rotateArm(-1);
        }
    }

    /**
     * This method runs once when the <i>start</i> button is activated.
     *
     * @see OpMode#start()
     */
    @Override
    public void start() {
        newStatus("Started");
    }

    /**
     * This method runs once when the <i>stop</i> button is activated.
     *
     * @see OpMode#stop()
     */
    @Override
    public void stop() {
        newStatus("Stopped");
    }

    /**
     * Updates the {@link org.firstinspires.ftc.robotcore.external.Telemetry} data for the specific
     * caption 'Status'.
     *
     * @param status the new status of the robot
     */
    private void newStatus(String status) {
        telemetry.addData("Status", status);
    }

    public void moveArm(int i) {
        if(aE <= aEMax) {
            arm.setPower(armPower * i);
        }
    }

    public void rotateArm(int direction){
        //Rotate Arm
    }

    public void intakeBlock(int direction){
        rightGear.setPower(intakePower * direction);
        leftGear.setPower(intakePower * direction);
    }
}

