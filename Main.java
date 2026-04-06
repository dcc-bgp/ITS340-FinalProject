import controller.MusicStoreController;
import view.MusicStoreView;

public class Main {
    public static void main(String[] args) {
        MusicStoreView view = new MusicStoreView();
        new MusicStoreController(view);
        view.setVisible(true);
    }
}