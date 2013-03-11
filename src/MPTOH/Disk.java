package MPTOH;


import java.awt.Color;


public class Disk
{
    int no;
    int Height ;
    int Width  ;
    int x;
    int y;
    Color c;
    public Disk(int h,int w)
    {
        Height = h;
        Width = w;

    }
    public void setColor(int r,int g,int b)
    {
        c = new Color(r,g,b);
    }

}