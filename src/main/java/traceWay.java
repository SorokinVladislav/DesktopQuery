import ModelTable.ModelTable3;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class traceWay {

    public static void TraceWayRequest(TableView<ModelTable3> table3, Statement statement, TextField codeCount ){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            List<String> indexes = new ArrayList<>();
            List<String> indexes2 = new ArrayList<>();
            try {
                String gtin="";
                String batchNum="";
                int jobIDCount=table3.getSelectionModel().getSelectedItem().getJID();
                ResultSet resultSet5;
                String token = codeCount.getText();
                String selectSql5 = "SELECT * FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster]\n" +
                        "  where JobID=" + "'" + jobIDCount + "'";

                resultSet5 = statement.executeQuery(selectSql5);

                while (resultSet5.next()) {
                    gtin = resultSet5.getString("GTIN");
                    batchNum = resultSet5.getString("BatchNo");
                }


//192.168.63.248
      
                String url="http://192.168.63.248/api/fns?expand=nomenclatures%2Cinvoice%2Cgtin" +
                        "2Cgtins%2CnewObject%2Cnewobject%2Cprev_operation_uid&gtin=" + gtin + "&operation_uid=1&page=1&per-" +
                        "page=100&series=" + batchNum + "&sort=-cdt&access-token=" +
                        token;

                URL obj = new URL(url);
                HttpURLConnection connection1 = (HttpURLConnection) obj.openConnection();

                connection1.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String json = response.toString();
                System.out.println(json);
                String word = "indcnt";


                String lowerCaseTextString = json.toLowerCase();
                String lowerCaseWord = word.toLowerCase();

                int index = 0;
                while (index != -1) {
                    index = lowerCaseTextString.indexOf(lowerCaseWord, index);
                    if (index != -1) {
                        String count = json.substring(index + 8, index + 14).replaceAll("\\D", "");
                        indexes.add(count);
                        index++;
                    }
                }



                String url2="http://192.168.63.248/api/fns?expand=nomenclatures%2Cinvoice%2Cgtin%2" +
                        "Cgtins%2CnewObject%2Cnewobject%2Cprev_operation_uid&gtin=" + gtin + "&operation_uid" +
                        "=6&page=1&per-page=100&series=" + batchNum + "&sort=-cdt&access-token=" +
                        token;
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
                System.out.println(json);
                String word2 = "codes_cnt";

                String lowerCaseTextString2 = json2.toLowerCase();
                String lowerCaseWord2 = word2.toLowerCase();

                int index2 = 0;
                while (index2 != -1) {
                    index2 = lowerCaseTextString2.indexOf(lowerCaseWord2, index2);
                    if (index2 != -1) {
                        String count2 = json2.substring(index2 + 8, index2 + 14).replaceAll("\\D", "");
                        indexes2.add(count2);
                        index2++;
                    }
                }

 
                alert.setTitle("Information");
                alert.setHeaderText("Code counter");

                System.out.println(indexes);
                System.out.println(indexes2);

            } catch (Exception k) {
                System.out.println("Error");
            }



            
            try {
                int jobIDCount = table3.getSelectionModel().getSelectedItem().getJID();
                ResultSet resultSet4 = null;
                String selectSql5 = "SELECT COUNT(*) as Count  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
                        "  where JobID=" + jobIDCount + " and IsAggregated=1 and Pkg_Level='SEC'\n";

                resultSet4 = statement.executeQuery(selectSql5);
                String codes="";
                String sampling="";
                for (String str: indexes)
                    codes=codes+str+" ";
                for (String str: indexes2)
                    sampling=sampling+str+" ";


                while (true) {

                    if (!resultSet4.next()) break;
                    alert.setContentText("Condot: "+resultSet4.getString("Count")+"\n"+"TraceWay: "+codes+"\n"+"Sampling: "+
                            sampling);
                }
            }catch (Exception e){}
            alert.showAndWait();

        };


    }



