package com.expensetracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import java.sql.Date;
import java.util.List;

public class ExpenseTrackerApp extends Application {

    TableView<Expense> table = new TableView<>();
    ObservableList<Expense> data = FXCollections.observableArrayList();

    public void start(Stage primaryStage) {

        Label titleLabel = new Label("💸 Expense Tracker");
        titleLabel.setFont(new Font("Arial", 26));
        titleLabel.setStyle("-fx-text-fill: #333333;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Food", "Travel", "Shopping", "Bills");
        categoryBox.setPromptText("Select category");

        TextArea noteArea = new TextArea();
        noteArea.setPromptText("Enter notes...");
        noteArea.setPrefRowCount(3);

        DatePicker datePicker = new DatePicker();

        Button addBtn = new Button("➕ Add Expense");
        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox formBox = new VBox(10,
                titleLabel,
                new Label("Amount:"), amountField,
                new Label("Category:"), categoryBox,
                new Label("Note:"), noteArea,
                new Label("Date:"), datePicker,
                addBtn
        );
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        formBox.setAlignment(Pos.TOP_LEFT);
        formBox.setPrefWidth(300);

        // Table
        TableColumn<Expense, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(e -> new javafx.beans.property.SimpleIntegerProperty(e.getValue().id).asObject());
        idCol.setPrefWidth(50);

        TableColumn<Expense, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(e -> new javafx.beans.property.SimpleDoubleProperty(e.getValue().amount).asObject());
        amountCol.setPrefWidth(100);

        TableColumn<Expense, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(e -> new javafx.beans.property.SimpleStringProperty(e.getValue().category));
        catCol.setPrefWidth(100);

        TableColumn<Expense, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(e -> new javafx.beans.property.SimpleStringProperty(e.getValue().note));
        noteCol.setPrefWidth(200);

        TableColumn<Expense, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(e -> new javafx.beans.property.SimpleObjectProperty<>(e.getValue().date));
        dateCol.setPrefWidth(120);

        table.getColumns().addAll(idCol, amountCol, catCol, noteCol, dateCol);
        table.setItems(data);
        table.setPrefWidth(600);

        loadExpenses();

        addBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryBox.getValue();
                String note = noteArea.getText();
                Date date = Date.valueOf(datePicker.getValue());

                Expense exp = new Expense(0, amount, category, note, date);
                ExpenseDAO.addExpense(exp);
                loadExpenses();

                amountField.clear();
                categoryBox.getSelectionModel().clearSelection();
                noteArea.clear();
                datePicker.setValue(null);
            } catch (Exception ex) {
                showAlert("Invalid Input", "Please fill all fields correctly.");
            }
        });

        BorderPane root = new BorderPane();
        root.setLeft(formBox);
        root.setCenter(table);
        BorderPane.setMargin(table, new Insets(20));

        Scene scene = new Scene(root, 950, 500);
        primaryStage.setTitle("Expense Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadExpenses() {
        data.clear();
        List<Expense> list = ExpenseDAO.getAllExpenses();
        data.addAll(list);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
