//מחלקה מופשטת המייצגת אובייקט במחשק (נקודה על הלוח)

public abstract class Point{
    //תכונות המייצגות את הקואורדינטות של הנקודה
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    //getters & setters לקואורדינטות
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    protected void setX(int x){
        this.x = x;
    }
    protected void setY(int y){
        this.y = y;
    }
}