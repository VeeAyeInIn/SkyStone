package org.firstinspires.ftc.teamcode.drafts.vann;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class VannAutonomous extends LinearOpMode {

    // We can load TFOD assets through this
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String TFOD_STONE_ASSET = "Stone";
    private static final String TFOD_SKYSTONE_ASSET = "Skystone";

    // Vuforia
    private final VuforiaLocalizer.Parameters parameters;
    private final VuforiaLocalizer localizer;

    // Tensor Flow Object Detection
    private final TFObjectDetector tfod;

    // Keep track of time
    private final ElapsedTime elapsed;

    // Hardware
    private final DcMotor rightFront;
    private final DcMotor leftFront;
    private final DcMotor rightBack;
    private final DcMotor leftBack;

    public VannAutonomous() {
        super(); // Required at the start of the constructor

        elapsed = new ElapsedTime();

        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");

        parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AbyhPYH/////AAABmVllNwWWaUnSozzaWrSP7kuAOpem63iiGDaZXW1b7zxbWr5h4qHCM4YqWKiRliNBlApeot38Nz3iQxRPGeIKlPlEtRbZXBY4nstLBf5mPFHwpq6Ajsr/3G60eThr4G+9KolTe30N2MHtfO0G7PkxkzP7wRPf8fji8+CMCvOxE19ZY6YF0L9MJEK+/p6JiXWO7E97kKcGlcfO85ipV5mC5JL9LVYOcVc5KvjkAwQiteEasU3Fv8kW/s4C1f/HPNqvF9I3jgyNz6HdxF/4OCic6nlJITNiTkKMTOeHYp65SXkFUYDsRKeTEvKQtTOe4Qkn6bmY2jhN2/EU1HC1JIOJ3kTVhsGZ8bWEzKA6aJ5CNuUa";

        localizer = ClassFactory.getInstance().createVuforia(parameters);

        // Tensor Flow Object Detector :: Initialization of Detector
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, localizer);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, TFOD_STONE_ASSET, TFOD_SKYSTONE_ASSET);
        } else {
            // Identify error to the class
            telemetry.addData("Error at " + getClass().getCanonicalName(), "TFOD Detector could not be created.");
            tfod = null; // We can't do anything else, but we MUST assign a value to it
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // Head right until you find a skystone...
        setPowers(1, -1, -1, 1);

        while (getClosestSkystone() == null) {
            idle(); // Wait until we locate it
        }

        while (Math.abs(getClosestSkystone().estimateAngleToObject(AngleUnit.DEGREES)) > 1) {
            if (getClosestSkystone().estimateAngleToObject(AngleUnit.DEGREES) < 0) {
                setPowers(0.1);
            } else {
                setPowers(-0.1);
            }
            idle();
        }

        setPowers(0);
        Thread.sleep(1000);
        setPowers(0.1, 0.1, 0.1, 0.1);
    }

    public Recognition getClosestSkystone() {
        Recognition closest = null;

        for (Recognition recognition : tfod.getRecognitions()) {
            if (recognition.getLabel().equals(TFOD_SKYSTONE_ASSET)) {
                if (closest == null) closest = recognition;
                else if (Math.abs(recognition.estimateAngleToObject(AngleUnit.DEGREES)) < Math.abs(closest.estimateAngleToObject(AngleUnit.DEGREES))) {
                    closest = recognition;
                }
            }
        }

        return closest;
    }

    /**
     * Sets a uniform power for each of the motors
     *
     * @param power the uniform power
     */
    protected void setPowers(double power) {
        setPowers(power, power, power, power);
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
        rightBack.setPower(backRightPower);
        leftBack.setPower(backLeftPower);
    }

    /**
     * Sets a uniform mode for all the motors
     *
     * @param mode the new uniform mode
     */
    protected void setModes(DcMotor.RunMode mode) {
        rightFront.setMode(mode);
        leftFront.setMode(mode);
        rightBack.setMode(mode);
        leftBack.setMode(mode);
    }

    /**
     * Prints out an error to the phone along with the time it occurred
     *
     * @param error the issue that occurred
     * @return the item added to the telemetry
     */
    protected Telemetry.Item error(String error) {
        return telemetry.addData("Error at " + elapsed.toString(), error);
    }

    /**
     * Prints out an error to the phone along with the time it occurred
     *
     * @param e the exception that occurred
     * @return the item added to the telemetry
     */
    protected Telemetry.Item error(Exception e) {
        return error(e.getMessage());
    }
}