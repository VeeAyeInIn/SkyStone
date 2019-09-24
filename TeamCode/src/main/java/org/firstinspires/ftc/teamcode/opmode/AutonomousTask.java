package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.util.Instance;

/**
 * @author NOTN Programming Team
 */
public final class AutonomousTask extends LinearOpMode {

    private static final Instance<AutonomousTask> INSTANCE = new Instance<>();

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

    /**
     * Default constructor to initiate storage of its instance, along with basic initialization
     * of all variables.
     */
    public AutonomousTask() {
        // Instance is now tied to this. If you were to create a new version of this class, an error
        // would be thrown as it would attempt to store an instance when it is already assigned.
        INSTANCE.store(this);

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
    }

    /**
     * Accesses the static referral to the Object.
     *
     * @return the static instance
     */
    public static AutonomousTask getInstance() {
        return INSTANCE.get();
    }

    /**
     * Executes when the {@link LinearOpMode} selection is activated.
     */
    @Override
    public void runOpMode() {
        // Preform startup actions here that should not be done within the constructor
        // . . .

        // Wait until the START button has been pressed
        waitForStart();
        time.reset();
    }

    /**
     * Moves the robot in the specified direction, along with a determined power and for the passed
     * amount of milliseconds.
     *
     * @param direction
     * @param power
     * @param timeMs
     */
    private void move(String direction, double power, double timeMs) {

    }
}
