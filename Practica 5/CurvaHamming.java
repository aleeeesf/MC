import java.awt.*;
import javax.swing.*;

public class CurvaHamming extends JFrame
{
    private int[][] Celulas = new int[AutomataCelular.alto][AutomataCelular.ancho];
    private int[] Hamming = new int[AutomataCelular.alto];
    private HammingGrafico grafico = new HammingGrafico();


    private JLabel TextoCurva = new JLabel("CURVA DE HAMMING");
    private JLabel TextoDistancia = new JLabel("Distancia de Hamming");
    private JLabel TextoGeneraciones = new JLabel("Numero de generaciones");

    private JLabel yOcho = new JLabel("800");
    private JLabel ySiete = new JLabel("700");
    private JLabel ySeis = new JLabel("600");
    private JLabel yCinco = new JLabel("500");
    private JLabel yCuatro = new JLabel("400");
    private JLabel yTres = new JLabel("300");
    private JLabel yDos = new JLabel("200");
    private JLabel yUno = new JLabel("100");


    private JLabel xCinco = new JLabel("500");
    private JLabel xCuatro = new JLabel("400");
    private JLabel xTres = new JLabel("300");
    private JLabel xDos = new JLabel("200");
    private JLabel xUno = new JLabel("100");


    public CurvaHamming(int[][] C)
    {
        this.Celulas = C;

        
        setBounds(0,0,1225,800);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        
        //Textos que se presentan en el frame
        TextoCurva.setBounds(550,25,150,25);
        add(TextoCurva);

        TextoDistancia.setBounds(40,30,150,25);
        add(TextoDistancia);

        TextoGeneraciones.setBounds(550,725,150,25);
        add(TextoGeneraciones);
        

        //Numeros de la y
        yOcho.setBounds(60,65,25,25);
        add(yOcho);

        ySiete.setBounds(60,140,25,25);
        add(ySiete);

        ySeis.setBounds(60,215,25,25);
        add(ySeis);

        yCinco.setBounds(60,290,25,25);
        add(yCinco);

        yCuatro.setBounds(60,365,25,25);
        add(yCuatro);

        yTres.setBounds(60,440,25,25);
        add(yTres);

        yDos.setBounds(60,515,25,25);
        add(yDos);

        yUno.setBounds(60,590,25,25);
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


        CalcularHamming();


        grafico.setArray(Hamming);
        grafico.blanco = false;

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        
        grafico.repaint();

        add(grafico);
    }


    private void CalcularHamming()
    {
        int diferencia;

        for(int i = 1; i < AutomataCelular.getFilasComputadas()-1;i++)
        {
            diferencia = 0;

            for(int j = 0; j < AutomataCelular.ancho; j++)
            {
                if(Celulas[i-1][j] != Celulas[i][j]) diferencia++;
            }

            Hamming[i] = diferencia;
        }
    }



/*
    public static void main(String args[])
    {
        new CurvaHamming();
    }
*/
}

class HammingGrafico extends Canvas
{
    private int[] Distancia;
    public boolean blanco;

    public HammingGrafico()
    {
        setBounds(100,75,1000,600);
        setBackground(Color.white);
        blanco = true;
    }

    public void setArray(int[] A)
    {
        Distancia = A;
    }

    public void paint(Graphics g)
    {
        if(!blanco)
        {
            int x1, y1, x2, y2;

            //Rectas en eje x
            g.drawLine(0,75,1000,75);
            g.drawLine(0,150,1000,150);
            g.drawLine(0,225,1000,225);
            g.drawLine(0,300,1000,300);
            g.drawLine(0,375,1000,375);
            g.drawLine(0,375,1000,375);
            g.drawLine(0,450,1000,450);
            g.drawLine(0,525,1000,525);
            g.drawLine(0,525,1000,525);
            g.drawLine(0,600,1000,600);

            //Rectas hacia arriba
            g.drawLine(0,0,0,600);
            g.drawLine(200,0,200,600);
            g.drawLine(400,0,400,600);
            g.drawLine(600,0,600,600);
            g.drawLine(800,0,800,600);
            g.drawLine(1000,0,1000,600);



            for(int i = 1; i < AutomataCelular.getFilasComputadas()-1; i++)
            {
                x1 = (int)(((i-1)*10)*0.2);
                x2 = (int)((i*10)*0.2);
                y1 = (int)((800-Distancia[i-1])*0.75);
                y2 = (int)((800-Distancia[i])*0.75);
                g.setColor(Color.red);
                g.drawLine(x1,y1,x2,y2);
            }
        }
    }
}