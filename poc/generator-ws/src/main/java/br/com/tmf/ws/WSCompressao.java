package br.com.tmf.ws;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.codec.binary.Base64;

@WebService(name = "WSCompressao")
@Stateless
public class WSCompressao {

	@WebMethod(operationName = "compressao")
	public String compress(@WebParam(name = "conteudo") String inputStr) throws Exception {
        byte[] input = inputStr.getBytes(CHARSET);

        // Compress the bytes
        byte[] output = new byte[input.length];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        compresser.end();
        
        System.out.println("Tamanho Comprimido = " + compressedDataLength);
        String str = new String(Base64.encodeBase64(output), CHARSET);
        return str;
	 }
	
	@WebMethod(operationName = "descompressao")
	public String decompress(@WebParam(name = "conteudo") String str) throws Exception {
		byte[] output2 = Base64.decodeBase64(str.getBytes(CHARSET));

        // Decompress the bytes
        Inflater decompresser = new Inflater();
        decompresser.setInput(output2);
        byte[] result = str.getBytes();
        int resultLength = decompresser.inflate(result);
        decompresser.end();

        // Decode the bytes into a String
        String outputString = new String(result, 0, resultLength, CHARSET);
        System.out.println("Tamanho descomprimido = " + resultLength);
        
        return outputString;
	 }
	
	public static void main(String[] args) throws Exception {
		List<String> lista = new ArrayList<String>();
		
		lista.add("E:\\scripts\\20160224_000000_Ic_1k.jpg");
		lista.add("E:\\scripts\\20160224_000000_Ic_flat_1k.jpg");
		lista.add("E:\\scripts\\20160224_000000_M_1k.jpg");
		lista.add("E:\\scripts\\20160224_000000_M_color_1k.jpg");
		lista.add("E:\\scripts\\hmi.Ic_720s.20160214_000000_TAI.1.continuum.fits");
		lista.add("E:\\scripts\\hmi.m_720s.20160412_000000_TAI.magnetogram.fits");
		
		for(String str : lista) {
			Path path = Paths.get(str);
			byte[] data = Files.readAllBytes(path);
			WSCompressao.comprime(data);
		}
		
		
	}
	
	private static void comprime(byte[] input){
		
        // Compress the bytes
        byte[] output = new byte[input.length];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        compresser.end();
        
        System.out.println("Tamanho Comprimido = " + compressedDataLength);
	} 

	private static final Charset CHARSET = Charset.forName("UTF-8");
}
