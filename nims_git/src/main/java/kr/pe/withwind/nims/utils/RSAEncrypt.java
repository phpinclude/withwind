package kr.pe.withwind.nims.utils;
/*
* @(#)EncryptionSampleCode.java	2018/03/07
*
* 본 소프트웨어는 RSA-512방식의 암호화를 위해 
* 한국의약품안전관리원에서 제공하는 암호화 샘플소스 코드입니다.
* 본 소스코드는 참고용이며 사용에 제약이 없이 제공되고 있습니다.
* 본 소스코드를 참조하여 개발한 결과물에 대해
* 한국의약품안전관리원의 법률적 책임을지지 않습니다.
* 
*/

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 공개키 기반 RSA암호화
 *
 * @version 1.00 2018년 3월 7일
 * @author 중외정보기술
 */

public class RSAEncrypt {
	
	private static final Logger logger = LogManager.getLogger(RSAEncrypt.class);

	public static void main(String arg[]) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		logger.debug(RSAEncrypt.EncryptRsa("01051599747"));
	}
	
	
	public static String EncryptRsa(String plainText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return EncryptRsa(plainText, "30820122300d06092a864886f70d01010105000382010f003082010a0282010100a276b723301526dcec143d1d8c77ba2c007dddf14b461d403c9db5fce2ca7091cbecf0622af3aebcb7d7e824dbeb33c281906815c829df82ff8a5c5224336fd4038d3c2bef7b28b21c00f81c087813d0b28298aa67cc63cb8f3e3d7265533380e5f649082f14bfcb6439bf8530275be3cdb3c7cb2ee535e2f8e73ef693bf0bedcb40726bd908c9816c0ed9b03a5f7c1770998db39cb292c7c6c09eb81033c1d4d1193ceb1571d5c41bd9388f697621169baba50648cb9c4a9bd9f8eac168dcba8337923b712824eaa4951bef19f726ca7f5e87d8dde3de7ff2e42001544ca7b55a83ab0ecc0c483c79a97666b0be513d4e619a97330668ec725776973b55421b0203010001");
	}

	/**
	 * 파라미터 문자열을 RSA암호화 문자열로 변환하는 함수
	 *
	 * @param arg
	 *            : 암호화 대상 문자열
	 * @return 암호화된 문자열
	 */
	public static String EncryptRsa(String arg, String pubKeyStr) throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "SunJCE");

		X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(hexToByteArray(pubKeyStr));
		KeyFactory ukeyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = null;
		try {
			publicKey = ukeyFactory.generatePublic(ukeySpec);
		} catch (InvalidKeySpecException e) {

			logger.error(RSAEncrypt.class.getSimpleName() + "오류!!", e);
		}

		String inputStr = arg;
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] arrCipherData = cipher.doFinal(inputStr.getBytes());

		return byteArrayToHex(arrCipherData);

	}

	/**
	 * HEX문자열을 BYTE배열로 변환하는 함수
	 *
	 * @param hex
	 *            : HEX문자열
	 * @return BYTE 배열
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	/**
	 * BYTE배열을 HEX문자열로 변환하는 함수
	 *
	 * @param ba
	 *            : BYTE 배열
	 * @return HEX문자열
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}
}
