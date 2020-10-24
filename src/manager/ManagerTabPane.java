package manager;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import utilities.OracleDBMS;

public class ManagerTabPane extends TabPane
{
    EmployeesTab employees;
    public ManagerTabPane(OracleDBMS con,Pane root)
    {
        employees=new EmployeesTab("Employees",con,root);
        this.getTabs().addAll(employees);
    }
    public void loadData(Integer managerId)
    {
        employees.loadData(managerId);
    }
}
