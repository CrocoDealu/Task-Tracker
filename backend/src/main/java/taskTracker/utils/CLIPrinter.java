package taskTracker.utils;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public final class CLIPrinter<T> implements Printer<T> {

    public CLIPrinter() {}

    public void printAll(List<T> container) {
        Iterator<T> iterator = container.iterator();
        while (iterator.hasNext()) {
            T o = iterator.next();
            System.out.println(o);
        }
    }
}