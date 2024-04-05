//מחלקה המייצגת את לוח המשחק
public class GameBoard {
    //תו המסמל תא ריק
    private static final char EMPTY_CHAR = ' ';
    //תכונות המייצגות את מימדי הלוח ולא ניתנים לעדכון במהלך המשחק
    protected static final int BOARD_HEIGHT = 20;
    protected static final int BOARD_WIDTH = 20;

    //הלוח יבנה על בסיס מערך דו מימדי כך שבכל תא במערך יודפס תו של נחש / פרי או תו ריק
    private char[][] board;

    public GameBoard(){
        //יצירת הלוח
        this.board = new char[BOARD_HEIGHT][BOARD_WIDTH];
        //אתחול לוח המשחק עם תווים ריקים כדי להבטיח תצוגה אחידה בכל פלטפורמה
        // (אצלי ב-intellij ללא המתודה הזאת הלוח לא הודפס ריק אלא עם תווים מוזרים)
        clearBoard();
    }
    //מתודה לניקוי הלוח והגדרת כל התאים כתאים ריקים
    public void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                this.board[i][j] = EMPTY_CHAR;
            }
        }
    }
    //תודה למחיקת תו מתא מסוים בלוח)
    /*public void clearCell(int x, int y) {
        this.board[x][y] = EMPTY_CHAR;
    }*/

    //הדפסת הלוח
    public void printBoard(){
        System.out.println("--------------------");

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                System.out.print(this.board[i][j]);
            }
            //הדפסה לירידת שורה לפני מעבר לשורה הבאה בלוח
            System.out.println();
        }
        System.out.print("--------------------");
        System.out.println();
    }

    //מיקום הנחש על לוח המשחק
    public void placeSnake(Snake snake){
        //עוברים על גוף הנחש וממקמים כל חלק בגופו בלוח המשחק, בהתאם לקואורדינטות של כל חלק (התכונות X ו-Y)
        for (int i = 0; i < snake.getSnakeBody().size(); i++) {
            //את השורות מייצג ה-Y (תזוזה למטה או למעלה) ואת העמודות מייצד ה-X (תזוזת ימינה או שמאלה)
            this.board[snake.getSnakeBody().get(i).getY()][snake.getSnakeBody().get(i).getX()] = SnakeBodyPart.SNAKE_CHAR;
        }
    }
    //מיקום הפרי על לוח המשחק
    public void placeFruit(Fruit fruit) {
        //בהתאם לקואורדינטות של הפרי (התכונות X ו-Y)
        board[fruit.getY()][fruit.getX()] = Fruit.FRUIT_CHAR;
    }
}