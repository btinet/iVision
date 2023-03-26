package tk.ivision.core.objects;

import TUIO.TuioObject;
import tk.ivision.core.global.SystemColor;
import tk.ivision.core.view.TuioComponent;
import tk.ivision.view.screen.DisplayComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SlideObject extends TuioObject {

    private Shape square;
    private Arc2D.Float arc;
    private Arc2D.Float arc2;
    private Arc2D.Float arc3;
    private Arc2D.Float sideA;
    private Arc2D.Float sideB;
    private Arc2D.Float sideC;
    private Arc2D.Float sideD;

    private int width;
    private int height;

    private Shape rectangle;

    public SlideObject(TuioObject tobj) {
        super(tobj);

    }

    public void paint(Graphics2D g,int dx, int dy, int width, int height) {
        this.width = width;
        this.height = height;
        g.setPaint(Color.LIGHT_GRAY);
        Rectangle surface = new Rectangle();
        surface.setBounds(dx,dy,width,height);
        this.rectangle = surface;
        g.fill(rectangle);

        System.out.println("Label hinzugefügt.");
    }

    public void paint(Graphics2D g,int dx, int dy, int width, int height, Color color) {
        this.width = width;
        this.height = height;
        g.setPaint(color);
        Rectangle surface = new Rectangle();
        surface.setBounds(dx,dy,width,height);
        this.rectangle = surface;
        g.fill(rectangle);
        String positionText = "";

        if(getAngleDegrees() <= 315 && getAngleDegrees() > 225){
            positionText = "D";
        }

        if (getAngleDegrees() <= 225 && getAngleDegrees() > 135) {
            positionText = "C";
        }

        if (getAngleDegrees() <= 135 && getAngleDegrees() > 45) {
            positionText = "B";
        }

        if (getAngleDegrees() <= 45 || getAngleDegrees() > 315) {
            positionText = "A";
        }

        System.out.println("Label hinzugefügt.");
    }

    public void update(TuioObject tobj) {

        float sideBarWidth = (width/8.0F*3.0F)/width;

        float dx = tobj.getX();
        float dy = tobj.getY();




        super.update(tobj);
    }

}
