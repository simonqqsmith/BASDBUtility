
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class BASDBUtility extends JFrame
{
    /**
	 * BASDBUtility by BAS Technologies
	 */
	private static final long serialVersionUID = 1L;
	JLabel Header1;
     JButton hotBackup;
     JButton duplicateDB;
     JButton coldBackup;
     JButton restoreDB;
     
   public BASDBUtility()
   {
     getContentPane().setLayout(null);
     setupGUI();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   void setupGUI()
   {
	String workingdir=System.getProperty("user.dir");
     	Header1 = new JLabel();
	Header1.setLocation(43,32);
	Header1.setSize(250,50);
	Header1.setText("Select the backup or restore option below:");
	getContentPane().add(Header1);

	hotBackup = new JButton();
	hotBackup.setLocation(44,95);
	hotBackup.setSize(120,50);
	hotBackup.setText("Hot Backup");
	hotBackup.setToolTipText("Online backup");
	hotBackup.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            Runtime runtime = Runtime.getRuntime();
	            try {
	                runtime.exec("cmd.exe /C start " + workingdir + "\\scripts\\hot_backup.cmd");
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    });
	getContentPane().add(hotBackup);

	duplicateDB = new JButton();
	duplicateDB.setLocation(45,154);
	duplicateDB.setSize(120,50);
	duplicateDB.setText("Duplicate DB");
	duplicateDB.setToolTipText("Duplicate DB from a backup. Ideal for cloning");
	duplicateDB.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("cmd.exe /C start " + workingdir + "\\scripts\\refresh.cmd");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    });
	getContentPane().add(duplicateDB);

	coldBackup = new JButton();
	coldBackup.setLocation(191,96);
	coldBackup.setSize(120,50);
	coldBackup.setText("Cold Backup");
	coldBackup.setToolTipText("Offline backup. Ideal for snapshots");
	coldBackup.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("cmd.exe /C start " + workingdir + "\\scripts\\snapshot.cmd");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    });
	getContentPane().add(coldBackup);

	restoreDB = new JButton();
	restoreDB.setLocation(191,155);
	restoreDB.setSize(120,50);
	restoreDB.setText("Restore DB");
	restoreDB.setToolTipText("Restore database from backup");
	restoreDB.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("cmd.exe /C start " + workingdir + "\\scripts\\restore_snapshot.cmd");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    });
	getContentPane().add(restoreDB);

	setTitle("BAS DB Utility");
	setSize(400,287);
	setVisible(true);
	setResizable(true);
	
	
   }
    public static void main( String args[] )
   {
     new BASDBUtility();
   }
}  
