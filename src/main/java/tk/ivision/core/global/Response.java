package tk.ivision.core.global;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Response {

    public static void redirectToController(ActionListener listener){
        System.out.println("Redirect wurde ausgeführt");
        JButton redirectButton = new JButton();
        redirectButton.addActionListener(listener);
        redirectButton.doClick();
    }

}
