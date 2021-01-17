import ModelTable.DBConnection;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class telegramBot {

    static Statement statement= DBConnection.getDBConnection();
    static HashSet<String> set = new HashSet<>();

   public static void startBot (){

        // ��� ��������
        String TOKEN ="1328276244:AAGtH38tX7J-NTmZInmMHoeWjIxBSHw2sQY";
        //��� ������
        //  String TOKEN ="1281488461:AAGPNoQucbNHZUAWyv8YsVuUR8gJe3Xmmfo";

        TelegramBot bot= new TelegramBot(TOKEN);

            int counter1=1;
            int counter2=1;

            //  "-1001395495263"
            //   691460172
           while (true) {

                String ss ="Null";
                try {
                    ss = query();

                } catch (SQLException throwables) {ss ="������ ������� " +counter1;}
                if (set.add(ss)) {
                    bot.execute(new SendMessage("-1001395495263", ss+" "+counter2));
                    counter2++;
                }
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException e) {}
            }


}


    public static String query() throws SQLException {
//String col="XMLType";
        String col="UpdatedDate";
        String selectSql  = "SELECT  TOP  (1) [JID], [JobName], LastUpdatedDate, [Document_Receipt], [XMLType],  BatchNo \n" +
                "\t  FROM [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
                "\t  INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
                "\t  ON [rT2_0ER02_Server].[dbo].[tbl_Job].JID=[rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].JobID\n" +
                "\t  WHERE  DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >2 and  [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "\t  and [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].TransactionStatus!=7  order by "+col+ " desc";
        ResultSet resultSet = statement.executeQuery(selectSql);
        while (resultSet.next()) {
            String lut=resultSet.getString("LastUpdatedDate");
            String  mes=("����� ������ - "+resultSet.getString("JID")+"\n"+
                    "����� ������ - "+resultSet.getString("BatchNo")+"\n"+resultSet.getString("JobName")+"\n"+
                    "�������� ������ - "+lut.substring(0, lut.length() - 6)
                    +"\n"+whatXMLType(resultSet.getString("XMLType")) +"\n"+ whatReceipt(resultSet.getString("Document_Receipt")));
            return mes;
        }
        return "";
    }

    public static String whatXMLType(String st){
        if (st.equals("213"))
            return st +" - ������������ SSCC";
        else if (st.equals("2132"))
            return st +" - ������ ������������ SSCC";
        else if (st.equals("431"))
            return st +" - �����������";
        else if (st.equals("915") || st.equals("9151") || st.equals("9152"))
            return st +" - ��������� �������������";
        else if (st.equals("552"))
            return st +" - ����� �� �������";
        else if (st.equals("911")|| st.equals("9111") || st.equals("9112"))
            return st +" - �������������";
        else return st;
    }
    public static String whatReceipt(String document_Receipt){
        if (document_Receipt==null)
            return "Empty";

        else if (document_Receipt.contains("<error_desc>")) {
            String[] a=null;
            String[] receiptString=null;
            a = document_Receipt.split("<error_desc>");
            receiptString = a[1].split("</");
            return receiptString[0]  ;
        }
        else {
            String[] a = null;
            String[] receiptString = null;
            a = document_Receipt.split("<operation_comment>");
            receiptString = a[1].split("</");
            return receiptString[0];
        }


}}
