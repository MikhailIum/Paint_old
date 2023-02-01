import java.awt.*;

public class Line {
    Point firstPoint;
    Point secondPoint;
    Color color;
    Stroke stroke;

    Line(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.color = secondPoint.color;
        this.stroke = secondPoint.stroke;
    }
}
