package com.cloud.cloudclient;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientController implements Initializable {

    @FXML
    public TableView <FileInfo>  filesTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Создаем столбец с типом файла
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(24);

        //Создаем столбец с именем файла
        TableColumn<FileInfo, String> filenameColumn = new TableColumn<>("Имя");
        filenameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        filenameColumn.setPrefWidth(240);


        filesTable.getColumns().addAll(fileTypeColumn, filenameColumn);
    }




    // Медод получения файлов из католога

    public void UpdatList(Path path){

    }


    public void btnExitAction(ActionEvent actionEvent) {

        Platform.exit();
    }

    public void copyBtnAction(ActionEvent actionEvent) {
    }

}