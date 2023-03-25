package tk.ivision.core.view;

import TUIO.*;

import tk.ivision.core.global.SystemColor;
import tk.ivision.core.objects.TuioDemoBlob;
import tk.ivision.core.objects.TuioDemoObject;
import tk.ivision.core.objects.feedbackObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class TuioComponent extends JComponent implements TuioListener, SystemColor {

    private Hashtable<Long, TuioObject> objectList = new Hashtable<Long,TuioObject>();
    private Hashtable<Long, TuioCursor> cursorList = new Hashtable<Long,TuioCursor>();
    private Hashtable<Long, TuioBlob>   blobList   = new Hashtable<Long,TuioBlob>();

    public static final int finger_size = 18;
    public static final int object_size = 240;
    public static final int table_size = 760;

    public static int width, height;
    private float scale = 1.0f;
    public boolean verbose = false;

    Color bgrColor = SECONDARY;
    Color curColor = PRIMARY;
    Color objColor = SUCCESS;
    Color blbColor = WARNING;

    public TuioComponent () {
        View.view.setTuioComponent(this);
    }

    public void setSize(int w, int h) {
        super.setSize(w,h);
        width = w;
        height = h;
        scale  = height/(float)TuioComponent.table_size;
    }

    public void addTuioObject(TuioObject tobj) {
        feedbackObject demo = new feedbackObject(tobj);
        objectList.put(tobj.getSessionID(),demo);

        if (verbose)
            System.out.println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
    }

    public void updateTuioObject(TuioObject tobj) {

        feedbackObject demo = (feedbackObject)objectList.get(tobj.getSessionID());
        demo.update(tobj);

        if (verbose)
            System.out.println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
    }

    public void removeTuioObject(TuioObject tobj) {
        objectList.remove(tobj.getSessionID());

        if (verbose)
            System.out.println("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
    }

    public void addTuioCursor(TuioCursor tcur) {

        if (!cursorList.containsKey(tcur.getSessionID())) {
            cursorList.put(tcur.getSessionID(), tcur);
            repaint();
        }

        if (verbose)
            System.out.println("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY());
    }

    public void updateTuioCursor(TuioCursor tcur) {

        repaint();

        if (verbose)
            System.out.println("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
    }

    public void removeTuioCursor(TuioCursor tcur) {

        cursorList.remove(tcur.getSessionID());
        repaint();

        if (verbose)
            System.out.println("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
    }

    public void addTuioBlob(TuioBlob tblb) {
        TuioDemoBlob demo = new TuioDemoBlob(tblb);
        blobList.put(tblb.getSessionID(),demo);

        if (verbose)
            System.out.println("add blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle());
    }

    public void updateTuioBlob(TuioBlob tblb) {

        TuioDemoBlob demo = (TuioDemoBlob)blobList.get(tblb.getSessionID());
        demo.update(tblb);

        if (verbose)
            System.out.println("set blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getMotionSpeed()+" "+tblb.getRotationSpeed()+" "+tblb.getMotionAccel()+" "+tblb.getRotationAccel());
    }

    public void removeTuioBlob(TuioBlob tblb) {
        blobList.remove(tblb.getSessionID());

        if (verbose)
            System.out.println("del blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+")");
    }


    public void refresh(TuioTime frameTime) {
        repaint();
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {


        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(bgrColor);
        g2.fillRect(0,0,width,height);

        int w = (int)Math.round(width-scale*finger_size/2.0f);
        int h = (int)Math.round(height-scale*finger_size/2.0f);

        Enumeration<TuioCursor> cursors = cursorList.elements();
        while (cursors.hasMoreElements()) {
            TuioCursor tcur = cursors.nextElement();
            if (tcur==null) continue;
            ArrayList<TuioPoint> path = tcur.getPath();
            TuioPoint current_point = path.get(0);
            if (current_point!=null) {
                // draw the cursor path
                g2.setPaint(Color.blue);

                for (int i=0;i<path.size();i++) {
                    TuioPoint next_point = path.get(i);
                    // g2.drawLine(current_point.getScreenX(w), current_point.getScreenY(h), next_point.getScreenX(w), next_point.getScreenY(h));
                    current_point = next_point;
                }
            }

            // draw the finger tip
            g2.setPaint(blbColor);
            int s = (int)(scale*finger_size);
            g2.fillOval(current_point.getScreenX(w-s/2),current_point.getScreenY(h-s/2),s,s);
            g2.setPaint(Color.black);
            g2.drawString(tcur.getCursorID()+"",current_point.getScreenX(w),current_point.getScreenY(h));
        }
        g2.setPaint(blbColor);
        // draw the objects
        Enumeration<TuioObject> objects = objectList.elements();
        while (objects.hasMoreElements()) {
            feedbackObject tobj = (feedbackObject) objects.nextElement();
            if (tobj!=null) tobj.paint(g2, width,height);
        }
    }

}
