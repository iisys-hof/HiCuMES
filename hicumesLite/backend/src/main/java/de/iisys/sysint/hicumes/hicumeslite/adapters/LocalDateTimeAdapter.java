package de.iisys.sysint.hicumes.hicumeslite.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter
        extends XmlAdapter<String, LocalDateTime>
{

    public LocalDateTime unmarshal(String inputDate) {
        if (inputDate == null)
        {return null;}
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        var result = LocalDateTime.parse(inputDate, formatter);
        return result;
    }

    public String marshal(LocalDateTime inputDate) {
        return inputDate != null ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(inputDate) : null;
    }

}
