import java.util.Random;

/**
 * Write a description of class sdf here.
 * 
 * @author Peter Fousteris
 * @version 18/4/2016
 *
 */

public abstract class Player {

  /* Private Class Variables accessible only within Player class.*/
  private String _name_;
  private short _number_;
  private short[] _pos_ = new short[2]; /* Cordinates of the player {x,y} */
  private short[] moveTable = { -1 , 1 }; /* { Left, Right } */
 
  /**
  * Constructor for objects of class Defender
  */

  public Player( String name, short number, short[] pos ) {
    /* Debug Log */
    this._name_ = name;
    this._number_ = number;
    this._pos_ = pos;
  }

  /* Class methods */

  public void setName( String name ) { _name_ = name; }

  public String getName() { return _name_; }

  public void setNumber( short number ) { _number_ = number; }

  public int getNumber() { return _number_; }

  public void setPos( short[] pos ){ _pos_ = pos; }

  public short[] getPos(){ return _pos_; } 

  public void setX( short x ) { _pos_[0] = x; }

  public short getX() { return _pos_[0]; }

  public void setY( short y ) { _pos_[1] = y; }

  public short getY() { return _pos_[1]; }

  public boolean isActive(){ return true; }

  /**
  * Moves player at given y cordinate allong with the ball if player has the ball.
  * 
  * @param  y    int      Destination of movement ( y cordinate ).
  * @param  ball <Ball>   Refers to a ball object.
  *
  */

  protected void movePlayer( short move ) {
    int _yOld_ = getY(); /* Backup old y cordinate */
    setY( (short) ( getY() + move ) ); /* Move player at y cordinate */
    System.out.println( getName() + " moved from {" + getX() + ", " + _yOld_ + "} to {" + getX() + ", " + getY() + "}." ); /* Debug Log */
  }


  /**
  * Checks wether a movement of a <Player>, at their current y position plus offset keeps them on the field.
  * 
  * @param  opponent    Player      Opponent player object.
  * @param  offset      short       Movement offset.
  *
  */

  protected boolean onField( short offset ) {
    return !( ( ( offset < 0 ) && ( getY() + offset < 0 ) ) || ( ( offset > 0 ) && ( getY() + offset > 5 ) ) );    
  }

  /**
  * Checks wether a movement of a <Player>, at their current y position plus offset makes an overlap with an opponent <Player>.
  * 
  * @param  opponent    Player      Opponent player object.
  * @param  offset      short       Movement offset.
  *
  */

  protected boolean rivalOvelap( Player opponent, short offset ) {
    return ( opponent.getY() == ( getY() + offset ) );
  }

  protected boolean canMove( Player opponent, short move ) {
    return ( !rivalOvelap( opponent, move ) && ( onField( move ) ) );
  }

  public void move( Team other, Ball ball ) { 
    Random rn = new Random();
    short move = moveTable [ 0 + rn.nextInt(2) ];
    /* First we must check if there is a <Player> player from the other team in a near cell */
    if ( canMove( other.getPlayer( 9 - getX() ), move ) ) {
      int _yOld_ = getY(); /* Backup old y cordinate */
      movePlayer( move );
      if ( ball.hasBall( this ) ) { /* If player had the ball move also the ball */
        ball.setY( getY() ); /* Move ball allong with player */
        System.out.println( "Ball moved with " + getName() + " from {" + getX() + ", " + _yOld_ + "} to {" + getX() + ", " + getY() + "}." ); /* Debug Log */
      }
    }
  }
   
  public void transition( Team team, Ball ball ) {
      short offset = 0; 
      if ( ball.hasBall( this ) ) { /* If player has the ball, move the ball at x axis randomly */
        Random rand = new Random(); /* Get a <Random> instance */
        if ( getX() != 9 ) {
          while ( true ) {
            offset = (short) rand.nextInt( 10 ); /* Random ball move  */
            if ( offset > ball.getX() ) break; /* Move only forward */
          }
        }
        ball.setX( offset ); /* Move the ball at x axis */
        ball.setY( (short) rand.nextInt( 5 ) ); /* Randomly move the ball at y axis */
        ball.setPrevPlayer( this ); /* Set Current Player as the Previous Owner of the Ball */
        /* If teamate is on new ball's position set him as Current */
        if ( !directTransition( team, ball ) ) {
          team.setMistakes();
          ball.setCurPlayer( null );
        }
        else team.setPasses();
        System.out.println( getName() + " moved the ball from {" + getX() + ", " + getY() + "} to {" + ball.getX() + ", " + ball.getY() + "}." ); /* Debug Log */
      }
   }

  private boolean directTransition( Team team, Ball ball ) {
    if ( ( ball.getY() == team.getPlayer( getY() ).getY() ) && ( ball.getX() == team.getPlayer( getY() ).getX() ) ) {
      ball.setCurPlayer( team.getPlayer( getX() ) );
      return true;
    }
    return false;
  }

  public void specialMove( Team team , Team other, Ball ball ) {}

  public boolean getCard() { return false; }
}