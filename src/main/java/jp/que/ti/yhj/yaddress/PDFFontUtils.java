package jp.que.ti.yhj.yaddress;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

public class PDFFontUtils {

	public static enum FontEmbded {
		/** OS依存しない */
		NOT_EMBEDDED(false), //
		/** OS依存する */
		EMBEDDED(true);

		final boolean embed;

		FontEmbded(boolean embed) {
			this.embed = embed;
		};

	}

	public static String DFKai_SB = "c:/windows/fonts/kaiu.ttf,0";
	public static String MS_GOTHIC = "c:/windows/fonts/msgothic.ttc,0";
	public static String MS_P_GOTHIC = "c:/windows/fonts/msgothic.ttc,1";
	public static String MS_MINCHO = "c:/windows/fonts/msmincho.ttc,0";
	public static String KANTEIRYU = "c:/windows/fonts/JKAN00H.TTC,0";
	public static String AR_GYOUSYO = "c:/windows/fonts/JSKA00H.TTC,0";
	public static String AR_P_GYOUSYO = "c:/windows/fonts/JSKA00H.TTC,1";

	public static String HEISEIKAKUGO_W5 = "HeiseiKakuGo-W5";
	public static String HEISEIMIN_W3 = "HeiseiMin-W3";
	public static String ENCODING_UNI_JIS_UCS2_H = "UniJIS-UCS2-H";

	public static String ENCODING_UNI_JIS_UCS2_V = "UniJIS-UCS2-V";

	/**
	 * DFKai_SB
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return HeiseiKakuGo-W5(OS依存論理フォント)
	 */
	public static final BaseFont baseFontDFKai_SB(boolean vertical) {
		final String fontName = DFKai_SB;
		return createUniJisNotEmbeddedBaseFont(fontName, vertical);
	}

	/**
	 * HEISEIMIN_W3
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return HEISEIMIN_W3(OS非依存論理フォント)
	 */
	public static final BaseFont baseFontHeiseiMin_W3(boolean vertical) {
		final String fontName = HEISEIMIN_W3;
		return createUniJisNotEmbeddedBaseFont(fontName, vertical);
	}

	/**
	 * HeiseiKakuGo-W5
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return HeiseiKakuGo-W5(OS非依存論理フォント)
	 */
	public static final BaseFont baseFontHeiseiKakuGo_W5(boolean vertical) {
		final String fontName = HEISEIKAKUGO_W5;
		return createUniJisNotEmbeddedBaseFont(fontName, vertical);
	}

	/**
	 * MS 明朝
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return MS_MINCHO(Windows 依存)
	 */
	public static final BaseFont baseFontMsMincho(boolean vertical) {
		final String fontName = MS_MINCHO;
		return createUnicodeEmbeddedBaseFont(fontName, vertical);
	}

	/**
	 * MS P ゴシック
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return MS P ゴシック(Windows 依存)
	 */
	public static final BaseFont baseFontMsPGothic(boolean vertical) {
		final String fontName = MS_P_GOTHIC;
		return createUnicodeEmbeddedBaseFont(fontName, vertical);
	}

	//msmincho.ttc
	/**
	 * MS ゴシック
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return MS ゴシック(Windows 依存)
	 */
	public static final BaseFont baseFontMsGothic(boolean vertical) {
		final String fontName = MS_GOTHIC;
		return createUnicodeEmbeddedBaseFont(fontName, vertical);
	}


	/**
	 * AR 行楷書体H
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return MS AR 行楷書体H(Windows 依存)
	 */
	public static final BaseFont baseFontArGyousyoH(boolean vertical) {
		final String fontName = AR_GYOUSYO;
		return createUnicodeEmbeddedBaseFont(fontName, vertical);
	}

	/**
	 * AR P 行楷書体H
	 *
	 * @param vertical
	 *            縦書き用である場合、true。そうでない場合、false
	 * @return MS AR P 行楷書体H(Windows 依存)
	 */
	public static final BaseFont __baseFontArPGyousyoH(boolean vertical) {
		final String fontName = AR_P_GYOUSYO;
		return createUnicodeEmbeddedBaseFont(fontName, vertical);
	}

	private static BaseFont createUnicodeEmbeddedBaseFont(String fontName,
			boolean vertical) {
		if (vertical) {
			return createUnicodeVerticalBaseFont(fontName, FontEmbded.EMBEDDED);
		} else {
			return createUnicodeHorizontalBaseFont(fontName,
					FontEmbded.EMBEDDED);
		}
	}

	public static BaseFont createUnicodeHorizontalBaseFont(String fontName,
			FontEmbded fontEmbedded) {
		BaseFont bf;
		try {
			bf = BaseFont.createFont(//
					fontName, BaseFont.IDENTITY_H, fontEmbedded.embed);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return bf;
	}

	public static BaseFont createUnicodeVerticalBaseFont(String fontName,
			FontEmbded fontEmbedded) {
		BaseFont bf;
		try {
			bf = BaseFont.createFont(//
					fontName, BaseFont.IDENTITY_V, fontEmbedded.embed);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bf;
	}

	public static BaseFont createUniJisHorizontalBaseFont(String fontName,
			FontEmbded fontEmbedded) {
		BaseFont bf;
		try {
			bf = BaseFont.createFont(//
					fontName, ENCODING_UNI_JIS_UCS2_H, fontEmbedded.embed);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return bf;
	}

	private static BaseFont createUniJisNotEmbeddedBaseFont(String fontName,
			boolean vertical) {
		if (vertical) {
			return createUniJisVerticalBaseFont(fontName,
					FontEmbded.NOT_EMBEDDED);
		} else {
			return createUnicodeHorizontalBaseFont(fontName,
					FontEmbded.NOT_EMBEDDED);
		}

	}

	public static BaseFont createUniJisVerticalBaseFont(String fontName,
			FontEmbded fontEmbedded) {
		BaseFont bf;
		try {
			bf = BaseFont.createFont(//
					fontName, ENCODING_UNI_JIS_UCS2_V, fontEmbedded.embed);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bf;
	}

	private PDFFontUtils() {
	}

}
