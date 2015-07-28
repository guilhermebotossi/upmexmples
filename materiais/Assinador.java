import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Assinador {

	public static void main(String[] args) {
		try {

			if(args.length != 3) {
				System.out.println("É obrigatorio fornecer os 3 parametros na ordem abaixo:");
				System.out.println("1 - o caminho do arquivo xml a ser assinado");
				System.out.println("2 - o caminho do certificado");
				System.out.println("3 - a senha do certificado");
				System.out.println("Conforme exemplo:\n");
				System.out.println("java Assinador \"c:\\arquivo.xml\" \"c:\\certificado.jks\" senhaDoCertificado");
				System.exit(0);
			}


			String caminhoXml = args[0];

	         BufferedReader br = new BufferedReader(new FileReader(caminhoXml));

	         StringBuilder sb = new StringBuilder();

	         while(br.ready()){
	            sb.append(br.readLine());
	         }

	         br.close();

	         String xml = sb.toString();

			String senha = args[2];
			String caminhoCertificado = args[1];

			Assinador assinador = new Assinador();
			String xmlAssinado = assinador.assinar(xml, true, senha, caminhoCertificado);

			Integer ultimoPonto = caminhoXml.lastIndexOf(".");

			if(ultimoPonto != -1) {
				String extensao = caminhoXml.substring(ultimoPonto);

				caminhoXml = caminhoXml.substring(0, ultimoPonto);
				caminhoXml = caminhoXml + "_assinado" + extensao;
			} else {
				caminhoXml = caminhoXml + "_assinado";
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoXml));
			bw.write(xmlAssinado);
			bw.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String assinar(String xmlSemAssinatura, boolean omitirDeclaracao, String senhaCertificado,
		String pathCertificado) throws Exception {

		try {
			this.validarParametros(xmlSemAssinatura, omitirDeclaracao, senhaCertificado, pathCertificado);
			setOmitirDeclaracao(omitirDeclaracao);
			obterCertificadoDigital(senhaCertificado, pathCertificado);
			DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbf = fabrica.newDocumentBuilder();
			Document doc = dbf.parse(new ByteArrayInputStream(xmlSemAssinatura.getBytes("UTF-8")));

			StringWriter fos = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.STANDALONE, "yes");
			trans.transform(new DOMSource(doc), new StreamResult(fos));
			return gerarAssinatura(fos.toString());
		} catch(Exception e) {
			throw new Exception("Ocorreu um erro ao assinar o XML. " + e.getMessage());
		}
	}

	/**
	 * Obtém o certificado digital
	 *
	 * @param senha do certificado
	 * @param pathChave do certificado
	 * @throws Exception caso ocorra algum erro ao obter certificado digital
	 */
	@SuppressWarnings({"rawtypes"})
	private void obterCertificadoDigital(String senha, String pathChave) throws Exception {
		this.validarParametros(senha, pathChave);
		try {
			if(senha == null || "".equals(senha) || pathChave == null || "".equals(pathChave)) {
				throw new Exception("Senha ou Caminho Chave esta incorreto");
			}
			KeyStore ks = KeyStore.getInstance(TP_CHAVE_JKS);
			FileInputStream in = new FileInputStream(pathChave);
			ks.load(in, senha.toCharArray());
			in.close();
			Enumeration en = ks.aliases();

			while(en.hasMoreElements()) {
				String alias = (String) en.nextElement();
				Key key = ks.getKey(alias, senha.toCharArray());
				if(key instanceof PrivateKey) {
					chavePrivada = (PrivateKey) key;
					cert = (X509Certificate) ks.getCertificate(alias);
					chavePublica = cert.getPublicKey();
				} else if(key instanceof PublicKey) {
					chavePublica = (PublicKey) key;
				}
			}
		} catch(KeyStoreException kse) {
			throw new Exception("Erro ao carregar o certificado. " + kse.getMessage());
		} catch(FileNotFoundException fne) {
			throw new Exception("Arquivo não encontrado. " + fne.getMessage());
		} catch(IOException ioe) {
			throw new Exception("Erro. " + ioe.getMessage());
		}
	}

	private String gerarAssinatura(String xml) throws Exception {
		this.validarParametros(xml);
		String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");

		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName)
			.newInstance());


		ArrayList<Transform> transformList = new ArrayList<Transform>();

		Transform tr1 = fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
		Transform tr2 = fac.newTransform(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null);
		transformList.add(tr1);
		transformList.add(tr2);


		Reference ref = fac.newReference("", fac
				.newDigestMethod(DigestMethod.SHA1, null), transformList, null,
				null);

		// Informações da Assinatura
		SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
			(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
			Collections.singletonList(ref));

		KeyInfoFactory kif = fac.getKeyInfoFactory();
		X509Data x509 = kif.newX509Data(Collections.singletonList(cert));
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		Document doc = dbf.newDocumentBuilder().parse(is);

		DOMSignContext dsc = new DOMSignContext(chavePrivada, doc.getDocumentElement());

		// Assina o documento
		XMLSignature signature = fac.newXMLSignature(si, ki);
		signature.sign(dsc);

		// Resultado
		StringWriter fos = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		if(isOmitirDeclaracao()) {
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		} else {
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "false");
		}
		trans.setOutputProperty(OutputKeys.STANDALONE, "yes");
		trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		trans.transform(new DOMSource(doc), new StreamResult(fos));
		return fos.toString();
	}


	/**
	 * Valida os parametros informados, verificando se o mesmo não é nulo ou vazio caso seja uma instância de String
	 *
	 * @param o objeto a ser validado
	 * @throws Exception caso parametro seja inválido
	 */
	private void validarParametros(Object... o) throws Exception {
		if(o != null) {
			for(Object obj : o) {
				if(obj == null)
					throw new Exception("Parametro enviado para assinatura digital é nulo.");
				if(obj instanceof String) {
					if("".equals(obj)) {
						throw new Exception("Parametro enviado para assinatura digital é nulo.");
					}
				}
			}
		} else {
			throw new Exception("Parametro enviado para assinatura digital é nulo.");
		}
	}

	public boolean isOmitirDeclaracao() {
		return omitirDeclaracao;
	}

	public void setOmitirDeclaracao(boolean omitirDeclaracao) {
		this.omitirDeclaracao = omitirDeclaracao;
	}

	private static String TP_CHAVE_JKS = "JKS";
	private PrivateKey chavePrivada = null;
	private X509Certificate cert = null;
	private boolean omitirDeclaracao = true;
	@SuppressWarnings("unused")
	private PublicKey chavePublica = null;
}
