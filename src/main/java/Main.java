import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public  class Main extends Application  {



  @Override
    public void start(Stage primaryStage) throws Exception, RuntimeException{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SQL Query");
        primaryStage.setScene(new Scene(root, 1429, 819));
        primaryStage.show();
    }

  /* @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        primaryStage.setTitle("Excel filler");
        primaryStage.setScene(new Scene(root, 271, 109));
        primaryStage.show();
    }
*/
    public static void main(String[] args) throws NullPointerException, Exception, RuntimeException {


       launch(args);

        //telegram-bot
      //  telegramBot.startBot();

    }}

