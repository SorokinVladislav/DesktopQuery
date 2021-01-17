import ModelTable.DBConnection;
import ModelTable.ModelTable3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class getClosedJob {

    static Statement statement= DBConnection.getDBConnection();
    static ResultSet resultSet3 = null;
    static String selectSql3="";

    public  static List<ModelTable3> excelFill() throws SQLException {
        List<ModelTable3> setClosedJOb = new ArrayList<>();

        selectSql3 = "SELECT TOP (3) *\n" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                "   INNER JOIN [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master]\n" +
                "   ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
                "                WHERE   [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
                "                  order by [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate desc";
        resultSet3 = statement.executeQuery(selectSql3);
        while (resultSet3.next()) {
            setClosedJOb.add(new ModelTable3(resultSet3.getInt("JID"), resultSet3.getString("JobName"),
                    resultSet3.getString("LastUpdatedDate"),resultSet3.getString("PRODUCT_NAME"),
                    resultSet3.getString("BatchNo"),  resultSet3.getString("Product_Code")  ));
        }
        return setClosedJOb;

    }
}
