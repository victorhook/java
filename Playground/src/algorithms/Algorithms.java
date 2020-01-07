package algorithms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/* 
 * En samling användbara algoritmer och tips som är essentiella att känna till
 * om du programmerar!
 */
public class Algorithms {

	
	/* Blandar talen från en lista i slumpmässig ordning */
	public static void shuffle(int[] list) {
		Random randomNumberGenerator = new Random();						// Skapar ett object från Random-klassen, för att kunna slumpa tal
		
		for (int index = list.length - 1; index > 0; index--) {				// Vi börjar på sista positionen i listan
			int randomNumber = randomNumberGenerator.nextInt(index + 1);	// Det nya slumptalet är mellan [0 - vår position] 	
			int temp = list[index];											// (nextInt är EXKLUDERANDE i högre talet, dvs om vi använder	
			list[index] = list[randomNumber];								// nextInt(0,2) blir talet mellan 0-1, INTE, 2, därav index + 1
			list[randomNumber] = temp;			// Vanlig platsbyte mellan talet vi är på och slumptalet
		}
	}
	
	/* Lägger till ett nytt heltal i en ArrayList, som ska hållad sorterad.
	 * Med denna algoritm kan listan hållas sorted från helt ny. 		*/
	public static void addToSortedList(int item, ArrayList<Integer> list) {
		int index = 0;														// Startposition = 0
		while (index < list.size() && list.get(index) < item) {				// Så länge startposition < listans storlek OCH
			index++;														// talet vi är på är mindre än det vi ska sätta in,
		}																	// så ökar vi positionen med 1
		list.add(index, item);		// Lägger till det nya talet med på rätt plats i listan										
	}
	/* ex:    Vi vill lägga till 4 till
	 *			[ 0, 3, 6, 9, 10 ]
	 *	index = 0	 
	 *	0 < 4 => index++
	 * 
	 *	index = 1
	 *  3 < 4 => index++
	 *  
	 *  index = 2
	 *  6 < 4 => BREAK
	 *  add(2, 4)		Lägger till 4 på index 2 i listan. 
	 *  
	 *  (notera att i en arraylist flyttas övriga element automatiskt när vi lägger till ett nytt)	
	 */
	
	
	/* Kollar om en given sträng existerar i listen och returnerar true
	 * om den gör det, annars false. Notera for-each loopen som är betydligt snyggare än en vanlig */
	public static boolean itemExists(String comparingItem, String[] list) {
		for (String item : list) {
			if (comparingItem.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	/* Binärsökning. OBS! listan MÅSTE vara sorterad för det ska funka */
	public static int binarySearch(int[] list, int number) {
		
		int low = 0, high = list.length - 1, mid;	// Ursprungsvärden
		
		while (low <= high) {						// Om low == high är "gapet" vi kollar 0, alltså är vi då klara,
			mid = (low + high) / 2;					// men tills dess letar vi
			
			if (list[mid] == number) {				// Om det är samma värde, har vi hittat talet!
				return mid;
			}
			else if (list[mid] > number) {			// Om talet vi jämför med är större, måste vi sänka HIGH-ribban
				high = mid - 1;
			}
			else {
				low = mid + 1;						// Om talet vi jämför med är mindre, måste vi sänka LOW-ribban
			}
		}
		return low;									// Om talet inte finns, returneras den position där talet passar in
	}

	/* Binärsökning är supereffektiv sökning för att finna något
	 * i en lista som redan är sorterad. Låt säga att vi vill finna
	 * positionen på värdet 2 i följande vektor:
	 * 				[ 0, 1, 2, 5, 6, 7, 10 ]		
	 *	low = 0
	 *	high = 6
	 *  mid = (low + high) / 2 = 3
	 * 				[ 0, 1, 2, 5, 6, 7, 10 ]
	 * 						  MID
	 *  5 > 2 -> sänk HIGH 
	 *  high = mid - 1 = 2
	 *  low = 0
	 *  mid = (low + high) / 2 = 1
	 *  			[ 0, 1, 2, 5, 6, 7, 10 ]
	 *  				MID
	 *  1 < 2 -> höj LOW
	 *  low = mid + 1 = 2
	 *  high = 2
	 *  
	 *  			[ 0, 1, 2, 5, 6, 7, 10 ]
	 *  				   MID
	 *  Vi funnit mid!
	 */
	
	
	/* Exakt som bubblesort fast med en vektor. Skillnaden är endast anropen
	 * .size() samt .get() 												*/ 
	public static void bubbleSortArrayList(ArrayList<Integer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(j) < list.get(i)) {
					int temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
					// Alternativt, allt på en rad, fast betydligt mindre läsbart:
					// list.set(i, list.set(j, list.get(i)));
				}
			}
		}
	}
	
	/* Vanlig bubble sort för en lista en heltal (integers) 
	 * Tyvärr är variabelnamne USLA (i, j...?!) men risken är att
	 * om man väljer längre (bättre namn) blir koden mer kladdig då det blir så långt */
	public static void bubbleSort(int[] list) {
		for (int i = 0; i < list.length - 1; i++) {				// Går igenom varje tal i vektorn 1 gång
			for (int j = i + 1; j < list.length; j++) {			// Jämför återstående tal av vektorn med talet ovanför
				if (list[j] < list[i]) {						// Om det tal vi jämför är mindre bytar vi plats på dem. 
					int temp = list[i];							// ex:   	[ 0, 3, 2, 7, 9 ]
					list[i] = list[j];							//     list[i] = 3
					list[j] = temp;								//     list[j] = 2
				}												//    	    [ 0, 2, 3, 7, 9 ]														
			}
		}	
	}
	
	/* Vanlig bubble sort för en lista en med strängar */
	public static void bubbleSort(String[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			for (int j = i + 1; j < list.length; j++) {
				if (list[j].compareTo(list[i]) < 0) {
					String temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}
	
	
	 /*
	  * 	Random bra tekniker / metoder att känna till! 
	  */
	
	/* Skapar en matris med givna rader och kolumner. 
	 * och fyller dem med ett unikt tal. Min poäng här är att
	 * försöka tydliggöra iterationerna som görs för respektive
	 * rad samt kolumn. Att namnge variablerna är superbra för tydlighet */
	 public static void makeMatrice(int rows, int columns) {
		 int[][] matrice = new int[rows][columns];
		 int uniqueNumber = 0;
		 
		 for (int row = 0; row < rows; row++) {
			 for (int column = 0; column < columns; column++) {
				 matrice[row][column] = uniqueNumber;
				 uniqueNumber++;
			 }
		 }
	 }
	 
	 /*						-- Static --
	  * Nyckelordet "static" betyder att metoden/attributen inte 
	  * är bundet till ett individuellt objekt utan till KLASSEN i sig
	  * Detta kan vara användbart om man har någon attribute i en klass,
	  * exempelvis en slumptalsgenerator, som man vill att alla objekt från den klassen
	  * skall dela på, istället för att skapa en egen version av den. 					*/
	  
}
