import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
/*
 * ARTICOLO USATO 
 * https://stackoverflow.com/questions/15558202/how-to-resize-image-in-java
 */
/*
 * 
 * nome del file path 
 * path
 */
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
    		
			
    		StringBuffer stringBuffer = new StringBuffer();
    		stringBuffer.append("PATH;NOME_CARTELLA;FILE_ORIGINALE;FILE_R\r\n");
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
        	        				 System.out.println(fileSub.getAbsolutePath());
        	        				 BufferedImage scaledImage = getScaledImage(srcImage,width,height);
        	        				 if(scaledImage==null) System.out.print("Il file e nullo?");
        	        				 
        	        				 String percorso = fileSub.getAbsolutePath();
        	        				 String nuova = percorso.substring(0,percorso.length()-4);
        	        				 String nomeCompleto = nuova+"_r.jpg";
        	        				 //nomefileoriginale;pathvecchioconnome;pathnuovo;\n
        	        				 stringBuffer.append(args[0]+"\\").append(";").append(f.getName()).append(";").append(fileSub.getName()).append(";").append(fileSub.getName().substring(0,fileSub.getName().length()-4)+"_r.jpg").append("\r\n");
        	        				// .append(fileSub.getName()).append(";").append(fileSub.getAbsolutePath()).append(";").append(nomeCompleto).append("\r\n");
        	        				 
        	        				 ImageIO.write(scaledImage, "jpg", new File(nomeCompleto));
        	        			 }
        	        		}
        				}
        			}	
        		}
        		
        	}
        	//scrittura
        	
        	
			PrintWriter out= new PrintWriter(new File(args[0]+"/report.txt"));
			out.printf(stringBuffer.toString());
			out.close();

            //FileWriter write = new FileWriter(args[0]+"\report.txt");
            //write.write(stringBuffer.toString());
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
	    BufferedImage resizedImage = new BufferedImage(correctWidth, newHeight, originalImage.getType());
	    Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, correctWidth, newHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
   
}


   