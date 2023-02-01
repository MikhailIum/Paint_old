import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class Frame extends JFrame implements MouseListener, MouseMotionListener {

    ArrayList<Point> arrayOfPoints = new ArrayList<>();
    ArrayList<Line> arrayOfLines = new ArrayList<>();
    ArrayList<Brushstroke> arrayOfBrushstrokes = new ArrayList<>();
    Color color = Color.WHITE;
    Stroke stroke = new BasicStroke(5);
    int strokeInt = 5;
    PalettePanel palettePanel;
    Client client = new Client(this);

    MenuBar menuBar = new MenuBar(this);

    Frame() throws IOException {
        this.setLayout(new BorderLayout());
        palettePanel = new PalettePanel();
        this.add(palettePanel, BorderLayout.WEST);
        this.pack();

        this.setJMenuBar(menuBar);
        this.setSize(1200,1000);
        setLocation(300, 30);
        setMinimumSize(new Dimension(800, 880));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Paint");

        this.setVisible(true);

        client.start();

        createBufferStrategy(2);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // paint
    @Override
    public void paint(Graphics g){

        BufferStrategy bufferStrategy = getBufferStrategy();        // Обращаемся к стратегии буферизации
        if (bufferStrategy == null) {                               // Если она еще не создана
            createBufferStrategy(2);                                // то создаем ее
            bufferStrategy = getBufferStrategy();                   // и опять обращаемся к уже наверняка созданной стратегии
        }
        g = bufferStrategy.getDrawGraphics();                       // Достаем текущую графику (текущий буфер)

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Brushstroke arrayOfBrushstroke : arrayOfBrushstrokes) {
            g.setColor(arrayOfBrushstroke.color);
            g2d.setStroke(arrayOfBrushstroke.stroke);
            for (int j = 0; j < arrayOfBrushstroke.arrayOfLines.size(); ++j)
                g.drawLine(arrayOfBrushstroke.arrayOfLines.get(j).firstPoint.x, arrayOfBrushstroke.arrayOfLines.get(j).firstPoint.y,
                        arrayOfBrushstroke.arrayOfLines.get(j).secondPoint.x, arrayOfBrushstroke.arrayOfLines.get(j).secondPoint.y);
        }

        for (Point arrayOfPoint : arrayOfPoints) {
            g.setColor(arrayOfPoint.color);
            g2d.setStroke(arrayOfPoint.stroke);
            g.fillOval(arrayOfPoint.x, arrayOfPoint.y, 7, 7);
        }

        if (isDragging)
            for (Line arrayOfLine : arrayOfLines) {
                g.setColor(arrayOfLine.color);
                g2d.setStroke(arrayOfLine.stroke);
                g.drawLine(arrayOfLine.firstPoint.x, arrayOfLine.firstPoint.y, arrayOfLine.secondPoint.x, arrayOfLine.secondPoint.y);
            }

        g.dispose();                // Освободить все временные ресурсы графики (после этого в нее уже нельзя рисовать)
        bufferStrategy.show();      // Сказать буферизирующей стратегии отрисовать новый буфер (т.е. поменять показываемый и обновляемый буферы местами)
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        mouseClicked(e);
        color = palettePanel.currentColor;
        stroke = palettePanel.currentStroke;
        strokeInt = palettePanel.currentStrokeWidth;
        if (e.getX() > palettePanel.getWidth() + 10) {
            arrayOfPoints.add(new Point(e.getX(), e.getY(), color, stroke, strokeInt));
            client.sendLine(e.getX() + " " + e.getY() + " " + color.getRed() + " " + color.getGreen() + " " +
                    color.getBlue() + " " + strokeInt);
            prevPoint = new Point(e.getX(), e.getY(), color, stroke, strokeInt);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isDragging) {
            arrayOfBrushstrokes.add(new Brushstroke(arrayOfLines));
            arrayOfLines = new ArrayList<>();
            client.sendLine("Brushstroke");
        }
        isDragging = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // MouseMotionListener

    Point prevPoint;
    boolean isDragging;
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > palettePanel.getWidth() + 10) {
            isDragging = true;
            arrayOfLines.add(new Line(prevPoint, new Point(e.getX(), e.getY(), color, stroke, strokeInt)));

            client.sendLine(prevPoint.x + " " + prevPoint.y + " " + e.getX() + " " + e.getY() + " " + color.getRed() + " " +
                    color.getGreen() + " " + color.getBlue() + " " + strokeInt);

            prevPoint = new Point(e.getX(), e.getY(), color, stroke, strokeInt);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
