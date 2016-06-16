package example.example.quasar;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.FiberFactory;

public abstract class QBasicActor<Message, V> extends BasicActor<Message, V> {

	private Comparable comparable;

	public QBasicActor(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see co.paralleluniverse.actors.Actor#spawn(co.paralleluniverse.fibers.FiberFactory)
	 */
	@Override
	public ActorRef<Message> spawn(FiberFactory ff) {
		if (ff == null)
			return spawn();
		checkReplacement();
		((QFiberExecutorScheduler) ff).newFiber(runner).setComparable(this.comparable).setName(getName()).start();
		return ref();
	}

	public void setComparable(Comparable comparable) {
		this.comparable = comparable;
	}

}
