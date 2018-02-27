package it.smartcommunitylab.csvjoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class App {

    public static void main(String[] args) throws Exception {
        final String weekNumber = "24";

        String commonPath =
                "/home/mirko/data/PROGETTI/gamification/p&g2017/week" + weekNumber + "/";
        String path1 = commonPath + "classifica_w" + weekNumber + ".csv";
        String path2 = commonPath + "players.csv";

        BufferedReader breader = new BufferedReader(new FileReader(path1));
        FileWriter writer =
                new FileWriter(commonPath + "finalClassificationWeek" + weekNumber + ".csv");
        String csvRow = null;
        String csvRowResult = null;
        String oldScore = null;
        int position = 1;
        int generalPosition = 1;
        while ((csvRow = breader.readLine()) != null) {
            String[] fields = csvRow.split(",");
            String playerId = fields[1];
            String rowScore = fields[0];
            if (!rowScore.equals(oldScore)) {
                position = generalPosition;
                oldScore = rowScore;
            }
            csvRowResult = String.format("%s,%s,", position, csvRow);
            generalPosition++;
            BufferedReader breader2 = new BufferedReader(new FileReader(path2));
            String csvRow2 = null;
            boolean joint = false;
            while ((csvRow2 = breader2.readLine()) != null) {
                if (csvRow2.contains("\"" + playerId + "\"")) {
                    csvRow2 = csvRow2.replaceAll("\"", "");
                    csvRowResult += csvRow2;
                    writer.write(csvRowResult + "\n");
                    joint = true;
                    break;
                }
            }
            if (!joint) {
                System.out.println(String.format("WARNING row %s not joint", playerId));
            }
        }

        writer.flush();
        writer.close();
    }

}
