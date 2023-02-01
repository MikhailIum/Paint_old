import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Brushstroke {
    ArrayList <Line> arrayOfLines;
    Color color;
    Stroke stroke;

    Brushstroke(ArrayList <Line> arrayOfLines){
        this.arrayOfLines = arrayOfLines;
        if (!arrayOfLines.isEmpty()) {
            this.color = arrayOfLines.get(0).color;
            this.stroke = arrayOfLines.get(0).stroke;
        }
        else stroke = new BasicStroke(7);
    }
}
