package edu.andrewisnew.java.hibernate.inheritance.table_per_class_mapped_superclass;

import edu.andrewisnew.java.hibernate.inheritance.Color;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
//каждый наследник в своей таблице
//по умолчанию свойства суперкласса игнорируются. Эта аннотация их пробрасывает
//MappedSuperclass можно использовать для сохранения иерархического embeddable. Но в сущность, в которую его встраивают,
//обязательно должен встраиваться конкретный класс. Hibernate не сможет смапить полиморфно.
@MappedSuperclass
public abstract class Shape {
    @Id
    protected long id;
    @Enumerated(EnumType.STRING)
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public Shape() {
    }
}
