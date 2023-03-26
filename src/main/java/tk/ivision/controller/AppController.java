package tk.ivision.controller;

import TUIO.TuioClient;
import TUIO.TuioListener;
import tk.ivision.core.controller.Controller;
import tk.ivision.core.objects.SlideObject;
import tk.ivision.core.view.TuioComponent;
import tk.ivision.core.view.View;
import tk.ivision.view.app.AppMenuBar;
import tk.ivision.view.app.MainPanel;
import tk.ivision.view.screen.DisplayComponent;
import tk.ivision.view.screen.FeedbackComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppController extends Controller {

    public AppController(){
        super(new FeedbackComponent());
    }

    public void index(ActionEvent e){

        TuioClient client = new TuioClient();
        client.removeAllTuioListeners();
        client.addTuioListener(this.getTuioListener());
        if(!client.isConnected()) {
            client.connect();
        }

        DisplayComponent displayComponent = new DisplayComponent();
        client.addTuioListener(displayComponent);


        // Neues Panel erstellen und Liste hinzufügen.
        MainPanel main = new MainPanel()
                .addNorth(new JLabel("test nord"), new Insets(0,0,0,0))
                .addCenter(new JLabel("test center"))
                ;


        // Panel zum Kartenlayout hinzufügen
        addLayoutComponent(View.view.feedbackFrame, getTuioComponent(), "app");
        addLayoutComponent(View.view.displayFrame,displayComponent , "app");
        view.feedbackFrame.setJMenuBar(null);




        // Panel aufdecken
        setLayout(View.view.feedbackFrame,"app");
        setLayout(View.view.displayFrame,"app");

        view.showWindow();

    }

}
