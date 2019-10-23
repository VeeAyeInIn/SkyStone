package org.firstinspires.ftc.teamcode.drafts.vann;

/**
 * @author Connor Vann
 */
public class VannOpMode extends Robot {

    @Override
    public void init() {

    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        // BOOLEAN CONDITION ? IF TRUE THEN THIS : IF FALSE THEN THIS
        double x = Math.abs(gamepad1.left_stick_x) > 0.1 ? gamepad1.left_stick_x : 0; // Strafing
        double y = Math.abs(gamepad1.left_stick_y) > 0.1 ? gamepad1.left_stick_y : 0; // Movement

        double rotate; // Don't assign yet
        if (gamepad1.right_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            rotate = gamepad1.right_trigger > gamepad1.left_trigger ? gamepad1.right_trigger : -gamepad1.left_trigger;
        } else {
            rotate = 0;
        }

        if (rotate == 0) {
            // Then move
            if (Math.abs(y) >= Math.abs(x)) {
                // Move Linearly
                setPowers(y);
            } else {
                // Strafe
                setPowers(x, -x, -x, x);
            }
        } else {
            // Else rotate
            setPowers(-rotate, rotate, -rotate, rotate);
        }
    }

    @Override
    public void stop() {

    }
}
