package org.firstinspires.ftc.teamcode.archive.trash.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

public class BrobotAutonomous extends LinearOpMode {

    // We can load TFOD assets through this
    public static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    public static final String TFOD_STONE_ASSET = "Stone";
    public static final String TFOD_SKYSTONE_ASSET = "Skystone";
    // Motors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor leftGear;
    private DcMotor rightGear;
    private DcMotor arm;
    // Continuous Rotational Servos
    private CRServo wrist;
    private CRServo tray;
    // Servos
    private Servo latch;
    // Vuforia
    private VuforiaLocalizer.Parameters vuforiaParameters;
    private VuforiaTrackables vuforiaTrackables;
    private VuforiaLocalizer vuforiaLocalizer;

    // Tensor Flow Object Detection
    private TFObjectDetector tfod;

    // Keep track of time
    private ElapsedTime elapsedTime;

    @Override
    public void runOpMode() throws InterruptedException {

        // Setup the robot
        this.assign();

        // Setup Vuforia and TFOD
        this.visualInit();

        // +-+- for strafe

        Runnable first = () -> {

            double angle; // Close in on the closest SkyStone
            while (Math.abs(angle = closestSkystone().estimateAngleToObject(AngleUnit.DEGREES))
                    /* Angle to closest SkyStone */ > 5) {
                if (angle < 0) { // Is negative?
                    move(0.25, -0.25, 0.25, -0.25); // Move left
                } else {
                    move(-0.25, 0.25, -0.25, 0.25); // Move right
                }
            }

            // Move forward to grab the SkyStone
        };
    }

    private void move(double lf, double rf, double lr, double rr) {
        leftFront.setPower(lf);
        rightFront.setPower(rf);
        leftRear.setPower(lr);
        rightRear.setPower(rr);
    }

    private void assign() {

        // Assign all DcMotors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftGear = hardwareMap.dcMotor.get("leftGear");
        rightGear = hardwareMap.dcMotor.get("rightGear");
        arm = hardwareMap.dcMotor.get("arm");

        // Assign all CRServos
        wrist = hardwareMap.crservo.get("wrist");
        tray = hardwareMap.crservo.get("tray");

        // Assign all Servos
        latch = hardwareMap.servo.get("latch");

        // Begin running the motors
        this.setModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void visualInit() {
        elapsedTime = new ElapsedTime();

        vuforiaParameters = new VuforiaLocalizer.Parameters();
        vuforiaParameters.vuforiaLicenseKey = " TOO LAZY TO DO THIS RN ";
        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(vuforiaParameters);
        vuforiaTrackables = vuforiaLocalizer.loadTrackablesFromAsset("SkyStone");

        // Tensor Flow Object Detector Initialization
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforiaLocalizer);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, TFOD_STONE_ASSET, TFOD_SKYSTONE_ASSET);
        } else {
            tfod = null; // We can't do anything else, but we MUST assign a value to it
        }
    }

    private void setModes(DcMotor.RunMode runmode) {
        leftFront.setMode(runmode);
        rightFront.setMode(runmode);
        leftRear.setMode(runmode);
        rightRear.setMode(runmode);
        leftGear.setMode(runmode);
        rightGear.setMode(runmode);
        arm.setMode(runmode);
    }

    private boolean canSeeSkystone() {
        for (Recognition recognition : tfod.getRecognitions()) {
            if (recognition.getLabel().equals(TFOD_SKYSTONE_ASSET)) return true;
        }
        return false;
    }

    private List<Recognition> skystones() {
        List<Recognition> skystones = new ArrayList<>();
        for (Recognition recognition : tfod.getRecognitions()) {
            if (recognition.getLabel().equals(TFOD_SKYSTONE_ASSET)) {
                skystones.add(recognition);
            }
        }
        return skystones;
    }

    private Recognition closestSkystone() {
        Recognition closest = null;
        for (Recognition recognition : skystones()) {
            if (closest == null) closest = recognition;
            else {
                double a = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                double b = closest.estimateAngleToObject(AngleUnit.DEGREES);
                if (Math.abs(a) < Math.abs(b)) {
                    closest = recognition;
                }
            }
        }
        return closest;
    }
}
