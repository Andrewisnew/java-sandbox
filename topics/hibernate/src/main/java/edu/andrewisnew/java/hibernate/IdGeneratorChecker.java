package edu.andrewisnew.java.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IdGeneratorChecker {
    @Id //обязателен для всех Entity
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
    private Long id;

}
