package org.firstinspires.ftc.teamcode.archive.util;

import android.support.annotation.NonNull;

/**
 * @author Connor Vann
 * <p>
 * This extends from the superclass {@link IllegalArgumentException} to throw an Exception when data
 * is modified in an {@link Instance}. This will only be thrown if the value has already been set,
 * similar to how an IDE would handle a <i>final</i> field.
 */
public class InstanceModificationException extends IllegalArgumentException {

    private final Instance<?> instance;

    /**
     * Throws an exception when an {@link Instance} is modified while a value is already set. This
     * is to make sure it can be used statically and remain constant for other classes.
     *
     * @param instance the instance being modified
     */
    public InstanceModificationException(@NonNull final Instance<?> instance) {
        super("Attempted to modify non-null value of " + instance.toString() + "!");
        this.instance = instance;
    }

    /**
     * Gets the {@link Instance} in question, if for some reason it needs to be reached still.
     *
     * @return the {@link Instance} causing the Exception
     */
    public Instance<?> getInstance() {
        return instance;
    }
}