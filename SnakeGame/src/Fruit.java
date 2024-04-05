//מחלקה המייצגת את הפרי

public class Fruit extends Point{
    //התו שמייצג את הפרי על לוח המשחק
    public static final char FRUIT_CHAR = '@';
    //הבנאי של מחלקה זו קורא לבנאי של מחלקת האב ממנה הוא יורש את התכונות והמתודות
    public Fruit(int x, int y){
        super(x, y);
    }
}