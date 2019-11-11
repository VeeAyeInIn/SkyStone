package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * An extension for a moveable entity, with abstract methods for how movement works to apply a
 * blueprint without hardcoded instructions with how to move.
 */
public interface Moveable {

    /**
     * Moves the entity forward, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    void forward(double power, DcMotor... motors);

    /**
     * Moves the entity backward, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    void backward(double power, DcMotor... motors);

    /**
     * Moves the entity left, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    void left(double power, DcMotor... motors);

    /**
     * Moves the entity right, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    void right(double power, DcMotor... motors);

    /**
     * Rotates the entity, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    void rotate(double power, DcMotor... motors);

    /**
     * Stops the entity, the method is provided by the extension.
     *
     * @param motors which motors to stop
     */
    void stop(DcMotor... motors);
}
