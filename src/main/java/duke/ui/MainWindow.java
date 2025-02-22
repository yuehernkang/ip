package duke.ui;

import java.util.Objects;

import duke.Duke;
import duke.DukeException;
import duke.constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    //Image from
    //https://www.vecteezy.com/vector-art/3607599-baby-duck-cartoon-cute-isolated-illustrations
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/image.png"));
    //Image from
    //https://www.freepik.com/free-vector/yellow-rubber-duck-flat-design_5336906.htm#query=duck&position=37&from_view=search
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/image2.png"));

    /**
     * Initializes the GUI
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String response = Constants.WELCOME_MESSAGE;
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(response, dukeImage)
        );
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        //Check if user input is empty
        assert !Objects.equals(userInput.getText(), "") : "User input is null";
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
