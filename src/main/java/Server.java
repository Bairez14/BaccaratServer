import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.ListView;

// 2 seperate threads
// initial thread is for gui
// the server - separate thread - waits for clients
// client thread - runs on seperate thread - serves the clients
public class Server{

	int port;
	int count = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;	
	
	Server(Consumer<Serializable> call, int port) {
		this.port = port;
		callback = call;
		server = new TheServer();
		server.start();
		
	}
	
	
	public class TheServer extends Thread {
		
		public void run() {

			try(ServerSocket mysocket = new ServerSocket(port);){
		    	System.out.println("Server is waiting for a client");
				callback.accept("Server is waiting for a client!");

		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{ //what sends info back nd forth
		   // serve each client
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			BaccaratGame game;// //only creates one instance of game
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			/***** NEED??????????????????????? */
			// public void updateClients(String message) {
			// 	for(int i = 0; i < clients.size(); i++) {
			// 		ClientThread t = clients.get(i);
			// 		try {
			// 			t.out.writeObject(message);
			// 		}
			// 		catch(Exception e) {}
			// 	}
			// }
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}

				//updateClients("new client on server: client #"+count);
					
				 while(true) { // start Baccarat stuff here??
					    try {
							// System.out.println("inside try loop, beginning");
							BaccaratInfo clientInfo = (BaccaratInfo)in.readObject(); //Null pointer exception hereeeee
							game = new BaccaratGame();
							// System.out.println(clientInfo);
							game.betOn = clientInfo.betOnWho;
							game.currentBet = clientInfo.currentBet;
							clientInfo.currentEarnings = game.evaluateWinnings();
							//add cards that were genereated by dealer to clientinfo
							// System.out.println("got here before cards");
							// clientInfo.playerHand = new ArrayList<String>();
							// clientInfo.bankerHand = new ArrayList<String>();
							System.out.println("Player Cards:");
							for(Card c: game.playerHand){
								System.out.println(c.getSuite() + c.getValue());
								clientInfo.pCardVals.add("Suite: " + c.getSuite() + " Value:" + c.getValue());
								clientInfo.playerHand.add(c.toString()); 
							}
							System.out.println("Banker cards: ");
							for(Card c: game.bankerHand){
								System.out.println(c.getSuite() + c.getValue());
								clientInfo.bCardVals.add("Suite: " + c.getSuite() + " Value:" + c.getValue());
								clientInfo.bankerHand.add(c.toString());
								
							}

						
							//System.out.println("got here");
							//game.currentBet = clientInfo.currentBet;
							//game.winner = clientInfo.winner;
							clientInfo.winner = game.winner;
							clientInfo.totalEarnings = game.totalWinnings;
							clientInfo.currentEarnings = game.currentBet;
							clientInfo.playerDrew = game.pdraw;
							clientInfo.bankerDrew = game.bdraw;
							clientInfo.pPoints = BaccaratGameLogic.playerPoints;
							clientInfo.bPoints = BaccaratGameLogic.bankerPoints;
							//System.out.println(BaccaratGameLogic.playerPoints);
							//System.out.println(BaccaratGameLogic.bankerPoints);
							// game.totalWinnings = clientInfo.totalEarnings;
							// game.pdraw = clientInfo.playerDrew;
							// game.bdraw = clientInfo.bankerDrew;
							// 0 = draw
							// 1 = player
							// 2 = banker
							// -1 = no natural win
							//game.natural = clientInfo.naturalWin;
							clientInfo.naturalWin = game.natural;
							callback.accept("Client: " + count + " bet on: " + clientInfo.betOnWho + " for " + clientInfo.currentEarnings);
							callback.accept("Actual winner: " + game.winner);
							callback.accept("Natural win: " + clientInfo.naturalWin);
							callback.accept("Client: " + count + " earned: $" + clientInfo.totalEarnings);
							out.writeObject(clientInfo);
					    }catch(Exception e) {
							callback.accept("Client #" + count + " has left the server!");
							e.printStackTrace();
					    	clients.remove(this);
					    	break;
					    }
				}
			} //end of run

			//need to send info???

		}//end of client thread
}
