import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpBox
{
	public static void display(String title, String message) {
		Stage window = new Stage();

		//Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Continue");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER.CENTER);

		//Display window and wait for it to be closed before returning
		Scene scene = new Scene(layout,250,100);
		window.setScene(scene);
		window.showAndWait();
	}
}
