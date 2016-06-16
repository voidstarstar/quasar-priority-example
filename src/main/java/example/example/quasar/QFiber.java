package example.example.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableCallable;

public class QFiber<V> extends Fiber<V> implements Comparable<QFiber<V>> {

	public Comparable comparable;

	public QFiber(FiberScheduler scheduler, SuspendableCallable<V> target) {
		super(scheduler, target);
	}

	@Override
	public int compareTo(QFiber<V> arg0) {
		return this.comparable.compareTo(arg0.comparable);
	}

	public Strand setComparable(Comparable comparable) {
		this.comparable = comparable;
		return this;
	}

}
