import java.util.Random;

public class NumberGenerator {
	private static Random rand = new Random();  // slumptalsgenerator
	private int secretNbr;						// det hemliga talet
	private int min;							// minsta v�rde det hemliga talet kan ha
	private int max;							// st�rsta v�rde det hamliga talet kan ha

	/** Skapar ett objekt som kan generera med ett slumpm�ssigt valt hemligt 
		    heltal i intervallet [min, max]. */
	public NumberGenerator(int min, int max) {
		
	}

	/** Drar ett nytt hemligt tal. */
	public void drawNbr() {

	}

	/** Tar reda p� minsta m�jliga v�rde det hemliga talet kan ha. */
	public int getMin() {
		return 0;
	}

	/** Tar reda p� st�rsta m�jliga v�rde det hemliga talet kan ha. */
	public int getMax() {
		return 0;
	}

	/** Tar reda p� om det hemliga talet �r lika med guess. */
	public boolean isEqual(int guess) {
		return false;
	}

	/** Tar reda p� om det hemliga talet �r st�rre �n guess. */
	public boolean isBiggerThan(int guess) {
		return false;
	}
}

