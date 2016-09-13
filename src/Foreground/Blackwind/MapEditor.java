/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 *
 * @author Connor
 */
public class MapEditor extends JPanel implements ActionListener, MouseListener{
    private static MapEditor editor;
    private static JFrame window;
    
    //MENU BAR
    private static JMenuBar menuBar;
    private static JMenu file;
    private static JMenuItem file_newmap;
    private static JMenuItem file_savemap;
    private static JMenuItem file_loadmap;
    private static JMenuItem file_closemap;
    private static JMenu edit;
    private static JMenuItem edit_entity;
    private static JMenuItem edit_tile;
    //Map and Map Editor
    private static boolean mapLoaded = false;
    private static boolean mapChanged = false;
    private static Map loadedMap;
    private static JComboBox<String> editType;
    //Sprite editor
    //private static boolean spriteSelected = false;
    //private static boolean spriteAltered = false;
    //private static Sprite selectedSprite;
    //Warp Tile Editor
    //private static Tile selectedTile;
    //private static boolean tileSelected = false;
    //private static int selectedX=0, selectedY=0;

    //sprite editor buttons/text areas
    //private static JButton newSprite;
    //private static JComboBox<ImageIcon> spriteID;
    //private static JTextArea spriteName;
    //private static JComboBox<Integer> spriteMapX;
    //private static JComboBox<Integer> spriteMapY;
    //private static JTextArea eventFileName;
    //Warp Tile Editor buttons/text areas
    //private static JButton addWarp;
    //private static JButton removeWarp;
    //private static JButton saveTile;
    //private static JTextArea mapToLoad;
    //private static JComboBox<Integer> destinationX;
    //private static JComboBox<Integer> destinationY;


    //Edit types for the Editor type drop down menu
    public static final int EDIT_TILE = 0;
    public static final int EDIT_SPRITES = 1;
    public static final int EDIT_WARPS = 2;
    //Edit Selectors
    private static TilePalette palette;
    private static JLabel tileSelectorLeft;
    private static JLabel tileSelectorRight;
    private static int tileIDLeft;
    private static int tileIDRight;
    //private static JComboBox<ImageIcon> tileSelecterLeft;
    //private static JComboBox<ImageIcon> tileSelecterRight;
    //map Size Altering
    private static JComboBox<Integer> mapWidth;
    private static JComboBox<Integer> mapHeight;
    private static int menuItemLeft = 1300, menuItemRight = menuItemLeft+100;

    public MapEditor(){
		setPreferredSize(new Dimension(40*32+200,25*32));
		setLayout(null);
		//Setup the MapEditor Combo Box's
		editType = new JComboBox<String>();
		editType.addItem("Tile Edit Mode");
                //editType.addItem("Sprite Edit Mode");
                //editType.addItem("Warp Placement Mode");
		editType.setBounds(menuItemLeft,10,160,30);
		add(editType);
		//map eiditing details
                palette = new TilePalette(4,5,menuItemLeft,200);
		tileSelectorLeft = new JLabel(){
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        g.drawImage(Tile.getImage(tileIDLeft>=Tile.getNumberOfTiles()?0:tileIDLeft), 0, 0, this);
                    }
                };
		tileSelectorRight = new JLabel(){
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        g.drawImage(Tile.getImage(tileIDRight>=Tile.getNumberOfTiles()?0:tileIDRight), 0, 0, this);
                    }
                };
                mapWidth = new JComboBox<Integer>();
                mapHeight = new JComboBox<Integer>();
		tileSelectorLeft.setBounds(menuItemLeft,50,32,32);
                
		tileSelectorRight.setBounds(menuItemRight,50,32,32);
                mapWidth.setBounds(menuItemLeft,100,60,30);
                mapHeight.setBounds(menuItemRight,100,60,30);
		//for(BufferedImage b : Tile.getImagesList()){
		//	tileSelecterLeft.addItem(new ImageIcon(b));
		//	tileSelecterRight.addItem(new ImageIcon(b));
		//}
                for(int i=3;i<41;i++)
                    mapWidth.addItem(i);
                for(int i=3;i<26;i++)
                    mapHeight.addItem(i);
                //sprite editing details
                /*
                spriteID = new JComboBox<ImageIcon>();
                spriteName = new JTextArea();
                eventFileName = new JTextArea();
                spriteMapX = new JComboBox<Integer>();
                spriteMapY = new JComboBox<Integer>();
                newSprite = new JButton();
                
                spriteID.setBounds(menuItemLeft, 350, 60, 45);
                newSprite.setBounds(menuItemLeft, 300, 140, 45);
                spriteName.setBounds(menuItemLeft, 400, 140, 30);
                eventFileName.setBounds(menuItemLeft, 440, 140, 30);
                spriteMapX.setBounds(menuItemLeft,480,60,30);
                spriteMapY.setBounds(menuItemRight,480,60,30);
                
                newSprite.setText("New Sprite");
                
                
                for(BufferedImage b:Sprite.getSpritesheetList()){
                    spriteID.addItem(new ImageIcon(b.getSubimage(1, 1, 32, 32)));
                }
                for(int i=0;i<=40;i++){
                    if(i<=25)
                        spriteMapY.addItem(i);
                    spriteMapX.addItem(i);
                }
                //Warp Tile editing details
                addWarp = new JButton();
                removeWarp = new JButton();
                saveTile = new JButton();
                mapToLoad = new JTextArea();
                destinationX = new JComboBox<>();
                destinationY = new JComboBox<>();
                
                addWarp.setBounds(menuItemLeft,350,150,45);
                removeWarp.setBounds(menuItemLeft,350,150,45);
                removeWarp.setVisible(false);
                saveTile.setBounds(menuItemLeft,400,150,45);
                mapToLoad.setBounds(menuItemLeft, 450, 140, 30);
                destinationX.setBounds(menuItemLeft,490,60,30);
                destinationY.setBounds(menuItemRight,490,60,30);
                
                addWarp.setText("Create New WarpTile");
                removeWarp.setText("Remove WarpTile");
                saveTile.setText("Save Warp Data");
                for(int i=2;i<=40;i++){
                    if(i<=25)
                        destinationY.addItem(i);
                    destinationX.addItem(i);
                }
                        */
                //all the add's
		//add(tileSelecterLeft);
		//add(tileSelecterRight);
                add(mapWidth);
                add(mapHeight);
                
                add(tileSelectorLeft);
                add(tileSelectorRight);
                add(palette);
                //System.out.println(palette.up.getLocation());
                /*
                add(spriteID);
                add(spriteName);
                add(eventFileName);
                add(spriteMapX);
                add(spriteMapY);
                add(newSprite);
                
                add(addWarp);
                add(removeWarp);
                add(saveTile);
                add(mapToLoad);
                add(destinationX);
                add(destinationY);
                        */
	}
    public static void setupMenu(){
        menuBar = new JMenuBar();
        file = new JMenu("File [TODO]");
        file_newmap = new JMenuItem("Create New Map [TODO]");
        file_savemap = new JMenuItem("Save Map");
        file_loadmap = new JMenuItem("Load Map");
        file_closemap = new JMenuItem("Close Map");
        file.add(file_newmap);
        file.addSeparator();
        file.add(file_savemap);
        file.add(file_loadmap);
        file.addSeparator();
        file.add(file_closemap);
        menuBar.add(file);
        edit = new JMenu("Edit");
        edit_entity = new JMenuItem("Open Entity Editor");
        edit_tile = new JMenuItem("Open Tile Editor [TODO]");
        edit.add(edit_tile);
        edit.add(edit_entity);
        file.add(edit);
        menuBar.add(edit);
        file.addActionListener(editor);
        file_newmap.addActionListener(editor);
        file_savemap.addActionListener(editor);
        file_loadmap.addActionListener(editor);
        file_closemap.addActionListener(editor);
        edit.addActionListener(editor);
        edit_entity.addActionListener(editor);
        edit_tile.addActionListener(editor);
    } 
    
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 19*32, 19*32);
        //palette.paintComponent(g);
        //dont paint more if a map isnt loaded
        if(!mapLoaded){
            return;
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, loadedMap.getY()*32, loadedMap.getX()*32);
        //Draw the tiles
        for(int col = 0; col < loadedMap.getX(); col++){
            for(int row = 0; row < loadedMap.getY(); row++){
                Tile thisTile;
                try{
                    thisTile = loadedMap.getTile(col,row);
                }catch(Map.MapBadIndex e){
                    thisTile = new Tile(0);
                }
                g.drawImage(thisTile.getImage(), col*32, row*32, 32, 32, null);
                
            }
        }
        //Draw the lines
        for(int row = 0; row < loadedMap.getY()+1; row++)
                g.drawLine(0, row*32, loadedMap.getX()*32, row*32);
        for(int col = 0; col < loadedMap.getX()+1; col++)
                g.drawLine(col*32, 0, col*32, loadedMap.getY()*32);
                
        //tile editing stuff
        
        //System.out.println("Palette");
        
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Action!");
        if(ae.getSource() == file_loadmap){
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/maps/"));
                int returnVal = fc.showOpenDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        String fileName = file.getName();
                        loadedMap = Map.loadMap(fileName);
                        mapLoaded = true;
                        mapHeight.setSelectedIndex(loadedMap.getY()-3);
                        mapWidth.setSelectedIndex(loadedMap.getX()-3);
                }
                repaint();
        }
        else if(ae.getSource() == file_savemap){
                if(mapLoaded){
                    System.out.println("Saving map");
                        loadedMap.saveMap(loadedMap);
                        mapChanged = false;
                }
        }
        else if(ae.getSource() == file_newmap){
            mapLoaded = true;
            loadedMap = new Map();
            mapHeight.setSelectedIndex(10-3);
            mapWidth.setSelectedIndex(10-3);
        }
    }
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    @Override
    public void mousePressed(MouseEvent me) {
        mapChanged = true;
        //Get the x and y of the square we clicked on
        int squareX = me.getX()/32;
        int squareY = me.getY()/32;
        //Only do this if we are inside our map
        repaint();
        if(squareX >= mapWidth.getSelectedIndex()+3 || squareY >= mapHeight.getSelectedIndex()+3 || !mapLoaded){
            //this is done  if the click happened outside map boundaries
            if(palette.isWithin(me.getPoint())){
                //System.out.println(me.getPoint());
                switch(me.getButton()){
                    case MouseEvent.BUTTON1:tileIDLeft=palette.getTileAt(me.getPoint()).getID();break;
                    case MouseEvent.BUTTON3:tileIDRight=palette.getTileAt(me.getPoint()).getID();break;
                }
            }
        }else{
            //this is done if the click happened inside map boundaries
            if(loadedMap.getX()!=mapWidth.getSelectedIndex()+3){
                loadedMap.setWidth(mapWidth.getSelectedIndex()+3);
                System.out.println(loadedMap.mapWidth);
            }
            if(loadedMap.getY()!=mapHeight.getSelectedIndex()+3){
                loadedMap.setHeight(mapHeight.getSelectedIndex()+3);
                System.out.println(loadedMap.mapHeight);
            }
            switch(me.getButton()){
                case MouseEvent.BUTTON1:loadedMap.setTile(tileIDLeft, squareX, squareY);break;
                case MouseEvent.BUTTON3:loadedMap.setTile(tileIDRight, squareX, squareY);break;
                case MouseEvent.BUTTON2:loadedMap.setTileGroup(tileIDLeft,squareX,squareY);break;
            }
        }
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
    
    
    public static void main(String[]args){
		System.out.println("Starting Up - Editor");
		System.out.println("======================");
		System.out.println("Loading Tiles...");
		Tile.startUp();
                //System.out.println("Loading Sprites...");
                //Sprite.startUp();
		System.out.println("Starting Editor...");
		editor = new MapEditor();
		setupMenu();
                //newSprite.addActionListener(editor);
                //addWarp.addActionListener(editor);
                //removeWarp.addActionListener(editor);
                //saveTile.addActionListener(editor);
                        
		window = new JFrame("Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(menuBar, BorderLayout.NORTH);
		window.add(editor, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		editor.addMouseListener(editor);
		window.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e){
				if(mapChanged){
					switch(JOptionPane.showConfirmDialog(editor, "Would you like to save the map before closing?")){
						case JOptionPane.OK_OPTION:{
							//loadedMap.saveMap(loadedMap);
							System.out.println("Saving Map");
						}
						case JOptionPane.NO_OPTION:{
							System.exit(0);
						}break;
					}
				}
				else{
					window.dispose();
				}	
			}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
	}

    
}
