public class LifeBoard {

	/** Skapar en spelplan med rows rader och cols kolonner. Spelplanen �r fr�n
	    b�rjan tom, dvs alla rutorna �r tomma och generationsnumret �r 1. */	
	public LifeBoard(int rows, int cols) {
	}

	/** Ger true om det finns en individ i rutan med index row, col, false annars. 
	    Om index row, col �r utanf�r spelplanen returneras false */
	public boolean get(int row, int col) {
		return false;
	}

	/** Lagrar v�rdet val i rutan med index row, col */
	public void put(int row, int col, boolean val) {
	}

	/** Tar reda p� antalet rader */
	public int getRows() {
		return 0;
	}

 	/** Tar reda p� antalet kolonner */
	public int getCols() {
		return 0;
	}

	/** Tar reda p� aktuellt generationsnummer */
	public int getGeneration() {
		return 0;
	}

	/** �kar generationsnumret med ett */
	public void increaseGeneration() {
	}
}