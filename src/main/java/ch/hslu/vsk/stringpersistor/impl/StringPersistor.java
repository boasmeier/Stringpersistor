/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import java.io.File;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author boasm
 */
public interface StringPersistor {

    public void setFile(File file);

    public void save(Instant timestamp, String payload);

    public List<PersistedString> get(int count);
}
