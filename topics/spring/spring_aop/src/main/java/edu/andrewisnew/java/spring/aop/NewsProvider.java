package edu.andrewisnew.java.spring.aop;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsProvider {
    private final List<String> news = new ArrayList<>();

    public void saveNews(String news) {
        this.news.add(news);
    }

    @Loggable
    public List<String> showLastNews(int n) {
        return news.subList(Math.max(news.size() - n, 0), news.size());
    }

    @Loggable
    public String showNews(int index) {
        return news.get(index);
    }
}
