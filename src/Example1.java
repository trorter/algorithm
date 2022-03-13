public class Example1 {

    private static String binarySreach(int[] array, int item) {
        int low = 0;
        int hight = array.length - 1;

        while (low <= hight) {
            int mid = (low + hight) /2;
            int guess = array[mid];

            if (guess == item) {
                return Integer.toString(guess);
            } else if (item > guess) {
                low = mid + 1;
            } else {
                hight = mid - 1;
            }
        }

        return "not found";
    }

    public static void main(String[] args) {
        int[] items = {1, 2, 3, 4, 5, 6, 7, 8, 10};

        System.out.println(binarySreach(items, 6));
        System.out.println(binarySreach(items, -6));
    }
}