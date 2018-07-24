/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.addMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.system.dao.MemberDAO;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author AMTCOMPUTER
 */
public class AddMemberController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton saveBtn;
    
    private MemberDAO memberDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDao = new MemberDAO();
    }    

    @FXML
    private void saveMemberInfo(ActionEvent event) {
        
        int id = 0;
        try {
            String idStr = idField.getText();
            if (!idStr.isEmpty()) {
                id = Integer.parseInt(idField.getText());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for id field.");
            return;
        }

        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            System.out.println("Please fill all the required fields.");
            return;
        }

        System.out.println(id + "" + name + ":" + mobile + " " + address);

        Member member = new Member(id, name, mobile, address);

        try {
            memberDao.saveMember(member);
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("fail");
        }
    }
    
}
