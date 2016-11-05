
public class ColaPrioridad<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    
    ColaPrioridad(){
        inicio=null;
        fin=null;
    }
    
    public boolean estaVacia(){
        return inicio==null;
    }
    
    public boolean agregar(Letra<T> letra){
        Nodo<T> nuevo =new Nodo<T>(letra,null);
        if(!estaVacia()){
            if(((Letra<T> )letra).getFrecuencia()<((Letra<T>)inicio.getDato()).getFrecuencia()){
                nuevo.setSiguiente(inicio);
                inicio=nuevo;
                return true;
            }else{
                Nodo<T> aux=inicio;
                while(aux.getSiguiente()!=null){
                    if(((Letra<T>)letra).getFrecuencia()<((Letra<T>)aux.getSiguiente().getDato()).getFrecuencia() ||  ((Letra<T>)letra).getFrecuencia()==((Letra<T>)aux.getSiguiente().getDato()).getFrecuencia()){
                        nuevo.setSiguiente(aux.getSiguiente());
                        aux.setSiguiente(nuevo);
                        return true;
                    }else if(((Letra<T>)letra).getFrecuencia()>((Letra<T>)aux.getSiguiente().getDato()).getFrecuencia()){
                        aux=aux.getSiguiente();
                    }
                }
                aux.setSiguiente(nuevo);
                fin=nuevo;
                return true;
            }
        }else{
            inicio=nuevo;
            fin=nuevo;
            return true;
        }
    }
    
    public boolean existe(T letra){
        Nodo<T> temporal = inicio;
        while (temporal!=null){
        	if(((Letra<T>)temporal.getDato()).equals(letra)){
                return true;
            }else{
                temporal=temporal.getSiguiente();
            }
        }
        return false;
    }
    
    public Object sacar(){
        if(!estaVacia()){
            Nodo temporal=inicio;
            inicio=inicio.getSiguiente();
            return temporal;
        }
        return null;
    }
    
    public String toString(){
        String texto = "";
        Nodo<T> aux = inicio;
        while(aux!=null){
            texto=texto+((Letra<T>)aux.getDato()).toString()+"\n";
            aux=aux.getSiguiente();
        }
        return texto;
    }

    public Nodo<T> getFrente() {
        return inicio;
    }
    
    public ColaPrioridad<T> pasarAArbol(){
        Nodo<T> temporal = getFrente();
        while(temporal.getSiguiente()!=null){
        	Letra<T> letra = new Letra<T>(null, (((Letra<T>)(temporal.getDato())).getFrecuencia() + ((Letra<T>)(temporal.getSiguiente().getDato())).getFrecuencia()));
                letra.setIzquierda((Letra<T>)(temporal.getDato()));
                letra.setDerecha((Letra<T>)temporal.getSiguiente().getDato());
                sacar();
                sacar();
                agregar(letra);
                temporal = getFrente();
        }
        return this;
    }
}
