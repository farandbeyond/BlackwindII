/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

/**
 *
 * @author Connor
 */
public class Map {
    int mapID;
    int mapWidth, mapHeight;
    String mapName;
    //int totalSprites;
    Tile[][] mapTiles;

    public Map() {
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
    public void setMapID(int mapID) {this.mapID = mapID;}
    public void setMapName(String mapName) {this.mapName = mapName;}
    public void setMapWidth(int mapWidth) {this.mapWidth = mapWidth;}
    public void setMapHeight(int mapHeight) {this.mapHeight = mapHeight;}
    
    //gets
    public int getMapID() {return mapID;}
    public int getMapHeight() {return mapHeight;}
    public int getMapWidth() {return mapWidth;}
    public String getMapName() {return mapName;}
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
