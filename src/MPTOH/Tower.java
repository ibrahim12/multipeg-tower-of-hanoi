package MPTOH;


import java.awt.Point;

public class Tower
{
    int n;
    Disk disk[] ;
    int noDisk = 1;
    int towerNo ;
    public Tower(int n,int index)
    {
        this.n = n;
        disk = new Disk[n];
        towerNo = index;
    }
    public void insert(Disk disk1)
    {
        disk[noDisk++] = disk1;
    }
    public Disk pop()
    {
        return disk[--noDisk];

    }
    public Disk top()
    {
        return disk[noDisk-1];
    }
    public int getNoDisk()
    {
        return noDisk-1;
    }
    





}
