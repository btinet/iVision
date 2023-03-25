package tk.ivision.core.objects;

import TUIO.TuioObject;
import TUIO.TuioPoint;
import com.formdev.flatlaf.util.Animator;
import com.sun.corba.se.spi.ior.Writeable;
import com.sun.xml.internal.ws.spi.db.PropertySetter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import tk.ivision.core.global.SystemColor;
import tk.ivision.core.view.TuioComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;

public class feedbackObject extends TuioObject {

    private Shape square;
    private Arc2D.Float arc;
    private Arc2D.Float arc2;
    private Arc2D.Float arc3;
    private Arc2D.Float sideA;
    private Arc2D.Float sideB;
    private Arc2D.Float sideC;
    private Arc2D.Float sideD;

    public feedbackObject(TuioObject tobj) {
        super(tobj);
        int size = TuioComponent.object_size;
        arc = new Arc2D.Float(Arc2D.OPEN);
        arc2 = new Arc2D.Float(Arc2D.OPEN);
        arc3 = new Arc2D.Float(Arc2D.OPEN);
        sideA = new Arc2D.Float(Arc2D.OPEN);
        sideB = new Arc2D.Float(Arc2D.OPEN);
        sideC = new Arc2D.Float(Arc2D.OPEN);
        sideD = new Arc2D.Float(Arc2D.OPEN);


        sideA.setFrame(-(80+size)/2,-(80+size)/2,80+size,80+size);
        sideB.setFrame(-(80+size)/2,-(80+size)/2,80+size,80+size);
        sideC.setFrame(-(80+size)/2,-(80+size)/2,80+size,80+size);
        sideD.setFrame(-(80+size)/2,-(80+size)/2,80+size,80+size);

        sideA.setAngleStart(55);
        sideA.setAngleExtent(70);

        sideB.setAngleStart(145);
        sideB.setAngleExtent(70);

        sideC.setAngleStart(235);
        sideC.setAngleExtent(70);

        sideD.setAngleStart(325);
        sideD.setAngleExtent(70);

        arc2.setFrame(-size/2,-size/2,size,size);
        arc3.setFrame(-size/2,-size/2,size,size);
        arc.setFrame(-(160+size)/2,-(160+size)/2,160+size,160+size);

        arc.setAngleStart(90);
        arc2.setAngleStart(tobj.getAngleDegrees()+67.5);
        arc2.setAngleExtent(45);
        arc3.setAngleStart(0);
        arc3.setAngleExtent(360);

        square = arc;

        AffineTransform transform = new AffineTransform();
        transform.translate(xpos,ypos);
        transform.rotate(angle,xpos,ypos);
        square = transform.createTransformedShape(arc);
    }

    public void paint(Graphics2D g, int width, int height) {

        float Xpos = xpos*width;
        float Ypos = ypos*height;
        float scale = height/(float)TuioComponent.table_size;

        AffineTransform trans = new AffineTransform();
        trans.translate(-xpos,-ypos);
        trans.translate(Xpos,Ypos);
        trans.scale(scale,scale);
        Shape s = trans.createTransformedShape(arc);
        Shape s2 = trans.createTransformedShape(arc2);
        Shape s3 = trans.createTransformedShape(arc3);
        Shape shapeA = trans.createTransformedShape(sideA);
        Shape shapeB = trans.createTransformedShape(sideB);
        Shape shapeC = trans.createTransformedShape(sideC);
        Shape shapeD = trans.createTransformedShape(sideD);



        g.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));

        String positionText = "";

        if(getAngleDegrees() <= 315 && getAngleDegrees() > 225){
            g.setPaint(Color.WHITE);
            g.draw(shapeD);
            g.setPaint(SystemColor.PRIMARY);
            g.draw(shapeB);
            g.draw(shapeA);
            g.draw(shapeC);
            positionText = "D";
        }

        if (getAngleDegrees() <= 225 && getAngleDegrees() > 135) {
            g.setPaint(Color.WHITE);
            g.draw(shapeC);
            g.setPaint(SystemColor.PRIMARY);
            g.draw(shapeD);
            g.draw(shapeA);
            g.draw(shapeB);
            positionText = "C";
        }

        if (getAngleDegrees() <= 135 && getAngleDegrees() > 45) {
            g.setPaint(Color.WHITE);
            g.draw(shapeB);
            g.setPaint(SystemColor.PRIMARY);
            g.draw(shapeC);
            g.draw(shapeA);
            g.draw(shapeD);
            positionText = "B";
        }

        if (getAngleDegrees() <= 45 || getAngleDegrees() > 315) {
            g.setPaint(Color.WHITE);
            g.draw(shapeA);
            g.setPaint(SystemColor.PRIMARY);
            g.draw(shapeB);
            g.draw(shapeC);
            g.draw(shapeD);
            positionText = "A";
        }



        g.setStroke(new BasicStroke(24, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER));

        g.setPaint(SystemColor.PRIMARY);
        g.draw(s3);

        g.setStroke(new BasicStroke(24, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));

        g.setPaint(Color.WHITE);
        g.draw(s2);

        g.setStroke(new BasicStroke(24, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));

        g.setPaint(SystemColor.PRIMARY);

        g.draw(s);
        //g.setColor(new Color(220, 190, 30));
        Font font = new Font("Tahoma", Font.BOLD, 36);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(-1, 1);
        Font rotatedFont = font.deriveFont(affineTransform);

        g.setPaint(Color.WHITE);
            g.setFont(rotatedFont);
            g.scale(1,1);
            g.drawString(positionText,Xpos+10,Ypos+TuioComponent.object_size+60);



    }

    public Color getColor(Color color){
        return color;
    }
    public synchronized void processLoop(Color color) {

    }

    public void update(TuioObject tobj) {

        square = arc;

        float dx = tobj.getX() - xpos;
        float dy = tobj.getY() - ypos;
        float da = tobj.getAngle() - angle;
        float dad = tobj.getAngleDegrees();

        if ((dx!=0) || (dy!=0)) {
            AffineTransform trans = AffineTransform.getTranslateInstance(dx,dy);
            square =  trans.createTransformedShape(square);
        }

        if (da!=0) {
            AffineTransform trans = AffineTransform.getRotateInstance(da,tobj.getX(),tobj.getY());
            arc.setAngleStart(90);
            arc2.setAngleStart(-dad+67.5);

            sideA.setAngleStart(55-dad);
            sideB.setAngleStart(145-dad);
            sideC.setAngleStart(235-dad);
            sideD.setAngleStart(325-dad);

            square = trans.createTransformedShape(square);
        }

        super.update(tobj);
    }

}
