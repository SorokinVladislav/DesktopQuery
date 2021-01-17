import ModelTable.*;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reportMap {



    private  XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    static Statement statement = DBConnection.getDBConnection();
    static ResultSet resultSet3 = null;
    static String selectSql = "";
    // static File file = new File("C:\\Users\\vladislav.sorokin\\Desktop\\Closed jobs.xls");
    static File file;

    public static void mapReport(String jobID, String jobName) throws SQLException, IOException, NullPointerException {

        file = new File("\\\\backup\\share\\CondotReports\\MapReport_" + jobName + ".xlsx");
        List<reportMapClass> listAllGoods = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report map");

        selectSql = "SELECT  [JobID]\n" +
                "      ,[Pkg_Level]\n" +
                "      ,[UIDCode]\n" +
                "      ,[NextLevel_UIDCode]\n" +
                "      ,[Prod_Pkg_ID]\n" +
                "      ,[IsPrinted]\n" +
                "      ,[IsInspected]\n" +
                "      ,[IsRejected]\n" +
                "      ,[IsSampled]\n" +
                "      ,[IsAggregated]" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                "  where JobID=" + jobID + " order by NextLevel_UIDCode\n";

                resultSet3 = statement.executeQuery(selectSql);
        while (resultSet3.next()) {
            listAllGoods.add(new reportMapClass(resultSet3.getString("JobID"), resultSet3.getString("Pkg_Level"),
                    resultSet3.getString("NextLevel_UIDCode"), resultSet3.getString("UIDCode"), resultSet3.getString("IsPrinted"),
                    resultSet3.getString("IsInspected"), resultSet3.getString("IsRejected"), resultSet3.getString("IsSampled"),
                    resultSet3.getString("IsAggregated")));

        }

        List<reportMapClass> allTER = new ArrayList<>();
        List<reportMapClass> allSEC3 = new ArrayList<>();
        List<reportMapClass> allSEC = new ArrayList<>();


        for (reportMapClass list : listAllGoods) {
            if (list.getPkg_Level().equals("TER")) {
                allTER.add(list);
            }
            if (list.getPkg_Level().equals("IL3")) {
                allSEC3.add(list);
            }
            if (list.getPkg_Level().equals("SEC")) {
                allSEC.add(list);
            }
        }

        int rownum = 1;
        Cell cell;
        Row row;
        boolean b=false;

        for (reportMapClass ter : allTER) {

            rownum = sheet.getLastRowNum() + 1;
            row = sheet.createRow(rownum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(ter.getUIDCode());

            if (allSEC3.size() == 0) {
                for (reportMapClass sec : allSEC) {

                    if (sec.getNextLevel_UIDCode().equals(ter.getUIDCode())) {
                        if (!b) {
                            rownum = sheet.getLastRowNum() + 1;
                            row = sheet.createRow(rownum);
                            cell = row.createCell(3, CellType.STRING);
                            cell.setCellValue(sec.getUIDCode());
                            b=true;

                        }
                        else {
                            rownum = sheet.getLastRowNum();
                            row = sheet.getRow(rownum);
                            cell = row.createCell(6, CellType.STRING);
                            cell.setCellValue(sec.getUIDCode());
                            b=false;
                        }
                    }

                }
            } else {


                for (reportMapClass sec3 : allSEC3) {

                        if (sec3.getNextLevel_UIDCode().equals(ter.getUIDCode())) {
                            rownum = sheet.getLastRowNum() + 1;
                            row = sheet.createRow(rownum);
                            cell = row.createCell(3, CellType.STRING);
                            cell.setCellValue(sec3.getUIDCode());
                            b=false;
                            for (reportMapClass sec : allSEC) {

                                if (sec.getNextLevel_UIDCode().equals(sec3.getUIDCode())) {

                                    if (!b) {
                                        rownum = sheet.getLastRowNum() + 1;
                                        row = sheet.createRow(rownum);
                                        cell = row.createCell(6, CellType.STRING);
                                        cell.setCellValue(sec.getUIDCode());
                                        b=true;

                                    }
                                    else {
                                        rownum = sheet.getLastRowNum();
                                        row = sheet.getRow(rownum);
                                        cell = row.createCell(9, CellType.STRING);
                                        cell.setCellValue(sec.getUIDCode());
                                        b=false;
                                    }
                                }

                            }


                        }
                }
            }
        }
        try {
            file.getParentFile().mkdirs();
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            workbook.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Create file: " + file.getAbsolutePath());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void detailedJob(String jobID, String jobName) throws SQLException, IOException, NullPointerException, InterruptedException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        file = new File("\\\\backup\\share\\CondotReports\\DetailedJob_" + jobName + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style = createStyleForTitle(workbook);
        XSSFSheet sheet2 = workbook.createSheet("Detailed Job (Ter)");
        XSSFSheet sheet3 = workbook.createSheet("Detail Job (Sec-3)");
        XSSFSheet sheet4 = workbook.createSheet("Detail Job (Sec)");

        Cell cell;
        Row row;

        row = sheet4.createRow(0);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Printed and Rejected");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Sampled UID");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Unused UID");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Accepted & NOT Aggregated");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("All Secondary");
        cell.setCellStyle(style);

        int t1 = 2;int t2 = 2;int t4 = 2;
        int p3 = 2;int p4 = 2;int p5 = 2;int p6 = 2;int p7 = 2;int p8 = 2;
        int s2 = 2;int s3 = 2;int s4 = 2;int s5 = 2;int s6 = 2;int s7 = 2;int s8 = 2;
        boolean b = false;

// get Prod_Pkg_ID
        String selectSql1 = "SELECT [JID], [Prod_Pkg_ID]\n" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Job]\n" +
                "  where JID="+ jobID;
        resultSet3 = statement.executeQuery(selectSql1);
        String packageId="";
        while (resultSet3.next()) {
                packageId=resultSet3.getString("Prod_Pkg_ID");
        }

//get detailed Prod_Pkg_ID

        String selectSql2 = "SELECT [Prod_Pkg_ID], [Pkg_Level], [PCMapCount]\n" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Level_Details]\n" +
                "  where Prod_Pkg_ID="+packageId;
        resultSet3 = statement.executeQuery(selectSql2);

        row = sheet2.createRow(0);
        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Secondary-3");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Tertiary");
        cell.setCellStyle(style);

        while (resultSet3.next()) {
            if (resultSet3.getString("Pkg_Level").equals("IL3")){
                if (sheet2.getRow(1) == null)
                    row = sheet2.createRow(1);
                else
                    row = sheet2.getRow(1);

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(resultSet3.getString("PCMapCount"));
                cell.setCellStyle(style);
            }

            if (resultSet3.getString("Pkg_Level").equals("TER")){
                if (sheet2.getRow(1) == null)
                    row = sheet2.createRow(1);
                else
                    row = sheet2.getRow(1);
                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(resultSet3.getString("PCMapCount"));
                cell.setCellStyle(style);
            }
        }

        selectSql = "SELECT  [JobID]\n" +
                "      ,[Pkg_Level]\n" +
                "      ,[UIDCode]\n" +
                "      ,[NextLevel_UIDCode]\n" +
                "      ,[Prod_Pkg_ID]\n" +
                "      ,[IsPrinted]\n" +
                "      ,[IsInspected]\n" +
                "      ,[IsRejected]\n" +
                "      ,[IsSampled]\n" +
                "      ,[IsAggregated]" +
                "  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                "  where JobID=" + jobID + " order by NextLevel_UIDCode\n";

        resultSet3 = statement.executeQuery(selectSql);
        while (resultSet3.next()) {
      /*      listAllGoods.add(new reportMapClass(resultSet3.getString("JobID"), resultSet3.getString("Pkg_Level"),
                     resultSet3.getString("UIDCode"), resultSet3.getString("IsPrinted"),
                    resultSet3.getString("IsInspected"), resultSet3.getString("IsRejected"), resultSet3.getString("IsSampled"),
                    resultSet3.getString("IsAggregated")));*/

            if (resultSet3.getString("Pkg_Level").equals("TER")) {
// printed
                if ( resultSet3.getString("IsInspected").equals("1") && resultSet3.getString("IsSampled").equals("0")) {

                    if (sheet2.getRow(t1) == null)
                        row = sheet2.createRow(t1);
                    else
                        row = sheet2.getRow(t1);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue( resultSet3.getString("UIDCode"));
                    t1++;
                }
                //rejected
                if (resultSet3.getString("IsRejected").equals("1")) {
                    if (sheet2.getRow(t2) == null)
                        row = sheet2.createRow(t2);
                    else
                        row = sheet2.getRow(t2);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue( resultSet3.getString("UIDCode"));
                    t2++;
                }

                if (resultSet3.getString("IsPrinted").equals("0")) {
                    if (sheet2.getRow(t4) == null)
                        row = sheet2.createRow(t4);
                    else
                        row = sheet2.getRow(t4);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue( resultSet3.getString("UIDCode"));
                    t4++;

                }

            }


            if (resultSet3.getString("Pkg_Level").equals("IL3")) {

//aggregated
                if ( resultSet3.getString("IsAggregated").equals("1")) {

                    if (sheet3.getRow(p3) == null)
                        row = sheet3.createRow(p3);
                    else
                        row = sheet3.getRow(p3);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    p3++;

                }

//rejected
                if (resultSet3.getString("IsRejected").equals("1")) {

                    if (sheet3.getRow(p4) == null)
                        row = sheet3.createRow(p4);
                    else
                        row = sheet3.getRow(p4);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    p4++;

                }
//sampled
                if (resultSet3.getString("IsSampled").equals("1")) {

                    if (sheet3.getRow(p5) == null)
                        row = sheet3.createRow(p5);
                    else
                        row = sheet3.getRow(p5);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    p5++;

                }
//unused
                if (resultSet3.getString("IsPrinted").equals("0")) {

                    if (sheet3.getRow(p6) == null)
                        row = sheet3.createRow(p6);
                    else
                        row = sheet3.getRow(p6);
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    p6++;

                }
                // accepted and not aggregated
                if ( resultSet3.getString("IsInspected").equals("1") &&  resultSet3.getString("IsAggregated").equals("0") &&
                        resultSet3.getString("IsRejected").equals("0") && resultSet3.getString("IsSampled").equals("0")) {

                    if (sheet3.getRow(p7) == null)
                        row = sheet3.createRow(p7);
                    else
                        row = sheet3.getRow(p7);
                    cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    p7++;

                }
                if (sheet3.getRow(p8) == null)
                    row = sheet3.createRow(p8);
                else
                    row = sheet3.getRow(p8);
                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(resultSet3.getString("UIDCode"));
                p8++;

            }

            if (resultSet3.getString("Pkg_Level").equals("SEC")) {
                //sec report
                //aggregated
                if (  resultSet3.getString("IsAggregated").equals("1")) {
                    if (b==false) {
                        if (sheet4.getRow(s3) == null)
                            row = sheet4.createRow(s3);
                        else
                            row = sheet4.getRow(s3);
                        cell = row.createCell(0, CellType.STRING);
                        cell.setCellValue(resultSet3.getString("UIDCode"));
                        s3++;
                        b=true;
                        s2++;
                    }
                    else {
                        row = sheet4.getRow(s3-1);
                        cell = row.createCell(3, CellType.STRING);
                        cell.setCellValue(resultSet3.getString("UIDCode"));
                        b=false;
                        s2++;

                    }

                }
//rejected
                if (resultSet3.getString("IsRejected").equals("1")) {

                    if (sheet4.getRow(s4) == null)
                        row = sheet4.createRow(s4);
                    else
                        row = sheet4.getRow(s4);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    s4++;
                }
//sampled
                if (resultSet3.getString("IsSampled").equals("1")) {

                    if (sheet4.getRow(s5) == null)
                        row = sheet4.createRow(s5);
                    else
                        row = sheet4.getRow(s5);
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    s5++;
                }
//unused
                if (resultSet3.getString("IsPrinted").equals("0")) {

                    if (sheet4.getRow(s6) == null)
                        row = sheet4.createRow(s6);
                    else
                        row = sheet4.getRow(s6);
                    cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    s6++;

                }
                // accepted and not aggregated
                if (resultSet3.getString("IsInspected").equals("1") &&   resultSet3.getString("IsAggregated").equals("0")
                        && resultSet3.getString("IsRejected").equals("0") && resultSet3.getString("IsSampled").equals("0")) {

                    if (sheet4.getRow(s7) == null)
                        row = sheet4.createRow(s7);
                    else
                        row = sheet4.getRow(s7);
                    cell = row.createCell(15, CellType.STRING);
                    cell.setCellValue(resultSet3.getString("UIDCode"));
                    s7++;
                }

                s8++;


            }

        }

        //ter report
            row = sheet2.getRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Printed and Accepted");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Printed and Rejected");
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Unused UID");
            cell.setCellStyle(style);


            row = sheet2.getRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(t1 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(t2 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(t4 - 2);
            cell.setCellStyle(style);

        //sec-3 report

            row = sheet3.createRow(0);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Aggregated");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Printed and Rejected");
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Sampled UID");
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Unused UID");
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue("Accepted & NOT Aggregated");
            cell.setCellStyle(style);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue("All Secondary-3");
            cell.setCellStyle(style);

            row = sheet3.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(p3 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(p4 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(p5 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(p6 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(p7 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(p8 - 2);
            cell.setCellStyle(style);

            row = sheet4.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(s2 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(s4 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(s5 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(s6 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(s7 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(s8 - 2);
            cell.setCellStyle(style);

        try {
        	FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
       //     outFile.flush();
            outFile.close();
            workbook.close();
            alert.setContentText("Create file: " + file.getAbsolutePath());
            alert.showAndWait();
        } catch (Exception e) {
            alert.setContentText("To big file");
            alert.showAndWait();
        }
    }
}