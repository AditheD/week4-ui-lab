package week4uilab.week4uilab;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;


public class Main extends Application {
    private Stage stage;
    private Timeline splashTimer;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Farmingdale State College");
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        showSplash();
        stage.show();
    }

    private void showSplash() {
        StackPane root = new StackPane();
        root.getStyleClass().add("splash-screen");

        URL campusUrl = getClass().getResource("/images/campus.jpg");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(630);
        progressBar.getStyleClass().add("loading-bar");

        Label loadingText = new Label("Loading... 0%");
        loadingText.getStyleClass().add("loading-text");

        VBox loadingBox = new VBox(12, progressBar, loadingText);
        loadingBox.setAlignment(Pos.CENTER);

        if (campusUrl != null) {
            ImageView campus = new ImageView(new Image(campusUrl.toExternalForm()));

            campus.setPreserveRatio(false);
            campus.fitWidthProperty().bind(root.widthProperty());
            campus.fitHeightProperty().bind(root.heightProperty());
            root.getChildren().add(campus);

            StackPane.setAlignment(loadingBox, Pos.BOTTOM_CENTER);
            StackPane.setMargin(loadingBox, new Insets(0, 30, 65, 30));
            root.getChildren().add(loadingBox);
        } else {
            VBox center = new VBox(25);
            center.setAlignment(Pos.CENTER);
            center.setMaxWidth(750);

            ImageView wordmark = loadImage("/images/wordmark.jpg", 620);
            if (wordmark != null) {
                center.getChildren().add(wordmark);
            }

            Label welcome = new Label("Welcome to Farmingdale");
            welcome.getStyleClass().add("splash-title");

            center.getChildren().addAll(welcome, loadingBox);
            root.getChildren().add(center);
        }

        setScreen(root);

        splashTimer = new Timeline();

        for (int i = 0; i <= 100; i += 5) {
            final int percent = i;

            splashTimer.getKeyFrames().add(new KeyFrame(Duration.millis(i * 25), event -> {
                        progressBar.setProgress(percent / 100.0);
                        loadingText.setText("Loading... " + percent + "%");

                        if (percent == 100) {
                            showLogin();
                        }
                    })
            );
        }

        splashTimer.play();
    }

    private void showLogin() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("auth-screen");

        VBox form = new VBox(20);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPrefWidth(440);
        form.getStyleClass().add("login-form");

        ImageView wordmark = loadImage("/images/wordmark.jpg", 340);
        if (wordmark != null) {
            form.getChildren().add(wordmark);
        }

        Label heading = new Label("Welcome Back");
        heading.getStyleClass().add("form-title");

        Label subtitle = new Label("Log in to continue");
        subtitle.getStyleClass().add("form-subtitle");

        TextField username = new TextField();
        username.setPromptText("Enter your username");
        username.getStyleClass().add("form-field");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your password");
        password.getStyleClass().add("form-field");

        Button login = new Button("LOGIN");
        login.getStyleClass().add("primary-button");
        login.setOnAction(event -> showLanding());

        Button cancel = new Button("CANCEL");
        cancel.getStyleClass().add("secondary-button");
        cancel.setOnAction(event -> {
            username.clear();
            password.clear();
        });

        HBox buttons = new HBox(16, login, cancel);

        Hyperlink register = new Hyperlink("Don't have an account? Register");
        register.setOnAction(event -> showRegistration());

        form.getChildren().addAll(heading, subtitle, labeledField("Username", username), labeledField("Password", password), buttons, register);

        root.setLeft(form);
        root.setCenter(createBranding());
        setScreen(root);
    }

    private void showRegistration() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("auth-screen");

        VBox form = new VBox(18);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPrefWidth(440);
        form.getStyleClass().add("login-form");

        ImageView wordmark = loadImage("/images/wordmark.jpg", 340);
        if (wordmark != null) {
            form.getChildren().add(wordmark);
        }

        Label heading = new Label("Create an Account");
        heading.getStyleClass().add("form-title");

        Label subtitle = new Label("Join the Farmingdale community");
        subtitle.getStyleClass().add("form-subtitle");

        TextField username = new TextField();
        username.setPromptText("Choose a username");
        username.getStyleClass().add("form-field");

        TextField email = new TextField();
        email.setPromptText("Enter your email");
        email.getStyleClass().add("form-field");

        PasswordField password = new PasswordField();
        password.setPromptText("Create a password");
        password.getStyleClass().add("form-field");

        Button signUp = new Button("SIGN UP");
        signUp.getStyleClass().add("primary-button");
        signUp.setOnAction(event -> showLanding());

        Button back = new Button("BACK");
        back.getStyleClass().add("secondary-button");
        back.setOnAction(event -> showLogin());

        HBox buttons = new HBox(16, signUp, back);

        form.getChildren().addAll(
                heading,
                subtitle,
                labeledField("Username", username),
                labeledField("Email", email),
                labeledField("Password", password),
                buttons
        );

        root.setLeft(form);
        root.setCenter(createBranding());
        setScreen(root);
    }

    private VBox labeledField(String text, Node field) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");

        VBox box = new VBox(7, label, field);
        box.setMaxWidth(350);
        return box;
    }

    private StackPane createBranding() {
        StackPane branding = new StackPane();
        branding.getStyleClass().add("branding-panel");
        branding.setMinWidth(400);

        URL imageUrl = getClass().getResource("/images/rams-sign.png");

        if (imageUrl == null) {
            branding.getChildren().add(new Label("rams-sign.png was not found"));
            return branding;
        }

        Image image = new Image(imageUrl.toExternalForm(), false);

        if (image.isError() || image.getWidth() == 0) {
            Label error = new Label("The Rams image could not be opened.\n" + String.valueOf(image.getException()));

            error.setWrapText(true);
            error.setStyle("-fx-text-fill: darkred; -fx-font-size: 18px;");
            branding.getChildren().add(error);
            return branding;
        }

        ImageView sign = new ImageView(image);
        sign.setFitWidth(560);
        sign.setFitHeight(650);
        sign.setPreserveRatio(true);
        branding.getChildren().add(sign);

        System.out.println("Rams image size: " + image.getWidth() + " x " + image.getHeight());

        return branding;
    }

    private ImageView loadImage(String path, double width) {
        URL imageUrl = getClass().getResource(path);

        if (imageUrl == null) {
            return null;
        }

        ImageView imageView = new ImageView(new Image(imageUrl.toExternalForm()));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        return imageView;
    }

    private void showLanding() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("landing-screen");

        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem logoutItem = new MenuItem("Log Out");
        logoutItem.setOnAction(event -> showLogin());

        fileMenu.getItems().addAll(newItem, openItem, new SeparatorMenuItem(), logoutItem);

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().add(new MenuItem("Preferences"));

        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("About"));

        MenuBar menuBar = new MenuBar(fileMenu, editMenu, helpMenu);
        menuBar.getStyleClass().add("app-menu");
        root.setTop(menuBar);

        VBox sidebar = new VBox(14);
        sidebar.setPrefWidth(210);
        sidebar.getStyleClass().add("sidebar");

        Label sidebarTitle = new Label("WORKSPACE");
        sidebarTitle.getStyleClass().add("sidebar-heading");

        Button newButton = new Button("New");
        Button filesButton = new Button("Files");
        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(event -> showLogin());

        for (Button button : new Button[]{newButton, filesButton, logoutButton}) {
            button.getStyleClass().add("sidebar-button");
            button.setMaxWidth(Double.MAX_VALUE);
        }

        sidebar.getChildren().addAll(sidebarTitle, newButton, filesButton, logoutButton);

        root.setLeft(sidebar);

        VBox content = new VBox(22);
        content.getStyleClass().add("content-area");

        Label title = new Label("Dashboard");
        title.getStyleClass().add("dashboard-title");

        Label subtitle = new Label("Welcome to your workspace");
        subtitle.getStyleClass().add("dashboard-subtitle");

        VBox heading = new VBox(5, title, subtitle);

        VBox firstTable = createTable("Table One");
        VBox secondTable = createTable("Table Two");

        HBox tables = new HBox(18, firstTable, secondTable);
        HBox.setHgrow(firstTable, Priority.ALWAYS);
        HBox.setHgrow(secondTable, Priority.ALWAYS);
        VBox.setVgrow(tables, Priority.ALWAYS);

        content.getChildren().addAll(heading, tables);
        root.setCenter(content);

        setScreen(root);
    }

    private VBox createTable(String title) {
        VBox panel = new VBox();
        panel.setMinWidth(0);
        panel.getStyleClass().add("table-panel");

        Label tableTitle = new Label(title);
        tableTitle.setMaxWidth(Double.MAX_VALUE);
        tableTitle.getStyleClass().add("table-title");

        HBox headers = createRow("Field name", "Data type", "Default", "ID");
        headers.getStyleClass().add("table-header");

        VBox rows = new VBox();

        for (int i = 1; i <= 20; i++) {
            HBox row = createRow("Field " + i, "Text", "None", String.valueOf(i));
            row.getStyleClass().add("table-row");
            rows.getChildren().add(row);
        }

        ScrollPane scrollPane = new ScrollPane(rows);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("table-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        panel.getChildren().addAll(tableTitle, headers, scrollPane);
        return panel;
    }

    private HBox createRow(String field, String type, String defaultValue, String id) {

        Label fieldLabel = tableCell(field);
        Label typeLabel = tableCell(type);
        Label defaultLabel = tableCell(defaultValue);
        Label idLabel = tableCell(id);

        fieldLabel.setMaxWidth(Double.MAX_VALUE);
        typeLabel.setMaxWidth(Double.MAX_VALUE);
        defaultLabel.setMaxWidth(Double.MAX_VALUE);
        idLabel.setPrefWidth(35);

        HBox.setHgrow(fieldLabel, Priority.ALWAYS);
        HBox.setHgrow(typeLabel, Priority.ALWAYS);
        HBox.setHgrow(defaultLabel, Priority.ALWAYS);

        HBox row = new HBox(fieldLabel, typeLabel, defaultLabel, idLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Label tableCell(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("table-cell-label");
        return label;
    }

    private void setScreen(Region root) {
        Scene scene = new Scene(root, 1280, 800);

        URL cssUrl = getClass().getResource("/styles.css");

        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setScene(scene);
    }

    @Override
    public void stop() {
        if (splashTimer != null) {
            splashTimer.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
