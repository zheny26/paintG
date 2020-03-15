package component;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class IntInputDialog extends JDialog {

    private int value;
    private JTextField input;

    public IntInputDialog(Frame owner) {

        super(owner, "Enter number of corners.");

        input = new JTextField();
        add(input);

        JButton okButton = getOkButton();
        add(okButton, BorderLayout.SOUTH);

        Point location = owner.getLocation();
        location.translate(50, 50);
        setLocation(location);
        getRootPane().setDefaultButton(okButton);
        setSize(250, 100);
        setModal(true);
        setResizable(false);
    }

    private JButton getOkButton() {

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                value = Integer.parseInt(input.getText());
                setVisible(false);
            } catch (NumberFormatException exc) {
                input.setText("Enter number > 3.");
            }
        });
        return okButton;
    }

    public int getValue() {

        return value;
    }

    public void setValue(int value) {

        this.value = value;
    }
}
