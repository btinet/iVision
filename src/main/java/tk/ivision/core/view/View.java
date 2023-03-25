package tk.ivision.core.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.*;
import tk.ivision.controller.AppController;
import tk.ivision.core.global.Resource;
import tk.ivision.core.global.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class View {

    public static View view;
    public JFrame feedbackFrame;
    public JFrame displayFrame;
    public CardLayout cardLayout = new CardLayout();

    public final int window_width  = 800;
    public final int window_height = 600;

    private boolean fullscreen = false;

    private TuioComponent tuioComponent;
    private GraphicsDevice device;

    private ArrayList<GraphicsDevice> devices = new ArrayList<>();
    private Cursor invisibleCursor;

    public View(){

        // Look and Feel Instanz erstellen
        try {
            UIManager.setLookAndFeel( new FlatLightLaf());
            FlatVuesionIJTheme.setup();
            // FlatGitHubDarkContrastIJTheme.setup();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        // View global verfügbar machen
        view = this;

        this.init();
    }

    protected void init() {



        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int deviceCount = devices.length;
        System.out.println(deviceCount);
        for (GraphicsDevice device : devices) {
            System.out.println(device);
        }

        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        System.out.println(centerPoint);
        invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "invisible cursor");



        setupWindow();

        // Zum Login-Gate weiterleiten
        Response.redirectToController(new AppController()::index);

    }

    public void setTuioComponent(TuioComponent tuioComponent) {
        this.tuioComponent = tuioComponent;
    }

    public void setupWindow() {

        feedbackFrame = new JFrame();

        displayFrame = new JFrame();
        displayFrame.setTitle("iVision Content Beamer");
        displayFrame.setIconImage(new ImageIcon(Resource.getImage("icons/favicon.png")).getImage());
        displayFrame.setLayout(this.cardLayout);
        displayFrame.setResizable(false);
        displayFrame.pack();
        displayFrame.setSize(window_width,window_height);
        displayFrame.setVisible(true);


        feedbackFrame.setTitle("iVision Feedback Beamer");
        feedbackFrame.setIconImage(new ImageIcon(Resource.getImage("icons/favicon.png")).getImage());
        feedbackFrame.setLayout(this.cardLayout);
        feedbackFrame.setResizable(false);
        feedbackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        feedbackFrame.addWindowListener( new WindowAdapter() { public void windowClosing(WindowEvent evt) {
            System.exit(0);
        } });

        feedbackFrame.addKeyListener( new KeyAdapter() { public void keyPressed(KeyEvent evt) {
            if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit(0);
            else if (evt.getKeyCode()==KeyEvent.VK_F1) {
                destroyWindow();
                setupWindow();
                fullscreen = !fullscreen;
                showWindow();
            }
            else if (evt.getKeyCode()==KeyEvent.VK_V) tuioComponent.verbose=!tuioComponent.verbose;
        } });
    }

    public void destroyWindow() {

        feedbackFrame.setVisible(false);
        if (fullscreen) {
            device.setFullScreenWindow(null);
        }
        feedbackFrame = null;
    }

    public void showWindow() {



        if (fullscreen) {
            int width  = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            this.tuioComponent.setSize(width,height);

            feedbackFrame.setSize(width,height);
            feedbackFrame.setUndecorated(true);
            device.setFullScreenWindow(feedbackFrame);
            feedbackFrame.setCursor(invisibleCursor);
        } else {
            int width  = window_width;
            int height = window_height;
            tuioComponent.setSize(width,height);

            feedbackFrame.pack();
            Insets insets = feedbackFrame.getInsets();
            feedbackFrame.setSize(width,height +insets.top);
            this.feedbackFrame.setLocationRelativeTo(null);
            feedbackFrame.setCursor(Cursor.getDefaultCursor());
        }

        feedbackFrame.setVisible(true);
        feedbackFrame.repaint();
    }

}
