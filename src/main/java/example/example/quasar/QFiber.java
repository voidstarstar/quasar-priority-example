package example.example.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberScheduler;
import co.paralleluniverse.strands.SuspendableCallable;

public class QFiber<V> extends Fiber<V> implements Comparable<QFiber<?>> {

	public Comparable comparable;

	public QFiber(FiberScheduler scheduler, SuspendableCallable<V> target, Comparable comparable) {
		super(scheduler, target);
		this.comparable = comparable;
	}

	@Override
	public int compareTo(QFiber<?> arg0) {
		return this.comparable.compareTo(arg0.comparable);
	}

}
