/*Sriram M.
 * Alex P.
 *                                                                      Missile Command
 * This game is a player versus computer type of game play, where the objective of the game is for the player
 * to protect his moon station from the incoming missiles 'falling' from the sky. The missiles shot by the user
 * come from the missile station and are wherever the mouse cursor is located. The missile travels until the 
 * point at which the mouse was clicked then explodes, at which point the explosion takes out other enemy missiles
 * which in turn causes other explosions and so on and so forth. Some unique features in this game are the
 * explosion, which are basically sprite images that expand outwards, and contact with these explosion cause
 * even more damage. Another unique feature is the difficulty settings, where the speed can be increased
 * to 2x as fast or 4x as fast, which provides a really immersive gaming experience for talented players.
 * Furthermore, the screens in the game are designed to be very user-friendly and can be easily navigated. This
 * allows for more gaming time and less time finding your way to the 'play game' button. Also, players can
 * choose what level they want to play at in the levels screen (and view their best scores in each level), as 
 * well as cycle through all of the levels while playing the game. A how-to-play screen is also included so that
 * new players can quickly learn how to play the game and waste less time experimenting and going through
 * the struggles of tedious learning. There are no documented errors or glitches in the game, as it has been
 * thoroughly tested by getting new players to play and test the game. Happy gaming! 
 */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class GUI extends JFrame implements Runnable, MouseListener, MouseMotionListener
{

	int width = 600; //sets the width of the window/JFrame
	int height = 600; //sets the height of the window/JFrame

	int cursorX;
	int cursorY;
	final int CURSORSIZEX;
	final int CURSORSIZEY;
	final int XCURSOROFFSET;
	final int YCURSOROFFSET;

	//creates new colors
	Color lightCyan = new Color (0, 204, 204);
	Color darkCyan = new Color (0, 153, 153);

	Color lightRed = new Color (255, 117, 117);
	Color darkRed = new Color (255, 71, 71);

	//declares variables that keeps track of the font size
	int smallFontSize;
	int largeFontSize;

	//creates new fonts
	Font smallFont;
	Font largeFont;

	boolean mouseClicked; //keeps track of whether the mouse is clicked
	boolean running; //keeps the while loop inside the "run()" method running

	World planet; //creates an instance of the world class

	boolean paused; //whether the game is paused
	boolean mainScreen=true; //whether the mainScreen could be displayed
	boolean playGame=false; //whether game is playing of not
	
	//the following line of booleans control the appearance of the different screens on the JPanel, and are ultimately decide the flow of logic in
	//screen events. most of the screens are for externally designed (Paint) pictures that are simply drawn on the screen.
	boolean drawPlayGamePic=false, drawLevelsPic=false, drawHTPPic=false, drawSettingsPic=false, drawCreditsPic=false, levelsScreen=false, drawLevelsScreen=false, drawLevelsScreen1=false,drawLevelsScreen2=false,drawLevelsScreen3=false,drawLevelsScreen4=false,drawLevelsScreen5=false,backToMainScreen=false, howToPlayScreen=false, backtomainscreen=false, drawSettingsScreen=false, drawCreditsScreen=false, drawCreditsScreenBack=false, drawGameOverScreen=false,drawGameOverScreenAgain=false,drawGameOverScreenExit=false,drawPauseScreen=false, drawPauseScreenExit=false, drawPauseScreenContinue=false, levelCompleteScreen=false,levelCompleteScreenReturn=false,levelCompleteScreenProceed=false,levelCompleteScreenSettings=false,levelCompleteScreenSettingsSelected=false,levelCompleteScreenSettingsReturn=false;

	boolean settingsScreenPack;
	boolean showSettingsScreen;
	boolean showSettingsScreenLeft;
	boolean showSettingsScreenRight;
	boolean showSettingsScreenBack;

	int settingsTracker; //keeps track of the current setting in the settings screen
	
	//Creates variables that will import images later on
	ImageIcon icon;

	BufferedImage planetSurface;

	BufferedImage missileDish;

	boolean prevScoreCounter; //keeps track of whether the "prevScore" variable should be updated
	
	double prevScore; //keeps track of the current score in game
	double bestScoreLvl1; //keeps track of the best score in level 1
	double bestScoreLvl2; //keeps track of the best score in level 2
	double bestScoreLvl3; //keeps track of the best score in level 3
	double bestScoreLvl4; //keeps track of the best score in level 4
	double bestScoreLvl5; //keeps track of the best score in level 5

	int xMissileDish; //x position of the missile dish
	int yMissileDish; //y position of the missile dish
	int missileDishSize; //size of the missile dish (width and height)

	//Creates variables that will import images later on
	BufferedImage missileDishLightingBigImg;
	BufferedImage[] missileDishLightingSprites;

	int missileDishLightingSpritesSize; //how big the "missileDishLightingSprites" array is going to be
	int missileDishLightingSpritesIndex; //at which index the "missileDishLightingSprites" is current at

	//keeps track of how fast the "missileDishLightingSpritesIndex" should change
	int missileDishLightingTimer; 
	int missileDishLightingTimerTime;

	//Creates variables that will import images later on
	BufferedImage missilesAmmo;

	int xMissilesAmmo; //x position of the ammo at the bottom of the game screen
	int missilesAmmoSize; //size of the ammo picture (width and height)
	
	//the following are the names assigned to the various buffered images that are implemented in the game. these images include all of the screen images, the planet
	//found when playing the game, the space background, etc.

	BufferedImage mainScreenPic;
	BufferedImage playGamePic;
	BufferedImage levelsPic;
	BufferedImage howToPlay;
	BufferedImage settings;
	BufferedImage credits;
	BufferedImage levelScreen;
	BufferedImage backToMS;
	BufferedImage HTPScreen;
	BufferedImage HTPScreenBack;
	BufferedImage creditsScreen;
	BufferedImage creditsScreenBack;
	BufferedImage deathStar;
	BufferedImage pauseScreen;
	BufferedImage gameOverScreen;
	BufferedImage pauseScreenExit;
	BufferedImage pauseScreenContinue;
	BufferedImage gameOverScreenAgain;
	BufferedImage gameOverScreenExit;
	BufferedImage settingsScreen;
	BufferedImage settingsScreenLeft;
	BufferedImage settingsScreenRight;
	BufferedImage settingsScreenBack;
	BufferedImage levelsScreenImage;
	BufferedImage levelsScreenImage1;
	BufferedImage levelsScreenImage2;
	BufferedImage levelsScreenImage3;
	BufferedImage levelsScreenImage4;
	BufferedImage levelsScreenImage5;
	BufferedImage levelCompleteScreenImage;
	BufferedImage levelCompleteScreenImageReturn;
	BufferedImage levelCompleteScreenImageProceed;
	BufferedImage levelCompleteScreenImageSettings;
	BufferedImage levelCompleteScreenImageSettingsReturn;
	BufferedImage levelCompleteScreenImageSettingsProceed;

	BufferedImage explosionBigImg;
	BufferedImage[] explosionSprites;
	int explosionSpritesSize; //size of the explosion sprite

	//Creates variables that will import images later on
	BufferedImage backGround;
	double planetSurfaceSize; //size of the planet surface

	int yHealthBarSize; //y size of the health bar picture

	int missilesHeadSize; //size of the heads of the missiles
	
	public GUI ()
	{
		super ("Missile Command"); //changes the name of the top of the window to "Missile Command"
		setSize (width, height); //Sets the size of the JFrame
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //Sets the close operation for the JFrame

		setContentPane (new DrawPane ());

		//adds the required components to the JFrame
		addMouseListener (this);
		addMouseMotionListener (this);

		//sets the layout to null
		setLayout (null);
		//sets it so, that the window/JFrame does not resize when it reaches the edges of the screen
		setLocationRelativeTo (null);

		//Sets the mouse cursor to invisible
				Toolkit toolkit = Toolkit.getDefaultToolkit ();
				Point hotSpot = new Point (0, 0);
				BufferedImage cursorImage = new BufferedImage (1, 1, BufferedImage.TRANSLUCENT);
				Cursor invisibleCursor = toolkit.createCustomCursor (cursorImage, hotSpot, "InvisibleCursor");
				setCursor (invisibleCursor);

		//sets the windows/JFrame to visible
		setVisible (true);

		//Declares all the values for the variables
		cursorX = 0;
		cursorY = 0;
		CURSORSIZEX = 10;
		CURSORSIZEY = 10;
		XCURSOROFFSET = 5;
		YCURSOROFFSET = 30;

		lightCyan = new Color (0, 204, 204);
		darkCyan = new Color (0, 153, 153);

		lightRed = new Color (255, 117, 117);
		darkRed = new Color (255, 71, 71);

		smallFontSize = 20;
		largeFontSize = 40;

		smallFont = new Font("Calibri",Font.BOLD,smallFontSize);
		largeFont = new Font("Calibri", Font.BOLD, largeFontSize);

		planet = new World(getWidth(), getHeight());

		settingsScreenPack = false;
		showSettingsScreen = false;
		showSettingsScreenLeft = false;
		showSettingsScreenRight = false;
		showSettingsScreenBack = false;

		settingsTracker = 0;

		paused = false;
		prevScoreCounter = false;
		
		prevScore = 0;
		bestScoreLvl1 = 0;
		bestScoreLvl2 = 0;
		bestScoreLvl3 = 0;
		bestScoreLvl4 = 0;
		bestScoreLvl5 = 0;

		planetSurfaceSize = 100;

		xMissileDish = getWidth();
		yMissileDish = 10;
		missileDishSize = 75;

		yHealthBarSize = 10;

		xMissilesAmmo = 0;
		missilesAmmoSize = 20;


		missilesHeadSize = 5;
		
		//try and catch statement, if the image file could not be found
		try {
			
			//the following lines of code take the names of the buffered images above and relate them to the images actually found
			//in the game files using the ImageIO.read command to 'read' these images and incorporate them into the program. as mentioned
			//above, most of these screens are externally developed (Microsoft Paint) to suit the design of the game.
			
			icon = new ImageIcon("Missile_Command_Icon.png");
			setIconImage(icon.getImage());

			backGround = ImageIO.read(new File("Night_Sky.png"));
			planetSurface = ImageIO.read(new File("Planet_Surface101.gif"));
			missileDish = ImageIO.read(new File("Missile_Dish.gif"));
			missileDishLightingBigImg = ImageIO.read(new File("Lighting.gif"));

			explosionBigImg = ImageIO.read(new File("Explosion_Sprite.gif"));
			mainScreenPic=ImageIO.read(new File("main_screen.png"));
			missilesAmmo = ImageIO.read(new File("Missiles_Ammo101.gif"));
			playGamePic=ImageIO.read(new File ("main_screen_playgame.png"));
			levelsPic=ImageIO.read(new File("main_screen_levels.png"));
			howToPlay=ImageIO.read(new File("main_screen_howtoplay.png"));
			settings=ImageIO.read(new File("main_screen_settings.png"));
			credits=ImageIO.read(new File("main_screen_credits.png"));
			levelScreen=ImageIO.read(new File("levels_screen.png"));
			backToMS=ImageIO.read(new File("levels_screen_backtomainscreen.png"));
			HTPScreen=ImageIO.read(new File("howtoplayscreen.png"));
			HTPScreenBack=ImageIO.read(new File("howtoplayscreenback.png"));
			creditsScreen=ImageIO.read(new File("credits.png"));
			creditsScreenBack=ImageIO.read(new File("creditsback.png"));
			pauseScreen=ImageIO.read(new File("game_paused_screen.png"));
			pauseScreenExit=ImageIO.read(new File("game_paused_screen_return.png"));
			pauseScreenContinue=ImageIO.read(new File("game_paused_screen_continue.png"));
			gameOverScreen=ImageIO.read(new File("game_over_screen.png"));
			gameOverScreenAgain=ImageIO.read(new File("game_over_screen_tryagain.png"));
			gameOverScreenExit=ImageIO.read(new File("game_over_screen_return.png"));

			settingsScreen=ImageIO.read(new File("settings_screen.png"));
			settingsScreenLeft=ImageIO.read(new File("settings_screen_left.PNG"));
			settingsScreenRight=ImageIO.read(new File("settings_screen_right.PNG"));
			settingsScreenBack=ImageIO.read(new File("settings_screen_back.PNG"));

			levelsScreenImage=ImageIO.read(new File("levels_screen.PNG"));
			levelsScreenImage1=ImageIO.read(new File("levels_screen_1.PNG"));
			levelsScreenImage2=ImageIO.read(new File("levels_screen_2.PNG"));
			levelsScreenImage3=ImageIO.read(new File("levels_screen_3.PNG"));
			levelsScreenImage4=ImageIO.read(new File("levels_screen_4.PNG"));
			levelsScreenImage5=ImageIO.read(new File("levels_screen_5.PNG"));

			levelCompleteScreenImage=ImageIO.read(new File("level_complete_screen.PNG"));
			levelCompleteScreenImageReturn=ImageIO.read(new File("level_complete_screen_return.PNG"));
			levelCompleteScreenImageProceed=ImageIO.read(new File("level_complete_screen_proceed.PNG"));
			levelCompleteScreenImageSettings=ImageIO.read(new File("level_complete_screen_settings.PNG"));
			levelCompleteScreenImageSettingsReturn=ImageIO.read(new File("level_complete_screen_settings_return.PNG"));
			levelCompleteScreenImageSettingsProceed=ImageIO.read(new File("level_complete_screen_settings_selected.PNG"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not load the image files.");
		}

		missileDishLightingSpritesSize = 8;
		missileDishLightingSpritesIndex = 0;
		missileDishLightingTimer = 0;
		missileDishLightingTimerTime = 2;

		missileDishLightingSprites = new BufferedImage[missileDishLightingSpritesSize];
		//gets all the images located inside the "missileDishLightingBigImg" using the "getSubimage" method
		missileDishLightingSprites[0] = missileDishLightingBigImg.getSubimage(0, 0, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[1] = missileDishLightingBigImg.getSubimage(0, 100, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[2] = missileDishLightingBigImg.getSubimage(0, 200, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[3] = missileDishLightingBigImg.getSubimage(0, 300, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[4] = missileDishLightingBigImg.getSubimage(0, 400, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[5] = missileDishLightingBigImg.getSubimage(0, 500, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[6] = missileDishLightingBigImg.getSubimage(0, 600, missileDishLightingBigImg.getWidth(), 100);
		missileDishLightingSprites[7] = missileDishLightingBigImg.getSubimage(0, 700, missileDishLightingBigImg.getWidth(), 125);

		explosionSpritesSize = 12;

		explosionSprites = new BufferedImage[explosionSpritesSize];

		//gets all the images located inside the "explosionBigImg" using the "getSubimage" method
		explosionSprites[0] = explosionBigImg.getSubimage(59, 43, 56, 46);
		explosionSprites[1] = explosionBigImg.getSubimage(267, 28, 72, 71);
		explosionSprites[2] = explosionBigImg.getSubimage(471, 19, 83, 82);
		explosionSprites[3] = explosionBigImg.getSubimage(40, 145, 87, 85);
		explosionSprites[4] = explosionBigImg.getSubimage(257, 130, 92, 98);
		explosionSprites[5] = explosionBigImg.getSubimage(462, 128, 98, 106);
		explosionSprites[6] = explosionBigImg.getSubimage(43, 264, 94, 105);
		explosionSprites[7] = explosionBigImg.getSubimage(253, 253, 116, 117);
		explosionSprites[8] = explosionBigImg.getSubimage(460, 253, 126, 115);
		explosionSprites[9] = explosionBigImg.getSubimage(33, 399, 124, 115);
		explosionSprites[10] = explosionBigImg.getSubimage(253, 396, 132, 110);
		explosionSprites[11] = explosionBigImg.getSubimage(460, 394, 128, 115);
		/////////////////////////////////////////////////////////////////////////////

		mouseClicked = false;
		running = true;
	}


	public static void main (String args[])
	{
		new GUI ().run (); //creates a new instance of the GUI
	}


	@ Override
	public void run ()
	{
		// TODO Auto-generated method stub

		//Calculates when the game should be updated and when the window/JFrame should be updates
		
		long lastTime = System.nanoTime ();
		double nsPerCalc = 1000000000D / 60D;

		int calcs = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis ();
		double delta = 0;


		while (running)
		{

			long now = System.nanoTime ();
			delta += (now - lastTime) / nsPerCalc;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1)
			{
				calcs++;

				//Program's Logic
				if (!paused)
					calc ();

				delta -= 1;
				shouldRender = true;
			}

			if (shouldRender)
			{
				frames++;

				planet.updateScore();
				//drawing method
				revalidate ();
				repaint ();
			}

			try
			{
				Thread.sleep (2);
			}
			//throws an InterruptedException
			catch (InterruptedException e)
			{
				e.printStackTrace ();
			}

			if ((System.currentTimeMillis () - lastTimer) > 1000)
			{
				lastTimer += 1000;
				//System.out.println("FPS: " + calcs);
				frames = 0;
				calcs = 0;
			}
		}
	}


	private void calc ()
	{
		// TODO Auto-generated method stub

		if(playGame==true){//if the boolean controlling the playing of the game is true then

			setAllFalse();//calls the method which sets all of the booleans in the game to false
			playGame=true;//sets playGame to true again so that the game can continue

			if(planet.getGameWon()==true){//if a level has been completed then
				setAllFalse();//sets all booleans false
				if(planet.getLevel()!=5){//if the game is on any other level besides 5 then
					levelCompleteScreen=true;//call on the screen meant for all of the other levels besides 5
				}
				else if(planet.getLevel()==5){//if the game is on level 5 then
					levelCompleteScreenSettings=true;///call on the screen meant for level 5
				}
			}

			//calls the "updateScreenSize" method from the planet class
			planet.updateScreenSize(getWidth(), getHeight());

			//calls the "updateBackGround" method from the planet class
			planet.updateBackGround();

			//decides whether the "missileDishLightingSpritesIndex" should be updated or reset to zero
			if (missileDishLightingTimer == 0){

				missileDishLightingTimer = missileDishLightingTimerTime;

				if (missileDishLightingSpritesIndex < (missileDishLightingSprites.length - 1))
					missileDishLightingSpritesIndex++;
				else
					missileDishLightingSpritesIndex = 0;
			}
			else if (missileDishLightingTimer > 0)
				missileDishLightingTimer--;

			//is true if the mouse if clicked and the cursor is above the planet surface
			if (mouseClicked && cursorY < getHeight() - planetSurfaceSize - planet.getCURSOROFFSET() && planet.getCurrNumOfMissiles() > 0){
				//calls the "addMissiles" method from the planet class
				planet.addMissiles(planetSurfaceSize, cursorX - XCURSOROFFSET, cursorY - YCURSOROFFSET);
			}

			//calls the "updateMissiles" method from the planet class
			planet.updateMissiles ();
			//calls the "updateExplosions" method from the planet class
			planet.updateExplosions ();
			
			if(!planet.getDied()){
				planet.updateEnemyMissiles(planetSurfaceSize);
			}
			//resets the variables if the player had died
			else{
				setAllFalse();
				drawGameOverScreen=true;
				planet.setDied(false);
				
				//calls the "updateScore" method
				updateScore();

				//calls the "reset" method from the planet class
				planet.reset(planet.getLevel());
			}
			//calls the "updateHealth" method from the planet class
			planet.updateHealth();
			//calls the "collisions" method from the planet class
			planet.collisions();

		}
		////////////////////////////////////////////////////////////////////////////
	}

	/*
	 * gets the best and prev score based on the level 
	 */
	private void updateScore() {
		// TODO Auto-generated method stub
		
		if (planet.getShownScore()!=0)
			prevScore = planet.getShownScore();

		if (planet.getLevel() == 1){
			if (bestScoreLvl1 < planet.getShownScore())
				bestScoreLvl1 = planet.getShownScore();
		}
		if (planet.getLevel() == 2){
			if (bestScoreLvl2 < planet.getShownScore())
				bestScoreLvl2 = planet.getShownScore();
		}
		if (planet.getLevel() == 3){
			if (bestScoreLvl3 < planet.getShownScore())
				bestScoreLvl3 = planet.getShownScore();
		}
		if (planet.getLevel() == 4){
			if (bestScoreLvl4 < planet.getShownScore())
				bestScoreLvl4 = planet.getShownScore();
		}
		if (planet.getLevel() == 5){
			if (bestScoreLvl5 < planet.getShownScore())
				bestScoreLvl5 = planet.getShownScore();
		}
	}


	//create a component that you can actually draw on.
	class DrawPane extends JPanel
	{
		public void paintComponent (Graphics g)
		{
			//draw on g here e.g.
			Graphics2D g2 = (Graphics2D) g;

			g2.setStroke(new BasicStroke(3));
			
			
			/*
			 *the entire following lines of code control the different images that are drawn on the screen based on the booleans that are true and false. 
			 *there are several situations that need to be met since mouseListener is being used, hence there are a lot of different images that need
			 *to be drawn and cases that need to be met. 
			 */
			
			
			
			
			if(mainScreen==true){//if the main screen is true then
				g2.drawImage(mainScreenPic,0,0,getWidth(),getHeight(),null);//draws the image meant for the main screen
				if(drawPlayGamePic==true){//if mouse moves over play game, draws screen that highlights the play game section of the screen
					
					//all of the following lines of code follow the same pattern as the couple above, and the only difference is that they are
					//for different screens so they will be implemented in different ways
					
					g2.drawImage(playGamePic,0,0,getWidth(),getHeight(),null);
				}
				else if(drawLevelsPic==true){
					g2.drawImage(levelsPic,0,0,getWidth(),getHeight(),null);
				}
				else if(drawHTPPic==true){
					g2.drawImage(howToPlay,0,0,getWidth(),getHeight(),null);
				}
				else if(drawSettingsPic==true){
					g2.drawImage(settings,0,0,getWidth(),getHeight(),null);
				}
				else if(drawCreditsPic==true){
					g2.drawImage(credits,0,0,getWidth(),getHeight(),null);
				}
			}

			else if(levelCompleteScreen==true){
				g2.drawImage(levelCompleteScreenImage,0,0,getWidth(),getHeight(),null);
				if(levelCompleteScreenProceed==true){
					g2.drawImage(levelCompleteScreenImageProceed,0,0,getWidth(),getHeight(),null);
				}
				else if(levelCompleteScreenReturn==true){
					g2.drawImage(levelCompleteScreenImageReturn,0,0,getWidth(),getHeight(),null);
				}
				
				g2.setFont(largeFont);//sets the following text to be in a previously declared font
				g2.setColor(Color.BLACK);//sets the color for the following text to be black
				
				//class the "updateScore" method because the following lines of code show the score on the screen
				updateScore();
				
				g2.drawString(String.valueOf(prevScore), 215, 202);//draws previous score on the screen
				
				//adjusts scores, to be displayed on different screens, to be displayed according to level
				
				if (planet.getLevel() == 1){
					g2.drawString(String.valueOf(bestScoreLvl1), 180, 270);
				}
				if (planet.getLevel() == 2){
					g2.drawString(String.valueOf(bestScoreLvl2), 180, 270);
				}
				if (planet.getLevel() == 3){
					g2.drawString(String.valueOf(bestScoreLvl3), 180, 270);
				}
				if (planet.getLevel() == 4){
					g2.drawString(String.valueOf(bestScoreLvl4), 180, 270);
				}
				if (planet.getLevel() == 5){
					g2.drawString(String.valueOf(bestScoreLvl5), 180, 270);
				}
			}
			
			else if(levelCompleteScreenSettings==true){
				g2.drawImage(levelCompleteScreenImageSettings,0,0,getWidth(),getHeight(),null);
				if(levelCompleteScreenSettingsReturn==true){
					g2.drawImage(levelCompleteScreenImageSettingsReturn,0,0,getWidth(),getHeight(),null);
				}
				else if(levelCompleteScreenSettingsSelected==true){
					g2.drawImage(levelCompleteScreenImageSettingsProceed,0,0,getWidth(),getHeight(),null);
				}
				
				g2.setFont(largeFont);//sets the following text to be in a previously declared font
				g2.setColor(Color.BLACK);//sets the color for the following text to be black
				
				//class the "updateScore" method because the following lines of code show the score on the screen
				updateScore();
				
				g2.drawString(String.valueOf(prevScore), 215, 202); //draws previous score on the screen
				
				//adjusts scores, to be displayed on different screens, to be displayed according to level
				
				if (planet.getLevel() == 1){
					g2.drawString(String.valueOf(bestScoreLvl1), 180, 270);
				}
				if (planet.getLevel() == 2){
					g2.drawString(String.valueOf(bestScoreLvl2), 180, 270);
				}
				if (planet.getLevel() == 3){
					g2.drawString(String.valueOf(bestScoreLvl3), 180, 270);
				}
				if (planet.getLevel() == 4){
					g2.drawString(String.valueOf(bestScoreLvl4), 180, 270);
				}
				if (planet.getLevel() == 5){
					g2.drawString(String.valueOf(bestScoreLvl5), 180, 270);
				}
			}



			else if (playGame==true && drawGameOverScreen==false){//if game is still continuing and the level has not been passed and paused is also false,
				//then reset all booleans to false and set playGame to true. this essentially resets all values modified by the following lines of code.
				if(paused==false){
					setAllFalse();
					playGame=true;
				}


				g2.drawImage(backGround, (int) planet.getXBackGround(), (int) planet.getYBackGround(), getWidth(), getHeight()*2, null);

				for (int i = 0 ; i < planet.getMissiles().size () ; i++)
				{
					Line_Missiles tempMissiles = (Line_Missiles) planet.getMissiles().get (i);
					g2.setColor(lightCyan);
					g2.drawLine ((int) tempMissiles.getXStart (), (int) tempMissiles.getYStart (), (int) tempMissiles.getXCurrent (), (int) tempMissiles.getYCurrent ());
					g2.setColor(darkCyan);
					g2.fillOval((int) tempMissiles.getXCurrent () - missilesHeadSize, (int) tempMissiles.getYCurrent () - missilesHeadSize, missilesHeadSize*2, missilesHeadSize*2);
				}

				g2.setColor(Color.GREEN);
				g2.setFont(smallFont);
				g2.drawString("Score: " + (int) planet.getShownScore(), 5, smallFontSize);
				g2.drawImage(planetSurface, 0, (int) (getHeight() - planetSurfaceSize), getWidth(), (int) planetSurfaceSize, null);

				g2.drawImage(missileDishLightingSprites[missileDishLightingSpritesIndex], getWidth()/2 - 25, getHeight() - missileDishSize - yHealthBarSize - missilesAmmoSize, 50, 25, null);
				g2.drawImage(missileDish, getWidth()/2, getHeight() - missileDishSize - yHealthBarSize - missilesAmmoSize - 5, missileDishSize, missileDishSize, null);
				g2.drawImage(missileDish, getWidth()/2, getHeight() - missileDishSize - yHealthBarSize - missilesAmmoSize - 5, -missileDishSize, missileDishSize, null);

				for (int i = 0 ; i < planet.getExplosions().size () ; i++)
				{
					Explosions tempExplosions = (Explosions) planet.getExplosions().get (i);

					int num = planet.getExplosionUpdateCount().get(i);
					int explosionSpritesIndex = 0;

					for (int j = (planet.getExplosionsSpeed()/explosionSprites.length); j<=planet.getExplosionsSpeed(); j += (planet.getExplosionsSpeed()/explosionSprites.length)){
						if (num <= j)
							g2.drawImage(explosionSprites[explosionSpritesIndex], (int) tempExplosions.getXCurrent (), (int) tempExplosions.getYCurrent (), (int) tempExplosions.getSizeCurrent (), (int) tempExplosions.getSizeCurrent (), null);
						else if (explosionSpritesIndex < (explosionSprites.length - 1))
							explosionSpritesIndex++;

					}
				}




				////////////////////////////////////////////////////////////////////////////

				//Srirams
				////////////////////////////////////////////////////////////////////////////
				
				//the following code draws the lines that act as the enemies, aka the line opponents. the starting x-coordinates are randomly generated within the width
				//of the screen
				g2.setColor(Color.RED);
				for(int x=0;x<planet.getOppo().size();x++){//for loop runs through the line opponents vector and draws every line in the vector
					Line_Opponents temp =  (Line_Opponents) planet.getOppo().get(x);
					g2.setColor(lightRed);
					g2.drawLine ((int) temp.getStartXCoor (), (int) temp.getStartYCoor (), (int) temp.getXCoor (), (int) temp.getYCoor ());
					g2.setColor(darkRed);
					g2.fillOval((int) temp.getXCoor () - missilesHeadSize, (int) temp.getYCoor () - missilesHeadSize, missilesHeadSize*2, missilesHeadSize*2);
				}
				
				////////////////////////////////////////////////////////////////////////////

				g2.setColor(Color.BLACK);
				g2.fillRect(0, getHeight() - yHealthBarSize - missilesAmmoSize - 10, getWidth(), missilesAmmoSize*2);
				g2.setColor(Color.DARK_GRAY);
				g2.fillRect(0, getHeight() - yHealthBarSize, getWidth(), yHealthBarSize);
				g2.drawImage(planet.getShownHealthBar(), 0,getHeight() - yHealthBarSize,(int) (planet.getShownHealth()*((getWidth())/planet.getStartingHealth())),yHealthBarSize, null);

				for (int i = 0; i<planet.getCurrNumOfMissiles(); i++){
					g2.drawImage(missilesAmmo, xMissilesAmmo + missilesAmmoSize*i, getHeight() - yHealthBarSize - missilesAmmoSize - 5, missilesAmmoSize, missilesAmmoSize, null);
				}

				//the screens that are drawn are continued here
				if (drawPauseScreen==true){
					g2.drawImage(pauseScreen,0,0,getWidth(),getHeight(),null);
					if(drawPauseScreenExit==true){
						g2.drawImage(pauseScreenExit,0,0,getWidth(),getHeight(),null);
					}
					else if(drawPauseScreenContinue==true){
						g2.drawImage(pauseScreenContinue,0,0,getWidth(),getHeight(),null);
					}
					
					g2.setFont(smallFont);//sets the following text to be in a previously declared font
					g2.setColor(Color.RED);//sets the color for the following text to be red
					
					//class the "updateScore" method because the following lines of code show the score on the screen
					updateScore();
					
					g2.drawString(String.valueOf(prevScore), 250, 125);//draws previous score on the screen
					
					//adjusts scores, to be displayed on different screens, to be displayed according to level
					if (planet.getLevel() == 1){
						g2.drawString(String.valueOf(bestScoreLvl1), 250, 83);
					}
					if (planet.getLevel() == 2){
						g2.drawString(String.valueOf(bestScoreLvl2), 250, 83);
					}
					if (planet.getLevel() == 3){
						g2.drawString(String.valueOf(bestScoreLvl3), 250, 83);
					}
					if (planet.getLevel() == 4){
						g2.drawString(String.valueOf(bestScoreLvl4), 250, 83);
					}
					if (planet.getLevel() == 5){
						g2.drawString(String.valueOf(bestScoreLvl5), 250, 83);
					}
				}

			}
			
			//used to display the best score on each of the levels in the levels screen 
			else if(levelsScreen==true){
				Font levelsFont = new Font("Calibri",Font.BOLD,40);
				g2.setFont(levelsFont);
				g2.drawImage(levelScreen,0,0,getWidth(),getHeight(),null);
				g2.drawString("Level: "+planet.getLevel(), 230, 180);
				if(backToMainScreen==true){
					g2.setFont(levelsFont);
					g2.drawImage(backToMS,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
				}
				else if(drawLevelsScreen1==true){
					g2.drawImage(levelsScreenImage1,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
					g2.setFont(smallFont);
					g2.drawString("Best Score: " + String.valueOf(bestScoreLvl1), 125, 225);
				}
				else if(drawLevelsScreen2==true){
					g2.drawImage(levelsScreenImage2,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
					g2.setFont(smallFont);
					g2.drawString("Best Score: " + String.valueOf(bestScoreLvl2), 125, 225);
				}
				else if(drawLevelsScreen3==true){
					g2.drawImage(levelsScreenImage3,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
					g2.setFont(smallFont);
					g2.drawString("Best Score: " + String.valueOf(bestScoreLvl3), 125, 225);
				}
				else if(drawLevelsScreen4==true){
					g2.drawImage(levelsScreenImage4,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
					g2.setFont(smallFont);
					g2.drawString("Best Score: " + String.valueOf(bestScoreLvl4), 125, 225);
				}
				else if(drawLevelsScreen5==true){
					g2.drawImage(levelsScreenImage5,0,0,getWidth(),getHeight(),null);
					g2.drawString("Level: "+planet.getLevel(), 230, 180);
					g2.setFont(smallFont);
					g2.drawString("Best Score: " + String.valueOf(bestScoreLvl5), 125, 225);
				}
			}
			
			//controls what is displayed in the different screens in the main menu

			else if(howToPlayScreen==true){
				g2.drawImage(HTPScreen,0,0,getWidth(),getHeight(),null);
				if(backtomainscreen==true){
					g2.drawImage(HTPScreenBack,0,0,getWidth(),getHeight(),null);
				}
			}

			else if (drawCreditsScreen==true){
				g2.drawImage(creditsScreenBack,0,0,getWidth(),getHeight(),null);
				if(drawCreditsScreenBack==true){
					g2.drawImage(creditsScreenBack,0,0,getWidth(),getHeight(),null);
				}
			}

			else if(planet.getDied()==true){
				setAllFalse();
				drawGameOverScreen=true;
			}

			else if(drawGameOverScreen==true){
				g2.drawImage(gameOverScreen,0,0,getWidth(),getHeight(),null);
				if(drawGameOverScreenAgain==true){
					g2.drawImage(gameOverScreenAgain,0,0,getWidth(),getHeight(),null);
				}
				else if(drawGameOverScreenExit==true){
					g2.drawImage(gameOverScreenExit,0,0,getWidth(),getHeight(),null);
				}
				
				g2.setFont(smallFont);//sets the following text to be in a previously declared font
				g2.setColor(Color.RED);//sets the color for the following text to be red
				
				//class the "updateScore" method because the following lines of code show the score on the screen
				updateScore();
				
				g2.drawString(String.valueOf(prevScore), 250, 125);//draws previous score on the screen
				
				//adjusts scores, to be displayed on different screens, to be displayed according to level
				if (planet.getLevel() == 1){
					g2.drawString(String.valueOf(bestScoreLvl1), 250, 83);
				}
				if (planet.getLevel() == 2){
					g2.drawString(String.valueOf(bestScoreLvl2), 250, 83);
				}
				if (planet.getLevel() == 3){
					g2.drawString(String.valueOf(bestScoreLvl3), 250, 83);
				}
				if (planet.getLevel() == 4){
					g2.drawString(String.valueOf(bestScoreLvl4), 250, 83);
				}
				if (planet.getLevel() == 5){
					g2.drawString(String.valueOf(bestScoreLvl5), 250, 83);
				}
			}

			//controls whats currently displayed in the settings screen
			else if(showSettingsScreenLeft){
				g2.drawImage(settingsScreenLeft,0,0,getWidth(),getHeight(),null);
			}
			else if(showSettingsScreenRight){
				g2.drawImage(settingsScreenRight,0,0,getWidth(),getHeight(),null);
			}
			else if(showSettingsScreenBack){
				g2.drawImage(settingsScreenBack,0,0,getWidth(),getHeight(),null);
			}
			else if(showSettingsScreen){
				g2.drawImage(settingsScreen,0,0,getWidth(),getHeight(),null);
			}

			g2.setFont(largeFont);//changes the fonts to a previously declared font
			g2.setColor(Color.RED);//sets the color for the following text to be red
			//controls whats being displayed on the settings screen
			if (showSettingsScreen){
				if (settingsTracker == 0){
					g2.drawString("Normal", getWidth()/2 - largeFontSize - 20, 240);
				}
				if (settingsTracker == 1){
					g2.drawString("2x Fast", getWidth()/2 - largeFontSize - 15, 240);
				}
				if (settingsTracker == 2){
					g2.drawString("4x Fast", getWidth()/2 - largeFontSize - 15, 240);
				}
			}

			//Draws the mouse cursor on the screen
			g2.setColor(Color.RED);//sets the color for the following text to be red
			g2.drawRect (cursorX - XCURSOROFFSET - CURSORSIZEX, cursorY - YCURSOROFFSET - CURSORSIZEY, CURSORSIZEX * 2, CURSORSIZEY * 2);
		}
	}


	@ Override
	public void mouseClicked (MouseEvent e)//this method is called when the left or right mouse buttons are clicked
	{
		if(mainScreen==true){//if the user is on the main screen
			if((cursorX>=104&&cursorX<=489)&&(cursorY>=131&&cursorY<=201)){//if the user clicks the playGame button then the game starts and everything is reset
				setAllFalse();
				planet.reset(planet.getLevel());
				playGame=true;
				prevScore = 0;
			}
			else if((cursorX>=98&&cursorX<=480)&&(cursorY>=360&&cursorY<=430)){ //for 'how to play' button
				setAllFalse();
				howToPlayScreen=true;//makes the how to play screen true

			}

			else if((cursorX>=101&&cursorX<=485)&&(cursorY>=245&&cursorY<=318)){//for 'levels' screen
				setAllFalse();
				levelsScreen=true;//makes the levels screen true

			}

			else if((cursorX>=21&&cursorX<=247)&&(cursorY>=479&&cursorY<=569)){//for the 'settings' screen
				setAllFalse();
				showSettingsScreen=true;//makes the settings screen true
			}

			else if((cursorX>=350&&cursorX<=577)&&(cursorY>=481&&cursorY<=571)){//for the 'credits' screen
				setAllFalse();
				drawCreditsScreen=true; //makes the credits screen true

			}
			else if(drawLevelsPic==true){//for the 'levels' screen
				setAllFalse();
				drawLevelsScreen=true;//makes the levels screen true
			}
		}
		else if(levelsScreen==true || howToPlayScreen==true || drawCreditsScreen==true){//sets the bottom left corner of the screen as the 'return to home screen'
			//section, for the levels screen, howToPlay screen, and credits screen
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){//once this section of the screen is clicked on, the user is taken back to the main menu
				setAllFalse();
				mainScreen=true;

			}
			else if(levelsScreen==true){//if the user is on the levels screen
				if((cursorX>=8&&cursorX<=124)&&(cursorY>=177&&cursorY<=288)){//if the first level is selected, the planet is reset to that level
					//the same concept is applied to all of the other levels
					planet.reset(1);
				}
				else if((cursorX>=126&&cursorX<=241)&&(cursorY>=290&&cursorY<=399)){
					planet.reset(2);                
				}
				else if((cursorX>=243&&cursorX<=358)&&(cursorY>=403&&cursorY<=514)){
					planet.reset(3);
				}
				else if((cursorX>=358&&cursorX<=475)&&(cursorY>=290&&cursorY<=402)){
					planet.reset(4);
				}
				else if((cursorX>=475&&cursorX<=591)&&(cursorY>=178&&cursorY<=290)){
					planet.reset(5);
				}
			}
		}

		else if(drawPauseScreen==true || drawGameOverScreen==true){//the same section of the game over screen and pause screen are dedicated to taking the user back
			//to the home screen
			if((cursorX>=42&&cursorX<=232)&&(cursorY>=335&&cursorY<=415)){//when this section is clicked, the user is taken back to the main screen
				setAllFalse();
				planet.reset(planet.getLevel());
				paused=true;    

				mainScreen=true;

			}
			else if((cursorX>=341&&cursorX<=530)&&(cursorY>=336&&cursorY<=414)){//if the player chooses to 'try again' or 'continue playing' then the game is
				//once again resumed
				setAllFalse();
				playGame=true;
				prevScore=0;
			}
		}
		
		else if(levelCompleteScreen==true){//if the user is on the screen that is reached when a level is complete then
			if((cursorX>=32&&cursorX<=250)&&(cursorY>=413&&cursorY<=550)){//if they choose to return to main screen then main screen is set to true and the user is taken back
				setAllFalse();
				mainScreen=true;
			}
			else if((cursorX>=324&&cursorX<=557)&&(cursorY>=413&&cursorY<=553)){//if the user proceeds to next level, then the planet is reset to the level after 
				//the current one
				setAllFalse();
				planet.reset(planet.getLevel()+1);
				playGame=true;
				prevScore=0;
			}
		}
		
		else if(levelCompleteScreenSettings==true){
			if((cursorX>=32&&cursorX<=250)&&(cursorY>=413&&cursorY<=550)){
				setAllFalse();
				mainScreen=true;
			}
			else if((cursorX>=324&&cursorX<=557)&&(cursorY>=413&&cursorY<=553)){
				setAllFalse();
				showSettingsScreen=true;
			}
		}

		else if (showSettingsScreen){
			//If the back button in the settings screen is clicked
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){
				setAllFalse();
				mainScreen=true;
			}
			//If the user clicks the left arrow in the settings screen
			else if(((cursorX>=35&&cursorX<=100)&&(cursorY>=205&&cursorY<=290))){
				if (settingsTracker > 0){
					settingsTracker--;
					//Calls the "setSpeedDown" method from the planet class
					planet.setSpeedDown();
				}
			}
			//If the user clicks the right arrow in the settings screen
			else if(((cursorX>=495&&cursorX<=560)&&(cursorY>=205&&cursorY<=290))){
				if (settingsTracker < 2){
					settingsTracker++;
					//Class the "setSpeedUp" method from the planet class
					planet.setSpeedUp();
				}
			}
		}



		// TODO Auto-generated method stub
	}


	@ Override
	public void mouseEntered (MouseEvent e)
	{
		// TODO Auto-generated method stub
	}


	@ Override
	public void mouseExited (MouseEvent e)//method called upon when the cursor leaves the screen
	{
		// TODO Auto-generated method stub
		if (!drawGameOverScreen && !levelCompleteScreen)
		drawPauseScreen=true;//makes drawing the pause screen true
		paused = true;//makes paused true
	}


	@ Override
	public void mousePressed (MouseEvent e)
	{
		// TODO Auto-generated method stub
		//If the mouse if pressed
		mouseClicked = true;
	}


	@ Override
	public void mouseReleased (MouseEvent e)
	{
		// TODO Auto-generated method stub
		//If the mouse is released
		mouseClicked = false;
	}


	@ Override
	public void mouseDragged (MouseEvent e)
	{
		// TODO Auto-generated method stub
		//Updates the x and y position of the cursor
		cursorX = e.getX ();
		cursorY = e.getY ();
	}


	@ Override
	public void mouseMoved (MouseEvent e)//method that is called on when the mouse cursor is moved
	{
		// TODO Auto-generated method stub
		cursorX = e.getX ();//gets the x-coordinate of the mouse cursor
		cursorY = e.getY ();//gets the y-coordinate of the mouse cursor
		//System.out.println(cursorX + ", " + cursorY);
		
		/*
		 * the following lines of code dictate what screens will appear (by changing the corresponding booleans) by reading the mouse's cursor location 
		 * and displaying the different images as proper feedback for the user.
		 */

		if(mainScreen==true){
			if((cursorX>=104&&cursorX<=489)&&(cursorY>=131&&cursorY<=201)){
				drawPlayGamePic=true;
			}
			else if((cursorX>=101&&cursorX<=485)&&(cursorY>=245&&cursorY<=318)){
				drawLevelsPic=true;
			}
			else if((cursorX>=98&&cursorX<=480)&&(cursorY>=360&&cursorY<=430)){
				drawHTPPic=true;
			}
			else if((cursorX>=21&&cursorX<=247)&&(cursorY>=479&&cursorY<=569)){
				drawSettingsPic=true;
			}
			else if((cursorX>=350&&cursorX<=577)&&(cursorY>=481&&cursorY<=571)){
				drawCreditsPic=true;
			}
			else{
				drawPlayGamePic=false;
				drawLevelsPic=false;
				drawHTPPic=false;
				drawSettingsPic=false;
				drawCreditsPic=false;
			}
		}

		else if(levelCompleteScreen==true){
			if((cursorX>=32&&cursorX<=250)&&(cursorY>=413&&cursorY<=550)){
				levelCompleteScreenReturn=true;
			}
			else if((cursorX>=324&&cursorX<=557)&&(cursorY>=413&&cursorY<=553)){
				levelCompleteScreenProceed=true;
			}
			else{
				levelCompleteScreenReturn=false;
				levelCompleteScreenProceed=false;
			}
		}
		
		else if(levelCompleteScreenSettings==true){
			if((cursorX>=32&&cursorX<=250)&&(cursorY>=413&&cursorY<=550)){
				levelCompleteScreenSettingsReturn=true;
			}
			else if((cursorX>=324&&cursorX<=557)&&(cursorY>=413&&cursorY<=553)){
				levelCompleteScreenSettingsSelected=true;
			}
			else{
				levelCompleteScreenSettingsReturn=false;
				levelCompleteScreenSettingsSelected=false;
			}
		}

		else if(levelsScreen==true){
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){
				backToMainScreen=true;
			}
			else if((cursorX>=8&&cursorX<=124)&&(cursorY>=177&&cursorY<=288)){
				drawLevelsScreen1=true;
			}
			else if((cursorX>=126&&cursorX<=241)&&(cursorY>=290&&cursorY<=399)){
				drawLevelsScreen2=true;
			}
			else if((cursorX>=243&&cursorX<=358)&&(cursorY>=403&&cursorY<=514)){
				drawLevelsScreen3=true;
			}
			else if((cursorX>=358&&cursorX<=475)&&(cursorY>=290&&cursorY<=402)){
				drawLevelsScreen4=true;
			}
			else if((cursorX>=475&&cursorX<=591)&&(cursorY>=178&&cursorY<=290)){
				drawLevelsScreen5=true;
			}
			else{
				backToMainScreen=false;
				drawLevelsScreen1=false;
				drawLevelsScreen2=false;
				drawLevelsScreen3=false;
				drawLevelsScreen4=false;
				drawLevelsScreen5=false;
			}
		}
		else if (howToPlayScreen==true){
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){
				backtomainscreen=true;
			}
			else{
				backtomainscreen=false;
			}
		}
		else if (showSettingsScreen){
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){
				setAllFalse();
				showSettingsScreen=true;
				showSettingsScreenBack=true;
			}
			else if(((cursorX>=35&&cursorX<=100)&&(cursorY>=205&&cursorY<=290))){
				showSettingsScreenLeft=true;
				showSettingsScreenBack=true;
			}
			else if(((cursorX>=495&&cursorX<=560)&&(cursorY>=205&&cursorY<=290))){
				showSettingsScreenRight=true;
				showSettingsScreenBack=true;
			}
			else{
				setAllFalse();
				showSettingsScreen=true;
			}
		}
		else if (drawCreditsScreen==true){
			if((cursorX>=9&&cursorX<=210)&&(cursorY>=539&&cursorY<=589)){
				drawCreditsScreenBack=true;
			}
			else{
				drawCreditsScreenBack=false;
			}
		}
		else if(drawPauseScreen==true){
			if((cursorX>=42&&cursorX<=232)&&(cursorY>=335&&cursorY<=415)){
				drawPauseScreenExit=true;
			}
			else if((cursorX>=341&&cursorX<=530)&&(cursorY>=336&&cursorY<=414)){
				drawPauseScreenContinue=true;
			}
			else{
				drawPauseScreenExit=false;
				drawPauseScreenContinue=false;
			}
		}
		else if(drawGameOverScreen==true){
			if((cursorX>=341&&cursorX<=530)&&(cursorY>=336&&cursorY<=414)){
				drawGameOverScreenAgain=true;
			}
			else if((cursorX>=42&&cursorX<=232)&&(cursorY>=335&&cursorY<=415)){
				drawGameOverScreenExit=true;
			}
			else{
				drawGameOverScreenAgain=false;
				drawGameOverScreenExit=false;
			}
		}

	}

	private void setAllFalse(){//method that sets all of the booleans in the game to false, so as to avoid boolean confusion
		drawPlayGamePic=false;
		drawLevelsPic=false;
		drawHTPPic=false;
		drawSettingsPic=false; 
		drawCreditsPic=false; 
		levelsScreen=false; 
		backToMainScreen=false; 
		howToPlayScreen=false; 
		backtomainscreen=false; 
		drawSettingsScreen=false; 
		drawCreditsScreen=false; 
		drawCreditsScreenBack=false; 
		drawGameOverScreen=false;
		drawPauseScreen=false;
		playGame=false;
		mainScreen=false;
		levelsScreen=false;
		drawCreditsScreen=false;        
		howToPlayScreen=false;
		paused=false;
		drawPauseScreenExit=false;
		drawPauseScreenContinue=false;
		drawGameOverScreen=false;
		drawGameOverScreenAgain=false;
		drawGameOverScreenExit=false;
		showSettingsScreen = false;
		showSettingsScreenLeft = false;
		showSettingsScreenRight = false;
		showSettingsScreenBack = false;
		drawLevelsScreen1=false;
		drawLevelsScreen2=false;
		drawLevelsScreen3=false;
		drawLevelsScreen4=false;
		drawLevelsScreen5=false;
		drawLevelsScreen=false;
		levelCompleteScreen=false;
		levelCompleteScreenReturn=false;
		levelCompleteScreenProceed=false;
		levelCompleteScreenSettings=false;
		levelCompleteScreenSettingsSelected=false;
		levelCompleteScreenSettingsReturn=false;
	}
}

