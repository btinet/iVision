package tk.ivision.view.screen;

import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import tk.ivision.core.global.SystemColor;
import tk.ivision.core.objects.SlideObject;
import tk.ivision.core.view.BagConstraints;
import tk.ivision.core.view.TuioComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class DisplayComponent extends TuioComponent {

    protected GridBagConstraints gbc = new BagConstraints();

    protected Boolean hasSidebar = false;
    protected Boolean hasMainbar = false;

    public static Rectangle sideBar = new Rectangle();

    public DisplayComponent() {

        gbc.anchor = GridBagConstraints.CENTER;
        setLayout(new GridBagLayout());

        DisplayComponent.sideBar.setBounds(0,0,width/8*3,height-40);

    }

    public void addTuioObject(TuioObject tobj) {
        SlideObject demo = new SlideObject(tobj);
        objectList.put(tobj.getSessionID(),demo);

        if (verbose)
            System.out.println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
    }

    public void updateTuioObject(TuioObject tobj) {

        SlideObject demo = (SlideObject)objectList.get(tobj.getSessionID());

        demo.update(tobj);

        if (verbose)
            System.out.println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
    }

    public void update(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);



        g2.setColor(bgrColor);
        //g2.fillRect(0,0,width,height);




        g2.setColor(blbColor);



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
        int sy = 0;
        while (objects.hasMoreElements()) {
            SlideObject tobj = (SlideObject) objects.nextElement();

            float sideBarWidth = (width/8.0F*3.0F)/width;
            System.out.println(tobj.getX() + " und Breite: "+sideBarWidth);

            Color objectColor;

            switch (tobj.getSymbolID()) {
                case 0:
                    objectColor = SystemColor.PRIMARY;
                    break;
                case 1:
                    objectColor = SystemColor.SECONDARY;
                    break;
                case 2:
                    objectColor = SystemColor.SUCCESS;
                    break;
                default:
                    objectColor = SystemColor.DANGER;
            }



            switch(objectList.size()) {
                case 1:
                    System.out.println("1 Objekt");
                    if (tobj != null) tobj.paint(g2, 0, 0, width, height, objectColor);
                    hasSidebar = false;
                    hasMainbar = false;
                    break;

                case 2:
                    System.out.println("2 Objekte");
                    if (tobj.getX() < sideBarWidth) {
                        if (tobj != null) tobj.paint(g2, 0, 0, width / 8 * 3, height, objectColor);
                        hasSidebar = true;
                    } else {
                        if (tobj != null) tobj.paint(g2, width / 8 * 3, 0, width / 8 * 5, height, objectColor);
                        }
                    break;
                default:
                    System.out.println("3 Objekte");
                    if (tobj.getX() < sideBarWidth) {
                        if (tobj != null) tobj.paint(g2, 0, 0, width / 8 * 3, height, objectColor);
                        hasSidebar = true;
                    } else {
                        tobj.paint(g2, width / 8 * 3, height / 2 * sy, width / 8 * 5, height / 2, objectColor);
                              sy++;
                    }
            }
        }
    }

}
