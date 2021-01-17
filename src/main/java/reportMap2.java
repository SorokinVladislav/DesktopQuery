import ModelTable.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
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


public class reportMap2 {



    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    static Statement statement = DBConnection.getDBConnection();
    static ResultSet resultSet3 = null;
    static String selectSql3 = "";
    static File file;


    public static void mapReport(String jobID, String jobName,AnchorPane anchorPaneReport) throws SQLException, IOException, NullPointerException {

        file = new File("\\\\backup\\share\\CondotReports\\MapReport_" + jobName + ".xlsx");
        List<reportMapClass> listAllGoods = new ArrayList<>();


        selectSql3 = "SELECT  [JobID]\n" +
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
        System.out.println(selectSql3);
        resultSet3 = statement.executeQuery(selectSql3);
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

        XSSFWorkbook workbook = new XSSFWorkbook();


        XSSFSheet sheet = workbook.createSheet("Report map");


        int rownum = 1;
        Cell cell;
        Row row;

        for (reportMapClass ter : allTER) {

            rownum = sheet.getLastRowNum() + 1;
            row = sheet.createRow(rownum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(ter.getUIDCode());

            if (allSEC3.size() == 0) {
                for (reportMapClass sec : allSEC) {

                    if (sec.getNextLevel_UIDCode().equals(ter.getUIDCode()) && sec.getIsAggregated().equals("1")) {
                        rownum = sheet.getLastRowNum() + 1;
                        row = sheet.createRow(rownum);
                        cell = row.createCell(2, CellType.STRING);
                        cell.setCellValue(sec.getUIDCode());
                    }

                }
            } else {


                for (reportMapClass sec3 : allSEC3) {

                    try {
                        if (sec3.getNextLevel_UIDCode().equals(ter.getUIDCode()) && sec3.getIsAggregated().equals("1")) {
                            rownum = sheet.getLastRowNum() + 1;
                            row = sheet.createRow(rownum);
                            cell = row.createCell(1, CellType.STRING);
                            cell.setCellValue(sec3.getUIDCode());

                            for (reportMapClass sec : allSEC) {

                                if (sec.getNextLevel_UIDCode().equals(sec3.getUIDCode()) && sec.getIsAggregated().equals("1")) {
                                    rownum = sheet.getLastRowNum() + 1;
                                    row = sheet.createRow(rownum);
                                    cell = row.createCell(3, CellType.STRING);
                                    cell.setCellValue(sec.getUIDCode());
                                }

                            }


                        }
                    } catch (Exception e) {
                    }

                }
            }
        }
        try {
            file.getParentFile().mkdirs();

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Updated file: " + file.getAbsolutePath());
            outFile.flush();
            outFile.close();
            workbook.close();
            anchorPaneReport.visibleProperty().set(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Create file: " + file.getAbsolutePath());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void detailedJob(String jobID, String jobName, AnchorPane anchorPaneReport) throws SQLException, IOException, NullPointerException, InterruptedException {

        long m = System.currentTimeMillis();
        System.out.println((double) (System.currentTimeMillis() - m));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        file = new File("\\\\backup\\share\\CondotReports\\DetailedJob_" + jobName + ".xlsx");
        System.out.println((double) (System.currentTimeMillis() - m));
        List<reportMapClass> listAllGoods = new ArrayList<>();

        System.out.println((double) (System.currentTimeMillis() - m));
        selectSql3 = "SELECT  [JobID]\n" +
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
        System.out.println(selectSql3);
        resultSet3 = statement.executeQuery(selectSql3);


        System.out.println((double) (System.currentTimeMillis() - m));


        while (resultSet3.next()) {
            if (resultSet3.getString("Pkg_Level").equals("TER")){

            }


            listAllGoods.add(new reportMapClass(resultSet3.getString("JobID"), resultSet3.getString("Pkg_Level"),
                    resultSet3.getString("UIDCode"), resultSet3.getString("IsPrinted"),
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

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style = createStyleForTitle(workbook);

        int rownum = 1;
        Cell cell;
        Row row;


        //ter report
        if (allTER.size() > 0) {
            XSSFSheet sheet2 = workbook.createSheet("Detailed Job (Ter)");


            row = sheet2.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Printed and Accepted");
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

            int k1 = 2;
            int k2 = 2;
            int k3 = 2;
            int k4 = 2;

            for (reportMapClass ter : allTER) {

// printed
                if (ter.getIsPrinted().equals("1") && ter.getIsInspected().equals("1") && ter.getIsRejected().equals("0") && ter.getIsSampled().equals("0")) {

                    if (sheet2.getRow(k1) == null)
                        row = sheet2.createRow(k1);
                    else
                        row = sheet2.getRow(k1);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k1++;
                }

                //rejected
                if (ter.getIsPrinted().equals("1") && ter.getIsRejected().equals("1")) {
                    if (sheet2.getRow(k2) == null)
                        row = sheet2.createRow(k2);
                    else
                        row = sheet2.getRow(k2);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k2++;

                }


                if (ter.getIsPrinted().equals("1") && ter.getIsSampled().equals("1")) {
                    if (sheet2.getRow(k3) == null)
                        row = sheet2.createRow(k3);
                    else
                        row = sheet2.getRow(k3);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k3++;

                }

                if (ter.getIsPrinted().equals("0")) {
                    if (sheet2.getRow(k4) == null)
                        row = sheet2.createRow(k4);
                    else
                        row = sheet2.getRow(k4);
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k4++;

                }


            }

            row = sheet2.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(k1 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(k2 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(k3 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(k4 - 2);
            cell.setCellStyle(style);


        }

        System.out.println((double) (System.currentTimeMillis() - m));


        //sec-3 report


        if (allSEC3.size() > 0) {
            XSSFSheet sheet3 = workbook.createSheet("Detail Job (Sec-3)");
            row = sheet3.createRow(0);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Printed");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Accepted");
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Aggregated");
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Printed and Rejected");
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue("Sampled UID");
            cell.setCellStyle(style);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue("Unused UID");
            cell.setCellStyle(style);

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue("Accepted & NOT Aggregated");
            cell.setCellStyle(style);

            cell = row.createCell(21, CellType.STRING);
            cell.setCellValue("All Secondary-3");
            cell.setCellStyle(style);

            int k1 = 2;
            int k2 = 2;
            int k3 = 2;
            int k4 = 2;
            int k5 = 2;
            int k6 = 2;
            int k7 = 2;
            int k8 = 2;

            for (reportMapClass ter : allSEC3) {

//printed
                if (ter.getIsPrinted().equals("1")) {
                    if (sheet3.getRow(k1) == null)
                        row = sheet3.createRow(k1);
                    else
                        row = sheet3.getRow(k1);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k1++;
                }

//accepted
                if (ter.getIsInspected().equals("1")) {

                    if (sheet3.getRow(k2) == null)
                        row = sheet3.createRow(k2);
                    else
                        row = sheet3.getRow(k2);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k2++;

                }
//aggregated
                if (ter.getIsAggregated().equals("1")) {

                    if (sheet3.getRow(k3) == null)
                        row = sheet3.createRow(k3);
                    else
                        row = sheet3.getRow(k3);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k3++;

                }

//rejected
                if (ter.getIsPrinted().equals("1") && ter.getIsRejected().equals("1")) {

                    if (sheet3.getRow(k4) == null)
                        row = sheet3.createRow(k4);
                    else
                        row = sheet3.getRow(k4);
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k4++;

                }
//sampled
                if (ter.getIsPrinted().equals("1") && ter.getIsSampled().equals("1")) {

                    if (sheet3.getRow(k5) == null)
                        row = sheet3.createRow(k5);
                    else
                        row = sheet3.getRow(k5);
                    cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k5++;

                }
//unused
                if (ter.getIsPrinted().equals("0")) {

                    if (sheet3.getRow(k6) == null)
                        row = sheet3.createRow(k6);
                    else
                        row = sheet3.getRow(k6);
                    cell = row.createCell(15, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k6++;

                }
                // accepted and not aggregated
                if (ter.getIsPrinted().equals("1") && ter.getIsInspected().equals("1") && ter.getIsAggregated().equals("0") && ter.getIsRejected().equals("0") && ter.getIsSampled().equals("0")) {

                    if (sheet3.getRow(k7) == null)
                        row = sheet3.createRow(k7);
                    else
                        row = sheet3.getRow(k7);
                    cell = row.createCell(18, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k7++;

                }

            }

            row = sheet3.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(k1 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(k2 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(k3 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(k4 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(k5 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(k6 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(k7 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(21, CellType.STRING);
            cell.setCellValue(allSEC3.size());
            cell.setCellStyle(style);


        }

        System.out.println((double) (System.currentTimeMillis() - m));


        //sec report

        if (allSEC.size() > 0) {



            XSSFSheet sheet4 = workbook.createSheet("Detail Job (Sec)");

            row = sheet4.createRow(0);


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
            cell.setCellValue("All Secondary");
            cell.setCellStyle(style);

            int k3 = 2;
            int k4 = 2;
            int k5 = 2;
            int k6 = 2;
            int k7 = 2;

            for (reportMapClass ter : allSEC) {

//aggregated
                if (ter.getIsAggregated().equals("1")) {

                    if (sheet4.getRow(k3) == null)
                        row = sheet4.createRow(k3);
                    else
                        row = sheet4.getRow(k3);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k3++;

                }

//rejected
                if (ter.getIsPrinted().equals("1") && ter.getIsRejected().equals("1")) {

                    if (sheet4.getRow(k4) == null)
                        row = sheet4.createRow(k4);
                    else
                        row = sheet4.getRow(k4);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k4++;

                }
//sampled
                if (ter.getIsPrinted().equals("1") && ter.getIsSampled().equals("1")) {

                    if (sheet4.getRow(k5) == null)
                        row = sheet4.createRow(k5);
                    else
                        row = sheet4.getRow(k5);
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k5++;

                }
//unused
                if (ter.getIsPrinted().equals("0")) {

                    if (sheet4.getRow(k6) == null)
                        row = sheet4.createRow(k6);
                    else
                        row = sheet4.getRow(k6);
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k6++;

                }
                // accepted and not aggregated
                if (ter.getIsPrinted().equals("1") && ter.getIsInspected().equals("1") && ter.getIsAggregated().equals("0") && ter.getIsRejected().equals("0") && ter.getIsSampled().equals("0")) {

                    if (sheet4.getRow(k7) == null)
                        row = sheet4.createRow(k7);
                    else
                        row = sheet4.getRow(k7);
                    cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(ter.getUIDCode());
                    k7++;
                }

            }

            row = sheet4.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(k3 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(k4 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(k5 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(k6 - 2);
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(k7 - 2);
            cell.setCellStyle(style);


            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(allSEC.size());
            cell.setCellStyle(style);


        }


        try {
            System.out.println((double) (System.currentTimeMillis() - m));
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
            workbook.close();
            System.out.println((double) (System.currentTimeMillis() - m));
            alert.setContentText("Create file: " + file.getAbsolutePath());
            alert.showAndWait();


        } catch (Exception e) {
            alert.setContentText("To big file");
            alert.showAndWait();
        }
    }
}
