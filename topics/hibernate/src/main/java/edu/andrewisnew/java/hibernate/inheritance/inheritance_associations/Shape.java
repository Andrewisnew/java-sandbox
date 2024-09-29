package edu.andrewisnew.java.hibernate.inheritance.inheritance_associations;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.List;

//создаются таблицы и для дочерних и для родительнкого. Во всех есть id, который в дочерних выступает и как FK.
//при получении родительской сущности из бд Hibernate сделает left outer join от shape ко всем наследникам
// и добавит ещё один столбец рассчитывающийся из id дочерних
//для сложных иерархий плохая производительность
//DiscriminatorColumn тут тоже можно использовать. Используется для вытаскивания Shape
//стоит выбирать если небольшая иерархия
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) //наследуемые могут быть nonnull
    protected Color color;

    @OneToMany
    protected List<Cookie> cookies;

    public Shape(Color color) {
        this.color = color;
    }

    public Shape() {
    }
}
