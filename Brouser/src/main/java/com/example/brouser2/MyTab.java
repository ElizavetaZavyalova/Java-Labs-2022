package com.example.brouser2;

import com.example.brouser2.History.History;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class MyTab extends Tab {
    public WebView getWebView() {
        return webView;
    }
    enum ViewStatus{
        HTML_CODE, WEB_VIEW, HISTORY,FAVORITES
    }
    SiteTab.ViewStatus viewStatus = SiteTab.ViewStatus.WEB_VIEW;
    History history;
    protected WebView webView=new WebView();
    protected TextArea textArea=new TextArea();
    MyTab(History history) {
        this.history = history;
        this.setContent(webView);

    }
    void loadPage(String url){
        webView.getEngine().load(url);
        textArea.setText(HtmlCode.getURLSource(getCurrentUrl()).toString());
    }
    protected String getCurrentUrl(){
        return webView.getEngine().getHistory().getEntries().get(
                webView.getEngine().getHistory().getCurrentIndex()).getUrl();
    }
    protected Date getCurrentDate(){

            Date date= webView.getEngine().getHistory().getEntries().get(
                    webView.getEngine().getHistory().getCurrentIndex()).getLastVisitedDate();
            if(date == null){
                return Calendar.getInstance().getTime();
            }
            return date;

    }
    protected long getCurrentTime(){
        try {
            Date date = getCurrentDate();
            Calendar calendar = Calendar.getInstance();
            return calendar.getTimeInMillis() - date.getTime();
        }
        catch (NullPointerException e){
            System.out.println("Дата ");
            return 0;
        }
    }
    public void download(){
        try {

            File file = new File("C:\\Users\\eliza\\Downloads\\test.zip");
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
            ZipEntry zipEntry = new ZipEntry("mytext.html");
            out.putNextEntry(zipEntry);
            byte[] data = textArea.getText().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
            out.close();
        } catch (IOException e) {
            System.out.println("Ошибка"+e.getMessage());
        }


    }
    public void goToHistory(Integer index){
        try {
            webView.getEngine().getHistory().go(index);
        }
        catch(RuntimeException e){

        }
    }
    abstract void stopHistory();
    abstract void stopFavorites();
    void showHistory(){
        if(viewStatus!=ViewStatus.HISTORY&&viewStatus!=ViewStatus.FAVORITES){
            viewStatus=ViewStatus.HISTORY;
            this.setContent(history.scrollPaneHistory());
        }
        else if(viewStatus==ViewStatus.HISTORY) {
            hideHistoryFavorites();
        }
    }
    void showFavorites(){
        if(viewStatus!=ViewStatus.HISTORY&&viewStatus!=ViewStatus.FAVORITES){
            viewStatus=ViewStatus.FAVORITES;
            this.setContent(history.scrollPaneFavorites());
        }
        else if(viewStatus==ViewStatus.FAVORITES) {
            hideHistoryFavorites();
        }
    }
    void hideHistoryFavorites(){
        this.setContent(webView);
        viewStatus = SiteTab.ViewStatus.WEB_VIEW;
    }
    abstract public void reload();
    public void showHtmlCode(){
        if(viewStatus!=ViewStatus.HISTORY&&viewStatus!=ViewStatus.FAVORITES) {
            switch (viewStatus) {
                case HTML_CODE:
                    this.setContent(webView);
                    viewStatus = SiteTab.ViewStatus.WEB_VIEW;
                    break;
                case WEB_VIEW:
                    this.setContent(new HBox(textArea, webView));
                    viewStatus = SiteTab.ViewStatus.HTML_CODE;
                    break;
            }
        }

    }

}
