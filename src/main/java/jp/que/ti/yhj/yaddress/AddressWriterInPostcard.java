package jp.que.ti.yhj.yaddress;

import static jp.que.ti.yhj.yaddress.PDFFontUtils.baseFontDFKai_SB;
import static jp.que.ti.yhj.yaddress.PDFFontUtils.baseFontMsGothic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.VerticalText;

/**
 * @author yhj
 */
public class AddressWriterInPostcard {
	final static class AddressSeparated {
		int fontSize;

		String address1;
		String address2;

		AddressSeparated(int fontSize, String address1, String address2) {
			this.fontSize = fontSize;
			this.address1 = address1;
			this.address2 = address2;
		}
	}

	public static int JAPAN_POSTCARD_W_MM = 100;

	public static int JAPAN_POSTCARD_H_MM = 148;
	private Document document;
	private PdfWriter writer;

	private PdfContentByte cb;

	public AddressWriterInPostcard(File outPath) {
		this.document = new Document(new Rectangle(pixel(JAPAN_POSTCARD_W_MM),
				pixel(JAPAN_POSTCARD_H_MM)));
		// create a writer with an output stream passed as an argument
		try {
			this.writer = PdfWriter.getInstance(document,
					bufferedOutputStream(outPath));
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	private void addTextVertically(int xMm, int yMm, String message,
			BaseFont baseFont, int fontSize) {
		VerticalText vt4fa = PDFUtils.createVerticalText(cb, pixel(xMm),
				pixel(yMm));
		PDFUtils.addTextVertically(message, vt4fa, baseFont, fontSize);
	}

	private void addTextVerticallyUnderPosition(int xMm, int yMm,
			String message, BaseFont baseFont, int fontSize) {
		VerticalText vt4fa = PDFUtils.createVerticalText(cb, pixel(xMm),
				pixel(yMm));
		vt4fa.setAlignment(Element.ALIGN_RIGHT);
		PDFUtils.addTextVertically(message, vt4fa, baseFont, fontSize);
	}

	final BufferedOutputStream bufferedOutputStream(File outPath) {
		if (outPath == null)
			throw new NullPointerException("引数 outPath が null です");
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(outPath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(//
					outPath.getAbsolutePath() + " のファイルが作成できませんでした", e);
		}
		final BufferedOutputStream bos = new BufferedOutputStream(fo);
		return bos;
	}

	final BufferedReader bufferedReader(File inPath, String encording) {
		if (inPath == null) {
			throw new NullPointerException("引数 inPath が null です");
		}
		if (!inPath.exists()) {
			throw new RuntimeException(//
					inPath.getAbsolutePath() + " のファイルが存在しませんでした");
		}
		if (inPath.isDirectory()) {
			throw new RuntimeException(//
					inPath.getAbsolutePath() + " はディレクトリであるためReadできません");

		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(inPath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(//
					inPath.getAbsolutePath() + " のファイルが存在しませんでした", e);
		}
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(fis, encording);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(//
					"指定されたエンコーディング文字列が不正です。encording=" + encording, e);
		}
		return new BufferedReader(isr);
	}

	public void close() {
		if (this.document != null) {
			this.document.close();
		}
		this.cb = null;
	}

	private float pixel(int millimetre) {
		return PDFUtils.mm2pixel(millimetre);
	}

	private String replace4VerticalMessage(String message) {
		List<char[]> tmp = PDFUtils.replaceCharacters;
		char[] rtnChar = message.toCharArray();
		for (char[] cs : tmp) {
			rtnChar = PDFUtils.replace(cs[0], cs[1], rtnChar);
		}
		return new String(rtnChar);
	}

	private AddressSeparated separateAddress(String address, int baseFontSize) {
		int spPoint = PDFUtils.addressSeparatePoint(address);
		String address1 = null;
		String address2 = null;
		if (spPoint > 0) {
			address1 = address.substring(0, spPoint);
			address2 = address.substring(spPoint);
			if (address2.length() > 0) {
				address1 = address1 + "　　　";
				address2 = "" + address2 + "　　　";
			}
		} else {
			address1 = address;
			address2 = "";
		}

		int toAddressLen = address1.length();
		if (toAddressLen < address2.length()) {
			toAddressLen = address2.length();
		}

		final int toAddressFontSize1 = baseFontSize * 15 / toAddressLen;
		final AddressSeparated rtn = new AddressSeparated(toAddressFontSize1,
				address1, address2);
		return rtn;
	}

	public void write(AddressCard to, AddressCard from) {
		// BaseFont f1 = baseFontHeiseiKakuGo_W5(false);
		BaseFont f1 = baseFontMsGothic(false);

		// *** 宛先郵便番号 ***
		cb.beginText();
		cb.setFontAndSize(f1, 22);
		cb.setTextMatrix(//
				1, 0, 0, 1, pixel(45), pixel(JAPAN_POSTCARD_H_MM - 21));
		cb.setCharacterSpacing(10.25f);
		cb.showText("" + to.getZipCode());
		cb.endText(); // ET

		// BaseFont baseFont = PDFFontUtils.baseFontArGyousyoH(true);
		// BaseFont baseFont = PDFFontUtils.baseFontDFKai_SB(true);
		// BaseFont baseFont = PDFFontUtils.baseFontDFKai_SB(true);
		// BaseFont baseFont = PDFFontUtils.baseFontHeiseiMin_W3(true);
		BaseFont baseFont = PDFFontUtils.baseFontMsMincho(true);

		// *** 宛先住所編集 ***
		cb.setCharacterSpacing(1.5f);
		final String toAddress = replace4VerticalMessage(to.getAddress() + "　");

		final AddressSeparated toAdrsSeprt = separateAddress(toAddress, 21);
		final int toX = JAPAN_POSTCARD_W_MM - 9;
		final int toY = JAPAN_POSTCARD_H_MM - 25;
		addTextVertically(toX, toY, toAdrsSeprt.address1//
				, baseFont, toAdrsSeprt.fontSize);
		addTextVerticallyUnderPosition(toX - 8, toY, toAdrsSeprt.address2//
				, baseFont, toAdrsSeprt.fontSize);

		// *** 宛名編集 ***
		cb.setCharacterSpacing(-2f);
		final String toName = to.getLastName() + "　" + to.getFirstName() + "　"
				+ to.getHonorificTitle();
		int toNameFontSize = 24 * 10 / toName.length();
		addTextVertically(52, JAPAN_POSTCARD_H_MM - 27//
				, toName, baseFont, toNameFontSize);

		// *** 送り主住所編集 ***
		cb.setCharacterSpacing(1.5f);
		final String fromAddress = replace4VerticalMessage(//
		from.getAddress() + "　　　　　　　　　");
		final AddressSeparated fromAdrsSeprt = separateAddress(fromAddress, 18);
		final int fromX = 30;
		final int fromY = 90;
		addTextVertically(fromX, fromY, fromAdrsSeprt.address1//
				, baseFont, fromAdrsSeprt.fontSize);
		addTextVerticallyUnderPosition(fromX - 5, fromY, fromAdrsSeprt.address2//
				, baseFont, fromAdrsSeprt.fontSize);

		// *** 送り主名前編集 ***
		cb.setCharacterSpacing(-2f);
		final String fromName = from.getLastName() + "　" + from.getFirstName();
		final String subFromName = wWordSpaces(from.getLastName().length() + 1)//
				+ from.getSubFirstName();
		int fromNameFontSize = 10 * 8 / fromName.length();
		final int fromNamX = 18;
		final int fromNamY = 85;
		addTextVertically(fromNamX, fromNamY//
				, fromName, baseFont, fromNameFontSize);
		addTextVertically(fromNamX - 6, fromNamY//
				, subFromName, baseFont, fromNameFontSize);

		// *** 送り主宛先郵便番号 ***
		cb.beginText();
		cb.setFontAndSize(f1, 10);
		cb.setTextMatrix(//
				1, 0, 0, 1, pixel(5), pixel(18));
		cb.setCharacterSpacing(7);
		cb.showText("" + from.getZipCode());
		cb.endText(); // ET

	}

	private String wWordSpaces(int len) {
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < len; idx++) {
			sb.append("　");
		}
		return sb.toString();

	}

	public void write(List<AddressCard> toList, AddressCard from) {
		boolean first = true;
		for (AddressCard addressCard : toList) {
			if (first) { //
				first = false;
				// add metadata (this should be done before open)
				document.addTitle("はがきに住所印刷");
				document.addAuthor("y-addresswriter4postcard");
				document.addSubject("はがきに住所を自動印刷します");
				document.addKeywords("addresswriter4postcard,iText, PDF");
				document.addCreator("y-addresswriter4postcard");

				document.open(); // open the document now
				this.cb = writer.getDirectContent();
			} else {
				document.newPage();
			}
			write(addressCard, from);
		}

	}
}
