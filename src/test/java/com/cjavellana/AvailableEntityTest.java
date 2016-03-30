package com.cjavellana;


import com.cjavellana.model.AvailableEntity;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Calendar;

public class AvailableEntityTest {

    @Test
    public void itCanConvertObjectToXmlString() throws Exception {
        Calendar cal = Calendar.getInstance();


        AvailableEntity availableEntity = new AvailableEntity();
        availableEntity.setBookCode("11");
        availableEntity.setCountryCode("SG");
        availableEntity.setPositionDate(cal.getTime());
        availableEntity.setRerun(false);
        availableEntity.setTable("Employees");

        JAXBContext jaxbContext = JAXBContext.newInstance(AvailableEntity.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        StringWriter sw = new StringWriter();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(availableEntity, sw);

        Assert.assertTrue(sw.toString().contains("<countryCode>SG</countryCode>"));
        Assert.assertTrue(sw.toString().contains("<bookCode>11</bookCode>"));
    }

    @Test
    public void itCanConvertXmlStringToObject() throws Exception {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<availableEntity>\n" +
                "    <bookCode>11</bookCode>\n" +
                "    <countryCode>SG</countryCode>\n" +
                "    <positionDate>30-Mar-2016</positionDate>\n" +
                "    <isRerun>false</isRerun>\n" +
                "    <table>Employees</table>\n" +
                "</availableEntity>";

        JAXBContext jaxbContext = JAXBContext.newInstance(AvailableEntity.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        AvailableEntity availableEntity =
                (AvailableEntity) unmarshaller.unmarshal(new ByteArrayInputStream(content.getBytes("UTF-8")));
        Assert.assertEquals("11", availableEntity.getBookCode());
    }
}
