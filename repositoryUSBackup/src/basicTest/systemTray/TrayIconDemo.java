package basicTest.systemTray;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TrayIconDemo
{
   /**
    * main method for testing only
    * @param args
    */
   public static void main ( String[] args )
   {
      new TrayIconDemo ();
   }
   
   public TrayIconDemo ()
   {
      SwingUtilities.invokeLater ( new Runnable ()
      {
         public void run ()
         {
            createAndShowGUI ();
         }
      } );
   }

   /**
    * 
    */
   private void createAndShowGUI ()
   {
      // Check the SystemTray support
      if ( !SystemTray.isSupported () )
      {
         JOptionPane.showMessageDialog ( null, "SystemTray is not supported", "USBackup", JOptionPane.ERROR_MESSAGE );
         return;
      }
      
      // Get Ressources for SystemTray
      Image image = createImage ( "images/ita.gif" );
      if ( image == null )
      {
         JOptionPane.showMessageDialog ( null, "TrayImage not found", "USBackup", JOptionPane.ERROR_MESSAGE );
         return;
      }
      
      TrayIcon trayIcon = new TrayIcon ( image );
      trayIcon.setImageAutoSize ( true );
      SystemTray tray = SystemTray.getSystemTray ();

      // Create visible components
      setLookAndFeel ();

      // Create main menu
      PopupMenu popup = new PopupMenu ();
      
      // Create popup menu components
      MenuItem         aboutItem   = new MenuItem ( "About" );
      CheckboxMenuItem cb2         = new CheckboxMenuItem ( "Set tooltip" );
      Menu             displayMenu = new Menu ( "Display" );
      MenuItem         errorItem   = new MenuItem ( "Error" );
      MenuItem         warningItem = new MenuItem ( "Warning" );
      MenuItem         infoItem    = new MenuItem ( "Info" );
      MenuItem         noneItem    = new MenuItem ( "None" );
      MenuItem         exitItem    = new MenuItem ( "Exit" );

      // Add components to popup menu
      popup.add ( aboutItem );
      popup.addSeparator ();
      popup.add ( cb2 );
      popup.addSeparator ();
      popup.add ( displayMenu );
      displayMenu.add ( errorItem );
      displayMenu.add ( warningItem );
      displayMenu.add ( infoItem );
      displayMenu.add ( noneItem );
      popup.add ( exitItem );

      trayIcon.setPopupMenu ( popup );

      try
      {
         tray.add ( trayIcon );
      }
      catch ( AWTException e )
      {
         JOptionPane.showMessageDialog ( null, "TrayIcon could not be added.", "USBackup", JOptionPane.ERROR_MESSAGE );
         return;
      }

      trayIcon.addMouseListener (  new MouseListener()
      {
         
         public void mouseReleased ( MouseEvent e )
         {
            // TODO Auto-generated method stub
            
         }
         
         public void mousePressed ( MouseEvent e )
         {
            // TODO Auto-generated method stub
            
         }
         
         public void mouseExited ( MouseEvent e )
         {
            // TODO Auto-generated method stub
            
         }
         
         public void mouseEntered ( MouseEvent e )
         {
            // TODO Auto-generated method stub
            
         }
         
         public void mouseClicked ( MouseEvent e )
         {
            if ( e.getButton () == MouseEvent.BUTTON1) // Linker Maus-Button
               JOptionPane.showMessageDialog ( null, "This dialog box is run from System Tray" );
         }
     
      } );

      aboutItem.addActionListener ( new ActionListener ()
      {
         public void actionPerformed ( ActionEvent e )
         {
            JOptionPane.showMessageDialog ( null, "This dialog box is run from the About menu item" );
         }
      } );

      cb2.addItemListener ( new ItemListener ()
      {
         public void itemStateChanged ( ItemEvent e )
         {
            int cb2Id = e.getStateChange ();
            if ( cb2Id == ItemEvent.SELECTED )
            {
               trayIcon.setToolTip ( "Menü öffnet sich mit rechter Maustaste" );
            }
            else
            {
               trayIcon.setToolTip ( null );
            }
         }
      } );

      ActionListener listener = new ActionListener ()
      {
         public void actionPerformed ( ActionEvent e )
         {
            MenuItem item = (MenuItem) e.getSource ();
            // TrayIcon.MessageType type = null;
            System.out.println ( item.getLabel () );
            if ( "Error".equals ( item.getLabel () ) )
            {
               // type = TrayIcon.MessageType.ERROR;
               trayIcon.displayMessage ( "Sun TrayIcon Demo", "This is an error message", TrayIcon.MessageType.ERROR );

            }
            else if ( "Warning".equals ( item.getLabel () ) )
            {
               // type = TrayIcon.MessageType.WARNING;
               trayIcon.displayMessage ( "Sun TrayIcon Demo", "This is a warning message", TrayIcon.MessageType.WARNING );

            }
            else if ( "Info".equals ( item.getLabel () ) )
            {
               // type = TrayIcon.MessageType.INFO;
               trayIcon.displayMessage ( "Sun TrayIcon Demo", "This is an info message", TrayIcon.MessageType.INFO );

            }
            else if ( "None".equals ( item.getLabel () ) )
            {
               // type = TrayIcon.MessageType.NONE;
               trayIcon.displayMessage ( "Sun TrayIcon Demo", "This is an ordinary message", TrayIcon.MessageType.NONE );
            }
         }
      };

      errorItem.addActionListener ( listener );
      warningItem.addActionListener ( listener );
      infoItem.addActionListener ( listener );
      noneItem.addActionListener ( listener );

      exitItem.addActionListener ( new ActionListener ()
      {
         public void actionPerformed ( ActionEvent e )
         {
            tray.remove ( trayIcon );
            System.exit ( 0 );
         }
      } );
   }

   /**
    * Create image object from image file
    * 
    * @param path Absolute or relative path to image to be loaded
    * @return     The image object - null if no image found 
    */
   private Image createImage ( String path )
   {
      URL imageURL = ClassLoader.getSystemClassLoader ().getResource ( path );
      if ( imageURL == null )
      {
         System.err.println ( "Resource not found: "
               + path );
         return null;
      }
      else
      {
         return ( new ImageIcon ( imageURL ) ).getImage ();
      }
   }

   /**
    * Set Look & Feel to current system style.
    * Will be "Metal", if no system style is supported. 
    */
   private void setLookAndFeel ( )
   {
      /* Use an appropriate Look and Feel */
      try
      {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch ( Exception ex )
      {
         ex.printStackTrace ();
      }
   }
}