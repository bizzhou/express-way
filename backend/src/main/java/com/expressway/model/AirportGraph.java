package com.expressway.model;

import java.util.*;

public class AirportGraph {
    Map<AirportNode, List<AirportNode>> airportList;         // Map<airport, destination airports>

    int routeCounter;
    ArrayList<ArrayList<AirportNode>> pathList;             // a list of list of paths
    ArrayList<AirportNode> path;                            // a list of path

    public AirportGraph() {
        super();
        this.airportList = new HashMap<>();
    }

    /**
     * Add node to vertex. vertex to node forms a route
     * @param vertex from
     * @param node to
     */
    public void addNewNode(AirportNode vertex, AirportNode node, Leg leg) {

        vertex = getNode(vertex);
        vertex.getLegs().add(leg);
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
//        System.out.println("Adding " + airportList.get(vertex).get(airportList.get(vertex).size()-1).getName()
//                + ", ID# " + airportList.get(vertex).get(airportList.get(vertex).size()-1)
//                + ", to " + vertex.getName() + ", ID# " + vertex);
    }

//
//    /**
//     * Take two vertices, using BFS to check if there is a path between the two vertices
//     * @param departure departure airport
//     * @param destination destination airport
//     * @return a path
//     */
//    public boolean isConnected(String departure, String destination) {
//        LinkedList<AirportNode> queue = new LinkedList<>();
//
//        // get the node
//        AirportNode departureAirport = getNodeFromName(departure);
//        AirportNode destinationAirport = getNodeFromName(destination);
//
//        // perform BFS
//        // mark current node as visited and enqueue it
//        departureAirport.setVisited(true);
//        queue.add(departureAirport);
//
//        AirportNode currAirport = null;
//        List<AirportNode> adjAirports = null;
//        while(queue.size() != 0) {
//            currAirport = queue.poll();        // dequeue the head
//
//            // if curr airport is not a head (currAirport is not a key)
//            if (airportList.get(currAirport) == null) {
//                adjAirports.clear();
//                adjAirports.add(currAirport);
//            }
//            else
//                adjAirports = airportList.get(currAirport);
//
//            // if an adj node has not been visited, mark it as visited and enqueue it
//            AirportNode currAdjAirport;
//            for (int i = 0; i < adjAirports.size(); i++) {
//                currAdjAirport = adjAirports.get(i);
//
//                System.out.println(currAdjAirport.getName());
//
//                // if this adjacent node is the destination node
//                if (currAdjAirport.getName().equals(destinationAirport.getName())) {
//                    return true;
//                }
//
//                // continue BFS
//                if (!currAdjAirport.isVisited()) {
//                    currAdjAirport.setVisited(true);
//                    queue.add(currAdjAirport);
//                }
//
//            }
//
//        }
//
//        return false;
//    }

    /**
     * DFS
     * @param departure
     * @param destination
     * @return
     */
    public ArrayList<ArrayList<AirportNode>> getRoutes(String departure, String destination) {
        LinkedList<AirportNode> queue = new LinkedList<>();

        // get the nodes
        AirportNode from = getNodeFromName(departure);
        AirportNode to = getNodeFromName(destination);

        path = new ArrayList<>();
        pathList = new ArrayList();
        routeCounter = 0;
        getRouteHelper(from, to);

        return pathList;

    }

    public void getRouteHelper(AirportNode fromNode, AirportNode toNode) {
        // mark current node as visited and store it in path
        fromNode.setVisited(true);
        path.add(fromNode);

        if (fromNode.getName().equals(toNode.getName())) {
            ArrayList pathClone = (ArrayList)path.clone();      //path will eventually be empty
            pathList.add(routeCounter, pathClone);
        }
        else {
            // recur for all adjacent vertices
            if (airportList.get(fromNode) != null) {
                for (int i = 0; i < airportList.get(fromNode).size(); i++) {
                    if (!airportList.get(fromNode).get(i).isVisited())
                        getRouteHelper(airportList.get(fromNode).get(i), toNode);
                }
            }
        }

        path.remove(fromNode);
        fromNode.setVisited(false);

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

//    Test method
//    public static void main(String[] args) {
//        AirportGraph graph = new AirportGraph();
//        graph.addNewNode(new AirportNode("JFK"), new AirportNode("LGA"));
//        graph.addNewNode(new AirportNode("LGA"), new AirportNode("LAX"));
//        graph.addNewNode(new AirportNode("LAX"), new AirportNode("NRT"));
//        graph.addNewNode(new AirportNode("JFK"), new AirportNode("SFO"));
//        graph.addNewNode(new AirportNode("SFO"), new AirportNode("BOS"));
//        graph.addNewNode(new AirportNode("BOS"), new AirportNode("LHR"));
//        graph.addNewNode(new AirportNode("SFO"), new AirportNode("JFK"));
//        graph.addNewNode(new AirportNode("JFK"), new AirportNode("TNR"));
//        graph.addNewNode(new AirportNode("LGA"), new AirportNode("TNR"));
//        ArrayList<ArrayList<AirportNode>> paths = graph.getRoutes("SFO", "TNR");
//
//        for (int i = 0; i < paths.size(); i++) {
//            for (int j = 0; j < paths.get(i).size(); j++)
//                System.out.print(paths.get(i).get(j).getName() + " - > ");
//            System.out.println();
//        }
//
//    }

}
