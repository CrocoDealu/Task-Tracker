package taskTracker.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Printer<T> {
    public void printAll(List<T> container);
}
