import java.util.ArrayList;
import java.util.Random;

//מחלקה המייצגת את המשחק עצמו ומיישמת את ממשק המחשק
public class SnakeGame implements Game {
    //תכונות המחלקה הן: נחש, ראש הנחש, פרי, משתנה עזר בוליאני (האם המשחק נגמר כלומר האם המשחק ממשיך) ולוח המשחק
    private Snake snake;
    private SnakeBodyPart head;
    private Fruit fruit;
    private boolean gameOver;
    public GameBoard gameBoard;

    public SnakeGame(){
        //הבנאי יוצא נחש בתא 10*10
        snake = new Snake(10, 10);
        //ראש הנחש הוא תמיד החלק הראשון ברשימת של חלקי גוף הנחש
        head = snake.getSnakeBody().get(0);
        //הבנאי יוצא פרי במיקום ראשוני בתא 5*5
        fruit = new Fruit(5, 5);
        //הגדרת משתנה העזר - המשחק לא נגמר
        gameOver = false;
        // יצירת לוח המשחק בגודל 20*20
        this.gameBoard = new GameBoard();
    }

    //getters & setters לתכונות המחלקה
    public Snake getSnake(){return this.snake;}
    public SnakeBodyPart getSnakeHead(){
        return this.head;
    }
    public Fruit getFruit(){
        return this.fruit;
    }
    public GameBoard getGameBoard(){
        return this.gameBoard;
    }
    public boolean getGameOver(){
        return this.gameOver;
    }


    //יישום שיטת start של הממשק - התחלת המשחק
    @Override
    public void start(){
        //הדפסת הנחיות לשחקן
        System.out.println("Let's Play the Snake Game...");
        System.out.println("In this game the Snake is represented as '*' and the Fruit is represented as '@'");
        System.out.println("The aim of the game is to make the snake eat the fruit,");
        System.out.println("move the snake towards the fruit, once the snake eats the fruit, the snake will grow.");
        System.out.println("Try to make the snake as long as possible without colliding with itself.");
        System.out.println("Press one of the following options to choose the direction for the snake to move: ");
        System.out.println("'w' to go up / 's' to go down / 'd' to go right / 'a' to go left");
        System.out.println();
        //מיקום הנחש על הלוח
        gameBoard.placeSnake(snake);
        //מיקום הפרי על הלוח
        gameBoard.placeFruit(fruit);
        //הדפסת הלוח
        gameBoard.printBoard();
    }

    //יישום שיטת stop של הממשק - הדפסת סיום המשחק ויציאה מהמשחק
    @Override
    public void stop(){
        System.out.println("Game Over!");
        System.exit(0);
    }

    //מתודה המשנה את מיקום הפרי לאחר שהפרי נאכל על ידי הנחש
    public void changeFruitPosition(){
        //נרצה לשנות את מיקום הפרי למיקום רנדומלי ולכן נשתמש במחלקת Random
        Random random = new Random();
        //לוח המשחק הוא 20*20 ולכן נגדיר מיקום רנדומלי לקואורדינטות x ו-y בטווח מספרים 0 עד 19
        int newX, newY;
        boolean overlap;
        //נגדיר את המיקום החדש של הפרי לאחר בדיקה שהוא לא מתנגש עם המיקום של חלקי גוף הנחש
        do {
            newX = random.nextInt(GameBoard.BOARD_WIDTH);
            newY = random.nextInt(GameBoard.BOARD_HEIGHT);
            overlap = false;
            for (SnakeBodyPart part : snake.getSnakeBody()) {
                if (part.getX() == newX && part.getY() == newY) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);

        //הגדרת הקואורדינטות החדשות לפרי
        this.fruit.setX(newX);
        this.fruit.setY(newY);

        //מיקום הפרי במיקומו החדש על הלוח
        gameBoard.placeFruit(fruit);
    }

    //מתודה הבודקת האם ראש הנחש מתנגש עם פרי (כלומר האם הנחש אוכל את הפרי)
    public boolean checkFruitCollision(){
        //ראש הנחש הוא החלק הראשון בגוף הנחש
        head = snake.getSnakeBody().get(0);
        //במידה והקואורדינטות של הנחש ושל הפרי זהות אז יש התנגשות = הנחש אכל פרי
        if (head.getX() == fruit.getX() && head.getY() == fruit.getY()) {
            return true;
        }
        return false;
    }

    //מתודה הבודקת האם הנחש התנגש בעצמו (כלומר האם הראש שלו התנגש באחד מחלקי הגוף האחרים)
    public void checkSnakeCollision(){
        if (this.snake.checkSelfCollision())
            this.gameOver = true;
    }
    //מתודה המעדכנת את המשחק בהתאם לבחירת כיוון הזזת הנחש (בהתאם לקלט מהמשתמש)
    public void updateGameState(char direction){
        //הזזת הנחש בהתאם לקלט
        snake.move(direction);
        //במידה וגוף הנחש גדול מ4 חלקים נבדוק האם הוא מתנגש בעצמו לאחר המהלך
        if(snake.getSnakeBody().size() > 4){
            checkSnakeCollision();
        };
        //אם הנחש התנגש בעצמו - המשחק נגמר
        if(gameOver){
            stop();
            return;
        }
        //בדיקה האם הנחש אוכל את הפרי בלוח
        if(checkFruitCollision()){
            changeFruitPosition();
            //אם הוא מתנגש בפרי = אוכל את הפרי, נגדיל את גודלו
            snake.grow();
            //מיקום הנחש המוגדל על הלוח והפרי במיקום החדש
            gameBoard.placeSnake(snake);
            gameBoard.placeFruit(fruit);
            //הדפסת הלוח המעודכן
            gameBoard.printBoard();
            //יציאה מהאיטרציה הנוכחית וכניסה לאיטרציה הבאה לקבלת קלט חדש מהמשתמש
            return;
        };

        //במידה והמשחק לא נגמר וגם הנחש לא אכל את הפרי
        //ננקה את הלוח כדי למחוק את המיקומים הישנים של הנחש
        gameBoard.clearBoard();
        //נמקם את הנחש במיקום החדש
        gameBoard.placeSnake(snake);
        //נמקם את הפרי על הלוח הנקי
        gameBoard.placeFruit(fruit);
        // הדפסת הלוח המעודכן
        gameBoard.printBoard();
        //נבקש מהמשתמש להזין כיוון למהלך הבא
        //System.out.println("Press the direction you choose to move the snake: ");
    }
}