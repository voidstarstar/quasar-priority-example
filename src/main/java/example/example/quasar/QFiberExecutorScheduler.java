package example.example.quasar;

import java.util.concurrent.Executor;

import co.paralleluniverse.fibers.FiberExecutorScheduler;
import co.paralleluniverse.strands.SuspendableCallable;

public class QFiberExecutorScheduler extends FiberExecutorScheduler {

	private Comparable comparable;

	public QFiberExecutorScheduler(String name, Executor executor) {
		super(name, executor);
	}

	@Override
	public <T> QFiber<T> newFiber(SuspendableCallable<T> target) {
		return new QFiber<T>(this, target, comparable);
	}

	public void setComparable(Comparable comparable) {
		this.comparable = comparable;
	}

}
