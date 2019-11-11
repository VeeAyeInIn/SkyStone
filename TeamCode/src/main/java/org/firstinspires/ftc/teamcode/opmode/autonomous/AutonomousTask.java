package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NOTN Programming Team
 */
public final class AutonomousTask extends LinearOpMode {

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
    private final ElapsedTime time;

    // Hardware
    private final Map<String, DcMotor> motorMap;

    /**
     * Default constructor to initiate storage of its instance, along with basic initialization
     * of all variables.
     */
    public AutonomousTask() {
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
            telemetry.addData("Error@" + getClass().getCanonicalName(), "TFOD Detector could not be created.");
            tfod = null; // We can't do anything else, but we MUST assign a value to it
        }

        time = new ElapsedTime();
        motorMap = new HashMap<>();
    }

    /**
     * Executes when the {@link LinearOpMode} selection is activated.
     */
    @Override
    public void runOpMode() {

        // Preform startup actions here that should not be done within the constructor
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

        // Wait until the START button has been pressed
        waitForStart();
        time.reset();
        tfod.activate();

        // NAVIGATION
        boolean cycle = true;
        while (cycle) {
            for (Recognition recognition : getImage()) {
                if (recognition.getLabel().equals(TFOD_SKYSTONE_ASSET)) {
                    cycle = false;
                    break;
                }
            }
            // Action
        }

        // We have located a SkyStone, now we need to take necessary actions
    }

    /**
     * Get the current list of {@link Recognition}.
     *
     * @return the list of {@link Recognition}
     */
    private strictfp List<Recognition> getImage() {
        List<Recognition> recognitions = new ArrayList<>();

        // If TFOD has been assigned
        if (tfod != null) {

            // Then get recognitions
            recognitions.addAll(tfod.getUpdatedRecognitions());

            // If there are recognitions
            if (!recognitions.isEmpty()) {
                int stone = 0;
                int skystone = 0;

                // Iterate through each recognition
                for (Recognition rec : recognitions) {

                    // Adding what we found
                    if (rec.getLabel().equals(TFOD_STONE_ASSET)) {
                        stone++;
                    } else if (rec.getLabel().equals(TFOD_SKYSTONE_ASSET)) {
                        skystone++;
                    }

                    // Get center of the object
                    // (rec.getLeft() + rec.getRight()) / 2;

                    double angle = rec.estimateAngleToObject(AngleUnit.DEGREES);
                }

                telemetry.addData("Stone Count", stone);
                telemetry.addData("SkyStone Count", skystone);
            } else {
                // TODO error if null recognition
            }
        }

        return Collections.unmodifiableList(recognitions);
    }
}