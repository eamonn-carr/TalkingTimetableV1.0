/*
 * Quick response code generator and reader
 * 
 * @author IC
 * @version 1.0.0
 */
package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
	static JTextField textField = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws WriterException, IOException, NotFoundException {
		// Initial hardcoded data for test program 
		String qrCodeData = "Day: Tuesday\nTime: 09:00 to 11:00\nSubject: Software Engineering\nRoom: E2004";
		String filePath = "myQRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		System.out.println("QR Code image created successfully!");

		System.out.println("Data read from QR Code:\n" + readQRCode(filePath, charset, hintMap));

	}

	/*
	 * void createQRCode()
	 * Description: This method writes out the passes qrCodeData string to the
	 * file specified in the current directory. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createQRCode(String qrCodeData, String filePath, String charset,
			Map hintMap, int qrCodeheight, int qrCodewidth)
					throws WriterException, IOException {

		Path p1 = Paths.get(filePath);
		// encode the data to the hashmap.
		BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		// write out QR code to file image
		MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), p1);
	}

	/*
	 * String readQRCode()
	 * Description: Reads the QR data from the image and returns the string of data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
		return qrCodeResult.getText();
	}
}
