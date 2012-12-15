package boy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FileSysPane extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Background Image
	private Image backgroundImage = null;
	
	public FileSysPane() {
		backgroundImage = Toolkit.getDefaultToolkit().getImage(".\\Resource\\background.png");
		MediaTracker tracker = new MediaTracker(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
