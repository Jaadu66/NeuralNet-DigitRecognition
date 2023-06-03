# NeuralNet-DigitRecognition
This repository contains a Java implementation of a Multilayer Perceptron algorithm for classification tasks. The Multilayer Perceptron is a type of artificial neural network that can learn and make predictions based on input data.

# Algorithm Overview
Multilayer Perceptron (MLP)
Multilayer Perceptron is a type of artificial neural network that is often used in machine learning and deep learning. Think of it like a series of interconnected "neurons" that help make predictions or decisions based on input data.
The network is made up of an input layer, one or more hidden layers, and an output layer. The input layer takes in the data, the hidden layers process and transform it, and the output layer provides the final prediction or classification result.
The "neurons" in the MLP model are connected by weighted connections that can be adjusted during the training process. This means the network can "learn" from past examples and improve its predictions over time. The training process involves finding the best weights and biases for the neurons to minimize the difference between the predicted output and the actual output.
While MLP is a simple and effective tool, it has some limitations. It can only model linear relationships between the input and output variables, so some additional techniques may be needed to improve its performance.
In short, MLP is a robust algorithm in the field of neural networking that has been successfully used in many real-world problems. However, it's essential to choose the right design and training method for each problem to ensure the best results.

## How it Works

The code provided implements a Multilayer Perceptron using a two-fold cross-validation approach. It consists of the following steps:

1. **Data Preparation**: Prepare your training and test data sets in CSV format. Each row represents a data instance, where the first 64 columns contain input features, and the last column represents the target class.

2. **Training**: The code trains the Multilayer Perceptron by iteratively adjusting the weights between nodes in the network. It uses the training data set to update the weights and improve the model's ability to classify the input data.

3. **Testing**: After training, the code evaluates the accuracy of the Multilayer Perceptron on the test data set. It feeds the test instances through the trained network and compares the predicted classes with the actual classes to measure the model's performance.

4. **Cross-Validation**: The code uses a two-fold cross-validation approach, where the data set is split into two parts. It performs training and testing on each part separately, allowing for a more robust evaluation of the model's accuracy.

## Usage

To use this code, follow these steps:

1. Prepare your training and test data sets in CSV format, ensuring that the last column represents the target class.

2. Update the file paths of your data sets in the `ReadDataSets` function of the `Main` class. Set the paths for the training and test data sets in the variables `trainPath` and `testPath`, respectively.

3. Compile and run the `Main` class. The code will train the Multilayer Perceptron using the training data and evaluate its accuracy on the test data.

4. Examine the output to see the accuracy of the Multilayer Perceptron for each fold of the two-fold cross-validation, as well as the average accuracy across both folds.

## Repository Structure

The repository is structured as follows:

- `Main.java`: Contains the main class that coordinates the training and testing of the Multilayer Perceptron.
- `cw2DataSet1.csv`: Sample training data set in CSV format.
- `cw2DataSet2.csv`: Sample test data set in CSV format.
- `README.md`: This readme file.
