import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Replace extends JFrame implements ActionListener {


        JTextArea textArea;
        JTextField textFieldWord;
        JTextField textFieldReplace;
        JButton ReplaceButton;
        KeyStroke keyStrokeEnter;
        int startIndex=0;
        int select_start=-1;
        JLabel textSearch;
        JLabel textReplace;
        int searchWord;

        Replace(JTextArea jtextArea){
            ImageIcon imageIcon = new ImageIcon("src/Media/img_icon.png");
            this.setIconImage(imageIcon.getImage());

            setLayout(null);

            this.textArea = jtextArea;
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setTitle("Word Replacer");
            this.setSize(600, 90);
            this.setLayout(new FlowLayout());
            this.setLocationRelativeTo(null);

            //------------ FUNCTIONAL UI -----------------

            textFieldWord = new JTextField();
            textFieldWord.setPreferredSize(new Dimension(120,40));

            textFieldReplace = new JTextField();
            textFieldReplace.setPreferredSize(new Dimension(120,40));

            textSearch = new JLabel("Word to replace: ");
            textReplace = new JLabel("Replace to word: ");


            ReplaceButton = new JButton("Replace");
            ReplaceButton.addActionListener(this);

            //------------ /FUNCTIONAL UI -----------------


            this.add(textSearch);
            this.add(textFieldWord);
            this.add(textReplace);
            this.add(textFieldReplace);
            this.add(ReplaceButton);
            this.setVisible(true);
        }
        //Method for finding the word to replace
        public void find(){

            searchWord = textArea.getText().indexOf(textFieldWord.getText().toLowerCase());
            if(searchWord == -1)
            {
                startIndex = 0;
                JOptionPane.showMessageDialog(null, "Could not find \"" + textFieldWord.getText() + "\"!");
                return;
            }
            if(searchWord == textArea.getText().indexOf(textFieldWord.getText().toLowerCase()))
            {
                startIndex = 0;
            }
            int select_end = searchWord + textFieldWord.getText().length();

            textArea.select(searchWord, select_end);
        }
        //Replacement method
        public void replace(){

            find();
            textArea.setText(textArea.getText().replaceAll(textFieldWord.getText(), textFieldReplace.getText()));

        }


        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == ReplaceButton){
                replace();
            }
        }
    }


