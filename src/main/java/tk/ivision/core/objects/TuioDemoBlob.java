package tk.ivision.core.objects;

import TUIO.TuioBlob;
import tk.ivision.core.view.TuioComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class TuioDemoBlob extends TuioBlob {

    private Shape square;

    public TuioDemoBlob(TuioBlob tblb) {
        super(tblb);
        int size = TuioComponent.object_size;
        square = new Rectangle2D.Float(-size/2,-size/2,size,size);

        AffineTransform transform = new AffineTransform();
        transform.translate(xpos,ypos);
        transform.rotate(angle,xpos,ypos);
        square = transform.createTransformedShape(square);
    }

    public void paint(Graphics2D g, int width, int height) {

        float Xpos = xpos*width;
        float Ypos = ypos*height;
        float scale = height/(float) TuioComponent.table_size;

        AffineTransform trans = new AffineTransform();
        trans.translate(-xpos,-ypos);
        trans.translate(Xpos,Ypos);
        trans.scale(scale,scale);
        Shape s = trans.createTransformedShape(square);

        g.setPaint(Color.black);
        g.fill(s);
        g.setPaint(Color.white);
        g.drawString(blob_id+"",Xpos-10,Ypos);
    }

    public void update(TuioBlob tblb) {

        float dx = tblb.getX() - xpos;
        float dy = tblb.getY() - ypos;
        float da = tblb.getAngle() - angle;

        if ((dx!=0) || (dy!=0)) {
            AffineTransform trans = AffineTransform.getTranslateInstance(dx,dy);
            square = trans.createTransformedShape(square);
        }

        if (da!=0) {
            AffineTransform trans = AffineTransform.getRotateInstance(da,tblb.getX(),tblb.getY());
            square = trans.createTransformedShape(square);
        }

        super.update(tblb);
    }

}
