package example.example.quasar;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.FiberExecutorScheduler;
import co.paralleluniverse.fibers.FiberSchedulerTask;
import co.paralleluniverse.strands.SuspendableCallable;

public class QFiberExecutorScheduler extends FiberExecutorScheduler {

	private static final int parallelism = Runtime.getRuntime().availableProcessors();
	private static final Comparator<? super Runnable> comparator = (a, b) -> {
		FiberSchedulerTask fiberSchedulerTaskA = (FiberSchedulerTask) a;
		FiberSchedulerTask fiberSchedulerTaskB = (FiberSchedulerTask) b;
		QFiber fiberA = (QFiber) fiberSchedulerTaskA.getFiber();
		QFiber fiberB = (QFiber) fiberSchedulerTaskB.getFiber();
		return fiberA.compareTo(fiberB);
	};

	private Comparable comparable;

	public QFiberExecutorScheduler(String name) {
		super(name, new ThreadPoolExecutor(parallelism, parallelism, Long.MAX_VALUE, TimeUnit.NANOSECONDS,
				new PriorityBlockingQueue<Runnable>(11, comparator)));
	}

	@Override
	public <T> QFiber<T> newFiber(SuspendableCallable<T> target) {
		return new QFiber<T>(this, target, comparable);
	}

	public void setComparable(Comparable comparable) {
		this.comparable = comparable;
	}

}
