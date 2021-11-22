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
			BaccaratGame game = new BaccaratGame(); //only creates one instance
			
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

							BaccaratInfo clientInfo = (BaccaratInfo)in.readObject();
							//add cards that were genereated by dealer to clientinfo
							clientInfo.playerHand.add(game.playerHand.get(0).toString());
							clientInfo.playerHand.add(game.playerHand.get(1).toString());
							clientInfo.bankerHand.add(game.bankerHand.get(0).toString());
							clientInfo.bankerHand.add(game.bankerHand.get(1).toString());
							game.betOn = clientInfo.betOnWho;
							game.currentBet = clientInfo.currentBet;
							game.winner = clientInfo.winner;
							game.totalWinnings = clientInfo.totalEarnings;
							game.pdraw = clientInfo.playerDrew;
							game.bdraw = clientInfo.bankerDrew;
							// 0 = draw
							// 1 = player
							// 2 = banker
							// -1 = no natural win
							game.natural = clientInfo.naturalWin;
					    	 
							callback.accept("Client: " + count + " bet on: " + clientInfo.betOnWho);
							callback.accept("Actual winner: " + game.winner);
							callback.accept("Client: " + count + " earned: $" + game.totalWinnings);
							
							out.writeObject(clientInfo);
					    	}
					    catch(Exception e) {
					    	callback.accept("Client #"+count+" has left the server!");
					    	clients.remove(this);
					    	break;
					    }
				}
			} //end of run

			//need to send info???

		}//end of client thread
}
