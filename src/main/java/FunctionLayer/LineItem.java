package FunctionLayer;

public class LineItem {
    
    String navn;
    int længde;
    int antal;
    String enhed;
    String beskrivelse;
    double pris;

    public LineItem(String navn, int længde, int antal, String enhed, String beskrivelse, double pris) {
        this.navn = navn;
        this.længde = længde;
        this.antal = antal;
        this.enhed = enhed;
        this.beskrivelse = beskrivelse;
        this.pris = pris;
    }

    public String getNavn() {
        return navn;
    }

    public int getLængde() {
        return længde;
    }

    public int getAntal() {
        return antal;
    }

    public String getEnhed() {
        return enhed;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public double getPris() {
        return pris;
    }
    
    
    
    
    
}
