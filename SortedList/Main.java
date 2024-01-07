import SortedList.SortedList;
import java.util.Optional;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Rolling a d20 1000000 times");

        SortedList<Integer> rolls = new SortedList<Integer>();
        Random random = new Random();

        for (int i = 0; i < 1000000; i++) {
            rolls.insert(random.nextInt(20) + 1);
        }

        for (int i = 1; i <= 20; i++) {
            Optional<LinkedListNode<Integer>> node = rolls.findRef(i);
            if(node.isPresent()) {
                System.out.println("Rolled " + node.get().value + " " + node.get().count + " times");
            } else {
                System.out.println("Rolled " + i + " 0 times");
            }
        }
    }
}
