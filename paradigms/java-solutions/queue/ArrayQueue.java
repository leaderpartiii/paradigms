package queue;

import java.util.*;
import java.util.function.Predicate;

//Inv: (\forall -1 < index < size | arrayQueue[(index+indexHead)%arrayQueue.length]!=null)
public class ArrayQueue extends AbstractQueue implements Queue {
    private static int indexTail; // :NOTE: 2 of 3
    private static int indexHead;
    private static final int basic_size = 16;
    private static Object[] arrayQueue = new Object[basic_size];

    //Post: arrayQueue[indexHead]
    protected Object elementImpl() {
        return arrayQueue[indexHead];
    }

    //Post: arrayQueue[indexTail] = element
    //indexTail'= (indexTail + 1)%arrayQueue.length
    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        arrayQueue[indexTail] = element;
        indexTail = (indexTail + 1) % (arrayQueue.length);
    }

    private void ensureCapacity(int capacity) {
        if (arrayQueue.length < capacity) {
            Object[] temp = new Object[capacity * 2];

            System.arraycopy(arrayQueue, indexHead, temp, 0, arrayQueue.length - indexHead);
            System.arraycopy(arrayQueue, 0, temp, arrayQueue.length - indexHead, indexHead);

            indexTail = (indexTail + arrayQueue.length - indexHead) % temp.length;
            indexHead = 0;
            arrayQueue = temp;
        }
    }

    //Post: indexHead' = (indexHead + 1)%arrayQueue.length
    // indexHead = indexHead'
    protected void dequeueImpl() {
        indexHead = (indexHead + 1) % arrayQueue.length;
    }

    //Pred: Predicate<Object> !=null
    //Post: (min index < size | p.test[(index+indexHead)%arrayQueue.length] is true ) || (index == size && index = -1)
    public int indexIf(Predicate<Object> p) {
        return generalIndexIf(p, 0);
    }

    //Pred: Predicate<Object> !=null
    //Post: (max index >=0 | p.test[(index+indexHead)%arrayQueue.length] is true ) || (\forall size>index>-1 p.test[(index+indexHead)%arrayQueue.length] is false => index = -1)
    public int lastIndexIf(Predicate<Object> p) {
        return generalIndexIf(p, size - 1);
    }

    private int generalIndexIf(Predicate<Object> p, int temp) {
        Objects.requireNonNull(p);
        int index = temp;
        while (size - 1 >= index && index >= 0) {
            if (p.test(arrayQueue[(index + indexHead) % arrayQueue.length])) {
                break;
            }
            if (temp == 0) {
                index++;
            } else {
                index--;
            }
        }
        return index == size ? -1 : index;
    }

    //Post: indexTail = indexHead = 0
    public void clearImpl() {
        // :NOTE: не освобождаются объекты
        indexTail = indexHead = 0;
    }
}
