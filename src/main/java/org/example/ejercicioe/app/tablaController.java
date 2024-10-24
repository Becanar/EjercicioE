package org.example.ejercicioe.app;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ejercicioe.model.Persona;

import java.util.ArrayList;

public class tablaController {

    @FXML
    private Button btAgregar;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btModificar;
    @FXML
    private TableColumn<Persona,String> columnaApellidos;
    @FXML
    private TableColumn<Persona,Integer> columnaEdad;
    @FXML
    private TableColumn<Persona,String> columnaNombre;
    @FXML
    private VBox rootPane;
    @FXML
    private TableView<Persona> tablaVista;
    @FXML
    private HBox contenedorBotones;

    private ObservableList<Persona> personas = FXCollections.observableArrayList();
    private TextField txtNombre;
    private TextField txtApellidos;
    private TextField txtEdad;
    private Button btnGuardar;
    private Button btnCancelar;
    private Stage modal;

    public void initialize() {
        columnaNombre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombre()));
        columnaApellidos.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getApellidos()));
        columnaEdad.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEdad()));
    }

    @FXML
    void agregarPersona(ActionEvent event) {
        mostrarVentanaDatos((Stage) btAgregar.getScene().getWindow(), false);
        btnGuardar.setOnAction(actionEvent -> {
            guardar(false);
            tablaVista.getSelectionModel().clearSelection();
        });
        btnCancelar.setOnAction(actionEvent -> cancelar());
    }

    private void mostrarAlertError(ArrayList<String> lst) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(btAgregar.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle("Error");
        String error = "";
        for (String str : lst) {
            error += str + "\n";
        }
        alert.setContentText(error);
        alert.showAndWait();
    }

    private void mostrarVentanaAgregado() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.initOwner(btAgregar.getScene().getWindow());
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText("Persona agregada correctamente.");
        alerta.showAndWait();
    }

    public void mostrarVentanaDatos(Stage ventanaPrincipal, boolean esModif) {
        modal = new Stage();
        modal.setResizable(false);
        try {
            Image img = new Image(getClass().getResource("/org/example/ejercicioe/agenda.png").toString());
            modal.getIcons().add(img);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
        modal.initOwner(ventanaPrincipal);
        modal.initModality(Modality.WINDOW_MODAL);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(75), new ColumnConstraints(200));
        Label lblNombre = new Label("Nombre");
        if (esModif) {
            txtNombre = new TextField(tablaVista.getSelectionModel().getSelectedItem().getNombre());
        } else {
            txtNombre = new TextField();
        }
        gridPane.add(lblNombre, 0, 0, 1, 1);
        gridPane.add(txtNombre, 1, 0, 1, 1);
        Label lblApellidos = new Label("Apellidos");
        if (esModif) {
            txtApellidos = new TextField(tablaVista.getSelectionModel().getSelectedItem().getApellidos());
        } else {
            txtApellidos = new TextField();
        }
        gridPane.add(lblApellidos, 0, 1, 1, 1);
        gridPane.add(txtApellidos, 1, 1, 1, 1);
        Label lblEdad = new Label("Edad");
        if (esModif) {
            txtEdad = new TextField("" + tablaVista.getSelectionModel().getSelectedItem().getEdad());
        } else {
            txtEdad = new TextField();
        }
        txtEdad.setMaxWidth(75);
        gridPane.add(lblEdad, 0, 2, 1, 1);
        gridPane.add(txtEdad, 1, 2, 1, 1);
        btnGuardar = new Button("Guardar");
        btnCancelar = new Button("Cancelar");
        FlowPane flowPane = new FlowPane(btnGuardar, btnCancelar);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(20);
        gridPane.add(flowPane, 0, 3, 2, 1);
        Scene scene = new Scene(gridPane, 300, 150);
        modal.setScene(scene);
        modal.setResizable(false);
        modal.setTitle(esModif ? "Editar Persona" : "Nueva Persona");
        modal.show();
    }

    public void guardar(boolean esModificar) {
        boolean resultado = valido();
        if (resultado) {
            Persona p = new Persona(txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
            ObservableList<Persona> personas = tablaVista.getItems(); // Obtenemos la lista directamente de la tabla

            // Verificar si ya existe en la tabla
            if (!personas.contains(p)) {
                if (esModificar) {
                    int selectedIndex = tablaVista.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        personas.set(selectedIndex, p);
                        mostrarVentanaModificado();
                    }
                } else {
                    personas.add(p);
                    mostrarVentanaAgregado();
                }
                modal.close();
                tablaVista.getSelectionModel().clearSelection();
            } else {
                ArrayList<String> lst = new ArrayList<>();
                lst.add("La persona ya existe.");
                mostrarAlertError(lst);
            }
        }
    }

    private void mostrarVentanaModificado() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.initOwner(btAgregar.getScene().getWindow());
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText("Persona modificada correctamente.");
        alerta.showAndWait();
    }

    public void cancelar() {
        modal.close();
    }

    private boolean valido() {
        boolean error = false;
        ArrayList<String> errores = new ArrayList<>();
        if (txtNombre.getText().equals("")) {
            errores.add("El campo Nombre es obligatorio.");
            error = true;
        }
        if (txtApellidos.getText().equals("")) {
            errores.add("El campo Apellidos es obligatorio.");
            error = true;
        }
        try {
            Integer.parseInt(txtEdad.getText());
        } catch (NumberFormatException e) {
            errores.add("El campo Edad debe ser numérico.");
            error = true;
        }
        if (error) {
            mostrarAlertError(errores);
            return false;
        }
        return true;
    }

    @FXML
    void eliminar(ActionEvent event) {
        Persona p = tablaVista.getSelectionModel().getSelectedItem();
        if (p == null) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("No has seleccionado ninguna persona.");
            mostrarAlertError(lst);
        } else {
            tablaVista.getItems().remove(p);
            txtNombre.setText("");
            txtApellidos.setText("");
            txtEdad.setText("");
            mostrarVentanaEliminado();
            tablaVista.getSelectionModel().clearSelection();
        }
    }

    private void mostrarVentanaEliminado() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.initOwner(btAgregar.getScene().getWindow());
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText("Persona eliminada correctamente.");
        alerta.showAndWait();
    }

    @FXML
    void modificar(ActionEvent event) {
        Persona p = tablaVista.getSelectionModel().getSelectedItem();
        if (p == null) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("No has seleccionado ninguna persona.");
            mostrarAlertError(lst);
        } else {
            mostrarVentanaDatos((Stage) btModificar.getScene().getWindow(), true);
            btnGuardar.setOnAction(actionEvent -> {
                guardar(true);
                tablaVista.getSelectionModel().clearSelection();
            });
            btnCancelar.setOnAction(actionEvent -> cancelar());
        }
    }
}
