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


    }


    /**
     * DFS
     * @param departure
     * @param destination
     * @return
     */
    public ArrayList<ArrayList<AirportNode>> getPaths(String departure, String destination) {
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

    // todo exclude extra legs
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

}
