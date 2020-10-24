import utilities.OracleDBMS;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;

public class Test extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	private Scene scene1,scene2;
	private TableView tableView;
	private ObservableList<Representative> data;

	TextField TRepID,TCmpID,TFirstName,TLastName,TCntNO;
	Connection con;

	@Override
	public void start(Stage stage) {

		stage.setFullScreen(true);
		stage.setOnCloseRequest(e->
		{
			try
			{
				if(con!=null)
					con.close();
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
		});

		stage.setHeight(600);
		stage.setWidth(900);
		//creating label Username
		Label text1 = new Label("User Name");

		//creating label password
		Label text2 = new Label("Password");

		//Creating Text Filed for Username
		TextField userName = new TextField();

		//Creating Text Filed for password
		PasswordField password = new PasswordField();

		//Creating Buttons
		Button submit = new Button("Submit");
		Button clear = new Button("Clear");

		TableColumn<Representative,Integer> RepIDCol = new TableColumn<>("RepID");
		RepIDCol.setMinWidth(100);
		RepIDCol.setCellValueFactory(new PropertyValueFactory<>("RepID"));

		TableColumn<Representative, String> FirstNameCol = new TableColumn<>("FirstName");
		FirstNameCol.setMinWidth(200);
		FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

		TableColumn<Representative, String> LastNameCol = new TableColumn<>("LastName");
		LastNameCol.setMinWidth(200);
		LastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));

		TableColumn<Representative,Integer> CmpIDCol = new TableColumn<>("CmpID");
		CmpIDCol.setMinWidth(100);
		CmpIDCol.setCellValueFactory(new PropertyValueFactory<>("CmpID"));

		TableColumn<Representative,String> CntNOCol = new TableColumn<>("CntNO");
		CntNOCol.setMinWidth(200);
		CntNOCol.setCellValueFactory(new PropertyValueFactory<>("CntNO"));

		tableView=new TableView();
		tableView.getColumns().addAll(RepIDCol,FirstNameCol,LastNameCol,CmpIDCol,CntNOCol);

		//Creating a Grid Pane
		GridPane gridPane = new GridPane();

		//Setting the padding
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		//Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(5);
		gridPane.setHgap(5);

		//Setting the Grid alignment
		gridPane.setAlignment(Pos.CENTER);

		//Arranging all the nodes in the grid
		gridPane.add(text1, 0, 0);
		gridPane.add(userName, 1, 0);
		gridPane.add(text2, 0, 1);
		gridPane.add(password, 1, 1);

		HBox hb=new HBox();
		hb.setSpacing(5);
		hb.getChildren().addAll(submit,clear);

		gridPane.add(hb, 1, 2);

		// Creating a scene object
		scene1 = new Scene(gridPane);
		scene1.getStylesheets().add("utilities/Style.css");

		HBox hbox1=new HBox();
		HBox hbox2=new HBox();
		hbox1.setPadding(new Insets(10,10,10,10));
		hbox1.setSpacing(10);
		hbox2.setPadding(new Insets(10,10,10,10));
		hbox2.setSpacing(10);

		Button add=new Button("Add");
		add.setOnAction(e->addButton());
		Button delete=new Button("Delete");
		delete.setOnAction(e->deleteButton());

		TRepID = new TextField();
		TRepID.setPromptText("Representative ID");
		TFirstName =new TextField();
		TFirstName.setPromptText("First Name");
		TLastName = new TextField();
		TLastName.setPromptText("Last Name");
		TCmpID = new TextField();
		TCmpID.setPromptText("Company ID");
		TCntNO = new TextField();
		TCntNO.setPromptText("Contact No");

		hbox1.getChildren().addAll(TRepID,TFirstName,TLastName);
		hbox2.getChildren().addAll(TCmpID,TCntNO,add,delete);

		VBox vb=new VBox();

		submit.setOnAction(e->
		{
			String user=userName.getText();
			String pass=password.getText();
			//utilities.OracleDBMS obj=new utilities.OracleDBMS(user,pass);
			OracleDBMS obj=new OracleDBMS("Pharmacy","1505101");
			con=obj.getConnection();
			if(con==null)
			{
				PopUpBox.display("Warning","Invalid Username or Password");
				userName.clear();
				password.clear();
			}
			else
			{
				System.out.println("Connected");
				load(con);
				stage.getScene().setRoot(vb);
			}
		});

		clear.setOnAction(e->
		{
			userName.clear();
			password.clear();
		});

//		vb.setMinSize(800,600);
		vb.setPadding(new Insets(10,10,10,10));
		vb.setSpacing(10);
		vb.getChildren().addAll(tableView,hbox1,hbox2);
//		gp.add(ins,0,1);

		// Setting title to the Stage
		stage.setTitle("Pharmacy Mangangement System");

		// Adding scene to the stage
		stage.setScene(scene1);

		//Displaying the contents of the stage
		stage.show();
	}

	public void load(Connection con)
	{

		data = FXCollections.observableArrayList();

		List<List<String>> RepresentativeDataList = Representative.getAllRepresentatives(con);
		for (List<String> row : RepresentativeDataList)
		{
			data.add(new Representative(row.get(0),row.get(1),row.get(2),row.get(3),row.get(4)));
		}

		tableView.setEditable(true);
		tableView.setItems(data);

	}

	public void addButton()
	{
		Representative rep=new Representative();
		rep.setRepID(Integer.parseInt(TRepID.getText()));
		rep.setCmpID(Integer.parseInt(TCmpID.getText()));
		rep.setFirstName(TFirstName.getText());
		rep.setLastName(TLastName.getText());
		rep.setCntNO(TCntNO.getText());
		boolean flag=true;
		try
		{
			rep.insertIntoDatabase(con);
			tableView.getItems().add(rep);
		}
		catch(Exception exp)
		{
			PopUpBox.display("Error","Invalid Data");
			flag=false;
		}
		if(flag)
		{
			TRepID.clear();
			TFirstName.clear();
			TLastName.clear();
			TCmpID.clear();
			TCntNO.clear();
		}
	}

	public void deleteButton()
	{
		ObservableList<Representative> selected,all;
		selected=tableView.getSelectionModel().getSelectedItems();
		all=tableView.getItems();
		for(Representative rep:selected)
		{
			all.remove(rep);
			rep.deleteFromDatabase(con);
		}
	}
}
