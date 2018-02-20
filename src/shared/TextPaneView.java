package shared;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Diese Klasse ist dafür verantwortlich, wie ein Zelleninhalt gerendert wird.
 * Objekte davon werden weitergeleitet um dann von der Tabelle gerendert zu 
 * werden.
 * 
 * @author sobdaro
 */
@SuppressWarnings("serial")
public class TextPaneView extends JTextPane
{

    private int rowheight;

    //Konstruktor für leere Zellen
    public TextPaneView()
    {

    }

    //Konstruktor für Spalte Stunden
    public TextPaneView(int number)
    {
        StyledDocument document = new DefaultStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributes, "Purisa");
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 24);
        StyleConstants.setForeground(attributes, Color.darkGray);
        try
        {
            document.insertString(document.getLength(), number + " ", attributes);
        } catch (BadLocationException bd)
        {
            System.out.println(bd.getMessage());
        }
        setDocument(document);
    }

    // Konstrukor für ausgefüllte Zellen
    public TextPaneView(String manuelText, Color color, int height)
    {
        this.rowheight = height;
        StyledDocument document = new DefaultStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        configFont_background(attributes, color);
        try
        {
            document.insertString(0, manuelText, attributes);
        } catch (BadLocationException bd)
        {
            System.out.println(bd.getMessage());
        }
        setDocument(document);
    }
    //Konstruktor für Vertretungsanzeige 

    public TextPaneView(String standout, String subject,
            String standin, boolean plus, int height)
    {
        this.rowheight = height;
        StyledDocument document = new DefaultStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        configFont_failure(attributes);
        String separator = "";
        try
        {
            separator = (standout.equals("") || standout.equals(null)) ? "" : " : ";
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        try
        {
            document.insertString(document.getLength(),
                    standout, attributes);
            configFont_separator(attributes);
            document.insertString(document.getLength(), separator, attributes);
            configFont_subject(attributes);
            document.insertString(document.getLength(), subject, attributes);
            configFont_substitute(attributes);
            document.insertString(document.getLength(), "\n", attributes);
            document.insertString(document.getLength(),
                    (standin != null ? standin : "") + "  ", attributes);
            configFont_plus(attributes);
            document.insertString(document.getLength(), (plus ? "+" : ""), attributes);
        } catch (BadLocationException bd)
        {
            System.out.println(bd.getMessage());
        }

        setDocument(document);
    }

    public void configFont_failure(SimpleAttributeSet attributes)
    {
        StyleConstants.setFontFamily(attributes, "Droid Sans");
        StyleConstants.setBold(attributes, true);
        StyleConstants.setForeground(attributes, Color.darkGray);
        StyleConstants.setFontSize(attributes, rowheight / 3);
    }

    public void configFont_separator(SimpleAttributeSet attributes)
    {
        StyleConstants.setBold(attributes, true);
        StyleConstants.setForeground(attributes, Color.black);
        StyleConstants.setFontFamily(attributes, "Mono");
    }

    public void configFont_subject(SimpleAttributeSet attributes)
    {
        StyleConstants.setFontSize(attributes, rowheight / 3);
        configFont_failure(attributes);
        StyleConstants.setForeground(attributes, Color.GREEN);
        StyleConstants.setFontFamily(attributes, "Sans");
    }

    public void configFont_substitute(SimpleAttributeSet attributes)
    {
        StyleConstants.setFontSize(attributes, rowheight / 3 + 2);
        StyleConstants.setForeground(attributes, Color.BLUE);
        StyleConstants.setFontFamily(attributes, "Century Schoolbook L");
    }

    public void configFont_plus(SimpleAttributeSet attributes)
    {
        StyleConstants.setFontSize(attributes, rowheight / 3 + 2);
        StyleConstants.setBold(attributes, true);
        StyleConstants.setForeground(attributes, Color.pink);
    }

    public void configFont_background(SimpleAttributeSet attributes, Color color)
    {
        StyleConstants.setFontSize(attributes, rowheight / 3);
        StyleConstants.setBackground(attributes, color);
    }
}
