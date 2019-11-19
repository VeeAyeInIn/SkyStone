package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Debugging", group = "OpMode")
public class DebugOpMode extends OpMode {

    private DcMotor leftFront, rightFront, leftRear, rightRear;

    @Override
    public void init() {

        leftFront = hardwareMap.dcMotor.get("leftFront");
        telemetry.addData("Debug0", "Left Front has been assigned");

        rightFront = hardwareMap.dcMotor.get("rightFront");
        telemetry.addData("Debug1", "Right Front has been assigned");

        leftRear = hardwareMap.dcMotor.get("leftRear");
        telemetry.addData("Debug2", "Left Rear has been assigned");

        rightRear = hardwareMap.dcMotor.get("rightRear");
        telemetry.addData("Debug3", "Right Rear has been assigned");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        telemetry.addData("Status", "Started");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("X", gamepad1.left_stick_x);
        telemetry.addData("Y", gamepad1.left_stick_y);
        telemetry.update();
    }

    @Override
    public void stop() {

    }
}
