import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class StreamWorker implements Runnable, Closeable {

    private final BufferedReader in;
    private final PrintWriter out;

    private final List<MessageListener> listeners = new ArrayList();

    private final Object outputLock = new Object();   // Не обращайте внимания, эти два объекта
    private final Object listenerLock = new Object(); // используются в synchronized-блоках

    public StreamWorker(InputStream input, OutputStream output) {
        in = new BufferedReader(new InputStreamReader(input));
        out = new PrintWriter(output, true);
    }

    public void addListener(MessageListener listener){
        listeners.add(listener);
    }

    @Override
    public void run() {
        try{
            String s;
            while ((s = in.readLine()) != null){
                synchronized (listenerLock){
                    for (MessageListener listener: listeners)
                        listener.onMessage(s);
                }
            }
        } catch (SocketException e){
            if (e.getMessage().equals("Connection reset"))
            synchronized (listenerLock){
                for (MessageListener listener: listeners)
                    listener.onDisconnect();
            }

            else {
                synchronized (listenerLock) {
                    for (MessageListener listener : listeners)
                        listener.onException(e);
                }
            }
        } catch (IOException e){
            synchronized (listenerLock){
                for (MessageListener listener: listeners)
                    listener.onException(e);
            }
        }
    }

    public void start(){
        Thread thread = new Thread(this, "StreamWorker");
        thread.start();
    }

    public void sendMessage(String text){
        synchronized (outputLock) {
            out.println(text);
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }
}
