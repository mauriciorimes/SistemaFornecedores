/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import model.Contato;
import model.dao.ContatoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author mauri
 */
public class FormularioController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;

    private static Contato contatoSelecionado;
    @FXML
    private ImageView imgview;

    private String firstPath;
    private String urlImagem;
    @FXML
    private ComboBox btnCombo;

    private ObservableList<String> Li = FXCollections.observableArrayList("Contratados", "Faltantes");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCombo.setValue("");
        btnCombo.setItems(Li);
        if (contatoSelecionado != null) {
            txtNome.setText(contatoSelecionado.getNome());
            txtEmail.setText(contatoSelecionado.getEmail());
            txtTelefone.setText(contatoSelecionado.getTelefone());

            urlImagem = contatoSelecionado.getFoto();

            if (contatoSelecionado.getFoto() != null) {
                File arquivo = new File(contatoSelecionado.getFoto());
                Image imagem = new Image(arquivo.toURI().toString());
                imgview.setImage(imagem);
            }

            btnCombo.getSelectionModel().select(contatoSelecionado.getStatus());
        }
        imgview.setOnMouseClicked((MouseEvent e) -> {
            selecionaFoto();
        });
    }

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
        contatoSelecionado = null;
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws IOException, Exception {
        Contato contato = new Contato();
        contato.setNome(txtNome.getText());
        contato.setEmail(txtEmail.getText());
        contato.setTelefone(txtTelefone.getText());
        contato.setFoto(urlImagem);
        contato.setStatus(btnCombo.getSelectionModel().getSelectedItem().toString());

        ContatoDaoJDBC dao = DaoFactory.novoContatoDao();

        if (contatoSelecionado == null) {
            dao.incluir(contato);
        } else {
            contato.setId(contatoSelecionado.getId());

            dao.editar(contato);
            contatoSelecionado = null;
        }

        App.setRoot("Principal");
    }

    public static Contato getContatoSelecionado() {
        return contatoSelecionado;
    }

    public static void setContatoSelecionado(Contato contatoSelecionado) {
        FormularioController.contatoSelecionado = contatoSelecionado;
    }

    public void selecionaFoto() {

        FileChooser fileChooser = new FileChooser();

        if (firstPath != null) {
            File path = new File(firstPath);
            fileChooser.initialDirectoryProperty().set(path);
        }

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg"));
        Window OwnStage = null;
        File selectFile = fileChooser.showOpenDialog(OwnStage);

        if (selectFile != null) {
            String path = selectFile.getPath();
            int len = path.lastIndexOf("/");
            if (len == -1) {
                len = path.lastIndexOf("\\");
            }
            firstPath = path.substring(0, len);
            urlImagem = path;
        }

        File file = new File(urlImagem);
        Image image = new Image(file.toURI().toString());
        imgview.setImage(image);

    }

}
