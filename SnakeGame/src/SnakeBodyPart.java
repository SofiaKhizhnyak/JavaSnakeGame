
//מחלקה המייצגת חלק גוף בגופו של הנחש
public class SnakeBodyPart extends Point{
    //הסימן שמייצג את הנחש על לוח המשחק
    public static final char SNAKE_CHAR = '*';

    //הבנאי של מחלקה זו קורא לבנאי של מחלקת האב ממנה הוא יורש את התכונות והמתודות
    public SnakeBodyPart(int x, int y){
        super(x, y);
    }

}