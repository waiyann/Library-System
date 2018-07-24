/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import library.system.database.Database;
import library.system.model.Book;
import library.system.model.IssueInfo;

/**
 *
 * @author AMTCOMPUTER
 */
public class IssueDAO {
    public void saveIssueInfo(IssueInfo info) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        
        String sql = "insert into lbdb.issue (book_id,member_id,issue_date,renew_count) value (?,?,now(),0)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, info.getBookID());
            stmt.setInt(2, info.getMemberID());
            stmt.execute();
    }

    public IssueInfo getIssueInfo(int bookID) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        
        String sql = "select * from lbdb.issue where book_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookID);
            
            ResultSet result = stmt.executeQuery();
            IssueInfo issueInfo = null;
            if(result.next()){
                int memberID = result.getInt("member_id");
                Date issueDate = result.getDate("issue_date");
                int renewCount = result.getInt("renew_count");
                
                issueInfo = new IssueInfo(bookID, memberID, issueDate, renewCount);
            }
            
            return issueInfo;
            
    }
    public ObservableList<IssueInfo>getIssuedInfos() throws SQLException{
        Connection conn = Database.getInstance().getConnection();

        String sql = "select * from lbdb.issue";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        ObservableList<IssueInfo> infoList = FXCollections.observableArrayList();
        while (result.next()) {
            int bookID = result.getInt("book_id");
            int memberID = result.getInt("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");
            

            IssueInfo issueInfo = new IssueInfo(bookID, memberID, issueDate, renewCount);

            infoList.add(issueInfo);
        }
        return infoList;
    }

    public void deleteIssuedBook(int bookID) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        
        String sql = "delete  from lbdb.issue where book_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookID);
            stmt.execute();
    }

    public void renewIssuedBook(int bookID) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        
        String sql = "update lbdb.issue set renew_count=renew_count+1 where book_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, bookID);
            stmt.execute();
    }

    

    
}
