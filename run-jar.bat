SET /P jfx11path="JavaFX 11 Lib Path > "
ECHO %jfx11path%
java^
    --module-path "%jfx11path%"^
    --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web^
    -jar application.jar