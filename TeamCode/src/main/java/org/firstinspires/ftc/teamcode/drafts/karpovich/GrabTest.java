package org.firstinspires.ftc.teamcode.drafts.karpovich;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Locale;

@TeleOp(name = "GrabTest", group = "Iterative OpMode")
public class GrabTest extends OpMode {
    private Servo grabber;
    private CRServo gate;

    @Override
    public void init() {

        // Find all devices
        for (HardwareDevice device : hardwareMap) {
            telemetry.addData(device.getDeviceName(), "has been detected.\n");
        }

        // Now update the info
        telemetry.update();

        grabber = hardwareMap.servo.get("grabber");
        gate = hardwareMap.crservo.get("gate");
    }

    public void loop()
    {
        if (gamepad2.dpad_up) {
            grabber.setPosition(90.0);
        } else if (gamepad2.dpad_down){
            grabber.setPosition(0.0);
        }
        if (Math.abs(gamepad2.left_stick_y) > .1){
            gate.setPower(gamepad2.left_stick_y);
        }
    }
}
