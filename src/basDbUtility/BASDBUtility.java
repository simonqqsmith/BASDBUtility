package basDbUtility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
     
   private void runHotBackup() throws IOException{

		String basedir=System.getProperty("user.dir");
		//Get time stamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");    
		String execDateTime = (sdf.format(System.currentTimeMillis()));
		
		//Read properties file
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
 
       Thread t = new Thread() {
           public void run() {
               String rmanCmd = "set ORACLE_SID=" + prop.getProperty("oracleSid")
               + " && set NLS_DATE_FORMAT=" + prop.getProperty("nlsDateFormat")
                    		 + " && call "
                    		 + prop.getProperty("oracleHome")
                    		 + "\\bin\\rman.exe target / "
                   		  +" cmdfile=" 
                   		  + basedir 
                   		  + "\\scripts\\hot_backup.rman '" 
                   		  + prop.getProperty("backupTargetLocation") 
                   		  + "' " ;
               	 String dotLog = basedir + 
         					"\\scripts\\log\\" +
         					  "HotBackup_" + execDateTime + ".log";
         			  String command[] = {"cmd", "/c", rmanCmd};
         			BasTextAreaOutputStream.main(command,dotLog);
           }
       };
       t.start();
   }
   
    
   private void runRefreshDB() throws IOException{

		String basedir=System.getProperty("user.dir");
		//Get time stamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");    
		String execDateTime = (sdf.format(System.currentTimeMillis()));
		
		//Read properties file
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
		   
      Thread t = new Thread() {
          public void run() {
                    String rmanCmd = "set ORACLE_SID=" + prop.getProperty("oracleSid")
                    + " && set NLS_DATE_FORMAT=" + prop.getProperty("nlsDateFormat")
                    + " && call "
              		 + prop.getProperty("oracleHome")
              		 + "\\bin\\sqlplus.exe -s /nolog" 
                     		  +" @" 
                     		  + basedir 
                     		  + "\\scripts\\nomountdb.sql '" 
                     		  + prop.getProperty("restorePfile") 
                     		  + "' " 
                     		  + " && call "
           		 + prop.getProperty("oracleHome")
           		 + "\\bin\\rman.exe AUXILIARY / " 
                  		  +" cmdfile=" 
                  		  + basedir 
                  		  + "\\scripts\\refreshDB.rman '" 
                  		  + prop.getProperty("restorePfile") 
                  		  + "' '" 
                  		  + prop.getProperty("dataFileNewName") 
                  		+ "' " 
                  		+ prop.getProperty("oracleSid")
                  		+ " "
                   		  + prop.getProperty("recoveryPointTimeStamp")
                    	+ " '"
                    	+ prop.getProperty("backupSourceLocation")
                  		+ "' '"
                  		+ prop.getProperty("dbLogFileDest11")
                  		+ "' '"
                  		+ prop.getProperty("dbLogFileDest12")
                  		+ "'";
              	 String dotLog = basedir + 
        					"\\scripts\\log\\" +
        					  "Refresh_" + execDateTime + ".log";
        			  String command[] = {"cmd", "/c", rmanCmd};
        			BasTextAreaOutputStream.main(command,dotLog);
          }
      };
      t.start();
  }
   
   private void runColdBackup() throws IOException{

		String basedir=System.getProperty("user.dir");
		//Get time stamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");    
		String execDateTime = (sdf.format(System.currentTimeMillis()));
		
		//Read properties file
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
		   
       Thread t = new Thread() {
           public void run() {
                     String rmanCmd = "set ORACLE_SID=" + prop.getProperty("oracleSid")
                     + " && set NLS_DATE_FORMAT=" + prop.getProperty("nlsDateFormat")
            		 + " && call "
            		 + prop.getProperty("oracleHome")
            		 + "\\bin\\rman.exe target / " 
                   		  +" cmdfile=" 
                   		  + basedir 
                   		  + "\\scripts\\coldbackup.rman '" 
                   		  + prop.getProperty("backupTargetLocation") 
                   		  + "' " 
                   		  + execDateTime ;
               	 String dotLog = basedir + 
         					"\\scripts\\log\\" +
         					  "ColdBackup_" + execDateTime + ".log";
         			  String command[] = {"cmd", "/c", rmanCmd};
         			BasTextAreaOutputStream.main(command,dotLog);
           }
       };
       t.start();
   }
   
   private void runRestoreDB() throws IOException{

		String basedir=System.getProperty("user.dir");
		//Get time stamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");    
		String execDateTime = (sdf.format(System.currentTimeMillis()));
		
		//Read properties file
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
		   
       Thread t = new Thread() {
           public void run() {
        	   String rmanCmd = "set ORACLE_SID=" + prop.getProperty("oracleSid")
               + " && set NLS_DATE_FORMAT=" + prop.getProperty("nlsDateFormat")
      		 + " && call "
      		 + prop.getProperty("oracleHome")
      		 + "\\bin\\rman.exe target / " 
             		  +" cmdfile=" 
             		  + basedir 
             		  + "\\scripts\\RestoreDB.rman '" 
             		  + prop.getProperty("restorePfile") 
             		  + "' " 
             		  + "\\scripts\\RestoreDB.rman '" 
             		  + prop.getProperty("restoreCfile") 
             		  + "' " 
             		  + "\\scripts\\RestoreDB.rman '" 
             		  + prop.getProperty("backupSourceLocation") 
             		  + "'" ;
               	 String dotLog = basedir + 
         					"\\scripts\\log\\" +
         					  "Restore_" + execDateTime + ".log";
         			  String command[] = {"cmd", "/c", rmanCmd};
         			BasTextAreaOutputStream.main(command,dotLog);
           }
       };
       t.start();
   }
     
   public BASDBUtility() throws IOException
   {
     getContentPane().setLayout(null);
     setupGUI();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   void setupGUI() throws IOException
   {  
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
				try {
					runHotBackup();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	 
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
			try {
				runRefreshDB();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
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
			try {
				runColdBackup();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
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
			try {
				runRestoreDB();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
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
   
    public static void main( String args[] ) throws IOException
   {
		new BASDBUtility();
   }
}  
