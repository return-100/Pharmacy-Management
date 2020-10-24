import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import utilities.OracleDBMS;
import javafx.application.Application;
import javafx.stage.Stage;

public class Tester extends Application
{
    public static OracleDBMS con;
    
	public static void main(String[] args)
	{
		con=new OracleDBMS("Pharmacy","1505101");
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("Pharmacy Management System");
        
        VBox root=new BasicInterface(con);
        
        Scene scene=new Scene(root);
        scene.getStylesheets().add("utilities/Style.css");
        
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e->
        {
            con.closeConnection();
        });
		primaryStage.show();
	}
}
