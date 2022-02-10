import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
/**
 * Die Spiele-Klasse in der alle Funktionen des Spiels "Tic Tac Toe" realisiert sind. 
 * 
 * @author (Bitte Deinen Namen hier einsetzen!) 
 * @version (a version number or a date)
 */
/*
 * In dieser Klasse, die zumindest vom Aufbau her von mir vorgeben ist, soll das Spiel 
 * "Tic Tac Toe" implementiert werden. Vorgegeben ist ein sinnvolles Geruest von
 * Methoden, deren Implementierung aber noch fehlt. Details enthalten die Kommentare bei 
 * den Methoden. Natuerlich koennen weitere Hilfsmethoden realisiert werden, diese sollten 
 * dann aber 'private' und damit fuer den Anwender nicht sichtbar sein. Zu Testzwecken kann 
 * man sie natuerlich kurzfristig als 'public' deklarieren.
 */
public class Game
{
    /* Diese Initialisierung des Arrays ist nur als anschauliches Beispiel gedacht.
     * Natuerlich ist es sinnvoller im Konstruktor die Methode "neuesSpiel()" aufzurufen,
     * die dann diese Initialisierung uebernimmt und dabei alle Felder "leer" setzt.
     */
    private char[][] gesetzt; // = {{'X',' ',' '},
    //    {' ','O',' '},  // (moegl. Initiialisierung des Arrays)
    //    {' ',' ',' '}};
    private boolean xIstAnDerReihe = true;
    public boolean spielIstZuEnde = false;
    public boolean spielHatBegonnen = false;
    public int ComputerModus=0;; //um gegen den Computer zu spielen
    public boolean ZweitesMalDruecken=false;
    public int neuesSpielcounter=1; //wird benoetigt da sonst die NeuesSpiel Methode direkt nach schliessen des Popup aufgerufen wird
    private int anzahlgesetzt=0;
    Random random = new Random();
    private int randomnumber1;
    private int randomnumber2;
    private int randomnumber3;
    private int randomnumberextra;
    // die Referenz 'oberflaeche', stellt eine Verbindung zur graphischen Oberflaeche her.
    private GUI oberflaeche;

    /**
     * Erzeugt die grafische Oberflaeche fuer das Spiel und ruft dann ... 
     */
    public Game()
    {
        // erzeugt das Array / die Spielfelder      
        gesetzt = new char[][] {{' ',' ',' '},   // (Initialisierung des Arrays im Konstruktor)     
            {' ',' ',' '},          
            {' ',' ',' '}};  //            NUR EIN BEISPIEL!

        // erzeugt die grafische Oberflaeche und zeigt die Spielfelder an
        oberflaeche = new GUI("Tic Tac Toe", gesetzt, this);

        // hier koennen alle weiteren fuer das Spiel erforderlichen Initialisierungen
        // erfolgen...
    }

    /** Setzt ein bestimmtes Zeichen in ein Spielfeld.     *
     *  ACHTUNG: diese Beispiel-Methode enthaelt NOCH KEINEERLEI Fehlerueberpruefungen!
     *  
     * @param zeile zulaessige Werte sind 0 bis Anzahl der Zeilen - 1
     * @param spalte zulaessige Werte sind 0 bis Anzahl der Spalten - 1
     * @param zeichen das neue Zeichen, das an der angeg. Position dargestellt werden soll.
     *                Moeglich sind grundsaetzlich alle Buchstaben (auch Leerzeichen).

     */
    public void setze(int zeile, int spalte, char zeichen)
    {
        // Aendern der Daten (des 2-dim. Arrays)...
        gesetzt[zeile][spalte] = zeichen; 
        // ... und Aktualisierung der Anzeioge
        oberflaeche.aktualisiereAnzeige();
    }

    /** zu erstellende Methode:     
     * 
     */
    public void neuesSpiel()
    {
        // - neruliches korr. Initialisieren des 2-dim. Arrays
        // - und aktualisieren der Anzeige
        // - sonstige Spielzustaende zuruecksetzen
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                gesetzt[i][j]=' ';
            }
        }
        oberflaeche.aktualisiereAnzeige();
        neuesSpielcounter=1;
        anzahlgesetzt=0;
        xIstAnDerReihe=true;
        spielHatBegonnen=false;
        spielIstZuEnde=false;
    }

    /** zu erstellende Methode:     
     * 
     */
    public void setzeX(int zeile, int spalte)
    {
        /* Soll fuer Spieler "X" ein Kreuzchen setzen. Das soll natuerlich nur an Stellen
         * moeglich sein, wo noch kein "X" oder "O" gesetzt wurde.
         * 
         * Weiters darf die Methode nur einmal aufgerufen werden, dann muss ein Aufruf von 
         * "setzeO()" erfolgen und erst danach darf diese Methode wieder einen Spielzug
         * erlauben.
         * 
         * Wird sie 2x unmittelbar hintereinander aufgerufen, dann soll der 2. Aufruf eine 
         * Fehlermeldung in der Art "Der andere Spieler ist am Zug!" ausgeben.
         *
         * 
         * Abschliessend ist die Methode "pruefeSpielstand() aufzurufen.
         */
        ZweitesMalDruecken=false;
        if(!spielHatBegonnen)
        {
            spielHatBegonnen=true;
        }
        gesetzt[zeile][spalte]='X';
        oberflaeche.aktualisiereAnzeige();
        anzahlgesetzt++;
        xIstAnDerReihe=false;
        pruefeSpielstand();
        if(ComputerModus==1)
        {
            modi();
        }
    }

    /** Setzt ein 'O' in die angegebene Zeile / Spalte
     *  @param zeile Wert zwischen 1 und 3
     *  @param spalte Wert zwischen 1 und 3
     */    
    public void setzeO(int zeile, int spalte)
    {
        /* Verhalten analog zu Methode "setzeX()" 
        Man koennte beide Methoden auch zu einer zusammenfassen 
        und das zu setzende Zeichen als zusaetzlichen Parameter uebergeben
         */
        ZweitesMalDruecken=false;
        if(!spielHatBegonnen)
        {
            spielHatBegonnen=true;
        }
        gesetzt[zeile][spalte]='O';
        oberflaeche.aktualisiereAnzeige();
        anzahlgesetzt++;
        xIstAnDerReihe=true;
        pruefeSpielstand();
        if(ComputerModus==2)
        {
            modi();
        }
    }

    /** zu erstellende Methode:     
     * 
     */
    public void /* char */ pruefeSpielstand()
    {
        int counterXHorizontal=0;
        int counterXVertikal=0;
        int counterYHorizontal=0;
        int counterYVertikal=0;
        int counterXDiagonalrunter=0;
        int counterXDiagonalrauf=0;
        int DiagonalXrunter=0;
        int DiagonalXrauf=2;
        int counterYDiagonalrunter=0;
        int counterYDiagonalrauf=0;
        int DiagonalYrunter=0;
        int DiagonalYrauf=2;
        int unentschieden=0;
        //Wie viele in der Reihe bereist gleich sind
        /* Diese Methode soll ueberpruefen ob von den 'X' oder den 'O' 3 horizontal, 
         * vertikal oder diagonal in einer Reihe sind.
         * Ist dies der Fall, dann soll der Gewinnerbuchstabe zurueckgegeben werden 
         * oder direkt ausgegeben werden (z.B.: "Spieler X hat gewonnen").
         * Danach soll kein weiterer Zug mehr moeglich sein, d.h. Aufrufe von 
         * "setzeX()" oder "setzeO()" sollen in diesem Zustand wirkungslos bleiben.
         * Erst nach einem Neustart des Spiels soll das wieder moeglich sein.
         * 
         * Anmerkung: zu Testzwecken ist es sinnvoll diese Methode "public" zu deklarieren,
         * sobald sie funktioniert, wird sie aber nur mehr von den setze...()-Methoden 
         * aufgerufen und kann dann auch auf "private" geaendert werden.
         */
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(xIstAnDerReihe==false&&gesetzt[i][j]=='X')
                {   
                    counterXHorizontal++;
                }
                if(xIstAnDerReihe==false&&gesetzt[j][i]=='X')
                {   
                    counterXVertikal++;
                }
                if(xIstAnDerReihe==true&&gesetzt[i][j]=='O')
                {   
                    counterYHorizontal++;
                }
                if(xIstAnDerReihe==true&&gesetzt[j][i]=='O')
                {   
                    counterYVertikal++;
                }
                if(gesetzt[i][j]!=' ')
                {   
                    unentschieden++;
                }
            }
            if(counterXHorizontal==3||counterXVertikal==3||counterYHorizontal==3||counterYVertikal==3)
            {
                break;
            }
            if(xIstAnDerReihe==false&&gesetzt[i][DiagonalXrunter]=='X')
            {
                counterXDiagonalrunter++;
            }
            if(xIstAnDerReihe==false&&gesetzt[i][DiagonalXrauf]=='X')
            {
                counterXDiagonalrauf++;
            }
            if(xIstAnDerReihe==true&&gesetzt[i][DiagonalYrunter]=='O')
            {
                counterYDiagonalrunter++;
            }
            if(xIstAnDerReihe==true&&gesetzt[i][DiagonalYrauf]=='O')
            {
                counterYDiagonalrauf++;
            } 
            counterXHorizontal=0;
            counterXVertikal=0;
            counterYHorizontal=0;
            counterYVertikal=0;
            DiagonalXrunter++;
            DiagonalXrauf--;
            DiagonalYrunter++;
            DiagonalYrauf--;
        }
        if(counterXHorizontal==3||counterXVertikal==3||counterXDiagonalrunter==3||counterXDiagonalrauf==3)
        {
            spielIstZuEnde=true;
            oberflaeche.popup("Spielinfo:", "Spieler X hat gewonnen", false);
            oberflaeche.popup("Spielinfo:", "Wenn du nochmal spielen willst drueck irgendwo im Programm hin oder drehe das Mausrad", false);
        }
        else if(counterYHorizontal==3||counterYVertikal==3||counterYDiagonalrunter==3||counterYDiagonalrauf==3)
        {
            spielIstZuEnde=true;
            oberflaeche.popup("Spielinfo:", "Spieler O hat gewonnen", false);
            oberflaeche.popup("Spielinfo:", "Wenn du nochmal spielen willst drueck irgendwo im Programm hin oder drehe das Mausrad", false);
        }
        else if(unentschieden==9)
        {
            spielIstZuEnde=true;
            oberflaeche.popup("Spielinfo:", "Unentschieden!!!", false);
            oberflaeche.popup("Spielinfo:", "Wenn du nochmal spielen willst drueck irgendwo im Programm hin oder drehe das Mausrad", false);
        }
    }

    /** Diese Methode wird von der graphischen Oberflaeche aufgerufen, sobald eine Taste
     * gedrueckt wurde. Als Information wird mitgeliefert in welcher Zeile und Spalte, 
     * welche Maustaste gedrueckt wurde (1=links, 2=mitte, 3=rechts). 
     *
     * Diese Methode kann verwendet werden um den Spielfeldinhalt entsprechend der eigenen
     * Spielelogik zu veraenden ;-)
     */
    public void mausKlick(int zeile, int spalte, int maustaste)
    {  
        /*
        System.out.println("Taste gedrueckt:");
        System.out.println("  Position: Zeile=" + zeile + ", Spalte=" + spalte);
        System.out.println("  Maustaste: " + maustaste);
         */
        if(maustaste==2)
        {
            oberflaeche.Spielanleitung();
        }
        if(maustaste==3)
        {
            if(xIstAnDerReihe==false)
            {
                // Mit der oberflaeche.popup(...)-Methode kannst Du ein Popup mit Titel und Text anzeigen
                // false ... INFO, true ... FEHLER - Nachricht
                //oberflaeche.popup("KLICK: ", "Taste " + maustaste + " gedrueckt, Spalte: " +spalte + ", Zeile: " + zeile, false);
                if(gesetzt[zeile][spalte]==' ')
                {
                    setzeO(zeile, spalte);
                }else{
                    oberflaeche.popup("Spielinfo:", "Dieses Feld ist bereits belegt", true);
                }
            }else{
                oberflaeche.popup("Spielinfo:", "Der Spieler X ist am Zug!", true);
            }
        }
        if(maustaste==1){
            if(xIstAnDerReihe==true)
            {
                // Mit der oberflaeche.popup(...)-Methode kannst Du ein Popup mit Titel und Text anzeigen
                // false ... INFO, true ... FEHLER - Nachricht
                //oberflaeche.popup("KLICK: ", "Taste " + maustaste + " gedrueckt, Spalte: " +spalte + ", Zeile: " + zeile, false);
                if(gesetzt[zeile][spalte]==' ')
                {
                    setzeX(zeile, spalte);
                }else{
                    oberflaeche.popup("Spielinfo:", "Dieses Feld ist bereits belegt", true);
                }
            }else{
                oberflaeche.popup("Spielinfo:", "Der Spieler O ist am Zug!", true);
            }
        }
    }

    public void modi()
    {        
        if(ComputerModus==1&&!spielIstZuEnde)
        {
            if(anzahlgesetzt==1)
            {
                if(gesetzt[1][1]=='X')
                {
                    randomnumber1=random.nextInt(4);
                    switch(randomnumber1)
                    {
                        case 0:
                        setzeO(0, 0);
                        break;

                        case 1:
                        setzeO(0, 2);
                        break;

                        case 2:
                        setzeO(2, 0);
                        break;

                        default:
                        setzeO(2, 2);
                    }
                }else{
                    setzeO(1, 1);
                    if(gesetzt[0][0]=='X')
                    {
                        randomnumber1=4;
                    }else if(gesetzt[0][1]=='X')
                    {
                        randomnumber1=5;
                    }else if(gesetzt[0][2]=='X')
                    {
                        randomnumber1=6;
                    }else if(gesetzt[1][0]=='X')
                    {
                        randomnumber1=7;
                    }else if(gesetzt[1][2]=='X')
                    {
                        randomnumber1=8;
                    }else if(gesetzt[2][0]=='X')
                    {
                        randomnumber1=9;
                    }else if(gesetzt[2][1]=='X')
                    {
                        randomnumber1=10;
                    }else if(gesetzt[2][2]=='X')
                    {
                        randomnumber1=11;
                    }
                }
            }
            else if(anzahlgesetzt==3)
            {
                if(gesetzt[1][1]=='X')
                { 
                    ChanceZumGewinnenOderBlocken('X', false);
                    if(anzahlgesetzt==3)
                    {
                        int randomnumber=random.nextInt(2);
                        if(randomnumber1==0||randomnumber1==3)
                        {
                            if(randomnumber==0)
                            {
                                setzeO(2,0);
                            }else{
                                setzeO(0,2);
                            }
                        }else{
                            if(randomnumber==0)
                            {
                                setzeO(0,0);
                            }else{
                                setzeO(2,2);
                            }
                        }
                    }
                }else if(randomnumber1==5||randomnumber1==7||randomnumber1==8||randomnumber1==10){
                    int randomnumber=random.nextInt(4);
                    if(randomnumber1==5)
                    {
                        if(gesetzt[2][1]=='X')
                        {
                            switch(randomnumber)
                            {
                                case 0:
                                setzeO(0,0);
                                break;

                                case 1:
                                setzeO(0,2);
                                break;

                                case 2:
                                setzeO(2,0);
                                break;

                                default:
                                setzeO(2,2);
                            }
                            randomnumber2=0;
                        }else if(gesetzt[0][0]=='X'||gesetzt[0][2]=='X'){
                            ChanceZumGewinnenOderBlocken('X', false);
                            randomnumber2=0;
                        }else if(gesetzt[1][0]=='X')
                        {
                            setzeO(0,0);
                            randomnumber2=1;
                        }else if(gesetzt[1][2]=='X')
                        {
                            setzeO(0,2);
                            randomnumber2=2;
                        }else{
                            int randomnumberextra=random.nextInt(2);
                            if(gesetzt[2][0]=='X')
                            {
                                if(randomnumberextra==0)
                                {
                                    setzeO(0,0);
                                    randomnumber2=3;
                                }else{
                                    setzeO(1,0);
                                    randomnumber2=4;
                                }
                            }else{
                                if(randomnumberextra==0)
                                {
                                    setzeO(0,2);
                                    randomnumber2=3;
                                }else{
                                    setzeO(1,2);
                                    randomnumber2=5;
                                }
                            }
                        }
                    }
                    else if(randomnumber1==7)
                    {
                        if(gesetzt[1][2]=='X')
                        {
                            switch(randomnumber)
                            {
                                case 0:
                                setzeO(0,0);
                                break;

                                case 1:
                                setzeO(0,2);
                                break;

                                case 2:
                                setzeO(2,0);
                                break;

                                default:
                                setzeO(2,2);
                            }
                            randomnumber2=0;
                        }else if(gesetzt[0][0]=='X'||gesetzt[2][0]=='X'){
                            ChanceZumGewinnenOderBlocken('X', false);
                            randomnumber2=0;
                        }else if(gesetzt[2][1]=='X')
                        {
                            setzeO(2,0);
                            randomnumber2=2;
                        }else if(gesetzt[0][1]=='X')
                        {
                            setzeO(0,0);
                            randomnumber2=1;
                        }else{
                            int randomnumberextra=random.nextInt(2);
                            if(gesetzt[2][2]=='X')
                            {
                                if(randomnumberextra==0)
                                {
                                    setzeO(2,0);
                                    randomnumber2=3;
                                }else{
                                    setzeO(2,1);
                                    randomnumber2=6;
                                }
                            }else{
                                if(randomnumberextra==0)
                                {
                                    setzeO(0,0);
                                    randomnumber2=3;
                                }else{
                                    setzeO(0,1);
                                    randomnumber2=4;
                                }
                            }
                        }
                    }
                    else if(randomnumber1==8)
                    {
                        if(gesetzt[1][0]=='X')
                        {
                            switch(randomnumber)
                            {
                                case 0:
                                setzeO(0,0);
                                break;

                                case 1:
                                setzeO(0,2);
                                break;

                                case 2:
                                setzeO(2,0);
                                break;

                                default:
                                setzeO(2,2);
                            }
                            randomnumber2=0;
                        }else if(gesetzt[0][2]=='X'||gesetzt[2][2]=='X'){
                            ChanceZumGewinnenOderBlocken('X', false);
                            randomnumber2=0;
                        }else if(gesetzt[0][1]=='X')
                        {
                            setzeO(0,2);
                            randomnumber2=2;
                        }else if(gesetzt[2][1]=='X')
                        {
                            setzeO(2,2);
                            randomnumber2=1;
                        }else{
                            int randomnumberextra=random.nextInt(2);
                            if(gesetzt[0][0]=='X')
                            {
                                if(randomnumberextra==0)
                                {
                                    setzeO(0,2);
                                    randomnumber2=3;
                                }else{
                                    setzeO(0,1);
                                    randomnumber2=5;
                                }
                            }else{
                                if(randomnumberextra==0)
                                {
                                    setzeO(2,2);
                                    randomnumber2=3;
                                }else{
                                    setzeO(2,1);
                                    randomnumber2=7;
                                }
                            }
                        }
                    }
                    else if(randomnumber1==10)
                    {
                        if(gesetzt[0][1]=='X')
                        {
                            switch(randomnumber)
                            {
                                case 0:
                                setzeO(0,0);
                                break;

                                case 1:
                                setzeO(0,2);
                                break;

                                case 2:
                                setzeO(2,0);
                                break;

                                default:
                                setzeO(2,2);
                            }
                            randomnumber2=0;
                        }else if(gesetzt[2][0]=='X'||gesetzt[2][2]=='X'){
                            ChanceZumGewinnenOderBlocken('X', false);
                            randomnumber2=0;
                        }else if(gesetzt[1][2]=='X')
                        {
                            setzeO(2,2);
                            randomnumber2=1;
                        }else if(gesetzt[1][0]=='X')
                        {
                            setzeO(2,0);
                            randomnumber2=2;
                        }else{
                            int randomnumberextra=random.nextInt(2);
                            if(gesetzt[0][2]=='X')
                            {
                                if(randomnumberextra==0)
                                {
                                    setzeO(2,2);
                                    randomnumber2=3;
                                }else{
                                    setzeO(1,2);
                                    randomnumber2=7;
                                }
                            }else{
                                if(randomnumberextra==0)
                                {
                                    setzeO(2,0);
                                    randomnumber2=3;
                                }else{
                                    setzeO(1,0);
                                    randomnumber2=6;
                                }
                            }
                        }
                    }
                }else{
                    if(randomnumber1==4)
                    {
                        ChanceZumGewinnenOderBlocken('X', false);
                        int randomnumberextra=random.nextInt(2);
                        if(anzahlgesetzt==4)
                        {
                            if(gesetzt[0][1]=='X'||gesetzt[1][0]=='X')
                            {
                                randomnumber2=0;
                            }else if(gesetzt[0][2]=='X')
                            {
                                randomnumber2=8;
                            }else if(gesetzt[2][0]=='X')
                            {
                                randomnumber2=9;
                            }
                        }else if(gesetzt[1][2]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(0,2);
                                randomnumber2=3;
                            }else{
                                setzeO(0,1);
                                randomnumber2=5;
                            }
                        }else if(gesetzt[2][1]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(2,0);
                                randomnumber2=3;
                            }else{
                                setzeO(1,0);
                                randomnumber2=6;
                            }
                        }else if(gesetzt[2][2]=='X'){
                            randomnumberextra=random.nextInt(4);
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeO(0,1);
                                break;

                                case 1:
                                setzeO(1,0);
                                break;

                                case 2:
                                setzeO(1,2);
                                break;

                                default:
                                setzeO(2,1);
                            }
                            randomnumber2=0;
                        }
                    }else if(randomnumber1==6)
                    {
                        ChanceZumGewinnenOderBlocken('X', false);
                        int randomnumberextra=random.nextInt(2);
                        if(anzahlgesetzt==4)
                        {
                            if(gesetzt[0][1]=='X'||gesetzt[1][0]=='X')
                            {
                                randomnumber2=0;
                            }else if(gesetzt[0][0]=='X')
                            {
                                randomnumber2=8;
                            }else if(gesetzt[2][0]=='X')
                            {
                                randomnumber2=9;
                            }
                        }else if(gesetzt[1][0]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(0,0);
                                randomnumber2=3;
                            }else{
                                setzeO(0,1);
                                randomnumber2=4;
                            }
                        }else if(gesetzt[2][1]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(2,2);
                                randomnumber2=3;
                            }else{
                                setzeO(1,2);
                                randomnumber2=7;
                            }
                        }else if(gesetzt[2][0]=='X'){
                            randomnumberextra=random.nextInt(4);
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeO(0,1);
                                break;

                                case 1:
                                setzeO(1,0);
                                break;

                                case 2:
                                setzeO(1,2);
                                break;

                                default:
                                setzeO(2,1);
                            }
                            randomnumber2=0;
                        }
                    }else if(randomnumber1==9)
                    {
                        ChanceZumGewinnenOderBlocken('X', false);
                        int randomnumberextra=random.nextInt(2);
                        if(anzahlgesetzt==4)
                        {
                            if(gesetzt[2][1]=='X'||gesetzt[1][0]=='X')
                            {
                                randomnumber2=0;
                            }else if(gesetzt[2][2]=='X')
                            {
                                randomnumber2=8;
                            }else if(gesetzt[0][0]=='X')
                            {
                                randomnumber2=9;
                            }
                        }else if(gesetzt[1][2]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(2,2);
                                randomnumber2=3;
                            }else{
                                setzeO(2,1);
                                randomnumber2=7;
                            }
                        }else if(gesetzt[0][1]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(0,0);
                                randomnumber2=3;
                            }else{
                                setzeO(1,0);
                                randomnumber2=4;
                            }
                        }else if(gesetzt[0][2]=='X'){
                            randomnumberextra=random.nextInt(4);
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeO(0,1);
                                break;

                                case 1:
                                setzeO(1,0);
                                break;

                                case 2:
                                setzeO(1,2);
                                break;

                                default:
                                setzeO(2,1);
                            }
                            randomnumber2=0;
                        }
                    }else{
                        ChanceZumGewinnenOderBlocken('X', false);
                        int randomnumberextra=random.nextInt(2);
                        if(anzahlgesetzt==4)
                        {  
                            if(gesetzt[1][2]=='X'||gesetzt[2][1]=='X')
                            {
                                randomnumber2=0;
                            }else if(gesetzt[0][2]=='X')
                            {
                                randomnumber2=9;
                            }else if(gesetzt[2][0]=='X')
                            {
                                randomnumber2=8;
                            }
                        }else if(gesetzt[0][1]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(0,2);
                                randomnumber2=3;
                            }else{
                                setzeO(1,2);
                                randomnumber2=5;
                            }
                        }else if(gesetzt[1][0]=='X')
                        {
                            if(randomnumberextra==0)
                            {
                                setzeO(2,0);
                                randomnumber2=3;
                            }else{
                                setzeO(2,1);
                                randomnumber2=6;
                            }
                        }else if(gesetzt[0][0]=='X'){
                            randomnumberextra=random.nextInt(4);
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeO(0,1);
                                break;

                                case 1:
                                setzeO(1,0);
                                break;

                                case 2:
                                setzeO(1,2);
                                break;

                                default:
                                setzeO(2,1);
                            }
                            randomnumber2=0;
                        }
                    }
                }
            }
            else if(anzahlgesetzt==5)
            {
                if(gesetzt[1][1]=='X'||randomnumber2==0)
                {
                    ChanceZumGewinnenOderBlocken('O', true);
                    if(anzahlgesetzt==5)
                    {
                        ChanceZumGewinnenOderBlocken('X', false);
                    }
                    if(anzahlgesetzt==5)
                    {
                        int i=0;
                        int j=0;
                        for(;gesetzt[i][j]!=' ';)
                        {
                            i=random.nextInt(3);
                            j=random.nextInt(3);
                        }
                        setzeO(i,j);
                    }
                }else{
                    ChanceZumGewinnenOderBlocken('O', true);
                    if(anzahlgesetzt==5)
                    {
                        int randomnumber=random.nextInt(2);
                        switch(randomnumber2)
                        {
                            case 1:
                            if(randomnumber==0)
                            {
                                setzeO(0,2);
                            }else{
                                setzeO(2,0);
                            }
                            break;

                            case 2:
                            if(randomnumber==0)
                            {
                                setzeO(0,0);
                            }else{
                                setzeO(2,2);
                            }
                            break;

                            case 3:
                            ChanceZumGewinnenOderBlocken('X', false);
                            break;

                            case 4: 
                            setzeO(2,2);
                            break;

                            case 5: 
                            setzeO(2,0);
                            break;

                            case 6: 
                            setzeO(0,2);
                            break;

                            case 7: 
                            setzeO(0,0);
                            break;

                            case 8:
                            if(randomnumber==0)
                            {
                                setzeO(1,0);
                            }else{
                                setzeO(1,2);
                            }
                            break;

                            case 9:
                            if(randomnumber==0)
                            {
                                setzeO(0,1);
                            }else{
                                setzeO(2,1);
                            }
                            break;
                        }
                    } 
                }
            }
            else
            {
                ChanceZumGewinnenOderBlocken('O', true);
                if(anzahlgesetzt==7)
                {
                    ChanceZumGewinnenOderBlocken('X', false);
                }
                if(anzahlgesetzt==7)
                {
                    int i=0;
                    int j=0;
                    for(;gesetzt[i][j]!=' ';)
                    {
                        i=random.nextInt(3);
                        j=random.nextInt(3);
                    }
                    setzeO(i,j);
                }
            }
        }else if(!spielIstZuEnde)
        {
            if(anzahlgesetzt==0)
            {
                randomnumber1=random.nextInt(4);
                switch(randomnumber1)
                {
                    case 0:
                    setzeX(0, 0);
                    break;

                    case 1:
                    setzeX(0, 2);
                    break;

                    case 2:
                    setzeX(2, 0);
                    break;

                    default:
                    setzeX(2, 2);
                }
            }
            else if(anzahlgesetzt==2)
            {
                if(gesetzt[1][1]=='O')
                {
                    int randomnumber=random.nextInt(2);
                    if(randomnumber==0)
                    {
                        switch(randomnumber1)
                        {
                            case 0:
                            setzeX(2, 2);
                            randomnumber2=0;
                            break;

                            case 1:
                            setzeX(2, 0);
                            randomnumber2=1;
                            break;

                            case 2:
                            setzeX(0, 2);
                            randomnumber2=1;
                            break;

                            default:
                            setzeX(0, 0);
                            randomnumber2=0;
                        }
                    }else{
                        randomnumber=random.nextInt(2);
                        switch(randomnumber1)
                        {
                            case 0:
                            if(randomnumber==1)
                            {
                                setzeX(1, 2);
                                randomnumber2=2;
                            }else{
                                setzeX(2, 1);
                                randomnumber2=3;
                            }
                            break;

                            case 1:
                            if(randomnumber==1)
                            {
                                setzeX(1, 0);
                                randomnumber2=4;
                            }else{
                                setzeX(2, 1);
                                randomnumber2=5;
                            }
                            break;

                            case 2:
                            if(randomnumber==1)
                            {
                                setzeX(0, 1);
                                randomnumber2=6;
                            }else{
                                setzeX(1, 2);
                                randomnumber2=7;
                            }
                            break;

                            default:
                            if(randomnumber==1)
                            {
                                setzeX(0, 1);
                                randomnumber2=8;
                            }else{
                                setzeX(1, 0);
                                randomnumber2=9;
                            }
                        }
                    }
                }
                else if(gesetzt[0][0]=='O'||gesetzt[2][0]=='O'||gesetzt[0][2]=='O'||gesetzt[2][2]=='O')
                {
                    switch(randomnumber1)
                    {
                        case 0:
                        if(gesetzt[2][0]=='O'||gesetzt[0][2]=='O')
                        {
                            setzeX(2, 2);
                            randomnumber2=10;
                        }else{
                            setzeX(1, 1);
                            randomnumber2=11;
                        }
                        break;

                        case 1:
                        if(gesetzt[0][0]=='O'||gesetzt[2][2]=='O')
                        {
                            setzeX(2, 0);
                            randomnumber2=10;
                        }else{
                            setzeX(1, 1);
                            randomnumber2=12;
                        }
                        break;

                        case 2:
                        if(gesetzt[0][0]=='O'||gesetzt[2][2]=='O')
                        {
                            setzeX(0, 2);
                            randomnumber2=10;
                        }else{
                            setzeX(1, 1);
                            randomnumber2=13;
                        }
                        break;

                        default:
                        if(gesetzt[2][0]=='O'||gesetzt[0][2]=='O')
                        {
                            setzeX(0, 0);
                            randomnumber2=10;
                        }else{
                            setzeX(1, 1);
                            randomnumber2=14;
                        }
                    }
                }else{
                    setzeX(1, 1);
                    if(gesetzt[0][1]=='O')
                    {
                        randomnumberextra=0;
                    }else if(gesetzt[1][0]=='O')
                    {
                        randomnumberextra=1;
                    }else if(gesetzt[1][2]=='O')
                    {
                        randomnumberextra=1;
                    }else{
                        randomnumberextra=0;
                    }
                    randomnumber2=15;
                }
            }
            else if(anzahlgesetzt==4)
            {
                switch(randomnumber2)
                {
                    case 0:
                    if(gesetzt[2][0]=='O')
                    {
                        setzeX(0, 2);
                        randomnumber3=0;
                    }
                    else if(gesetzt[0][2]=='O')
                    {
                        setzeX(2, 0);
                        randomnumber3=0;
                    }else if(gesetzt[0][1]=='O')
                    {
                        setzeX(2, 1);
                        randomnumber3=1;
                    }
                    else if(gesetzt[1][0]=='O')
                    {
                        setzeX(1, 2);
                        randomnumber3=1;
                    }
                    else if(gesetzt[1][2]=='O')
                    {
                        setzeX(1, 0);
                        randomnumber3=1;
                    }
                    else
                    {
                        setzeX(0, 1);
                        randomnumber3=1;
                    }
                    break;

                    case 1:
                    if(gesetzt[0][0]=='O')
                    {
                        setzeX(2, 2);
                        randomnumber3=0;
                    }
                    else if(gesetzt[0][1]=='O')
                    {
                        setzeX(2, 1);
                        randomnumber3=1;
                    }else if(gesetzt[1][0]=='O')
                    {
                        setzeX(1, 2);
                        randomnumber3=1;
                    }
                    else if(gesetzt[1][2]=='O')
                    {
                        setzeX(1, 0);
                        randomnumber3=1;
                    }
                    else if(gesetzt[2][1]=='O')
                    {
                        setzeX(0, 1);
                        randomnumber3=1;
                    }
                    else
                    {
                        setzeX(0, 0);
                        randomnumber3=0;
                    }
                    break;

                    case 2:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            randomnumber3=3;
                        }
                        else if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=4;
                        }else{
                            randomnumber3=0;
                        }    
                    }
                    else
                    {
                        if(gesetzt[1][0]=='O')
                        {
                            setzeX(0,2);
                            randomnumber3=0;
                        }else{
                            setzeX(2,0);
                            randomnumber3=5;
                        }
                    }
                    break;

                    case 3:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=0;
                        }
                        else if(gesetzt[1][2]=='O')
                        {
                            randomnumber3=6;
                        }else{
                            randomnumber3=7;
                        }    
                    }
                    else
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            setzeX(2,0);
                            randomnumber3=0;
                        }else{
                            setzeX(0,2);
                            randomnumber3=8;
                        }
                    }
                    break;

                    case 4:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=8;
                        }
                        else if(gesetzt[0][1]=='O')
                        {
                            randomnumber3=9;
                        }else{
                            randomnumber3=0;
                        }    
                    }
                    else
                    {
                        if(gesetzt[1][2]=='O')
                        {
                            setzeX(0,0);
                            randomnumber3=0;
                        }else{
                            setzeX(2,2);
                            randomnumber3=10;
                        }
                    }
                    break;

                    case 5:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=0;
                        }
                        else if(gesetzt[1][0]=='O')
                        {
                            randomnumber3=1;
                        }else{
                            randomnumber3=10;
                        }    
                    }
                    else
                    {
                        if(gesetzt[1][2]=='O')
                        {
                            setzeX(0,0);
                            randomnumber3=0;
                        }else{
                            setzeX(2,2);
                            randomnumber3=8;
                        }
                    }
                    break;

                    case 6:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[2][2]=='O')
                        {
                            randomnumber3=0;
                        }
                        else if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=6;
                        }else if(gesetzt[1][0]=='O'){
                            randomnumber3=11;
                        }else{
                            randomnumber3=6;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            setzeX(2,2);
                            randomnumber3=10;
                        }else{
                            setzeX(0,0);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 7:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[2][2]=='O')
                        {
                            randomnumber3=6;
                        }
                        else if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=0;
                        }else if(gesetzt[0][1]=='O'){
                            randomnumber3=10;
                        }else{
                            randomnumber3=12;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            setzeX(0,0);
                            randomnumber3=13;
                        }else{
                            setzeX(2,2);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 8:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=10;
                        }
                        else if(gesetzt[1][0]=='O')
                        {
                            randomnumber3=8;
                        }else if(gesetzt[1][2]=='O'){
                            randomnumber3=14;
                        }else{
                            randomnumber3=0;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            setzeX(2,0);
                            randomnumber3=6;
                        }else{
                            setzeX(0,2);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 9:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            randomnumber3=6;
                        }
                        else if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=0;
                        }else if(gesetzt[2][0]=='O'){
                            randomnumber3=10;
                        }else{
                            randomnumber3=15;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            setzeX(0,2);
                            randomnumber3=8;
                        }else{
                            setzeX(2,0);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 10:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==4)
                    {
                        ChanceZumGewinnenOderBlocken('O', false);
                        randomnumber3=0;
                    }
                    break;

                    case 11:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=5;
                        }
                        else if(gesetzt[1][2]=='O')
                        {
                            randomnumber3=0;
                        }else if(gesetzt[2][0]=='O'){
                            randomnumber3=16;
                        }else{
                            randomnumber3=0;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            setzeX(2,0);
                            randomnumber3=0;
                        }else{
                            setzeX(0,2);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 12:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=5;
                        }
                        else if(gesetzt[1][0]=='O')
                        {
                            randomnumber3=0;
                        }else if(gesetzt[2][1]=='O'){
                            randomnumber3=0;
                        }else{
                            randomnumber3=7;
                        }
                    }
                    else
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            setzeX(2,2);
                            randomnumber3=0;
                        }else{
                            setzeX(0,0);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 13:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            randomnumber3=16;
                        }
                        else if(gesetzt[0][1]=='O')
                        {
                            randomnumber3=0;
                        }else if(gesetzt[1][2]=='O'){
                            randomnumber3=0;
                        }else{
                            randomnumber3=4;
                        }
                    }
                    else
                    {
                        if(gesetzt[1][0]=='O')
                        {
                            setzeX(2,2);
                            randomnumber3=0;
                        }else{
                            setzeX(0,0);
                            randomnumber3=0;
                        }
                    }
                    break;

                    case 14:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==5)
                    {
                        if(gesetzt[0][1]=='O')
                        {
                            randomnumber3=0;
                        }
                        else if(gesetzt[0][2]=='O')
                        {
                            randomnumber3=7;
                        }else if(gesetzt[1][0]=='O'){
                            randomnumber3=0;
                        }else{
                            randomnumber3=4;
                        }
                    }
                    else
                    {
                        if(gesetzt[1][2]=='O')
                        {
                            setzeX(2,0);
                            randomnumber3=0;
                        }else{
                            setzeX(0,2);
                            randomnumber3=0;
                        }
                    }
                    break;

                    default:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==4)
                    {
                        switch(randomnumber1)
                        {
                            case 0:
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeX(2,0);
                                randomnumber3=0;
                                break;

                                default:
                                setzeX(0,2);
                                randomnumber3=0;
                            }
                            break;

                            case 1:
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeX(2,2);
                                randomnumber3=0;
                                break;

                                default:
                                setzeX(0,0);
                                randomnumber3=0;
                            }
                            break;

                            case 2:
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeX(0,0);
                                randomnumber3=0;
                                break;

                                default:
                                setzeX(2,2);
                                randomnumber3=0;
                            }
                            break;

                            default:
                            switch(randomnumberextra)
                            {
                                case 0:
                                setzeX(0,2);
                                randomnumber3=0;
                                break;

                                default:
                                setzeX(2,0);
                                randomnumber3=0;
                            }
                        }
                    }
                }
            }
            else if(anzahlgesetzt==6)
            {
                switch(randomnumber3)
                {
                    case 0:
                    ChanceZumGewinnenOderBlocken('X', true);
                    break;

                    case 1:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        ChanceZumGewinnenOderBlocken('O', false);
                    }
                    break;

                    //case 2:

                    case 3:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[1][0]=='O')
                        {
                            setzeX(2,2);
                        }else{
                            setzeX(2,0);
                        }
                    }
                    break;

                    case 4:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(2,1);
                    }
                    break;

                    case 5:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(0,1);
                    }
                    break;

                    case 6:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(0,2);
                    }
                    break;

                    case 7:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(1,2);
                    }
                    break;

                    case 8:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(2,0);
                    }
                    break;

                    case 9:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[1][2]=='O')
                        {
                            setzeX(2,0);
                        }else{
                            setzeX(1,2);
                        }
                    }
                    break;

                    case 10:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(0,0);
                    }
                    break;

                    case 11:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[0][2]=='O')
                        {
                            setzeX(2,1);
                        }else{
                            setzeX(0,2);
                        }
                    }

                    case 12:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[1][0]=='O')
                        {
                            setzeX(0,2);
                        }else{
                            setzeX(1,0);
                        }
                    }
                    break;

                    case 13:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(2,2);
                    }
                    break;

                    case 14:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            setzeX(2,1);
                        }else{
                            setzeX(0,0);
                        }
                    }
                    break;

                    case 15:
                    ChanceZumGewinnenOderBlocken('O', false);
                    if(anzahlgesetzt==6)
                    {
                        if(gesetzt[0][0]=='O')
                        {
                            setzeX(1,2);
                        }else{
                            setzeX(0,0);
                        }
                    }
                    break;

                    case 16:
                    ChanceZumGewinnenOderBlocken('X', true);
                    if(anzahlgesetzt==6)
                    {
                        setzeX(1,0);
                    }
                    break;

                }
            }
            else
            {
                for(int i=0; i<3; i++)
                {
                    for(int j=0; j<3; j++)
                    {
                        if(gesetzt[i][j]==' ')
                            setzeX(i, j);
                    }
                }
            }
        }
    }

    public void ChanceZumGewinnenOderBlocken(char spieler, boolean gewinnen)
    {
        int counterHorizontal=0;
        int counterHorizontalLeerzeichen=0;
        int counterVertikal=0;
        int counterVertikalLeerzeichen=0;
        int counterDiagonalrunter=0;
        int counterDiagonalrauf=0;
        int Diagonalrunter=0;
        int Diagonalrauf=2;
        int counterDiagonalrunterLeerzeichen=0;
        int counterDiagonalraufLeerzeichen=0;
        int indexHorizontal=0;
        int indexVertikal=0;
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(gesetzt[i][j]==spieler)
                {   
                    counterHorizontal++;
                }
                if(gesetzt[j][i]==spieler)
                {   
                    counterVertikal++;
                }
                if(gesetzt[i][j]==' ')
                {   
                    counterHorizontalLeerzeichen++;
                }
                if(gesetzt[j][i]==' ')
                {   
                    counterVertikalLeerzeichen++;
                }
            }
            if(counterHorizontal==2&&counterHorizontalLeerzeichen==1||counterVertikal==2&&counterVertikalLeerzeichen==1)
            {
                break;
            }
            if(gesetzt[i][Diagonalrunter]==spieler)
            {
                counterDiagonalrunter++;
            }
            if(gesetzt[i][Diagonalrauf]==spieler)
            {
                counterDiagonalrauf++;
            }
            if(gesetzt[i][Diagonalrunter]==' ')
            {
                counterDiagonalrunterLeerzeichen++;
            }
            if(gesetzt[i][Diagonalrauf]==' ')
            {
                counterDiagonalraufLeerzeichen++;
            }
            counterHorizontal=0;
            counterVertikal=0;
            counterHorizontalLeerzeichen=0;
            counterVertikalLeerzeichen=0;
            Diagonalrunter++;
            Diagonalrauf--;
            indexHorizontal++;
            indexVertikal++;
        }
        if(counterHorizontal==2&&counterHorizontalLeerzeichen==1)
        {
            switch(indexHorizontal)
            {
                case 0:
                if(gesetzt[0][0]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,0);
                        }else{
                            setzeO(0,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,0);
                        }else{
                            setzeX(0,0);
                        }
                    }
                }
                else if(gesetzt[0][1]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,1);
                        }else{
                            setzeO(0,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,1);
                        }else{
                            setzeX(0,1);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,2);
                        }else{
                            setzeO(0,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,2);
                        }else{
                            setzeX(0,2);
                        }
                    }
                }
                break;
                case 1:
                if(gesetzt[1][0]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,0);
                        }else{
                            setzeO(1,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,0);
                        }else{
                            setzeX(1,0);
                        }
                    }
                }
                else if(gesetzt[1][1]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,1);
                        }else{
                            setzeO(1,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,1);
                        }else{
                            setzeX(1,1);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,2);
                        }else{
                            setzeO(1,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,2);
                        }else{
                            setzeX(1,2);
                        }
                    }
                }
                break;
                default:
                if(gesetzt[2][0]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,0);
                        }else{
                            setzeO(2,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,0);
                        }else{
                            setzeX(2,0);
                        }
                    }
                }
                else if(gesetzt[2][1]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,1);
                        }else{
                            setzeO(2,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,1);
                        }else{
                            setzeX(2,1);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,2);
                        }else{
                            setzeO(2,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,2);
                        }else{
                            setzeX(2,2);
                        }
                    }
                }
            }
        }
        else if(counterVertikal==2&&counterVertikalLeerzeichen==1)
        {
            switch(indexVertikal)
            {
                case 0:
                if(gesetzt[0][0]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,0);
                        }else{
                            setzeO(0,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,0);
                        }else{
                            setzeX(0,0);
                        }
                    }
                }
                else if(gesetzt[1][0]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,0);
                        }else{
                            setzeO(1,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,0);
                        }else{
                            setzeX(1,0);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,0);
                        }else{
                            setzeO(2,0);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,0);
                        }else{
                            setzeX(2,0);
                        }
                    }
                }
                break;
                case 1:
                if(gesetzt[0][1]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,1);
                        }else{
                            setzeO(0,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,1);
                        }else{
                            setzeX(0,1);
                        }
                    }
                }
                else if(gesetzt[1][1]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,1);
                        }else{
                            setzeO(1,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,1);
                        }else{
                            setzeX(1,1);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,1);
                        }else{
                            setzeO(2,1);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,1);
                        }else{
                            setzeX(2,1);
                        }
                    }
                }
                break;
                default:
                if(gesetzt[0][2]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(0,2);
                        }else{
                            setzeO(0,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(0,2);
                        }else{
                            setzeX(0,2);
                        }
                    }
                }
                else if(gesetzt[1][2]==' ')
                {
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(1,2);
                        }else{
                            setzeO(1,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(1,2);
                        }else{
                            setzeX(1,2);
                        }
                    }
                }else{
                    if(spieler=='X')
                    {
                        if(gewinnen==true)
                        {
                            setzeX(2,2);
                        }else{
                            setzeO(2,2);
                        }
                    }else{
                        if(gewinnen==true)
                        {
                            setzeO(2,2);
                        }else{
                            setzeX(2,2);
                        }
                    }
                }
            }
        }
        else if(counterDiagonalraufLeerzeichen==1&&counterDiagonalrauf==2)
        {
            if(gesetzt[2][0]==' ')
            {
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(2,0);
                    }else{
                        setzeO(2,0);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(2,0);
                    }else{
                        setzeX(2,0);
                    }
                }
            }
            else if(gesetzt[1][1]==' ')
            {
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(1,1);
                    }else{
                        setzeO(1,1);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(1,1);
                    }else{
                        setzeX(1,1);
                    }
                }
            }else{
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(0,2);
                    }else{
                        setzeO(0,2);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(0,2);
                    }else{
                        setzeX(0,2);
                    }
                }
            }
        }
        else if(counterDiagonalrunterLeerzeichen==1&&counterDiagonalrunter==2)
        {
            if(gesetzt[0][0]==' ')
            {
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(0,0);
                    }else{
                        setzeO(0,0);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(0,0);
                    }else{
                        setzeX(0,0);
                    }
                }
            }
            else if(gesetzt[1][1]==' ')
            {
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(1,1);
                    }else{
                        setzeO(1,1);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(1,1);
                    }else{
                        setzeX(1,1);
                    }
                }
            }else{
                if(spieler=='X')
                {
                    if(gewinnen==true)
                    {
                        setzeX(2,2);
                    }else{
                        setzeO(2,2);
                    }
                }else{
                    if(gewinnen==true)
                    {
                        setzeO(2,2);
                    }else{
                        setzeX(2,2);
                    }
                }
            }
        }
    }

    /** main-Methode: zum Start des Spiels ohne in BlueJ ein Objekt manuell zu erzeugen */
    public static void main(String[] args)
    {
        new Game();
    }
}   