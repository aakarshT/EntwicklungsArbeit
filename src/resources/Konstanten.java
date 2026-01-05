package resources;

public class Konstanten {
    // --- Main Class ---
    public static final String MAIN_MSG_DEBUG_DIR = "[Debug] Working Directory: ";
    public static final String MAIN_MSG_START = ">>> Starting Windkraftanlange Analysis <<<";
    public static final String MAIN_MSG_LOAD_PATH = "Loading CSV from: ";
    public static final String MAIN_MSG_LOAD_SUCCESS = "Loaded %d entries successfully in %d ms.%n";
    public static final String MAIN_ERR_LOAD = "Error loading CSV file: ";
    public static final String MAIN_MSG_EXIT = "Exiting...";
    public static final String MAIN_ERR_INVALID_OPT = "Invalid, Please choose a valid option";

    public static final String CMD_1 = "1";
    public static final String CMD_2 = "2";
    public static final String CMD_3 = "3";
    public static final String CMD_4 = "4";
    public static final String CMD_5 = "5";
    public static final String CMD_6 = "6";
    public static final String CMD_Q = "q";
    public static final String CMD_EXIT = "exit";

    // --- General Constants ---
    public static final String SEPARATOR = "--------------------------------------------------";
    public static final String SEPARATOR_LONG = "--------------------------------------------------------------------------";
    public static final String UNIT_MS = " ms";                 //Miliseconds
    public static final String UNIT_MW = " MW";                 //Megawatt
    public static final long NANO_TO_MILLI = 1_000_000;
    public static final double SCALE_FACTOR_10 = 10.0;

    // --- Files & Paths ---
    public static final String DIR_SRC = "src";
    public static final String DIR_RESOURCES = "src/resources"; // Correct path for your folder structure
    public static final String FILE_CSV = "Windkraftanlagen_DE.csv";

    // --- UI Strings ---
    public static final String UI_MENU_SEPARATOR = "-----------------------------------------";
    public static final String UI_MENU_HEADER = " Please select a task:";
    public static final String UI_MENU_OPT_1 = " 1 - Aufgabe 1";
    public static final String UI_MENU_OPT_2 = " 2 - Aufgabe 2";
    public static final String UI_MENU_OPT_3 = " 3 - Aufgabe 3";
    public static final String UI_MENU_OPT_4 = " 4 - Aufgabe 4";
    public static final String UI_MENU_OPT_5 = " 5 - Aufgabe 5";
    public static final String UI_MENU_OPT_6 = " 6 - Aufgabe 6";
    public static final String UI_MENU_OPT_Q = " q - Quit";
    public static final String UI_PROMPT_CHOICE = "Choice: ";

    // --- CSV Loader ---
    public static final String ERR_CSV_PATH_NULL = "CSV Path should not be empty";
    public static final String REGEX_CSV_SPLIT = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String STR_QUOTE = "\"";
    public static final String STR_EMPTY = "";
    public static final char CHAR_COMMA = ',';
    public static final char CHAR_DOT = '.';
    public static final int CSV_MIN_COLUMNS = 12;

    // CSV Column Indices
    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_BAUJAHR = 2;
    public static final int COL_LEISTUNG = 3;
    public static final int COL_ANZAHL = 4;
    public static final int COL_TYP = 5;
    public static final int COL_ORT = 6;
    public static final int COL_LANDKREIS = 7;
    public static final int COL_BREITE = 8;
    public static final int COL_LAENGE = 9;
    public static final int COL_BETREIBER = 10;
    public static final int COL_BEMERKUNG = 11;

    // --- Model Constants ---
    public static final String ERR_NULL_TECH_DATA = "TechnischeDaten should not be null";
    public static final String ERR_NULL_STANDORT = "Standort should not be null";
    public static final double TECH_DEFAULT_POWER = 0.0;
    public static final int TECH_DEFAULT_YEAR = 0;

    // --- Aufgabe 1:  ---
    public static final String A1_HEADER = "=== Aufgabe 1 ===";
    public static final String A1_ERR_NO_DATA = "Error: No data loaded!";
    public static final String A1_STATUS_OK = "Status: Data Model & CSV Loader are working.";
    public static final String A1_MSG_ENTRIES = "Total Entries Loaded: ";
    public static final String A1_MSG_PARKS = "Total Windparks:     ";
    public static final String A1_MSG_DURATION = "Loading Duration:     ";
    public static final String A1_MSG_SAMPLE = "Sample Data (First 3 entries):";
    public static final String A1_FORMAT_ENTRY = "ID: %d | Name: %s | Typ: %s%n";
    public static final int A1_SAMPLE_LIMIT = 3;

    // --- Aufgabe 2: ---
    public static final String A2_HEADER = "=== Aufgabe 2 ===";
    public static final String A2_MSG_FINISHED = "Correction process finished.";
    public static final String A2_MSG_CORRECTED = "Total Corrected entries: ";
    public static final String A2_MSG_DURATION = "Time taken for correction: ";

    public static final float GEO_EARTH_RADIUS_KM = 6371.0F;            //Radius of earth
    public static final float GEO_LAT_MIN = 47.0F;                      //Minimum value of latitude for germany
    public static final float GEO_LAT_MAX = 55.5F;                      //Maximum value of latitude for germany
    public static final float GEO_LON_MIN = 5.8F;                       //Minimum value of longitude for germany
    public static final float GEO_LON_MAX = 15.1F;                      //Maximum value of longitude for germany

    // --- Aufgabe 3:  ---
    public static final String A3_HEADER = "=== Aufgabe 3: ===";
    public static final String A3_ERR_NO_DATA = "Error: No data available.";
    public static final String A3_MSG_NO_VALID_YEAR = "No valid year data found.";
    public static final String A3_MSG_ANALYSIS_HEADER = "Data Analysis of Construction Years:";
    public static final String A3_SEPARATOR_SHORT = "------------------------------------";
    public static final String A3_MSG_TOTAL = " Total Turbines with Data: ";
    public static final String A3_MSG_OLDEST = " Oldest Turbine Built:     ";
    public static final String A3_MSG_NEWEST = " Newest Turbine Built:     ";
    public static final String A3_FORMAT_AVG = " Average Construction Year: %.2f%n";

    public static final int YEAR_MIN_VALID = 1900;
    public static final int YEAR_MAX_VALID = 2025;

    // --- Aufgabe 4:  ---
    public static final String A4_HEADER = "=== Aufgabe 4: ===";
    public static final String A4_ERR_NO_DATA = "Error: No data available for analysis.";
    public static final String A4_MSG_TOTAL_POWER = "Total Installed Power:   %.2f MW%n";
    public static final String A4_MSG_TOTAL_COUNT = "Total Number of Turbines: %d%n";
    public static final String A4_MSG_STRONGEST_HEADER = "Strongest Windpark:";
    public static final String A4_LBL_NAME = " Name:  ";
    public static final String A4_LBL_POWER = " Power: ";
    public static final String A4_LBL_TYPE = " Type:  ";

    // --- Aufgabe 5:  ---
    public static final String A5_HEADER = "=== Aufgabe 5: ===";
    public static final String A5_MSG_PHASE1 = "Phase 1: Repairing by Name Clusters...";
    public static final String A5_MSG_PHASE2 = "Phase 2: Repairing by Geographic Graph...";
    public static final String A5_MSG_SUMMARY = "Repair Summary:";
    public static final String A5_LBL_FIX_CLUSTER = " - Fixed via Name Grouping:  ";
    public static final String A5_LBL_FIX_GRAPH = " - Fixed via Graph Neighbor: ";
    public static final String A5_LBL_TIME = " - Total Time:               ";

    public static final String REGEX_BRACKET_COUNT = "\\((\\d+)";
    public static final String BRACKET_OPEN = "(";
    public static final float A5_MAX_DIST_KM = 15.0F;
    public static final float A5_POWER_MIN_VALID = 0.1F;
    public static final float A5_POWER_MAX_VALID = 10.0F;
    public static final int GRAPH_EDGE_DIVISOR = 2;

    // --- Aufgabe 6:  ---
    public static final String A6_HEADER = "=== Aufgabe 6 ===";
    public static final String A6_TABLE_FORMAT = "%-20s | %-10s | %-15s | %-15s%n";
    public static final String A6_COL_MANUFACTURER = "Manufacturer";
    public static final String A6_COL_COUNT = "Count";
    public static final String A6_COL_DAYS = "Days Needed";
    public static final String A6_COL_WEEKS = "Weeks (approx)";
    public static final String A6_REGEX_CLEAN = "[^a-zA-Z0-9-]";
    public static final String A6_ROW_FORMAT = "%-20s | %-10d | %-15d | %-15.1f%n";
    public static final String A6_ELLIPSIS = "...";

    public static final int A6_TURBINES_PER_DAY = 4;
    public static final float A6_DAYS_PER_WEEK = 5.0F;
    public static final int A6_TOP_LIMIT = 10;
    public static final int A6_MIN_NAME_LEN = 2;
    public static final int A6_TRUNCATE_LEN = 20;


}