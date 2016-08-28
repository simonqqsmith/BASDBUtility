package basDbUtility;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Scanner;

import javax.swing.*;
//import javax.swing.GroupLayout.ParallelGroup;
//import javax.swing.GroupLayout.SequentialGroup;

@SuppressWarnings("serial")
public class BasTextAreaOutputStream extends JPanel {

   private JTextArea textArea = new JTextArea(15, 30);
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(
         textArea, "Output");

   public BasTextAreaOutputStream(String[] osCmd, String dotLog) {
      setLayout(new BorderLayout());
      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      System.setOut(new PrintStream(taOutputStream));

      String command[]=osCmd;
      try {
    	 Process process = Runtime.getRuntime().exec(command);
 
         Scanner scanner = new Scanner(process.getInputStream());
     	 scanner.useDelimiter("\r\n");
             	
         Writer writer = new BufferedWriter(new OutputStreamWriter(
                 new FileOutputStream(dotLog), "utf-8"));

         while (scanner.hasNext()) {
     		 String nextLine = scanner.next();
     	     System.out.println(nextLine);
     	    writer.write(nextLine + "\r\n");
     	 }
      	writer.close();
      	scanner.close();

     	
      } catch (IOException e1) {
          e1.printStackTrace();
      }
   }
 
   static void createAndShowGui(String[] osCmd, String dotLog) {
      JFrame frame = new JFrame("BAS Technologies");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      //JLabel jLabel1 = new JLabel("Try typing 'spectacular' or 'Swing'...");
      //JTextArea textArea = new JTextArea();
      //GroupLayout layout = new GroupLayout(getContentPane());
      //getContentPane().setLayout(layout);
      //Create a parallel group for the horizontal axis
      //ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
      //Create a sequential and a parallel groups
      //SequentialGroup h1 = layout.createSequentialGroup();
      //ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
      //Add a scroll panel and a label to the parallel group h2
      //h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
      //h2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
      
      JTextArea inittextArea = new JTextArea(5, 30);
      JScrollPane initJScrollPane = new JScrollPane(inittextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
              JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      inittextArea.append("Running process, please wait..... \r\n");

      frame.getContentPane().add(initJScrollPane);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      
      frame.getContentPane().add(new BasTextAreaOutputStream(osCmd,dotLog));
      frame.getContentPane().remove(initJScrollPane);
      frame.getContentPane().invalidate();
      frame.getContentPane().validate();
   }

   public static void main(String[] osCmd, String dotLog) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui(osCmd,dotLog);
         }
      });
   }
}
