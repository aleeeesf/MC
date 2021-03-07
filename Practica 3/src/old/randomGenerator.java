import java.util.ArrayList;

public class randomGenerator
{
    private static ArrayList<Double> Generados = new ArrayList<Double>();
    private long nNumeros, semilla;   

    public randomGenerator(long n, long seed)
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
        
        long cont = 0, rand; 
        final int m = (int)Math.pow(2, 5);        

        while(cont < nNumeros)
        {
            rand = (long)((((5*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x261b()
    {
        Generados.clear();

        long cont = 0, rand; 
        final int m = (int)Math.pow(2, 5);

        while(cont < nNumeros)
        {
            rand = (long)((((7*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x262()
    {
        Generados.clear();

        long cont = 0, rand;
        final int m = 31; 
        
        while(cont < nNumeros)
        {
            rand = (long)((((3*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void x263()
    {
        Generados.clear();

        long cont = 0, B = (long)Math.pow(7, 5), rand;  
        final long m = (long)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (long)((((B*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }

    public void combinado()
    {
        Generados.clear();

        long x, y, w, semilla_x, semilla_y, cont = 0;

        semilla_x = semilla;
        semilla_y = semilla;

        while(cont < nNumeros)
        {
            x = (long)((((40014*semilla_x)) % 2147483563));
            y = (long)((((40692*semilla_y)) % 2147483399));

            w = (Math.abs(x-y)) % 2147483562;

            Generados.add((double)w/2147483562);
            
            semilla_x = x;
            semilla_y = y;
            cont++;
        }
    }

    public void FishmanMoore()
    {
        Generados.clear();

        long cont = 0, rand; 
        final long m = (long)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (long)((((48271*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }

    }

    public void FishmanMoore2()
    {
        Generados.clear();

        long cont = 0, rand; 
        final long m = (long)Math.pow(2,31) - 1; 
        
        while(cont < nNumeros)
        {
            rand = (long)((((69621*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }

    }

    public void Randu()
    {
        Generados.clear();

        long cont = 0, rand, B = (long)Math.pow(2,16) + 3; 
        final long m = (long)Math.pow(2,31); 
        
        while(cont < nNumeros)
        {
            rand = (long)((((B*semilla)) % m));
            Generados.add((double)rand/m);
            cont++;
            semilla = rand;
        }
    }   
}