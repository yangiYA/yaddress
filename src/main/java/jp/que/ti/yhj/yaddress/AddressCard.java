package jp.que.ti.yhj.yaddress;

/**
 * 1名の住所情報を意味するオブジェクト
 *
 * @author toto
 *
 */
public class AddressCard {

	/** 郵便番号 */
	private int zipCode;
	/** 住所 */
	private String address = "";
	/** 名 */
	private String firstName;
	/** 姓 */
	private String lastName;
	/** 名 */
	private String subFirstName;
	/** 姓 */
	private String subLastName;
	/** 敬称 */
	private String honorificTitle = "様";

	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @return the firstName
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @return the honorificTitle
	 */
	public final String getHonorificTitle() {
		return honorificTitle;
	}

	/**
	 * @return the lastName
	 */
	public final String getLastName() {
		return lastName;
	}

	public String getSubFirstName() {
		return subFirstName;
	}

	public String getSubLastName() {
		return subLastName;
	}

	/**
	 * @return the zipCode
	 */
	public final int getZipCode() {
		return zipCode;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public final void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 敬称の setter
	 *
	 * @param honorificTitle
	 *            敬称
	 */
	public final void setHonorificTitle(String honorificTitle) {
		this.honorificTitle = honorificTitle;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSubFirstName(String subFirstName) {
		this.subFirstName = subFirstName;
	}

	public void setSubLastName(String subLastName) {
		this.subLastName = subLastName;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public final void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
}
