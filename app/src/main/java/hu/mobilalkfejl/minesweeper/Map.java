package hu.mobilalkfejl.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Map {
    private Tile[][] tiles;
    static final int sizeX = 8;
    static final int sizeY = 10;
    static final int mineNumber = 10;

    public Map() {
        generateMap();
    }

    private void generateMap(){
        tiles = new Tile[sizeX][sizeY];
        boolean[][] mines = new boolean[sizeX][sizeY];
        int nom = 0;
        Random rand = new Random();

        for(boolean[] row: mines){
            Arrays.fill(row, false);
        }

        while (nom < mineNumber){
            int posX = rand.nextInt(sizeX);
            int posY = rand.nextInt(sizeY);
            if(!mines[posX][posY]){
                mines[posX][posY] = true;
                nom++;
            }
        }

        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j< sizeY; j++){
                tiles[i][j] = new Tile(i, j, mines[i][j]);
            }
        }
    }

    public String[][] getTypeMap(){
        String[][] types = new String[sizeX][sizeY];

        for(int i = 0; i < sizeX; i++){
            for(int j =0; j < sizeY; j++){
                types[i][j] = getTileType(tiles[i][j]);
            }
        }
        return types;
    }

    private String getTileType(Tile t){
        if(!t.isKnown()){
            return "unknown";
        }
        if(t.isMine()){
            return "mine";
        }
        return  String.valueOf(nearbyMines(t));
    }

    private int nearbyMines(Tile t){
        int nom = 0;

        for(Tile ot: getNearbyTiles(t)){
            if(ot.isMine()){
                nom++;
            }
        }
        return nom;
    }

    private List<Tile> getNearbyTiles(Tile t){
        List<Tile> nearbyTiles = new ArrayList<>();

        for(Tile[] row: tiles){
            for(Tile ot: row){
                if(
                    t.getX()-ot.getX() >= -1 && t.getX()-ot.getX() <= 1
                    && t.getY()-ot.getY() >= -1 && t.getY()-ot.getY() <= 1
                    && t != ot
                ){
                    nearbyTiles.add(ot);
                }
            }
        }
        return nearbyTiles;
    }

    public int remainingTiles(){
        int nout = sizeX * sizeY - mineNumber;

        for(Tile[] row: tiles){
            for(Tile t: row){
                if(t.isKnown()){
                    if(t.isMine()){
                        return -1;
                    }
                    nout--;
                }
            }
        }
        return nout;
    }

    public void clickTile(int posX, int posY) {
        LinkedList<Tile> triggeredTiles = new LinkedList<>();
        LinkedList<Tile> tilesToProcess = new LinkedList<>();
        tilesToProcess.add(tiles[posX][posY]);

        if(tiles[posX][posY].isMine()){
            tiles[posX][posY].setKnown();
        }
        if(tiles[posX][posY].isKnown()){
            return;
        }

        while(tilesToProcess.size() > 0){
            Tile t = tilesToProcess.pop();
            if(!triggeredTiles.contains(t) && !t.isKnown() && !t.isMine()){
                triggeredTiles.add(t);
                if(nearbyMines(t) == 0){
                    tilesToProcess.addAll(getNearbyTiles(t));
                }
            }
        }

        for(Tile t: triggeredTiles){
            t.setKnown();
        }
    }
}
