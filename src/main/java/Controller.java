
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import ModelTable.*;
import javafx.scene.layout.AnchorPane;



public class Controller {


    @FXML
    private Button excellButton;

    @FXML
    private TextField tokenField;


    ObservableList<ModelTable> data=FXCollections.observableArrayList();
    ObservableList<ModelTable2> data2=FXCollections.observableArrayList();
    ObservableList<ModelTable3> data3=FXCollections.observableArrayList();


    public String whatJobStatus(String st){
        switch (st) {
            case "2":
                return "2 - Approved";
            case "4":
                return "4 - Линия выделена";
            case "7":
                return "7 - Transfered To Plant";
            case "8":
                return "8 - Направлена на линию";
            case "12":
                return "12 - Работа приостановлена";
            case "16":
                return "16 - Работа отклонена";
            case "13":
                return "13 - Работа закрыта";
            default:
                return st;
        }
    }

    public String whatDataTransferStatus(String st){
        switch (st) {
            case "14":
                return "14 - Тайм-аут по времени в 6 часов";
            case "200":
                return "200 - Данные ушли в МДЛП, но не закрыли заказ в СУЗ";
            case "201":
                return "201 - Все данные переданы в том числе в Trace Way";
            case "17":
                return "17 - Передача данных в МДЛП выполнена, но не выполнена отправка дерева в TraceWay";
            case "102":
                return "102";
            default:
                return st;
        }
    }

    public String whatTransactionStatus(String st){
        switch (st) {
            case "0":
                return "0 – Новый отчёт";
            case "2":
                return "2 – Отчёт отклонён (Reject)";
            case "4":
                return "4 – процесс получения квитанции";
            case "7":
                return "7 – Успешно получена квитанция";
            default:
                return st;
        }
    }

    public String whatXMLType(String st){
        switch (st) {
            case "213":
                return st + " - Бронирование SSCC";
            case "2132":
                return st + " - Отмена бронирования SSCC";
            case "431":
                return st + " - Перемещение";
            case "915":
            case "9151":
            case "9152":
                return st + " - Групповое агрегирование";
            case "552":
                return st + " - Вывод из оборота";
            case "911":
            case "9111":
            case "9112":
                return st + " - Агрегирование";
            default:
                return st;
        }
    }


    @FXML
    private TextField jobN;

    @FXML
    private Button authSignInButton;

    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable, Integer> jID;

    @FXML
    private TableColumn<ModelTable, String> jobName;

    @FXML
    private TableColumn<ModelTable, String> jobStatus;

    @FXML
    private TableColumn<ModelTable, String> dataTransferStage;

    @FXML
    private TableView<ModelTable2> table2;

    @FXML
    private TableColumn<ModelTable2, Integer> jobID2;

    @FXML
    private TableColumn<ModelTable2, String> status;

    @FXML
    private TableColumn<ModelTable2, String> receipt;

    @FXML
    private TableColumn<ModelTable2, String> xmlType;

    @FXML
    private TableColumn<ModelTable2, String> upTimeCol;

    @FXML
    private TableColumn<ModelTable2, String> docIDcol2;

    @FXML
    private TableColumn<ModelTable2, String> ssccsgtinCol2;

    @FXML
    private TableView<ModelTable3> table3;

    @FXML
    private TableColumn<ModelTable3, Integer> jobid3;

    @FXML
    private TableColumn<ModelTable3, String> jobName3;

    @FXML
    private TableColumn<ModelTable3, String> closedTime;

    @FXML
    private TableColumn<ModelTable3, String> receipt2;

    @FXML
    private TableColumn<ModelTable3, String> xml2;

    @FXML
    private TableColumn<ModelTable3, String> docIDcol;

    @FXML
    private TableColumn<ModelTable3, String> ssccORsgtin;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox checkBox;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private TextField jobN2;

    @FXML
    private Button authSignInButton2;

    @FXML
    private CheckBox checkBox4;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField jobID4;

    @FXML
    private ComboBox<String> boxCombo;

    @FXML
    private Button authSignInButton3;

    @FXML
    private Button copyDocIDBut;

    @FXML
    private TextField batchNoField;

    @FXML
    private Button copySsccSgtin;

    @FXML
    private TextField codeCount;

    @FXML
    private Button codeCountButton;

    @FXML
    private Button jobStatusButton;

    @FXML
    private Button getReportButton;

    @FXML
    private Button getDetailedJob;

    @FXML
    private Button resend9151Button;

    @FXML
    private Button copyNameButton;

    @FXML
    private AnchorPane anchorPaneReport;

    @FXML
    private Button jobStatusButton12;

    @FXML
    private Button jobStatusButton16;

    @FXML




    void initialize() {



/*
        excellButton.setOnAction(event -> {
            try {
                String token=tokenField.getText();
                Stage stage = (Stage) excellButton.getScene().getWindow();
                stage.close();
                excelFiller filler=new excelFiller();

                while (true) {
                    filler.execute(token);
                    Thread.sleep(3000000);
                }
            }catch (Exception e){}

        });
*/



        copyNameButton.setOnAction(event -> {
        String myString=null;
        myString =table3.getSelectionModel().getSelectedItem().getJobName();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });




//get Map report
        getReportButton.setOnAction(event -> {
       
          String jobName =table3.getSelectionModel().getSelectedItem().getJobName().replaceAll("/", "_");
            String jobID =String.valueOf(table3.getSelectionModel().getSelectedItem().getJID());
            try {
               new reportMap().mapReport(jobID, jobName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//get detailed job
        getDetailedJob.setOnAction(event -> {

            String jobName =table3.getSelectionModel().getSelectedItem().getJobName().replaceAll("/", "_");
            String jobID =String.valueOf(table3.getSelectionModel().getSelectedItem().getJID());
            try {
                new reportMap().detailedJob(jobID, jobName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        resend9151Button.setOnAction(event -> {
            String queryText2 = jobN2.getText();
            try {
                resend9151.resend915(queryText2, checkBox4, passField);
            } catch (SQLException | InterruptedException throwables) {
                throwables.printStackTrace();
            }
        });





       boxCombo.getItems().addAll("552", "9151" , "9152",  "2132", "213", "9111", "9112");


        Statement statement = DBConnection.getDBConnection();

        copyDocIDBut.setOnAction(event -> {
            String myString;
            try {
         myString =table3.getSelectionModel().getSelectedItem().getDocument_ID();
            }
            catch (Exception e) {
                myString = table2.getSelectionModel().getSelectedItem().getDocument_ID();
            }
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        });


        copySsccSgtin.setOnAction(event -> {
            String myString;
            try {
                myString = table3.getSelectionModel().getSelectedItem().getDocument_Path();
            }
            catch (Exception e) {
                myString = table2.getSelectionModel().getSelectedItem().getDocument_Path();
            }

            String ssccORsgtin=SSCCorSGTIN.whatSSCCorSGTIN(myString);
            StringSelection stringSelection = new StringSelection(ssccORsgtin);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

        });


        jID.setCellValueFactory(new PropertyValueFactory<>("JID"));
        jobName.setCellValueFactory(new PropertyValueFactory<>("JobName"));
        jobStatus.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
        dataTransferStage.setCellValueFactory(new PropertyValueFactory<>("DataTransferStage"));

        jobID2.setCellValueFactory(new PropertyValueFactory<>("JobID"));
        status.setCellValueFactory(new PropertyValueFactory<>("TransactionStatus"));
        receipt.setCellValueFactory(new PropertyValueFactory<>("Document_Receipt"));
        xmlType.setCellValueFactory(new PropertyValueFactory<>("XMLType"));
        upTimeCol.setCellValueFactory(new PropertyValueFactory<>("UpdatedDate"));
        docIDcol2.setCellValueFactory(new PropertyValueFactory<>("Document_ID"));
        ssccsgtinCol2.setCellValueFactory(new PropertyValueFactory<>("Document_Path"));

        jobid3.setCellValueFactory(new PropertyValueFactory<>("JID"));
        jobName3.setCellValueFactory(new PropertyValueFactory<>("JobName"));
        closedTime.setCellValueFactory(new PropertyValueFactory<>("LastUpdatedDate"));
        receipt2.setCellValueFactory(new PropertyValueFactory<>("Document_Receipt"));
        xml2.setCellValueFactory(new PropertyValueFactory<>("XMLType"));
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("Document_ID"));
        ssccORsgtin.setCellValueFactory(new PropertyValueFactory<>("Document_Path"));

        table3.getSelectionModel().setCellSelectionEnabled(true);

        // пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅ TraceWay
        codeCountButton.setOnAction(event -> {
            traceWay.TraceWayRequest(table3, statement, codeCount);
                });


        // set JobStatus=4

        jobStatusButton.setOnAction(event -> {
            //status 4
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

            String update1;
            String queryText2 = jobN2.getText();
            if (checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                alert.setContentText("Incorrect password");
            }

            if (checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                update1 = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                        "SET [JobStatus] = 4\n" +
                        "     WHERE  JID=" + "'" + queryText2 + "'";
                try {
                    int k=statement.executeUpdate(update1);

                    if (k>0)
                        alert.setContentText("Successful");
                    else
                        alert.setContentText("Incorrect value");
                } catch (SQLException throwables) {
                    alert.setContentText("Error");
                }
                checkBox4.setSelected(false);
                passField.setText("");
            }
            alert.showAndWait();

        });


        jobStatusButton12.setOnAction(event -> {
            //status 12
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

            String update1;
            String queryText2 = jobN2.getText();
            if (checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                alert.setContentText("Incorrect password");
            }

            if (checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                update1 = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                        "SET [JobStatus] = 12\n" +
                        "     WHERE  JID=" + "'" + queryText2 + "'";
                try {
                    int k=statement.executeUpdate(update1);

                    if (k>0)
                        alert.setContentText("Successful");
                    else
                        alert.setContentText("Incorrect value");
                } catch (SQLException throwables) {
                    alert.setContentText("Error");
                }
                checkBox4.setSelected(false);
                passField.setText("");
            }
            alert.showAndWait();

        });

        jobStatusButton16.setOnAction(event -> {
            //status 16
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

            String update1;
            String queryText2 = jobN2.getText();
            if (checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                alert.setContentText("Incorrect password");
            }

            if (checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                update1 = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                        "SET [JobStatus] = 16\n" +
                        "     WHERE  JID=" + "'" + queryText2 + "'";
                try {
                    int k=statement.executeUpdate(update1);

                    if (k>0)
                        alert.setContentText("Successful");
                    else
                        alert.setContentText("Incorrect value");
                } catch (SQLException throwables) {
                    alert.setContentText("Error");
                }
                checkBox4.setSelected(false);
                passField.setText("");
            }
            alert.showAndWait();

        });

        // set report status =7

        authSignInButton3.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

            String update1;
            String queryText2 = jobN2.getText();
            String xmlType=boxCombo.getValue();
            if (!xmlType.equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ") && checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                alert.setContentText("Incorrect password");
            }

            if (!xmlType.equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ") && checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                update1 = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                        "SET [TransactionStatus] = 7\n" +
                        "     WHERE XMLType=" + "'" + xmlType +"'"+ " AND JobID=" + "'" + queryText2 + "'";
                try {
                    int k=statement.executeUpdate(update1);
                    if (k>0)
                        alert.setContentText("Successful");
                    else
                        alert.setContentText("Incorrect value");
                } catch (SQLException throwables) {
                    alert.setContentText("Error");
                }
                checkBox4.setSelected(false);
                passField.setText("");
            }
            alert.showAndWait();

        });

        // пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
        authSignInButton2.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

                     String update1;
            String queryText2 = jobN2.getText();
            String xmlType=boxCombo.getValue();
            if (!xmlType.equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ") && checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                alert.setContentText("Incorrect password");
            }
            if (!xmlType.equals("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ") && checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobN2.getText().equals("")) {
                update1 = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                        "SET \n" +
                        "       [Document_GUID] = NULL\n" +
                        "      ,[Code] = NULL\n" +
                        "      ,[Token] = NULL\n" +
                        "      ,[Document_ID] = NULL\n" +
                        "      ,[Document_Link] = NULL\n" +
                        "      ,[Document_Receipt_Link] = NULL\n" +
                        "      ,[Document_Receipt] = NULL\n" +
                        "      ,[TransactionStatus] = 0\n" +
                        "      ,[CreatedDate] = NULL\n" +
                        "      ,[UpdatedDate] = NULL\n" +
                        "     WHERE XMLType=" + "'" + xmlType +"'"+ " AND JobID=" + "'" + queryText2 + "'";
                try {
                 int k=statement.executeUpdate(update1);
                 if (k>0)
                 alert.setContentText("Successful");
                 else
                     alert.setContentText("Incorrect value");
                } catch (SQLException throwables) {
                    alert.setContentText("Error");
                }


                checkBox4.setSelected(false);
                passField.setText("");
            }
            alert.showAndWait();
                });


        authSignInButton.setOnAction(event -> {
try {
    try {
        jobN.setText(table3.getSelectionModel().getSelectedItem().getJobName());
        jobN2.setText(String.valueOf(table3.getSelectionModel().getSelectedItem().getJID()));
        jobID4.setText("");
      } catch (Exception e) {
    }

    table.getItems().clear();
    table2.getItems().clear();
    table3.getItems().clear();

    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    ResultSet resultSet3 = null;
    ResultSet resultSet4 = null;
    String jobNameQuery = jobN.getText();
    String jodIDquery =jobID4.getText();
    String batchNo =batchNoField.getText();
    data = FXCollections.observableArrayList();
    String selectSql="";

// search by JobID
if (!jodIDquery.equals("")){
    selectSql = "SELECT * FROM [rT2_0ER02_Server].[dbo].[tbl_Job] where [JID]=" + "'" + jodIDquery + "'";
    jobN.setText("");
    batchNoField.setText("");
    jobN2.setText(jodIDquery);
   }

// search by JobName
else {    selectSql = "SELECT * FROM [rT2_0ER02_Server].[dbo].[tbl_Job] where [JobName]=" + "'" + jobNameQuery + "'";

}

    resultSet = statement.executeQuery(selectSql);

    String jobId = null;
    while (resultSet.next()) {
        jobId = resultSet.getString("JID");
        data.add(new ModelTable(resultSet.getString("JID"), resultSet.getString("JobName"),
                whatJobStatus(resultSet.getString("JobStatus")), whatDataTransferStatus(resultSet.getString("DataTransferStage"))));

        ;
    }

    table.setItems(data);
    String selectSql2 = "SELECT * FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog] where [JobID]=" + jobId;
    resultSet2 = statement.executeQuery(selectSql2);

    while (resultSet2.next()) {
        data2.add(new ModelTable2(resultSet2.getString("JobID"), whatTransactionStatus(resultSet2.getString("TransactionStatus")),
                resultSet2.getString("Document_Receipt"), whatXMLType(resultSet2.getString("XMLType")),resultSet2.getString("UpdatedDate"),
                resultSet2.getString("Document_ID"), resultSet2.getString("Document_Path") ));
    }
    table2.setItems(data2);
    LocalDate value = LocalDate.now();
    value = datePicker.getValue();
    String pattern = "yyyy, MM, dd";
    DateTimeFormatter dtFormatter;
    String date2 = null;
    dtFormatter = DateTimeFormatter.ofPattern(pattern);
    if (value != null)
        date2 = dtFormatter.format(value);

    String selectSql3;

    //пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
    if (checkBox.isSelected() && checkBox2.isSelected() && batchNo.equals("")) {
        receipt2.setCellValueFactory(new PropertyValueFactory<>("Document_Receipt"));
        receipt2.setText("Document_Receipt");
        xml2.setCellValueFactory(new PropertyValueFactory<>("XMLType"));
        xml2.setText("XMLType");
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("Document_ID"));
        docIDcol.setText("Document_ID");
        selectSql3 = "SELECT TOP  (300) [JID], [JobName], LastUpdatedDate, [Document_Receipt], [XMLType], [Document_ID],[Document_Path]\n" +
                "\t  FROM [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
                "\t  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                "\t  ON [rT2_0ER02_Server].[dbo].[tbl_Job].JID=[rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].JobID\n" +
                "\t  WHERE  DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >6 and  [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "\t  and [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].TransactionStatus!=7 " +
                "and [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate>DATEFROMPARTS (" + date2 + ") order by UpdatedDate desc";
        resultSet3 = statement.executeQuery(selectSql3);
        while (resultSet3.next()) {

            data3.add(new ModelTable3(resultSet3.getInt("JID"), resultSet3.getString("JobName"),
                    resultSet3.getString("LastUpdatedDate"), resultSet3.getString("Document_Receipt"),
                    whatXMLType(resultSet3.getString("XMLType") ), resultSet3.getString("Document_ID"), resultSet3.getString("Document_Path")));

        }

 //пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
    } else if (checkBox2.isSelected() && batchNo.equals("")) {
        receipt2.setCellValueFactory(new PropertyValueFactory<>("Document_Receipt"));
        receipt2.setText("Document_Receipt");
        xml2.setCellValueFactory(new PropertyValueFactory<>("XMLType"));
        xml2.setText("XMLType");
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("Document_ID"));
        docIDcol.setText("Document_ID");

        selectSql3 = "SELECT TOP  (300) [JID], [JobName], LastUpdatedDate, [Document_Receipt], [XMLType],[Document_ID],[Document_Path] \n" +
                "\t  FROM [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
                "\t  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                "\t  ON [rT2_0ER02_Server].[dbo].[tbl_Job].JID=[rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].JobID\n" +
                "\t  WHERE  DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >6 and  [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "\t  and [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].TransactionStatus!=7 order by UpdatedDate desc";
        resultSet3 = statement.executeQuery(selectSql3);
        while (resultSet3.next()) {

            data3.add(new ModelTable3(resultSet3.getInt("JID"), resultSet3.getString("JobName"),
                    resultSet3.getString("LastUpdatedDate"), resultSet3.getString("Document_Receipt"),
                    whatXMLType(resultSet3.getString("XMLType")), resultSet3.getString("Document_ID"), resultSet3.getString("Document_Path")));

        }


//пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
    } else if (checkBox.isSelected() && batchNo.equals("")) {

        receipt2.setCellValueFactory(new PropertyValueFactory<>("Document_Receipt"));
        receipt2.setText("Document_Receipt");
        xml2.setCellValueFactory(new PropertyValueFactory<>("XMLType"));
        xml2.setText("XMLType");
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("Document_ID"));
        docIDcol.setText("Document_ID");
        selectSql3 = "SELECT TOP  (300) [JID], [JobName], LastUpdatedDate\n" +
                "\t  FROM [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
                "\t  WHERE  DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >6 and  [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "and [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate>DATEFROMPARTS (" + date2 + ") order by LastUpdatedDate desc";
        resultSet3 = statement.executeQuery(selectSql3);
        while (resultSet3.next()) {

            data3.add(new ModelTable3(resultSet3.getInt("JID"), resultSet3.getString("JobName"),
                    resultSet3.getString("LastUpdatedDate")));

        }
    }

    // search by BatchNo
    else if (!batchNo.equals("")) {
        receipt2.setCellValueFactory(new PropertyValueFactory<>("PRODUCT_NAME"));
        receipt2.setText("Product Name");
        xml2.setCellValueFactory(new PropertyValueFactory<>("GTIN"));
        xml2.setText("GTIN");
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("BatchNo"));
        docIDcol.setText("BatchNo");

        String selectSql4 = "SELECT JID,[rT2_0ER02_Server].[dbo].[tbl_Job].JobName, [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, \n" +
                "[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].PRODUCT_NAME, GTIN, [rT2_0ER02_Server].[dbo].[tbl_Job].BatchNo FROM [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                "                  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master]\n" +
                "                  ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
                "  LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster]\n" +
                "  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
                "   where [rT2_0ER02_Server].[dbo].[tbl_Job].BatchNo=" + "'" + batchNo + "'";

        jobN.setText("");
        jobID4.setText("");
        resultSet3 = statement.executeQuery(selectSql4);
        while (resultSet3.next()) {
            data3.add(new ModelTable3(resultSet3.getString("JobName"),resultSet3.getInt("JID"),
                    resultSet3.getString("LastUpdatedDate"),resultSet3.getString("PRODUCT_NAME")
                    ,resultSet3.getString("GTIN"),resultSet3.getString("BatchNo")));
        }}

    else {
        //search all closed jobs
        receipt2.setCellValueFactory(new PropertyValueFactory<>("PRODUCT_NAME"));
        receipt2.setText("Product Name");
        xml2.setCellValueFactory(new PropertyValueFactory<>("GTIN"));
        xml2.setText("GTIN");
        docIDcol.setCellValueFactory(new PropertyValueFactory<>("BatchNo"));
        docIDcol.setText("BatchNo");

        selectSql3 = "SELECT JID,[rT2_0ER02_Server].[dbo].[tbl_Job].JobName, [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, \n" +
                "[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].PRODUCT_NAME, GTIN, [rT2_0ER02_Server].[dbo].[tbl_Job].BatchNo FROM [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                "                  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master]\n" +
                "                  ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
                "  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster]\n" +
                "  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
                "                              WHERE   [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "                                  order by [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate desc";
        resultSet3 = statement.executeQuery(selectSql3);
        while (resultSet3.next()) {
            data3.add(new ModelTable3(resultSet3.getString("JobName"),resultSet3.getInt("JID"),
                    resultSet3.getString("LastUpdatedDate"),resultSet3.getString("PRODUCT_NAME")
                    ,resultSet3.getString("GTIN"),resultSet3.getString("BatchNo")));
        }

    }

    table3.setItems(data3);

} catch (Exception e){}

        });



    }

}