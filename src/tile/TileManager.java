package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {

            setup(0,"pebble_path",false);
            setup(1,"pebble_wall3",true);
            setup(2,"water",true);
    }
    public void setup(int index, String imageName, boolean collision) { //setup image

        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col <gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){
            System.out.println("failed to load map");
        }
    }
    public void draw(Graphics2D g2) {
//        if (gp.player.screenX > gp.invSquareX && gp.player.screenX < (gp.invSquareX + gp.invSquareL)
//                && gp.player.screenY > gp.invSquareY && gp.player.screenY < (gp.invSquareY + gp.invSquareH)) {
//            int col = 0;
//            int row = 0;
//            int x = 0;
//            int y = 0;
//
//            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
//
//                int tileNum = mapTileNum[row][col];
//
//                g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
//                col++;
//                x += gp.tileSize;
//
//                if (col == gp.maxScreenCol) {
//                    col = 0;
//                    x = 0;
//                    row++;
//                    y += gp.tileSize;
//                }
//            }
//        }
//        else {
            int worldRow = 0;
            int worldCol = 0;

            while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

                int worldX = worldCol * gp.tileSize; //coordinates of the whole map
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX; //where on the screen is drawn on the screen
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                int tileNum = mapTileNum[worldCol][worldRow];

                if ((worldX + gp.tileSize) > (gp.player.worldX - gp.player.screenX) &&
                        (worldX - gp.tileSize) < (gp.player.worldX + gp.player.screenX) &&
                        (worldY + gp.tileSize) > (gp.player.worldY - gp.player.screenY) &&
                        (worldY - gp.tileSize) < (gp.player.worldY + gp.player.screenY)) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }

                //g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
                worldCol++;

                if (worldCol == gp.maxWorldCol) {
                    worldCol = 0;
                    worldRow++;
                }
            }
//        }
    }
}