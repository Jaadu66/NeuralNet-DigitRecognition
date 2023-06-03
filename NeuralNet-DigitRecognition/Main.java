package Coursework2_CST3170;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    //Test and Train Data/Index Variables
    private static double[][] trainData = new double[2810][65];
    private static double[][] testData = new double[2810][65];

    //Score calculation variables
    private static double foldScore = 0;
    private static double firstFoldScore = 0;
    private static double secondFoldScore = 0;
    private static double foldAverage = 0;

    //Hidden layer numbers
    private static int numOfHiddenLayers=100;
    private static int numOfOutput=10;

    //Hidden Layer Array
    private static double [] outputHidden = new double [numOfHiddenLayers];
    //Output Layer Output Array
    private static double [] outputOutput = new double [numOfOutput];
    //Hidden nodes delta array
    private static double [] deltaHiddenLayer = new double [numOfHiddenLayers];
    //Output nodes delta array
    private static double [] deltaOutputNodes = new double [numOfOutput];

    //total input rows
    private static int numOfInput=64;

    //hidden and output layer number of weights variables
    private static int numOfHiddenLayerWeights = numOfInput+1;
    private static int numOfOutputWeights = numOfHiddenLayers+1;

    //hidden weights array
    private static double [][] hiddenLayerWeights = new double [numOfHiddenLayers][numOfHiddenLayerWeights];
    private static double [][] outputWeights = new double [numOfOutput][numOfOutputWeights];

    //confusion matrix array
    private static int [][]confusionMatrix = new int [10][10];

    //default learning rate
    private static double learningRate = 0.09;
    //number of test to perform two-fold
    private static int testNum=0;

    public static void main(String[] args) throws Exception{
        // Current time
        long start = System.currentTimeMillis();
        Thread.sleep(300);
        //Run Two-Fold test loop
        for(testNum = 0; testNum <= 1; testNum++){
            //Execute functions below
            ReadDataSets(testNum);
            trainPerceptrons();
            testPerceptron();
        }
        // Determining the time after the functions have been executed
        long end = System.currentTimeMillis();
        // calculating and converting the time difference into seconds
        float sec = (end - start) / 1000F;
        System.out.println("Time spent on execution "+ sec + " seconds");
    }


//      This function is unit activation
    private static double dynamicNeuralNetwork(double learningRateInput) {
        double unitActivation;
        unitActivation= 1 + Math.exp(-learningRateInput);
        return 1/unitActivation;
    }


//      This function computes the output of the hidden layer.
    private static void calculateHiddenLayerOutput(double[] image) {
        double totalHL;
        for (int i = 0; i < numOfHiddenLayers; i++) {
            totalHL = 0;
            for (int k =   0; k <  numOfHiddenLayerWeights-1; k++ ){
                totalHL +=image[k]*hiddenLayerWeights[i][k];
            }
            totalHL += hiddenLayerWeights[i][numOfHiddenLayerWeights -1 ];
            outputHidden[i] = dynamicNeuralNetwork(totalHL);
        }
    }


//      This function calculates output-layer output

    private static void estimateOutputLayerOutput() {
        double totalOL;
        for (int i = 0; i < numOfOutput; i++) {
            totalOL = 0;
            for (int k =   0; k <  numOfOutputWeights-1; k++ ) {
                totalOL += outputHidden[k]*outputWeights[i][k];
            }
            totalOL += outputWeights[i][numOfOutputWeights -1];
            outputOutput[i] = dynamicNeuralNetwork(totalOL);
        }
    }



//     This function computes delta nodes in the output layer.

    private static void calculateOutputLayerDeltas(double digit) {
        double result;
        for (int i = 0; i < outputOutput.length;i++) {
            if(i == digit) {
                result=1;
            }
            else {
                result=0;
            }
            deltaOutputNodes[i]= outputOutput[i]*(1-outputOutput[i])*(outputOutput[i]-result);
        }
    }


//     This function computes delta nodes in the hidden layer.

    private static void calculateHiddenLayerDeltas() {
        for (int i = 0; i < numOfHiddenLayers; i++) {
            double sum = 0;
            for (int k = 0; k < numOfOutput; k++) {
                sum += deltaOutputNodes[k] * outputWeights[k][i];
            }
            deltaHiddenLayer[i] = sum * (1 - outputHidden[i]) * outputHidden[i];
        }
    }


//      This function changes the weight of nodes in the output layer and the hidden layer.

    private static void updateOutputAndHiddenLayerWeights() {
        for (int i = 0; i < numOfOutput; i++) {
            for (int k = 0; k < numOfOutputWeights - 1; k++) {
                double delta = -learningRate * deltaOutputNodes[i] * outputHidden[k];
                outputWeights[i][k] += delta;
            }
            outputWeights[i][numOfOutputWeights - 1] -= learningRate * deltaOutputNodes[i];
        }
    }



//    This function is for hidden layer weight update.
    private static void updateHiddenLayerWeights(double[] input) {
    for (int i = 0; i < numOfHiddenLayers; i++) {
        for (int k = 0; k < numOfHiddenLayerWeights - 1; k++) {
            double delta = -learningRate * deltaHiddenLayer[i] * input[k];
            hiddenLayerWeights[i][k] += delta;
        }
        hiddenLayerWeights[i][numOfHiddenLayerWeights - 1] -= learningRate * deltaHiddenLayer[i];
    }
}




//     Generate random weights for epoch

    private static void randomWeights() {
        double range = 0.5;
        for (int hiddenLayer = 0; hiddenLayer < numOfHiddenLayers; hiddenLayer++) {
            for (int inputLayer = 0; inputLayer < numOfHiddenLayerWeights; inputLayer++) {
                hiddenLayerWeights[hiddenLayer][inputLayer] = Math.random() * range * 2 - range;
            }
        }
        for (int outputLayer = 0; outputLayer < numOfOutput; outputLayer++) {
            for (int outputWeight = 0; outputWeight < numOfOutputWeights; outputWeight++) {
                outputWeights[outputLayer][outputWeight] = Math.random() * range * 2 - range;
            }
        }
    }



//       Train the network weights for the Perceptron.

    private static void trainPerceptrons() {
        randomWeights();
        for (int i = 0; i < 500; i++){
            for(int eachimage=0; eachimage<2810; eachimage++) {
                calculateHiddenLayerOutput(trainData[eachimage]);
                estimateOutputLayerOutput();
                calculateOutputLayerDeltas(trainData[eachimage][64]);
                calculateHiddenLayerDeltas();
                updateOutputAndHiddenLayerWeights();
                updateHiddenLayerWeights(trainData[eachimage]);
            }
        }
    }



//      Two-Fold test function
    private static void testPerceptron() {
        int outputNode = 0;
        int foldScore = 0;

        for (int i = 0; i < 2810; i++) {
            calculateHiddenLayerOutput(testData[i]);
            estimateOutputLayerOutput();

            outputNode = getLargestOutputNode();

            if (outputNode == testData[i][64]) {
                foldScore++;
            }

            incrementConfusionMatrix(outputNode, (int) testData[i][64]);
        }

        System.out.println("No of correct digit: " + foldScore);
        foldAverage += foldScore;

        if (testNum == 0) {
            firstFoldScore += foldScore;
            foldScore = 0;
        } else {
            secondFoldScore += foldScore;

            System.out.println(" *-----------------------------------------------*");
            System.out.println(" | Percentage of The First Fold: " + firstFoldScore * 100 / 2810 + "    |");
            System.out.println(" | Percentage of The Second Fold: " + secondFoldScore * 100 / 2810 + "   |");
            System.out.println(" *| Average Folds Percentage: " + foldAverage * 100 / 5620 + " |* ");
            System.out.println(" x-----------------------------------------------x");
        }
    }

    public static int getLargestOutputNode() {
        int outputNode = 0;

        for (int k = 0; k < numOfOutput; k++) {
            if (outputOutput[k] > 0.5) {
                outputNode = k;
            }
        }

        return outputNode;
    }

    public static void incrementConfusionMatrix(int outputNode, int actual) {
        confusionMatrix[outputNode][actual]++;
    }



    //      This function reads data sets and runs addLineToData
    private static void ReadDataSets(int testNum) throws Exception{
        String trainPath, testPath;
        if(testNum==0) {
            trainPath = "C:\\\\Users\\\\Luciu\\\\Desktop\\\\cw2DataSet2.csv";
            testPath = "C:\\\\Users\\\\Luciu\\\\Desktop\\\\cw2DataSet1.csv";
        }
        else {
            trainPath = "C:\\\\Users\\\\Luciu\\\\Desktop\\\\cw2DataSet1.csv";
            testPath = "C:\\\\Users\\\\Luciu\\\\Desktop\\\\cw2DataSet2.csv";
        }

        addLineToData(trainPath, trainData);
        addLineToData(testPath, testData);

        System.out.println("----------------------------------------------------------");
        System.out.println("Performing training and testing on Perceptron. Please wait... Fold/Test Number:" + testNum);
        System.out.println("----------------------------------------------------------");
    }


//     This function adds line to data
    private static void addLineToData(String fileName, double[][] trainData) throws Exception {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        int theLine = 0;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            for (int i = 0; i < 65; i++) {
                trainData[theLine][i] = Integer.parseInt(data[i]);
            }
            theLine++;
        }
    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    }
}



}