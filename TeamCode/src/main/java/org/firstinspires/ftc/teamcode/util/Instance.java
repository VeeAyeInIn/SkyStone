package org.firstinspires.ftc.teamcode.util;

import android.support.annotation.NonNull;

/**
 * @param <T> Any extension of an {@link Object}
 * @author Connor Vann
 * <p>
 * The purpose of the class is to provide a pseudo-constant value available to any form of data. The
 * usage of the generic type {@link T} is a placeholder for an extension of {@link Object}, and is
 * thus applicable to anything.
 * <p>
 * When using this class, keep in mind that editing values are not permitted, and will result in an
 * {@link InstanceModificationException}. Check if the value is <i>null</i> before modifying data,
 * and to commonly use it to prevent a {@link NullPointerException} if the value has not been
 * assigned on creation, but via {@link #store(Object)}.
 * @see InstanceModificationException
 */
public class Instance<T> {

    private T instance;

    /**
     * Creates an instance of the class without a value, so the value can be later assigned using
     * {@link #store(Object)} at any point.
     */
    public Instance() {
    }

    /**
     * Creates an instance of the class with a predefined value. The value is now constant and
     * <i>cannot</i> be modified, otherwise it will throw an {@link InstanceModificationException}.
     *
     * @param instance the predefined value being stored
     */
    public Instance(@NonNull final T instance) {
        this.instance = instance;
    }

    /**
     * @param instance the predefined value being stored
     */
    public void store(@NonNull final T instance) {
        if (this.instance != null) throw new InstanceModificationException(this);
        this.instance = instance;
    }

    /**
     * @return the stored value
     */
    public T get() {
        return instance;
    }
}
