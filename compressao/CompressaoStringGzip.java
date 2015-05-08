package compressao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class CompressaoStringGzip {
	
	
	public static String compress(String inputStr) throws Exception {
        byte[] input = inputStr.getBytes("UTF-8");

        // Compress the bytes
        byte[] output1 = new byte[input.length];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output1);
        compresser.end();
        
        System.out.println("Tamanho Comprimido = " + compressedDataLength);
        String str = Base64.encode(output1);
        return str;
	 }
	
	
	public static String decompress(String str) throws Exception {
		byte[] output2 = Base64.decode(str);

        // Decompress the bytes
        Inflater decompresser = new Inflater();
        decompresser.setInput(output2);
        byte[] result = str.getBytes();
        int resultLength = decompresser.inflate(result);
        decompresser.end();

        // Decode the bytes into a String
        String outputString = new String(result, 0, resultLength, "UTF-8");
        System.out.println("Tamanho descomprimido = " + resultLength);
        
        return outputString;
	 }
	
	public static void main(String[] args) throws Exception {
		
		String file = args[0];
		
		FileReader fr = new FileReader(new File(file)); 
		BufferedReader br = new BufferedReader(fr);
		
		StringBuilder sb = new StringBuilder();
		String linha = null;
		
		 while((linha = br.readLine()) != null ) {
			 sb.append(linha);
		 }
		 
		 br.close();
		 
		 String str = sb.toString();
		
		str = CompressaoStringGzip.compress(str);
		System.out.println("texto Comprimido : " + str);
		
		str = CompressaoStringGzip.decompress(str);
		System.out.println("texto Descomprimido : " + str);
	}
	
	
}

