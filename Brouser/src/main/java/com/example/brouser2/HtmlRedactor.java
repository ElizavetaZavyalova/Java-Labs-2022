package com.example.brouser2;

import com.example.brouser2.History.History;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class HtmlRedactor extends MyTab {


    HtmlRedactor(History history){
        super(history);
        this.viewStatus=ViewStatus.HTML_CODE;
        this.webView = new WebView();
        HBox hbox= new HBox(textArea,webView);
        this.setContent(hbox);
        this.setText("Редактор html");

    }

    @Override
    void stopHistory() {

    }

    @Override
    void stopFavorites() {

    }

    @Override
    public void reload(){
        webView.getEngine().loadContent(textArea.getText());
    }
}
