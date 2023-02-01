import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends MessageListener{

    private  final static ArrayList<StreamWorker> postmen = new ArrayList<>();
    private static ArrayList<String> info = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        final int port = 2390;

        ServerSocket serverDoorMan = new ServerSocket(port);

        Server server = new Server();

        while(true){
            Socket socket = serverDoorMan.accept();
            StreamWorker postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream());

            postman.addListener(server);
            postman.start();
            postmen.add(postman);

            for (String s : info) {
                postman.sendMessage(s);
            }
        }

    }

    @Override
    public void onMessage(String text) {
        System.out.println(text);
        info.add(text);
        for (StreamWorker postman: postmen){
            postman.sendMessage(text);
        }

    }

    @Override
    public void onDisconnect() {

    }
}
