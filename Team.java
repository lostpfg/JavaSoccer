import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Team here.
 * 
 * @author (your name) 
 * @version 0.2
 */

public class Team {

  private String _name_; /* Team's name */
  private short _goals_ = 0; /* Team's goals */
  private int _passes_ = 0; /* Team's passes */
  private int _mistakes_ = 0; /* Team's wrong passes */

  private Player[] _Players_ = new Player[10]; /* Team's Players */
  /**
  * Constructor for objects of class Team
  */

  public Team( short index ) {
    try { 
      File inputFile = new File("Teams.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      NodeList tList = doc.getElementsByTagName("team"); /* Get team Nodes */
      /* Parse Team Nodes */
      Node tNode = tList.item( index ); /* Get child nodes */
      if ( tNode.getNodeType() == Node.ELEMENT_NODE ) {
         Element tElement = (Element) tNode; /* Get Elements of node */
         this._name_ = tElement.getElementsByTagName("name").item(0).getTextContent();
         NodeList pList = tElement.getElementsByTagName("players"); /* Get players Nodes */
         Node pNode = pList.item(0); 
         if ( pNode.getNodeType() == Node.ELEMENT_NODE ) { 
            Element pElement = (Element) pNode; /* Get Elements of node */
            NodeList dList = pElement.getElementsByTagName("defence"); /* Get defence Nodes */
            Node _dNode_ = dList.item(0); /* Get child nodes */
            if ( _dNode_.getNodeType() == Node.ELEMENT_NODE ) { 
               Element _dElement_ = (Element) _dNode_; /* Get Elements of node */
               NodeList _dList_ = _dElement_.getElementsByTagName("player"); /* Get player Nodes */
               for ( int _def_ = 0; _def_ < _dList_.getLength(); _def_++ ) {  /* For every defender */
                  Node dNode = _dList_.item( _def_ ); /* Get player node */
                  if ( dNode.getNodeType() == Node.ELEMENT_NODE ) { 
                    Element dElement = (Element) dNode; /* Get Elements of node */
                    String _pName_ = dElement.getElementsByTagName("name").item(0).getTextContent(); /* Get Player Name */
                    short _pNum_ = Short.parseShort( dElement.getElementsByTagName("number").item(0).getTextContent() ); /* Get Player's Number */
                    short[] _pPos_ = { Short.parseShort( dElement.getElementsByTagName("x").item(0).getTextContent() ), Short.parseShort ( dElement.getElementsByTagName("y").item(0).getTextContent() ) };
                    this._Players_[_def_] = new Defender( _pName_, _pNum_, _pPos_ );           
                  }
               }
            }
            NodeList sList = pElement.getElementsByTagName("attack"); /* Get attack Nodes */
            Node _sNode_ = sList.item(0); /* Get child nodes */  
            if ( _sNode_.getNodeType() == Node.ELEMENT_NODE ) { 
               Element _sElement_ = (Element) _sNode_; /* Get Elements of node */
               NodeList _sList_ = _sElement_.getElementsByTagName("player"); /* Get player Nodes */
               for ( int _str_ = 0; _str_ < _sList_.getLength(); _str_++ ) {  /* For every defender */
                  Node sNode = _sList_.item( _str_ ); /* Get player node */
                  if ( sNode.getNodeType() == Node.ELEMENT_NODE ) { 
                     Element sElement = (Element) sNode; /* Get Elements of node */
                     String _pName_ = sElement.getElementsByTagName("name").item(0).getTextContent(); /* Get Player Name */
                     short _pNum_ = Short.parseShort( sElement.getElementsByTagName("number").item(0).getTextContent() ); /* Get Player's Number */
                     short[] _pPos_ = { Short.parseShort( sElement.getElementsByTagName("x").item(0).getTextContent() ), Short.parseShort ( sElement.getElementsByTagName("y").item(0).getTextContent() ) };
                     this._Players_[ _str_ + 5 ] = new Striker( _pName_, _pNum_, _pPos_ );   
                  }
               }
            }             
         }       
      }
      this._passes_ = _passes_;
      this._mistakes_ = _mistakes_;
    }
    catch (Exception e) {
       e.printStackTrace();
    }
    this._goals_ = _goals_;
  }

  public void setName( String name ) { _name_ = name; }

  public String getName() { return _name_; }  

  public void setGoals() { _goals_++; }

  public short getGoals() { return _goals_; } 

  public Player getPlayer( int index ) { return _Players_[ index ]; }

  public Player[] getPlayers() { return _Players_; }

  public void setPasses() { _passes_++; }

  public int getPasses() { return _passes_; }

  public void setMistakes() { _mistakes_++; }

  public int getMistakes() { return _mistakes_; }
  
  public void printTeam() {
    System.out.println( getName() + "Squad" );
    for( int i = 0 ;i<=9;i++) {
      System.out.println( getPlayer(i).getName() );
    }
  }

  public void getStats() {
    System.out.println( getName() );
    System.out.println( "Total Passes " + ( getPasses() + getMistakes() ) );
    System.out.println( "Mistakes " + getMistakes() );
  }
  public boolean isPlaymate( Player player, Ball ball ) {
    Player[] _team_ = getPlayers(); 
    for ( int p = 0 ; p < _team_.length ; p++ ) { /* Check if a Playmate has the ball */
      if ( getPlayer( p ) != player ) {
        if ( ball.hasBall( getPlayer( p ) ) ) {
          return true;
        }
      }
    }
    return false;
  }

  public Player getRandPlayer() {
    Random rn = new Random();
    int _mode_ = rn.nextInt( 10 );
    return this.getPlayer( _mode_ );
  }

  public void action( Team other, Ball ball ) {
    Random rn = new Random();
    for ( int i = 0 ; i < 10 ; i++ ) {
      int _mode_ = rn.nextInt( 100 );
      if ( _mode_ < 35 ) this.getPlayer(i).move( other, ball );
      else if ( _mode_ < 65 )  this.getPlayer(i).transition( this, ball );
      else this.getPlayer(i).specialMove( this, other, ball );
    }
  }

  public boolean isPlayer( Player player ) {
    for ( int i = 0 ; i < 10 ; i++ )
      if ( this.getPlayer(i) == player )
        return true;
    return false;
  }

}
