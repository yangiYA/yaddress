package jp.que.ti.yhj.yaddress;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class AddressWriterInPostcardTest extends TestCase {

	public void test_write() {
		File outFile = new File("testresult.pdf");
		AddressCard toTmp = null;

		List<AddressCard> toList = new LinkedList<AddressCard>();

		toTmp = new AddressCard();
		toTmp.setZipCode(9998888);
		toTmp.setAddress("東京都千代田区千代田1-9-777 ボヌールメゾン千代田ハウス101号");
		toTmp.setLastName("佐藤");
		toTmp.setFirstName("一郎");
		toTmp.setHonorificTitle("様");
		toList.add(toTmp);

		toTmp = new AddressCard();
		toTmp.setZipCode(1000001);
		toTmp.setAddress("東京都千代田区千代田1丁目 100-22-333");
		toTmp.setLastName("山田");
		toTmp.setFirstName("太郎左衛門");
		toTmp.setHonorificTitle("様");
		toList.add(toTmp);

		toTmp = new AddressCard();
		toTmp.setZipCode(1000002);
		toTmp.setAddress("東京都千代田区皇居外苑100-22-333");
		toTmp.setLastName("高橋");
		toTmp.setFirstName("一");
		toTmp.setHonorificTitle("様");
		toList.add(toTmp);

		AddressCard from = new AddressCard();
		from.setZipCode(1100002);
		from.setAddress("東京都台東区上野桜木9-100-22-333 ボヌールメゾン千代田ハウス101号");
		from.setLastName("夏目");
		from.setFirstName("漱石");
		from.setHonorificTitle("様");
		from.setSubLastName(from.getLastName());
		from.setSubFirstName("樹下無子");

		AddressWriterInPostcard awp = new AddressWriterInPostcard(outFile);
		try {
			awp.write(toList, from);
		} finally {
			awp.close();
		}

	}
}
