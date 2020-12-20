package Game;
import api.*;
import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;

public class GraphDeserializer implements JsonDeserializer<directed_weighted_graph> {


    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonGraph = jsonElement.getAsJsonObject();
        directed_weighted_graph loaded_graph = new DWGraph_DS();
        JsonArray loaded_edges = jsonGraph.getAsJsonArray("Edges");
        JsonArray loaded_vertices = jsonGraph.getAsJsonArray("Nodes");

        graphBuilder(loaded_graph, loaded_edges, loaded_vertices);

        return loaded_graph;
    }

    public void graphBuilder(directed_weighted_graph loaded_graph, JsonArray loaded_edges, JsonArray loaded_vertices) {
        for (JsonElement node : loaded_vertices) {
            node_data n = new NodeData(((JsonObject) node).get("id").getAsInt());
            String location = ((JsonObject) node).get("pos").getAsString();
            String[] xyz = location.split(",");
            Point3D nLocation = new Point3D(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
            n.setLocation(nLocation);
            loaded_graph.addNode(n);
        }

        for (JsonElement edge : loaded_edges) {
            int src = ((JsonObject) edge).get("src").getAsInt();
            double w = ((JsonObject) edge).get("w").getAsDouble();
            int dest = ((JsonObject) edge).get("dest").getAsInt();
            edge_data json_edge = new EdgeData(src, dest, w);
            loaded_graph.connect(json_edge.getSrc(), json_edge.getDest(), json_edge.getWeight());

        }
    }
}