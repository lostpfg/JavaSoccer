import java.util.Random;

/**
 * Write a description of class Striker here.
 * 
 * @author Peter Fousteris
 * @version 18/4/2016
 */

public class Striker extends Player {
  
  private short[] moveTable = { -2 , 2 }; /* { Left, Right } */
  private short[] ballTarget = { 0, 1 , 1,  2, 2 , 2, 2, 3, 3, 3, 3 , 4, 4, 5 }; /* 0,5 ~ 0.014, 1,4 ~ 0.28, 2,3 ~ 0.56  */
  
  /**
  * Constructor for objects of class Striker
  */

  public Striker( String name, short number, short[] pos ) {
    super( name, number, pos );
  }

  public void specialMove( Team team , Team other, Ball ball ) {
  	short move;
  	if ( team.isPlaymate( this, ball ) ) { /* Chekc if owner of the ball is a playmate */
  		move = ( other.getPlayer( getX() ).getY() > getY() ) ? moveTable[0] : moveTable[1]; /* Check rivals position either at player's left or right*/
  		if ( canMove( other.getPlayer( getX() ),  move ) ) /* Check if player stays on field */
  			movePlayer( move ); /* Move player at Y cordinate */
  	}
  }

  public void transition( Team team, Ball ball ) {
      short _offset_ = 0; 
      if ( ball.hasBall( this ) ) { /* If player has the ball, move the ball at x axis randomly */
        Random rand = new Random(); /* Get a <Random> instance */
        if ( getX() != 9 ) {
          while ( true ) {
            _offset_ = (short) rand.nextInt( 10 ); /* Random offset( 0 - 9 )  */
            if ( _offset_ > ball.getX() ) break; /* Move Ball only forward positions */
          }
        }
        ball.setX( _offset_ ); /* Move the ball at x axis */
        ball.setY( ballTarget[rand.nextInt( ballTarget.length - 1 )] ); /* Randomly move the ball at y axis */
        ball.setPrevPlayer( this ); /* Set player as the previous owner of the ball */
        ball.setCurPlayer( null );
        System.out.println( getName() + " moved the ball from {" + getX() + ", " + getY() + "} to {" + ball.getX() + ", " + ball.getY() + "}." ); /* Debug Log */
      }
   }
}
