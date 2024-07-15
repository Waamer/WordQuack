
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class AppController {

    @FXML
    private Button startGameButton;


    public void clickStartGameButton() throws  Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage window  = (Stage) startGameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}





