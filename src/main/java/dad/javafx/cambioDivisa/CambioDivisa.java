package dad.javafx.cambioDivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {
	
	private Stage primaryStage;
	
	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.9);
	private Divisa dolar = new Divisa("Dolar", 1.17);
	private Divisa yen = new Divisa("Dolar", 124.1);
	
	private Divisa [] divisas = {euro, libra, dolar, yen};
	
	private Button cambiarButton;
	private TextField precioOrigen, precioDestino;
	private ComboBox<Divisa> tipomonedaOrigen, tipomonedaDestino;

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		
		// creamos un cuadro de texto
		precioOrigen = new TextField();
		precioOrigen.setPrefColumnCount(4);
		precioOrigen.setPromptText("Introduce un precio"); // ponemos un texto de ayuda
		precioOrigen.setMaxWidth(150); // ponemos el tamaño maximo del componente

		// Creamos el desplegable
		tipomonedaOrigen = new ComboBox<>();
		tipomonedaOrigen.getItems().addAll(divisas);
		tipomonedaOrigen.getSelectionModel().selectFirst();

		// creamos un panel con disposición hotizontal
		HBox cajaprimaria = new HBox();
		cajaprimaria.setSpacing(5);
		cajaprimaria.setAlignment(Pos.BASELINE_CENTER);
		cajaprimaria.getChildren().addAll(precioOrigen, tipomonedaOrigen);

		// creamos un cuadro de texto 2
		precioDestino = new TextField();
		precioDestino.setPrefColumnCount(4);
		precioDestino.setPromptText("Introduce un precio"); // ponemos un texto de ayuda
		precioDestino.setMaxWidth(150); // ponemos el tamaño maximo del componente
		precioDestino.setEditable(false);

		// Creamos el desplegable 2 
		tipomonedaDestino = new ComboBox<>();
		tipomonedaDestino.getItems().addAll(divisas);
		tipomonedaDestino.getSelectionModel().selectLast();

		// creamos un panel con disposición hotizontal
		HBox cajasecundaria = new HBox();
		cajasecundaria.setSpacing(5);
		cajasecundaria.setAlignment(Pos.BASELINE_CENTER);
		cajasecundaria.getChildren().addAll(precioDestino, tipomonedaDestino);

		// creamos el boton
		cambiarButton = new Button();
		cambiarButton.setText("Cambiar");
		cambiarButton.setOnAction(e -> onCambiarButtonAction(e)); // on + nombre componente + evento
		cambiarButton.setDefaultButton(true);

		// creamos un panel con disposicon vertical
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(cajaprimaria, cajasecundaria, cambiarButton);

		// creamos la escena
		Scene escena = new Scene(root, 320, 200);

		// configuramos la ventana
		primaryStage.setScene(escena);
		primaryStage.setTitle("Hola Mundo Mejorado");
		primaryStage.show();
	}

	private void onCambiarButtonAction(ActionEvent e) {
		try {
			Double cantidadOrigen = Double.parseDouble(precioOrigen.getText());
			Divisa divisaOrigen = tipomonedaOrigen.getSelectionModel().getSelectedItem();
			Divisa divisaDestino = tipomonedaDestino.getSelectionModel().getSelectedItem();
			
			Double cantidadDestino = divisaDestino.fromEuro(divisaOrigen.toEuro(cantidadOrigen));
			precioDestino.setText("" + cantidadDestino);
		} catch (NumberFormatException execp) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.initOwner(primaryStage);
			alerta.setTitle("Error");
			alerta.setHeaderText("Debe introducir un numero en la cantidad otigen");
			alerta.setContentText(execp.getMessage());
			alerta.showAndWait();
			execp.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);	
	}
}
