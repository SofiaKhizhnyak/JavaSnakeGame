import java.util.ArrayList;
// מחלקת המייצגת את הנחש
public class Snake{
    //רשימה המחזיקה אובייקטים מסוג חלקי גוף של נחש המייצגת את גוף הנחש
    private ArrayList<SnakeBodyPart> snakeBody;

    public Snake(int x, int y) {
        snakeBody = new ArrayList<SnakeBodyPart>();
        snakeBody.add(new SnakeBodyPart(x, y));
    }

    //getter לגוף הנחש
    //לא נגדיר setter כיוון שהנחש אמור לגדול רק בעת שהוא מתנגש עם פרי ואוכל אותו, במידה ויהיה setter פומבי, השחקן יוכל לרמות במחשק
    public ArrayList<SnakeBodyPart> getSnakeBody(){
        return this.snakeBody;
    }

    //מתודה שמזיזה את ראש הנחש בהתאם לקלט שהוא כיוון ההתקדמות שמוזן על ידי השחקן ולבסוף מעדכנת את מיקום כל גוף הנחש
    public void move(char direction) {
        //תזוזת הנחש מתבססת על מיקום ראש הנחש, כיוון שבעת גדילת הנחש החלק החדש של גוף הנחש יתווסף לזנב (לסוף הרשימה)
        SnakeBodyPart head = snakeBody.get(0);
        // משתני עזר לקביעת מיקום חדש לראש הנחש בהתאם לקלט מהשחקן
        int newX = head.getX();
        int newY = head.getY();

        //חישוב המיקום החדש לראש הנחש בהתאם לקלט
        switch (direction) {
            case 'w': //למעלה
                //נבדוק שהנחש לא יוצא מגבולות הלוח
                if (head.getY() > 0) {
                    newY = head.getY() - 1;
                }
                else{
                    //במידה והשחקן בחר כיוון שההתקדמות בו תגרום ליציאת מגבולות הלוח
                    System.out.println("Invalid direction. Please use 's', 'd', or 'a'.");
                    // יציאה מהאיטרציה הנוכחית וכניסה לאיטרציה הבאה לקבלת קלט חדש מהמשתמש
                    return;
                }
                break;
            case 's': //למטה
                if (head.getY() < GameBoard.BOARD_HEIGHT - 1) {
                    newY = head.getY() + 1;
                }
                else{
                    System.out.println("Invalid direction. Please use 'w', 'd', or 'a'.");
                    return;
                }
                break;
            case 'a': //שמאלה
                if (head.getX() > 0) {
                    newX = head.getX() - 1;
                }
                else{
                    System.out.println("Invalid direction. Please use 'w', 's' or 'd'.");
                    return;
                }
                break;
            case 'd': //ימינה
                if (head.getX() < GameBoard.BOARD_WIDTH - 1) {
                    newX = head.getX() + 1;
                }
                else{
                    System.out.println("Invalid direction. Please use 'w', 's', or 'a'.");
                    return;
                }
                break;
            default:
                //במידה והשחקן הזין קלט לא תקין - לא אחת מהאותיות שהוגדרו לבחירת כיוון להזזת הנחש
                System.out.println("Invalid char. Please use 'w', 's', 'd', or 'a' to choose you direction.");
                return;
        }
        // בדיקה האם המיקום החדש של ראש הנחש הוא לכיוון קדימה (כלומר לא לכיוון ממנו הנחש בא)
        // (אסור לנוע לכוון ממנו באת - למשל אם זזת מימין לשמאל אסור לזוז ימינה, אם זזת מלמטה למעלה אסור לזוז למטה)
        //אם גוף הנחש מורכב רק מחדש אחר (יש ראש בלבד) אפשר ללכת לכל כיוון גם אם זה הכיוון ממנו באת
        if (snakeBody.size() > 1) {
            SnakeBodyPart nextPart = snakeBody.get(1);
            if (newX == nextPart.getX() && newY == nextPart.getY()) {
                // אם הכיוון שאותו השחקן בחר הוא לחזור לכיוון ממנו הוא כרגע הגיע
                System.out.println("Invalid direction. Snake must move forward.");
                return;
                //יציאה מהאיטרציה הנוכחית וכניסה לאיטרציה הבאה לקבלת קלט חדש מהמשתמש
            }
        }

        // עדכון מיקומים של חלקי הגוף בהתאם למיקום החדש של ראש הנחש לאחר הזזתו
        //העדכון יתבצע רק כשהנחש יהיה בגודל 2 חלקי גוף לפחות
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            SnakeBodyPart currentPart = snakeBody.get(i);
            SnakeBodyPart previousPart = snakeBody.get(i - 1);
            currentPart.setX(previousPart.getX());
            currentPart.setY(previousPart.getY());
        }

        // עדכון מיקום ראש הנחש למיקומו החדש
        //עדכון הראש נעשה לאחר עדכון חלקי גוף הנחש כדי לא לגרום להתנגשות עצמית פקטיבית
        // (כדי לא לגרום לכפילות במיקומי חלקי גוף הנחש הראשון והשני שיזוהה כ-selfCollision)
        head.setX(newX);
        head.setY(newY);

    }
    //בדיקת התנגשות הנחש בעצמו במידה בהתחשב לגודל הנחש (רק מעל 4 חלקים תתכן התנגשות בעצמו בלוח המבוסס תאים כי השחקן יכול לנוע בכיוון קדימה בלבד
    public boolean checkSelfCollision() {
        //התנגשות עצמית יכולה להתרחש רק במידה וגוף הנחש גדול מ4 (תופס מעל 4 משבות
        if(snakeBody.size() > 4) {
            for (int i = 2; i < snakeBody.size(); i++) {
                SnakeBodyPart bodyPart = snakeBody.get(i);
                if (snakeBody.get(0).getX() == bodyPart.getX() && snakeBody.get(0).getY() == bodyPart.getY()) {
                    // Collision detected
                    System.out.println("Snake collided with itself!");
                    return true;
                }
            }
        }
        return false;
    }

    //הגדלת גוף הנחש לאחר אכילת פרי
    public void grow() {
        // נוסיף אובייקט חדש של חלק גוף לסוף הרשימה של גוף הנחש בהתאם למיקום הזנב של הנחש (החלק האחרון של הגוף)
        SnakeBodyPart tail = snakeBody.get(snakeBody.size() - 1);
        int newX = tail.getX();
        int newY = tail.getY();
        snakeBody.add(new SnakeBodyPart(newX, newY));
    }

}