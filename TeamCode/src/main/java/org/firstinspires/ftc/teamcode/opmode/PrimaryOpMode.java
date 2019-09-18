package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Instance;

/**
 * @author NOTN Programming Team
 * <p>
 * This is the main {@link OpMode} for use with manual controls via gamepads, phones, or some other
 * device. The Autonomous task will be handled using {@link com.qualcomm.robotcore.eventloop.opmode.LinearOpMode}.
 */
public final class PrimaryOpMode extends OpMode {

    private static final Instance<PrimaryOpMode> INSTANCE = new Instance<>();

    /**
     * Default constructor to initiate storage of its instance.
     */
    public PrimaryOpMode() {
        // Instance is now tied to this. If you were to create a new version of this class, an error
        // would be thrown as it would attempt to store an instance when it is already assigned.
        INSTANCE.store(this);
    }

    /**
     * Accesses the static referral to the Object
     *
     * @return the static instance
     */
    public static PrimaryOpMode getInstance() {
        return INSTANCE.get();
    }

    /**
     * This method loops when the <i>init</i> button is activated.
     *
     * @see OpMode#init_loop()
     */
    @Override
    public void init_loop() {

    }

    /**
     * This method runs once when the <i>init</i> button is activated.
     *
     * @see OpMode#init()
     */
    @Override
    public void init() {
        newStatus("Initialized");
    }

    /**
     * This method loops once the <i>start</i> button is activated.
     *
     * @see OpMode#loop()
     */
    @Override
    public void loop() {

    }

    /**
     * This method runs once when the <i>start</i> button is activated.
     *
     * @see OpMode#start()
     */
    @Override
    public void start() {
        newStatus("Started");
    }

    /**
     * This method runs once when the <i>stop</i> button is activated.
     *
     * @see OpMode#stop()
     */
    @Override
    public void stop() {
        newStatus("Stopped");
    }

    /**
     * Updates the {@link org.firstinspires.ftc.robotcore.external.Telemetry} data for the specific
     * caption 'Status'.
     *
     * @param status the new status of the robot
     */
    private void newStatus(String status) {
        telemetry.addData("Status", status);
    }
}
