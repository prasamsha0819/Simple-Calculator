import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {

    private StringBuilder currentExpression = new StringBuilder();

    @Override
    public void start(Stage primaryStage) {

        TextField displayField = new TextField();
        displayField.setPromptText("Enter number...");
        displayField.setPrefHeight(50);
        displayField.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-background-color: #ffe6f0;" + 
            "-fx-border-width: 2px;"
        );
        displayField.setEditable(false);

        Button addButton = new Button("+");
        Button subtractButton = new Button("-");
        Button equalsButton = new Button("=");
        Button clearButton = new Button("C");

        Button[] operatorButtons = {addButton, subtractButton, equalsButton, clearButton};
        for (Button btn : operatorButtons) {
            btn.setPrefSize(60, 60);
            btn.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-background-color: #ffb6c1;" +
                "-fx-text-fill: white;" +
                "-fx-border-color: #ff66b2;" +
                "-fx-border-width: 1px;"
            );
            
        }

        
        
        Button[] numberButtons = new Button[10];
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new Button(String.valueOf(i));
            numberButtons[i].setPrefSize(60, 60);
            numberButtons[i].setStyle(
                "-fx-font-size: 20px;" +
                "-fx-background-color: #ffc0cb;" + // a little different pink
                "-fx-text-fill: white;" +
                "-fx-border-color: #ff66b2;" +
                "-fx-border-width: 1px;"
            );

            int num = i; // must be final for lambda
            numberButtons[i].setOnAction(e -> {
                currentExpression.append(num);
                displayField.setText(currentExpression.toString());
            });
            
        }

        
        addButton.setOnAction(e -> handleOperator("+", displayField));
        subtractButton.setOnAction(e -> handleOperator("-", displayField));
        equalsButton.setOnAction(e -> evaluate(displayField));
        clearButton.setOnAction(e -> {
            currentExpression.setLength(0);
            displayField.setText("");
        });

        
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPadding(new Insets(10));
        buttonGrid.setAlignment(Pos.CENTER);


        
        buttonGrid.add(numberButtons[7], 0, 0);
        buttonGrid.add(numberButtons[8], 1, 0);
        buttonGrid.add(numberButtons[9], 2, 0);
        buttonGrid.add(addButton, 3, 0);

        buttonGrid.add(numberButtons[4], 0, 1);
        buttonGrid.add(numberButtons[5], 1, 1);
        buttonGrid.add(numberButtons[6], 2, 1);
        buttonGrid.add(subtractButton, 3, 1);

        buttonGrid.add(numberButtons[1], 0, 2);
        buttonGrid.add(numberButtons[2], 1, 2);
        buttonGrid.add(numberButtons[3], 2, 2);
        buttonGrid.add(equalsButton, 3, 2);

        buttonGrid.add(numberButtons[0], 1, 3);
        buttonGrid.add(clearButton, 3, 3);

        VBox root = new VBox(20);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #fff0f5;");
        root.getChildren().addAll(displayField, buttonGrid);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOperator(String op, TextField displayField) {
        if (currentExpression.length() == 0) return;

        char lastChar = currentExpression.charAt(currentExpression.length() - 1);
        if (lastChar == '+' || lastChar == '-') {
            currentExpression.setCharAt(currentExpression.length() - 1, op.charAt(0));
        } else {
            currentExpression.append(op);
        }
        displayField.setText(currentExpression.toString());
    }

    private void evaluate(TextField displayField) {
        try {
            String[] parts = currentExpression.toString().split("(?<=\\d)(?=[+-])|(?<=[+-])(?=\\d)");
            int result = Integer.parseInt(parts[0]);

            for (int i = 1; i < parts.length; i += 2) {
                String operator = parts[i];
                int operand = Integer.parseInt(parts[i + 1]);

                if (operator.equals("+")) {
                    result += operand;
                } else if (operator.equals("-")) {
                    result -= operand;
                }
            }

            displayField.setText(String.valueOf(result));
            currentExpression.setLength(0);
            currentExpression.append(result); 
        } catch (Exception ex) {
            showAlert("Error", "Invalid expression!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
