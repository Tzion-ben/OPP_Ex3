package gameClient;

import Server.Game_Server;

/**
 * This class is running the game.
 * @author tzion
 *
 */
public class loginTOgameManager implements Runnable {
	public static void main(String[] a) {
		Thread client = new Thread(new loginTOgameManager());
		client.start();
	}

	public void run() {
	}
	/*******************************************************************************************
	 ***********************************contractor**********************************************
	 *******************************************************************************************/
	public loginTOgameManager() {
		newGame=new MyGameGUI();
	}

	//**************************************private field***************************************
	private MyGameGUI newGame;
}
