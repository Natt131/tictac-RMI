import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {

        Game igame= new Game();
        try {
        IGame stub = (IGame) UnicastRemoteObject.exportObject( igame, 0);

        Registry registry = LocateRegistry.createRegistry(8080);
        registry.bind("Game", stub);

        System.out.println("Сервер готов");
    } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    }
}