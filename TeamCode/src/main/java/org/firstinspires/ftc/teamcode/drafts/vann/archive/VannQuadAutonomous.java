package org.firstinspires.ftc.teamcode.drafts.vann.archive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.util.QuadMecanumAutonomous;

@TeleOp(name = "Linear: Vann Quad", group = "Autonomous")
public class VannQuadAutonomous extends QuadMecanumAutonomous {

    private final VuforiaLocalizer localizer;
    private final TFObjectDetector tfod;

    public VannQuadAutonomous() {
        localizer = getVuforiaLocalizer();
        tfod = getTfod();
    }

    /**
     * Override this method and place your code here.
     * <p>
     * Please do not swallow the InterruptedException, as it is used in cases
     * where the op mode needs to be terminated early.
     *
     * @throws InterruptedException if the thread is interrupted
     */
    @Override
    public void runOpMode() throws InterruptedException {

        // We need the TFOD from the start
        tfod.activate();

        // The program has finished, hence the shutdown
        tfod.shutdown();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Moves the entity backward, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    public void backward(double power, DcMotor... motors) {
        warning("This method is deprecated, and should not be used.");
    }

    /**
     * Moves the entity left, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    public void left(double power, DcMotor... motors) {
        warning("This method is deprecated, and should not be used.");
    }

    /**
     * Moves the entity right, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    public void right(double power, DcMotor... motors) {
        warning("This method is deprecated, and should not be used.");
    }

    /**
     * Rotates the entity, the method is provided by the extension.
     *
     * @param power  how strong the motors should move
     * @param motors which motors to power
     */
    @Override
    public void rotate(double power, DcMotor... motors) {
        warning("This method is deprecated, and should not be used.");
    }
}
