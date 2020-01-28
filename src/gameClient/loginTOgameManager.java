package gameClient;

import Server.Game_Server;

/**
 * This class allow as to login with a ID and play automaticaly and then create a KML file
 * of the path of the robots if the bast path.
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
