package fractal;
public abstract class Fractal {
	protected int order;
	protected int dev;

	/** Construts a fractal object.
	 * @param order the order of the fractal 
	 */
	public Fractal() {
		order = 0;
	}

	/**
	 * Returns the title.
	 * @return the title
	 */
	public abstract String getTitle();

	/** Sets the order of the fractal to order.
	 * @param order the new order of the fractal
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/** 
	 * Returns the order of the fractal. 
	 * @return the order of the fractal*/
	public int getOrder(){
		return order;
	}

	/**
	 * Returns a string representation of this fractal
	 * @return a string representation of this fractal
	 */
	public String toString() {
		return getTitle();
	}

	/**
	 * Sets the dev value, used for increasing the y-value
	 * @param dev
	 */
	public void setDev(int dev) { this.dev = dev; }

	/**
	 * Gets the dev value, used for increasing the y-value
	 * @return dev
	 */
	public int getDev() { return dev; }

	/** Draws the fractal.  
	 * @param g the turtle graphic object
	 */
	public abstract void draw(TurtleGraphics g);

}
