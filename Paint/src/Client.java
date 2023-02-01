import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends MessageListener {

    StreamWorker postman;
    Frame frame;
//    ArrayList<Line> lines = new ArrayList<>();

    public Client(Frame frame) {
        this.frame = frame;
    }

    public void start() throws IOException {
        final String host = "192.168.0.199";
        int port = 2390;

        Socket socket = new Socket(host, port);

        postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream());

        postman.addListener(this);
        postman.start();
    }

    public void sendLine(String text){
        postman.sendMessage(text);
    }

    @Override
    public void onMessage(String text) {
        System.out.println(text);
        frame.isDragging = true;

        if (text.equals("new")){
            frame.arrayOfBrushstrokes.clear();
            frame.arrayOfLines.clear();
            frame.arrayOfPoints.clear();

            frame.repaint();
        }
        else if (text.equals("Brushstroke")) {
            frame.arrayOfBrushstrokes.add(new Brushstroke(frame.arrayOfLines));
            frame.arrayOfLines = new ArrayList<>();
        }
        else {
            String[] splitText = text.split(" ");

            if (splitText.length == 6) {

                Color color = new Color(Integer.parseInt(splitText[2]), Integer.parseInt(splitText[3]), Integer.parseInt(splitText[4]));
                Stroke stroke = new BasicStroke(Integer.parseInt(splitText[5]));

                Point point = new Point(Integer.parseInt(splitText[0]), Integer.parseInt(splitText[1]), color, stroke, Integer.parseInt(splitText[5]));

                frame.arrayOfPoints.add(point);

            } else {
                Color color = new Color(Integer.parseInt(splitText[4]), Integer.parseInt(splitText[5]), Integer.parseInt(splitText[6]));
                Stroke stroke = new BasicStroke(Integer.parseInt(splitText[7]));

                Point firstPoint = new Point(Integer.parseInt(splitText[0]), Integer.parseInt(splitText[1]), color, stroke, Integer.parseInt(splitText[7]));
                Point secondPoint = new Point(Integer.parseInt(splitText[2]), Integer.parseInt(splitText[3]), color, stroke, Integer.parseInt(splitText[7]));

                frame.arrayOfLines.add(new Line(firstPoint, secondPoint));
            }
        }
        frame.repaint();
    }


    @Override
    public void onDisconnect() {

    }
}
