package queue;

import java.util.Objects;
import java.util.function.Predicate;

//Model: massive with FIFO, first in(queueADT.arrayQueue[queueADT.indexTail])  first out(queueADT.arrayQueue[queueADT.indexHead])
//Inv: (\forall -1 < index < size | queueADT.arrayQueue[(index+queueADT.indexHead)%queueADT.arrayQueue.length] != null)

public class ArrayQueueADT {
    private int indexTail;
    private int indexHead;
    private int size;
    private final static int basic_size = 16;
    private Object[] arrayQueue;

    public ArrayQueueADT() {
        arrayQueue = new Object[basic_size];
    }

    //Pred: (element != null) && ( ArrayQueueADT queueADT)
    //Post: arrayQueue[queueADT.indexTail] = element
    public static void enqueue(ArrayQueueADT queueADT, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queueADT, queueADT.size + 1);
        queueADT.arrayQueue[queueADT.indexTail] = element;
        queueADT.indexTail = (queueADT.indexTail + 1) % (queueADT.arrayQueue.length);
        queueADT.size++;
    }

    private static void ensureCapacity(ArrayQueueADT queueADT, int capacity) {
        if (queueADT.arrayQueue.length < capacity) {
            Object[] temp = new Object[capacity * 2];
            System.arraycopy(queueADT.arrayQueue, queueADT.indexHead, temp, 0, queueADT.arrayQueue.length - queueADT.indexHead);
            System.arraycopy(queueADT.arrayQueue, 0, temp, queueADT.arrayQueue.length - queueADT.indexHead, queueADT.indexHead);
            queueADT.indexTail = (queueADT.indexTail + queueADT.arrayQueue.length - queueADT.indexHead) % temp.length;
            queueADT.indexHead = 0;
            queueADT.arrayQueue = temp;
        }
    }

    //Pred: size>0 && ArrayQueueADT queueADT
    //Post: queueADT.arrayQueue[queueADT.indexHead]
    public static Object element(ArrayQueueADT queueADT) {
        assert queueADT.size > 0;
        return queueADT.arrayQueue[queueADT.indexHead];
    }

    //Pred: size>0 && ArrayQueueADT queueADT
    //Post: queueADT.arrayQueue[queueADT.indexHead]
    public static Object dequeue(ArrayQueueADT queueADT) {
        assert queueADT.size >= 0;
        Object elem = element(queueADT);
        queueADT.indexHead = (queueADT.indexHead + 1) % queueADT.arrayQueue.length;
        queueADT.size--;
        return elem;
    }

    //Pred: Predicate<Object> !=null && ArrayQueueADT queueADT
    //Post: (min index < queueADT.size | p.test[(index+queueADT.indexHead) % queueADT.arrayQueue.length] is true ) || (\forall queueADT.size>index>-1 p.test[(index+queueADT.indexHead) % queueADT.arrayQueue.length] is false => index = -1)
    public static int indexIf(ArrayQueueADT queueADT, Predicate<Object> p) {
        return generalIndexIf(queueADT, p, 0);
    }

    //Pred: Predicate<Object> !=null && ArrayQueueADT queueADT
    //Post: (max index >=0 | p.test[(index+queueADT.indexHead) % queueADT.arrayQueue.length] is true ) || (\forall queueADT.size>index>-1 p.test[(index+queueADT.indexHead) % queueADT.arrayQueue.length] is false => index = -1)
    public static int lastIndexIf(ArrayQueueADT queueADT, Predicate<Object> p) {
        return generalIndexIf(queueADT, p, queueADT.size - 1);
    }

    private static int generalIndexIf(ArrayQueueADT queueADT, Predicate<Object> p, int temp) {
        Objects.requireNonNull(p);
        int index = temp;
        while (queueADT.size - 1 >= index && index >= 0) {
            if (p.test(queueADT.arrayQueue[(index + queueADT.indexHead) % queueADT.arrayQueue.length])) {
                break;
            }
            if (temp == 0) {
                index++;
            } else {
                index--;
            }
        }
        return (index == queueADT.size ? -1 : index);
    }

    //Pred: ArrayQueueADT queueADT
    //Post: queueADT.size>=0
    public static int size(ArrayQueueADT queueADT) {
        return queueADT.size;
    }

    //Pred: ArrayQueueADT queueADT
    //Post: queueADT.size() == 0
    public static boolean isEmpty(ArrayQueueADT queueADT) {
        return size(queueADT) == 0;
    }

    //Pred: ArrayQueueADT queueADT
    //Post: queueADT.indexHead = 0
    //queueADT.indexTail = 0
    //queueADT.size = 0
    public static void clear(ArrayQueueADT queueADT) {
        queueADT.indexTail = queueADT.indexHead = 0;
        queueADT.size = 0;
    }
}
