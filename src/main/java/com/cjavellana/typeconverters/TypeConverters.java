package com.cjavellana.typeconverters;

import com.cjavellana.model.AvailableEntity;
import com.cjavellana.model.Employee;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.camel.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class TypeConverters implements org.apache.camel.TypeConverters {

    @Converter
    public AvailableEntity toAvailableEntity(String xmlValue) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(AvailableEntity.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        AvailableEntity availableEntity =
                (AvailableEntity) unmarshaller.unmarshal(new ByteArrayInputStream(xmlValue.getBytes("UTF-8")));
        return availableEntity;
    }

    @Converter
    public byte[] toByteArray(List<?> list) throws Exception {
        try (ByteOutputStream bos = new ByteOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(list);
            return bos.getBytes();
        }
    }

}
