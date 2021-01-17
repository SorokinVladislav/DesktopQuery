import ModelTable.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class resend9151 {
    static Statement statement= DBConnection.getDBConnection();
    static ResultSet resultSet3 = null;
    static String selectSql3="";

    public  static void resend915(String jobID, CheckBox checkBox4, PasswordField passField) throws SQLException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        String delete;
        String update;
        String update2;

        if (checkBox4.isSelected() &&  !passField.getText().equals("Gr0m21$h0w") && !jobID.equals("")) {
            alert.setContentText("Incorrect password");
        }

        if (checkBox4.isSelected() &&  passField.getText().equals("Gr0m21$h0w") && !jobID.equals("")) {
            delete = "DELETE FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DataUpload]\n" +
                    "WHERE JobID=" + "'" + jobID + "'";

            update ="UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
                    "  SET DataTransferStage=4 WHERE JID=" + "'" + jobID + "'";
            int l=statement.executeUpdate(delete);
            int k=statement.executeUpdate(update);
            Thread.sleep(10000);

            update2 ="UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                    "                        SET \n" +
                    "                         [Document_GUID] = NULL\n" +
                    "                             ,[Code] = NULL\n" +
                    ", Signed_Code_Path = NULL, Signed_Document_Path = NULL, Base64_Document_Path = NULL\n" +
                    "                             ,[Token] = NULL\n" +
                    "                              ,[Document_ID] = NULL\n" +
                    "                              ,[Document_Link] = NULL\n" +
                    "                              ,[Document_Receipt_Link] = NULL\n" +
                    "                              ,[Document_Receipt] = NULL\n" +
                    "                              ,[TransactionStatus] = 0\n" +
                    "                             WHERE XMLType='9151' AND JobID=" + "'" + jobID + "'";
            int m=statement.executeUpdate(update2);
        if (m>0)
                    alert.setContentText("Successful");
                else
                    alert.setContentText("Error");
            checkBox4.setSelected(false);
            passField.setText("");

        }
        alert.showAndWait();

    }
}
