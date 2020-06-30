import java.util.Scanner;
import java.util.ArrayList;
public class ReverseEven {
    public static void main(String[] args) {
		List array = new ArrayList<ArrayList<Integer>>();
		Scanner scanner = new Scanner(System.in);
		int k = 0;
		while (scanner.hasNextLine()) {
			array.add(new ArrayList<>());
			Scanner scanner2 = new Scanner(scanner.nextLine());
			while(scanner2.hasNextInt()) {
				array.get(k).add(scanner2.nextInt());
			}
			k++;
		}
		for (int i = array.size() - 1; i >= 0; i--) {
			for (int j = array.get(i).size() - 1; j >= 0; j--) {
				if (array.get(i).get(j) % 2 == 0) {
					System.out.print(array.get(i).get(j) + " ");
				}
			}
		System.out.println();
		}
	}
}


