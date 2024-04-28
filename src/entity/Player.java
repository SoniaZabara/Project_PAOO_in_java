package entity;

import entity.Entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;  //where player is drawn on screen /player position relative to screen
    public final int screenY;

    public int hasApple = 0; //how many apples does the player have;
    public Player(GamePanel gp,KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        screenX = gp.screenWidth/2-(gp.tileSize/2);  //player position on screen
        screenY = gp.screenHeight/2-(gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x; //collision for objects
        solidAreaDefaultY = solidArea.y;
        solidArea.width = (int)gp.tileSize/2;
        solidArea.height = (int)gp.tileSize/2;

        setDefaultValue();
        getPlayerImage();
    }
    public  void setDefaultValue(){
        worldX = gp.tileSize*10;  //player position on the hole map
        worldY = gp.tileSize*10;
        speed = 4;
        direction="up";
    }
    public void getPlayerImage() {

        up1 = setup("ant_up1");
        up2 = setup("ant_up2");
        down1 = setup("ant_down1");
        down2 = setup("ant_down2");
        left1 = setup("ant_left1");
        left2 = setup("ant_left2");
        right1 = setup("ant_right1");
        right2 = setup("ant_right2");
    }
    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed){
            if(keyH.upPressed == true){
                direction="up";
            }
            else if(keyH.downPressed == true){
                direction="down";
            }
            else if(keyH.leftPressed == true){
                direction="left";
           }
            else if(keyH.rightPressed == true){
                direction="right";
            }
            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //if collision is false player can move
            if(collisionOn == false) {

                switch(direction) {
                    case "up":worldY = worldY - speed; break;
                    case "down":worldY = worldY + speed; break;
                    case "left":worldX = worldX - speed; break;
                    case "right":worldX = worldX + speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter=0;
            }
        }
    }
    public void pickUpObject(int i){
        if(i != 999) {
            String objectName = gp.obj[i].name;
            switch(objectName){
                case "Apple":
                    hasApple++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got apple");
                    break;
                case "Rock":
                    /*if(hasKey > 0){
                        gp.obj[i] = null;
                        hasKey--;
                    }*/
                    break;
                case "Honey":
                    //gp.playSE(1); //play sound effect when picking up
                    speed +=2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got honey comb");
                    break;
                /*case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;*/
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image,screenX,screenY,null);
    }
}
