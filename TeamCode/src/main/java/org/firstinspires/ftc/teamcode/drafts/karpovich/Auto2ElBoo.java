package org.firstinspires.ftc.teamcode.drafts.karpovich;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Auto2ElBoo {

    // Wheels
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;

    // Intake Gears
    private DcMotor leftGear;
    private DcMotor rightGear;

    // Arm (PID)
    private DcMotor arm;

    // Other
    private CRServo wrist;
    private CRServo latch;
    private Servo tray;

    boolean i = false;

    public void e(){
        //Move forward a bit
        moveDistance(0.5, 0.5, 0.0, 1.0, 1000);

        //Strafe right until the skystone is sensed
        while (/*not sensed*/i=false) {
            move(0.5, 0.5, 0.5, 0.5);
        }
        //Move forward until we grab the block

        //Grab block

        //Drive forward

        //Commit sudoku

        //Strafe towards center

        //Strafe through our gate

        //Strafe to the base

        //Place block on base
    }
    /*
    public void moveDistance(double rf, double rr, double lf, double lr, int distance){
        while(encoder value > distance) {
            leftFront.setPower(lf);
            leftRear.setPower(lr);
            rightFront.setPower(rf);
            rightRear.setPower(rr);
        }
    }
     */

    private void moveDistance(final double x, final double y, final double r, final double multiplier, int distance) {

        double dx = x * multiplier;
        double dy = y * multiplier;
        double dr = r * multiplier;
        while(/*encoder value*/0 > distance)
        // Change the power of everything
        leftFront.setPower(dy - dx + dr);
        rightFront.setPower(dy + dx - dr);
        leftRear.setPower(dy + dx + dr);
        rightRear.setPower(dy - dx - dr);
    }

    private void move(final double x, final double y, final double r, final double multiplier) {

        double dx = x * multiplier;
        double dy = y * multiplier;
        double dr = r * multiplier;

        // Change the power of everything
        leftFront.setPower(dy - dx + dr);
        rightFront.setPower(dy + dx - dr);
        leftRear.setPower(dy + dx + dr);
        rightRear.setPower(dy - dx - dr);
    }
}



