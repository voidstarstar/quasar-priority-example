package example.example;

public class MutableComparable<C extends Comparable<C>> implements Comparable<MutableComparable<C>> {

	private C c;

	public MutableComparable(C c) {
		this.c = c;
	}

	public void setComparable(C c) {
		this.c = c;
	}

	public C getComparable() {
		return this.c;
	}

	@Override
	public int compareTo(MutableComparable<C> o) {
		return this.c.compareTo(o.c);
	}

}
