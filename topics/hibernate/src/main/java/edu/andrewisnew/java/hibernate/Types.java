package edu.andrewisnew.java.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Types {
    @Id
    int id;
    byte aByte;
    short aShort;
    int anInt;
    long aLong;
    boolean aBoolean;
    BigInteger aBigInteger;
    BigDecimal aBigDecimal;
    double aDouble;
    float aFloat;
    char aChar;
    Date aDate;
    Instant anInstant;
    LocalDate aLocalDate;
    LocalTime aLocalTime;
    String aString;

    /*
    PG
        id integer NOT NULL,
    abigdecimal numeric(19,2),
    abiginteger numeric(19,2),
    aboolean boolean NOT NULL,
    abyte smallint NOT NULL,
    achar character(1) COLLATE pg_catalog."default" NOT NULL,
    adate timestamp without time zone,
    adouble double precision NOT NULL,
    afloat real NOT NULL,
    alocaldate date,
    alocaltime time without time zone,
    along bigint NOT NULL,
    ashort smallint NOT NULL,
    astring character varying(255) COLLATE pg_catalog."default",
    aninstant timestamp without time zone,
    anint integer NOT NULL,
     */

}
