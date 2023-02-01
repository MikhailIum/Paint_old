import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuBar extends JMenuBar {
    PrintWriter out;

    public MenuBar(Frame frame) throws FileNotFoundException {

        Font font = new Font("Verdana", Font.PLAIN, 11);

        JMenu File = new JMenu("File");
        File.setFont(font);
        this.add(File);

        JMenuItem save = new JMenuItem("save as");
        save.setFont(font);
        File.add(save);

        JMenuItem open = new JMenuItem("open");
        open.setFont(font);
        File.add(open);

        JMenuItem newPainting = new JMenuItem("new");
        newPainting.setFont(font);
        File.add(newPainting);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog((Frame) null);
                dialog.setVisible(true);                  // Показали окно и данный вызов не завершиться, пока пользователь не выберет файл или не закроет окно
                String directory = "paintings/"; // Узнали папку выбранную пользователем (может быть null)
                String filename = dialog.getFile();       // Узнали файл выбранный пользователем (может быть null)
                dialog.dispose();                         // Сказали что все ресурсы связанные с диалоговым окном можно освободить
                if (filename == null) {
                    System.out.println("Файл не выбран!");
                    return;
                }
                String path = directory + filename;       // Сложив папку и название файла - получает полный путь к файлу
                System.out.println("Выбранный файл: " + path);

                try {
                    out = new PrintWriter(path);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }


                for (Point arrayOfPoint : frame.arrayOfPoints) {
                    out.println("Point");

                    out.println(arrayOfPoint.x);
                    out.println(arrayOfPoint.y);
                    out.println(arrayOfPoint.color.getRGB());
                    out.println(arrayOfPoint.strokeInt);
                }

                System.out.println(frame.arrayOfBrushstrokes);
                for (Brushstroke arrayOfBrushstroke: frame.arrayOfBrushstrokes){
                    for(int i = 0; i < arrayOfBrushstroke.arrayOfLines.size(); ++i){
                        out.println("Brushstroke");

                        Point firstPoint = arrayOfBrushstroke.arrayOfLines.get(i).firstPoint;
                        out.println("first");
                        out.println(firstPoint.x);
                        out.println(firstPoint.y);
                        out.println(firstPoint.color.getRGB());
                        out.println(firstPoint.strokeInt);

                        out.println("Brushstroke");
                        Point secondPoint = arrayOfBrushstroke.arrayOfLines.get(i).secondPoint;
                        out.println("second");
                        out.println(secondPoint.x);
                        out.println(secondPoint.y);
                        out.println(secondPoint.color.getRGB());
                        out.println(secondPoint.strokeInt);
                    }
                    out.println("End of a Brushstroke");
                }

                out.close();
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog dialog = new FileDialog((Frame) null);
                dialog.setVisible(true);
                String directory = dialog.getDirectory();
                String filename = dialog.getFile();
                dialog.dispose();
                if (filename == null || directory == null){
                    System.out.println("Файл не выбран!");
                    return;
                }
                String path = directory + filename;
                java.io.File file = new File(path);
                Scanner scanner = null;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                Point firstPoint = null;
                ArrayList<Line> arrayOfLines = new ArrayList<>();
                while (true){
                    assert scanner != null;
                    if (!scanner.hasNext()) break;
                    switch (scanner.nextLine()) {
                        case "Point":
                            int x = Integer.parseInt(scanner.nextLine());
                            int y = Integer.parseInt(scanner.nextLine());
                            Color color = new Color(Integer.parseInt(scanner.nextLine()));
                            int strokeInt = Integer.parseInt(scanner.nextLine());

                            frame.arrayOfPoints.add(new Point(x, y, color, new BasicStroke(strokeInt), strokeInt));
                            frame.client.sendLine(x + " " + y + " " + color.getRed() + " " + color.getGreen() + " " +
                                    color.getBlue() + " " + strokeInt);
                            break;
                        case "Brushstroke":
                            if (scanner.nextLine().equals("first")) {
                                x = Integer.parseInt(scanner.nextLine());
                                y = Integer.parseInt(scanner.nextLine());
                                color = new Color(Integer.parseInt(scanner.nextLine()));
                                strokeInt = Integer.parseInt(scanner.nextLine());

                                firstPoint = new Point(x, y, color, new BasicStroke(strokeInt), strokeInt);
                            } else {
                                x = Integer.parseInt(scanner.nextLine());
                                y = Integer.parseInt(scanner.nextLine());
                                color = new Color(Integer.parseInt(scanner.nextLine()));
                                strokeInt = Integer.parseInt(scanner.nextLine());

                                assert firstPoint != null;
                                arrayOfLines.add(new Line(firstPoint, new Point(x, y, color, new BasicStroke(strokeInt), strokeInt)));
                                frame.client.sendLine(firstPoint.x + " " + firstPoint.y + " " + x + " " + y + " " +
                                        color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + strokeInt);
                            }
                            break;
                        case "End of a Brushstroke":
                            frame.arrayOfBrushstrokes.add(new Brushstroke(arrayOfLines));
                            frame.client.sendLine("Brushstroke");
                            arrayOfLines = new ArrayList<>();
                            break;
                    }
                }
                frame.repaint();
            }
        });

        newPainting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.client.sendLine("new");

                frame.arrayOfBrushstrokes.clear();
                frame.arrayOfLines.clear();
                frame.arrayOfPoints.clear();

                frame.repaint();
            }
        });

    }
}
