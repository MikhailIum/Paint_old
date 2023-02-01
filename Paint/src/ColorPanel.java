import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ColorPanel extends JPanel {
    PalettePanel palettePanel;

    ColorPanel(PalettePanel palettePanel){
        this.setBackground(Color.WHITE);
        this.palettePanel = palettePanel;
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setLayout(new GridLayout(0, 2, 10, 10));


        this.add(new ColorButton(Color.WHITE, palettePanel));
        this.add(new ColorButton(Color.BLACK, palettePanel));
        this.add(new ColorButton(Color.BLUE, palettePanel));
        this.add(new ColorButton(Color.CYAN, palettePanel));
        this.add(new ColorButton(Color.ORANGE, palettePanel));
        this.add(new ColorButton(Color.DARK_GRAY, palettePanel));
        this.add(new ColorButton(Color.GRAY, palettePanel));
        this.add(new ColorButton(Color.GREEN, palettePanel));
        this.add(new ColorButton(Color.LIGHT_GRAY, palettePanel));
        this.add(new ColorButton(Color.MAGENTA, palettePanel));
        this.add(new ColorButton(Color.PINK, palettePanel));
        this.add(new ColorButton(Color.RED, palettePanel));
        this.add(new ColorButton(Color.YELLOW, palettePanel));
        this.add(new ColorButton(new Color(106,44,112), palettePanel));
        this.add(new ColorButton(new Color(220,214,247), palettePanel));
        this.add(new ColorButton(new Color(0, 217, 255), palettePanel));



    }
}
