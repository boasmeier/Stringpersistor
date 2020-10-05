/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import java.time.Instant;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author boasm
 */
public class PersistedStringTest {

    /**
     * Test of getTimestamp method, of class PersistedString.
     */
    @Test
    public void testGetTimestamp() {
        Instant now = Instant.now();
        PersistedString persistedString = new PersistedString(now, "Test");
        assertEquals(now, persistedString.getTimestamp());
    }

    /**
     * Test of getPayload method, of class PersistedString.
     */
    @Test
    public void testGetPayload() {
        Instant now = Instant.now();
        PersistedString persistedString = new PersistedString(now, "Test");
        assertEquals("Test", persistedString.getPayload());
    }

    /**
     * Test of equals and hashcode methods, of class Person.
     */
    @Test
    public void testEqualsWithVerifier() {
        EqualsVerifier.forClass(PersistedString.class).verify();
    }

}
