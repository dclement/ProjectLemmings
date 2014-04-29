
package environment;

import java.util.EventListener;


public interface GameListener extends EventListener {

	/** Invoked when game is over.
	 */
	public void onGameOver();

}
