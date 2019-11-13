package org.firstinspires.ftc.teamcode.drafts.karpovich;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.HashMap;
import java.util.Map;
@TeleOp(name = "DLKTeleOp", group = "TeleOp")
public class KarpovichOpMode extends OpMode {


    private final Map<String, DcMotor> motorMap;

    private double x; // X position of the joystick
    private double y; // Y position of the joystick
    private double rotation; // Rotational value
    private double speedMultiplier; //Speed Multiplier




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
        newStatus("Initialized");

        // Setup the motors by their name
        motorMap.put("leftFront", hardwareMap.dcMotor.get("leftFront"));
        motorMap.put("leftBack", hardwareMap.dcMotor.get("leftBack"));
        motorMap.put("rightFront", hardwareMap.dcMotor.get("rightFront"));
        motorMap.put("rightBack", hardwareMap.dcMotor.get("rightBack"));

        // For each motor, STOP, RESET, then RUN the ENCODER
        for (DcMotor motor : motorMap.values()) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * This method loops once the <i>start</i> button is activated.
     *
     * @see OpMode#loop()
     */
    @Override
    public void loop() {

        // For each motor, update the displayed position
        for (DcMotor motor : motorMap.values()) {
            telemetry.addData(motor.getDeviceName() + " position", motor.getCurrentPosition());
        }

        //Set x and y to the joystick x and y -1.0 to 1.0
        x = gamepad1.left_stick_x; // Updated X Position
        y = gamepad1.left_stick_y; // Updated Y Position

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
                motorMap.get("rightFront").setPower(-rotation * speedMultiplier);
                motorMap.get("rightBack").setPower(-rotation * speedMultiplier);
                motorMap.get("leftFront").setPower(-rotation * speedMultiplier);
                motorMap.get("leftBack").setPower(-rotation * speedMultiplier);
            } catch (NullPointerException ignored) { /* Actions if NPE */ }
        } else {
            //Move the robot
            motorMap.get("rightFront").setPower(-y * speedMultiplier - x * speedMultiplier);
            motorMap.get("rightBack").setPower(-y * speedMultiplier + x * speedMultiplier);
            motorMap.get("leftFront").setPower(y * speedMultiplier - x * speedMultiplier);
            motorMap.get("leftBack").setPower(y * speedMultiplier + x * speedMultiplier);
        }

        if (gamepad2.left_trigger > 0.2) {
            moveArm(-1)
        } else if (gamepad2.right_trigger > 0.2) {
            moveArm(1);
        }

        if (gamepad2.dpad_down) {
            grabBlock(-1);
        }

        if (gamepad2.dpad_up) {
            grabBlock(1);
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
        //Move Arm
    }

    public void rotateArm(int direction){
        //Rotate Arm
    }

    public void grabBlock(int r){
        //Grab/Release Block
    }
}

