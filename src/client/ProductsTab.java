package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilities.OracleDBMS;
import java.util.List;

public class ProductsTab extends Tab
{
    public ProductsTab(String text,OracleDBMS con,Pane root)
    {
        super(text);
        this.setClosable(false);
        
        AnchorPane pane=new AnchorPane();
        pane.prefWidthProperty().bind(root.widthProperty());
        pane.prefHeightProperty().bind(root.heightProperty());
    
        TableColumn<Product,String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    
        TableColumn<Product, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
    
        TableColumn<Product, Integer> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    
        TableColumn<Product,String> companyName = new TableColumn<>("Company Name");
        companyName.setCellValueFactory(new PropertyValueFactory<>("cmpName"));
    
        TableView tableView=new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(name,type,price,companyName);
        
    
        new Thread(()->
        {
            ObservableList<Product> data = FXCollections.observableArrayList();
            List<List<String>> RepresentativeDataList = Product.getAllProducts(con);
            
            for (List<String> row : RepresentativeDataList)
                data.add(new Product(row.get(0),Integer.parseInt(row.get(1)),row.get(2),row.get(3)));
        
            tableView.setEditable(true);
            tableView.setItems(data);
        }).start();
    
        pane.getChildren().add(tableView);
        tableView.prefWidthProperty().bind(pane.widthProperty());
        tableView.prefHeightProperty().bind(pane.heightProperty());
        
        this.setContent(pane);
    }
}
