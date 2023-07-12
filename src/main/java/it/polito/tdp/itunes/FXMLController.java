/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	
    	if (this.cmbA1.getValue() == null) {
    		txtResult.setText("Scegli un album");
    		return;
    	}
    	
    	model.braniConnessi(cmbA1.getValue());
    	
    	txtResult.setText("Componente connessa - " + cmbA1.getValue().toString() + '\n');
    	txtResult.appendText("Dimensione componente = " + model.getComponenteConnessa() + '\n');
    	txtResult.appendText("# Album componente = " + model.braniConnessi(cmbA1.getValue()));
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	if (this.txtDurata.getText() == "") {
    		txtResult.setText("Inserisci una durata");
    		return;
    	}
    	try {
    		Integer.parseInt(txtDurata.getText());
    		if (Integer.parseInt(txtDurata.getText()) < 0) {
    			txtResult.setText("Insersci un numero >= 0");
    			return;
    		}
    	}
    	catch(Exception e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	
    	SimpleGraph<Album, DefaultEdge> graph = model.creaGrafo(Integer.parseInt(txtDurata.getText()));
    	
    	txtResult.setText("Grafo creato con " + graph.vertexSet().size() + " vertici e " + graph.edgeSet().size() + " archi.\n\n");
    	
    	this.btnComponente.setDisable(false);
    	this.btnSet.setDisable(false);
    	
    	List<Album> vertici = new ArrayList<>(graph.vertexSet());
    	Collections.sort(vertici);
    	
    	this.cmbA1.getItems().clear();
    	this.cmbA1.getItems().addAll(vertici);
    	
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	
    	if (this.cmbA1.getValue() == null) {
    		txtResult.setText("Scegli un album");
    		return;
    	}

    	if (this.txtX.getText() == "") {
    		txtResult.setText("Inserisci una durata (dTOT)");
    		return;
    	}
    	try {
    		Integer.parseInt(txtX.getText());
    		if (Integer.parseInt(txtX.getText()) < 0) {
    			txtResult.setText("Insersci un numero >= 0");
    			return;
    		}
    	}
    	catch(Exception e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	
    	Set<Album> soluzione = model.getSequenza(cmbA1.getValue(), Integer.parseInt(txtX.getText()));
    	
    	txtResult.setText("SEQUENZA:\n");
    	for (Album a : soluzione)
    		txtResult.appendText(a.toString() + '\n');
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.btnComponente.setDisable(true);
    	this.btnSet.setDisable(true);
    }

}
