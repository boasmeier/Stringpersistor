/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import java.time.Instant;
import java.util.Objects;

/**
 * {Class description here}.
 *
 * @author Boas Meier
 * @version JDK 12.0.2
 */
public final class PersistedString {

    private final Instant timestamp;
    private final String payload;

    public PersistedString(final Instant timestamp, final String payload) {
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public final Instant getTimestamp() {
        return timestamp;
    }

    public final String getPayload() {
        return payload;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.timestamp, this.payload);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersistedString)) {
            return false;
        }
        final PersistedString other = (PersistedString) obj;
        return Objects.equals(this.timestamp, other.getTimestamp()) && Objects.equals(this.payload, other.getPayload());
    }
}
