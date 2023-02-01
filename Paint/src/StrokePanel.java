import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StrokePanel extends JPanel {
    PalettePanel palettePanel;

    StrokePanel(PalettePanel palettePanel){
        this.palettePanel = palettePanel;
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(0, 1, 10, 10));


        add(new StrokeButton(3, palettePanel));
        add(new StrokeButton(6, palettePanel));
        add(new StrokeButton(9, palettePanel));
        add(new StrokeButton(12, palettePanel));
        add(new StrokeButton(15, palettePanel));
        add(new StrokeButton(18, palettePanel));
        add(new StrokeButton(21, palettePanel));
        add(new StrokeButton(24, palettePanel));
    }
}
