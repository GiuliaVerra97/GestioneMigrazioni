package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Simulatore sim=new Simulatore();
	private Graph<Country, DefaultEdge> graph ;
	private List<Country> countries ;
	private Map<Integer,Country> countriesMap ;
	
	public Model() {
		this.countriesMap = new HashMap<>() ;
		this.sim=new Simulatore();

	}
	
	
	
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), this.countriesMap.get(c.getState2no())) ;
		}
	}
	
	
	
	
	/**
	 * Trovo gli stati e il numero di stati a loro confinanti
	 * @return lista di {@link CountryAndNumber}
	 */
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list);
		return list ;
	}

	
	
	
	
	
	public Collection<Country> getCountries(){
		List<Country> lista=new ArrayList<>();
		
		for(Country c:this.countriesMap.values()) {
			lista.add(c);
		}
			
		Collections.sort( lista);
		return lista;
	}

	
	
	
	
	
	public void simula(Country partenza) {

		sim.init(partenza, this.graph);
		sim.run();
		
	}

	
	
	public int getLastTempo() {
		return this.sim.getLastTempo();
	}
	
	
	
	
	
	
	/**
	 * Metodo che mi permettde di capire quanti immigrati stanziati ci sono nello stato considerato
	 * @return lista di {@link CountyAndNumber}
	 */
	public List<CountryAndNumber> getStanziati(){
		Map<Country, Integer> stanziati=this.sim.getStanziati();
		List<CountryAndNumber> lista=new ArrayList<CountryAndNumber>();
		for(Country c: stanziati.keySet()) {
			CountryAndNumber cn=new CountryAndNumber(c, stanziati.get(c));
			lista.add(cn);
		}
		return lista;
	}
	
	
	
	/**
	 * Metodo che permette di vedere quali siano gli stati confinanti con lo stato passato come parametro
	 * @param c Country
	 * @return lista di Country
	 */

	public List<Country> trovaConfinanti(Country c){
		List<Country> lista=new ArrayList<Country>();
		lista=Graphs.neighborListOf(this.graph, c);
		return lista;
	}
	
	
	
	
}
