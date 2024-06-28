package search;

public class BinarySearchClosestA {
    public static void main(String[] args) {
        //Pred: (args.length != 0 || (args.length>1 && \forall  0 < i < j < args.length args[i]>= args[j] && \forall 0 <= i < args.length args[i] is int ))
        //Post: answer = {min args[i]: \forall 1 < j < args.length && abs(args[i]-args[0])<abs(args[j]-args[0])}
        //let us introduce the notation
        //array it is String[] args beginning 1 and ending args.length
        //x = String[0]
        // {Pred} (C) {Post}
        try {
            int x = Integer.parseInt(args[0]);
            int[] array = new int[args.length - 1];
            //0 is neutral element by the operation + mod2
            int isEven = 0;
            int answer;
            for (int i = 1; i < args.length; i++) {
                array[i - 1] = (Integer.parseInt(args[i]));
//                (array[i - 1] & 1) = array[i-1]%2
                isEven += (array[i - 1] & 1);
            }
            //P: {x is int && ((array.length>0 && array[i] <= array[j] \forall 0<=i<=j<array.length) || (array.length==0))}
            //Pred {isEven && P} (binarySearchIterativeClosestA) {answer}
            if ((isEven) == 1) {
                //Pred: INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
                answer = binarySearchIterativeClosestA(array, x);
                //Post: answer = {min args[i]: \forall 1 < j < args.length && abs(args[i]-args[0])<abs(args[j]-args[0])}
            } else {
                //Pred: INV: ((l=-1 || x>array[l]) && (r=n || x<=array[r]) || (l < r && (x>array[l] && x<=array[r]) && array.length>0 && \forall  0 <= i <= j < args.length args[i] <= args[j]))
                answer = binarySearchRecursiveClosestA(array, -1, array.length, x);
                //Post: answer = {min args[i]: \forall 1 < j < args.length && abs(args[i]-args[0])<abs(args[j]-args[0])}
            }
            //Post: min args[i]: \forall 1 < j < args.length && abs(args[i]-args[0])<abs(args[j]-args[0])
            System.out.println(answer);
        } catch (NumberFormatException e) {
            //!Pred
            //in the case when {Pred} is not executed, then it will not be possible to get right {post}
            System.out.println("Oops, it is not number");
        }
    }

    public static int binarySearchIterativeClosestA(int[] array, int x) {
        //Pred: INV
        int left = -1;
        //INV: ((l=-1 || array[l]<x) && (r=array.size() || x<=array[r]))
        int right = array.length;
        //INV: ((l=-1 || x<array[l]) && (r=array.size() || x<=array[r]))
        while (left + 1 < right) {
            //cond = left + 1 < right; INV2 = left+1 < right
            // INV && cond -> {INV}
            int middle = left + (right - left) / 2;
            //it is like (l+r)/2 but does not allow overflow for int
            // cond = x>array[m]
            if (array[(middle)] > x) {
                // cond = x<array[m]
                // {INV && cond} (r = m) {INV}
                right = middle;
                //INV: ((l=-1 || x>array[l]) && (r=n || x<=array[r]))
                //array.size() -> array.size()/2 because (right-left) = ((left + (right - left) / 2)-left) <= (left + right/2 - left/2 -left) = (right/2 - left/2)
            } else {
                //!(x>array[m]) = x<=array[m]
                // {INV && !cond} (l = m) {INV}
                left = middle;
                //INV: ((l=-1 || x>array[l]) && (r=n || x<=array[r]))
                //array.size() -> array.size()/2 because (right-left) = (right-(left + (right - left) / 2)) <= (right - left - right/2 + left/2) = (right/2 - left/2)
            }
        }
        return isItNormal(array, left, right, x);
        //Post: INV && answer
        // why is answer, because we in Post have l and r,  where array[l]<x && array[r]>=x, and
        // this is r is a minimal because if we have r-1 it is will be l, but array[l]<x by the INV,contradiction.
    }

    public static int binarySearchRecursiveClosestA(int[] array, int left, int right, int x) {
        //Pred: INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r])) && l<r
        if (left + 1 < right) {
            //cond = {l + 1 < r}
            //(INV && cond) -> {(l + 1 < r)}
            int middle = left + (right - left) / 2;
            // C = (m = l + (r-l)/2) it is more formally because (l+r)/2 can be overflow for int
            if (x < array[(middle)]) {
                // cond = (x<array[m])
                // INV && cond -> (l=m) because if l=m ->array[m] < x INV go to be correct
                return binarySearchRecursiveClosestA(array, left, middle, x);
            } else {
                // cond = (x<array[m])
                // INV && !cond ->( x>=array[m]) if r = m array[m] >= x INV go to be correct
                return binarySearchRecursiveClosestA(array, middle, right, x);
            }
            //Post: INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
        }
        //Post: !cond && INV: ((l=-1 || x<array[l]) && (r=n || x>=array[r]))
        return isItNormal(array, left, right, x);
        //Post: (INV && !cond) -> answer
        // because if it not a minimal (We understand that the invariant is still satisfied,
        // so it is enough to prove that the index is minimal) If the index were not minimal,
        // then since l =r -1 the invariant must be satisfied, but then the invariant would break,
        // and since the invariant was preserved along the entire path, this cannot happen.
        // more formally if answer is not minimal \exist answer - 1 -> answer - 1 = l (because !cond ->l=r-1) but by the
        //INV array[l]<x, contradiction -> answer is minimal.
    }

    private static int isItNormal(int[] array, int left, int right, int x) {
        //Pred !cond(l=r-1) && INV
        //by the INV we know what left maybe equal left=-1 or right =1 in this situation we can't figure out with array
        //because doesn't exist index -1 or n in array and this situation we can chose another one because by the !cond
        //we know what we have doesn't empty array and equation l=r-1 and if we have l=-1 ->(by the !cond) r=0 (this index is exists)
        // or we have r=n (what n!=0 because array not empty) -> l >= 0, in this situation we chose tne another one argument as an answer
        // or we chose the minimal difference from ours x (Math.abs(array[left] - x) > Math.abs(array[right] - x)))
        // therefore, we will choose the minimum such number, purely due to the inequality
        if (left == -1 || (right != array.length && Math.abs(array[left] - x) > Math.abs(array[right] - x))) {
            return array[right];
        } else {
            //!cond(left == -1 || (right != array.length && Math.abs(array[left] - x) > Math.abs(array[right] - x))) =
            //= left !=-1 && right == array.length || Math.abs(array[left] - x) <= Math.abs(array[right] - x))
            // this !cond will be answer because we have doesn't have IndexOutOfBoundOfRange cause left!=-1 or  it will
            //min Math.abs(array[left] - x) <= Math.abs(array[right] - x) this number;
            return array[left];
        }
    }
}
