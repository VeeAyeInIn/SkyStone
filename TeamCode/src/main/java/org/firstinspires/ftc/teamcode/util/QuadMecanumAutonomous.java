package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class QuadMecanumAutonomous extends Autonomous {

    private final DcMotor rightFront, leftFront, rightRear, leftRear;

    public QuadMecanumAutonomous() {
        super(Global.VUFORIA_KEY);

        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");
    }

    public void forward(double power) {
        forward(power, rightFront, leftFront, rightRear, leftFront);
    }

    public void strafe(double power) {
        forward(power, rightFront, leftRear);
        forward(-power, leftFront, rightRear);
    }

    public void rotate(double power) {
        forward(power, rightFront, rightRear);
        forward(-power, leftFront, leftRear);
    }

    public DcMotor getRightFront() {
        return rightFront;
    }

    public DcMotor getLeftFront() {
        return leftFront;
    }

    public DcMotor getRightRear() {
        return rightRear;
    }

    public DcMotor getLeftRear() {
        return leftRear;
    }
}
