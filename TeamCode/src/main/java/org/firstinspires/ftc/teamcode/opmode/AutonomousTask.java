package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Instance;

/**
 * @author NOTN Programming Team
 */
public final class AutonomousTask extends LinearOpMode {

    private static final Instance<AutonomousTask> INSTANCE = new Instance<>();

    /**
     * Default constructor to initiate storage of its instance.
     */
    public AutonomousTask() {
        // Instance is now tied to this. If you were to create a new version of this class, an error
        // would be thrown as it would attempt to store an instance when it is already assigned.
        INSTANCE.store(this);
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

    }
}
