package new_features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeInferenceTest {
    public static void main(String[] args) {
        var arr = new ArrayList<Integer>(List.of(1, 2, 3));
        var list = Arrays.asList(1, 3, 5);

        System.out.println(arr);
        System.out.println(list);
    }
}
