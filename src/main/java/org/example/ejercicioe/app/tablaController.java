package org.example.ejercicioe.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class tablaController {

    @FXML
    private Button btAgregar;

    @FXML
    private Button btEliminar;

    @FXML
    private Button btModificar;

    @FXML
    private TableColumn<?, ?> columnaApellidos;

    @FXML
    private TableColumn<?, ?> columnaEdad;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private HBox contenedorBotones;

    @FXML
    private VBox rootPane;

    @FXML
    private TableView<?> tablaVista;

    @FXML
    void agregarPersona(ActionEvent event) {

    }

    @FXML
    void eliminar(ActionEvent event) {

    }

    @FXML
    void modificar(ActionEvent event) {

    }

}
