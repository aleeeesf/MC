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

        LaminaSeleccion laminaB = new LaminaSeleccion();
                      
        setTitle("Simulador");
        setBounds(200,200,300,300);
        setResizable(true);
        setLayout(null);

        //add(laminaI);
        add(laminaB);  
    }
    public static void main(String[] args)
    {
        //new SimuladorFrame();
        ArrayList<Double> A = new ArrayList<Double>();

        A.add(0.89);
        A.add(0.78);
        A.add(0.60);
        A.add(0.17);
        A.add(0.23);
        A.add(0.36);
        A.add(0.47);
        A.add(0.02);
        A.add(0.14);
        A.add(0.78);
        A.add(0.66);
        A.add(0.22);
        A.add(0.33);



        new Grafico(A);
    }
}

class LaminaSeleccion extends JPanel implements ActionListener
{
    private JTextField Introducir = new JTextField(12);    
    private String[] messageList = {"26.1a","26.1b","26.2"};
    private JComboBox Selector = new JComboBox(messageList);

    private JButton boton = new JButton("Ejecutar");     

    public LaminaSeleccion()
    {
        setBounds(0,0,300,250);
        setLayout(null);

        Introducir.setBounds(75,50,150,25);
        add(Introducir);

        Selector.setBounds(75,100,100,25);
        add(Selector);

        boton.setBounds(75,150,100,25);
        add(boton);


        Selector.addActionListener(this);
        boton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == boton)
        {
            String msg = (String) Selector.getSelectedItem();
            String numbers = Introducir.getText();
            int introducedNumber = Integer.parseInt(numbers);
            ArrayList<Double> randList = new ArrayList<Double>();
            randomGenerator rg;
            
            switch(msg)
            {
                case "26.1a": rg = new randomGenerator(introducedNumber, 1);
                                rg.x261a();
                                randList = rg.getArray();
                                break;

                case "26.1b": rg = new randomGenerator(introducedNumber, 1);
                                rg.x261b();
                                randList = rg.getArray();
                                break;
            }


            new Grafico(randList);
               
        }
    }
}

class Grafico extends JFrame
{
    public Grafico(ArrayList<Double> A)
    {
        super();
        setTitle("Grafico");
        add(new Dibujo(A));
        setBounds(0,0,700,500);
        setVisible(true);
        setResizable(false);
    }
}

class Dibujo extends Canvas
{
    private ArrayList<Double> points = new ArrayList<Double>();

    public Dibujo(ArrayList<Double> A)
    {
        this.points = A;
        setBackground(Color.white);
    }

    public void paint(Graphics g)
    {
        double x,y;

        for(Double p: points)
        {
            x = p * 700;
            y = p * 500;
            System.out.println(x+" "+y);
            g.drawOval((int)x,(int)y,4,4);
        }
    }
}