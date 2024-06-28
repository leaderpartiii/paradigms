package queue;

//Model: structure with FIFO, first in(arrayQueue[indexTail] or Node tail.item)  first out(arrayQueue[indexHead] or Node head.item)
// immutableArray(indexHead) = \forall i = indexTail, ... i = indexHead-1 arrayQueue[i]==arrayQueue'[i]
// immutableNode(n) = while (tail.next!=null) {tail.item == tail'.item, tail = tail.next }
public interface Queue {
    //    enqueue – добавить элемент в очередь;
    //Pred: elem!=null
    //Post: (immutable(indexHead) arrayQueue[indexHead] = element,  (indexHead + 1)%arrayQueue.length) ||
    //immutableNode(n) tail.next = (item, null), tail = (item, null);
    void enqueue(Object elem);

    //    element – первый элемент в очереди;
    //Pred: size>0
    //Post: head.item || arrayQueue[indexHead]
    Object element();

    //    dequeue – удалить и вернуть первый элемент в очереди;
    //Pred: size>0
    //Post: head.item  || arrayQueue[indexHead]
    Object dequeue();

    //        size – текущий размер очереди;
    //Post: size>=0
    int size();

    //    isEmpty – является ли очередь пустой;
    //Post:size==0
    boolean isEmpty();

    //    clear – удалить все элементы из очереди.
    //Post: size = 0 && (indexTail = 0 indexHead = 0 || tail = head = null)
    void clear();

    //Post: (((\forall elements in newQueue  with index i) != (\forall elements in newQueue with index i!=j)) &&
    // (
    // (\forall element in queue with index i) == (\forall elements in queue with index i!=j) => (element in newQueue with index min(i, j)) &&
    // (\forall element in queue with index i) != (\forall elements in queue with index i!=j) => (element in newQueue with index i)
    // ))
    void distinct();
}
