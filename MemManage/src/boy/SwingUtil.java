package boy;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import com.incors.plaf.alloy.*;
import com.incors.plaf.alloy.themes.glass.*;

public class SwingUtil {
	public static final void setLookAndFeel() {
		try {
			Font font = new Font("JFrame", Font.PLAIN, 12);
			Enumeration<?> keys = UIManager.getLookAndFeelDefaults().keys();

			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
			AlloyLookAndFeel.setProperty("alloy.isLookAndFeelFrameDecoration",
					"true");
			AlloyTheme theme = new GlassTheme();
			LookAndFeel alloyLnF = new AlloyLookAndFeel(theme);
			JFrame.setDefaultLookAndFeelDecorated(true);

			UIManager.setLookAndFeel(alloyLnF);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
}
