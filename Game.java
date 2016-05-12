import java.util.Random;

/**
* Write a description of class Test here.
* 
* @author Peter Fousteris
* @version 28/04/2016
*
*/

public class Game {
   private short goals[] = {0, 0};
   private int turns = 0;
   private static Team A;
   private static Team B;
   private static Ball ball;
   private static short[] t = { 5,3 };

   public Game() { 
      this.A = new Team( (short) 0 );
      this.B = new Team( (short) 1 );
      this.ball =  new Ball( "Addidas", t );
      this.goals = goals;
      this.turns = turns;
   }

   public void incTurn() { turns++; }
   
   public int getTurn() { return turns; }
   
   public static void runTurn() {
      Random rn = new Random(); 
      int _team_ = rn.nextInt( 2 );
      if ( _team_ == 0 )  {
         A.action( B, ball );
         B.action( A, ball );
      }
      else {
         B.action( A, ball );
         A.action( B, ball );
      }
      System.out.println( "End of turn" );
   }

   public Ball getBall() { return ball; }

   public Team getTeam( short index ) { 
      if ( index == 0 ) return A;
      return B;
   }

   public short[] getGoals() { return goals; }
}
