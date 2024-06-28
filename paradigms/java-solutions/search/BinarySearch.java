package search;

public class BinarySearch {
    public static void main(String[] args) {
        //Pred: (args.length == 1 || (args.length>1 && \forall  0 < i < j < args.length args[i]>= args[j] && \forall 0 <= i < args.length args[i] is int ))
        //Post: answer = {min i: 1 < i < args.length && args[i] <= args[0]}
        //let us introduce the notation
        //array it is String[] args beginning 1 and ending args.length
        //x = String[0]
        // {Pred} (C) {Post}
        try {//Pred
            int x = Integer.parseInt(args[0]);
            int[] array = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                array[i - 1] = (Integer.parseInt(args[i]));
            }
            //Pred: (array.length == 0 || (array.length>0 && \forall  0 <= i <= j < args.length args[i]>= args[j] && \forall 0 <= i < args.length args[i] is int ))
            int answerIteraitve = binarySearchIterative(array, x);
            //Post: min i: a[i]<=x;

            //Pred: (array.length == 0 || (array.length>0 && \forall  0 <= i <= j < args.length args[i]>= args[j] && \forall 0 <= i < args.length args[i] is int ))
            // INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]) || (l < r && (x<array[l] && x>=array[r]) && array.length>0 && \forall  0 <= i <= j < args.length args[i]>= args[j]))
            int answerRecursive = binarySearchRecursive(array, -1, array.length, x);
            //Post: min i: a[i]<=x;
            System.out.println(answerRecursive);
        } catch (NumberFormatException e) {
            //!Pred
            //in the case when {pred} is not executed, then it will not be possible to get {post}
            System.out.println("Oops, it is not number");
        }
    }

    public static int binarySearchIterative(int[] array, int x) {
        //Pred: INV
        int left = -1;
        //INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
        int right = array.length;
        //INV: ((l=-1 || x<array[l]) && (r=array.size() || x>=array[r]))
        while (left + 1 < right) {
            //cond = left + 1 < right; INV2 = left+1 < right
            // INV && cond -> {INV}
            int middle = left + (right - left) / 2;
            //it is like (l+r)/2 but does not allow overflow for int
            if (x < array[(middle)]) {
                // cond = x<array[m]
                // {INV && cond} (l = m) {INV}
                left = middle;
                //INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
                //array.size() -> array.size()/2 because (right-left) = (right-(left + (right - left) / 2)) <= (right - left - right/2 + left/2) = (right/2 - left/2)
            } else {
                //!(x<array[m]) = x>=array[m]
                // {INV && !cond} (r = m) {INV}
                right = middle;
                //INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
                //array.size() -> array.size()/2 because (right-left) = ((left + (right - left) / 2)-left) <= (left + right/2 - left/2 -left) = (right/2 - left/2)
            }
        }
        //Post: INV && answer
        // why is answer, because we in Post have l and r,  where array[l]>x && array[r]<=x, and
        // this is r is a minimal because if we have r-1 it is will be l, but array[l]>x by the INV,contradiction.
        return right;
    }

    public static int binarySearchRecursive(int[] array, int left, int right, int x) {
        //Pred: INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
        if (left + 1 < right) {
            //cond = {l + 1 < r}
            //(INV && cond) -> {(l + 1 < r)}
            int middle = left + (right - left) / 2;
            // C = (m = l + (r-l)/2) it is more formally because (l+r)/2 can be overflow for int
            if (x < array[(middle)]) {
                // cond = (x<array[m])
                // INV && cond -> (l=m) because if l=m ->array[m] < x INV go to be correct
                return binarySearchRecursive(array, middle, right, x);
            } else {
                // cond = (x<array[m])
                // INV && !cond ->( x>=array[m]) if r = m array[m] >= x INV go to be correct
                return binarySearchRecursive(array, left, middle, x);
            }
            //Post: INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
        }
        //Post: (INV && !cond) -> answer
        // because if it not a minimal (We understand that the invariant is still satisfied,
        // so it is enough to prove that the index is minimal) If the index were not minimal,
        // then since l =r -1 the invariant must be satisfied, but then the invariant would break,
        // and since the invariant was preserved along the entire path, this cannot happen.
        // more formally if answer is not minimal \exist answer - 1 -> answer - 1 = l (because !cond ->l=r-1) but by the
        //INV array[l]<x, contradiction -> answer is minimal.
        return right;
    }

}
