package edu.andrewisnew.java.hibernate.inheritance.inheritance_table_per_class;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
//каждый наследник в своей таблице
//TABLE_PER_CLASS даёт возможность запрашивать Shape в отличие от MappedSuperclass. Происходит это через union.
// При этом hibernate создаёт доп колонку для использования её при инстанциации класса.
// стоит использовать если вы редко
//выполняете (или никогда не выполняете) запрос select sh from Shape sh и если у вас нет классов, ссылающихся на Shape
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
    @Enumerated(EnumType.STRING)
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public Shape() {
    }
}
