package queue;

import java.util.function.Predicate;

public class ArrayQueueTests {
    public final static int numberOfTests = 16;
    private static int count;
    private static int counter;

    private static void counter(int t) {
        if (count != t) {
            System.err.println("Your counter must be " + t + ", not the " + count);
            throw new AssertionError();
        } else {
            counter += 1;
            count = 0;
        }
    }

    private static void clear() {
        count = 0;
        counter = 0;
    }

    //Test ArrayQueue
    private static void generatedFilledArray(ArrayQueue queue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfTests; i++) {
            sb.append(i);
            queue.enqueue(sb.toString());
            count += 1;
        }
    }

    public static void check(ArrayQueue queue, int t) {
        counter(t);
        generatedFilledArray(queue);
    }

    public static void check(ArrayQueue queue) {
        check(queue, 0);
    }

    public static boolean testsForQueue(ArrayQueue queue) {
        clear();
        check(queue);

        System.out.println("size " + queue.size());
        System.out.println("element " + queue.element());

        for (int i = 0; i < numberOfTests; i++) {
            if (!queue.dequeue().equals(i)) {
                count -= 1;
            }
        }

        check(queue);

        for (int i = 0; i < numberOfTests; i++) {
            if (queue.element().equals(Integer.toString(i))) {
                count -= 1;
            }
            queue.dequeue();
        }

        check(queue, 15);

        for (int i = 0; i < numberOfTests; i++) {
            if (queue.size() == 16 - i) {
                count -= 1;
            }
            queue.dequeue();
        }
        check(queue);

        for (int i = 0; i < numberOfTests; i++) {
            if (queue.size() == 16 - i) {
                count -= 1;
            }
            queue.dequeue();
        }
        check(queue);

        Predicate<Object> p = Object -> Object.equals("0123");
        for (int i = 0; i < numberOfTests; i++) {
            if (queue.indexIf(p) != -1) {
                count -= 1;
            }
            queue.dequeue();
        }
        check(queue, 16 - 4);

        for (int i = 0; i < numberOfTests; i++) {
            if (queue.lastIndexIf(p) != -1) {
                count -= 1;
            }
            queue.dequeue();
        }
        check(queue, 16 - 4);

        return true;
    }

    //Test ArrayQueueADT
    private static void generatedFilledArray(ArrayQueueADT queue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfTests; i++) {
            sb.append(i);
            ArrayQueueADT.enqueue(queue, sb.toString());
            count += 1;
        }
    }

    public static void check(ArrayQueueADT queue, int t) {
        counter(t);
        generatedFilledArray(queue);
    }

    public static void check(ArrayQueueADT queue) {
        check(queue, 0);
    }

    public static boolean testsForQueue(ArrayQueueADT queue) {
        clear();
        check(queue);
        System.out.println("size " + ArrayQueueADT.size(queue));
        System.out.println("element " + ArrayQueueADT.element(queue));

        for (int i = 0; i < numberOfTests; i++) {
            if (!ArrayQueueADT.dequeue(queue).equals(i)) {
                count -= 1;
            }
        }

        check(queue);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueADT.element(queue).equals(Integer.toString(i))) {
                count -= 1;
            }
            ArrayQueueADT.dequeue(queue);
        }

        check(queue, 15);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueADT.size(queue) == 16 - i) {
                count -= 1;
            }
            ArrayQueueADT.dequeue(queue);
        }
        check(queue);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueADT.size(queue) == 16 - i) {
                count -= 1;
            }
            ArrayQueueADT.dequeue(queue);
        }
        check(queue);

        Predicate<Object> p = Object -> Object.equals("0123");
        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueADT.indexIf(queue, p) != -1) {
                count -= 1;
            }
            ArrayQueueADT.dequeue(queue);
        }
        check(queue, 16 - 4);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueADT.lastIndexIf(queue, p) != -1) {
                count -= 1;
            }
            ArrayQueueADT.dequeue(queue);
        }
        check(queue, 16 - 4);

        return true;
    }

    //Test ArrayQueueModule
    private static void generatedFilledArray() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfTests; i++) {
            sb.append(i);
            ArrayQueueModule.enqueue(sb.toString());
            count += 1;
        }
    }

    public static void check(int t) {
        counter(t);
        generatedFilledArray();
    }

    public static void check() {
        check(0);
    }

    public static boolean testsForQueue(ArrayQueueModule queue) {
        clear();
        check();
        System.out.println("size " + ArrayQueueModule.size());
        System.out.println("element " + ArrayQueueModule.element());

        for (int i = 0; i < numberOfTests; i++) {
            if (!ArrayQueueModule.dequeue().equals(i)) {
                count -= 1;
            }
        }

        check();

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueModule.element().equals(Integer.toString(i))) {
                count -= 1;
            }
            ArrayQueueModule.dequeue();
        }

        check(15);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueModule.size() == 16 - i) {
                count -= 1;
            }
            ArrayQueueModule.dequeue();
        }
        check();

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueModule.size() == 16 - i) {
                count -= 1;
            }
            ArrayQueueModule.dequeue();
        }
        check();

        Predicate<Object> p = Object -> Object.equals("0123");
        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueModule.indexIf(p) != -1) {
                count -= 1;
            }
            ArrayQueueModule.dequeue();
        }
        check(16 - 4);

        for (int i = 0; i < numberOfTests; i++) {
            if (ArrayQueueModule.lastIndexIf(p) != -1) {
                count -= 1;
            }
            ArrayQueueModule.dequeue();
        }
        check(16 - 4);

        return true;
    }

    public static void main(String[] args) {
        ArrayQueue src = new ArrayQueue();
        if (testsForQueue(src)) {
            System.out.println("ArrayQueue: You successfully passed " + counter + " tests");
        }
        ArrayQueueADT src1 = new ArrayQueueADT();
        if (testsForQueue(src1)) {
            System.out.println("ArrayQueueADT: You successfully passed " + counter + " tests");
        }
        ArrayQueueModule src2 = new ArrayQueueModule();
        if (testsForQueue(src2)) {
            System.out.println("ArrayQueueModule: You successfully passed " + counter + " tests");
        }
    }

}
