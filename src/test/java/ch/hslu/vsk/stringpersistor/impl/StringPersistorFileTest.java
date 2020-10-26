/*
 * StringPersistorFileTest.java
 * Created on 05.10.2020
 *
 * Copyright(c) 2020 Boas Meier.
 * This software is the proprietary information of Boas Meier.
 */
package ch.hslu.vsk.stringpersistor.impl;

import java.io.File;
import java.time.Instant;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Tests all the methods of class StringPersistorFile.
 *
 * @author Boas Meier
 * @version JDK 12.0.2
 */
public class StringPersistorFileTest {

    /**
     * Test of setFile() method, of class StringPersistorFile.
     */
    @Test
    public void testSetFile() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("." + File.separator + "spfTest.log");
        spf.setFile(file);
        assertEquals("." + File.separator + "spfTest.log", spf.getFile().getPath());
    }

    /**
     * Test of setFile() method, of class StrinPersistorFile.
     */
    @Test
    public void testSetFileEmptyPath() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("");
        final Exception e = assertThrows(IllegalStateException.class, () -> {
            spf.setFile(file);
        });
        assertEquals("Can not set file without a path.", e.getMessage());
    }

    /**
     * Test of setFile() method, of class StrinPersistorFile.
     */
    @Test
    public void testSetFileExceptionNull() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = null;
        final Exception e = assertThrows(IllegalStateException.class, () -> {
            spf.setFile(file);
        });
        assertEquals("Can not set file without a path.", e.getMessage());
    }

    /**
     * Test of save() method, of class StrinPersistorFile.
     */
    @Test
    public void testSave() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("." + File.separator + "spfTest.log");
        spf.setFile(file);
        spf.save(Instant.now(), "Test Payload");
        assertEquals("Test Payload", spf.get(1).get(0).getPayload());
    }

    /**
     * Test of get(int count) method, of class StrinPersistorFile.
     */
    @Test
    public void testGet() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("." + File.separator + "spfTest.log");
        spf.setFile(file);
        for (int i = 0; i < 10; i++) {
            spf.save(Instant.now(), "Test Payload " + i);
        }
        assertEquals("Test Payload 9", spf.get(5).get(0).getPayload());
        assertEquals("Test Payload 8", spf.get(5).get(1).getPayload());
        assertEquals("Test Payload 7", spf.get(5).get(2).getPayload());
        assertEquals("Test Payload 6", spf.get(5).get(3).getPayload());
        assertEquals("Test Payload 5", spf.get(5).get(4).getPayload());
    }

    /**
     * Test of get(int count) method, of class StrinPersistorFile.
     */
    @Test
    public void testGetWhenFileNull() {
        StringPersistorFile spf = new StringPersistorFile();
        final Exception e = assertThrows(NullPointerException.class, () -> {
            spf.get(1);
        });
        assertEquals("File is not set.", e.getMessage());
    }

    /**
     * Test of equals and hashcode methods, of class StrinPersistorFile.
     */
    @Test
    public void testEqualsWithVerifier() {
        EqualsVerifier.forClass(StringPersistorFile.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }
}
