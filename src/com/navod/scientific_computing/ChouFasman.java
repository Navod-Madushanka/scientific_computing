package com.navod.scientific_computing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChouFasman {
    private static final Map<Character, double[]> PROPENSITY_TABLE = new HashMap<>();

    static {
        PROPENSITY_TABLE.put('A', new double[]{1.42, 0.83, 0.66});
        PROPENSITY_TABLE.put('C', new double[]{0.70, 1.19, 1.19});
        PROPENSITY_TABLE.put('D', new double[]{1.01, 0.54, 1.46});
        PROPENSITY_TABLE.put('E', new double[]{1.51, 0.37, 0.74});
        PROPENSITY_TABLE.put('F', new double[]{1.13, 1.38, 0.60});
        PROPENSITY_TABLE.put('G', new double[]{0.57, 0.75, 1.56});
        PROPENSITY_TABLE.put('H', new double[]{1.00, 0.87, 0.95});
        PROPENSITY_TABLE.put('I', new double[]{1.08, 1.60, 0.47});
        PROPENSITY_TABLE.put('K', new double[]{1.16, 0.74, 1.01});
        PROPENSITY_TABLE.put('L', new double[]{1.34, 1.22, 0.59});
        PROPENSITY_TABLE.put('M', new double[]{1.45, 1.05, 0.60});
        PROPENSITY_TABLE.put('N', new double[]{0.67, 0.89, 1.56});
        PROPENSITY_TABLE.put('P', new double[]{0.57, 0.55, 1.52});
        PROPENSITY_TABLE.put('Q', new double[]{1.11, 1.10, 0.98});
        PROPENSITY_TABLE.put('R', new double[]{0.98, 0.93, 0.95});
        PROPENSITY_TABLE.put('S', new double[]{0.77, 0.75, 1.43});
        PROPENSITY_TABLE.put('T', new double[]{0.83, 1.19, 0.96});
        PROPENSITY_TABLE.put('V', new double[]{1.06, 1.70, 0.50});
        PROPENSITY_TABLE.put('W', new double[]{1.08, 1.37, 1.01});
        PROPENSITY_TABLE.put('Y', new double[]{0.69, 1.47, 1.14});
    }

    private static boolean isValidSequence(String sequence) {
        for (char aa : sequence.toCharArray()) {
            if (!PROPENSITY_TABLE.containsKey(aa)) {
                return false; // Invalid amino acid found
            }
        }
        return true; // All characters are valid
    }

    private static String predictSecondaryStructure(String sequence) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sequence.length(); i++) {
            if (i < sequence.length() - 3) { // Check up to the last four residues
                double[] helixSum = {0}, sheetSum = {0}, turnSum = {0};

                // Calculate propensities in a window of four residues
                for (int j = i; j < i + 4; j++) {
                    char aa = sequence.charAt(j);
                    helixSum[0] += PROPENSITY_TABLE.get(aa)[0];
                    sheetSum[0] += PROPENSITY_TABLE.get(aa)[1];
                    turnSum[0] += PROPENSITY_TABLE.get(aa)[2];
                }

                // Determine structure type based on highest propensity
                if (helixSum[0] > sheetSum[0] && helixSum[0] > turnSum[0]) {
                    result.append("H"); // Helix
                } else if (sheetSum[0] > helixSum[0] && sheetSum[0] > turnSum[0]) {
                    result.append("E"); // Sheet
                } else {
                    result.append("T"); // Turn
                }
            } else {
                result.append("."); // No defined structure for last residues
            }
        }

        return result.toString();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the amino acid sequence: ");
        String sequence = scanner.nextLine().toUpperCase().trim();

        if (!isValidSequence(sequence)) {
            System.out.println("Invalid amino acid sequence.");
            return;
        }

        String predictedStructure = predictSecondaryStructure(sequence);

        System.out.println("Predicted Secondary Structure: " + predictedStructure);
    }
}
