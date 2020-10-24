import client.ClientTabPane;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import manager.ManagerTabPane;
import utilities.OracleDBMS;

public class BasicInterface extends VBox
{
    public BasicInterface(OracleDBMS con)
    {
        ImageView logo=new ImageView("images/logo.png");
        logo.setFitWidth(160.0);
        logo.setFitHeight(120.0);
        logo.setPickOnBounds(true);
        logo.setPreserveRatio(true);
    
        Label pms=new Label("Pharmacy Management\nSystem");
        pms.setFont(new Font(40));
        pms.setEffect(new Glow(0.5));
        pms.setTextFill(Paint.valueOf("#008CBA"));
    
        MenuItem signOut=new MenuItem("Sign Out");
    
        SplitMenuButton name=new SplitMenuButton();
        name.setEffect(new Glow(0.5));
        name.getItems().add(signOut);
    
        Label welcome=new Label("Welcome");
        welcome.setEffect(new Glow(0.5));
        welcome.setTextFill(Paint.valueOf("#008CBA"));
    
        TextField username=new TextField();
        username.setPromptText("Username");
    
        PasswordField password=new PasswordField();
        password.setPromptText("Password");
    
        Button signIn=new Button("Sign In");
    
        GridPane gp=new GridPane();
        gp.add(username,0,0);
        gp.add(password,0,1);
        gp.add(signIn,1,1);
        gp.setPadding(new Insets(5));
        gp.setHgap(5);
        gp.setVgap(5);
        
        TabPane clientPane=new ClientTabPane(con,this);
        ManagerTabPane managerPane=new ManagerTabPane(con,this);
    
        signIn.setOnAction(e->
        {
            boolean flag=true;
            if(flag)
            {
                name.setText(username.getText());
                username.clear();
                password.clear();
                gp.getChildren().removeAll(username,password,signIn);
                gp.add(welcome,0,0);
                gp.add(name,1,0);
                this.getChildren().remove(clientPane);
                this.getChildren().add(managerPane);
                managerPane.loadData(1);
            }
        });
    
        signOut.setOnAction(e->
        {
            gp.getChildren().removeAll(name,welcome);
            gp.add(username,0,0);
            gp.add(password,0,1);
            gp.add(signIn,1,1);
            this.getChildren().remove(managerPane);
            this.getChildren().add(clientPane);
        });
    
        Region reg=new Region();
    
        HBox hb=new HBox(logo,pms,reg,gp);
        HBox.setHgrow(reg,Priority.ALWAYS);
        hb.setSpacing(5);
        hb.setPadding(new Insets(5));
    
        this.getChildren().addAll(hb,clientPane);
    }
}
