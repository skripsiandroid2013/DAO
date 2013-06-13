


import static org.junit.Assert.assertNotNull;
import id.ac.itats.skripsi.dao.util.parsing.GraphBuilder;
import id.ac.itats.skripsi.dao.util.parsing.model.Graph;

import org.junit.Test;

public class TestGraph {

	@Test
	public void testGraphBuilder() throws Exception {
		GraphBuilder builder = new GraphBuilder("data/surabaya.osm");
		Graph graph = builder.getGraph();
		assertNotNull("graph loaded !", graph);

	
	}

}
