package tk.ivision.core.controller;

import TUIO.TuioListener;
import tk.ivision.core.global.Response;
import tk.ivision.core.global.SystemColor;
import tk.ivision.core.view.TuioComponent;
import tk.ivision.core.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, SystemColor {

    private TuioComponent tuioComponent;

    protected View view = View.view;

    public Controller(TuioComponent tuioComponent) {
        this.tuioComponent = tuioComponent;
    }

    public void addLayoutComponent(JFrame targetFrame, Component c, String constraint){
        targetFrame.add(c,constraint);
    }

    public void setLayout(JFrame targetFrame,String constraint){
        this.view.cardLayout.show(targetFrame.getContentPane(), constraint);
    }

    // TODO: Redirect Workaround mit ActionListener auf unsichtbaren Button "richtig" implementieren.
    public void redirectToController(ActionListener listener){
        Response.redirectToController(listener);
    }

    public TuioListener getTuioListener() {
        return this.tuioComponent;
    }

    public TuioComponent getTuioComponent() {
        return this.tuioComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
