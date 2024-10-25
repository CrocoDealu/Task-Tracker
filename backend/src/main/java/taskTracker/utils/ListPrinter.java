package taskTracker.utils;

import java.util.ArrayList;
import java.util.List;

public class ListPrinter<T> implements Printer<T>{
    List<T> list;
    public ListPrinter() {
        this.list = new ArrayList<>();
    }

    @Override
    public void printAll(List<T> container) {
        list.addAll(container);
    }

    public List<T> getList() {
        return list;
    }
}
