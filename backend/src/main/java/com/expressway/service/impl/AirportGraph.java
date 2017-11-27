package com.expressway.service.impl;

import com.expressway.model.AirportNode;

import java.util.*;

public class AirportGraph {
    Map<AirportNode, List<AirportNode>> airportList;         // Map<airport, destination airports>

    public AirportGraph() {
        super();
        this.airportList = new HashMap<>();
    }

    public void addNewNode(AirportNode vertex, AirportNode node) {

        vertex = getNode(vertex);
        node = getNode(node);

        List<AirportNode> nodes = airportList.get(vertex);

        // if list is empty, create new
        if (nodes == null) {
            nodes = new ArrayList<AirportNode>();
            nodes.add(node);
        }
        else {
            nodes.add(node);
        }
        airportList.put(vertex, nodes);
        System.out.println("Adding " + airportList.get(vertex).get(airportList.get(vertex).size()-1).getName()
                + ", ID# " + airportList.get(vertex).get(airportList.get(vertex).size()-1)
                + ", to " + vertex.getName() + ", ID# " + vertex);
    }


    /**
     * Take two vertices, using BFS to check if there is a path between two vertices
     * @param departure departure airport
     * @param destination destination airport
     * @return a path
     */
    public boolean isConnected(String departure, String destination) {
        LinkedList<AirportNode> queue = new LinkedList<>();

        // get the node
        AirportNode departureAirport = getNodeFromName(departure);
        AirportNode destinationAirport = getNodeFromName(destination);

        // perform BFS
        // mark current node as visited and enqueue it
        departureAirport.setVisited(true);
        queue.add(departureAirport);

        AirportNode currAirport = null;
        List<AirportNode> adjAirports = null;
        while(queue.size() != 0) {
            currAirport = queue.poll();        // dequeue the head

            // if curr airport is not a head
            if (airportList.get(currAirport) == null) {
                adjAirports.clear();
                adjAirports.add(currAirport);
            }
            else
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

    /**
     * get the airport if it already exists
     * @param node
     * @return
     */
    public AirportNode getNode(AirportNode node) {
        for (AirportNode a : airportList.keySet()) {
            // if the node already exists
            if (a.getName().equals(node.getName()))
                return a;

            for (int i = 0; i < airportList.get(a).size(); i++) {
                if (airportList.get(a).get(i).getName().equals(node.getName())) {
                    return airportList.get(a).get(i);
                }
            }
        }
        return node;
    }

    public AirportNode getNodeFromName(String airportName) {
        for(AirportNode a : airportList.keySet()) {
            if (a.getName().equals(airportName)) {
                return a;
            }
            for (int i = 0; i < airportList.get(a).size(); i++) {
                if (airportList.get(a).get(i).getName().equals(airportName)) {
                    return airportList.get(a).get(i);
                }
            }
        }
        return null;
    }

    //Test method
    public static void main(String[] args) {
        AirportGraph graph = new AirportGraph();
        graph.addNewNode(new AirportNode("LAX", 50), new AirportNode("SFO", 100));
        graph.addNewNode(new AirportNode("LAX", 50), new AirportNode("BOS", 100));
        graph.addNewNode(new AirportNode("SFO", 50), new AirportNode("JFK", 100));
        graph.addNewNode(new AirportNode("JFK", 50), new AirportNode("PPP", 100));
        graph.addNewNode(new AirportNode("PPP", 50), new AirportNode("BBB", 100));

        if (graph.isConnected("LAX", "PPP"))
            System.out.println("there is a path");
        else
            System.out.println("no path");

    }

}
