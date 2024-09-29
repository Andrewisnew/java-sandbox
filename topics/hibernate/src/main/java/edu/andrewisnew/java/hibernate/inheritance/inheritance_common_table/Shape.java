package edu.andrewisnew.java.hibernate.inheritance.inheritance_common_table;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
//самая производителььная и простая стратегия
//столбцы не своей сущности - null.
//минус в том, что нужно отказаться от nonnull ограничения а также денормализировать таблицу. Она уже не в 3НФ

//В дочерних Hibernate игнорирует аннотацию @NotNull при формировании схемы, но проверя-
//ет ее перед вставкой записи во время выполнения.

//стоит использовать если дочерние добавляют мало собственных полей
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//в связке с DiscriminatorValue в дочерних для маркировки. Если не задан, то Hibernate будут использоваться имена классов
@DiscriminatorColumn(name = "SHAPE_TYPE")
//Вместо DiscriminatorColumn можно сделать вот так. Тогда столбца не будет. DiscriminatorValue остаётся нужен.
//@org.hibernate.annotations.DiscriminatorFormula(
//        "case when RADIUS is not null then ‘R’ else ‘C’ end"
//)
public abstract class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) //наследуемые могут быть nonnull
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public Shape() {
    }
}
