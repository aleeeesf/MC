import java.awt.*;
import java.io.*;
import java.util.Random;
import java.lang.Math;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CurvaEntropia extends JFrame
{
    private int[][] Celulas = new int[AutomataCelular.alto][AutomataCelular.ancho];
    private double[] Entropia = new double[AutomataCelular.alto];
    private GraficoEntropia grafico = new GraficoEntropia();


    private JLabel TextoCurva = new JLabel("CURVA ENTROPIA");
    private JLabel TextoDistancia = new JLabel("Entropia");
    private JLabel TextoGeneraciones = new JLabel("Numero de generaciones");

    private JLabel yCinco = new JLabel("1");
    private JLabel yCuatro = new JLabel("0.8");
    private JLabel yTres = new JLabel("0.6");
    private JLabel yDos = new JLabel("0.4");
    private JLabel yUno = new JLabel("0.2");


    private JLabel xCinco = new JLabel("500");
    private JLabel xCuatro = new JLabel("400");
    private JLabel xTres = new JLabel("300");
    private JLabel xDos = new JLabel("200");
    private JLabel xUno = new JLabel("100");


    public CurvaEntropia(int[][] C, int k)
    {
        this.Celulas = C;

        setBounds(0,0,1225,800);
        setVisible(true);
        setLayout(null);

        
        //Textos que se presentan en el frame
        TextoCurva.setBounds(550,25,150,25);
        add(TextoCurva);

        TextoDistancia.setBounds(40,30,150,25);
        add(TextoDistancia);

        TextoGeneraciones.setBounds(525,725,150,25);
        add(TextoGeneraciones);
        

        //Números de la y
        yCinco.setBounds(60,70,25,25);
        add(yCinco);

        yCuatro.setBounds(60,185,25,25);
        add(yCuatro);

        yTres.setBounds(60,300,25,25);
        add(yTres);

        yDos.setBounds(60,420,25,25);
        add(yDos);

        yUno.setBounds(60,540,25,25);
        add(yUno);



        //Números de la x
        xCinco.setBounds(1090,690,25,25);
        add(xCinco);

        xCuatro.setBounds(890,690,25,25);
        add(xCuatro);

        xTres.setBounds(690,690,25,25);
        add(xTres);

        xDos.setBounds(490,690,25,25);
        add(xDos);

        xUno.setBounds(290,690,25,25);
        add(xUno);
        
        CalcularEntropia(2);

        grafico.setArray(Entropia);
        grafico.blanco = false;


        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        

        grafico.repaint();

        add(grafico);
    }


    private void CalcularEntropia(int k)
    {
        double[] contador = new double[k];

        for(int i = 0; i < AutomataCelular.alto; i++)
        {
            for(int j = 0; j < AutomataCelular.ancho; j++)
            {
                contador[Celulas[i][j]]++;                
            }
            for(int m = 0; m < k; m++)
            {
                contador[m] /= AutomataCelular.ancho; 
                Entropia[i] += -(contador[m]*log(contador[m],k));                  
                contador[m] = 0;                
            }  
            //System.out.println(Entropia[i]);                   
        }
    }

    public static double CalcularEntropiaCelula(int[][] v,int j, int k)
    {
        double[] contador = new double[k];
        double entropiaCelula = 0.0;
         
        for(int i = 0; i < AutomataCelular.alto; i++)
        {
            contador[v[i][j]]++;                
        }

        for(int m = 0; m < k; m++)
        {
            System.out.println(contador[m]);   
            contador[m] /= AutomataCelular.alto; 
            entropiaCelula += -(contador[m]*log(contador[m],k));     
                         
        } 
       
       return entropiaCelula;
    }


    public static double log(double N, int k)
    {
        return (double)(Math.log(N)/Math.log(k));
    }

    public static void main(String args[])
    {
        //new CurvaEntropia();
    }
}

class GraficoEntropia extends Canvas
{
    public static boolean blanco;
    private double[] Entropia;

    public GraficoEntropia()
    {
        setBounds(100,75,1000,600);
        setBackground(Color.white);
        blanco = true;
    }

    public void setArray(double[] A)
    {
        this.Entropia = A;
    }

    public void paint(Graphics g)
    {   
        if(!blanco)
        {
            int x1, y1, x2, y2;
            //Rectas en eje x
            g.drawLine(0,120,1000,120);
            g.drawLine(0,240,1000,240);
            g.drawLine(0,360,1000,360);
            g.drawLine(0,480,1000,480);


            //Rectas hacia arriba
            g.drawLine(0,0,0,600);
            g.drawLine(200,0,200,600);
            g.drawLine(400,0,400,600);
            g.drawLine(600,0,600,600);
            g.drawLine(800,0,800,600);
            g.drawLine(1000,0,1000,600);

            for(int i = 1; i < AutomataCelular.alto; i++)
            {
                x1 = (int)(((i-1)*10)*0.2);
                x2 = (int)((i*10)*0.2);
                y1 = (int)(600-((Entropia[i-1]*1000)*0.6));
                y2 = (int)(600-((Entropia[i]*1000)*0.6));
                g.setColor(Color.red);
                g.drawLine(x1,y1,x2,y2);
                System.out.println(Entropia[i-1]);

            }        
        }
    }
}