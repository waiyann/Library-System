/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.editMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.system.dao.MemberDAO;
import library.system.editBook.EditBookController;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author AMTCOMPUTER
 */
public class EditMemberController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton updateMemberbtn;
    @FXML
    private JFXButton cancelBtn;
    
    private MemberDAO memberDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDao = new MemberDAO();
    }
        
    

    @FXML
    private void updateMemberView(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            System.out.println("Please fill in all the required fields...");
            return;
        }

        Member member = new Member(id, name, mobile, address);

        try {
            memberDao.updateMember(member);
            System.out.println("Updating success...");
            Stage stage = (Stage) mobileField.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditBookController.class.getName()).log(Level.SEVERE, null, ex);
    }

    
    }
    

    @FXML
    private void closeWindow(ActionEvent event) {
         Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
    public void initData(Member member) {
        idField.setDisable(true);
        idField.setText(Integer.toString(member.getId()));
        nameField.setText(member.getName());
        mobileField.setText(member.getMobile());
        addressField.setText(member.getAddress());

    }
    
}
