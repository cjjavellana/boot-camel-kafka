package com.cjavellana.serialization;

import com.cjavellana.model.Employee;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Converts a {@link List} containing serializable objects to byte array
 */
public class ListEncoder implements Encoder<List> {

    private VerifiableProperties properties;

    public ListEncoder(VerifiableProperties properties) {
        this.properties = properties;
    }

    @Override
    public byte[] toBytes(List list) {
        try (ByteOutputStream bos = new ByteOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(list);
            return bos.getBytes();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
