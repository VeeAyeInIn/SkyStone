package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NOTN Programming Team
 * <p>
 * This is the main {@link OpMode} for use with manual controls via gamepads, phones, or some other
 * device. The Autonomous task will be handled using {@link com.qualcomm.robotcore.eventloop.opmode.LinearOpMode}.
 */

@TeleOp(name = "Primary OpMode", group = "OpMode")
public final class PrimaryOpMode extends OpMode {

    private final Map<String, DcMotor> motorMap;

    private double x; // X position of the joystick
    private double y; // Y position of the joystick

    /**
     * Default constructor to initiate storage of its instance.
     */
    public PrimaryOpMode() {
        // Instance is now tied to this. If you were to create a new version of this class, an error
        // would be thrown as it would attempt to store an instance when it is already assigned.
        motorMap = new HashMap<>();
    }

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

        // -1.0 to 1.0
        x = gamepad1.left_stick_x; // Updated X Position
        y = gamepad1.left_stick_y; // Updated Y Position

        if (Math.abs(x) < 0.1) x = 0; // Dead-Zone of 0.1
        if (Math.abs(y) < 0.1) y = 0; // Dead-Zone of 0.1
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
}