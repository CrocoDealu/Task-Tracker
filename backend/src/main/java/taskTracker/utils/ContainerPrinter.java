package taskTracker.utils;

import org.springframework.stereotype.Component;
import taskTracker.repository.Container;

import java.util.Iterator;
import java.util.List;

@Component
public class ContainerPrinter<T> implements Printer<T>{
    Container<T> c;

    public ContainerPrinter(Container<T> container) {
        c = container;
    }

    public Container<T> getContainer() {
        return c;
    }

    @Override
    public void printAll(List<T> container) {
        for (T o : container) {
            c.add(o);
        }
    }
}
