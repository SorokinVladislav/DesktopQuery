package ModelTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.*;


public class ModelTable3 {

    int JID;
    String XMLType;
    String Document_ID;
    String PRODUCT_NAME;
    String Document_Path;
    String Document_Receipt;
    String JobName;
    String LastUpdatedDate;

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    String GTIN;

    public int getGtin() {
        return gtin;
    }

    public void setGtin(int gtin) {
        this.gtin = gtin;
    }

    int gtin;



    String BatchNo;

    public ModelTable3(int JID, String jobName, String lastUpdatedDate, String document_Receipt,
                       String XMLType, String document_ID, String document_Path) {
        this.JID = JID;
        JobName = jobName;
        LastUpdatedDate = lastUpdatedDate;
        if (document_Receipt==null) {
            Document_Receipt = "Empty";
        }
        else if (document_Receipt.contains("<error_desc>")) {
            String[] a;
            String[] receiptString;
            a = document_Receipt.split("<error_desc>");
            receiptString = a[1].split("</");
            Document_Receipt = receiptString[0]  ;
        }
        else {
            String[] a = null;
            String[] receiptString = null;
            a = document_Receipt.split("<operation_comment>");
            receiptString = a[1].split("</");
            Document_Receipt = receiptString[0];
        }

        this.XMLType = XMLType;

        if (document_ID==null)
            Document_ID="Empty";
        else {
        Document_ID=document_ID;
        }

        if (document_Path==null)
            Document_Path="Empty";
        else {
            Document_Path=document_Path;
        }
    }

    public ModelTable3(Integer JID, String jobName, String lastUpdatedDate, String product_Name, String batchNo, String gtin ) throws SQLException {
        this.JID = JID;
        JobName = jobName;
        LastUpdatedDate = lastUpdatedDate;
        PRODUCT_NAME=product_Name;
        BatchNo=batchNo;
        GTIN=gtin;

        String selectSql5 = "SELECT * FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster]\n" +
                "  where JobID=" + "'" + JID + "'";
        ResultSet resultSet5 = null;
        Statement statement = DBConnection.getDBConnection();
        resultSet5 = statement.executeQuery(selectSql5);

        while (resultSet5.next()) {
            GTIN = resultSet5.getString("GTIN");

        }

    }

    public ModelTable3( String jobName, Integer JID,String lastUpdatedDate, String product_Name, String gtin, String batch) {
                this.JID = JID;
        JobName = jobName;
        LastUpdatedDate = lastUpdatedDate;
        PRODUCT_NAME=product_Name;
        if (gtin==null)
            GTIN="Empty";
        else
        GTIN=gtin;
        BatchNo=batch;

    }

    public ModelTable3(Integer JID, String jobName, String lastUpdatedDate ) {
        this.JID = JID;
        JobName = jobName;
        LastUpdatedDate = lastUpdatedDate;

    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getDocument_Path() {
        return Document_Path;
    }

    public void setDocument_Path(String document_Path) {
        Document_Path = document_Path;
    }

    public String getDocument_ID() {
        return Document_ID;
    }

    public void setDocument_ID(String document_ID) {
        Document_ID = document_ID;
    }

    public int getJID() {
        return JID;
    }

    public void setJID(int JID) {
        this.JID = JID;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getLastUpdatedDate() {
        return LastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        LastUpdatedDate = lastUpdatedDate;
    }

    public String getDocument_Receipt() {
        return Document_Receipt;
    }

    public void setDocument_Receipt(String document_Receipt) {
        Document_Receipt = document_Receipt;
    }

    public String getXMLType() {
        return XMLType;
    }

    public void setXMLType(String XMLType) {
        this.XMLType = XMLType;
    }

    public String getBatchNo() { return BatchNo;    }

    public void setBatchNo(String batchNo) { BatchNo = batchNo;  }
}
