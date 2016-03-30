package com.cjavellana.model;

import com.cjavellana.jaxbsupport.BooleanAdapter;
import com.cjavellana.jaxbsupport.DateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement
public class AvailableEntity {


    private String table;

    private String countryCode;

    private String bookCode;

    private Date positionDate;

    private Boolean isRerun;

    public String getTable() {
        return table;
    }

    @XmlElement
    public void setTable(String table) {
        this.table = table;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @XmlElement
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @XmlElement
    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    @XmlElement(name = "positionDate", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(Date positionDate) {
        this.positionDate = positionDate;
    }

    public Boolean getRerun() {
        return isRerun;
    }

    @XmlElement(name = "isRerun", required = false, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    public void setRerun(Boolean rerun) {
        isRerun = rerun;
    }
}
