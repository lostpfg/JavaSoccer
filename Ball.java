import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of class sdf here.
 * 
 * @author Peter Fousteris
 * @version 18/4/2016
 *
 */

public class Ball {

  private String _name_;
  private short[] _pos_ = { 2, 3}; /* Cordinates of ball {x,y} */
  private Player _pPlayer_, _cPlayer_;

  public Ball( String name, short[] pos ) {
    this._name_ = name;
    this._pos_ = pos;
    this._cPlayer_ = _cPlayer_;
    this._pPlayer_ = _pPlayer_;
  }

  public void setName( String name ) { _name_ = name; }

  public String getName() { return _name_; }

  public void setPos( short[] pos ){ _pos_ = pos; }

  public short[] getPos(){ return _pos_; } 

  public void setX( short x ) { _pos_[0] = x; }

  public short getX() { return _pos_[0]; }

  public void setY( short y ) { _pos_[1] = y; }

  public short getY() { return _pos_[1]; }

  public boolean hasBall( Player player ) { return ( player == _cPlayer_ ); }

  public void setCurPlayer( Player player ) {
    _cPlayer_ = player; 
    if ( player != null ) {
      setX( player.getX() ); 
      setY( player.getY() ); 
    }
  }

  public Player getCurPlayer() { return _cPlayer_;  }

  public void setPrevPlayer( Player player ) { _pPlayer_ = player; }

  public Player getPrevPlayer() { return _pPlayer_; }

  public boolean nearNet() { return ( ( getY() == 2 ) || ( getY() == 3 ) ) && ( getX() == 9 ); }
  
  public void assign( Team A, Team B ) {
    /* Check for imidiate assigment */
    ArrayList<Player> _nearBy_ = this.getNearByPlayers( A, B ); /* Get nearby Players */
    ArrayList<Player> _shorted_ = new ArrayList<Player>(); /* Gets all nearby Opponent Players of Team other  */
    int _min_ = 99;
    int i = 0;
    while ( i < _nearBy_.size() ) { 
      /* Check for direct Assign */
      if ( ( getY() == _nearBy_.get(i).getY() ) && ( getX() == _nearBy_.get(i).getX() ) ) { // Doulevei
        setCurPlayer( _nearBy_.get(i) );
        break;
      } 
      for ( int _yOffest_ = 1 ; _yOffest_ < 6; _yOffest_++ ) {
        if ( ( _nearBy_.get(i).getY() == getY() + _yOffest_ ) || ( _nearBy_.get(i).getY() == getY() - _yOffest_ ) ) { 
          //System.out.println( "Check Player " + _nearBy_.get(i).getName() + " me offset " +  _yOffest_ );
          if ( _yOffest_ <= _min_ ) {
            _min_ = _yOffest_;
            //System.out.println("Prosthiki tou " +  _nearBy_.get(i).getName() + " me offset " +  _yOffest_ );
          }
          break;  
        }
      }
      i++;
    }
    i = 0;
    while ( i < _nearBy_.size() ) { 
      /* Check for direct Assign */
      if ( ( getY() == _nearBy_.get(i).getY() ) && ( getX() == _nearBy_.get(i).getX() ) ) { // Doulevei
        setCurPlayer( _nearBy_.get(i) );
        break;
      } 
      for ( int _yOffest_ = 1 ; _yOffest_ < 6; _yOffest_++ ) {
        if ( ( _nearBy_.get(i).getY() == getY() + _yOffest_ ) || ( _nearBy_.get(i).getY() == getY() - _yOffest_ ) ) { 
          if ( _yOffest_ == _min_ ) {
            _min_ = _yOffest_;
            _shorted_.add( _nearBy_.get(i) );
            break;  
          }
        }
      }
      i++;
    }    
    /* Now Assign the ball randomly */
    //System.out.println( "Ball is at position {" + getX() + " ," + getY() + "}" );
    if ( getCurPlayer() == null ) {
      //for ( i = 0 ; i < _shorted_.size(); i++ )
       // System.out.println( _shorted_.get(i).getName() + " at position {" + _shorted_.get(i).getX() + " ," + _shorted_.get(i).getY() + "}" );
      Random rn = new Random(); 
      int _rp_ = rn.nextInt( _shorted_.size() );
      setCurPlayer( _shorted_.get(_rp_ ) );

    }
  }

  private ArrayList<Player> getNearByPlayers( Team A, Team B ) {
    ArrayList<Player> _nearBy_ = new ArrayList<Player>(); /* Gets all nearby Opponent Players of Team other  */
    _nearBy_.add( A.getPlayer( getX() ) );
    _nearBy_.add( B.getPlayer( getX() ) );
    if ( getX() > 0 ) {
      _nearBy_.add( A.getPlayer( getX() - 1 ) );
      _nearBy_.add( B.getPlayer( getX() - 1 ) );
    }
    if ( getX() <  9 ) {
      _nearBy_.add( A.getPlayer( getX() + 1 ) );
      _nearBy_.add( B.getPlayer( getX() + 1 ) );      
    }
    return _nearBy_;
  }
}
