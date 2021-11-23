
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	TextField  portField;
	Button exit, turnOff, portButton;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	HBox portBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	EventHandler<ActionEvent> beginServer;
	int port;
	TextField userPort = new TextField();
	ListView<String> listItems, listItems2;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("The Server GUI ");
		sceneMap = new HashMap<String, Scene>();
		listItems = new ListView<String>();

		sceneMap.put("gameInfo", gameInfoScene(primaryStage));

		turnOff = new Button("Server OFF");
		exit = new Button("EXIT");

		portField = new TextField();
		portField.setPromptText("Enter port number:");
		portButton = new Button("Connect to Server");

		portBox = new HBox(10, portField, portButton);
		buttonBox = new HBox(600, turnOff);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(250));
		startPane.setBottom(buttonBox);
		startPane.setCenter(portBox);

		startScene = new Scene(startPane, 800, 800);
		startScene.getRoot().setStyle("-fx-font-family: 'serif';" + "-fx-background-color: Red");
				

		beginServer = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int p = Integer.parseInt(portField.getText());
				primaryStage.setScene(sceneMap.get("gameInfo"));
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						listItems.getItems().add(data.toString()); 
					});
				}, p);
			}
		};

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

		portButton.setOnAction(beginServer);
		exit.setOnAction(e->Platform.exit());

		primaryStage.setScene(startScene);
		primaryStage.show();

	}
	
	public Scene gameInfoScene(Stage primaryStage) {
		// # of clients on the server -- textbox
		// results of each game played -- listpane
		// how much the client bet -- listpane
		// how much the client won or lost -- listpane
		// clients dropping off the server -- textbox
		// new clients joining the server -- listpane
		// client plays another hand -- listpane
		// leave button to disconnect from server

		//listItems = new ListView<String>();
		BorderPane serverPane = new BorderPane();
		turnOff = new Button("Turn Off Server");
		HBox serverBox = new HBox(10, turnOff, listItems);
		serverPane.setCenter(serverBox);
		turnOff.setOnAction(e-> primaryStage.close()); //turns off server, closes it
		Scene serverScene = new Scene(serverPane, 800, 800);
		listItems.setPrefWidth(575);
		listItems.setPrefHeight(700);
		serverScene.getRoot().setStyle("-fx-font-family: 'serif';" + "-fx-background-color: Red");
		//sceneMap.put("gameInfo", serverScene);

		return serverScene;
	}

}
// this.serverChoice.setOnAction(e->{
// primaryStage.setScene(sceneMap.get("server"));
// primaryStage.setTitle("This is the Server");
// serverConnection = new Server(data -> {
// Platform.runLater(()->{
// listItems.getItems().add(data.toString());
// });

// }, port);

// });