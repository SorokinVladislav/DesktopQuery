package ModelTable;

public class ModelTable2 {


    String JobID;
    String TransactionStatus;
    String Document_Receipt;
    String Document_Path;
    String Document_ID;
    String UpdatedDate;
    String XMLType;

    public ModelTable2(String jobID, String transactionStatus, String document_Receipt, String XmlType,
                       String updatedDate, String document_ID, String document_Path) {
        JobID = jobID;
        TransactionStatus = transactionStatus;

        if (document_Receipt==null)
            Document_Receipt="Empty";

        // есть ли ошибка в отчете
        else if (document_Receipt.contains("<error_desc>")) {
            String[] a=null;
            String[] receiptString=null;
            a = document_Receipt.split("<error_desc>");
            receiptString = a[1].split("</");
            Document_Receipt = receiptString[0]  ;
        }
        else {
            // иначе проверяем статус отчета
            String[] a = null;
            String[] receiptString = null;
            a = document_Receipt.split("<operation_comment>");
            receiptString = a[1].split("</");
            Document_Receipt = receiptString[0];
        }
        XMLType = XmlType;
        if (updatedDate==null)
            updatedDate="Empty";
        else
            UpdatedDate=updatedDate;

        if (document_ID==null)
            Document_ID="Empty";
        else {
            Document_ID=document_ID;
        }

        if (document_Path==null)
            Document_Path="Empty";
        else {
            Document_Path = document_Path;
        }

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

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getXMLType() {
        return XMLType;
    }

    public void setXMLType(String XMLType) {
        this.XMLType = XMLType;
    }

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public String getTransactionStatus() {
        return TransactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        TransactionStatus = transactionStatus;
    }

    public String getDocument_Receipt() {
        return Document_Receipt;
    }

    public void setDocument_Receipt(String document_Receipt) {
        Document_Receipt = document_Receipt;
    }
}
