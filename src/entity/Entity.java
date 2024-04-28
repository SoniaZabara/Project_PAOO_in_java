package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX,worldY;  //entity position on map
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;

    public int spriteCounter = 0; //after how many frames the sprite changes
    public int spriteNum = 1;  //switch sprite

    public Rectangle solidArea; //players hitbox
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
