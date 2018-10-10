package co.simplon.tools;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Class to have some controls to check that values of data are conform
 * 
 * @author Frederick
 *
 */
public class ControlsData {
	/**
	 * control for name value (lastName or FirstName)
	 * 
	 * @param name
	 * @return
	 */
	public static boolean controlName(String name) {
		String regex = "[a-zA-ZÀ-ÖØ-öø-ÿ -]*";
		return Pattern.matches(regex, name);
	}

	/**
	 * control for phone number value
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean controlPhone(String phone) {
		String regex = "[a-zA-Z0-9 +-]*";
		return Pattern.matches(regex, phone);
	}

	/**
	 * control for Date value
	 * 
	 * @param date
	 * @return
	 */
	public static boolean controlDate(Date date) {
		String regex = "[a-zA-Z0-9: ]*";
		return Pattern.matches(regex, date.toString());
	}

	/**
	 * control for street value
	 * 
	 * @param street
	 * @return
	 */
	public static boolean controlStreet(String street) {
		String regex = "['a-zA-ZÀ-ÖØ-öø-ÿ0-9 -]*";
		return Pattern.matches(regex, street);
	}

	/**
	 * control for zipCode value
	 * 
	 * @param zipCode
	 * @return
	 */
	public static boolean controlZipCode(String zip) {
		String regex = "[a-zA-Z0-9 -]*";
		return Pattern.matches(regex, zip);
	}

	/**
	 * control for city value
	 * 
	 * @param city
	 * @return
	 */
	public static boolean controlCity(String city) {
		String regex = "['a-zA-ZÀ-ÖØ-öø-ÿ -]*";
		return Pattern.matches(regex, city);
	}
	
	/**
	 * control for mail value
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean controlMail(String mail) {
		String regex = "[a-zA-ZÀ-ÖØ-öø-ÿ0-9!?#=+&$@_.-]*";
		return Pattern.matches(regex, mail);
	}
	
	/**
	 * control for category name
	 * @param name
	 * @return
	 */
	public static boolean controlCatName(String name) {
		String regex = "[\\'a-zA-ZÀ-ÖØ-öø-ÿ0-9, -]*";
		if (name.length() <= LengthDatas.DATA_CAT_NAME && Pattern.matches(regex, name)) {return true; } else {return false; }
	}
	
	/**
	 * control for picture name of category
	 * @param name
	 * @return
	 */
	public static boolean controlPicNameOfCat(String name) {
		String regex = "[a-zA-Z0-9.]*";
		if (name.length() <= LengthDatas.DATA_CAT_PICNAME && Pattern.matches(regex, name)) {return true; } else {return false; }
	}
	
	/**
	 * control for colors name
	 * @param colorName
	 * @return
	 */
	public static boolean controlColorName(String colorName) {
		String regex = "[\\'a-zA-ZÀ-ÖØ-öø-ÿ, -]*";
		if (colorName.length() <= LengthDatas.DATA_COLOR_NAME && Pattern.matches(regex, colorName)) {return true; } else {return false; }
	}

}
