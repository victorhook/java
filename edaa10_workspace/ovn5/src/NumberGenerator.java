import java.util.Random;

public class NumberGenerator {
	private static Random rand = new Random();  // slumptalsgenerator
	private int secretNbr;						// det hemliga talet
	private int min;							// minsta värde det hemliga talet kan ha
	private int max;							// största värde det hamliga talet kan ha

	/** Skapar ett objekt som kan generera med ett slumpmässigt valt hemligt 
		    heltal i intervallet [min, max]. */
	public NumberGenerator(int min, int max) {
		
	}

	/** Drar ett nytt hemligt tal. */
	public void drawNbr() {

	}

	/** Tar reda på minsta möjliga värde det hemliga talet kan ha. */
	public int getMin() {
		return 0;
	}

	/** Tar reda på största möjliga värde det hemliga talet kan ha. */
	public int getMax() {
		return 0;
	}

	/** Tar reda på om det hemliga talet är lika med guess. */
	public boolean isEqual(int guess) {
		return false;
	}

	/** Tar reda på om det hemliga talet är större än guess. */
	public boolean isBiggerThan(int guess) {
		return false;
	}
}

