package org.firstinspires.ftc.teamcode.drafts.vann.archive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Autonomous;

public class ExampleAutonomous extends Autonomous {

    private final DcMotor rightFront;
    private final DcMotor leftFront;
    private final DcMotor rightRear;
    private final DcMotor leftRear;

    private final static double DEAD_ZONE = 0.1;

    /**
     * Default constructor for an Autonomous program.
     */
    public ExampleAutonomous() {
        super("AbyhPYH/////AAABmVllNwWWaUnSozzaWrSP7kuAOpem63iiGDaZXW1b7zxbWr5h4qHCM4YqWKiRliNBlApeot38Nz3iQxRPGeIKlPlEtRbZXBY4nstLBf5mPFHwpq6Ajsr/3G60eThr4G+9KolTe30N2MHtfO0G7PkxkzP7wRPf8fji8+CMCvOxE19ZY6YF0L9MJEK+/p6JiXWO7E97kKcGlcfO85ipV5mC5JL9LVYOcVc5KvjkAwQiteEasU3Fv8kW/s4C1f/HPNqvF9I3jgyNz6HdxF/4OCic6nlJITNiTkKMTOeHYp65SXkFUYDsRKeTEvKQtTOe4Qkn6bmY2jhN2/EU1HC1JIOJ3kTVhsGZ8bWEzKA6aJ5CNuUa");

        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");
    }

    /**
     * Override this method and place your code here.
     * <p>
     * Please do not swallow the InterruptedException, as it is used in cases
     * where the op mode needs to be terminated early.
     *
     * @throws InterruptedException should the thread have a problem sleeping
     */
    @Override
    public void runOpMode() throws InterruptedException {
        info("Autonomous has begun!");
    }

    /**
     * Moves the entity forward, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    public void forward(double power, DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setPower(deadZone(power));
            debug(motor.getDeviceName() + " power", motor.getPower());
        }
    }

    /**
     * Moves the entity backward, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    @Deprecated
    public void backward(double power, DcMotor... motors) {
        warning("This method is redundant, please use #forward(-power, motors) instead!");
    }

    /**
     * Moves the entity left, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    @Deprecated
    public void left(double power, DcMotor... motors) {
        warning("The movement method used is not supported.");
    }

    public void left(double power) {

    }

    /**
     * Moves the entity right, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    @Deprecated
    public void right(double power, DcMotor... motors) {
        warning("The movement method used is not supported.");
    }

    /**
     * Rotates the entity, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    @Deprecated
    public void rotate(double power, DcMotor... motors) {
        warning("The rotation method used is not supported.");
    }

    /**
     * Stops the entity, the method is provided by the extension.
     *
     * @param motors which motors to stop
     */
    @Override
    public void stop(DcMotor... motors) {
        for (DcMotor motor : motors) {
            motor.setPower(0);
            debug(motor.getDeviceName() + " power", motor.getPower());
        }
    }

    /**
     * Checks if a value is below the dead zone, and if so, return the lowest value, 0. If over 1,
     * then return 1, otherwise return the value that was inputted. Represented through a short
     * comparison, DEAD_ZONE <= in <= 1; else if DEAD_ZONE > in, return 0, or if in > 1, return 1.
     *
     * @param in the value to check
     * @return the new checked value
     */
    private double deadZone(double in) {
        return in > DEAD_ZONE ? in > 1 ? 1 : in : 0;
    }
}
