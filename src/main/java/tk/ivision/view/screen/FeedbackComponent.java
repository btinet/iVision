package tk.ivision.view.screen;

import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import tk.ivision.core.objects.TuioDemoObject;
import tk.ivision.core.objects.feedbackObject;
import tk.ivision.core.view.TuioComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class FeedbackComponent extends TuioComponent {

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

    public void update(Graphics g) {


        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(bgrColor);
        g2.fillRect(0,0,width,height);

        g2.setColor(blbColor);
        g2.fillRect(width/8*3,20,5,height-40);

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
