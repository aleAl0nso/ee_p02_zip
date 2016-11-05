public class Letra<T> {
    
    private T letra;
    private int frecuencia;
    private Letra<T> der;
    private Letra<T> izq;

    public Letra(String letra, int rep) {
        this.letra = (T) letra;
        frecuencia = rep;
        der=null;
        izq=null;
    }

    public Letra(T letra, int rep, Letra<T> der, Letra<T> izq) {
        this.letra = letra;
        frecuencia = rep;
        this.der = der;
        this.izq = izq;
    }
    
    public Letra<T> getDerecha() {
        return der;
    }

    public void setDerecha(Letra<T> der) {
        this.der = der;
    }

    public Letra<T> getIzquierda() {
        return izq;
    }

    public void setIzquierda(Letra<T> izq) {
        this.izq = izq;
    }
    
    public T getLetra() {
        return letra;
    }

    public void setLetra(T letra) {
        this.letra = letra;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int rep) {
        frecuencia = rep;
    }
    
    public String toString(){
    	return "El caracter '"+letra+"' se repite "+frecuencia+" veces";
    }

	
}
