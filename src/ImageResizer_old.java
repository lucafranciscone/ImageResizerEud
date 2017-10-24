import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {
	private static int LARGHEZZA_MAX = 800;
	private static int LARGHEZZA_MAX_ALTEZZA = 600;


    public static void main(String [] args) throws IOException
    {
    	
    	if(args.length!=1)
		{
			System.out.println("Inserire parametro in input");
		}
    	else
    	{
    		File folder = new File(args[0]);
    		//directory base
        	File[] listOfFile= folder.listFiles();
        	File[] tmp;
        	//sottodirectory
        	for(File f : listOfFile)
        	{
        		//System.out.println("nome della dir: "+f.getName());
        		//entro e stampo tutti i file
        		if(f.isDirectory())
        		{
        			//elenco file nella sottodirectory
        			tmp = f.listFiles();
        			for(File fileSub : tmp)
        			{
        				if(fileSub.isFile())
        				{
        	        		//System.out.println("nome della dir: "+f.getName()+" Nome del file: "+fileSub.getName());
        	        		String estensione=  fileSub.getName().substring(fileSub.getName().lastIndexOf(".") + 1);
        	        		if(estensione.equalsIgnoreCase("jpg"))
        	        		{
        	        			//System.out.println(fileSub.getName() +"TIPO IMMAGINE PATH ASSOLUTO = "+fileSub.getAbsolutePath());
        	        			//CODICE PER MODIFICA IMMAGINE
        	        			
        	        			BufferedImage srcImage;
        	        			srcImage = ImageIO.read(new File(fileSub.getAbsolutePath()));
        	        			 int width = srcImage.getWidth();
        	        			 int height = srcImage.getHeight();
        	        			 if((width>=height && width>=LARGHEZZA_MAX) || (height>width && width>LARGHEZZA_MAX_ALTEZZA) )//800
        	        			 {
        	        				 BufferedImage scaledImage = getScaledImage(srcImage,width,height);
        	        				 if(scaledImage==null) System.out.print("Il file e nullo?");

        	        				 //devo recuperare il nome del file
        	        				 String nomeImmagine = fileSub.getName();
        	        				 String nuova = nomeImmagine.substring(0,nomeImmagine.length()-4);
        	        				 String nomeCompleto = nuova+"_r.jpg";
        	        				 File outputfile = new File(nomeCompleto);
        	        				 ImageIO.write(scaledImage, "jpg", outputfile);
        	        			 }
        	        		}
        				}
        			}	
        		}
        		System.out.println("---------------------\n\n");
        	}
    	}
    }
    public static BufferedImage getScaledImage(BufferedImage originalImage, int width, int height) {
    	int correctWidth=0;
    	int newHeight=0;
    	Boolean verticale= false;
    	//verifico se è orizzontale setto larghezza 800
        if(width>=height)//800
		 {
        	correctWidth = LARGHEZZA_MAX;
		 }
        else 
        {			 
        	correctWidth = LARGHEZZA_MAX_ALTEZZA; 
        	verticale = true;
        }
        
        //creo la nuova altezza proporzionata
		 newHeight = (correctWidth * height) / width;
		 if(verticale)
		 {
			 System.out.print("Verticale ");
		 }
		 else
		 {
			 System.out.print("Orizzontale ");
		 }
		 System.out.println("Nuove Misure: larghezza "+correctWidth+" altezza:"+newHeight );
		 BufferedImage resizedImage = new BufferedImage(correctWidth, newHeight,originalImage.getType());
		 
        return resizedImage;
    }
}


   