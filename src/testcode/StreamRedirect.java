package testcode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;



// text pane version
public class StreamRedirect {
	JTextPane textPane;
	JTextArea textArea;
	
	private void updateTextPane(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      Document doc = textPane.getDocument();
		      try {
		        doc.insertString(doc.getLength(), text, null);
		      } catch (BadLocationException e) {
		        throw new RuntimeException(e);
		      }
		      textPane.setCaretPosition(doc.getLength() - 1);
		    }
		  });
		}
		 
		private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(final int b) throws IOException {
		      updateTextPane(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextPane(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}

//textarea version
private void updateTextArea(final String text) {
	  SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
	      textArea.append(text);
	    }
	  });
	}
	 
	private void redirectSystemStreams2() {
	  OutputStream out = new OutputStream() {
	    @Override
	    public void write(int b) throws IOException {
	      updateTextArea(String.valueOf((char) b));
	    }
	 
	    @Override
	    public void write(byte[] b, int off, int len) throws IOException {
	      updateTextArea(new String(b, off, len));
	    }
	 
	    @Override
	    public void write(byte[] b) throws IOException {
	      write(b, 0, b.length);
	    }
	  };
	 
	  System.setOut(new PrintStream(out, true));
	  System.setErr(new PrintStream(out, true));
	}
}