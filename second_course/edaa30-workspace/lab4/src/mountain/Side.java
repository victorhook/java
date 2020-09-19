package mountain;

public class Side {

    private Point start, end;

    public Side(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Side other = (Side) obj;
            return (other.start.equals(this.start) && other.end.equals(this.end)
                    || other.end.equals(this.start) && other.start.equals(this.end));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode();
    }

}
