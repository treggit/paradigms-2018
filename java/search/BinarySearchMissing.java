package search;

public class BinarySearchMissing {

    // Pre: a[i + 1] >= a[i] for all array elements
    // Post: a[i] == a[i]' && a.size == a.size'

    // Result: 1) a.size == 0 ==> result == -1
    //      2) a.size > 0 && a[a.size - 1] > value ==> result == -a.size - 1
    //      3) a.size > 0 && a[a.size - 1] <= value ==> 1) result >= 0: a[result] == value && (result == 0 || a[result - 1] > value)
    //                                                  2) result < 0: a[-result - 1] < value && (-result - 1 == 0 || a[-result - 2] > value)
    static int binarySearchIterative(int value, int[] a) {
        // Pre
        int l = -1, r = a.length;
        // Inv: 1) r == a.size || (r < a.size && a[r] <= value)
        //      2) l == -1 || (l >= 0 && a[l] > value)
        //      3) r - l >= 1
        while (r - l > 1) {
            // Pre && Inv && r - l > 1
            int mid = (l + r) / 2;
            // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r
            if (a[mid] > value) {
                // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r && a[mid] > value

                l = mid;

                // r' == r ==> r' == a.size || (r' < a.size && a[r'] <= value)
                // Pre && a[l' == mid] > value && l < mid < r ===> l' >= 0 && l' < a.size && a[l'] > value
                // r - l > 1 && l < mid < r ===> r' - l' >= 1

                // Pre && Inv ok
            } else {
                // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r && a[mid] <= value

                r = mid;

                // l' == l ==> l' == -1 || (l' >= 0 && a[l'] > value)
                // Pre && a[r' == mid] <= value && l < mid < r ===> r' < a.size && a[r'] <= value
                // r - l > 1 && l < mid < r ===> r' - l' >= 1

                // Pre && Inv ok
            }

            // l < mid < r && (l' == mid || r' == mid) ===> r - l > r' - l'
        }
        // Inv && !(r - l > 1) ===> r - l == 1
        return (r < a.length && a[r] == value) ? r : -r - 1;

        // 1) a.size == 0 ===> (while wasn't visited) ===> result = -1
        // 2) a.size > 0 && a[a.size - 1] > value ===> (else block never visited, r never changed) ===> result = -a.size - 1
        // 3) a.size() > 0 && a[a.size - 1] <= value:
        //      *) a[r] == value && (l == -1 || a[l] > value) ===> result == r
        //      *) a[r] != value && (l == -1 || a[l] > value) ===> missing value in a ===> result = -r-1
    }

    // Pre: a[i + 1] >= a[i] for all array elements
    // Post: a[i] == a[i]' && a.size == a.size'

    // Inv: 1) r == a.size || (r < a.size && a[r] <= value)
    //      2) l == -1 || (l >= 0 && a[l] > value)
    //      3) r - l >= 1

    // Result: 1) a.size == 0 ==> result == -1
    //      2) a.size > 0 && a[a.size - 1] > value ==> result == -a.size - 1
    //      3) a.size > 0 && a[a.size - 1] <= value ==> 1) result >= 0: a[result] == value && (result == 0 || a[result - 1] > value)
    //                                                  2) result < 0: a[-result - 1] < value && (-result - 1 == 0 || a[-result - 2] > value)
    static int binarySearchRecursive(int l, int r, int value, int[] a) {
        if (r - l == 1) {
            // Inv && !(r - l > 1) ===> r - l == 1
            return (r < a.length && a[r] == value) ? r : -r - 1;
            // Result: 1) a.size == 0 ==> result == -1
            //      2) a.size > 0 && a[a.size - 1] > value ==> result == -a.size - 1
            //      3) a.size > 0 && a[a.size - 1] <= value ==> 1) result >= 0: a[result] == value && (result == 0 || a[result - 1] > value)
            //                                                  2) result < 0: a[-result - 1] < value && (-result - 1 == 0 || a[-result - 2] > value)
        }

        // Pre && Inv && r - l > 1
        int mid = (l + r) / 2;
        // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r

        if (a[mid] > value) {
            // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r && a[mid] <= value

            return binarySearchRecursive(mid, r, value, a);
            // l' == l ==> l' == -1 || (l' >= 0 && a[l'] > value)
            // Pre && a[r' == mid] <= value && l < mid < r ===> r' < a.size && a[r'] <= value
            // r - l > 1 && l < mid < r ===> r' - l' >= 1

            // Pre && Inv ok
        } else {
            // Pre && Inv && r - l > 1 && mid == (l + r) / 2 && l < mid < r && a[mid] <= value

            return binarySearchRecursive(l, mid, value, a);
            // l' == l ==> l' == -1 || (l' >= 0 && a[l'] > value)
            // Pre && a[r' == mid] <= value && l < mid < r ===> r' < a.size && a[r'] <= value
            // r - l > 1 && l < mid < r ===> r' - l' >= 1

            // Pre && Inv ok
        }
        // l < mid < r && (l' == mid || r' == mid) ===> r - l > r' - l'
    }

    static int binarySearchRecursive(int value, int[] a) {
        return binarySearchRecursive(-1, a.length, value, a);
    }

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.err.println("Arguments expected");
        }
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        int iterRes = binarySearchIterative(x, a);
        int recRes = binarySearchRecursive(x, a);
        if (iterRes != recRes) {
            System.out.println("Results of iterative and recursive binary searches are not equal, something went wrong");
        } else {
            System.out.println(iterRes);
        }
    }
}