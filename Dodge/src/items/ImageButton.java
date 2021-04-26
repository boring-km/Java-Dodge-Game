package items;

import lombok.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Builder
public class ImageButton extends JButton {

    private ImageIcon normalStartIcon;
    private ImageIcon rolloverStartIcon;
    private ImageIcon pressedStartIcon;
    private boolean borderPainted;
    private Rectangle bounds;
    private ActionListener listener;

    public ImageButton make() {
        super.setIcon(normalStartIcon);
        super.setRolloverIcon(rolloverStartIcon);
        super.setPressedIcon(pressedStartIcon);
        super.setBorderPainted(borderPainted);
        super.setBounds(bounds);
        super.addActionListener(listener);
        return this;
    }
}
