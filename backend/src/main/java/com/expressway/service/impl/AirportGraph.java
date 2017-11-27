package com.expressway.service.impl;

import com.expressway.model.AirportNode;

import java.util.*;

public class AirportGraph {
    Map<AirportNode, List<AirportNode>> airportList;         // Map<airport, destination airports>

    int verticesCount;
    int edgeCount;

    public AirportGraph() {
        super();
        this.airportList = new HashMap<>();
        verticesCount = 0;
        edgeCount = 0;
    }

    /**
     * Add a new node for the given vertex, i.e add a new destination airport for the given airport
     * Vertex to node connection forms an edge
     * @param vertex departure airport
     * @param node destination airport
     */
    public void addNewNode(AirportNode vertex, AirportNode node) {
        for (AirportNode a : airportList.keySet()) {
            // if departure airport object already exists
            if (a.getName().equals(vertex.getName()))
                vertex = a;
            if (a.getName().equals(node.getName()))
                node = a;

            // if destination airport object already exists
            for (int i = 0; i < airportList.get(a).size(); i++) {
                if (airportList.get(a).get(i).getName().equals(vertex.getName())) {
                    vertex = airportList.get(a).get(i);
                }
            }
            for (int i = 0; i < airportList.get(a).size(); i++) {
                if (airportList.get(a).get(i).getName().equals(node.getName())) {
                    node = airportList.get(a).get(i);
                }
            }
        }

        List<AirportNode> nodes = airportList.get(vertex);

        // if list is empty, create new
        if (nodes == null || nodes.isEmpty()) {
            nodes = new ArrayList<AirportNode>();
            nodes.add(node);
            verticesCount++;
        }
        else {
            nodes.add(node);
        }
        edgeCount++;
        airportList.put(vertex, nodes);
        System.out.println("Adding " + airportList.get(vertex).get(airportList.get(vertex).size()-1) + " to " + vertex);
    }

    /**
     * Take two vertices, using BFS to check if there is a path between v1 and v2
     * @param departure departure airport
     * @param destination destination airport
     * @return a path
     */
    public boolean isConnected(String departure, String destination) {
        LinkedList<AirportNode> queue = new LinkedList<>();

        // get the node from name
        AirportNode departureAirport = null, destinationAirport = null;
        for(AirportNode a : airportList.keySet()) {
            if (a.getName().equals(departure)) {
                departureAirport = a;
            }
            if (a.getName().equals(destination)) {
                destinationAirport = a;
            }
        }

        // mark current node as visited and enqueue it
        departureAirport.setVisited(true);
        queue.add(departureAirport);

        AirportNode currAirport = null;
        List<AirportNode> adjAirports;
        while(queue.size() != 0) {
            currAirport = queue.poll();        // dequeue the head

            System.out.println("TESTING: " + currAirport.getName());
            adjAirports = airportList.get(currAirport);

            // if an adj node has not been visited, mark it as visited and enqueue it
            AirportNode currAdjAirport;
            for (int i = 0; i < adjAirports.size(); i++) {
                currAdjAirport = adjAirports.get(i);

                // if this adjacent node is the destination node
                if (currAdjAirport.getName().equals(destinationAirport.getName())) {
                    System.out.println(" " + currAdjAirport.getName() + " ");
                    return true;
                }

                // continue BFS
                if (!currAdjAirport.isVisited()) {
                    currAdjAirport.setVisited(true);
                    queue.add(currAdjAirport);
                }

            }

        }

        return false;
    }

    //Test method
    public static void main(String[] args) {
        AirportGraph graph = new AirportGraph();
        graph.addNewNode(new AirportNode("LAX", 50), new AirportNode("SFO", 100));
        graph.addNewNode(new AirportNode("LAX", 50), new AirportNode("BOS", 100));
        graph.addNewNode(new AirportNode("SFO", 50), new AirportNode("JFK", 100));
        graph.addNewNode(new AirportNode("JFK", 50), new AirportNode("LAX", 100));

        if (graph.isConnected("LAX", "JFK"))
            System.out.println("there is a path");
        else
            System.out.println("no path");

    }

}
