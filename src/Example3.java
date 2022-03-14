import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example3 {

    private static List<Integer> quickSort(List<Integer> array) {
        if (array.size() < 2) {
            return array;
        } else {
            int pivot = array.get(0);

            List<Integer> lessList = new ArrayList<>();
            List<Integer> greaterList = new ArrayList<>();

            array.stream().skip(1).forEach(value -> {
                if (pivot <= value) {
                    lessList.add(value);
                } else {
                    greaterList.add(value);
                }
            });

            List<Integer> finalList = quickSort(lessList);
            finalList.add(pivot);
            finalList.addAll(quickSort(greaterList));
            return finalList;
        }
    }

    public static void main(String[] args) {
        System.out.println(quickSort(Arrays.asList(-1, 10, -20, 333, 11, 5, 66, 66, 9)));
    }
}
