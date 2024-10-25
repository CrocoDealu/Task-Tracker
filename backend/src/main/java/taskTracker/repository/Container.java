package taskTracker.repository;

import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public interface Container<T> {
    T remove(int id) throws Exception;
    void add(T o);
    int size();
    boolean isEmpty();
    Iterator<T> iterator();
}
