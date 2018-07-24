/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.issueInfo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.system.bookList.BookListController;
import library.system.dao.IssueDAO;
import library.system.model.Book;
import library.system.model.IssueInfo;

/**
 * FXML Controller class
 *
 * @author AMTCOMPUTER
 */
public class IssueInfoController implements Initializable {

    @FXML
    private TableView<IssueInfo> issueInfoTable;
    @FXML
    private TableColumn<IssueInfo, Integer> bIdColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> uIdColumn;
    @FXML
    private TableColumn<IssueInfo, Date> issuedDateColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> renewCountColumn;
    
    private IssueDAO issueDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        issueDao = new IssueDAO();
        initCoulumn();
        loadTableData();
    }    
    
    private void initCoulumn() {
        bIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        uIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        renewCountColumn.setCellValueFactory(new PropertyValueFactory<>("renewCount"));

    }

    private void loadTableData() {
        try {
            ObservableList<IssueInfo> list = issueDao.getIssuedInfos();
            issueInfoTable.getItems().setAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
}
