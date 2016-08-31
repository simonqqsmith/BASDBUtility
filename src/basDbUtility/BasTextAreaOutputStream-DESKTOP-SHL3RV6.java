package basDbUtility;

import java.awt.BorderLayout;
//import java.io.BufferedWriter;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.IOException;
//import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
public class basTextAreaOutputStream extends JPanel {

   private JTextArea textArea = new JTextArea(15, 30);
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(
         textArea, "Output");

   public basTextAreaOutputStream(String osCmd) {
      setLayout(new BorderLayout());
      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      System.setOut(new PrintStream(taOutputStream));

      String workingdir=System.getProperty("user.dir");
      String osExe="cmd.exe /C ";
      //String osCmd="\\scripts\\hot_backup.cmd";
      String command=osExe + workingdir + osCmd;
      try {
     	 Process process = Runtime.getRuntime().exec(command);
     	 
         //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
         //writer.write("\r\n");
         //writer.write("y");
         //writer.write("\r\n");
         //writer.close();
     	 
         Scanner scanner = new Scanner(process.getInputStream());
     	 scanner.useDelimiter("\r\n");
     	       	 
     	 while (scanner.hasNext()) {
     	     System.out.println(scanner.next());
     	 }
     	 scanner.close();
      } catch (IOException e1) {
          e1.printStackTrace();
      }
   }

   static void createAndShowGui(String osCmd) {
      JFrame frame = new JFrame("Test");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new basTextAreaOutputStream(osCmd));
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String osCmd) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui(osCmd);
         }
      });
   }
}
