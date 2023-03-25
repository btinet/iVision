package tk.ivision.view.app;

import tk.ivision.controller.AppController;
import tk.ivision.core.global.Resource;

import javax.swing.*;
import java.awt.*;

public class AppMenuBar {

    public JMenuBar getComponent(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(224,224,224));

        JMenu menu0 = new JMenu("Datei");
        JMenuItem menu0_1 = new JMenuItem("beenden", new ImageIcon(Resource.getImage("icons/icons8_view_all_16px.png")));
        menu0_1.addActionListener((new AppController()::index));
        menu0.add(menu0_1);

        JMenu menu1 = new JMenu("Präsentation");
        JMenuItem menu1_1 = new JMenuItem("Reactable Modus", new ImageIcon(Resource.getImage("icons/icons8_view_all_16px.png")));
        JMenuItem menu1_2 = new JMenuItem("Präsentation starten", new ImageIcon(Resource.getImage("icons/icons8_eye_16px.png")));

        JMenu menu2 = new JMenu("Ansicht");
        JMenuItem menu2_1 = new JMenuItem("Vollbild umschalten", new ImageIcon(Resource.getImage("icons/icons8_lips_16px.png")));

        menu1_1.addActionListener((new AppController()::index));
        menu1_1.setActionCommand("1");

        menu1_2.addActionListener((new AppController()::index));
        menu1_2.setActionCommand("1");
        menu1.add(menu1_1);
        menu1.add(menu1_2);
        menu2.add(menu2_1);
        menuBar.add(menu0);
        menuBar.add(menu2);
        menuBar.add(menu1);


        return menuBar;
    }

}
