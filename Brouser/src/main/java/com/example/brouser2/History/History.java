package com.example.brouser2.History;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.example.brouser2.Controller;
import com.example.brouser2.HtmlCode;
import com.example.brouser2.HtmlRedactor;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.*;

public class History {
    List<String[]> history;
    Controller controller;
    HashSet<String> noHistory=new HashSet<>();
    HashSet<String>  favorites= new HashSet<>();
    public History(Controller controller){
        this.controller=controller;
        readHistory();
        readNoHistoryFile();
        readFavoritesFile();

    }
    public void save(){
        writeHistory();
        writeNoHistoryFile();
        writeFavoritesFile();
    }
    public void addToHistory(String host,String url, String Date, String time){
        history.add(new String[]{host,url,Date,time});
    }
    public void addToNoHistory(String host){
        if(noHistory.contains(host)){
            noHistory.remove(host);
            controller.getHistoryStop().setText("Отключить историю");
            return;
        }
        noHistory.add(host);
        controller.getHistoryStop().setText("Включить историю");
    }
    public void addFavorites(String site){
        if(favorites.contains(site)){
            favorites.remove(site);
            controller.getBtToFavourites().setText("Добавить в избранное");
            return;
        }
        favorites.add(site);
        controller.getBtToFavourites().setText("Удалить из избранного");
    }
    public void setFavorites(String site){
        if(!favorites.contains(site)){
            controller.getBtToFavourites().setText("Добавить в избранное");
            return;
        }
        controller.getBtToFavourites().setText("Удалить из избранного");
    }

    public void setHistoryText(String host){
        if(!noHistory.contains(host)){
            controller.getHistoryStop().setText("Отключить историю");
            return;
        }
        controller.getHistoryStop().setText("Включить историю");
    }
    void readHistory(){
        try {
            CSVReader reader = new CSVReader(new FileReader("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\history.csv"));
            history = reader.readAll();
            reader.close();
        } catch (IOException e) {
            history=new ArrayList<String[]>();
        }
    }
    void writeHistory(){
        try {
            PrintWriter writer = new PrintWriter(new File("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\history.csv"));
            for (String[] currentString : history) {
                StringBuilder toFile = new StringBuilder();
              //  Object[] objectArray = currentList.toArray();
                toFile.append(currentString[0]);
                toFile.append(",");
                toFile.append(currentString[1]);
                toFile.append(",");
                toFile.append(currentString[2]);
                toFile.append(",");
                toFile.append(currentString[3]);
                toFile.append("\n");
                writer.write(toFile.toString());
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("No correct csv format");
        }


    }
    private void readNoHistoryFile(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\noHistory.csv");
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            noHistory =  (HashSet<String>)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
             noHistory = new HashSet<String>();
        } catch (ClassNotFoundException e) {
            System.out.println("File: "+noHistory+"was change" + e.getMessage());
        }
    }
    private void writeNoHistoryFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\noHistory.csv");
            ObjectOutputStream objectOutputStream= objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(noHistory);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Cash write error: " + e.getMessage());
        }
    }
    private void readFavoritesFile(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\favorites.csv");
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            favorites=  (HashSet<String>)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            favorites = new HashSet<String>();
        } catch (ClassNotFoundException e) {
            System.out.println("File: "+favorites+"was change" + e.getMessage());
        }
    }
    private void writeFavoritesFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("P:\\Projects\\JetBrains\\IntelliJIDEA\\RPKS" +
                    "\\Brouser2\\src\\main\\java\\com\\example\\brouser2\\History\\favorites.csv");
            ObjectOutputStream objectOutputStream= objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(favorites);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Cash write error: " + e.getMessage());
        }
    }
    public ScrollPane scrollPaneHistory(){
        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        scrollPane.setContent(vbox);
        for(String[] site:history){
            if(!noHistory.contains(site[0])){
                Hyperlink hyperlink=new Hyperlink();
                hyperlink.setText(site[1]);
                 hyperlink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent mouseEvent) {
                          controller.OpenFromLink(site[1]);
                     }
                 });
                HBox hBox=new HBox(new Label(site[2]),new Label(site[3]),hyperlink);
                vbox.getChildren().add(hBox);
            }
        }
        return scrollPane;
    }
    public ScrollPane scrollPaneFavorites(){
        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        scrollPane.setContent(vbox);
        for(String site:favorites){
                Hyperlink hyperlink=new Hyperlink();
                hyperlink.setText(site);
                hyperlink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        controller.OpenFromLink(site);
                    }
                });
                HBox hBox=new HBox(hyperlink);
                vbox.getChildren().add(hBox);
        }
        return scrollPane;
    }
}
