import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ModelTable.DBConnection;
import ModelTable.ModelTable3;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelFiller {


   //  File file = new File("\\\\backup\\share\\Closed jobs.xls");



    public  void execute(String token) throws SQLException, IOException {
        File file = new File("C:\\Users\\vladislav.sorokin\\Desktop\\Closed jobs.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheet("Closed jobs");

        List<ModelTable3> setClosedJOb = getClosedJob.excelFill();
        int rownum;
        Cell cell;
        Row row;
        Set<String> list = new HashSet<>();
        XSSFCell cell2;
        System.out.println('2');
        // заполнение данными
        for (ModelTable3 emp : setClosedJOb) {

            //проверка совпадений
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                try {
                    list.add(sheet.getRow(i).getCell(0).toString().replace(".0", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String jid = Integer.toString(emp.getJID());

            if (!list.contains(jid)) {
                rownum = sheet.getLastRowNum() + 1;
                row = sheet.createRow(rownum);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(emp.getJID());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(emp.getJobName());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(emp.getLastUpdatedDate().substring(0, 16));

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(emp.getBatchNo());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(emp.getPRODUCT_NAME());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(emp.getGTIN());


                // запрос количества кодов в Condot
          try {
                int jobIDCount =emp.getJID();
                ResultSet resultSet4 = null;
                Statement statement= DBConnection.getDBConnection();
                    System.out.println(jobIDCount);
                String selectSql5 = "SELECT COUNT(*) as Count  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                        "  where JobID=" + jobIDCount + " and IsAggregated=1 and Pkg_Level='SEC'\n";

                resultSet4 = statement.executeQuery(selectSql5);
                while (true) {

                    if (!resultSet4.next()) break;
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(resultSet4.getString("Count"));

                }

            }catch (Exception e){    e.printStackTrace();}
            }
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            // 192.168.63.248
            try {
                String url2 = "http://93.190.230.34//api/fns?expand=nomenclatures%2Cinvoice%2Cgtin%2Cgtins%2CnewObject%" +
                        "2Cnewobject%2Cprev_operation_uid&gtin=" + sheet.getRow(i).getCell(5).toString().replace(".0", "") + "&operation" +
                        "_uid=1&page=1&per-page=100&series=" + sheet.getRow(i).getCell(3).toString().replace(".0", "") + "&sort" +
                        "=-cdt&access-token=" + token;
                URL obj2 = new URL(url2);
                HttpURLConnection connection2 = (HttpURLConnection) obj2.openConnection();
                connection2.setRequestMethod("GET");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();

                while ((inputLine2 = in2.readLine()) != null) {
                    response2.append(inputLine2);
                }
                in2.close();

                String json2 = response2.toString();
                System.out.println(json2);
                String word2 = "indcnt";
                String lowerCaseTextString2 = json2.toLowerCase();
                String lowerCaseWord2 = word2.toLowerCase();

                int index2 = 0;
                List<String> indexes2 = new ArrayList<>();
                while (index2 != -1) {
                    index2 = lowerCaseTextString2.indexOf(lowerCaseWord2, index2);
                    if (index2 != -1) {
                        String count2 = json2.substring(index2 + 8, index2 + 14).replaceAll("\\D", "");
                        System.out.println(count2);
                        indexes2.add(count2);
                        index2++;
                    }
                }
                String counter = "";
                int summ = 0;
                for (String count : indexes2) {
                    counter = counter + " " + count;
                    summ = summ + Integer.parseInt(count);
                }

                Row row3 = sheet.getRow(i);
                Cell cell3;
                cell3 = row3.createCell(7, CellType.STRING);

                cell3.setCellValue(summ + ": " + counter);


            } catch (Exception e) {
            }

            try {
                // 192.168.63.248
                String url2 = "http://93.190.230.34//api/fns?expand=nomenclatures%2Cinvoice%2Cgtin%2" +
                        "Cgtins%2CnewObject%2Cnewobject%2Cprev_operation_uid&gtin=" + sheet.getRow(i).getCell(5).toString().replace(".0", "") + "&operation_uid=6&page=1&per-page=100&series=" + sheet.getRow(i).getCell(3).toString().replace(".0", "") + "&sort" +
                        "=-cdt&access-token=" + token;
                URL obj2 = new URL(url2);
                HttpURLConnection connection2 = (HttpURLConnection) obj2.openConnection();
                connection2.setRequestMethod("GET");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();

                while ((inputLine2 = in2.readLine()) != null) {
                    response2.append(inputLine2);
                }
                in2.close();

                String json2 = response2.toString();
                String word2 = "codes_cnt";

                String lowerCaseTextString2 = json2.toLowerCase();
                String lowerCaseWord2 = word2.toLowerCase();

                int index2 = 0;
                List<String> indexes2 = new ArrayList<>();
                while (index2 != -1) {
                    index2 = lowerCaseTextString2.indexOf(lowerCaseWord2, index2);
                    if (index2 != -1) {
                        String count2 = json2.substring(index2 + 8, index2 + 14).replaceAll("\\D", "");
                        indexes2.add(count2);
                        index2++;
                    }
                }
                String counter = "";
                int summ = 0;
                for (String count : indexes2) {
                    counter = counter + " " + count;
                    summ = summ + Integer.parseInt(count);
                }

                Row row3 = sheet.getRow(i);
                Cell cell3;
                cell3 = row3.createCell(8, CellType.STRING);

                cell3.setCellValue(summ);


            } catch (Exception e) {

            }



        }

        try {
            file.getParentFile().mkdirs();

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            System.out.println("Updated file: " + file.getAbsolutePath());
            outFile.flush();
            outFile.close();
        }catch (Exception e){e.printStackTrace();}

        }

    }