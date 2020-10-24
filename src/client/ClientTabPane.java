package client;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import utilities.OracleDBMS;

public class ClientTabPane extends TabPane
{
    public ClientTabPane(OracleDBMS con,Pane root)
    {
        Tab home=new Tab("Home");
        home.setClosable(false);
    
        Tab products=new ProductsTab("Products",con,root);
    
        Tab purchases=new Tab("Purchases");
        purchases.setClosable(false);
    
        Tab about=new Tab("About");
        about.setClosable(false);
    
        this.getTabs().addAll(home,products,purchases,about);
    }
}
