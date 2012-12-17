package jp.que.ti.yhj.yaddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class AddressProperties {
	public static final String DEFAULT_PROPERTIES_PATH = "address.props";
	public static final String DEFAULT_PROPERTIES_CHARSET = "UTF-8";

	private Properties prop;

	public AddressProperties(String propertieFilePath, String charsetName) {
		Properties pr = new Properties();

		String pPath = propertieFilePath;
		if (isEmpty(pPath)) {
			pPath = DEFAULT_PROPERTIES_PATH;
		}

		final File f = new File(pPath);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		String enc = charsetName;
		if (isEmpty(charsetName)) {
			enc = DEFAULT_PROPERTIES_CHARSET;
		}

		try {
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis, enc);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ファイルが存在しません。 FilePath="
					+ propertieFilePath, e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("サポートされていないエンコーディング指定です。 charsetName="
					+ charsetName + " : FilePath=" + propertieFilePath, e);
		}

		try {
			pr.load(isr);
			prop = pr;
		} catch (IOException e) {
			throw new RuntimeException("ファイルが読み込めません。 FilePath="
					+ propertieFilePath, e);
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {// なにもできないのでもみ消し
					System.out.println("クローズに失敗しました。 FilePath="
							+ propertieFilePath);
					e.printStackTrace();
				}
			}
		}
	}

	public AddressProperties(String propertieFilePath) {
		this(propertieFilePath, DEFAULT_PROPERTIES_CHARSET);
	}

	public AddressProperties() {
		this(DEFAULT_PROPERTIES_PATH, DEFAULT_PROPERTIES_CHARSET);
	}

	public String fromAddress() {
		return getString("from.address", "");
	}

	public int fromAddressZipCode() {
		return getInt("from.address.zip.code", 0);
	}

	public String fromAddressLastName() {
		return getString("from.address.last.name", "");
	}

	public String fromAddressLastNameSub() {
		return getString("from.address.last.name.sub", "");
	}

	public String fromAddressFirstName() {
		return getString("from.address.first.name", "");
	}

	public String fromAddressFirstNameSub() {
		return getString("from.address.first.name.sub", "");
	}

	public String getString(String key, String defaultValue) {
		final String val = prop.getProperty(key);
		if (isEmpty(val)) {
			return defaultValue;
		} else {
			return val;
		}
	}

	public int getInt(String key, int defaultValue) {
		final String val = prop.getProperty(key);
		if (isEmpty(val)) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(val);
			} catch (Exception e) {
				return defaultValue;
			}
		}
	}

	private boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
