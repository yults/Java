import java.util.*;
import java.io.*;
public class ReverseTranspose {
    public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> array = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				int i = 0;
				Scanner scanner2 = new Scanner(scanner.nextLine());
				while(scanner2.hasNextInt()) {
					if (i < array.size()) {
						array.get(i).add(scanner2.nextInt());
					} else {
					ArrayList<Integer> newArray = new ArrayList<>();
					newArray.add(scanner2.nextInt());
					array.add(newArray);
					}
					i++;
				}
			}
			for (int i = 0; i < array.size(); i++){
				for (int j = 0; j < array.get(i).size(); j++){
					System.out.print(array.get(i).get(j));
					System.out.print(" ");
				}
				System.out.print("\n");
			}
		} catch (IOException e){
        System.err.print(e.getMessage());
      }
	}
}

