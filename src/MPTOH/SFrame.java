/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MPTOH;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author SystemCrashed
 */


public class SFrame extends javax.swing.JFrame {
    
    private JLabel SpeedLabel;
    private JSlider SpeedSlider;
    JPanel Menu = new JPanel();
    SWindow sw;
    JButton about = new JButton("About");

    public SFrame(int n,int p,int s,int d,Deque source,Deque dest,Deque disk_no)
    {
        Dimension sd = getToolkit().getScreenSize();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(sd);
        SpeedLabel = new JLabel("Delay: ");
        SpeedSlider = new JSlider(50, 200, 100);
        Menu.setLayout(new FlowLayout());
        Menu.add(SpeedLabel);
        Menu.add(SpeedSlider);
        Menu.add(about);
        sd.height -=100;
        
        about.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                JOptionPane.showMessageDialog(null, "Multi Peg Tower of Hanoi Simulation\nBy Md. Ibrahim Rashid\nibrahin.cse07@yahoo.com\n\nSpecial Thanks to: Dr. Muhammad Kaykobad\n\n");
            }


        }
        );
        
        SpeedSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent arg0)
            {
                sw.moveSpeed = SpeedSlider.getValue();
            }

        }
        );

        setTitle("MultiPeg Tower Of Hanoi Simulation");
        Menu.setVisible(true);
       
        add(Menu,"North");
        sw = new SWindow(n, p, s, d, source, dest, disk_no,sd);
        add(sw,"Center");
        sw.setVisible(true);
        this.setVisible(true);
        setLocation(0, 0);
        setUndecorated(true);
        setResizable(false);
        try {
            sw.t.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
   
    }


}
