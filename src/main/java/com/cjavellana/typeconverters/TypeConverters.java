package com.cjavellana.typeconverters;

import com.cjavellana.model.AvailableEntity;
import org.apache.camel.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;


public class TypeConverters implements org.apache.camel.TypeConverters {

    @Converter
    public AvailableEntity toAvailableEntity(String xmlValue) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(AvailableEntity.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        AvailableEntity availableEntity =
                (AvailableEntity) unmarshaller.unmarshal(new ByteArrayInputStream(xmlValue.getBytes("UTF-8")));
        return availableEntity;
    }

}
