/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import java.io.File;
import java.time.Instant;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests all the methods of class StringPersistorFile.
 *
 * @author boasm
 */
public class StringPersistorFileTest {

    /**
     * Test of setFile() method, of class StrinPersistorFile.
     */
    @Test
    public void testSetFile() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("C:\\BoasMeier\\spfTest.txt");
        spf.setFile(file);
        assertEquals("C:\\BoasMeier\\spfTest.txt", spf.getFile().getPath());
    }

    /**
     * Test of save() method, of class StrinPersistorFile.
     */
    @Test
    public void testSave() {
        StringPersistorFile spf = new StringPersistorFile();
        File file = new File("C:\\BoasMeier\\spfTest.txt");
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
