package org.firstinspires.ftc.teamcode.drafts.vann.archive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "i swear to god i hate this god damn thing", group = "OpMode")
public class VannIterativeOpMode extends OpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    @Override
    public void init() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        double x = gamepad1.left_stick_x > 0.1 ? gamepad1.left_stick_x : 0;
        double y = gamepad1.left_stick_y > 0.1 ? gamepad1.left_stick_y : 0;

        double rotate = 0;
        if (gamepad1.left_trigger > gamepad1.right_trigger) {
            rotate = gamepad1.left_trigger > 0.1 ? -gamepad1.left_trigger : 0;
        } else {
            rotate = gamepad1.right_trigger > 0.1 ? gamepad1.right_trigger : 0;
        }

        // Rotation is given priority
        if (rotate > 0) {
            setPowers(rotate, -rotate, -rotate, rotate);
        } else {
            if (x > y) {
                // TODO
            } else {
                setPowers(y, y, y, y);
            }
        }
    }

    @Override
    public void stop() {

    }

    /**
     * Sets a power for each of the motors within a single line
     *
     * @param frontRightPower the new front right power
     * @param frontLeftPower  the new front left power
     * @param backRightPower  the new back right power
     * @param backLeftPower   the new back left power
     */
    protected void setPowers(double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        rightFront.setPower(frontRightPower);
        leftFront.setPower(frontLeftPower);
        rightRear.setPower(backRightPower);
        leftRear.setPower(backLeftPower);
    }

    /**
     * Sets a uniform mode for all the motors
     *
     * @param mode the new uniform mode
     */
    protected void setModes(DcMotor.RunMode mode) {
        rightFront.setMode(mode);
        leftFront.setMode(mode);
        rightRear.setMode(mode);
        leftRear.setMode(mode);
    }
}
