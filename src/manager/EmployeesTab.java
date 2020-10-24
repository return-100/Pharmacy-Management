package manager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import utilities.OracleDBMS;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class EmployeesTab extends Tab
{
    static OracleDBMS con;
    static TableView tableView;
    public EmployeesTab(String text,OracleDBMS con,Pane root)
    {
        super(text);
        this.setClosable(false);
        
        EmployeesTab.con=con;
    
        AnchorPane pane=new AnchorPane();
        pane.prefWidthProperty().bind(root.widthProperty());
        pane.prefHeightProperty().bind(root.heightProperty());
    
        TableColumn<Employee,String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    
        TableColumn<Employee,String> doj = new TableColumn<>("Date of Joining");
        doj.setCellValueFactory(new PropertyValueFactory<>("doj"));
    
        TableColumn<Employee,String> cntNo = new TableColumn<>("Contact No");
        cntNo.setCellValueFactory(new PropertyValueFactory<>("cntNo"));
    
        TableColumn<Employee,Integer> salary = new TableColumn<>("Salary");
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    
        tableView=new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(name,doj,cntNo,salary);
    
        pane.getChildren().add(tableView);
        tableView.prefWidthProperty().bind(pane.widthProperty());
        tableView.prefHeightProperty().bind(pane.heightProperty());
    
        this.setContent(pane);
    }
    
    void loadData(Integer managerId)
    {
        new Thread(()->
        {
            ObservableList<Employee> data = FXCollections.observableArrayList();
            List<List<String>> EmployeeDataList = Employee.getAllEmployees(con,managerId);
    
            for (List<String> row : EmployeeDataList)
            {
                StringTokenizer st=new StringTokenizer(row.get(1)," ");
                data.add(new Employee(row.get(0),st.nextToken(),row.get(2),Integer.parseInt(row.get(3))));
            }
        
            tableView.setEditable(true);
            tableView.setItems(data);
        }).start();
    }
}
