package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao;
	SimpleGraph<Album, DefaultEdge> graph;
	
	public Model() {
		dao = new ItunesDAO();
	}
	
	public SimpleGraph<Album, DefaultEdge> creaGrafo(int d){
		
		graph = new SimpleGraph<>(DefaultEdge.class);
		
		List<Album> vertex = new ArrayList<>(dao.getAllAlbums(d));
		Graphs.addAllVertices(graph, vertex);
		
		Map<Integer, Album> albumMap = new HashMap<>();
		
		for (Album a : graph.vertexSet())
			albumMap.put(a.getAlbumId(), a);
		
		dao.setAllTracks(albumMap);
		
		List<PlaylistTracks> playlists = new ArrayList<>(dao.getAllPlaylistsTracks());
		
		for (Album a1 : graph.vertexSet())
			for (Album a2 : graph.vertexSet())
				if ((!a1.equals(a2)) && (!graph.containsEdge(a2, a1)) )
					for (Integer i1 : a1.getTracksIds())
						for (Integer i2 : a2.getTracksIds())
							for (PlaylistTracks pt : playlists)
								if (pt.getTracks().contains(i1) && pt.getTracks().contains(i2))
									graph.addEdge(a2, a1);
		
		return graph;
		
	}
	
	private int componenteConnessa;
	private Set<Album> connessi;
	
	public int braniConnessi(Album a) {
		
		ConnectivityInspector<Album,DefaultEdge> conn = new ConnectivityInspector<Album,DefaultEdge>(graph);
		
		connessi = conn.connectedSetOf(a);
		
		componenteConnessa = connessi.size();
		
		int totBrani = 0;
		
		for (Album a1 : connessi) {
			totBrani += a1.getTracksIds().size();
		}
		
		return totBrani;
		
	}
	
	private Set<Album> soluzione;

	public Set<Album> getSequenza(Album a1, int dTOT){
		
		soluzione = new HashSet<Album>();
		
		Set<Album> parziale = new HashSet<>();
		
		parziale.add(a1);
		
		this.braniConnessi(a1);
		
		Set<Album> connessi = new HashSet<>(this.connessi);
		
		ricorsiva(parziale,connessi,dTOT);
		
		return soluzione;	
		
	}
	
	private void ricorsiva(Set<Album> parziale, Set<Album> connessi, int dTOT) {
		
		if (parziale.size() > soluzione.size())
			soluzione = new HashSet<>(parziale);
		
		for (Album a : connessi) {
			if ((!parziale.contains(a)) && (getDurataTot(parziale) + a.getDurata() <= dTOT)){
				parziale.add(a);
				ricorsiva(parziale,connessi,dTOT);
				parziale.remove(a);
			}
		}
		
	}
	
	private double getDurataTot(Set<Album> parziale) {
		
		double tot = 0.0;
		
		for (Album a : parziale)
			tot += a.getDurata();
		
		return tot;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ItunesDAO getDao() {
		return dao;
	}

	public void setDao(ItunesDAO dao) {
		this.dao = dao;
	}

	public SimpleGraph<Album, DefaultEdge> getGraph() {
		return graph;
	}

	public void setGraph(SimpleGraph<Album, DefaultEdge> graph) {
		this.graph = graph;
	}

	public int getComponenteConnessa() {
		return componenteConnessa;
	}

	public void setComponenteConnessa(int componenteConnessa) {
		this.componenteConnessa = componenteConnessa;
	}
	
}
