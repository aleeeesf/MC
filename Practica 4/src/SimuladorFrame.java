import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SimuladorFrame extends JFrame {
    
    public SimuladorFrame()
    {
        super();
        iniciar();
    }

    private void iniciar()
    {       
        LaminaSeleccion1 lamina = new LaminaSeleccion1();
        ImageIcon icon = new ImageIcon("shuffle.png");  
        setIconImage(icon.getImage());              
        setTitle("Generador Num.Aleatorios");
        setBounds(200,200,300,300);
        setResizable(true);
        setLayout(null);
        
        add(lamina);  
    }
}

class LaminaSeleccion1 extends JPanel implements ActionListener
{
    private JTextField intrNumeros = new JTextField(12);    
    private JTextField intrSemilla = new JTextField(12); 

    private JLabel intrNumeroLabel = new JLabel("Numeros a generar");
    private JLabel intrSemillaLabel = new JLabel("Semilla");

    private String[] messageList = {"26.1a","26.1b","26.2","26.3","Combinado","FishmanMoore","FishmanMoore 2","Randu"};
    private JComboBox Selector = new JComboBox(messageList);

    private JButton boton = new JButton("Ejecutar");     

    private ArrayList<Double> randList = new ArrayList<Double>();
    private randomGenerator rg;



    public LaminaSeleccion1()
    {
        setBounds(0,0,300,250);
        setLayout(null);

        intrNumeroLabel.setBounds(75,25,150,25);
        add(intrNumeroLabel);

        intrNumeros.setBounds(75,50,150,25);
        add(intrNumeros);

        intrSemillaLabel.setBounds(75,75,150,25);
        add(intrSemillaLabel);

        intrSemilla.setBounds(75,100,150,25);
        add(intrSemilla);

        Selector.setBounds(75,150,100,25);
        add(Selector);

        boton.setBounds(75,200,100,25);
        add(boton);


        Selector.addActionListener(this);
        boton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == boton)
        {
            String msg = (String) Selector.getSelectedItem();
            String numbers = intrNumeros.getText();
            long introducedNumber = Long.parseLong(numbers);
            numbers = intrSemilla.getText();
            long indroducedSeed = Long.parseLong(numbers);           
            
            
            switch(msg)
            {
                case "26.1a":   rg = new randomGenerator(introducedNumber, indroducedSeed);
                                rg.x261a();
                                randList = rg.getArray();
                                break;

                case "26.1b":   rg = new randomGenerator(introducedNumber, indroducedSeed);
                                rg.x261b();
                                randList = rg.getArray();
                                break;

                case "26.2":    rg = new randomGenerator(introducedNumber, indroducedSeed);
                                rg.x262();
                                randList = rg.getArray();
                                break;

                case "26.3":    rg = new randomGenerator(introducedNumber, indroducedSeed);
                                rg.x263();
                                randList = rg.getArray();
                                break;

                case "Combinado":   rg = new randomGenerator(introducedNumber, indroducedSeed);
                                    rg.combinado();
                                    randList = rg.getArray();
                                    break;

                case "FishmanMoore":    rg = new randomGenerator(introducedNumber, indroducedSeed);
                                        rg.FishmanMoore();
                                        randList = rg.getArray();
                                        break;

                case "FishmanMoore 2":   rg = new randomGenerator(introducedNumber, indroducedSeed);
                                         rg.FishmanMoore2();
                                         randList = rg.getArray();
                                         break;

                case "Randu":   rg = new randomGenerator(introducedNumber, indroducedSeed);
                                rg.Randu();
                                randList = rg.getArray();
                                break;
            }

            new Grafico(randList);               
        }
    }
}




//Frame donde se muestra el Gráfico
class Grafico extends JFrame
{
    public Grafico(ArrayList<Double> A)
    {
        super();
        setTitle("Grafico");
        add(new Dibujo(A));
        setBounds(0,0,700,700);
        setVisible(true);
        setResizable(false);
    }
}

//Dibuja los puntos por pantalla
class Dibujo extends Canvas
{
    private ArrayList<Double> points = new ArrayList<Double>();

    public Dibujo(ArrayList<Double> A)
    {
        this.points = A;
        setBackground(Color.white);
        setBounds(0,0,600,600);
    }

    public void paint(Graphics g)
    {
        double x,y;

        for(int i = 0; i < points.size()-1; i += 2)
        {
            x = points.get(i) * 600; 
            y =  points.get(i+1) * 600;       
             
            //System.out.println(x);   
            g.drawOval((int)x,(int)y,4,4);
        }
    }
}