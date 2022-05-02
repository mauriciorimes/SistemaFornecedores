/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import static javafx.scene.control.ButtonType.OK;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contato;
import model.dao.ContatoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author mauri
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TableColumn<Contato, String> tblColNome;
    @FXML
    private TableColumn<Contato, String> tblColEmail;
    @FXML
    private TableColumn<Contato, String> tblColTelefone;

    private List<Contato> listaContatos;
    private ObservableList<Contato> obsersableListContato;
    @FXML
    private TableView<Contato> tblContato;
    @FXML
    private Label ip;    
    @FXML
    private TableColumn<?, ?> cStatus;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarContatos("");
    }

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        App.setRoot("Formulario");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException {
        Contato contatoSelecinado = tblContato.selectionModelProperty().getValue().getSelectedItem();
        FormularioController.setContatoSelecionado(contatoSelecinado);
        App.setRoot("Formulario");
        
    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Contato contatoSelecinado = tblContato.selectionModelProperty().getValue().getSelectedItem();
        ContatoDaoJDBC dao = DaoFactory.novoContatoDao();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setContentText("Deseja excluir esse fornecedor?" + contatoSelecinado.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {
                dao.excluir(contatoSelecinado);
            } catch (Exception e) {
                String mensagem = "Ocorreu um erro" + e.getMessage();
                Alert alertErro = new Alert(AlertType.INFORMATION);
                alertErro.setTitle("Aviso");
                alertErro.setContentText(mensagem);
                alertErro.showAndWait();
            }
        }
        carregarContatos("");

    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarContatos(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        carregarContatos("");
        txtFiltro.clear();
    }

    public void carregarContatos(String param) {
        double l = 0;
        double x = 0;
     
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tblColTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        try {
            ContatoDaoJDBC dao = DaoFactory.novoContatoDao();
            listaContatos = dao.listar(param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        for(Contato i : listaContatos){
            x++;
            if("Contratados".equals(i.getStatus())){
                l++;
            }
        }
        double prog = l/x;

        
        ip.setText(Math.round(prog*100)+"%");

        obsersableListContato = FXCollections.observableArrayList(listaContatos);
        tblContato.setItems(obsersableListContato);
    }    

}
