package jp.que.ti.yhj.yaddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class AddressWriterInPostcard42011NewYearTest  extends TestCase{
	public void testDo() {
		AddressWriterInPostcard awip = new AddressWriterInPostcard(new File(
				"2011NewYear.pdf"));
		final String tsvFilePath = "data/addressFor2011.tsv";
		AddressCard fromAd = new AddressCard();
		fromAd.setZipCode(2300078);
		fromAd.setAddress("沖縄市大田区山中1-2-34川中ABマンションズ987");
		fromAd.setLastName("山川");
		fromAd.setSubLastName("山川");
		fromAd.setFirstName("太郎");
		fromAd.setSubFirstName("花子");
		fromAd.setHonorificTitle("");

		List<AddressCard> tos = new LinkedList<AddressCard>();
		String record = "";

		BufferedReader bfrd = awip.bufferedReader(new File(tsvFilePath),
				"Shift-JIS");
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

	private String[] spilitTSVRecord(String str) {
		if (str == null || str.trim().length() == 0) {
			throw new IllegalArgumentException("引数が異常です。[str=" + str + "]");
		}
		return str.split("\\t");
	}


}
