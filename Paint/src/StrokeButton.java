import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StrokeButton extends JButton {
    Stroke stroke;
    StrokeIcon strokeIcon = new StrokeIcon();

    StrokeButton(int strokeWidth, PalettePanel palettePanel) {
        this.setPreferredSize(new Dimension(80, 40));
        stroke = new BasicStroke(strokeWidth);
        this.setIcon(strokeIcon);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.WHITE, 0));

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                palettePanel.currentStroke = stroke;
                palettePanel.currentStrokeWidth = strokeWidth;
            }
        });
    }

    class StrokeIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.BLACK);
            g2.setStroke(stroke);
            g2.drawLine(30, 20 , 80, 20);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 80;
        }

        @Override
        public int getIconHeight() {
            return 40;
        }
    }
}
