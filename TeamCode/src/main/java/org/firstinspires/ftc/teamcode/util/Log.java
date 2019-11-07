package org.firstinspires.ftc.teamcode.util;

/**
 * Keeps track of different ways to log information to the console, or in the case of the SkyStone
 * Robotics challenge, using Telemetry.
 */
public enum Log {

    /**
     * Used for information under the tag <i>INFO</i>
     * <p>
     * When would you put information under the <i>INFO</i> tag?
     * <li>Informational Data</li>
     * <li>Important Variable Updates</li>
     */
    INFO,

    /**
     * Used for information under the tag <i>ERROR</i>
     * <p>
     * When would you put information under the <i>ERROR</i> tag?
     * <li>Something happened that normally should not happen</li>
     * <li>Something threw an exception</li>
     */
    ERROR,

    /**
     * Used for information under the tag <i>WARNING</i>
     * <p>
     * When would you put information under the <i>WARNING</i> tag?
     * <li>Something happened that normally should not happen</li>
     * <li>Something that could endanger the program can happen</li>
     * <li>An action could not be completed due to errors</li>
     */
    WARNING,

    /**
     * Used for information under the tag <i>SEVERE</i>
     * <p>
     * When would you put information under the <i>INFO</i> tag?
     * <li>Something that affects the integrity of the code has occurred</li>
     * <li>Physical problems have occurred on the robot</li>
     * <li>An action could not be completed due to errors</li>
     */
    SEVERE,

    /**
     * Used for information under the tag <i>DEBUG</i>
     * <p>
     * When would you put information under the <i>INFO</i> tag?
     * <li>Detailed information like <i>INFO</i> data</li>
     */
    DEBUG
}
