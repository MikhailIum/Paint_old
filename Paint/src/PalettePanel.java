import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PalettePanel extends JPanel {
    ColorPanel colorPanel = new ColorPanel(this);
    StrokePanel strokePanel = new StrokePanel(this);

    Stroke currentStroke = new BasicStroke(5);
    Color currentColor = Color.BLACK;
    int currentStrokeWidth = 5;

    PalettePanel(){
        setLayout(new BorderLayout());
        add(colorPanel, BorderLayout.NORTH);
        add(strokePanel, BorderLayout.SOUTH);
        this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        this.setBackground(Color.WHITE);
    }
}
