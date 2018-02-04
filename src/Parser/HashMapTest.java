package Parser;


import java.util.HashMap;

public class HashMapTest {

    public static void main(String[] args) {

        int q3 = 3, q2 = 2;
        HashMap<String, String> hm = new HashMap<>();

        hm.put("1", "a");
        hm.put(Integer.toString(q3 - q2), "b");
    }
}
