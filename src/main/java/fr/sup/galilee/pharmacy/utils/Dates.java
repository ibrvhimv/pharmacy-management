package fr.sup.galilee.pharmacy.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class Dates {

    public Date from(LocalDateTime from) {
        return Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
    }
}
