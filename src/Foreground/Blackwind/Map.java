/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Map {
    static String loadedMapName = "unnamed.txt";
    //statis functions for map IO
    public static Map loadMap(String mapname){
        return loadMap(mapname,"maps");
    }
    public static Map loadMap(String mapname, String folderName){
        loadedMapName= mapname;
        System.out.println(mapname);
        //try{
            try{
                //setup
                String line = "";
                ArrayList<String> contents = new ArrayList<>();
                String filePath = String.format("%s/%s",folderName,mapname);
                InputStream input = new FileInputStream(filePath);
                InputStreamReader inputReader = new InputStreamReader(input);
                BufferedReader fileReader = new BufferedReader(inputReader);
                //tile loading
                int mapID = Integer.parseInt(fileReader.readLine());
                while((line=fileReader.readLine()).charAt(0)!='-'){
                        //System.out.println(line);
                        contents.add(line);
                }
                //for(String mapRow:contents)
                    //System.out.println(mapRow);
                String[] testLen = contents.get(0).split(" ");
                //tile writing
                Tile[][] tileIDs = new Tile[contents.size()][testLen.length];
                for(int x=0;x<tileIDs.length;x++){
                    for(int y=0;y<testLen.length;y++){
                        String tileInfo = contents.get(x).split(" ")[y];
                        //System.out.println(x+"/"+y);
                        try{
                            tileIDs[x][y] = new Tile(Integer.parseInt(tileInfo));
                        }catch(NumberFormatException e){
                            //System.out.printf("Non-Basic Tile found at %d/%d\n",x,y);
                            String[] tileDetails = tileInfo.split("/");
                            //warp tile
                            /*
                            if(tileInfo.split("/")[1].equals("w")){
                                tileIDs[x][y] = new WarpTile(
                                        Integer.parseInt(tileDetails[0]),
                                        Integer.parseInt(tileDetails[3]),
                                        Integer.parseInt(tileDetails[4]),
                                        (tileDetails[2]));
                            }
                            //event tile
                            if(tileInfo.split("/")[1].equals("e")){
                                tileIDs[x][y] = new EventTile(
                                        Integer.parseInt(tileDetails[0]),
                                        (tileDetails[2]),
                                        mapname.split(".txt")[0]);
                            }*/
                        }
                    }
                }
                Map m = new Map(mapID,mapname,tileIDs);
                m.setName(mapname.split(".txt")[0]);
                System.out.println(m.getName());
                //sprite loading
                /*
                while((line=fileReader.readLine())!=null){
                    String spriteID = line;
                    String spriteName = fileReader.readLine();
                    
                    System.out.println(spriteName.split("=")[0]);
                    String spriteXY = fileReader.readLine();
                    int id = Integer.parseInt(spriteID.split(" ")[0]);
                    String[] xy = spriteXY.split("/");
                    int x = Integer.parseInt(xy[0]);
                    int y = Integer.parseInt(xy[1]);
                    m.addSprite(new Sprite(id,spriteName.split("=")[0],x,y,0,fileReader.readLine().split(" ")[0],mapname));
                }
                */
                return m;
            }catch(IOException e){
                System.out.printf("Error Loading map %s\n",mapname);
                System.out.println(e);
                return null;
            }
        //}catch(Exception e){
        //    System.out.printf("Error Loading map %s\n",mapname);
        //    System.out.println(e);
        //    return null;
        //}
    }
    public static void saveMap(Map m){
        saveMap(m,"maps");
    }
    public static void saveMap(Map m, String folderName){
        //try{
            try{
                String filePath = String.format("%s/%s",folderName,loadedMapName);
                FileWriter path = new FileWriter(filePath, false);
                PrintWriter writeline = new PrintWriter(path);
                //System.out.printf("%d/%d",m.getX(),m.getY());
                writeline.printf("%d%n", m.getMapID());
                for(int x=0;x<m.getX();x++){
                    String line = "";
                    for(int y=0;y<m.getY();y++){
                        //System.out.println(x+"/"+y);
                        line+=m.getTile(x, y).getDetails()+" ";
                    }
                    writeline.printf("%s%n",line);
                }
                writeline.printf("-%n");
                /*
                for(Sprite s:m.getSpriteList()){
                    writeline.printf("%d --SpriteID%n",s.getID());
                    writeline.printf("%s=--Sprite Name%n", s.getName());
                    writeline.printf("%d/%d/--spriteXY%n", s.getMapX(),s.getMapY());
                    writeline.printf("%s --eventName%n",s.getEventFileName());
                }
                        */
                writeline.close();
            }catch(IOException e){
                System.out.println("Error saving file "+loadedMapName);
                System.out.println(e);
            }
        //}catch(Exception e){
        //    
        //}
    }
    int mapID;
    int mapWidth, mapHeight;
    String mapName;
    //int totalSprites;
    Tile[][] mapTiles;

    public Map() {
        this(-1,10,10,"Unnamed");
    }
    public Map(int mapID,String mapName, Tile[][] mapTiles) {
        this.mapID = mapID;
        this.mapWidth = mapTiles.length;
        this.mapHeight = mapTiles[0].length;
        this.mapName = mapName;
        this.mapTiles = mapTiles;
    }
    public Map(int mapID, int mapWidth, int mapHeight, String mapName) {
        this.mapID = mapID;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapName = mapName;
        this.mapTiles = new Tile[mapWidth][mapHeight];
        for(int x=0;x<mapWidth;x++){
            for(int y=0;y<mapHeight;y++){
                mapTiles[x][y]=new Tile(0);//0 being a void tile
            }
        }
    }
    
    
    //sets
    public void setTile(int newID, int x, int y){
        mapTiles[x][y].setID(newID);
    }
    public void setTileGroup(int newID, int x,int y){
        //this function is recursive
        int myOldId = getTile(x,y).getID();
        System.out.println(myOldId);
        setTile(newID,x,y);
        try{
            if(getTile(x-1,y).getID()==myOldId)
                setTileGroup(newID,x-1,y);
        }catch(MapBadIndex e){
            
        }
        try{
            if(getTile(x+1,y).getID()==myOldId)
                setTileGroup(newID,x+1,y);
        }catch(MapBadIndex e){
            
        }
        try{
            if(getTile(x,y-1).getID()==myOldId)
                setTileGroup(newID,x,y-1);
        }catch(MapBadIndex e){
            
        }
        try{
            if(getTile(x,y+1).getID()==myOldId)
                setTileGroup(newID,x,y+1);
        }catch(MapBadIndex e){
            
        }
    }
    public void setMapID(int mapID) {this.mapID = mapID;}
    public void setName(String mapName) {this.mapName = mapName;}
    public void setWidth(int mapWidth) {
        int tilesDeleted=0;
        Tile[][] tempHold = mapTiles;
        mapTiles = new Tile[mapWidth][tempHold[0].length];
        for(int x=0;x<mapTiles.length;x++){
            for(int y=0;y<mapTiles[0].length;y++){
                try{
                    mapTiles[x][y] = tempHold[x][y];
                }catch(IndexOutOfBoundsException e){
                    mapTiles[x][y] = new Tile(0);
                    tilesDeleted++;
                }
            }
        }
        this.mapWidth = mapWidth;
        System.out.printf("Width set to %d. %d tiles added/deleted by this\n", mapWidth,tilesDeleted);
    }
    public void setHeight(int mapHeight) {
        int tilesDeleted=0;
        Tile[][] tempHold = mapTiles;
        mapTiles = new Tile[tempHold.length][mapHeight];
        for(int x=0;x<mapTiles.length;x++){
            for(int y=0;y<mapTiles[0].length;y++){
                try{
                    mapTiles[x][y] = tempHold[x][y];
                }catch(IndexOutOfBoundsException e){
                    mapTiles[x][y] = new Tile(0);
                    tilesDeleted++;
                }
            }
        }
        this.mapHeight = mapHeight;
        System.out.printf("Height set to %d. %d tiles added/deleted by this\n", mapHeight,tilesDeleted);}
    
    //gets
    public int getMapID() {return mapID;}
    public int getX() {return mapWidth;}
    public int getY() {return mapHeight;}
    public String getName() {return mapName;}
    public Tile getTile(int x, int y){
        try{
            return mapTiles[x][y];
        }catch(IndexOutOfBoundsException e){
            throw new MapBadIndex(String.format("Tile at %dx/%dy is out of map bounds",x,y));
        }
    }
    public Tile[][] getMapTiles() {return mapTiles;}
    public Tile[][] getSubMap(int startx, int starty, int width, int height){
        Tile[][] newTiles = new Tile[width][height];
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                try{
                    newTiles[x][y]=getTile(startx+x,starty+y);
                }catch(MapBadIndex e){
                    newTiles[x][y]=new Tile(0); //0 being a void tile
                }
            }
        }
        return newTiles;
    }
    
    public class MapBadIndex extends RuntimeException{
        public MapBadIndex(String err) {
            super(err);
        }
    }
    
}
