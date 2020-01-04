package gfx;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player1, player2, playground, grass, concrete, centerLine, centerPoint, ball, goal1, goal2, postBL, postBR, postUL, postUR,
            horWall, verWall_L, verWall_R, horNet, verNet, balls0, balls1, balls2, balls3, balls4, balls5, cup,
            redShoe, blueShoe, redShoeTrans, blueShoeTrans, redHalo, blueHalo, ballIndicatorBig, ballIndicatorBlue, ballIndicatorRed,
            gradientBlue, gradientRed, clock, clockHand, clockFace, whiteFace, blueFace, redFace, whiteBar, blueBar, redBar, menuBackground, halvesText, imgGrass, imgUrban,
            thunderBlue, thunderRed, kickingShoeBlue, kickingShoeRed, photoKajder, gameController, keyboardSettings;
    //public static Image field;
    public static BufferedImage[] framesBall, framesPlayer1, framesPlayer2, startBoard, btn_start, btn_trainingP1, btn_trainingP2, btn_settings, btn_info, btn_arrowUP, btn_arrowLEFT, btn_arrowRIGHT;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        framesBall = new BufferedImage[20];
        framesPlayer1 = new BufferedImage[20];
        framesPlayer2 = new BufferedImage[20];
        startBoard = new BufferedImage[4];
        btn_start = new BufferedImage[2];
        btn_trainingP1 = new BufferedImage[2];
        btn_trainingP2 = new BufferedImage[2];
        btn_settings = new BufferedImage[2];
        btn_info = new BufferedImage[2];
        btn_arrowUP = new BufferedImage[2];
        btn_arrowLEFT = new BufferedImage[2];
        btn_arrowRIGHT = new BufferedImage[2];

        //player1 = sheet.crop(23,602, 43, 22);
        player2 = sheet.crop(71, 602, 44, 22);
        grass = sheet.crop(0, 0, 970, 600);
        playground = grass;
        concrete = sheet.crop(1013, 0, 970, 600);
        centerLine = sheet.crop(0, 1761, 970, 6);
        centerPoint = sheet.crop(118, 601, 6, 6);
        ball = sheet.crop(0, 600, 20, 20);
        goal2 = sheet.crop(1, 627, 151, 47);
        goal1 = sheet.crop(1, 685, 151, 45);
        postBL = sheet.crop(153, 626, 18, 18);
        postBR = sheet.crop(153, 626, 18, 18);
        postUL = sheet.crop(153, 626, 18, 18);
        postUR = sheet.crop(153, 626, 18, 18);
        horWall = sheet.crop(0, 733, 970, 20);
        verWall_L = sheet.crop(971, 0, 20, 600);
        verWall_R = sheet.crop(992, 0, 20, 600);
        verNet = sheet.crop(181, 604, 3, 41);
        horNet = sheet.crop(181, 642, 125, 3);
        balls0 = sheet.crop(179, 650, 110, 21);
        balls1 = sheet.crop(179, 673, 110, 21);
        balls2 = sheet.crop(179, 696, 110, 21);
        balls3 = sheet.crop(290, 650, 110, 21);
        balls4 = sheet.crop(290, 673, 110, 21);
        balls5 = sheet.crop(290, 696, 110, 21);
        cup = sheet.crop(401, 673, 50, 55);
        blueShoe = sheet.crop(513, 600, 23, 69);
        blueShoeTrans = sheet.crop(916, 600, 23, 69);
        redShoe = sheet.crop(538, 600, 23, 69);
        redShoeTrans = sheet.crop(941, 600, 23, 69);
        ballIndicatorBig = sheet.crop(562, 600, 97, 97);
        blueHalo = sheet.crop(660, 600, 107, 107);
        redHalo = sheet.crop(768, 600, 107, 107);
        ballIndicatorBlue = sheet.crop(876, 600, 20, 20);
        ballIndicatorRed = sheet.crop(896, 600, 20, 20);
        gradientBlue = sheet.crop(0, 753, 50, 200);
        gradientRed = sheet.crop(50, 753, 50, 200);
        clock = sheet.crop(200, 773, 139, 170);
        clockHand = sheet.crop(408, 776, 22, 57);
        clockFace = sheet.crop(339, 774, 65, 128);
        whiteFace = sheet.crop(693, 773, 131, 131);
        blueFace = sheet.crop(432, 773, 131, 131);
        redFace = sheet.crop(562, 774, 131, 131);
        whiteBar = sheet.crop(340, 924, 154, 19);
        blueBar = sheet.crop(495, 904, 154, 19);
        redBar = sheet.crop(495, 924, 154, 19);
        //ball
        framesBall[0] = sheet.crop(200, 753, 20, 20);
        framesBall[1] = sheet.crop(201 + 20 * 1, 753, 20, 20);
        framesBall[2] = sheet.crop(201 + 20 * 2, 753, 20, 20);
        framesBall[3] = sheet.crop(201 + 20 * 3, 753, 20, 20);
        framesBall[4] = sheet.crop(201 + 20 * 4, 753, 20, 20);
        framesBall[5] = sheet.crop(201 + 20 * 5, 753, 20, 20);
        framesBall[6] = sheet.crop(201 + 20 * 6, 753, 20, 20);
        framesBall[7] = sheet.crop(201 + 20 * 7, 753, 20, 20);
        framesBall[8] = sheet.crop(201 + 20 * 8, 753, 20, 20);
        framesBall[9] = sheet.crop(201 + 20 * 9, 753, 20, 20);
        framesBall[10] = sheet.crop(201 + 20 * 10, 753, 20, 20);
        framesBall[11] = sheet.crop(201 + 20 * 11, 753, 20, 20);
        framesBall[12] = sheet.crop(201 + 20 * 12, 753, 20, 20);
        framesBall[13] = sheet.crop(201 + 20 * 13, 753, 20, 20);
        framesBall[14] = sheet.crop(201 + 20 * 14, 753, 20, 20);
        framesBall[15] = sheet.crop(201 + 20 * 15, 753, 20, 20);
        framesBall[16] = sheet.crop(201 + 20 * 16, 753, 20, 20);
        framesBall[17] = sheet.crop(201 + 20 * 17, 753, 20, 20);
        framesBall[18] = sheet.crop(201 + 20 * 18, 753, 20, 20);
        framesBall[19] = sheet.crop(201 + 20 * 19, 753, 20, 20);
        //player1
        framesPlayer1[0] = sheet.crop(0, 955, 46, 26);
        player1 = framesPlayer1[0];
        framesPlayer1[1] = sheet.crop(46 * 1, 955, 46, 26);
        framesPlayer1[2] = sheet.crop(46 * 2, 955, 46, 26);
        framesPlayer1[3] = sheet.crop(46 * 3, 955, 46, 26);
        framesPlayer1[4] = sheet.crop(46 * 4, 955, 46, 26);
        framesPlayer1[5] = sheet.crop(46 * 5, 955, 46, 26);
        framesPlayer1[6] = sheet.crop(46 * 6, 955, 46, 26);
        framesPlayer1[7] = sheet.crop(46 * 7, 955, 46, 26);
        framesPlayer1[8] = sheet.crop(46 * 8, 955, 46, 26);
        framesPlayer1[9] = sheet.crop(46 * 9, 955, 46, 26);
        framesPlayer1[10] = sheet.crop(46 * 10, 955, 46, 26);
        framesPlayer1[11] = sheet.crop(46 * 11, 955, 46, 26);
        framesPlayer1[12] = sheet.crop(46 * 12, 955, 46, 26);
        framesPlayer1[13] = sheet.crop(46 * 13, 955, 46, 26);
        framesPlayer1[14] = sheet.crop(46 * 14, 955, 46, 26);
        framesPlayer1[15] = sheet.crop(46 * 15, 955, 46, 26);
        framesPlayer1[16] = sheet.crop(46 * 16, 955, 46, 26);
        framesPlayer1[17] = sheet.crop(46 * 17, 955, 46, 26);
        framesPlayer1[18] = sheet.crop(46 * 18, 955, 46, 26);
        framesPlayer1[19] = sheet.crop(46 * 19, 955, 46, 26);
        //player2
        framesPlayer2[0] = sheet.crop(0, 982, 46, 26);
        player2 = framesPlayer2[0];
        framesPlayer2[1] = sheet.crop(46 * 1, 982, 46, 26);
        framesPlayer2[2] = sheet.crop(46 * 2, 982, 46, 26);
        framesPlayer2[3] = sheet.crop(46 * 3, 982, 46, 26);
        framesPlayer2[4] = sheet.crop(46 * 4, 982, 46, 26);
        framesPlayer2[5] = sheet.crop(46 * 5, 982, 46, 26);
        framesPlayer2[6] = sheet.crop(46 * 6, 982, 46, 26);
        framesPlayer2[7] = sheet.crop(46 * 7, 982, 46, 26);
        framesPlayer2[8] = sheet.crop(46 * 8, 982, 46, 26);
        framesPlayer2[9] = sheet.crop(46 * 9, 982, 46, 26);
        framesPlayer2[10] = sheet.crop(46 * 10, 982, 46, 26);
        framesPlayer2[11] = sheet.crop(46 * 11, 982, 46, 26);
        framesPlayer2[12] = sheet.crop(46 * 12, 982, 46, 26);
        framesPlayer2[13] = sheet.crop(46 * 13, 982, 46, 26);
        framesPlayer2[14] = sheet.crop(46 * 14, 982, 46, 26);
        framesPlayer2[15] = sheet.crop(46 * 15, 982, 46, 26);
        framesPlayer2[16] = sheet.crop(46 * 16, 982, 46, 26);
        framesPlayer2[17] = sheet.crop(46 * 17, 982, 46, 26);
        framesPlayer2[18] = sheet.crop(46 * 18, 982, 46, 26);
        framesPlayer2[19] = sheet.crop(46 * 19, 982, 46, 26);
        //start board
        startBoard[2] = sheet.crop(0, 1011, 209, 144);
        startBoard[1] = sheet.crop(210, 1011, 209, 144);
        startBoard[0] = sheet.crop(420, 1011, 209, 144);
        startBoard[3] = sheet.crop(630, 1011, 209, 144);
        //buttons
        btn_start[0] = sheet.crop(843, 876, 90, 56);
        btn_start[1] = sheet.crop(934, 876, 90, 56);
        btn_trainingP1[0] = sheet.crop(843, 762, 90, 56);
        btn_trainingP1[1] = sheet.crop(934, 762, 90, 56);
        btn_trainingP2[0] = sheet.crop(843, 819, 90, 56);
        btn_trainingP2[1] = sheet.crop(934, 819, 90, 56);
        btn_settings[0] = sheet.crop(843, 1011, 90, 56);
        btn_settings[1] = sheet.crop(934, 1011, 90, 56);
        btn_info[0] = sheet.crop(843, 1068, 90, 56);
        btn_info[1] = sheet.crop(934, 1068, 90, 56);

        btn_arrowUP[0] = sheet.crop(923, 938, 100, 64);
        btn_arrowUP[1] = sheet.crop(924, 1162, 100, 64);

        btn_arrowLEFT[0] = sheet.crop(924, 1359, 100, 66);
        btn_arrowLEFT[1] = sheet.crop(924, 1425, 100, 64);

        btn_arrowRIGHT[0] = sheet.crop(924, 1228, 100, 66);
        btn_arrowRIGHT[1] = sheet.crop(924, 1294, 100, 64);

        menuBackground = sheet.crop(0, 1156, 600, 600);
        halvesText = sheet.crop(848, 1127, 166, 33);
        imgGrass = sheet.crop(608, 1163, 100, 100);
        imgUrban = sheet.crop(608, 1264, 100, 100);
        thunderBlue = sheet.crop(710, 1165, 95, 95);
        thunderRed = sheet.crop(712, 1266, 95, 95);
        kickingShoeBlue = sheet.crop(617, 1367, 137, 194);
        kickingShoeRed = sheet.crop(767, 1368, 137, 194);
        photoKajder = sheet.crop(1026, 600, 200, 283);
        gameController = sheet.crop(1026, 885, 900, 519);
        keyboardSettings = sheet.crop(1026, 1406, 900, 519);
    }


    public static void setConcrete() {
        playground = concrete;
    }

    public static void setGrass() {
        playground = grass;
    }

}
