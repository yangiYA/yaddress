package jp.que.ti.yhj.yaddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final String tsvFilePath = "address.tsv";
		final String outPdfPath = "postCard.pdf";
		final String propPath = AddressProperties.DEFAULT_PROPERTIES_PATH;
		File in = new File(tsvFilePath);
		File out = new File(outPdfPath);
		File prpFl = new File(propPath);

		System.out.println("input      file = " + in.getAbsolutePath());
		System.out.println("output     file = " + out.getAbsolutePath());
		System.out.println("properties file = " + prpFl.getAbsolutePath());
		System.out.println("  properties encording is UTF-8 only !!!");

		AddressProperties prop = new AddressProperties(propPath);

		AddressWriterInPostcard awip = new AddressWriterInPostcard(out);
		AddressCard fromAd = new AddressCard();
		fromAd.setZipCode(prop.fromAddressZipCode());
		fromAd.setAddress(prop.fromAddress());
		fromAd.setLastName(prop.fromAddressLastName());
		fromAd.setSubLastName(prop.fromAddressLastNameSub());
		fromAd.setFirstName(prop.fromAddressFirstName());
		fromAd.setSubFirstName(prop.fromAddressFirstNameSub());
		fromAd.setHonorificTitle("");

		List<AddressCard> tos = new LinkedList<AddressCard>();
		String record = "";

		BufferedReader bfrd = awip.bufferedReader(in, "Shift-JIS");
		try {

			do {
				try {
					record = bfrd.readLine();
				} catch (IOException e) {
					throw new RuntimeException("ファイルの読み込みに失敗しました。file="
							+ tsvFilePath);
				}

				System.out.println(record);
				if (record == null || "".equals(record)
						|| record.startsWith("ID")) {
				} else {
					String[] spltd = null;
					spltd = spilitTSVRecord(record);
					AddressCard toAd = new AddressCard();
					toAd.setZipCode((int) (Float.parseFloat(spltd[8])));
					toAd.setAddress(spltd[9]);
					toAd.setFirstName(spltd[2]);
					toAd.setLastName(spltd[1]);
					toAd.setHonorificTitle("様");
					// toAd.setSubFirstName(subFirstName);
					// toAd.setSubLastName(subLastName);

					tos.add(toAd);
				}
			} while (record != null);
		} finally {
			if (!(bfrd == null)) {
				try {
					bfrd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		awip.write(tos, fromAd);
		awip.close();
	}

	public static String[] spilitTSVRecord(String str) {
		if (str == null || str.trim().length() == 0) {
			throw new IllegalArgumentException("引数が異常です。[str=" + str + "]");
		}
		return str.split("\\t");
	}

}
