package MPTOH;


import javax.swing.JOptionPane;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ibrahim
 */
public class MPTOH implements Runnable{

    public final int MAXP = 800;
    public final int MAXN = 800;
    public int arr[][] = new int[MAXP][MAXN];
    public int pass[][] = new int[MAXN][MAXN];
    public Deque dq[] = new Deque[MAXP];
    int No_Move = 0;
    int n1, s1, p1, d1;
    Deque source;
    Deque dest;
    Deque disk_no;
    long no_move;
    int result = 1;
    
    Thread t;
//    static Deque source = new Deque();
//    static Deque dest = new Deque();
//    static Deque disk_no = new Deque();

    public MPTOH(int n, int p, int s, int d, Deque source, Deque dest, Deque disk_no)  {
    //public MPTOH(int n, int p, int s, int d) {
        init();

        n1 = n;
        p1 = p;
        s1 = s;
        d1 = d;
        this.source = source;
        this.dest = dest;
        this.disk_no = disk_no;
        t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY-2);
        t.start();
        
        
//      MP(n1, p1, s1, d1);
    }

    public int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public void init() {
        int i, j;
        for (j = 3; j < MAXP; j++) {
            arr[0][j] = 1;
        }
        for (i = 0; i < MAXP; i++) {
            arr[i][3] = 1;
        }

        for (i = 1; i < MAXP; i++) {
            for (j = 4; j < MAXP; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
            }
        }
        for (i = 1; i < MAXP; i++) {
            dq[i] = new Deque();
        }
        ncr(MAXN /2);
//        {
//
//            for (i = 0; i < MAXP; i++) {
//                for (j = 3; j < MAXP; j++) {
//                    System.out.print(arr[i][j] + " ");
//
//                }
//                print("");
//            }
//        }


    }

    void ncr(int n) {
        int i, j;
        pass[0][0] = 1;
        pass[1][0] = pass[1][1] = 1;

        for (i = 2; i <= n; i++) {
            pass[i][0] = 1;
            for (j = 1; j < i; j++) {
                pass[i][j] = pass[i - 1][j - 1] + pass[i - 1][j];
            }
            pass[i][j] = 1;

        }
//
//        {
//            for (i = 0; i < n; i++) {
//                for (j = 0; j < n; j++) {
//                    System.out.print(pass[i][j] + " ");
//
//                }
//                print("");
//            }
//        }
    }

    int getN1(int n, int p) {

        int q = p;
        q -= 3;
        int x = 1;
        //BS KORLE EFICIENT HOBE...
        while (pass[q + x][x - 1] < n) {
            x++;
        }
        x--;
        int Kmax = x;

        int Na = n - pass[q + x][x - 1];

        //_N(Kmax-2,p) <= n1 <= _N(Kmax-2,p) + min{N(Kmax-1,p),Na(Kmax,p)}
        //left1                                   r1
        //print(n + " " + p + " "+ q + " " + x );

        int left1;
        int temp = Kmax - 2;
        if (temp >= 0) {
            left1 = arr[Kmax - 2][p + 1];
        } else {
            left1 = 0;
        }
        temp = Kmax - 1;
        int r1;
        if (temp >= 0) {
            r1 = arr[Kmax - 1][p];
        } else {
            r1 = 0;
        }

        int right1 = left1 + min(r1, Na);

        //_N(Kmax-1,p-1) <= n-n1 <= _N(Kmax-1,p-1) + min{N(Kmax,p-1),Na(Kmax,p)}
        //  [ n  - ( _N(Kmax-1,p-1) ) + min{N(Kmax,p-1),Na(Kmax,p)} ] <= n1 <=  [ n - ( _N(Kmax-1,p-1) ) ]
        //left2      l2                                                    r2
        temp = Kmax - 1;
        int left2;
        if (temp >= 0) {
            left2 = arr[Kmax - 1][p];
        } else {
            left2 = 0;
        }

        int l2 = arr[Kmax][p - 1];

        int right2 = n - left2;
        left2 += min(l2, Na);

        left2 = n - left2;

        int left_max = max(left1, left2);
        int right_min = min(right1, right2);

        return right_min;
    }

    long MinMove(int n, int p) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        if (p == 3) {
            return 2 * MinMove(n - 1, p) + 1;
        } else {
            int n1 = getN1(n, p);
            return 2 * MinMove(n1, p) + MinMove(n - n1, p - 1);
        }
    }

    public void print(String s) {
        System.out.println(s);
    }

    void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void TH(int n, int s, int d) {
        if (n == 1) {
           // print(s + " -> " + d);
            //sw.setUpdate(s, d);
            //delay(5);
            source.push_back(s);
           // print(source.front() + " ");
            dest.push_back(d);
            disk_no.push_back(dq[s].front());
            No_Move++;
        } else {
            TH(n - 1, 1, 2);
            TH(1, 1, 3);
            TH(n - 1, 2, 3);
        }
    }

    public int find_i(int n, int s, int d, int p) {
        int i;

        for (i = 1; i <=p1; i++) {
            //(i != s) &&
            if ((i != d))
            {
                if(dq[i].empty())
                {
                  break;
                }
                else if(dq[i].front() != -1 )
                {
                    if(dq[s].front() < dq[i].front())
                        {
                        break;
                    }
                }
                
            }

        }
        return i;
    }

    void MP1(int n, int p, int s, int d) {

        if (n == 1) {
            No_Move++;
           // print(s + " -> " + d);
//            if (!dq[d].empty() && (dq[d].front() == -1)) {
//                dq[d].pop_front();
//            }
            dq[d].push_front(dq[s].front());
            dq[s].pop_front();
            //delay(5);
            //sw.setUpdate(s, d);
            source.push_back(s);
            dest.push_back(d);
            disk_no.push_back(dq[s].front());
        //dq[s].pop_front();
//        } else if (p == 3) {
//            TH(n, s, d);
        } else {
            int n1 = getN1(n, p);
            int i = find_i(n - n1, s, d, p);


            MP1(n1, p, s, i);
            dq[i].push_front(-1);

            MP1(n - n1, p - 1, s, d);
            dq[i].pop_front();

            MP1(n1, p, i, d);


        }
    }

    void MP(int n, int p, int s, int d) {
        int i;
        //print(n + " " + p + " " + s + " "+ d);
        try {
            for (i = 1; i <= n; i++) {
                dq[s].push_back(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        no_move = MinMove(n,p) ;
        result = JOptionPane.showConfirmDialog(null, "No of Move Required is " + no_move + '\n'+ " Simulate ?");
        if(result==0)
        {
            MP1(n, p, s, d);
        }


        
        //print(source.front() + "--");
        



    }

    public void run() {

        MP(n1,p1,s1,d1);
    }
}