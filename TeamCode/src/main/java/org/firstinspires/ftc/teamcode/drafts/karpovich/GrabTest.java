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

    @Override
    public void init() {

        // Find all devices
        for (HardwareDevice device : hardwareMap) {
            telemetry.addData(device.getDeviceName(), "has been detected.\n");
        }

        // Now update the info
        telemetry.update();

        grabber = hardwareMap.servo.get("grabber");
    }

    public void loop()
    {
        if (gamepad2.dpad_up) {
            grabber.setPosition(90.0);
        }
    }
}
