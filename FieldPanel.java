import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.*;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldPanel extends JPanel implements ActionListener {

  String[] field_out = { "textures/track/main.png", "textures/track/netl.png", "textures/track/netr.png","textures/track/netlw.png", "textures/track/netrw.png" };
  String ball = "textures/ball.png";
  String[] team = { "textures/team/shirt.png","textures/team/shirt1.png","textures/team/shirt_card.png","textures/team/shirt1_card.png" };
  String card = "textures/card.png";
  Timer timer;
  ArrayList<CellPane> fieldCells  = new ArrayList<CellPane>();
  ArrayList<CellPane> scoreCells  = new ArrayList<CellPane>();
  ArrayList<GridBagConstraints> fieldGbc  = new ArrayList<GridBagConstraints>();
  ArrayList<GridBagConstraints> scoreGbc  = new ArrayList<GridBagConstraints>();
  private URL imageSrc;
  private BufferedImage bi;
  int w,h;
  boolean paused,started,initialized = false;
  public static Game game1;
  private static int timerStep = 100; /* 0.1 second */
  private static int currentSpeed = 2*timerStep; /* 1 second */
  private String[] field_l = {  "textures/field/low/main.png",
                                      "textures/field/low/wt.png",
                                      "textures/field/low/wl.png",
                                      "textures/field/low/wr.png",
                                      "textures/field/low/wlt.png",
                                      "textures/field/low/wrt.png",
                                      "textures/field/low/area/wbl.png",
                                      "textures/field/low/area/wbr.png",
                                      "textures/field/low/center/wb.png",
                                      "textures/field/low/center/wbl.png",
                                      "textures/field/low/center/wbr.png",
                                      "textures/field/low/center/cl.png",
                                      "textures/field/low/center/cr.png"        
                                  };

  private String[] field_h = {  "textures/field/high/main.png",
                                "textures/field/high/wt.png",
                                "textures/field/high/wl.png",
                                "textures/field/high/wr.png",
                                "textures/field/high/wlt.png",
                                "textures/field/high/wrt.png",
                                "textures/field/high/area/wbl.png",
                                "textures/field/high/area/wbr.png",
                                "textures/field/high/center/wb.png",
                                "textures/field/high/center/wbl.png",
                                "textures/field/high/center/wbr.png",
                                "textures/field/high/center/cl.png",
                                "textures/field/high/center/cr.png"        
                            };

  private String[] scoreBoard = {  "textures/s_board/0.png",
                                    "textures/s_board/1.png",
                                    "textures/s_board/2.png",
                                    "textures/s_board/3.png",
                                    "textures/s_board/4.png",
                                    "textures/s_board/5.png",
                                    "textures/s_board/6.png",
                                    "textures/s_board/7.png",
                                    "textures/s_board/8.png",
                                    "textures/s_board/9.png",
                                    "textures/s_board/dashr.png",
                                    "textures/s_board/dashl.png",
                                    "textures/s_board/blank.png"        
                            };

  ArrayList<CellPane> gameCells = new ArrayList<CellPane>();
  ArrayList<GridBagConstraints> gameGbc = new ArrayList<GridBagConstraints>(); ;
  boolean tt = false;
  JLabel statusbar;

  public void actionPerformed( ActionEvent e ) {
    upGameObjects( );
    getScoreBoard();
    setGameObjects();
    validate();
    repaint();
  }

  private void time( boolean direction ) {
    if ( direction ) {
      if ( currentSpeed == 50*timerStep ) {
        System.out.println( "Game Speed Riched Maximum limit (5.0 sec)" );
        return;
      }
      currentSpeed += timerStep; /* Increase by half of Second the Speed of Game */
      timer.setDelay( currentSpeed );
    } else {
      if ( currentSpeed == timerStep ) {
        System.out.println( "Game Speed Riched Lower limit (0.1 sec)" );
        return;
      }
      currentSpeed -= timerStep; /* Decrease by half of Second the Speed of Game */
      timer.setDelay( currentSpeed );      
    }
    System.out.println( "Game Speed Setted to ("+ ( (currentSpeed/100)*0.1 ) + "sec)" );
  }

  private void pause() {
    if ( !started ) return;
    if ( !paused ) {
      timer.stop();
      System.out.println( "Game Paused" );
    } else {
      timer.start();
      System.out.println( "Game Running" );
    }
    paused = !paused;
  }
        
  public void start() {
    if ( started ) return;
    if ( !initialized ) {
      System.out.println( "Load game data first!." );
      return;
    }
    if ( !started ) {
      started = true;
      timer.start();
      System.out.println( "Game Running" );
    }

  }

  public void init() {
    if ( !initialized ) {
      initialized = true;
      getGameObjects();
      setGameObjects();
      System.out.println( "Game Initialized" );
      validate();
      repaint();
    }
  }

  private void getField() {
    for (int row = 1; row < 13; row++) {
      for (int col = 0; col < 8; col++) {
        CellPane cellPane = new CellPane();
        GridBagConstraints gbc = new GridBagConstraints();
        /* Outside Track */
        if ( ( row == 1 ) || ( ( col == 0 ) || ( col == 7 ) ) ) {
          if ( col == 3 ) cellPane.setBackgroundImg( readBugImg( field_out[1] ) );
          else if ( col == 4 ) cellPane.setBackgroundImg( readBugImg( field_out[2] ) );
          else cellPane.setBackgroundImg( readBugImg( field_out[0] ) );
        }
        else if ( ( row == 12 ) ) {
          if ( col == 3 ) cellPane.setBackgroundImg( readBugImg( field_out[3] ) );
          else if ( col == 4 ) cellPane.setBackgroundImg( readBugImg( field_out[4] ) );
          else cellPane.setBackgroundImg( readBugImg( field_out[0] ) );
        }
        else if ( row % 2 != 1 ) {
          if ( row == 2 ) {
            if ( col == 1 ) cellPane.setBackgroundImg( readBugImg( field_l[4] ) );
            else if ( col == 3  ) cellPane.setBackgroundImg( readBugImg( field_l[6] ) ); 
            else if ( col == 4  ) cellPane.setBackgroundImg( readBugImg( field_l[7] ) );
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_l[5] ) );
            else cellPane.setBackgroundImg( readBugImg( field_l[1] ) );
          }
          else if ( row == 6 ) {
            if ( col == 1 ) cellPane.setBackgroundImg( readBugImg( field_l[9] ) );
            else if ( col == 3  ) cellPane.setBackgroundImg( readBugImg( field_l[11] ) );
            else if ( col == 4  ) cellPane.setBackgroundImg( readBugImg( field_l[12] ) );
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_l[10] ) );
            else cellPane.setBackgroundImg( readBugImg( field_l[8] ) );
          }
          else {
            if ( col == 1 ) { 
                cellPane.setBackgroundImg( readBugImg( field_l[2] ) );
            }
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_l[3] ) );
            else cellPane.setBackgroundImg( readBugImg( field_l[0] ) );
          }
        }
        else {
          if ( row == 11 ) {
            if ( col == 1 ) cellPane.setBackgroundImg( readBugImg( field_h[4] ) );
            else if ( col == 3  ) cellPane.setBackgroundImg( readBugImg( field_h[6] ) );
            else if ( col == 4  ) cellPane.setBackgroundImg( readBugImg( field_h[7] ) );
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_h[5] ) );
            else cellPane.setBackgroundImg( readBugImg( field_h[1] ) );
          }
          else if ( row == 7 ) {
            if ( col == 1 ) cellPane.setBackgroundImg( readBugImg( field_h[9] ) );
            else if ( col == 3  ) cellPane.setBackgroundImg( readBugImg( field_h[11] ) );
            else if ( col == 4  ) cellPane.setBackgroundImg( readBugImg( field_h[12] ) );
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_h[10] ) );
            else cellPane.setBackgroundImg( readBugImg( field_h[8] ) );
          }
          else {
            if ( col == 1 ) cellPane.setBackgroundImg( readBugImg( field_h[2] ) );
            else if ( col == 6  ) cellPane.setBackgroundImg( readBugImg( field_h[3] ) );
            else cellPane.setBackgroundImg( readBugImg( field_h[0] ) );
          }
        }
        fieldCells.add( cellPane );
        gbc.gridx = col;
        gbc.gridy = row;
        fieldGbc.add( gbc );
      }
    }
  }
  
  private void setScoreBoard() {
    for (int col = 0; col < 8; col++) {
        CellPane cellPane = new CellPane();
        GridBagConstraints gbc = new GridBagConstraints();
        scoreCells.add( cellPane );
        if ( ( col == 0 ) || ( col == 1 ) || ( col == 6 ) || ( col == 7 ) )
          cellPane.setBackgroundImg( readBugImg( scoreBoard[12] ) );
        else if ( col == 3 )
          cellPane.setBackgroundImg( readBugImg( scoreBoard[10] ) );
        else if ( col == 4 )
          cellPane.setBackgroundImg( readBugImg( scoreBoard[11] ) );
        else
          cellPane.setBackgroundImg( readBugImg( scoreBoard[0] ) );
        gbc.gridx = col;
        gbc.gridy = 0;
        scoreGbc.add( gbc );
      }
  }

  private void getScoreBoard() {
  	/* Update ScoreBoard Panels with current team's goals */
    scoreCells.get( 2 ).setBackgroundImg( readBugImg( scoreBoard[ game1.getTeam( (short) 0 ).getGoals() ] ) );
    scoreCells.get( 5 ).setBackgroundImg( readBugImg( scoreBoard[ game1.getTeam( (short) 1 ).getGoals() ] ) );
  }

  public FieldPanel( Game game ) {
    game1 =  game;
    setLayout( new GridBagLayout() );
    setScoreBoard();
    getField();
    setField();
    timer = new Timer( currentSpeed, this );
    setFocusable(true);
    addKeyListener(new TAdapter());   
  }

  public void setField() {
    for ( int i =0; i < scoreCells.size(); i++ )
      add( scoreCells.get( i ), scoreGbc.get( i ), 0 ); /* Add on background */

    for ( int i =0; i < fieldCells.size(); i++ )
      add( fieldCells.get( i ), fieldGbc.get( i ), 0 ); /* Add on background */
  }

  public void upGameObjects(  ) {
    Random rn = new Random(); 
    int _rv_ = rn.nextInt( 2 );
    if ( ( game1.getTeam( (short) 0 ).getGoals() == 7 ) || ( game1.getTeam( (short) 1 ).getGoals() == 7 ) ) {
    	System.out.println("\nGame is Over!");
    	System.out.println(game1.getTeam( (short) 0 ).getName()+" vs "+game1.getTeam( (short) 1 ).getName());
    	game1.getTeam( (short) 0 ).printTeam();
    	game1.getTeam( (short) 1 ).printTeam();
    	System.out.println("\nStatistics!");
    	game1.getTeam( (short) 0 ).getStats();
    	game1.getTeam( (short) 1 ).getStats();  
    	System.out.println( "Total turns " + game1.getTurn() );
    	timer.stop();
   	}
			game1.runTurn();
			if ( game1.getBall().getCurPlayer() == null ) {
			  if ( game1.getBall().nearNet() ) {
			     _rv_ = rn.nextInt( 2 );
			     if ( _rv_ == 0 ) {
			        System.out.println( "GOAAAAAAL!!!!" ); /* Debug Log */
			        System.out.println( "Scorer " + game1.getBall().getPrevPlayer().getName() );
		          if ( game1.getTeam( (short) 0 ).isPlayer( game1.getBall().getPrevPlayer() ) )
		             game1.getTeam( (short) 0 ).setGoals();
		          else
		            game1.getTeam( (short) 1 ).setGoals();
			        System.out.println( "Score is " + game1.getTeam( (short) 0 ).getGoals() + "-" + game1.getTeam( (short) 1 ).getGoals() );   
			        game1.getBall().setCurPlayer( null );
			        game1.getBall().setPrevPlayer( null );
			        game1.getBall().setX((short)5);
			        game1.getBall().setY((short)5);
			     }else {
			        game1.getBall().assign( game1.getTeam( (short) 0 ), game1.getTeam( (short) 1 ) );
			     }
			  } else {
			        game1.getBall().assign( game1.getTeam( (short) 0 ), game1.getTeam( (short) 1 ) );
			  }
			}
	    gameGbc.set( 0, ball2Gbc( game1.getBall() ) );
	    int j = 0, k = 0;
	    for ( int i = 1; i < gameGbc.size() ; i++ ) {
	      if ( i % 2 == 0 ){
	        gameGbc.set( i, player2Gbc( game1.getTeam( (short) 1 ).getPlayer( k ), 1 ) );
	        if ( game1.getTeam( (short) 1 ).getPlayer( k ).getCard() )
	        	gameCells.get(i).setBackgroundImg( readBugImg( team[2] ) );
	        k++;
	      }
	      else {
	        gameGbc.set( i, player2Gbc( game1.getTeam( (short) 0 ).getPlayer( j ), 0 ) );
			if ( game1.getTeam( (short) 1 ).getPlayer( j ).getCard() )
	        	gameCells.get(i).setBackgroundImg( readBugImg( team[3] ) );
	        j++;
	      }
	    }
	 
  }

  public void setGameObjects() {
    for ( int i =0; i < gameCells.size(); i++)
      add( gameCells.get( i ), gameGbc.get( i ), 1 );
  }
        
  public void paint( Graphics g ) {
    super.paint(g);
    Dimension size = getSize();
  }

  public void getGameObjects(  ) {
    gameGbc.add( 0 ,ball2Gbc( game1.getBall() ) );
    gameCells.add( 0, ball2Cpane() );
    for ( int i = 0; i < game1.getTeam( (short) 0 ).getPlayers().length ; i+=1 ) {
      gameGbc.add(  player2Gbc( game1.getTeam( (short) 0 ).getPlayer( i ), 0 ) );
      gameCells.add(  player2Cpane( game1.getTeam( (short) 0 ).getPlayer( i ), 0 ) );
      gameGbc.add(  player2Gbc( game1.getTeam( (short) 1 ).getPlayer( i ), 1 ) );
      gameCells.add(  player2Cpane( game1.getTeam( (short) 1 ).getPlayer( i ), 1 ) );
    }
  }

  private JLabel num2Label( int num ) { 
    JLabel label = new JLabel( Integer.toString( num ) );
    label.setForeground( Color.WHITE );
    label.setFont( new Font( "Arial", Font.BOLD, 25 ) );
    label.setPreferredSize( new Dimension( 60, 20 ) );
    label.setHorizontalAlignment( JLabel.CENTER );
    return label;
  }

  private JLabel name2Label( String name ) { 
    JLabel label = new JLabel( name );
    label.setForeground( Color.WHITE );
    label.setFont( new Font( "Arial", Font.ITALIC, 8 ) );
    label.setPreferredSize( new Dimension( 60, 20 ) );
    label.setHorizontalAlignment( JLabel.CENTER );
    label.setVerticalAlignment( JLabel.BOTTOM );
    return label;
  }

  private JLabel cords2Label( Player player ) { 
    JLabel label = new JLabel( "(" + player.getX() + ", " + player.getY() + ")" );
    label.setForeground( Color.GRAY );
    label.setFont( new Font( "Arial", Font.BOLD, 12 ) );
    label.setPreferredSize( new Dimension( 10, 10 ) );
    label.setHorizontalAlignment( JLabel.CENTER );
    return label;
  }

  private JPanel cardPane() {
    JPanel cellPane = new JPanel();
    cellPane.setBackground( Color.YELLOW );  
    cellPane.setPreferredSize( new Dimension( 10, 10 ) );
    return cellPane;          
  }
        
  private CellPane player2Cpane( Player player, int index ) {
    CellPane cellPane = new CellPane();
    cellPane.setBackgroundImg( readBugImg( team[index] ) );
    cellPane.add( name2Label( player.getName() ), 0 );
    cellPane.add( num2Label( player.getNumber() ), 1 );
    cellPane.setVisible( true );
    return cellPane;
  }

  private GridBagConstraints player2Gbc( Player player, int team ) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1 + player.getY();
    if ( team == 0 ) gbc.gridy = 11 - player.getX();
    else gbc.gridy = 2 + player.getX();
    return gbc;
  }

  private GridBagConstraints ball2Gbc( Ball ball ) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1 + ball.getY();
    gbc.gridy = 11 - ball.getX();
    return gbc;
  }

  private CellPane ball2Cpane() {
    CellPane cellPane = new CellPane();
    cellPane.setBackgroundImg( readBugImg( ball ) ); 
    return cellPane;
  }


  private BufferedImage readBugImg( String FileName ) {
    BufferedImage img = null;
    try {
        img = ImageIO.read( new File( FileName ) );
    } catch ( IOException e ) {
       e.printStackTrace();
    }
    return img;
  }
        
  class TAdapter extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      int keycode = e.getKeyCode();
      if ( keycode == 'p' || keycode == 'P' )
        pause(); /* Pause Game */
      if ( keycode == 's' || keycode == 'S' )
        start(); /* Pause Game */
      if ( keycode == 'l' || keycode == 'L' )
        init(); /* Pause Game */
      if ( keycode == 38 )
        time( true ); /* Pause Game */      
      if ( keycode == 40 )
        time( false ); /* Pause Game */
      }
   }


}