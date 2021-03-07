import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ca1DSim extends JFrame {
    
    public static AutomataCelular automata = new AutomataCelular(); 
    private LaminaSeleccion lamina = new LaminaSeleccion();

    public ca1DSim()
    {
        super();
        iniciar();
    }

    private void iniciar()
    {                     
        setTitle("Automata Celular 1D (Debug Mode)");
        setBounds(100,100,1200,700);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
        add(lamina); 
        add(automata);         
    }

    public static void main(String[] args)
    {     
        new ca1DSim();
        //System.out.println(AutomataCelular.convertirNarioS(151515,16));
    }
}

class ThreadAutomata implements Runnable
{
    private static Component componente;
    private int k, regla, generaciones;
    private boolean frontera, configuracion;
    ArrayList<Long> randList;

    public ThreadAutomata(Component componente_,int k_, int regla_, int generaciones_, boolean frontera_, boolean configuracion_, ArrayList<Long> randList_)
    {
        componente = componente_;
        this.k = k_;
        this.regla = regla_;
        this.generaciones = generaciones_;
        this.frontera = frontera_;
        this.configuracion = configuracion_;
        this.randList = randList_;


    }

    public void run()
    {                if(configuracion)   //Celula central activa
        {
            AutomataCelular.rellenarCelulas();
        }
        else    //Configuracion aleatoria
        {
            AutomataCelular.rellenarCelulasAleatorias(randList, k);
        }
        ca1DSim.automata.recargar(k,regla,generaciones,frontera); 
        ca1DSim.automata.paint(componente.getGraphics());
    }
}

class LaminaSeleccion extends JPanel implements ActionListener
{
    private static Thread t;

    private JTextField intrEstado = new JTextField(12);    
    private JTextField intrFuncion = new JTextField(12); 
    private JTextField intrGeneraciones = new JTextField(12); 

    private JLabel GeneradorLabel = new JLabel("Generador");
    private JLabel FronteraLabel = new JLabel("Frontera");
    private JLabel ConfiguracionLabel = new JLabel("Configuracion");
    private JLabel EstadosLabel = new JLabel("Estados por celula (k)");
    private JLabel TransicionLabel = new JLabel("Funcion de transicion");
    private JLabel GeneracionesLabel = new JLabel("Numero de generaciones");

    private String[] messageListGener = {"26.1a","26.1b","26.2","26.3","Combinado","FishmanMoore","FishmanMoore 2","Randu"};
    private String[] messageListFrontera = {"Frontera Nula","Cilindrica"};
    private String[] messageListConfiguracion = {"Celula central activa","Aleatoria"};

    private JComboBox SelectorGenerador = new JComboBox(messageListGener);
    private JComboBox SelectorFrontera = new JComboBox(messageListFrontera);
    private JComboBox SelectorConfiguracion = new JComboBox(messageListConfiguracion);

    private JButton ejecutar = new JButton("Ejecutar");  
    private JButton Detener = new JButton("Detener");  
    private JButton Reset = new JButton("Reset");    

    private ArrayList<Long> randList = new ArrayList<Long>();
    private randomGenerator rg;
    private int introducedNumber = AutomataCelular.ancho; //Ancho del gráfico
 
    public LaminaSeleccion()
    {
        setBounds(0,0,275,700);
        //setBackground(Color.blue);
        setLayout(null);

        //Izquierda Arriba
        GeneradorLabel.setBounds(75,25,150,25);
        add(GeneradorLabel);
        SelectorGenerador.setBounds(75,50,125,25);
        add(SelectorGenerador);


        FronteraLabel.setBounds(75,75,150,25);
        add(FronteraLabel);
        SelectorFrontera.setBounds(75, 100, 125, 25);
        add(SelectorFrontera);

        ConfiguracionLabel.setBounds(75,125,150,25);
        add(ConfiguracionLabel);
        SelectorConfiguracion.setBounds(75, 150, 125, 25);
        add(SelectorConfiguracion);


        EstadosLabel.setBounds(75,200,150,25);
        add(EstadosLabel);
        intrEstado.setBounds(75,225,150,25);
        add(intrEstado);


        TransicionLabel.setBounds(75,250,150,25);
        add(TransicionLabel);
        intrFuncion.setBounds(75,275,150,25);
        add(intrFuncion);

        GeneracionesLabel.setBounds(75,300,150,25);
        add(GeneracionesLabel);
        intrGeneraciones.setBounds(75,325,150,25);
        add(intrGeneraciones);


        //Izquierda Abajo
        Reset.setBounds(85,550,100,25);
        add(Reset);

        ejecutar.setBounds(25,500,100,25);
        add(ejecutar);

        Detener.setBounds(150,500,100,25);
        add(Detener);

        SelectorGenerador.addActionListener(this);
        SelectorFrontera.addActionListener(this);
        ejecutar.addActionListener(this);
        Reset.addActionListener(this);
        Detener.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ejecutar)
        {
            String msg = (String) SelectorGenerador.getSelectedItem(),
                   msg_frontera = (String) SelectorFrontera.getSelectedItem(),
                   msg_configuracion = (String) SelectorConfiguracion.getSelectedItem(),
                   msg_estados = intrEstado.getText(),
                   msg_transicion = intrFuncion.getText(),
                   msg_generaciones = intrGeneraciones.getText();
    
            //Comprobaremos que no haya campos vacíos
            if(msg_estados.isEmpty() || msg_transicion.isEmpty() || msg_generaciones.isEmpty())
            {                
                JOptionPane.showMessageDialog(null, "Rellene todos los campos antes de ejecutar",
                "Alerta", JOptionPane.WARNING_MESSAGE);
            }

            else{
                try{

                    boolean frontera = false, configuracion = true;
                    int estados = Integer.valueOf(intrEstado.getText()),
                     transicion = Integer.valueOf(intrFuncion.getText()),
                         generaciones = Integer.valueOf(intrGeneraciones.getText());

                         
                    if(transicion >= AutomataCelular.maxReglas(estados,1))
                        JOptionPane.showMessageDialog(null, "Regla no valida para el estado introducido",
                        "Alerta", JOptionPane.ERROR_MESSAGE);
                    
                    else if(generaciones > 500)
                        JOptionPane.showMessageDialog(null, "El maximo de generaciones es 500",
                        "Alerta", JOptionPane.ERROR_MESSAGE);

                    else if(estados < 2)
                    {
                        JOptionPane.showMessageDialog(null, "El minimo de estados es 2",
                        "Alerta", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    else
                    {
                        switch(msg_frontera)
                        {
                            case "Frontera Nula": frontera = false;
                            break;
    
                            case "Cilindrica":  frontera = true;
                            break;
                        }
    
                        switch(msg_configuracion)
                        {
                            case "Celula central activa": configuracion = true;
                            break;
    
                            case "Aleatoria": configuracion = false;
                            break;
                        }
    
    
                        switch(msg)
                        {
                            case "26.1a":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.x261a();
                                            randList = rg.getArrayOfLong();
                                            break;
    
                            case "26.1b":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.x261b();
                                            randList = rg.getArrayOfLong();
                                            break;
    
                            case "26.2":    rg = new randomGenerator(introducedNumber, 1);
                                            rg.x262();
                                            randList = rg.getArrayOfLong();
                                            break;
    
                            case "26.3":    rg = new randomGenerator(introducedNumber, 1);
                                            rg.x263();
                                            randList = rg.getArrayOfLong();
                                            break;
    
                            case "Combinado":   rg = new randomGenerator(introducedNumber, 1);
                                                rg.combinado();
                                                randList = rg.getArrayOfLong();
                                                break;
    
    
                            case "FishmanMoore":    rg = new randomGenerator(introducedNumber, 1);
                                                    rg.FishmanMoore();
                                                    randList = rg.getArrayOfLong();
                                                    break;
    
                            case "FishmanMoore 2":   rg = new randomGenerator(introducedNumber, 1);
                                                    rg.FishmanMoore2();
                                                    randList = rg.getArrayOfLong();
                                                    break;
    
                            case "Randu":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.Randu();
                                            randList = rg.getArrayOfLong();
                                            break;
                        }
    
                        Runnable r = new ThreadAutomata(ca1DSim.automata,estados,transicion,generaciones,frontera,configuracion,randList);
                        t = new Thread(r);
                        t.start();

                    }
                    

                }catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Caracter invalido introducido. Compruebe los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                }                   
            }            
        }

        if(e.getSource() == Reset)
        {
            ca1DSim.automata.detener();
            ca1DSim.automata.resetear();
            ca1DSim.automata.paint(ca1DSim.automata.getGraphics());  
        }

        if(e.getSource() == Detener)
        {
            ca1DSim.automata.detener();    
        }
    }
}


class AutomataCelular extends Canvas
{
    //Combinaciones guarda en NArios la regla definida  
    private static int[] data = new int[800];
    private static int[] data2 = new int[800];
    private static int[] temp;

    private static int[] Combinaciones;
    private static int k, regla, generaciones;
    private static boolean frontera, blanco, parar; 

    //Vecindad es igual a 1 indicado en la práctica
    private static final int r = 1;

    public static final int ancho = 800, alto = 500;


    
    public AutomataCelular()    
    {
        setBounds(325,50,800,500);        
        setBackground(Color.white);
        blanco = true;
    }


    public static void recargar(int k_, int regla_, int generaciones_, boolean frontera_)
    {
        k = k_;
        regla = regla_;
        generaciones = generaciones_;
        frontera = frontera_;
        parar = false;
        blanco = false;
        Combinaciones = new int[vecindades()];     
   
        try {
            asignarRegla();
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }
    

    public void paint(Graphics g)
    {  
        super.paint(g);
        
        if(!blanco)
        {
            calcularCelulas(g);
        }

        else{
            setBackground(Color.white);
        }
    }

    public static void rellenarCelulasAleatorias(ArrayList<Long> list, int k)
    {
        Long aux;
        for(int i = 0; i < ancho; i++)
        {
            aux = list.get(i)%k;
            data[i] = aux.intValue(); 
        }
    }

    public static void rellenarCelulas()
    {
        for(int i = 0; i < ancho; i++)
        {
            data[i] = 0;
        }

        data[ancho/2] = 1;
    }

    public static void detener()
    {
        parar = true;
    }


    public static void resetear()
    {
        blanco = true;
    }


    private static void calcularCelulas(Graphics g)
    {
        int comb,value;

        if(!frontera) //Si no hay frontera -> frontera nula
        {
            for(int i = 0; i < generaciones && !parar; i++)
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
                    //System.out.print(data2[j]+" ");
                }
                //System.out.println();
                
                for(int j = 0; j < ancho; j++)
                {
                    System.out.print(data[j]+" ");
                }
                System.out.println();

                
                /*
                for(int k=0; k<800; k++)
                    if(data2[k]==0){g.setColor(Color.magenta);g.drawOval(k, i, 1, 1);}
                    else{g.setColor(Color.yellow);g.drawOval(k, i, 1, 1);} */ 
                for(int k=0; k<ancho; k++)
                {
                    value = data2[k];
                    switch(value)
                    {
                        case 0: g.setColor(Color.yellow);g.drawOval(k, i, 1, 1);
                        break;
                        case 1: g.setColor(Color.red);g.drawOval(k, i, 1, 1);
                        break;
                        case 2: g.setColor(Color.blue);g.drawOval(k, i, 1, 1);
                        break;
                        case 3: g.setColor(Color.black);g.drawOval(k, i, 1, 1);
                        break;
                        case 4: g.setColor(Color.cyan);g.drawOval(k, i, 1, 1);
                        break;
                    }
                }

                pause(25);

                temp=data;
                data=data2;
                data2=temp;	
            }
        }
        else //Si hay frontera -> cilindrica
        {
            for(int i = 0; i < generaciones && !parar; i++)
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
                    //System.out.print(data2[j]+" ");
                }
                //System.out.println();
                /*
                for(int j = 0; j < 10; j++)
                {
                    System.out.print(data2[j]+" ");
                }
                System.out.println();
                */
                for(int k=0; k<ancho; k++)
                {
                    value = data2[k];
                    switch(value)
                    {
                        case 0: g.setColor(Color.magenta);g.drawOval(k, i, 1, 1);
                        break;
                        case 1: g.setColor(Color.yellow);g.drawOval(k, i, 1, 1);
                        break;
                        case 2: g.setColor(Color.green);g.drawOval(k, i, 1, 1);
                        break;
                        case 3: g.setColor(Color.white);g.drawOval(k, i, 1, 1);
                        break;
                        case 4: g.setColor(Color.blue);g.drawOval(k, i, 1, 1);
                        break;
                    }
                }                
                
                pause(25);

                temp=data;
                data=data2;
                data2=temp;	
            }
        }
    }

    public static int[] getCombinaciones()
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
    {/*
        int ret = 0, factor = 1;
        while (N > 0) {
            ret += N % Nario * factor;
            N /= Nario;
            factor *= 10;
        }
        return ret;
        */
        return Integer.parseInt(Integer.toString(N,Nario));
    }

    public static String convertirNarioS(int N, int Nario)
    {
        return Integer.toString(N,Nario);
    }


    public static int NarioDecimal(int N, int Nario)
    {
        String s = String.valueOf(N);
        return (int)Integer.parseInt(s, Nario);
    }
        

    private static void pause(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static void asignarRegla() throws Exception
    {
        if(regla < reglasEvolucion())
        {/*
            int reglaNArio = convertirNario(regla, k);
            int ultDigito, i = 0;

            while (reglaNArio != 0)
            {
                ultDigito = reglaNArio%10;
                reglaNArio /= 10;
                Combinaciones[i] = ultDigito;
                i++;
            }

            while(i < vecindades())
            {
                Combinaciones[i] = 0;
                i++;
            } 
            */
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
    private static int reglasEvolucion()
    {        
        return (int)Math.pow(k,vecindades());
    }
}
