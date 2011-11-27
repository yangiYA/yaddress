package jp.que.ti.yhj.yaddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.VerticalText;

/**
 *PDF編集のユーティリティーライブラリ
 *
 * @author yhj
 */
public class PDFUtils {
	public static String MS_P_GOTHIC = "c:/windows/fonts/msgothic.ttc,2";
	public static String KANTEIRYU = "c:/windows/fonts/JKAN00H.TTC,0";
	public static String AR_GYOUSYO = "c:/windows/fonts/JSKA00H.TTC,0";
	public static String HEISEIKAKUGO_W5 = "HeiseiKakuGo-W5";

	public static float MM_PER_ONE_INCH = 25.4f;
	public static int DEFAULT_DPI = 72;
	// public static float CONSTANT_PIXEL_PER_MM = 2.83f;
	/** @deprecated 正しいのか未確認 TODO */
	public static float CONSTANT_PIXEL_PER_MM = 2.83f;

	public static int JAPAN_POSTCARD_W_MM = 100;
	public static int JAPAN_POSTCARD_H_MM = 148;

	private PDFUtils() {
	};

	// public static /

	/**
	 *
	 * 縦方向にテキストを追記する
	 *
	 * @param cb
	 *            {@link PdfContentByte}オブジェクト
	 * @param startX
	 *            書き出し位置X。右上からのポイント位置で指定
	 * @param startY
	 *            書き出し位置Y。右上からのポイント位置で指定
	 * @param height
	 *            書き出す高さ
	 * @param maxLines
	 *            書き込む行の最大行数
	 * @param leading
	 *            行と行の間のポイント数
	 */
	public static VerticalText createVerticalText(PdfContentByte cb,
			float startX, float startY, float height, int maxLines,
			float leading) {
		VerticalText vt = new VerticalText(cb);
		vt.setAlignment(Element.ALIGN_LEFT);
		vt.setVerticalLayout(startX, startY, height, maxLines, leading);
		return vt;
	}

	/**
	 * 縦方向にテキストを追記する。
	 * {@link #createVerticalText(PdfContentByte, float, float, float, int, float)}
	 * メソッドの高さの引数に、startYを指定、最大行数に 1、行と行の間のポイント数に 0 を指定して呼び出す
	 *
	 * @param cb
	 *            {@link PdfContentByte}オブジェクト
	 * @param startX
	 *            書き出し位置X。左上からのポイント位置で指定
	 * @param startY
	 *            書き出し位置Y。左上からのポイント位置で指定
	 */
	public static VerticalText createVerticalText(PdfContentByte cb,
			float startX, float startY) {
		VerticalText vt = new VerticalText(cb);
		vt.setAlignment(Element.ALIGN_LEFT);
		vt.setVerticalLayout(startX, startY, startY, 2, 0);
		return vt;
	}

	/**
	 * 縦書きで文字を追加
	 *
	 * @param message
	 *            書き込む文字
	 * @param vt
	 *            縦書き用オブジェクト。{@link VerticalText} オブジェクト
	 * @param fontEmbedded
	 *            OSフォントに依存するか否か
	 * @param fontName
	 *            the name of the font or its location on file
	 * @param fontSize
	 *            フォントサイズ。ポイントで指定
	 * @return returns the result of the operation. It can be
	 *         <CODE>NO_MORE_TEXT</CODE> and/or <CODE>NO_MORE_COLUMN</CODE>
	 */
	public static int addTextVertically(String message, VerticalText vt,
			BaseFont baseFont, float fontSize) {
		Chunk naru = new Chunk(message, new Font(baseFont, fontSize));
		vt.addText(naru);
		return vt.go();
	}

	/**
	 * 印刷上の長さ(ミリメートル)を Graphics2D 上の長さ(ピクセル)へ変換します。
	 *
	 * @param mm
	 *            印刷上の長さ(単位: ミリメートル)
	 * @param dpi
	 *            DPI(dot per inch) 1インチ当りのドット数 (ピクセル数)
	 * @return Graphics2D オブジェクト上での長さ (単位: ピクセル)
	 *
	 *         参考: - インチ - Wikipedia
	 *         http://ja.wikipedia.org/wiki/%E3%82%A4%E3%83%B3%E3%83%81
	 *
	 */
	public static float mm2pixel(float mm, float dpi) {
		// 国際インチ(international inch): 1 インチ = 25.4 ミリメートル
		return mm * dpi / MM_PER_ONE_INCH;
	}

	public static float mm2pixel(float mm) {
		return mm2pixel(mm, DEFAULT_DPI);
	}

	static List<char[]> replaceCharacters = new ArrayList<char[]>();
	static {
		replaceCharacters.add(new char[] { '1', '１' });
		replaceCharacters.add(new char[] { '2', '２' });
		replaceCharacters.add(new char[] { '3', '３' });
		replaceCharacters.add(new char[] { '4', '４' });
		replaceCharacters.add(new char[] { '5', '５' });
		replaceCharacters.add(new char[] { '6', '６' });
		replaceCharacters.add(new char[] { '7', '７' });
		replaceCharacters.add(new char[] { '8', '８' });
		replaceCharacters.add(new char[] { '9', '９' });
		replaceCharacters.add(new char[] { '0', '０' });
		replaceCharacters.add(new char[] { '-', '｜' });
		replaceCharacters.add(new char[] { 'ー', '｜' });
		replaceCharacters.add(new char[] { '－', '｜' });
		replaceCharacters.add(new char[] { '‐', '｜' });
		replaceCharacters.add(new char[] { 'A', 'Ａ' });
		replaceCharacters.add(new char[] { 'B', 'Ｂ' });
		replaceCharacters.add(new char[] { 'C', 'Ｃ' });
		replaceCharacters.add(new char[] { 'D', 'Ｄ' });
		replaceCharacters.add(new char[] { 'E', 'Ｅ' });
		replaceCharacters.add(new char[] { 'F', 'Ｆ' });
		replaceCharacters.add(new char[] { 'G', 'Ｇ' });
		replaceCharacters.add(new char[] { 'H', 'Ｈ' });
		replaceCharacters.add(new char[] { 'I', 'Ｉ' });
		replaceCharacters.add(new char[] { 'J', 'Ｊ' });
		replaceCharacters.add(new char[] { 'K', 'Ｋ' });
		replaceCharacters.add(new char[] { 'L', 'Ｌ' });
		replaceCharacters.add(new char[] { 'M', 'Ｍ' });
		replaceCharacters.add(new char[] { 'N', 'Ｎ' });
		replaceCharacters.add(new char[] { 'O', 'Ｏ' });
		replaceCharacters.add(new char[] { 'P', 'Ｐ' });
		replaceCharacters.add(new char[] { 'Q', 'Ｑ' });
		replaceCharacters.add(new char[] { 'R', 'Ｒ' });
		replaceCharacters.add(new char[] { 'S', 'Ｓ' });
		replaceCharacters.add(new char[] { 'T', 'Ｔ' });
		replaceCharacters.add(new char[] { 'U', 'Ｕ' });
		replaceCharacters.add(new char[] { 'V', 'Ｖ' });
		replaceCharacters.add(new char[] { 'W', 'Ｗ' });
		replaceCharacters.add(new char[] { 'X', 'Ｘ' });
		replaceCharacters.add(new char[] { 'Y', 'Ｙ' });
		replaceCharacters.add(new char[] { 'Z', 'Ｚ' });
		replaceCharacters.add(new char[] { 'a', 'ａ' });
		replaceCharacters.add(new char[] { 'b', 'ｂ' });
		replaceCharacters.add(new char[] { 'c', 'ｃ' });
		replaceCharacters.add(new char[] { 'd', 'ｄ' });
		replaceCharacters.add(new char[] { 'e', 'ｅ' });
		replaceCharacters.add(new char[] { 'f', 'ｆ' });
		replaceCharacters.add(new char[] { 'g', 'ｇ' });
		replaceCharacters.add(new char[] { 'h', 'ｈ' });
		replaceCharacters.add(new char[] { 'i', 'ｉ' });
		replaceCharacters.add(new char[] { 'j', 'ｊ' });
		replaceCharacters.add(new char[] { 'k', 'ｋ' });
		replaceCharacters.add(new char[] { 'l', 'ｌ' });
		replaceCharacters.add(new char[] { 'm', 'ｍ' });
		replaceCharacters.add(new char[] { 'n', 'ｎ' });
		replaceCharacters.add(new char[] { 'o', 'ｏ' });
		replaceCharacters.add(new char[] { 'p', 'ｐ' });
		replaceCharacters.add(new char[] { 'q', 'ｑ' });
		replaceCharacters.add(new char[] { 'r', 'ｒ' });
		replaceCharacters.add(new char[] { 's', 'ｓ' });
		replaceCharacters.add(new char[] { 't', 'ｔ' });
		replaceCharacters.add(new char[] { 'u', 'ｕ' });
		replaceCharacters.add(new char[] { 'v', 'ｖ' });
		replaceCharacters.add(new char[] { 'w', 'ｗ' });
		replaceCharacters.add(new char[] { 'x', 'ｘ' });
		replaceCharacters.add(new char[] { 'y', 'ｙ' });
		replaceCharacters.add(new char[] { 'z', 'ｚ' });
	}

	/**
	 * 文字列に含まれている引数 source の文字を、引数 dist の文字列に変更する
	 *
	 * @param source
	 *            変更前の文字。
	 * @param dist
	 *            変更後の文字。
	 * @param stringSource
	 *            リプレース対象の文字列全体
	 * @return 変更後の文字列。引数 stringSource の参照を返却する
	 */
	public static char[] replace(char source, char dist, char[] stringSource) {
		for (int idx = 0; idx < stringSource.length; idx++) {
			if (stringSource[idx] == source) {
				stringSource[idx] = dist;
			}
		}
		return stringSource;
	}

	static int addressSeparatePoint(String address) {
		Pattern ptn = Pattern.compile("[0-9０-９]+([-ー－‐｜|-][0-9０-９]+)+");
		Matcher mtc = ptn.matcher(address);
		int rtn = -1;
		if (mtc.find()) {
			// rtn = mtc.start();
			rtn = mtc.end();
		}
		return rtn;
	}
}
