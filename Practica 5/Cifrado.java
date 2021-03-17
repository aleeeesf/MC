import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class Cifrado extends JFrame{
    
    private LaminaCifrado lamina = new LaminaCifrado();
    
    public Cifrado()
    {
        setBounds(100,100,630,650);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        setTitle("Cifrado AutomataCelular");
        add(lamina);
    }

    public static void main(String[] args) {
        //new Cifrado();
        //new Cifrador("hola");
        //System.out.println(Integer.toBinaryString('a'));

        String text = "hola que";
        int[] caracteres = text.chars().toArray();
        int [] binarios = new int[caracteres.length];


        for(int i = 0; i < caracteres.length; i++)
        {
            binarios[i] = Integer.parseInt(Integer.toBinaryString(caracteres[i]));
        }

        for(int i = 0; i < caracteres.length; i++)
        {
            binarios[i] = binarios[i] ^ 1;
        }

        String cifrado = "";
        for(int i = 0; i < binarios.length; i++)
        {
            cifrado += (char)AutomataCelular.NarioDecimal(binarios[i],2);
        }
        
        System.out.println(cifrado);


        int[][] m = {{2,3,4},{5,6,7}};
        int[] m2 = IntStream.range(0, m.length).map(i -> m[i][2]).toArray();
        System.out.println(m2[0]);


    }

}

class LaminaCifrado extends JPanel implements ActionListener
{
    private JLabel label1 = new JLabel("Introduce el texto a cifrar:");
    private JLabel label2 = new JLabel("Texto cifrado:");
    private JLabel label3 = new JLabel("Password:");

    private JTextArea introducirTexto = new JTextArea(5,10);
    private JTextArea salidaTexto = new JTextArea(5,10);

    private JTextField clave = new JTextField(30);
    
    private JButton boton = new JButton("Generar");
    private JButton MejorReglas = new JButton("Mejores reglas");
    
    private Border border = BorderFactory.createLineBorder(Color.BLACK);

    public LaminaCifrado()
    {
        setBounds(0,0,630,650);
        setLayout(null);

        
        introducirTexto.setLineWrap(true);
        introducirTexto.setBounds(20, 40, 575, 175);  
        introducirTexto.setBorder(BorderFactory.createCompoundBorder(border, 
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(introducirTexto);

        salidaTexto.setLineWrap(true);
        salidaTexto.setBounds(20, 350, 575, 175);  
        salidaTexto.setBorder(BorderFactory.createCompoundBorder(border, 
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(salidaTexto);


        boton.setBounds(110,270,175,25);
        boton.addActionListener(this);
        add(boton);
        clave.setBounds(300,270,175,25);
        clave.addActionListener(this);
        add(clave);
        MejorReglas.setBounds(20, 550, 175, 25);
        MejorReglas.addActionListener(this);
        add(MejorReglas);

        label1.setBounds(20, 10, 150, 25);
        add(label1);
        
        label2.setBounds(20, 320, 150, 25);
        add(label2);

        label3.setBounds(300, 240, 125,25);
        add(label3);
    }

    public void actionPerformed(ActionEvent e)   
    {
        

        if(e.getSource() == MejorReglas)
        {
            ExecutorService ex = Executors.newFixedThreadPool(5);
            ArrayList<MejorRegla> mr;
            int inicio = 0, fin = 51;

            for(int i = 0; i < 5; i++)
            {
                ex.submit(new ProbarAutomatas(inicio,fin));
                inicio = fin+1;
                fin += 51;
            }

            ex.shutdown();
            while(!ex.isTerminated());

            mr = ProbarAutomatas.Mejores;

            for(int i = 0; i < mr.size(); i++)
                System.out.println(mr.get(i));
        }
    }
}





class NucleoAutomata
{
    private int[][] Cells;
    private int[] data;
    private int[] data2;
    private int[] temp;

    /*  Combinaciones guarda en NArios la regla definida,
     *  es decir, almacena el siguiente estado de la célula    */
    private int[] Combinaciones; 
    private static final int k = 2;
    private long regla;
    
    //frontera: true si es cilíndrica y false si es frontera nula
    private static final boolean frontera = true; 

    //Vecindad es igual a 1 indicado en la práctica
    private static final int r = 1;

    private int ancho = 1024, alto;

    
    public NucleoAutomata()    
    {
        this.alto = 2000;
        this.Cells = new int[alto][ancho];
        this.data = new int[ancho];
        this.data2 = new int[ancho];
        this.temp = new int[ancho];

    }

    public NucleoAutomata(int generaciones_)
    {
        this.alto = generaciones_;
        this.Cells = new int[alto][ancho];
        this.data = new int[ancho];
        this.data2 = new int[ancho];
        this.temp = new int[ancho]; 
    }


    //Recarga los nuevos datos cuando pulsamos en ejecutar
    public void recargar(long regla_)
    {
        this.regla = regla_;
        Combinaciones = new int[vecindades()];     
   
        try {
            asignarRegla();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }    


    //Devuelve las células almacenadas en una matriz
    public int[][] getCells()
    {
        return Cells;
    }

    //Rellena la primera fila de células de número aleatorios
    public void rellenarCelulasAleatorias(ArrayList<Long> list, int k)
    {
        Long aux;
        for(int i = 0; i < ancho; i++)
        {
            aux = list.get(i)%k;
            data[i] = aux.intValue(); 
        }
    }

    public void resetear()
    {  
        Cells = new int[500][800];
    }


    public static int getEstadoActual()
    {
        return k;
    }

    //Núcleo del autómata celular
    public void calcularCelulas()
    {
        int comb;
        if(!frontera) //Si no hay frontera -> frontera nula
        {
            for(int i = 0; i < alto; i++)
            {
                for(int j = 0; j < ancho; j++)
                {
                    if(j == 0)
                    {
                        comb = data[j]*10+data[j+1];
                        comb = NarioDecimal(comb, k);                                             

                        data2[j] = Combinaciones[comb];
                    }
                    else if(j == ancho-1)
                    { 
                        comb = data[j-1]*100+data[j]*10;
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }
                    else
                    {
                        comb = data[j-1]*100+data[j]*10+data[j+1];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }                    
                }

                Cells[i] = data.clone();

                temp=data;
                data=data2;
                data2=temp;	

            }
        }

        else //Si hay frontera -> cilindrica
        {
            for(int i = 0; i < alto; i++)
            {
                for(int j = 0; j < ancho; j++)
                {
                    if(j == 0)
                    {
                        comb = data[ancho-1]*100+data[j]*10+data[j+1];
                        comb = NarioDecimal(comb, k);                                             

                        data2[j] = Combinaciones[comb];
                    }
                    else if(j == ancho-1)
                    {
                        comb = data[j-1]*100+data[j]*10+data[0];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }
                    else
                    {
                        comb = data[j-1]*100+data[j]*10+data[j+1];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }                    
                }              
                
                Cells[i] = data.clone();

                temp=data;  //Intercambiamos las filas
                data=data2;
                data2=temp;	
            }
        }        
    }

    //Devuelve un vector que almacena los estados siguientes que tendrán las celulas dependiendo de sus vecinos
    public int[] getCombinaciones()
    {
        return Combinaciones;
    }


    public static long maxReglas(int estado, int vecindad)
    {
        long max = (long)Math.pow(estado,(2*vecindad)+1);
        return (long)Math.pow(estado,max);
    }


    private static int vecindades()
    {
        //Hay k^(2*r + 1) vecindades dististas
        //111 , 110, 101...
        return (int)Math.pow(k,(2*r)+1);
    }


    public static int convertirNario(int N, int Nario)
    {
        return Integer.parseInt(Integer.toString(N,Nario));
    }

    //Convertidor de Decimal a N-ario en String
    public static String convertirNarioS(long N, int Nario)
    {
        return Long.toString(N,Nario);
    }


    //Convertidor de N-ario a Decimal
    public static int NarioDecimal(int N, int Nario)
    {
        String s = String.valueOf(N);
        return (int)Integer.parseInt(s, Nario);
    }


    //Alamacena la regla introducida en decimal a N-ario en el vector Combinaciones[] 
    private void asignarRegla() throws Exception
    {
        if(regla < reglasEvolucion())
        {
            String reglaNArio = convertirNarioS(regla, k);
            char digito;
            int i = 0, j = reglaNArio.length()-1;

            while (i < reglaNArio.length())
            {         
                digito = reglaNArio.charAt(j);     
                Combinaciones[i] = Integer.parseInt(String.valueOf(digito));
                
                j--;
                i++;
            }

            while(i < vecindades())
            {
                Combinaciones[i] = 0;
                i++;
            }   
        }

        else{
            throw new Exception("Regla no válida");
        }
    }
    

    //Combinaciones entre las distintas vecindades
    private static long reglasEvolucion()
    {        
        return (long)Math.pow(k,vecindades());
    }


    public double CalcularHammingMedia()
    {
        int diferencia;
        int[] Hamming = new int[this.alto]; 
        double media = 0.0;

        for(int i = 1; i < this.alto; i++)
        {
            diferencia = 0;

            for(int j = 0; j < this.ancho; j++)
            {
                if(Cells[i-1][j] != Cells[i][j]) diferencia++;
            }

            Hamming[i] = diferencia;
            media += Hamming[i];
        }

        media /= this.alto;

        return media;
    }

    public double CalcularEntropia()
    {
        double[] contador = new double[k];
        double[] Entropia = new double[this.alto];
        double media = 0.0;

        for(int i = 0; i < this.alto; i++)
        {
            for(int j = 0; j < this.ancho; j++)
            {
                contador[Cells[i][j]]++;                
            }

            for(int m = 0; m < this.k; m++)
            {
                contador[m] /= this.ancho; 
                Entropia[i] += -(contador[m]*CurvaEntropia.log(contador[m],k));                  
                contador[m] = 0;                
            }    

            media += Entropia[i];
        }

        media /= this.alto;

        return media;
    }

    public double CalcularEntropiaCentral()
    {
        double[] contador = new double[k];
        double entropiaCelula = 0.0;
         
        for(int i = 0; i < this.alto; i++)
        {
            contador[Cells[i][((this.ancho/2)-1)]]++;                
        }

        for(int m = 0; m < this.k; m++)
        {              
            contador[m] /= this.alto; 
            entropiaCelula += -(contador[m]*CurvaEntropia.log(contador[m],this.k));                                
        }

       return entropiaCelula;
    }

}


class ProbarAutomatas implements Runnable
{
    private static Object lock = new Object();
    private static int po = 0;
    public static ArrayList<MejorRegla> Mejores = new ArrayList<MejorRegla>();
    private ArrayList<Long> Aleatorios = new ArrayList<Long>();
    private static final int ancho = 1024;
    private static randomGenerator rg = new randomGenerator(ancho, 1);

    private NucleoAutomata AC = new NucleoAutomata();
    private int inicio, fin;

    public ProbarAutomatas(int inicio_, int fin_)
    {
        this.inicio = inicio_;
        this.fin = fin_;
        rg.Randu();
        Aleatorios = rg.getArrayOfLong();
    }

    public void run()
    {

        double hamming, entropia, entropiacentral;
        for(int i = inicio; i <= fin; i++)
        {
            AC.recargar(i);
            AC.rellenarCelulasAleatorias(Aleatorios, NucleoAutomata.getEstadoActual());
            AC.calcularCelulas();
            hamming = AC.CalcularHammingMedia();
            entropia = AC.CalcularEntropia();
            entropiacentral = AC.CalcularEntropiaCentral();

            System.out.println("regla: "+i+" h: "+hamming+" e: "+entropia+" ec: "+entropiacentral);
            if(hamming > 300 &&  entropia > 0.8 &&  entropiacentral > 0.8)
            {
                synchronized(lock)
                {
                    Mejores.add(new MejorRegla(i, hamming,entropia,entropiacentral));
                }
            }
        }

    }
}

class MejorRegla
{
    public double regla, entropia, hamming, entropiacentral;

    public MejorRegla(int regla_,double hamming_,double entropia_, double entropiacentral_)
    {
        this.regla = regla_;
        this.entropia = entropia_;
        this.hamming = hamming_;
        this.entropiacentral = entropiacentral_;
    }
    public String toString()
    {
        String msg = "Regla: "+regla+"hamming: "+hamming+"entropia: "+entropia+"entropia central:"+entropiacentral;
        return msg;
    }
}

class Cifrador
{
    private String text;
    private NucleoAutomata A = new NucleoAutomata();

    public Cifrador(String msg)//, int[] col
    {
        this.text = msg;
        cifrar();
    }

    public void cifrar()
    {
        int[] caracteres = text.chars().toArray();
        int [] binarios = new int[caracteres.length];


        for(int i = 0; i < caracteres.length; i++)
        {
            binarios[i] = Integer.parseInt(Integer.toBinaryString(caracteres[i]));
        }

        for(int i = 0; i < caracteres.length; i++)
        {
            binarios[i] = binarios[i] ^ 1;
        }

        String cifrado = "";
        for(int i = 0; i < binarios.length; i++)
        {
            cifrado += (char)binarios[i];
        }        

    }    

}