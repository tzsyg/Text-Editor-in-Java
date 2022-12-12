import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontEditor extends JFrame implements ActionListener{


    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColourButton;
    JComboBox fontBox;
    JTextArea textarea;



    FontEditor(JTextArea jtextArea) {

        ImageIcon imageIcon = new ImageIcon("src/Media/img_icon.png");

        this.setIconImage(imageIcon.getImage());
        this.textarea = jtextArea;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("FontEditor");
        this.setSize(250, 110);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        //------------ FUNCTIONAL UI -----------------
        fontLabel = new JLabel("Font: ");
        //Setting spinner
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textarea.setFont(new Font(textarea.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue()));
            }
        });

        fontColourButton = new JButton("Color");
        fontColourButton.addActionListener(this);

        //Setting combobox to contain all available java fonts
        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        //------------ /FUNCTIONAL UI -----------------

        this.add(fontSizeSpinner);
        this.add(fontColourButton);
        this.add(fontBox);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Method for colour change
        if(e.getSource() == fontColourButton){
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null, "Choose a color", Color.BLACK);
            textarea.setForeground(color);

        }
        //Method for font change
        if(e.getSource() == fontBox){
            textarea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textarea.getFont().getSize()));
        }

    }
}

