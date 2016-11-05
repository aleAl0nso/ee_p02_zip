import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class main<T> {
    
    private final JButton comprimir = new JButton("Comprimir");
    private final JButton examinar = new JButton("Examinar");
    private final JButton guardar = new JButton("Guardar");
   
    private final JButton salir = new JButton("Salir");
	
    private final JLabel tamaño = new JLabel();
    private final JLabel caracteres = new JLabel();
    private String recorrido="";
    private String textoArchivoOriginal;
    private String repeticionesPorLetra;
    private String[] letras;
    private File documento;
    private File txz;
    private Letra<T> raiz;
    
    
    main(){
        Administrador admin = new Administrador();
        examinar.addActionListener(admin);
        salir.addActionListener(admin);
        guardar.addActionListener(admin);
         comprimir.addActionListener(admin);
        crearMenuPrincipal();
    }
    
    private class Administrador implements ActionListener{
        private JFileChooser escoger = new JFileChooser();
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==examinar){
                int resultado = escoger.showOpenDialog(null);
                if(resultado==JFileChooser.APPROVE_OPTION){
                    if(escoger.getSelectedFile().getName().substring(escoger.getSelectedFile().getName().length()-4).equals(".txt")){
                        documento = escoger.getSelectedFile();
                        leerArchivo(documento);
                        tamaño.setText("Peso: "+(documento.length())+" bytes");
                        caracteres.setText("Caracteres: "+textoArchivoOriginal.length());
                        comprimir.setVisible(true);
                    }else{
                        JOptionPane.showMessageDialog(null,"El archivo seleccionado no es un archivo .txt","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }else if(e.getSource()==comprimir){
                if(documento==null){
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado un archivo","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    comprimir.setVisible(false);
                    guardar.setVisible(true);
                    comprimir();
                    ventanaDetalles();
                }
            }else if(e.getSource()==guardar){
                if(txz!=null){
                    JOptionPane.showMessageDialog(null, "Se guardo el archivo");
                }
               
                } else if(e.getSource()==salir){
                    System.exit(0);
            }
        }
        
    }
    
    public void crearMenuPrincipal(){
        JFrame principal = new JFrame("Elegir archivo");
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        principal.setSize(350, 180);
        principal.setLocation(500,200);
        principal.setLayout(new BorderLayout());
        JPanel panelCentral = new JPanel(new GridLayout(3,1));
        JPanel panelArriba = new JPanel(new FlowLayout());
        panelArriba.add(new JLabel("Seleccionar el archivo"));
        panelArriba.add(examinar);
        panelArriba.add(salir);
        panelCentral.add(tamaño);
        panelCentral.add(caracteres);
        comprimir.setVisible(false);
        panelCentral.add(comprimir);
        principal.add(panelArriba,BorderLayout.NORTH);
        principal.add(panelCentral,BorderLayout.CENTER);
        guardar.setVisible(false);
        principal.add(guardar,BorderLayout.SOUTH);
        principal.setVisible(true);
    }
    
    public void ventanaDetalles(){
        JFrame ventana = new JFrame("Detalles de compresion");
        ventana.setLayout(new BorderLayout());
        JPanel respuestas = new JPanel(new GridLayout(2, 1));
        JLabel tamaño = new JLabel("El documento comprimido tiene un tamaño de: "+txz.length()+" bytes");
        JTextArea repeticiones = new JTextArea();
        repeticiones.setBackground(ventana.getBackground());
        repeticiones.setEditable(false);
        repeticiones.setBorder(null);
        JTextArea clave = new JTextArea();
        clave.setBackground(ventana.getBackground());
        clave.setEditable(false);
        clave.setBorder(null);
        ventana.add(tamaño,BorderLayout.NORTH);
        ventana.setSize(350,450);
        ventana.setLocation(50, 50);
        repeticiones.setText("Tabla de frecuencias:\n"+repeticionesPorLetra);
        JScrollPane barra = new JScrollPane(repeticiones);
        JScrollPane barra2 = new JScrollPane(clave);
        clave.setText("Clave para seguir el arbol:\nNota: 0 es izquierda y 1 es derecha\n"+imprimirClave());
        respuestas.add(barra);
        respuestas.add(barra2);
        ventana.add(respuestas,BorderLayout.CENTER);
        ventana.setVisible(true);
    }
    
    public void leerArchivo(File archivo){
        String texto="";
        try{
            FileReader lector = new FileReader(archivo);
            int valor = lector.read();
            while(valor!=-1){
                texto = texto+(char)valor;
                valor=lector.read();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se pudo leer","Error",JOptionPane.ERROR_MESSAGE);
        }
        textoArchivoOriginal = texto;
    }
    
    public int apareceVeces(String s, char c){
        int cont =0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == c)
                cont ++;
        }
        return cont;
    }
    
    public String imprimirClave(){
        String resp="";
        for(int i=0;i<letras.length;i++){
            resp=resp+letras[i]+"\n";
        }
        return resp;
    }
    
    public String[] separarClave(){
        ArrayList<String> resp = new ArrayList<>();
        String temp="";
        for(int i=0;i<recorrido.length();i++){
            if(Character.isLetter(recorrido.charAt(i)) || recorrido.charAt(i)==' '){
                temp=temp+"-"+recorrido.charAt(i);
                resp.add(temp);
                temp="";
            }else{
                temp=temp+recorrido.charAt(i);
            }
        }
        String [] resp2 = new String[resp.size()];
        for(int i=0;i<resp2.length;i++){
            resp2[i]=resp.get(i);
        }
        return resp2;
    }
    
    public void comprimir(){
        ColaPrioridad<T> cola = new ColaPrioridad<T>();
        String ya = "";
        for(int i=0;i<textoArchivoOriginal.length();i++){
            char letra = textoArchivoOriginal.charAt(i);
            if(!((letra+"").equals("\n"))){
                if(!(ya.contains(letra+""))){
                    int veces= apareceVeces(textoArchivoOriginal,letra);
                    
                    Letra<T> l = new Letra<T>(letra+"", veces);
                    cola.agregar(l);
                    ya=ya+letra;
                }
            }
        }
        repeticionesPorLetra=cola.toString();
        raiz = (Letra<T>) cola.pasarAArbol().getFrente().getDato();
        generarRecorridos(this.raiz);
        letras = separarClave();
        this.txz=crearArchivoNuevo();
    }
    
    public File crearArchivoNuevo(){
        File file = new File(documento.getName().substring(0,documento.getName().length()-4)+".txz");
        String textoNuevo = "";
        for(int i=0;i<textoArchivoOriginal.length();i++){
            textoNuevo=textoNuevo+hacerSustitucion(textoArchivoOriginal.charAt(i)+"");
        }
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(textoNuevo);
            fw.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se pudo crear el archivo","Error",JOptionPane.ERROR_MESSAGE);
        }
        txz = file;
        return file;
    }
    
    public String hacerSustitucion(String letra){
        for(int i=0;i<this.letras.length;i++){
            if(letras[i].contains(letra))
                return letras[i].substring(0, letras[i].length()-2);
        }
        return "";
    }
    
    
    
    
    public void generarRecorridos(Letra<T> nodoA){
        while(nodoA!=null){
            nodoA=buscarLetra(nodoA);
        }
    }
    
    public Letra<T> buscarLetra(Letra<T> nodoA){
        if(nodoA!=null){
            if(nodoA.getLetra()!=null && nodoA.getDerecha()==null && nodoA.getIzquierda()==null){
                recorrido=recorrido+nodoA.getLetra();
                nodoA=null;
                return nodoA;
            }else if(nodoA.getLetra()==null && nodoA.getDerecha()==null && nodoA.getIzquierda()==null){
                nodoA=null;
                return nodoA;
            }else{
                if(nodoA.getIzquierda()!=null){
                    recorrido=recorrido+0;
                    nodoA.setIzquierda(buscarLetra(nodoA.getIzquierda()));
                }else if(nodoA.getDerecha()!=null){
                    recorrido=recorrido+1;
                    nodoA.setDerecha(buscarLetra(nodoA.getDerecha()));
                }
                if(nodoA.getLetra()==null && nodoA.getDerecha()==null && nodoA.getIzquierda()==null){
                    nodoA=null;
                    return nodoA;
                }
            }
        }
        return nodoA;
    }
    
    
    
    public static void main(String[] args) {
        main main = new main();
    }
    
}
