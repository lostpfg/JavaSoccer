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

public class GameGraph extends JFrame{

  public static Game game;
  JMenuBar menuBar;
  JMenu menu, submenu;
  public static void main(String [] args) throws UnsupportedEncodingException {
    GameGraph ggo = new GameGraph(); /* Graphics Object */ 
  }

  public GameGraph() {
      EventQueue.invokeLater(new Runnable() {
          @Override
          public void run() {
              try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
              } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
              }
              game = new Game();
              setTitle( "Java Soccer" ); 
              FieldPanel gameP = new FieldPanel( game );
              setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
              setLayout( new BorderLayout() );
              add( gameP ,BorderLayout.CENTER);
              setResizable( false );
              pack();
              setLocationRelativeTo( null );
              setVisible( true );
          }
      });
  }
  
}