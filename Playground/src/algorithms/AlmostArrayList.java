package algorithms;


/* En class som fungerar som en ArrayList.
 * Den är dock inte generiskt, utan använder endast
 * heltal. Detta kan lätt ändras om man vill dock; */
public class AlmostArrayList {
	
	private int size; 			// Antalet object i listan										
	private int[] list;
	
	public AlmostArrayList() {
		this.size = 0;					
		this.list = new int[10];							// Börjar med storleken 10 (du kan välja vad som helst)
	}

	/* Kör en vanlig bubble sort */
	public void sort() {
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (list[j] < list[i]) {
					int temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}
	
	/* Tar bort ett givet element från listan, om det finns, annars händer inget. 
	 * Notera att det är samma algoritm om man vill ta bort
	 * från givet index, fast då kollar du bara om i == object
	 * istället för list[i] == object						*/
	public void remove(int object) {
		for (int i = 0; i < size; i++) {
			if (list[i] == object) {						// Om vi hittar objectet som ska tas bort
				while (i < size - 1) {						// Går vi igenom alla ÅTERSTÅENDE object i listan
					list[i] = list[i + 1];					// och flyttar dem en position till vänster
					i++;
				}
				size--;										// Därefter minskar vi antalet element i listan
				return;										// Om vi har funnit talet returnar vi då det inte finns
			}												// någon poäng att kolla resten (förutsatt att vi bara
		}													// vill ta bort ett tal dock!)
	}
	
	
	/* Lägger till ett nytt objekt i listan */
	public void add(int object) {
		if (this.size == this.list.length ) {				// Om antalet object i listan har nått listans kapacitet
			growList();										// ökar vi storleken på listan
		}
		list[size] = object;								// Sätter in objektet och ökar antalet element i listan
		size++;
	}
	
	
	/* Returnerar en sträng som representerar innehållet
	 * i listan											*/
	public String toString() {
		StringBuilder listString = new StringBuilder();		 
		for (int i = 0; i < size; i++) {
			listString.append(list[i] + ", ");				// Lägger till varje objekt + ", " för snyggare utskrift
		}
		return listString.toString().substring(0, listString.length() - 2);
	}	// Då jag inte vill ha med den sista ", " tar jag bort den genom 
		//	.substring(START, END)
	
	
	/* Skapar en ny lista dubbelt så stor som den gamla */
	private void growList() {
		int[] tempList = list;								// Gör ny lista som pekar på den gamla listan
		list = new int[list.length * 2];					// OBS! tempList gör alltså INGEN kopia av ursprungslistan! 
		for (int i = 0; i < tempList.length; i++) {			// Skapar en ny lista, dubbelt så stor som den gamla
			list[i] = tempList[i];							// och för över alla objekt från den gamla till den nya
		}
	}

	
}