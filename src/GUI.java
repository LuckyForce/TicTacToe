import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* Eigentlich wollte ich diese Klasse nur in Form einer class-Datei (und damit ohne Quelltext)
 * dem BlueJ-Ordner beilegen. Damit koennte sie verwendet, aber nicht im Quelltext angezeigt oder
 * veraendert werden. Leider hat das auf einigen Schulrechnern nicht funktioniert.
 * Bitte hier keine Aenderungen vornehmen. Das noetige Wissen dazu folgt erst im 2. Jahrgang!
 */
public class GUI extends JFrame
{
    private JButton[][] buttons;       // "Darstellungseigenschaften" der Felder (JButtons)
    private char[][] settings;         // eine Referenz auf das uebergebene Array
    private Game spiel;                // eine Referenz auf die Spiele-Instanz fuer den "Rueckruf"
    private MouseListener mListener;
    private MouseWheelListener mwListener;
    /** Erzeugt die graphische Oberflaeche fuer das einfache Spiel. Sie ist aus simplen "JButtons" 
     * aufgebaut, die in einem sogenannten "GridLayout" (Gitter) angeordnet sind. Die Anzahl dieser
     * Felder richtet sich nach der Anzahl der Felder des uebergegeben 2-dimensionalen Arrays.
     * 
     * @param titel Text der in der Titelleiste des Fensters angezeigt wird.
     * @param gesetzt 2-dimensinonales Array von char Feldern. Grundsaetzlich kann ein char-Feld
     * jeden beliebigen Buchstaben enthalten, fuer das Spiel sollten aber bevorzugt die Grossbuchstaben
     * 'X', 'O' oder das Leerzeichen ' ' verwendet werden (die Felder werden dann hier automatisch gruen,
     * rot oder grau eingefaerbt). Alle anderen Buchstaben werden ebenfalls nur grau hinterlegt.
     * @param spiel Eine Referenz auf die Spielklasse Game um dort die Methoden mausKlick(...) aufzurufen.
     * (wird diese Parameter null gesetzt, dann erfolgt der Aufruf dieser Methode nicht).
     */
    public GUI(String titel, char[][] gesetzt, Game spiel) 
    {
        super(titel);
        this.spiel = spiel;

        if (gesetzt != null && gesetzt.length > 0 && gesetzt[0].length > 0)
        {
            settings = gesetzt;
            buttons = new JButton[settings.length][settings[0].length]; 
            // -> alle Zeilen haben soviele Elemente wie die erste Zeile!
            init();
        }
        else
            System.err.println("Kein gueltiges Array uebergeben!");
    }

    /** Aubau und Initialisierung der grafischen Oberflaeche */
    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(settings.length, settings[0].length));
        if (spiel != null)
        {
            mListener = new MyMouseListener();
            mwListener = new MyMouseWheelListener();
        }

        JButton actButton = null;
        for (int row=0; row<settings.length; row++)
            for (int col=0; col<settings[0].length; col++) 
            //(alle Zeilen haben soviele Elemente wie die erste Zeile!)
            {
                actButton = new JButton();
                actButton.setEnabled(false);
                if (mListener != null)
                {
                    actButton.addMouseListener(mListener);
                    actButton.addMouseWheelListener(mwListener);
                }
                actButton.setBackground(Color.LIGHT_GRAY); 
                buttons[row][col] = actButton;

                add(actButton);   
            }

        setBounds(20, 20, settings[0].length*70, settings.length*70);     
        aktualisiereAnzeige();
        setResizable(false);
        setVisible(true);
        Spielanleitung();
    }

    public void Spielanleitung()
    {
        popup("Spielanleitung", "Spieler X und O. 3 in einer Reihe=Gewonnen.\nX,Linke Maustaste, O,Rechte Maustaste.\nDruecke Mausrad um diesen Text nochmal anzuzeigen.\nUm das Spiel neuzustarten drehe das Mausrad.\nUm den Spielmodi zu wechseln drehe das Mausrad", false);
    }

    /** Aktualisiert die graphische Darstellung. D.h. alle im 2-dimensionalen char-Array 
     * durchgefuehrten Aenderungen werden zur Anzeige gebracht.
     */
    public void aktualisiereAnzeige()
    {
        JButton actButton = null;
        for (int row=0; row<settings.length; row++)
            for (int col=0; col<settings[0].length; col++)
            {
                actButton = buttons[row][col];
                actButton.setText(""+settings[row][col]);

                if (settings[row][col] == 'X') 
                    actButton.setBackground(Color.GREEN);
                else if (settings[row][col] == 'O')
                    actButton.setBackground(Color.RED);
                else 
                    actButton.setBackground(Color.LIGHT_GRAY);   
            }
    }

    /** Wird aufgerufen wenn eine Aktion mit der Maus stattgefunden hat, stellt fest
     * Ueber welchen Button die Aktion stattfand, welche Maustaste dabei gedrueckt wurde
     * und gibt diese Information an die Methode mausKlick(...) in der Game-Klasse weiter.
     * (nur wenn die Referenze auf die Spielklasse Game gesetzt wurde - 2. Konstruktor)
     */
    private class MyMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if (spiel==null) return;

            if(spiel.ComputerModus==2&&!spiel.spielHatBegonnen)
            {
                spiel.modi();
            }
            else if(spiel.spielIstZuEnde==false)
                for (int row=0; row<settings.length; row++)
                    for (int col=0; col<settings[0].length; col++)
                        if (e.getSource() == buttons[row][col])
                        {
                            spiel.mausKlick(row, col, e.getButton());
                        }

            if(spiel.spielIstZuEnde==true)
            {
                if(spiel.neuesSpielcounter==2)
                {
                    spiel.neuesSpiel();
                }else{
                    spiel.neuesSpielcounter++;
                }
            }
        }
    }
    private class MyMouseWheelListener implements MouseWheelListener
    {
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            if(spiel.spielIstZuEnde)
            {
                spiel.neuesSpiel();
            }
            else if(!spiel.spielHatBegonnen)
            {
                if(spiel.ComputerModus==0)
                {
                    spiel.ComputerModus++;
                    popup("Spielinfo", "Du spielst jetzt gegen den Computer als X", false);
                }
                else if(spiel.ComputerModus==1)
                {
                    spiel.ComputerModus++;
                    popup("Spielinfo", "Du spielst jetzt gegen den Computer als O", false);
                }else{
                    spiel.ComputerModus=0;
                    popup("Spielinfo", "Du spielst jetzt den zwei Spieler Modus", false);
                }
            }else{
                if(!spiel.spielIstZuEnde)
                {
                    if(!spiel.ZweitesMalDruecken)
                    {                    
                        spiel.ZweitesMalDruecken=true;
                        popup("Spielinfo:", "Wenn du das Spiel Neustarten willst drehe erneut das Mausrad", false);
                    }else{
                        spiel.neuesSpiel();
                    }
                }
            }
        }
    }

    /** Zeigt ein Popup-Fenster mit einer Meldung an
     * @param title Titel des Fensters
     * @param nachricht der anzuzeigende Text
     * @param fehler true: es wird ein Fehler-Popup-Fenster angezeigt
     *               false: es wird ein Info-Popup-Fenster angezeigt
     */
    public void popup(String titel, String nachricht, boolean fehler)
    {
        if (fehler)
            JOptionPane.showMessageDialog(this, nachricht, titel, JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, nachricht, titel, JOptionPane.INFORMATION_MESSAGE);
    }
}

