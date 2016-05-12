import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class Defender here.
 * 
 * @author Peter Fousteris
 * @version 19/4/2016
 */

public class Defender extends Player {
  
  private boolean _yellowCard_ = false;
  private boolean _redCard_ = false; // Red Card flag
  static private int _steals_ = 0; // Steals of player

  /**
  * Constructor for objects of class Defender
  */

  public Defender( String name, short number, short[] pos ) {
    super( name, number, pos );
    this._yellowCard_ = _yellowCard_;
    this._redCard_ = _redCard_;
    this._steals_ = _steals_;
  }

  protected boolean isNear( Player rival ) {
    /* If on same row, bellow or above with Rival */
    return ( ( this.getY() == ( rival.getY() + 1) ) || ( this.getY() == ( rival.getY() - 1 ) ) || ( this.getY() == ( rival.getY() ) ) );
  }

  public void setCard() { _yellowCard_ = true; }
  public boolean isActive() { return !_redCard_; }

  public boolean getCard() { return _yellowCard_; }

  private void setSteals() { _steals_ = _steals_ + 1; }

  public void specialMove( Team team , Team other, Ball ball ) {
    if ( !ball.hasBall( this ) ) { /* If player is not the owner of the ball */
      ArrayList<Player> _opps_ = this.getNearByOpponents( other ); /* Get nearby Opponents */
      for ( int i = 0; i < _opps_.size(); i++ ) { /* Loop over the list */
        if ( ( ball.hasBall( _opps_.get(i) ) ) && ( this.isNear( _opps_.get(i) ) ) ) {
          Random rn = new Random();
          int _tr_ = rn.nextInt( 10 );
          if ( _tr_ < 7 ) {
            setSteals();
            System.out.println( this.getName() + " stole the ball from " + _opps_.get(i).getName() + " from {" + ball.getX() + ", " + ball.getY() + "}" ); /* Debug Log */
            ball.setX( this.getX() );
            ball.setY( this.getY() );
            ball.setPrevPlayer( _opps_.get(i) );
            ball.setCurPlayer( this );
            System.out.println( this.getName() + " has now the ball at {" + ball.getX() + ", " + ball.getY() + "}" ); /* Debug Log */
          }
          else if ( _tr_ < 9 )
            setCard();
        }
      } 
    }
  }

  private ArrayList<Player> getNearByOpponents( Team team ) {
    ArrayList<Player> _opps_ = new ArrayList<Player>(); /* Gets all nearby Opponent Players of Team other  */
    _opps_.add( team.getPlayer( 9 - this.getX() ) ); /* Add Opponent from the Same row ( x cordinate ) */
    if ( this.getX() > 0 ) _opps_.add( team.getPlayer( 10 - this.getX() ) ); /* Add Rival from the Bellow Line if possible */
    if ( this.getX() < 9 ) _opps_.add( team.getPlayer( 8 - this.getX() ) );  /* Add Rival from the Above Line if possible */
    return _opps_;
  }
  
}
