package com.cjavellana.serialization;

import kafka.serializer.Encoder;

import java.util.List;

/**
 * Converts a {@link List} containing serializable objects to byte array
 */
public class ListEncoder implements Encoder<List> {

    @Override
    public byte[] toBytes(List list) {
        return new byte[0];
    }
}
