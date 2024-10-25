package taskTracker.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class AbstractMapContainer<T> implements Container<T> {

    protected HashMap<Integer, T> elems = new HashMap<>();  // Encapsulated to protect direct access

    @Override
    public int size() {
        return elems.size();
    }

    @Override
    public boolean isEmpty() {
        return elems.isEmpty();
    }

    public ArrayList<T> getAll() {
        return new ArrayList<>(elems.values());
    }

    public Iterator<T> iterator() {
        return elems.values().iterator();
    }

    protected void clear() {
        elems.clear();  // Moved 'clear' from JSONRepository to here for reuse
    }
}
