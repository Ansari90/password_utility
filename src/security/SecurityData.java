package security;

public class SecurityData {

	public static final int KEY_SIZE = 1024;
	public static final String ALGORITHM = "AES";
	public static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
	public static final String CHARSET = "ISO-8859-1";
	public static final String KEY_FILE = "keyFile.dat";
	public static final String PASSWORD_FILE = "yitrcedmp.dat";
	public static final String TEXT_FILE = "keepItSafe.txt";
	public static final String SEPAPRATOR = "---------------------";
	public static final String SECURE_IMPORT = "Secure Import";
	public static final String TEXT_IMPORT = "Text Import";
	
	//Strings for buttons and labels on the GUI
	public static final String TITLE = "Password Utility";
	public static final String SEARCH_FOR = "Search";
	public static final String NEW_ENTRY = "New Entry";
	public static final String SAVE_CHANGES = "Save Changes";
	public static final String COMMIT_CHANGES = "Commit Changes";
	public static final String FORWARD = "Next";
	public static final String BACKWARD = "Previous";
	public static final String EXPORT_TO_TEXT = "Create Backup";
	public static final String IMPORT_FROM_TEXT = "Read Backup";
	public static final String DELETE_ENTRY = "Delete Entry";
	public static final String ENTRY_NAME = "Entry Name: ";
	public static final String NO_RESULT = "Nothing with that name!";
	public static final int DEFUALT_COLUMNS = 5;
}
