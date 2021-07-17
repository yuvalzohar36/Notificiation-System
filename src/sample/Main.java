package sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import java.time.LocalDate;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Font;
import jdk.jfr.Event;
import java.util.*;
import javax.mail.*;
//package com.journaldev.mail;

import java.util.Properties;

import javax.mail.Session;


import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.*;
//import javax.activation.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main mainInst = new Main();

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        mainInst.handleTitle(vbox,"Calendar");
        DatePicker datePicker = mainInst.handleDatePicker(vbox,"Choose date");
        TextArea textArea = mainInst.handleTextArea(vbox);
        Button SentButton = mainInst.handleSentButton(vbox);
        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calendar");
        primaryStage.show();
        SentButton.setOnAction(action -> {
            String date;
            if(datePicker.getValue()==null)
                date = "Not inserted";
            else
                date = datePicker.getValue().toString();
            String text = textArea.getText();
            text = "Date: " +date+" \n"+text;
            textArea.setText("Sent !");
            mainInst.SendEmail(date,text);
        });
    }

    public void handleTitle(VBox vbox, String title_text){
        Text title = new Text("Calendar");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vbox.getChildren().add(title);
    }
    public TextArea handleTextArea(VBox vbox){
        TextArea textArea = new TextArea();
        vbox.getChildren().add(textArea);
        return textArea;
    }

    public Button handleSentButton(VBox vbox){
        Button SentButton = new Button("Click to get text");
        SentButton.setMinWidth(50);
        vbox.getChildren().add(SentButton);
        return SentButton;
    }

    public DatePicker handleDatePicker(VBox vbox, String textPickerHeader){
        Label label = new Label(textPickerHeader);
        vbox.getChildren().add(label);
        DatePicker datePicker = new DatePicker();
        System.out.println(datePicker);
        HBox hbox = new HBox(datePicker);
        vbox.getChildren().add(hbox);
        return datePicker;
    }


    public void SendEmail(String date, String text){
            // Mention the Recipient's email address
            String to = "yuvalzohar36@gmail.com";
            // Mention the Sender's email address
            String from = "yuvalzohar36@gmail.com";
            // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
            String host = "smtp.gmail.com";
            // Get system properties
            Properties properties = System.getProperties();
            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("yuvalzohar36@gmail.com", "*********");
                }
            });
            // Used to debug SMTP issues
            session.setDebug(true);
            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);
                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));
                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                // Set Subject: header field
                message.setSubject("New Alart");
                // Now set the actual message
                message.setText(text);
                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
    }

    public static void main(String[] args) {

        launch(args);
    }

}

