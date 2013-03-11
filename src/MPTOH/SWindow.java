package MPTOH;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ibrahim
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;


public class SWindow extends javax.swing.JPanel implements Runnable{


    
    int screenWidth ;
    int screenHeight;
    int delay_time  = 100;
    int moveSpeed = 100;
    int diskwInc = 5;
    int diskUP = 200;
    int PegDis ;
    int Base ;   /// See Below
    int THeight = 300;
    int TWidth = 10;
    int n,p,s,d;
    Disk disk[];
    Tower tower[];
    int diskw = 10;
    int BaseHeight = 15+10;
    int DiskHeight = 10+5;
    
    boolean first = true;
    int pegx1 = 50;    //start PegDraw  (x,y)
    int pegy1 =  screenHeight-100;//500;   //
    boolean UpdateP = false;
    boolean isMove = false;
    Thread t;
    Deque dest;
    Deque source;
    Deque disk_no;
    
    boolean Simulate = true;
    
    
    
    public SWindow(int n,int p,int s,int d,Deque source,Deque dest,Deque disk_no,Dimension sd)
    {
        this.n = n;
        this.p = p;
        this.s = s;
        this.d = d;
        screenWidth = sd.width;
        screenHeight = sd.height;
        //Base  = 10 * n + 50 ;
        //PegDis = Base + 50 ;
       // if( (p*Base + p*PegDis) > screenWidth)
        
        int h = (p+1)*5;
        Base = (screenWidth - h)/(p+1);
        PegDis = Base + 5;

        //if(DiskHeight*n >= 350)
            DiskHeight = 350/n;
        //if(diskw+n*diskwInc >= (Base-40)/2 )
                diskw = (Base-40)/n;

        //System.out.println(Base + " " + PegDis + " " + DiskHeight + " " + diskw);
            
  
        
        this.source = source;
        this.dest = dest;
        this.disk_no = disk_no;
        disk = new Disk[n+1];
        tower = new Tower[p+1];
        
        initComponents();
        
        t = new Thread(this);
        t.start();

    }

    public void DrawPeges(Graphics2D g2)
    {
        int px1 = pegx1;
        int py1 = pegy1;

        int tx1 = px1 + (Base)/2;
        int ty1 = py1-THeight;

        for(int i=1;i<=p;i++)
        {

            g2.setColor(new Color(35,40,60));
            g2.fillRoundRect(px1, py1, Base, BaseHeight,3,3);
            g2.setColor(new Color(122,40,160));
            g2.fill3DRect(px1+2, py1+2, Base-4, BaseHeight-4,true);
            

            //g2.setColor(new Color(100,150,100));
            //g2.fillRoundRect(px1, py1, Base, BaseHeight, 3, 3);

            g2.setColor(new Color(150,150,150));
            g2.fillRoundRect(tx1-1,ty1 , TWidth, THeight, 3, 3);
            int midx = tx1;
            int midy = (ty1+THeight+BaseHeight/2+5);
            g2.drawString(i+"", midx, midy);
            
            px1 += PegDis;
            tx1 += PegDis;
         
        }

    }
    void drawDisk(Graphics2D g2,Disk disk1)
    {
        

        g2.setColor(new Color(255,255,255));
        g2.drawRoundRect(disk1.x,disk1.y,disk1.Width,disk1.Height ,3,3);
        g2.setColor(disk1.c);
        g2.fillRoundRect(disk1.x,disk1.y,disk1.Width,disk1.Height ,3,3);
        g2.setColor(new Color(50,50,50));
        int midx = disk1.x+disk1.Width/2;
        int midy = disk1.y+disk1.Height/2+5;
        g2.drawString(disk1.no+"", midx, midy);

    }

    public void UpdatePeg(Graphics2D g2)
    {
//            //tower[2].insert(disk[2]);
//            //Update(g2);
//            tower[2].insert(tower[1].pop());
//            Update(g2);
//            print(tower[s].getNoDisk()+ "");
//            print(tower[2].getNoDisk()+ "");
//            print(tower[d].getNoDisk()+ "");
//
//            moveDisk(g2,2,1);
          //  print(source.front() + "->" + dest.front());
            moveDisk(g2,source.front(),dest.front());
            source.pop_front();
            dest.pop_front();
            disk_no.pop_front();
        
    }
    public void delay(int n)
    {
        try{
            Thread.sleep(n);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
    public void print(String s)
    {
        System.out.println(s);
    }

    public void moveDisk(Graphics2D g2,int s1,int d1)
    {
        
       // System.out.println(s1 + " " + d1 + " ");
       // System.out.println(tower[s1].getNoDisk());
       // System.out.println(tower[d1].getNoDisk());
        
        Disk disk1 = tower[s1].top();
        
        while(disk1.y > diskUP) //;i+=1)
        {
            if( (disk1.y - moveSpeed) < 100)
                disk1.y = diskUP;
            else
                disk1.y -= moveSpeed;
            Update(g2);
            delay(delay_time);
        }
      // print(PegDis + disk1.Width/2 + "");
       // print(disk1.x + "");
        int len;
        if(s1 < d1)
        {
            len = disk1.x + PegDis * (d1-s1);
            while(disk1.x < len) //;i+=1)
            {
                //print(disk1.x + "");
                if(disk1.x + moveSpeed > len)
                    disk1.x = len;
                else
                    disk1.x += moveSpeed;
                Update(g2);
                delay(delay_time);
            }
        }
        else
        {
            len = disk1.x - PegDis * (s1-d1);
            while(disk1.x > len) //;i+=1)
            {
            //    print(disk1.x + "");
                if(disk1.x -moveSpeed < len)
                    disk1.x =len;
                else
                    disk1.x -= moveSpeed;
                Update(g2);
                delay(delay_time);
            }


        }


        Point p1 = getDiskPos(tower[d1],disk1);
        len = p1.y;
        //print(p1.y+ "-");
        for(;disk1.y < p1.y;)
        {
            if(disk1.y +moveSpeed > p1.y)
                disk1.y =p1.y;
            else
                disk1.y += moveSpeed;
           // print(disk1.y + " ");
            Update(g2);
            delay(delay_time);
        }
        tower[d1].insert(tower[s1].pop());

        Update(g2);
      
        
        
        
    }
    
    public void drawPegDisk(Graphics2D g2,Tower tower1,boolean ignore)
    {
       // print(tower1.getNoDisk() + "@");
        int dpos = 0;
        int i;
        int BaseMid = pegx1 + (Base/2) + (tower1.towerNo-1)*PegDis;
        int noDisk =  tower1.getNoDisk();
        
        for(i=1;i<=noDisk;i++)
        {
            if(!ignore)
            {
                tower1.disk[i].x = BaseMid - (tower1.disk[i].Width/2);
                tower1.disk[i].y = pegy1-DiskHeight - dpos;
            }
            drawDisk(g2,tower1.disk[i]);
            dpos += tower1.disk[i].Height;
        }

    }
    
    public Point getDiskPos(Tower tower1,Disk disk1)
    {
        Point p1 = new Point();
        int BaseMid = pegx1 + (Base/2) + (tower1.towerNo-1)*PegDis;
        int dpos = disk1.Height * (tower1.getNoDisk());
        p1.x = BaseMid - (disk1.Width/2);
        //print("dpos " + dpos + " " + pegy1);
        p1.y = pegy1- disk1.Height - dpos;

        
        return p1;
    }
    public void initDisk(Graphics2D g2)
    {
        drawPegDisk(g2,tower[s],false);
        
        first = false;

    }
    public void DrawCurrent(Graphics2D g2)
    {
        for(int i=1;i <= p; i++)
        {
            drawPegDisk(g2,tower[i],true);
            //print(tower[i].getNoDisk() + "&");
        }
    }
    public void clear(Graphics2D g2)
    {
        //g2.clearRect(0, 0, screenHeight,screenWidth);
        g2.setColor(new Color(0,0,0));
        g2.fill3DRect(0, 0,screenWidth,screenHeight,true);
        drawWindow(g2);
        

        
    }
    public void drawWindow(Graphics2D g2)
    {

        g2.setColor(new Color(155,155,0));
        g2.draw3DRect(40, 60,screenWidth-80,screenHeight-80,true);
        g2.setColor(new Color(155,0,155));
        g2.draw3DRect(40-10, 60-10,screenWidth-80+20,screenHeight-80+20,true);
    }
    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        drawWindow(g2);
        
         
        
        if(first)
        {
            DrawPeges(g2);
            initDisk(g2);
            delay(delay_time*10);  // Needs to Check
        }
        if(UpdateP)
        {
            UpdatePeg(g2);
            UpdateP = false;
            
        }
        
        
       
    }
    public void Update(Graphics2D g2)
    {
         clear(g2);
         DrawPeges(g2);
         DrawCurrent(g2);
         
    }

    private void initComponents() {

        

        
        Random ran = new Random(255);
        for(int i=1;i<=n;i++)
        {
            disk[i] = new Disk(DiskHeight,Base-diskw);
            disk[i].setColor(ran.nextInt(255),ran.nextInt(255), ran.nextInt(255));
            disk[i].no= i;
            diskw+=diskwInc;
        }
        for(int i=1;i<=p;i++)
        {
            tower[i] = new Tower(n+1,i);
        }
        //for(int i=n;i>0;i--)
        for(int i=1;i<=n;i++)
        {
            tower[s].insert(disk[i]);
           
        }
        
        
        
        //pack();

    }

    public void run() {

         
      
        while(Simulate)
        {
            if(source.empty())
            {
                UpdateP = false;
            }
            else
            {
                 UpdateP = true;
            }
            repaint();
            
        }
        Simulate = false;
        
    }


}



