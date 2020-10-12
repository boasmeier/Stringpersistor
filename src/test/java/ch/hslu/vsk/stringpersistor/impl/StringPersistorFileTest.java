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
     * Test of setFile() method, of class StrinPersistorFile.
     */
    @Test
    public void testSetFile() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File(".\\spfTest.txt");
        spf.setFile(file);
        assertEquals(".\\spfTest.txt", spf.getFile().getPath());
    }

    /**
     * Test of setFile() method, of class StrinPersistorFile.
     */
    @Test
    public void testSetFileException() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("");
        final Exception e = assertThrows(IllegalStateException.class, () -> {
            spf.setFile(file);
        });
        assertEquals("Cannot set file without a path.", e.getMessage());
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
        assertEquals("Cannot set file without a path.", e.getMessage());
    }

    /**
     * Test of save() method, of class StrinPersistorFile.
     */
    @Test
    public void testSave() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File(".\\spfTest.txt");
        spf.setFile(file);
        spf.save(Instant.now(), "Test Payload");
        assertEquals("Test Payload", spf.get(1).get(0).getPayload());
    }

    /**
     * Test of equals and hashcode methods, of class StrinPersistorFile.
     */
    @Test
    public void testEqualsWithVerifier() {
        EqualsVerifier.forClass(StringPersistorFile.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }
}
