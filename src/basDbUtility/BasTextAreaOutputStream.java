package basDbUtility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

@SuppressWarnings("serial")
public class BasTextAreaOutputStream extends JPanel {

   private static JTextArea textArea = new JTextArea();
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(
         textArea, "Output");

   public BasTextAreaOutputStream(String[] osCmd, String dotLog) {

      System.setOut(new PrintStream(taOutputStream));

      String command[]=osCmd;
      System.out.println("Running.....");
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
      System.out.println("Process complete. The output has been logged at " + dotLog);
   }
 
   static void createAndShowGui() {
      JFrame frame = new JFrame("BAS Technologies");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      JLabel jLabel1 = new JLabel("There may be a delay whilst the program runs, please wait...");
      textArea.setColumns(40);
      textArea.setLineWrap(true);
      textArea.setRows(20);
      textArea.setWrapStyleWord(true);
      JScrollPane jScrollPane1 = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    		  											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      GroupLayout layout = new GroupLayout(frame.getContentPane());
      frame.getContentPane().setLayout(layout);
      //Create a parallel group for the horizontal axis
      ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
      //Create a sequential and a parallel groups
      SequentialGroup h1 = layout.createSequentialGroup();
      ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
      //Add a scroll panel and a label to the parallel group h2
      h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE);
      h2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE);
      //Add a container gap to the sequential group h1
      h1.addContainerGap();
          // Add the group h2 to the group h1
      h1.addGroup(h2);
          h1.addContainerGap();
          //Add the group h1 to hGroup
      hGroup.addGroup(Alignment.TRAILING,h1);
          //Create the horizontal group
      layout.setHorizontalGroup(hGroup);
           
      //Create a parallel group for the vertical axis
          ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
          //Create a sequential group
      SequentialGroup v1 = layout.createSequentialGroup();
          //Add a container gap to the sequential group v1
      v1.addContainerGap();
          //Add a label to the sequential group v1
      v1.addComponent(jLabel1);
          v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
          //Add scroll panel to the sequential group v1
      v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE);
          v1.addContainerGap();
          //Add the group v1 to vGroup
      vGroup.addGroup(v1);
          //Create the vertical group
      layout.setVerticalGroup(vGroup);

      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] osCmd, String dotLog) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
                     }
      });
      new BasTextAreaOutputStream(osCmd,dotLog);
   }
   
}
