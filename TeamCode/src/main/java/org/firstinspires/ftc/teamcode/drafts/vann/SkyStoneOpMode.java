package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.AdvancedOpMode;

public class SkyStoneOpMode extends AdvancedOpMode {

    @Override
    public void init() {

        // Start running the encoders
        setUniversalMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        info("Robot has initialized");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

        // Start the time back at zero
        getElapsedTime().reset();

        info("Robot has started");
    }

    @Override
    public void loop() {

        // Give priority to rotation instead of movement, they can not happen at the same time
        if (gamepad1.right_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            // Choose the large value, then if right, positive, else if left, then negative
            double rotate = gamepad1.right_trigger > gamepad1.left_trigger
                    ? gamepad1.right_trigger : -gamepad1.left_trigger;
            setWheelPower(rotate, -rotate, rotate, -rotate);
        } else {
            double x = Math.abs(gamepad1.left_stick_x) > 0.1 ? gamepad1.left_stick_x : 0;
            double y = Math.abs(gamepad1.left_stick_y) > 0.1 ? gamepad1.left_stick_y : 0;

            // Give priority to linear forward movement
            if (y != 0) {
                setWheelPower(y);
            } else if (x != 0) {
                setWheelPower(x, -x, -x, x);
            }
        }

        // Now we need to deal with the second controller
        if (gamepad2.right_trigger > 0.1 || gamepad2.left_trigger > 0.1) {
            setArmPower(gamepad2.right_trigger > gamepad2.left_trigger
                    ? gamepad2.right_trigger : -gamepad2.left_trigger);
        }

        // Handle the gears if not in the deadzone
        setGearPower(Math.abs(gamepad2.left_stick_y) > 0.1 ? gamepad2.left_stick_y : 0,
                Math.abs(gamepad2.right_stick_y) > 0.1 ? gamepad2.right_stick_y : 0);
    }

    @Override
    public void stop() {

        // Stop the encoders
        setUniversalMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        info("Robot has stopped");
    }
}
