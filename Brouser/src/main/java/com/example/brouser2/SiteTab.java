package com.example.brouser2;

import com.example.brouser2.History.History;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SiteTab extends MyTab {
    SiteTab(String url, History history) {
        super(history);
        webView.getEngine().load(url);
        webView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/613.1(KHTML, like Gecko) JavaFX/19 Safari/613.1");
        this.setText(url);
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener() {
                    long lastIndex=-1;
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        if(lastIndex!=webView.getEngine().getHistory().getCurrentIndex()) {
                           lastIndex=webView.getEngine().getHistory().getCurrentIndex();
                           String hostName=HtmlCode.toHost(getCurrentUrl());
                           setText(hostName);
                           textArea.setText(HtmlCode.getURLSource(getCurrentUrl()).toString());
                           long time=getCurrentTime();
                           history.addToHistory(hostName, getCurrentUrl(),getCurrentDate().toString(), String.format(" %02d:%02d:%02d", time / 1000 / 3600, time / 1000 / 60 % 60, time / 1000 % 60));
                           history.setHistoryText(hostName);
                           history.setFavorites(getCurrentUrl());
                        }

                    }
                }
        );

    }
    @Override
        public void reload(){
            webView.getEngine().reload();
        }
    @Override
    void stopHistory(){
        history.addToNoHistory(HtmlCode.toHost(getCurrentUrl()));
    }
    @Override
    void stopFavorites(){
        history.addFavorites(getCurrentUrl());
    }


}
