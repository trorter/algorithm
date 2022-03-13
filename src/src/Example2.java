import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example2 {

    private static int findSmalles(List<Integer> array) {
        int smallest_item = array.get(0);
        int smallest_index = 0;

        for (int i = 0; i < array.size(); i++) {
            if (smallest_item > array.get(i)) {
                smallest_index = i;
                smallest_item = array.get(i);
            }
        }
        return smallest_index;
    }

    private static List<Integer> selectedSort(List<Integer> array) {
        List<Integer> newArray = new ArrayList<>(array.size());

        int count = array.size();
        for (int i = 0; i < count; i++) {
            int smallestIndex = findSmalles(array);
            newArray.add(array.get(smallestIndex));

            array.remove(smallestIndex);
        }

        return newArray;
    }

    public static void main(String[] args) {
        List<Integer> test1 = new ArrayList<>(Arrays.asList(1, 20, 1, 10, 77, 50, -1));


        System.out.println(selectedSort(test1));
    }
}
