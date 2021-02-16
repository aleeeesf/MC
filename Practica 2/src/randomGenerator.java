import java.util.ArrayList;

public class randomGenerator
{
    public static ArrayList<Double> Generados = new ArrayList<Double>();
    public int nNumeros;
    int semilla;    

    public randomGenerator(int n, int seed)
    {
        this.nNumeros = n;
        this.semilla = seed;
    }

    ArrayList<Double> getArray()
    {
        return Generados;
    }

    public void x261a()
    {
        Generados.clear();
        int cont = 0, rand; 
        final int m = (int)Math.pow(2, 5);
        

        while(cont < nNumeros)
        {
            rand = (int)((((5*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x261b()
    {
        Generados.clear();
        int cont = 0, rand; 
        final int m = (int)Math.pow(2, 5);

        while(cont < nNumeros)
        {
            rand = (int)((((7*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x262()
    {
        int cont = 0, rand;
        final int m = 31; 
        
        while(cont < nNumeros)
        {
            rand = (int)((((3*semilla)) % m));
            System.out.println(rand);
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x263()
    {
        int cont = 0, rand, B = (int)Math.pow(7, 5);  
        final int m = (int)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (int)((((B*semilla)) % m));
            System.out.println(rand);
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void combinado()
    {
        int x, y, w, rand, semilla_x, semilla_y, cont = 0;

        semilla_x = semilla;
        semilla_y = semilla;

        while(cont < nNumeros)
        {
            x = (int)((((40014*semilla_x)) % 2147483563));
            y = (int)((((40692*semilla_y)) % 21));

            w = (Math.abs(x-y)) % 2147483562;

            Generados.add((double)w/2147483562);
            
            semilla_x = x;
            semilla_y = y;
            cont++;
        }
    }

    public void Fishman()
    {
        int cont = 0, rand; 
        final int m = (int)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (int)((((48271*semilla)) % m));
            System.out.println(rand);
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }

    }

    public void Moore()
    {
        int cont = 0, rand; 
        final int m = (int)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (int)((((69621*semilla)) % m));
            System.out.println(rand);
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }

    }

    public void Randu()
    {
        int cont = 0, rand, B = (int)Math.pow(2,16) + 3; 
        final int m = (int)Math.pow(2,31) + 1; 
        
        while(cont < nNumeros)
        {
            rand = (int)((((B*semilla)) % m));
            System.out.println(rand);
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public static void main(String args[])
    {
        randomGenerator r = new randomGenerator(20, 1);
        r.Randu();
/*
        for(int i = 0; i < 10; i++)
        {
            System.out.println(Generados.get(i));
        }*/
    }
    
}