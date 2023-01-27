import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextEditor extends JFrame implements ActionListener{

    JTextArea textArea;

    JScrollPane scene;

    JLabel fontlabel;

    JSpinner font_size_spinner;
    JComboBox fontstylebox;

    JMenuBar menubar;
    JMenu file;
    JMenuItem save;
    JMenuItem exit;

    TextEditor() {
        this.setTitle("Text Editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 25));

        scene = new JScrollPane(textArea);
        scene.setPreferredSize(new Dimension(400, 400));
        scene.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        fontlabel = new JLabel("Font :");

        font_size_spinner = new JSpinner();
        font_size_spinner.setPreferredSize(new Dimension(40, 30));
        font_size_spinner.setValue(20);
        font_size_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) font_size_spinner.getValue()));
            }
        });

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontstylebox = new JComboBox(fonts);
        fontstylebox.setSelectedItem("Arial");

        fontstylebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font((String) fontstylebox.getSelectedItem(), Font.PLAIN, (int) font_size_spinner.getValue()));
            }
        });

        menubar = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        save.addActionListener(this);
        exit.addActionListener(this);


        file.add(save);
        file.add(exit);

        menubar.add(file);

        this.setJMenuBar(menubar);
        this.add(fontlabel);
        this.add(font_size_spinner);
        this.add(fontstylebox);
        this.add(scene);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exit) {
            System.exit(99);
        }

        if(e.getSource() == save) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response_of_user = fileChooser.showSaveDialog(null);

            if(response_of_user == JFileChooser.CANCEL_OPTION) {
                ;
            }
            else {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    PrintWriter writing_content = new PrintWriter(file);
                    writing_content.println(textArea.getText());
                    writing_content.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

