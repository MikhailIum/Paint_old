import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButton extends JButton {
    Color color;

    ColorButton(Color color, PalettePanel palettePanel){
        this.color = color;
        this.setBackground(this.color);
        if (color != Color.WHITE) this.setBorder(new LineBorder(Color.WHITE, 0));
        this.setPreferredSize(new Dimension(40, 40));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                palettePanel.currentColor = color;
            }
        });
    }
}
