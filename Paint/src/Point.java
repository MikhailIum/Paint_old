import java.awt.*;

public class Point {
    int x;
    int y;
    Color color;
    Stroke stroke;
    int strokeInt;

    Point(int x, int y, Color color, Stroke stroke, int strokeInt){
        this.x = x;
        this.y = y;
        this.color = color;
        this.stroke = stroke;
        this.strokeInt = strokeInt;
    }
}
