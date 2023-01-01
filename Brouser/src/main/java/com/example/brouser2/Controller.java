package com.example.brouser2;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.brouser2.History.History;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;


public class Controller implements Initializable {




    @FXML
    private TextField tfSearch;

    public Button getHistoryStop() {
        return HistoryStop;
    }

    @FXML
     private Button HistoryStop;
    @FXML
    private Button btToFavourites;

    public Button getBtToFavourites() {
        return btToFavourites;
    }

    History history;


    @FXML
    private TabPane tabPane;


    @FXML
    void handleAbout(ActionEvent event) {

    }

    @FXML
    void handleBackWard(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).goToHistory( -1);
    }

    @FXML
    void handleForWard(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).goToHistory(1);
    }
    @FXML
    void handleHome(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).loadPage(LinksName.Google);
    }

    @FXML
    void handleReaload(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).reload();
    }

    @FXML
    void handleSearch(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).loadPage(tfSearch.getText());
    }
    @FXML
    void handleHiStory(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).showHistory();



    }
    @FXML
    void handleDounload(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).download();
    }
    @FXML
    void handlePlus(ActionEvent event){
        SiteTab tab = new SiteTab(LinksName.Google,history);
        tabPane.getTabs().add(tab);
    }
    @FXML
    void handleMinus(ActionEvent event){
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
        if(tabPane.getTabs().size()==0) {
            SiteTab tab = new SiteTab(LinksName.Google,history);
            tabPane.getTabs().add(tab);
        }
    }
    public void OpenFromLink(String url){
        SiteTab tab = new SiteTab(url,history);
        tabPane.getTabs().add(tab);
    }
    @FXML
    void handleHtml(ActionEvent event){
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).showHtmlCode();
    }
    @FXML
    void handleCreateHtml(ActionEvent event) {
        HtmlRedactor tab = new HtmlRedactor(history);
        tabPane.getTabs().add(tab);
    }

    @FXML
    void handleFavourites(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).showFavorites();
    }
    @FXML
    void handleToFavourites(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).stopFavorites();
    }
    @FXML
    void handleHistoryStop(ActionEvent event) {
        ((MyTab)(tabPane.getSelectionModel().getSelectedItem())).stopHistory();

    }

    @FXML
    void initialize() {
        this.assertError();

    }
    void assertError(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        history= new History(this);

        SiteTab tab = new SiteTab(LinksName.Google,history);
        tabPane.getTabs().add(tab);
    }
    public void shutdown() {
        this.history.save();
    }
}
