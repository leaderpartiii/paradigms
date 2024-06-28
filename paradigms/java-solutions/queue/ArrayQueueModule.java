package queue;

import java.util.Objects;
import java.util.function.Predicate;

//Model: massive with FIFO, first in(arrayQueue[indexTail])  first out(arrayQueue[indexHead])
//Inv: (\forall -1 < index < size | arrayQueue[(index+indexHead)%arrayQueue.length] != null)
public class ArrayQueueModule {
    private static int indexTail;
    private static int indexHead;
    private static final int basic_size = 16;
    private static int size;
    private static Object[] arrayQueue = new Object[basic_size];

    //Pred: (element != null)
    //Post: arrayQueue[indexTail] = element
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        arrayQueue[indexTail] = element;
        indexTail = (indexTail + 1) % (arrayQueue.length);
        size++;
    }

    private static void ensureCapacity(int capacity) {
        if (arrayQueue.length < capacity) {
            Object[] temp = new Object[capacity * 2];
            System.arraycopy(arrayQueue, indexHead, temp, 0, arrayQueue.length - indexHead);
            System.arraycopy(arrayQueue, 0, temp, arrayQueue.length - indexHead, indexHead);
            indexTail = (indexTail + arrayQueue.length - indexHead) % temp.length;
            indexHead = 0;
            arrayQueue = temp;
        }
    }

    //Pred: size>0
    //Post: arrayQueue[indexHead]
    public static Object element() {
        assert size > 0;
        return arrayQueue[indexHead];
    }

    //Pred: size>0
    //Post: arrayQueue[indexHead]
    public static Object dequeue() {
        assert size > 0;
        Object elem = element();
        indexHead = (indexHead + 1) % arrayQueue.length;
        size--;
        return elem;
    }

    //Pred: Predicate<Object> p != null
    //Post: (min index < size | p.test[(index+indexHead)%arrayQueue.length] is true ) || (index == size && index = -1)
    public static int indexIf(Predicate<Object> p) {
        return generalIndexIf(p, 0);
    }

    //Pred: Predicate<Object> != null
//    Post: (max index >= 0 | p.test[(index+indexHead)%arrayQueue.length] is true ) || (\forall size>index>-1 p.test[(index+indexHead)%arrayQueue.length] is false => index = -1)
    public static int lastIndexIf(Predicate<Object> p) {
        return generalIndexIf(p, size - 1);
    }

    private static int generalIndexIf(Predicate<Object> p, int temp) {
        Objects.requireNonNull(p);
        int index = temp;
        while (size > index && index > -1) {
            if (p.test(arrayQueue[(index + indexHead) % arrayQueue.length])) {
                break;
            }
            if (temp == 0) {
                index++;
            } else {
                index--;
            }
        }
        return (index == size ? -1 : index);
    }

    //Post: size
    public static int size() {
        return size;
    }

    //Post: size == 0
    public static boolean isEmpty() {
        return size() == 0;
    }

    //Post: indexTail = 0, indexHead = 0, size = 0
    public static void clear() {
        indexTail = indexHead = 0;
        size = 0;
    }
}
