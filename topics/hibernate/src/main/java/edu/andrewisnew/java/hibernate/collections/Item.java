package edu.andrewisnew.java.hibernate.collections;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ElementCollection
    @CollectionTable( //будет создана доп таблица с ITEM_ID и IMAGE. Так как использован set, ключ будет составной из обоих полей
            name = "IMAGE", //Имя по умолчанию ITEM_IMAGES
            joinColumns = {@JoinColumn(name = "ITEM_ID")}
    )
    @Column(name = "FILENAME") //Имя по умолчанию IMAGES
    private Set<String> images = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "COMMENT")
    @Column(name = "COMMENT_TEXT")
    @org.hibernate.annotations.CollectionId( //вводится суррогатный первичный ключ чтобы для того чтобы COMMENT_TEXT COMMENT_ID могли повторяться
            column = @Column(name = "COMMENT_ID"), // Столбец суррогатного первичного ключа
            type = @org.hibernate.annotations.Type(type = "long"),
            generator = "sequence")
    protected Collection<String> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "VIDEO",
            joinColumns = {@JoinColumn(name = "ITEM_ID")}
    )
//    @OrderColumn //Позволяет сохранять порядок; имя по умолчанию: VIDEOS_ORDER - доп столбец с индексом
    //аналог из JPA поддерживает только тип asc/desc. а в этот можно sql, даже вот так substring(FILENAME, 0, 3) desc
    @org.hibernate.annotations.OrderBy(clause = "FILENAME desc")
    @Column(name = "FILENAME")
    protected List<String> videos = new ArrayList<>();//первичный ключ VIDEOS_ORDER ITEM_ID

    @ElementCollection
    @CollectionTable(name = "CATEGORY")
    @MapKeyColumn(name = "CATEGORY") // Отображает ключ
    @Column(name = "CATEGORY_VALUE") // Отображает значение
//    @MapKeyEnumerated
    protected Map<String, String> categoryToValue = new HashMap<>(); //ключ CATEGORY + ITEM_ID

    @ElementCollection
    @CollectionTable(name = "TAG")
    @MapKeyColumn(name = "TAG")
    @Column(name = "TAG_VALUE")
    @org.hibernate.annotations.SortComparator(CaseInsensitiveComparator.class)
    protected SortedMap<String, String> tags = new TreeMap<>();

    @ElementCollection
    @CollectionTable(name = "KEYWORD")
    @Column(name = "KEYWORD")
    @org.hibernate.annotations.SortComparator(CaseInsensitiveComparator.class)
    protected SortedSet<String> keywords = new TreeSet<>();

    public Item(Set<String> images, Collection<String> comments, List<String> videos, Map<String, String> categoryToValue, SortedMap<String, String> tags, SortedSet<String> keywords) {
        this.images = images;
        this.comments = comments;
        this.videos = videos;
        this.categoryToValue = categoryToValue;
        this.tags = tags;
        this.keywords = keywords;
    }

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", images=" + images +
                ", comments=" + comments +
                ", videos=" + videos +
                ", categoryToValue=" + categoryToValue +
                ", tags=" + tags +
                ", keywords=" + keywords +
                '}';
    }

    public static class CaseInsensitiveComparator implements Comparator<String> {
        private final Comparator<String> delegate = Comparator.comparing(String::toString);

        @Override
        public int compare(String s1, String s2) {
            return delegate.compare(s1, s2);
        }
    }
 }
