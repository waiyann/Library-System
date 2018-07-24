/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.model;

import java.sql.Date;

/**
 *
 * @author AMTCOMPUTER
 */
public class IssueInfo {
    private int bookID;
    private int memberID;
    private Date issueDate;
    private int renewCount;

    public IssueInfo(int bookID, int memberID) {
        this.bookID = bookID;
        this.memberID = memberID;
    }

    public IssueInfo(int bookID, int memberID, Date issueDate, int renewCount) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueDate = issueDate;
        this.renewCount = renewCount;
    }
    

    

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
    
    
}
