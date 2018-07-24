/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.editBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.model.Book;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author AMTCOMPUTER
 */
public class EditBookController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton updateBookInfo;
    @FXML
    private JFXButton cancelBtn;

    private BookDAO bookDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDao = new BookDAO();
    }

    @FXML
    private void saveBookInfo(ActionEvent event) throws IOException {

        int id = Integer.parseInt(idField.getText());
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            System.out.println("Please fill in all the required fields...");
            return;
        }

        Book book = new Book(id, title, author, publisher);

        try {
            bookDao.updateBook(book);
            System.out.println("Updating success...");
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    public void initData(Book book) {
        idField.setDisable(true);
        idField.setText(Integer.toString(book.getId()));
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        publisherField.setText(book.getPublisher());

    }

    

}
