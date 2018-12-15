package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.*;
import zaidimas.Forma;
import zaidimas.Zaidimas;

public class Langas extends JFrame
    implements DocumentListener {

    private JTextField entry;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JLabel status;
    private JTextArea textArea;

    final static Color HILIT_COLOR = Color.LIGHT_GRAY;
    final static Color ERROR_COLOR = Color.PINK;
    final static Color SUCCESS_COLOR = Color.GREEN;
    final static Color EMPTY_COLOR = Color.WHITE;
    final static String CANCEL_ACTION = "cancel";

    final Color entryBg; //balta spalva
    final Highlighter hilit;
    final Highlighter.HighlightPainter painter;
    
    final Forma forma;
    

    

    public Langas() {
        initComponents();
        
        // sukuriam formos objektą ir inicijuojam konstruktoriu
        forma = new Forma();
        
        //pradinė žinutė
        message(forma.startMessage());
        
        //nustatom kokį tekstą reikės suvesti
        textArea.setText(forma.getRandomLine());
        
        //nusistatom paryškinimus
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
        textArea.setHighlighter(hilit);

        entryBg = entry.getBackground();
        
        //klausomes ir nusistatom ką darys esc mygtukas
        entry.getDocument().addDocumentListener(this); //fiksuoja klav. paspaudima
        InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = entry.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new CancelAction());
    }

    private void initComponents() {
        //nusistatom pagrindinius lango kintamuosius
        entry = new JTextField();
        textArea = new JTextArea();
        status = new JLabel();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Typeracer");

        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        jScrollPane1 = new JScrollPane(textArea);

        jLabel1.setText("Įveskite tekstą:");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        //Create a parallel group for the horizontal axis
        ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

        //Create a sequential and a parallel groups
        SequentialGroup h1 = layout.createSequentialGroup();
        ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

        //Add a container gap to the sequential group h1
        h1.addContainerGap();

        //Add a scroll pane and a label to the parallel group h2
        h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
        h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

        //Create a sequential group h3
        SequentialGroup h3 = layout.createSequentialGroup();
        h3.addComponent(jLabel1);
        h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        h3.addComponent(entry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

        //Add the group h3 to the group h2
        h2.addGroup(h3);
        //Add the group h2 to the group h1
        h1.addGroup(h2);

        h1.addContainerGap();

        //Add the group h1 to the hGroup
        hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
        //Create the horizontal group
        layout.setHorizontalGroup(hGroup);

        //Create a parallel group for the vertical axis
        ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential group v1
        SequentialGroup v1 = layout.createSequentialGroup();
        //Add a container gap to the sequential group v1
        v1.addContainerGap();
        //Create a parallel group v2
        ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        v2.addComponent(jLabel1);
        v2.addComponent(entry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        //Add the group v2 tp the group v1
        v1.addGroup(v2);
        v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
        v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        v1.addComponent(status);
        v1.addContainerGap();

        //Add the group v1 to the group vGroup
        vGroup.addGroup(v1);
        //Create the vertical group
        layout.setVerticalGroup(vGroup);
        pack();
    }
    
    // palygina kiekvieną kart įrašius naują raidę, ar ištrynus senąją
    public void compare() {
        // nuima paryškinimą
        hilit.removeAllHighlights();
        
        //atnaujina kintamuosius
        forma.refreshValues(textArea, entry);

        //ar gerai įrašė simbolį
        if (forma.textsAreEqual()) {
            try {
                //paryškina simbolius
                hilit.addHighlight(0, forma.getEntryStringLength(), painter);

                //nupaišo background
                paint();
                //patikrina ar reikia ir jei taip tai pradeda matuoti laiką
                forma.timeStart();
                
                //tikrina ar baigė rašyti
                if (forma.isFinnished()) {
                    message(forma.finishGame());
                }

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            entry.setBackground(ERROR_COLOR);
        }
    }

    public void insertUpdate(DocumentEvent ev) {
        compare();
    }

    public void removeUpdate(DocumentEvent ev) {
        compare();
    }

    public void changedUpdate(DocumentEvent ev) {
    }

    class CancelAction extends AbstractAction {
        //viskas nunulinama paspaudus esc mygtuką
        public void actionPerformed(ActionEvent ev) {
            hilit.removeAllHighlights();
            entry.setText("");
            entry.setBackground(entryBg);
            forma.setStart(false);
            message("Geriausio žaidėjo (" + Zaidimas.getWinningPlayer() + ") rezultatas: " + Zaidimas.getScore() + " sek.");
        }
    }
    
    private void paint() {
        if (forma.getEntryStringLength() == 0) {
            entry.setBackground(EMPTY_COLOR);
        } else {
            entry.setBackground(SUCCESS_COLOR);
        }
    }

    void message(String msg) {
        status.setText(msg);
    }
    
}
