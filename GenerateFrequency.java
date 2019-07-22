package tests;

import java.util.ArrayList;
import java.util.Random;

import networks.Network;
import networks.ScaleFree;

public class GenerateFrequency {

	public static void main(String[] args) {
		
		// generating the network
		Network startingNetwork = new Network();
		startingNetwork.addVertex();
		startingNetwork.addEdge(0, 1, 1, 1);
		int size = 100;
		ScaleFree.build(startingNetwork, size, 3.5); // initial network	
		
		double[] frequencies = new double[size];
		for (int i = 0; i < size; i++) frequencies[i] = 1.0 / size;
		
		Random generator = new Random();
		
		int updateFreq = 100;
		int recordEndTimes = 1000;
		int simulateTime = 100;
		
		for (int i = 0; i < updateFreq; i++) {
			
			double[] endPoint = new double[size];
			
			for (int j = 0; j < recordEndTimes; j++) {
				
				double value = generator.nextDouble();
				double accumulator = 0;
				int counter = 0; // where the carrier is established
				
				// establishing where the carrier starts
				while (value > accumulator) {
					accumulator += frequencies[counter];
					counter++;
				}
				counter--;
				
				// moving the carrier around
				for (int k = 0; k < simulateTime; k++) {
					ArrayList<Integer> friends = startingNetwork.vertices.get(counter).friends;
					counter = friends.get(generator.nextInt(friends.size()));
				}
				
				// recording where carrier ends
				endPoint[counter]++;
				
			}
			
			for (int j = 0; j < size; j++) {
				frequencies[j] = endPoint[j] / recordEndTimes;
			}
			
		}
		
		for (int i = 0; i < size; i++) {
			System.out.println(frequencies[i]);
		}

	}

}
