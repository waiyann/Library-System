/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.dao.IssueDAO;
import library.system.dao.MemberDAO;
import library.system.util.MessageBox;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;

/**
 *
 * @author AMTCOMPUTER
 */
public class MainController implements Initializable {

    @FXML
    private Button HomeBtn;
    @FXML
    private Button addBookBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton bookListBtn;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private JFXButton memberListBtn;
    @FXML
    private JFXTextField searchBookIDField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;
    @FXML
    private Text availableText;

    private MemberDAO memberDao;

    private BookDAO bookDao;

    private IssueDAO issueDao;
    @FXML
    private JFXTextField searchMemberField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField issuedBookSearch;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text issuedDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton returnBtn;
    @FXML
    private JFXButton renewCountBtn;
    @FXML
    private JFXButton issueInfoBtn;
    @FXML
    private MenuItem dbConfigItem;

    private final String defaultStyle = "-fx-border-width: 0px 0px 0px 5px; -fx-border-color: #263238";
    private final String activeStyle = "-fx-border-width: 0px 0px 0px 5px; -fx-border-color: #eceff1";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeActive();
        bookDao = new BookDAO();
        memberDao = new MemberDAO();
        issueDao = new IssueDAO();
    }

    @FXML
    private void loadHomeView(ActionEvent event) {
        homeActive();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);

    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        addBookActive();
        loadView("/library/system/addBook/addBook.fxml");

    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        bookListActive();
        loadView("/library/system/bookList/bookList.fxml");

    }

    @FXML
    private void loadAddMemberView(ActionEvent event) throws IOException {
        addMemberActive();
        loadView("/library/system/addMember/addMember.fxml");

    }

    @FXML
    private void loadMemberListView(ActionEvent event) throws IOException {
        memberListActive();
        loadView("/library/system/memberList/memberList.fxml");

    }

    @FXML
    private void searchBookInfo(ActionEvent event) throws SQLException {
        clearSearchBookCache();

        String idStr = searchBookIDField.getText();

        if (idStr == null) {
            MessageBox.showErrorMessage("Error", "Please enter the book id first...");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);

            Book book = bookDao.getBook(id);

            if (book != null) {
                String title = book.getTitle();
                String author = book.getAuthor();
                String publisher = book.getPublisher();
                boolean available = book.isAvailable();

                titleText.setText(title);
                authorText.setText(author);
                publisherText.setText(publisher);
                if (available) {
                    availableText.setText("Available");
                } else {
                    availableText.setText(" Not Available");
                }
            } else {
                MessageBox.showErrorMessage("Error", "Cannot find the book info for this id");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for book id");
        } catch (SQLException ex) {

        }

    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {
        clearSearchMemberCache();

        String idStr = searchMemberField.getText();

        if (idStr == null) {
            MessageBox.showErrorMessage("Input Error", "Please enter the member id first...");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);

            Member member = memberDao.getMember(id);

            if (member != null) {
                String name = member.getName();
                String mobile = member.getMobile();
                String address = member.getAddress();

                nameText.setText(name);
                mobileText.setText(mobile);
                addressText.setText(address);
            } else {
                MessageBox.showErrorMessage("Error", "Cannot find the book for this id.");

            }
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Invalid Input", "Invalid input for memeber id");
        } catch (SQLException ex) {

        }
    }

    private void clearSearchBookCache() {
        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
    }

    private void clearSearchMemberCache() {
        nameText.setText("-");
        mobileText.setText("-");
        addressText.setText("-");
    }

    @FXML
    private void issueBook(ActionEvent event) throws SQLException {

        String bookIdStr = searchBookIDField.getText();
        String memberIdStr = searchMemberField.getText();
        if (bookIdStr.isEmpty() || memberIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill out the fields first.");
            return;
        }

        int bookID;
        int memberID;
        try {
            bookID = Integer.parseInt(bookIdStr);
            memberID = Integer.parseInt(memberIdStr);
        } catch (NumberFormatException e) {
            return;
        }

        try {
            Book book = bookDao.getBook(bookID);
            if (book.isAvailable()) {
                issueDao.saveIssueInfo(new IssueInfo(bookID, memberID));
                bookDao.updateAvailable(bookID, false);
            } else {
                MessageBox.showErrorMessage("Error", "This book is already issued.");
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            MessageBox.showErrorMessage("Error", "Check your input first");

        }

    }

    @FXML
    private void searchIssuedBook(ActionEvent event) {

        clearSearchIssuedBookCache();

        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill in the book ID field.");
            return;
        }
        int bookID;
        try {
            bookID = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {

            return;
        }

        try {
            IssueInfo issueInfo = issueDao.getIssueInfo(bookID);
            if (issueInfo != null) {
                Book book = bookDao.getBook(issueInfo.getBookID());
                Member member = memberDao.getMember(issueInfo.getMemberID());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());

                mNameText.setText(member.getName());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());

                issuedDateText.setText("Issued Date : " + issueInfo.getIssueDate());
                renewCountText.setText("Renew Count : " + issueInfo.getRenewCount());
            } else {
                MessageBox.showErrorMessage("Error", "Cannot find the book for this id.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void returnBook(ActionEvent event) {
        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("error", "Please fill in the book ID field.");
            return;
        }
        int bookID;
        try {
            bookID = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid Number format");
            return;
        }

        try {
            IssueInfo issueInfo = issueDao.getIssueInfo(bookID);
            if (issueInfo != null) {
                Optional<ButtonType> option = MessageBox.showConfirmationMessage("Confirmation", "Are you sure you want to return this book???");

                if (option.get() == ButtonType.OK) {
                    issueDao.deleteIssuedBook(bookID);
                    bookDao.updateAvailable(bookID, true);
                }
            } else {
                MessageBox.showErrorMessage("Error", "Cannot find the book for this id.");

            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void renewBook(ActionEvent event) throws SQLException {
        String bookIdStr = issuedBookSearch.getText();
        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill in the book ID field.");
            return;
        }
        int bookID;
        try {
            bookID = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Input Error", "Invalid Number format");
            return;
        }

        IssueInfo issueInfo = issueDao.getIssueInfo(bookID);
        if (issueInfo != null) {
            issueDao.renewIssuedBook(bookID);
            System.out.println("Done renew");

        } else {
            MessageBox.showErrorMessage("Error", "Cannot find the book.");
        }

    }

    private void clearSearchIssuedBookCache() {
        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");
        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");
        issuedDateText.setText("-");
        renewCountText.setText("-");
    }

    private void loadView(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));

        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadIssueInfoView(ActionEvent event) throws IOException {
        issueInfoActive();
        loadView("/library/system/issueInfo/issueInfo.fxml");
    }

    @FXML
    private void loadDbConfigView(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/library/system/config/dbConfig.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(centerPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void homeActive() {
        HomeBtn.setStyle(activeStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issueInfoBtn.setStyle(defaultStyle);
    }

    private void addBookActive() {
        HomeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(activeStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issueInfoBtn.setStyle(defaultStyle);

    }

    private void bookListActive() {
        HomeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(activeStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issueInfoBtn.setStyle(defaultStyle);

    }

    private void addMemberActive() {
        HomeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(activeStyle);
        memberListBtn.setStyle(defaultStyle);
        issueInfoBtn.setStyle(defaultStyle);

    }

    private void memberListActive() {
        HomeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(activeStyle);
        issueInfoBtn.setStyle(defaultStyle);

    }

    private void issueInfoActive() {
        HomeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issueInfoBtn.setStyle(activeStyle);

    }
}
