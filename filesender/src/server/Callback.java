package server;

import java.util.logging.Logger;

public interface Callback {

    void emit(String message);

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("hubert");
        System.out.println(logger);
    }

}
