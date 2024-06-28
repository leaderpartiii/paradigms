package queue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    //Pred: n>0;
    //Post: elementImpl();
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    //Pred: (item != null)
    //Post: size' = size++ size = size'
    public void enqueue(Object item) {
        Objects.requireNonNull(item);
        enqueueImpl(item);
        size++;
    }

    protected abstract void enqueueImpl(Object item);


    //Pred: size>0
    //Post: element()
    public Object dequeue() {
        assert size > 0;
        Object elem = element();
        dequeueImpl();
        size--;
        return elem;
    }

    protected abstract void dequeueImpl();

    //Post: size==0;
    public boolean isEmpty() {
        return size == 0;
    }

    //Post: size;
    public int size() {
        return size;
    }

    //Post: size = 0 && clearImpl();
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();

    public void distinct() {
        HashSet<Object> set = new LinkedHashSet<>();
        while (!isEmpty()) {
            Object elem = dequeue();
            set.add(elem);
        }
        // :NOTE: no need for clear
        clear();
        for (Object s : set) {
            enqueue(s);
        }
        size = set.size();
    }
}
