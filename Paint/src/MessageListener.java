import java.util.ArrayList;

public abstract class MessageListener {
    public abstract void onMessage(String text);

    public abstract void onDisconnect();

    public void onException(Exception e) {
        e.printStackTrace();
    }
}
