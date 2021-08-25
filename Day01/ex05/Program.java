import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        Menu menu = new Menu(service);
        Menu.Mode mode = Menu.Mode.Production;

        if ((args.length > 1) || (args.length == 1
                && !args[0].matches("--profile=(production|dev)"))) {
            System.out.println("Usage: --profile=(production|dev)");
            return;
        }

        if ((args.length) == 1 && (args[0].equals("--profile=dev"))) {
            mode = Menu.Mode.Dev;
        }

        menu.exec(mode);
    }
}