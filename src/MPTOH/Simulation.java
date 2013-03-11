package MPTOH;


import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ibrahim
 */

public class Simulation {

    public static void main(String[] args) {

        int n = 3, p = 3;
        int s = 1, d = 3;
        int result = 1;
        String s1 = JOptionPane.showInputDialog(null, "No of Disk:");
        
        String s2 = JOptionPane.showInputDialog(null, "No of Pegs:");
        
        String s3 = JOptionPane.showInputDialog(null, "Source Peg No:");
        
        String s4 = JOptionPane.showInputDialog(null, "Dest Peg No:");
        
         if((s1.length()!=0 ) && (s2.length()!=0) && (s3.length()!=0) && (s4.length()!=0))
         {
             System.out.println("st");
            n = Integer.parseInt(s1);
            p = Integer.parseInt(s2);
            s = Integer.parseInt(s3);
            d = Integer.parseInt(s4);
            if(p >2)
                result = JOptionPane.showConfirmDialog(null, "MPTOH(" + n + "," + p + "," + s + "," + d + "" + ")" + '\n' + "RUN ?");
            else
            {
                result = 2;
            }
         }
         else
         {
            result = 2;
         }
         if(result == 2)
         {
            JOptionPane.showMessageDialog(null,"Wrong Input Program Tarminates");
            
         }


        //JOptionPane.showMessageDialog(null,"MPTOH(" + n + "" + p + "" + s + ""+ d + "" + ")" +'\n'
        //      + "RUN ?");
        
        if (result == 0) {



            Deque source = new Deque();
            Deque dest = new Deque();
            Deque disk_no = new Deque();

            
            MPTOH mp = new MPTOH(n, p, s, d, source, dest, disk_no);
            try {
                mp.t.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }


//        while(!source.empty())
//        {
//            System.out.println( source.front() + "->" + dest.front());
//            System.out.println(source.numberOfItems + "");
//            dest.pop_front();
//            source.pop_front();
//        }
            if(mp.result == 0)
            {
                 String s5 = "Moves : " + mp.No_Move + " = " + "No of Move Required " + mp.no_move;
                 mp.print(s5);
                 JOptionPane.showMessageDialog(null,s5);
           
                SFrame sf = new SFrame(n, p, s, d, source, dest, disk_no);
                
                try {
                    sf.sw.t.join();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Caceled By User");
            }

        }
        else if(result == 1)
        {
            JOptionPane.showMessageDialog(null,"Caceled By User");
        }
        


    }
}
