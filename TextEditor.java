import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class TextEditor extends JFrame implements ActionListener {

    //UI setting
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu searchMenu;
    JMenu formatMenu;

    //File items
    JMenuItem newItem;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem saveAsItem;
    JMenuItem printItem;
    JMenuItem exitItem;
    KeyStroke keyStrokeNew;
    KeyStroke keyStrokeOpen;
    KeyStroke keyStrokeSave;
    KeyStroke keyStrokePrint;
    //Edit items
    JMenuItem undoItem;
    JMenuItem copyItem;
    JMenuItem pasteItem;
    JMenuItem findItem;
    JMenuItem replaceItem;
    JMenuItem dateHourItem;

    KeyStroke keyStrokeUndo;
    KeyStroke keyStrokeCopy;
    KeyStroke keyStrokePaste;
    KeyStroke keyStrokeFind;
    KeyStroke keyStrokeReplace;
    KeyStroke keyStrokeDateHour;

    //Font item
    JMenuItem fontItem;

    //Variables used for edit
    String undoText;
    String markedText;
    File file;
    boolean fileCreated;

    TextEditor(){

        ImageIcon imageIcon = new ImageIcon("src/Media/img_icon.png");

        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("TextEditor");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));


        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(480,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // ------------ MENUBAR -----------------

            menuBar = new JMenuBar();

        //------------ FILES -----------------
            fileMenu = new JMenu("File");
            newItem = new JMenuItem("New");
            openItem = new JMenuItem("Open");
            saveItem = new JMenuItem("Save");
            saveAsItem = new JMenuItem("Save As...");
            printItem = new JMenuItem("Print");
            exitItem = new JMenuItem("Exit");

            keyStrokeNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
            keyStrokeOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
            keyStrokeSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
            keyStrokePrint = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);

            newItem.setAccelerator(keyStrokeNew);
            openItem.setAccelerator(keyStrokeOpen);
            saveItem.setAccelerator(keyStrokeSave);
            printItem.setAccelerator(keyStrokePrint);


            newItem.addActionListener(this);
            openItem.addActionListener(this);
            saveItem.addActionListener(this);
            saveAsItem.addActionListener(this);
            printItem.addActionListener(this);
            exitItem.addActionListener(this);

            fileMenu.add(newItem);
            fileMenu.add(openItem);
            fileMenu.add(saveItem);
            fileMenu.add(saveAsItem);
            fileMenu.add(printItem);
            fileMenu.add(exitItem);

            //------------ EDIT -----------------

            editMenu = new JMenu("Edit");
            undoItem = new JMenuItem("Undo");
            copyItem = new JMenuItem("Copy");
            pasteItem = new JMenuItem("Paste");
            dateHourItem = new JMenuItem("Date/ hour");

            keyStrokeUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
            keyStrokeCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
            keyStrokePaste = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
            keyStrokeDateHour = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);

            undoItem.setAccelerator(keyStrokeUndo);
            copyItem.setAccelerator(keyStrokeCopy);
            pasteItem.setAccelerator(keyStrokePaste);
            dateHourItem.setAccelerator(keyStrokeDateHour);


            editMenu.addActionListener(this);
            undoItem.addActionListener(this);
            copyItem.addActionListener(this);
            pasteItem.addActionListener(this);
            dateHourItem.addActionListener(this);

            editMenu.add(undoItem);
            editMenu.add(copyItem);
            editMenu.add(pasteItem);
            editMenu.add(dateHourItem);

            formatMenu = new JMenu("Format");
            fontItem = new JMenuItem("Font...");

            fontItem.addActionListener(this);

            formatMenu.add(fontItem);

            //------------ SEARCH AND REPLACE -----------------

            searchMenu = new JMenu("Search");
            findItem = new JMenuItem("Find");
            replaceItem = new JMenuItem("Replace");

            findItem.addActionListener(this);
            replaceItem.addActionListener(this);

            keyStrokeFind = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
            keyStrokeReplace = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);

            findItem.setAccelerator(keyStrokeFind);
            replaceItem.setAccelerator(keyStrokeReplace);

            searchMenu.add(findItem);
            searchMenu.add(replaceItem);


        // ------------ MENU BAR FINAL -----------------

            menuBar.add(fileMenu);
            menuBar.add(editMenu);
            menuBar.add(searchMenu);
            menuBar.add(formatMenu);



        // ------------ /MENUBAR -----------------


        this.setJMenuBar(menuBar);
        this.add(scrollPane);
        this.setVisible(true);


        //------------ DRAG AND DROP FUNCTION -----------------
        enableDragAndDrop();

    }

    //Methods for on click ussability
    public void newItem(){
        new TextEditor().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void openItem(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            Scanner fileIn = null;

            try {
                fileIn = new Scanner(file);
                if(file.isFile()){
                    while (fileIn.hasNextLine()){
                        String line = fileIn.nextLine() + "\n";
                        textArea.append(line);
                    }
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                fileIn.close();
            }
        }
    }

    public void saveItem(){
        PrintWriter fileOut = null;

        if(fileCreated) {


            try {
                fileOut = new PrintWriter(file);
                fileOut.println(textArea.getText());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } finally {
                fileOut.close();
            }
        }
        else{
            saveAsItem();
        }
    }

    public void saveAsItem(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){

            PrintWriter fileOut = null;

            file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                fileOut = new PrintWriter(file);
                fileOut.println(textArea.getText());
                fileCreated = true;
            }
            catch (FileNotFoundException ex){
                throw new RuntimeException(ex);
            }
            finally {
                fileOut.close();
            }

        }
    }

    public void printer(){
        try {
            textArea.print();
        }
        catch (PrinterException ex){
            throw new RuntimeException(ex);
        }
    }

    public void undoItem(){
        undoText = textArea.getText();
        StringBuffer sb= new StringBuffer(undoText);
        sb.deleteCharAt(sb.length()-1);
        textArea.setText(sb.toString());
    }
    public void copyItem(){
        markedText = textArea.getSelectedText();
    }

    public void dateHourItem(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        textArea.append(formatter.format(date));
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        //------------ FILES ACTIONS  -----------------
        if(e.getSource() == newItem){
            newItem();
        }
        if(e.getSource() == openItem){
           openItem();
        }

        if(e.getSource() == saveItem){
            saveItem();
        }

        if(e.getSource() == saveAsItem){
            saveItem();

        }

        if(e.getSource() == printItem){
          printer();
        }

        if(e.getSource() == exitItem){
            System.exit(0);
        }

        //------------ EDIT ACTIONS  -----------------

        if(e.getSource() == undoItem){
            undoItem();
        }
        if(e.getSource() == copyItem)
        {
            copyItem();
        }
        if(e.getSource() == pasteItem){
            textArea.append(markedText);
        }
        if(e.getSource() == dateHourItem){
            dateHourItem();
        }
        //------------ SEARCH AND REPLACE ACTIONS  -----------------
        if(e.getSource() == findItem){
            new Find(textArea);
        }
        if(e.getSource() == replaceItem){
            new Replace(textArea);
        }
        //------------ FONT ACTIONS  -----------------
        if(e.getSource() == fontItem){
            new FontEditor(textArea);
        }


    }
    private void enableDragAndDrop()
    {
            DropTarget target=new DropTarget(textArea,new DropTargetListener(){
            public void dragEnter(DropTargetDragEvent e)
            {
            }

            public void dragExit(DropTargetEvent e)
            {
            }

            public void dragOver(DropTargetDragEvent e)
            {
            }

            public void dropActionChanged(DropTargetDragEvent e)
            {

            }

            public void drop(DropTargetDropEvent e)
            {
                try
                {

                    e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);


                    java.util.List list=(java.util.List) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    File file=(File)list.get(0);
                    textArea.read(new FileReader(file),null);

                }catch(Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}



