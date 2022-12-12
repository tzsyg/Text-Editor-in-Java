import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;


public class Find extends JFrame implements ActionListener {
    JTextArea textArea;
    JTextField textField;
    JButton findButton;
    int startIndex=0;
    int select_start=-1;
    JLabel textSearch;
    int searchWord;
    Highlighter.HighlightPainter myHighlightPainter;

    //Creating class for highlighting founded text
    class  MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{
        public MyHighlightPainter(Color color){
            super(color);
        }


    }

    Find(JTextArea jtextArea){
        ImageIcon imageIcon = new ImageIcon("src/Media/img_icon.png");
        this.setIconImage(imageIcon.getImage());

        setLayout(null);

        this.textArea = jtextArea;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Word Finder");
        this.setSize(400, 90);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        //------------ FUNCTIONAL UI -----------------
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(120,40));

        myHighlightPainter = new MyHighlightPainter(Color.YELLOW);

        textSearch = new JLabel("Find Word: ");

        findButton = new JButton("Find");
        findButton.addActionListener(this);

        findButton.setMnemonic(KeyEvent.VK_ENTER);

        //------------ /FUNCTIONAL UI -----------------
        this.add(textSearch);
        this.add(textField);
        this.add(findButton);
        this.setVisible(true);



        //On closing the Find window the highlighted text is removed
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               removeHighlight(textArea);
            }
        });
    }

    //Method for highlighting the text
    public void highlight(JTextArea textArea, String pattern){
        try {
            Highlighter highlightW = textArea.getHighlighter();
            String text = textArea.getText(0, textArea.getDocument().getLength());
            int pos = 0;

            while((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0){
                highlightW.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }

        }
        catch (Exception ex){

        }
    }
    //Method for removing highlighted text
    public void removeHighlight(JTextArea textArea){
        Highlighter highlightW = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlightW.getHighlights();

        for(int i = 0; i < highlights.length; i++){
            if(highlights[i].getPainter() instanceof MyHighlightPainter){
                highlightW.removeHighlight(highlights[i]);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == findButton){
            highlight(textArea, textField.getText());

        }
    }

}
