import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class interfaz {
    
    public interfaz()
    {
        iniciar();
    }

    private void iniciar()
    {        
        //Panel donde se muestra el gráfico
        LaminaImagen laminaI = new LaminaImagen();
        //Panel donde se muestra los botones
        LaminaBoton laminaB = new LaminaBoton();
        //Menu superior
        LaminaMenu laminaM = new LaminaMenu();
        //Icono de la aplicación
        ImageIcon icon = new ImageIcon("rocket.png");

        //Ventana Principal
        JFrame frame = new JFrame();               
        frame.setTitle("Interfaz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setIconImage(icon.getImage());

        frame.add(laminaI);
        frame.add(laminaB);  
        frame.setJMenuBar(laminaM);
    }

    public static void main(String[] args)
    {
        new interfaz();
    }
}


class LaminaImagen extends JPanel 
{
    private Image imagen;

    LaminaImagen()
    {
        super();
        //Resolucion 600x500
        setBounds(0,0,600,500);
        setLayout(null);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);       
        
        try
        {
            imagen = ImageIO.read(new File("sn9.jpg"));
        }catch(IOException e)
        {
            System.out.println("La imagen no se encuentra");
        }

        g.drawImage(imagen, 75, 75,null);
    }
}



class LaminaBoton extends JPanel implements ActionListener
{
    private JButton boton = new JButton("Ejecutar");
    private JButton Ayuda = new JButton("Ayuda");
    private JButton AcercaDe = new JButton("Acerca De");

    //Frames que ejecutan los botones
    private JFrame EjecutarFrame = new JFrame();
    private JFrame AyudaFrame = new JFrame();
    private JFrame AcercaFrame = new JFrame();


    public LaminaBoton()
    {
        setBounds(700,0,300,500);
        setLayout(null);

        boton.setBounds(0,150,100,25);
        add(boton);

        Ayuda.setBounds(0,200,100,25);
        add(Ayuda);

        AcercaDe.setBounds(0,250,100,25);
        add(AcercaDe);

        boton.addActionListener(this);
        Ayuda.addActionListener(this);
        AcercaDe.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == boton)
        {
            EjecutarFrame.setVisible(true);
            EjecutarFrame.setBounds(0,0,500,500);
            EjecutarFrame.setTitle("Ejecucion"); 
        }
        else if(e.getSource() == Ayuda)
        {
            AyudaFrame.setVisible(true);
            AyudaFrame.setBounds(0,0,500,500);
            AyudaFrame.setTitle("Ayuda");            
        }
        else if(e.getSource() == AcercaDe)
        {
            JLabel label = new JLabel();
            label.setText("Creado por Alejandro Serrano");
            
            AcercaFrame.setVisible(true);
            AcercaFrame.setResizable(false);
            AcercaFrame.setBounds(0,0,500,500);
            AcercaFrame.setTitle("Acerca De");    
            AcercaFrame.add(label);        
        }                  
    }
}

class LaminaMenu extends JMenuBar implements ActionListener
{
    private JMenu file = new JMenu("Archivo");
    private JMenu options = new JMenu("Opciones");

    //Opciones de File
    private JMenuItem opc1 = new JMenuItem("Cargar");
    private JFrame opc1Frame = new JFrame();    
    private JMenuItem opc2 = new JMenuItem("Salir");  

    //Opciones de Options
    private JMenuItem opc3 = new JMenuItem("Generador");       
    private SimuladorFrame opc3Frame = new SimuladorFrame();   
    private JMenuItem opc4 = new JMenuItem("Automata 1D");
    private JFrame opc4Frame = new JFrame();   

    public LaminaMenu()
    {
        super();

        opc1.addActionListener(this);
        opc2.addActionListener(this);
        opc3.addActionListener(this);
        opc4.addActionListener(this);
        

        file.add(opc1);
        file.add(opc2);

        options.add(opc3);
        options.add(opc4);

        add(file);
        add(options);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == opc1)
        {
            opc1Frame.setVisible(true);
            opc1Frame.setBounds(0,0,500,500);
            opc1Frame.setTitle("Cargar");  
        }
        else if(e.getSource() == opc2)
            System.exit(0);

        else if(e.getSource() == opc3)
        {
            opc3Frame.setVisible(true);
        }

        else if(e.getSource() == opc4)
        {
            opc4Frame.setVisible(true);
            opc4Frame.setBounds(0,0,500,500);
            opc4Frame.setTitle("Opcion 2");  
        }
    }
}
