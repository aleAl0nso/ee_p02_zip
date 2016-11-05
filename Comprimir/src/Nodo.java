


public class Nodo<T>
{
   private T dato;
   private Nodo<T> siguiente;
   
   Nodo(Letra<T> letra,Nodo<T> s){
       dato=(T) letra;
       siguiente=s;
   }
   public Nodo(Letra<T> letra, Object s) {
	// TODO Auto-generated constructor stub
}

public T getDato(){
       return dato;
   }
   public void setDato(T o){
       dato=o;
   }
   public Nodo<T> getSiguiente(){
       return siguiente;
   }
   public void setSiguiente(Nodo<T> siguiente){
       this.siguiente=siguiente;
   }
}