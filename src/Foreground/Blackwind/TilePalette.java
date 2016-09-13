/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Connor
 */
public class TilePalette extends JPanel implements ActionListener, MouseListener{

    private static final int buttonWidth = 45,buttonHeight = 50;
    int myX, myY, myWidth, myHeight;
    int tileX, tileY;
    int scrollValue;
    JButton up,down;
    public TilePalette(int tileX, int tileY,int myX,int myY) {
        
        this.setLayout(null);
        this.myX=myX;
        this.myY=myY;
        this.tileX = tileX;
        this.tileY = tileY;
        //  2 for border size
        myWidth = 3+tileX*Tile.tileSize;
        myHeight = 3+tileY*Tile.tileSize;
        this.setSize(myWidth+buttonWidth+10, myHeight);
        this.setLocation(myX, myY);
        up = new JButton();
        down = new JButton();
        scrollValue =0;
        up.addActionListener(this);
        up.setLocation(myWidth+1, 0);
        up.setSize(buttonWidth, buttonHeight);
        up.setVisible(true);
        up.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        up.setText("^");
        down.addActionListener(this);
        down.setLocation(myWidth+1, buttonHeight);
        down.setSize(buttonWidth, buttonHeight);
        down.setText("v");
        down.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(up);
        this.add(down);
    }
    public int getMaxScrollPos(){
        return (int)1+((Tile.getNumberOfTiles()-tileX*tileY)/tileX);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /*
        g.setColor(Color.black);
        g.drawRect(myX, myY, myWidth, myHeight);
        g.drawRect(myX+1, myY+1, myWidth-2, myHeight-2);
        for(int x=0;x<tileX;x++){
            for(int y=0;y<tileY;y++){
                try{
                    g.drawImage(Tile.getImage(x+(y+scrollValue)*tileX), myX+x*Tile.tileSize+2, myY+y*Tile.tileSize+2, this);
                }catch(IndexOutOfBoundsException e){
                    g.drawImage(new Tile(0).getImage(), myX+x*Tile.tileSize+2, myY+y*Tile.tileSize+2, this);
                }
            }
        }*/
        g.setColor(Color.black);
        g.drawRect(0, 0, myWidth, myHeight);
        g.drawRect(1, 1, myWidth-2, myHeight-2);
        for(int x=0;x<tileX;x++){
            for(int y=0;y<tileY;y++){
                try{
                    g.drawImage(Tile.getImage(x+(y+scrollValue)*tileX), x*Tile.tileSize+2, y*Tile.tileSize+2, this);
                }catch(IndexOutOfBoundsException e){
                    g.drawImage(new Tile(0).getImage(), x*Tile.tileSize+2, y*Tile.tileSize+2, this);
                }
            }
        }
        //up.setBounds(this.myX+myWidth+1, this.myY, buttonWidth, buttonHeight);
        //up.paint(g);
        //down.setBounds(this.myX+myWidth+1, this.myY+buttonHeight, buttonWidth, buttonHeight);
        //down.paint(g);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println(scrollValue);
        if(ae.getSource()==up){
            if(scrollValue>0)
                scrollValue--;
        }else if(ae.getSource()==down){
            if(scrollValue<getMaxScrollPos())
                scrollValue++;
        }
        repaint();
    }

    public static void main(String[] args) {
        Tile.startUp();
        TilePalette p = new TilePalette(2,2,0,0);
        JFrame frame = new JFrame("Blackwind Dev",null);
        //frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        frame.addMouseListener(p);
        frame.add(p);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        if(isWithin(me.getPoint())){
            //System.out.println("Location: "+me.getPoint().getX()+"/"+me.getPoint().getY());
            try{
                System.out.println(new Tile(((me.getX()-myX)/Tile.tileSize)+(((me.getY()-myY-Tile.tileSize)/Tile.tileSize)+scrollValue)*tileX).getName());
            }catch(IndexOutOfBoundsException e){
                System.out.println("No tile at this location (Void by default to replace null)");
            }
        }else
            System.out.println(me.getPoint());
    }
    public boolean isWithin(Point loc){
        //Point loc = me.getLocationOnScreen();
        //the -2 is a compensation for the border: Clicking on the border should not trigger isWithin
        if(loc.getY()>myY+2 && loc.getY()<myY+myHeight-3){
            //System.out.println("Y position legal");
            if(loc.getX()>myX+2 && loc.getX()<myX+myWidth-3){
                return true;
            }
        }
        /*
        if(loc.getX()>myX+2 && loc.getX()<myX+myWidth-3){
            System.out.println("X position legal");
            if(loc.getY()>myY+2 && loc.getY()<myY+myHeight-3){
                return true;
            }
        }
                */
        return false;
                
    }
    public Tile getTileAt(Point p){
        //return new Tile((int) (((p.getX()-myX)/Tile.tileSize)+(((p.getY()-myY-Tile.tileSize)/Tile.tileSize)+scrollValue)*tileX));
        //return new Tile((int)((p.getX()-myX)/Tile.tileSize+((p.getY()-myY)/Tile.tileSize+scrollValue)*tileX));
        //Step 1: Find the location of the MouseEvent
        //Step 2: -ct the Location variables of the Panel (retreiving the location within the panel)
        //Step 3: Find out how many tiles there are per row (# of cols, TileX)
        //Step 4: the number, from this point, should be x+(#cols)*y
        int xPos = (int)p.getX();
        int yPos = (int)p.getY();
        System.out.println(new Point(xPos,yPos));
        xPos-=myX;
        yPos-=myY;
        System.out.println(new Point(xPos,yPos));
        xPos/=32;
        yPos/=32;
        System.out.println(new Point(xPos,yPos));
        System.out.println();
        return new Tile(xPos+tileX*yPos+scrollValue*tileX);
        //return new Tile(0);
    }
            

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    
        
}
