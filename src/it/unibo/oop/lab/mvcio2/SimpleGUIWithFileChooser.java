package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    private static final String TITLE = "SIMPLEGUI WITH FILECHOOSER";
    private final JFrame frame = new JFrame(TITLE);
    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    public SimpleGUIWithFileChooser(final Controller controller) {
        final JPanel canvas1 = new JPanel();
        final JPanel canvas = new JPanel();
        final JTextArea textArea = new JTextArea();
        final JButton btnSave = new JButton("save");
        final JButton btnBrowse = new JButton("browse...");
        final JTextField textField = new JTextField(controller.getDirFile()); //default
        textField.setEditable(false);
        canvas1.setLayout(new BorderLayout());
        canvas.setLayout(new BorderLayout());
        canvas1.add(textField, BorderLayout.CENTER);
        canvas1.add(btnBrowse, BorderLayout.LINE_END);
        canvas.add(canvas1, BorderLayout.NORTH);
        canvas.add(textArea, BorderLayout.CENTER);
        canvas.add(btnSave, BorderLayout.SOUTH);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                try {
                    controller.writeOnFile(textArea.getText());
                    JOptionPane.showMessageDialog(frame, "salvato");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        });
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
               final JFileChooser fileChooser = new JFileChooser();
               final int result = fileChooser.showSaveDialog(canvas);
               if (result == JFileChooser.APPROVE_OPTION) {
                   controller.setCurrentFile(fileChooser.getSelectedFile());
                   textField.setText(controller.getDirFile());
               }
               else {
                   if (result != JFileChooser.CANCEL_OPTION) {
                       JOptionPane.showMessageDialog(frame, result, "Error", JOptionPane.ERROR_MESSAGE);
                   } 
               }
            } 
        });
        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         * 
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(final String... args) {
        new SimpleGUIWithFileChooser(new Controller()); 
    }
}
