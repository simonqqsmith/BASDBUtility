package basDbUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
 
public class BasGetPassword extends JPanel
                          implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static String OK = "ok";
    private static String HELP = "help";
 
    private JFrame controllingFrame;
    private JPasswordField passwordField;
    static String inputPasswd;
    static Object lock = new Object();
 
    public BasGetPassword(JFrame f) {
        controllingFrame = f;
 
        passwordField = new JPasswordField(10);
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(this);
 
        JLabel label = new JLabel("Enter the sys password: ");
        label.setLabelFor(passwordField);
 
        JComponent buttonPane = createButtonPanel();
 
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);
 
        add(textPane);
        add(buttonPane);
    }
 
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("Help");
 
        okButton.setActionCommand(OK);
        helpButton.setActionCommand(HELP);
        okButton.addActionListener(this);
        helpButton.addActionListener(this);
 
        p.add(okButton);
        p.add(helpButton);
 
        return p;
    }
 public String getPasswd(){
	 return inputPasswd;
 }
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
 
        if (OK.equals(cmd)) { 
            char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
                synchronized (lock) {
                	inputPasswd = new String(input);
                	SwingUtilities.getWindowAncestor((JComponent) e.getSource()).dispose();
                    lock.notify();
                }
            	

            } else {
                JOptionPane.showMessageDialog(controllingFrame,
                    "Invalid password. Try again.",
                    "Error Message",
                    JOptionPane.ERROR_MESSAGE);
            }
 
            Arrays.fill(input, '0');
 
            passwordField.selectAll();
            resetFocus();
        } else { 
            JOptionPane.showMessageDialog(controllingFrame,
                "Enter the Oracle database sys user password\n"
              + "for the target database.");
        }
    }
 
    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;

        // Simple check for something - validation can be added later
        if (input.length > 3) {
        	isCorrect = true;
        } else {
        	isCorrect = false;
        }
         
        return isCorrect;
    }
 
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }
 
   private static void createAndShowGUI() {
	   JFrame pwframe = new JFrame("BAS Technologies");
        pwframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        final BasGetPassword newContentPane = new BasGetPassword(pwframe);
        newContentPane.setOpaque(true);
        pwframe.setContentPane(newContentPane);
 
        pwframe.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                newContentPane.resetFocus();
            }
        });

        pwframe.pack();
        pwframe.setVisible(true);

    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
            }
        });

     }
}