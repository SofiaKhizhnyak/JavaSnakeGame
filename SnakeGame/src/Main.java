import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //יצירת משחק חדש
        SnakeGame game = new SnakeGame();
        //התחלת המשחק
        game.start();

        //לופ המשחק בהתאם לסטטוס המשחק
        while (!game.getGameOver()) {
            // בסיום מהלך השחקן - לפני הדפסת הלוח המעודכן
            // נמחק את הלוח הישן מהקונסולה כדי לראות את הלוח החדש בצורה נוחה יותר בקונסולה
            System.out.print("\033[H\033[2J");
            System.out.flush();

            //קליטת הקלט מהמשתש - כיוון הזזת הנחש
            char userInput = scanner.next().charAt(0);
            //עדכון המשחק בהתאם לקלט מהמשתש
            game.updateGameState(userInput);
            //נבקש מהמשתמש להזין כיוון למהלך הבא
            System.out.println("Press the direction you choose to move the snake: ");
        }
    }

}