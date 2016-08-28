package basDbUtility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.*;

public class BASDBUtility extends JFrame
{
    /**
	 * BASDBUtility by BAS Technologies
	 */
	private static final long serialVersionUID = 1L;
	JLabel Header1;
     JButton hotBackup;
     JButton refreshData;
     JButton coldBackup;
     JButton restoreDB;
     JTextArea textArea = new JTextArea(15, 30);
     TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "Output");
     InputStream inputStream;
     String propFileName = "config.properties";
     
   
     public BASDBUtility() throws IOException
   {
     getContentPane().setLayout(null);
     setupGUI();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   void setupGUI() throws IOException
   {
	   
	   Properties prop = new Properties();
	   try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
							prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	   
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
	     @SuppressWarnings("static-access")
		@Override
	     public void actionPerformed(ActionEvent e) {

			String basedir=System.getProperty("user.dir");
			BasGetPassword pw = new BasGetPassword(null);

	        Thread t = new Thread() {
	            public void run() {
	                synchronized(pw.lock) {
	                	pw.main(null);
	                        try {
	                            pw.lock.wait();
	                        } catch (InterruptedException e2) {
	                            e2.printStackTrace();
	                        }
	                      String inputPasswd = pw.getPasswd();
	                      String rmanCmd = prop.getProperty("oracleHome") + 
	                    		  "\\bin\\rman.exe target sys/" 
	                    		  + inputPasswd 
	                    		  + "@" 
	                    		  + prop.getProperty("oracleSid") 
	                    		  +" cmdfile=" 
	                    		  + basedir 
	                    		  + "\\scripts\\hot_backup.rman '" 
	                    		  + prop.getProperty("backupTargetLocation") 
	                    		  + "' " 
	                    		  + prop.getProperty("recoveryPointTimeStamp") ;
	                	 String dotLog = basedir + 
	          					"\\scripts\\log\\" +
	          					  "HotBackup.log";
	          			  String command[] = {"cmd", "/c", rmanCmd};
	          			BasTextAreaOutputStream.main(command,dotLog);
	        			System.out.println(rmanCmd);
	                }
	            }
	        };
	        t.start();
	        
			}

	});
	getContentPane().add(hotBackup);

	refreshData = new JButton();
	refreshData.setLocation(45,154);
	refreshData.setSize(120,50);
	refreshData.setText("Refresh Data");
	refreshData.setToolTipText("Refresh the data from a backup. Can be used to refresh a non-prod database from a prod backup");
	refreshData.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	//BasTextAreaOutputStream.main("\\scripts\\refresh.cmd");
        }
    });
	getContentPane().add(refreshData);

	coldBackup = new JButton();
	coldBackup.setLocation(191,96);
	coldBackup.setSize(120,50);
	coldBackup.setText("Cold Backup");
	coldBackup.setToolTipText("Offline backup. Ideal for snapshots");
	coldBackup.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	//BasTextAreaOutputStream.main("\\scripts\\snapshot.cmd");
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
        	//BasTextAreaOutputStream.main("\\scripts\\restore_snapshot.cmd");
        }
    });
	getContentPane().add(restoreDB);	
	
	setTitle("BAS DB Utility");
	setSize(400,287);
	setVisible(true);
	setResizable(true);
}
   
    public static void main( String args[] ) throws IOException
   {
		new BASDBUtility();
   }
}  
