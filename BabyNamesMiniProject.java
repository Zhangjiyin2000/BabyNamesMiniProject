
/**
 * Write a description of BabyNamesMiniProject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.*;

public class BabyNamesMiniProject {
    void totalBirths() {
        FileResource fr = new FileResource();
        int total = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            total++;
            if (rec.get(1).equals("M")) {
                totalBoys++;
            } else {
                totalGirls++;
            }
        }
        System.out.println("total: " + total);
        System.out.println("total boys: " + totalBoys);
        System.out.println("total girls: " + totalGirls);
        
    }
    
    void testTotalBirths() {
        totalBirths();
    }
    
    int getRank(int year, String name, String gender) {
       FileResource fr = new FileResource("testing/yob"+year+"short.csv"); 
       int rank = 0;
       for (CSVRecord rec : fr.getCSVParser(false)) {
           if (rec.get(1).equals(gender) ) {
               rank++;
               if (rec.get(0).equals(name) ) {
                   return rank;
               }
           }
       }
       return -1;
    }
    
    void testGetRank() {
        System.out.println(getRank(2012, "Mason", "M"));
        System.out.println(getRank(2012, "Mason", "F"));
    }
    
    String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource("testing/yob"+year+"short.csv"); 
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender) ) {
                count++;
                if (count == rank ) {
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
    void testGetName() {
        System.out.println(getName(2012, 2, "M"));
        System.out.println(getName(2012, 2, "F"));
    }
    
    void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name+" born in "+year+" would be "+newName+" if born in "+newYear);
    }
    
    void testWhatIsNameInYear() {
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }
    
    int yearOfHighestRank(String name, String gender) {
        int bestRankYear = -1;
        int bestRank = Integer.MAX_VALUE;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles() ) {
            int year = Integer.parseInt(f.getName().substring(3,7));
            int rank = getRank(year, name, gender);
            if (rank < bestRank && rank != -1) {
                bestRank = rank;
                bestRankYear = year;
            }
        }
        if(bestRankYear == -1) {
            return -1;
        }
        return bestRankYear;
    }
    
    void testYearOfHighestRank() {
        System.out.println("=====");
        System.out.println(yearOfHighestRank("Mason", "M"));
        System.out.println("=====");
    }
    
    double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int sum = -1;
        int count = 0;
        for (File f : dr.selectedFiles() ) {
            int year = Integer.parseInt(f.getName().substring(3,7));
            int rank = getRank(year, name, gender);
            if (rank != -1 ) {
                sum += rank;
                count++;
            }
        }
        if (sum == -1) {
            return -1.0;
        }
        return ((double) sum ) / count;
    }
    
    void testGetAverageRank() {
        System.out.println(getAverageRank("Jacob", "M"));
    }
    
    int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int totalBirth = 0;
        int rank = getRank(year, name, gender);
        if (rank == -1) {
            return -1;
        }
        System.out.println("Input rank: " + rank);
        String fileName = "testing/yob"+year+"short.csv";
        System.out.println(fileName);
        FileResource fr = new FileResource("testing/yob"+year+"short.csv");
        System.out.println("File: " + fr);
        for (CSVRecord rec : fr.getCSVParser(false) ) {
            System.out.println("Record: " + rec);
            String nameRec = rec.get(0);
            System.out.println("Name: " + nameRec);
            String genderRec = rec.get(1);
            System.out.println("Gender: " + genderRec);
            int rankRec = getRank(year, nameRec, genderRec);
            System.out.println("Rank: " + rankRec);
            System.out.println("genderRec == gender: " + genderRec.equals(gender));
            if (genderRec.equals(gender)) {
                if ( rankRec < rank ) {
                    totalBirth += Integer.parseInt(rec.get(2));
                }
            }
            
            
        }
        return totalBirth;
        
    }
    
    void testGetTotalBirthsRankedHigher() {
        System.out.println(getTotalBirthsRankedHigher(2012, "Ethan", "M"));
    }
}
