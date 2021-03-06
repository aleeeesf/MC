import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ca1DSim extends JFrame {
    
    public 
    public ca1DSim()
    {
        super();
        iniciar();
    }

    private void iniciar()
    {       

        LaminaSeleccion lamina = new LaminaSeleccion();
                      
        setTitle("Generador Num.Aleatorios");
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
        
        //System.out.println(Integer.valueOf("1001",2));
        //String s;
        //s = Integer.toString(1) + Integer.toString(2) + Integer.toString(3);
        //System.out.println(s);
        new ca1DSim();
        //System.out.println(Integer.parseInt(Integer.toString(5,3)));
        //System.out.println(AutomataCelular.convertirNario(255,2));
        //System.out.println(AutomataCelular.vecindades(2,1));
        //AutomataCelular a = new AutomataCelular(2,102,20,false);
        //System.out.println(AutomataCelular.convertirNario(14141, 3));

    }
}

class LaminaSeleccion extends JPanel implements ActionListener
{
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

    private ArrayList<Double> randList = new ArrayList<Double>();
    private randomGenerator rg;

    private AutomataCelular grafico = new AutomataCelular();

    public LaminaSeleccion()
    {
        setBounds(0,0,1200,700);
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

        //Derecha
        add(grafico); 


        SelectorGenerador.addActionListener(this);
        SelectorFrontera.addActionListener(this);
        ejecutar.addActionListener(this);
        Reset.addActionListener(this);
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
    
    /*
                        switch(msg)
                        {
                            case "26.1a":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.x261a();
                                            randList = rg.getArray();
                                            break;
    
                            case "26.1b":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.x261b();
                                            randList = rg.getArray();
                                            break;
    
                            case "26.2":    rg = new randomGenerator(introducedNumber, 1);
                                            rg.x262();
                                            randList = rg.getArray();
                                            break;
    
                            case "26.3":    rg = new randomGenerator(introducedNumber, 1);
                                            rg.x263();
                                            randList = rg.getArray();
                                            break;
    
                            case "Combinado":   rg = new randomGenerator(introducedNumber, 1);
                                                rg.combinado();
                                                randList = rg.getArray();
                                                break;
    
    
                            case "FishmanMoore":    rg = new randomGenerator(introducedNumber, 1);
                                                    rg.FishmanMoore();
                                                    randList = rg.getArray();
                                                    break;
    
                            case "FishmanMoore 2":   rg = new randomGenerator(introducedNumber, 1);
                                                    rg.FishmanMoore2();
                                                    randList = rg.getArray();
                                                    break;
    
                            case "Randu":   rg = new randomGenerator(introducedNumber, 1);
                                            rg.Randu();
                                            randList = rg.getArray();
                                            break;
                        }
    
    */
                        grafico.recargar(estados,transicion,generaciones,frontera,configuracion); 
                        grafico.repaint();
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
            grafico = new AutomataCelular();
            grafico.setBounds(300,50,800,500);
            add(grafico); 
            grafico.repaint();
            
        }

    }
}


class AutomataCelular extends Canvas
{
    //Combinaciones guarda en NArios la regla definida  
    private int[] data = new int[800];
    private int[] data2 = new int[800];
    private int[] temp;

    private int[] Combinaciones;
    private int k, regla, generaciones;
    private boolean frontera, configuracion;

    private boolean blanco;

    //Vecindad es igual a 1 indicado en la práctica
    private final int r = 1;

    public AutomataCelular()    
    {
        setBounds(325,50,800,500);        
        setBackground(Color.white);
        blanco = true;
    }

    public void recargar(int k_, int regla_, int generaciones_, boolean frontera_, boolean configuracion_)
    {
        this.k = k_;
        this.regla = regla_;
        this.generaciones = generaciones_;
        this.frontera = frontera_;
        this.configuracion = configuracion_;
        blanco = false;
        Combinaciones = new int[vecindades()];        
         
        asignarRegla();
    }
    
    public void paint(Graphics g)
    {  
        super.paint(g);

        
        if(!blanco)
        {
            calcularCelulas(g);
            //Image img = calcularCelulas();
            //g.drawImage(img, 20,20,this);  

        }     
    }
    
    private static void pause(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void calcularCelulas(Graphics g)
    {
        //BufferedImage bufferedImage = new BufferedImage(800,500, BufferedImage.TYPE_INT_RGB);
        //Graphics g = bufferedImage.getGraphics();

        int comb;

        for(int i = 0; i < 800; i++)
        {
            data[i] = 0;
        }
        data[400] = 1;
        

        
        if(!frontera) //Si no hay frontera -> frontera nula
        {
            for(int i = 0; i < generaciones; i++)
            {
                for(int j = 0; j < 800; j++)
                {
                    if(j == 0)
                    {
                        comb = data[j]*10+data[j+1];
                        comb = NarioDecimal(comb, k);                                             

                        data2[j] = Combinaciones[comb];


                    }
                    else if(j == 800-1)
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
                /*
                for(int j = 0; j < 10; j++)
                {
                    System.out.print(data2[j]+" ");
                }
                System.out.println();
                */
                for(int k=0; k<800; k++)
                    if(data2[k]==0){g.setColor(Color.BLUE);g.drawOval(k, i, 1, 1);}
                    else{g.setColor(Color.YELLOW);g.drawOval(k, i, 1, 1);}  
                pause(25);

                temp=data;
                data=data2;
                data2=temp;	
            }
        }
        else //Si hay frontera -> cilindrica
        {
            for(int i = 0; i < generaciones; i++)
            {
                for(int j = 0; j < 800; j++)
                {
                    if(j == 0)
                    {
                        comb = data[799]*100 + data[j]*10 + data[j+1];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }
                    else if(j == 800-1)
                    {
                        comb = data[j-1]*100 + data[j]*10 +data[0];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }
                    else
                    {
                        comb = data[j-1]*100 + data[j]*10 + data[j+1];
                        comb = NarioDecimal(comb, k);
                        data2[j] = Combinaciones[comb];
                    }

                    for(int k=0; k<data.length; k++)
                        if(data2[k]==0){g.setColor(Color.BLUE);g.drawOval(k, i, 1, 1);}
                        else{g.setColor(Color.YELLOW);g.drawOval(k, i, 1, 1);}
                    

                    temp=data;
                    data=data2;
                    data2=temp;
                }	
            }
/*
            for(int j = 0; j < 10; j++)
            {
                System.out.print(data2[j]+" ");
            }
            System.out.println();
*/
                    //se pinta la actual configuración

        }

       //return bufferedImage;
    }

    public int[] getCombinaciones()
    {
        return Combinaciones;
    }

    private void asignarRegla()
    {
        if(regla < reglasEvolucion())
        {
            int reglaNArio = convertirNario(regla, k),
                ultDigito, i = 0;

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
        }

        else{
            //Regla no valida, lanzar excepcion
        }
    }
    
    //Combinaciones entre las distintas vecindades
    private int reglasEvolucion()
    {        
        return (int)Math.pow(k,vecindades());
    }

    public static long maxReglas(int estado, int vecindad)
    {
        long max = (long)Math.pow(estado,(2*vecindad)+1);
        return (long)Math.pow(estado,max);
    }

    private int vecindades()
    {
        //Hay k^(2*r + 1) vecindades dististas
        //111 , 110, 101...
        return (int)Math.pow(k,(2*r)+1);
    }

    public static int convertirNario(int N, int Nario)
    {
        int ret = 0, factor = 1;
        while (N > 0) {
            ret += N % Nario * factor;
            N /= Nario;
            factor *= 10;
        }
        return ret;
        //return Integer.parseInt(Integer.toString(N,Nario));
    }

    public static int NarioDecimal(int N, int Nario)
    {
        String s = String.valueOf(N);
        return (int)Integer.parseInt(s, Nario);
    }
}
/*
class Dibujar extends Canvas
{
    Dibujar()
    {

    }

    void paint()
    {}
}
*/