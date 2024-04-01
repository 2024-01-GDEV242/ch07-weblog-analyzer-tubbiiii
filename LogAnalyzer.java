/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Chris Compierchio
 * @version     2024.30.3
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * @param fileName The name of the log file to be analyzed.
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }
    public int numberOfAccesses() {
        int total = 0;
        // Add the value in each element of hourCounts to total.
        for (int count : hourCounts) {
            total += count;
        }
        return total;
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int Day = entry.getDay();
            dayCounts[Day]++;
        }
    }
     /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
    }
    /**
     * Finds the busiest hour based on the number of accesses recorded in the log file.
     * If multiple hours have the same maximum access count, the first encountered hour is returned.
     * 
     * @return The hour (in 24-hour format) with the highest number of accesses.
     */
    public int busiestHour() {
        int busiestHour = 0;
        int maxAccesses = 0;

        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > maxAccesses) {
                maxAccesses = hourCounts[hour];
                busiestHour = hour;
            }
        }

        return busiestHour;
    }   
    /**
     * Finds the quietest hour based on the number of accesses recorded in the log file.
     * If multiple hours have the same minimum access count, the first encountered hour is returned.
     * 
     * @return The hour (in 24-hour format) with the lowest number of accesses.
     */
    public int quietestHour() {
        int quietestHour = 0;
        int minAccesses = Integer.MAX_VALUE;

        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < minAccesses) {
                minAccesses = hourCounts[hour];
                quietestHour = hour;
            }
        }

        return quietestHour;
    }
    /**
     * Finds the busiest two-hour period based on the number of accesses recorded in the log file.
     * If multiple two-hour periods have the same maximum access count, the first encountered period is returned.
     * 
     * @return The starting hour (in 24-hour format) of the busiest two-hour period.
     */
    public int busiestTwoHour() {
        int busiestPeriodStartHour = 0;
        int maxAccesses = 0;

        // Loop through all possible two-hour periods.
        for (int hour = 0; hour < 24; hour++) {
            int accesses = hourCounts[hour] + hourCounts[(hour + 1) % 24]; // Sum up accesses for current and next hour.
            if (accesses > maxAccesses) {
                maxAccesses = accesses;
                busiestPeriodStartHour = hour;
            }
        }

        return busiestPeriodStartHour;
    }
     /**
     * Finds the busiest day based on the number of accesses recorded in the log file.
     * 
     * @return The day of the month with the highest number of accesses.
     */
        public int busiestDay() {
        // Ensure dayCounts is not null
        if (dayCounts == null) {
            throw new IllegalStateException("dayCounts array is not initialized");
        }

        int busiestDay = 0;
        int maxAccesses = 0;

        for (int day = 0; day < hourCounts.length; day++) {
            if (hourCounts[day] > maxAccesses) {
                maxAccesses = hourCounts[day];
                busiestDay = day;
            }
        }

        return busiestDay;
    }
    /**
     * Finds the quietest day based on the number of accesses recorded in the log file.
     * 
     * @return The day of the month with the lowest number of accesses.
     */
        public int quietestDay() {
        int quietestDay = 0;
        int minAccesses = Integer.MAX_VALUE;

        for (int day = 0; day < hourCounts.length; day++) {
            if (hourCounts[day] < minAccesses) {
                minAccesses = hourCounts[day];
                quietestDay = day;
            }
        }

        return quietestDay;
    }
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
     /**
     * Determines the month based on the day of the month.
     * Assumes a 30-day month.
     * 
     * @param day The day of the month (1-30).
     * @return The index of the month (0 for January, 11 for December).
     */
    private int determineMonth(int day) {
        // Day ranges for each month (assuming a 30-day month)
        int[] monthRanges = {0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330};

        // Determine the month based on the day
        for (int month = 0; month < monthRanges.length; month++) {
            if (day <= monthRanges[month]) {
                return month;
            }
        }

        // Default return value (should never be reached)
        return 0;
    }

    /**
     * Finds the busiest month based on the total number of accesses recorded in the log file.
     *
     * @return The index of the busiest month (0 for January, 11 for December).
     */
    public int busiestMonth() {
            
            int busiestMonth = 0;
            int maxAccesses = 0;
    
        for (int month = 0; month < dayCounts.length; month++) {
            if (hourCounts[month] > maxAccesses) { // Changed dayCounts to hourCounts
                maxAccesses = hourCounts[month];
                busiestMonth = month;
            }
        }

        return busiestMonth;
    }
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
