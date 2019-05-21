package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	
	//modello->stato del sitema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	//tipo di evento/coda priority->un solo evento: i migranti quando arrivano in uno stato di spostano
	private PriorityQueue<Evento> queue;
	
	//parametri della simulazione
	private int NUM_MIGRANTI=1000;	
	private Country partenza; 		//lo stato di partenza dei migranti
	
	//valori in output
	private int  tempo;			//numero di passaggi per arrivare ad avere tutti i migranti stanziati in qualche stato
	private Map<Country, Integer> mappaStanziati;		//stato, numero dei migranti stanziati nel paese->indica tutti gli stati che hanno dei migranti stanziati

	
	
	//due metodi: uno di inizializzazione e uno di run
	
	
	/**
	 * metodo di inizializzazione, suppongo che inizialmente tutti gli stati non posseggano migranti
	 * e che il tempo in cui si verifica il primo evento sia 1
	 * @param partenza
	 * @param grafo
	 */
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		
		this.partenza=partenza;
		this.grafo=grafo;
		
		//impostazione dello stato iniziale
		this.tempo=1;
		mappaStanziati=new HashMap<Country, Integer>();
		
		for(Country c:this.grafo.vertexSet()) {
			mappaStanziati.put(c, 0);		//si suppone che inizialmente non ci siano migranti stanziati negli stati
		}
		
		queue=new PriorityQueue<Evento>();
		
		//inserisco il primo evento
		this.queue.add(new Evento(tempo, NUM_MIGRANTI, partenza));
		
		
	}
	
	
	
	/**
	 * Eseguo l'evento: se i migranti sono maggiori del numero di stati confinanti allo stato di partenza allora il 50% dii loro migra
	 * 
	 */
	
	public void run() {
		//Estraggo un evento per volta dalla coda e lo eseguo, finchè la coda non è vuota
		Evento e;
		
		while((e=queue.poll())!=null) {
			
			//eseguo
			this.tempo=e.getTempo();			//ogni evento fatto il tempo aumenta di uno
			int nPersone=e.getNumMigranti();
			Country stato=e.getStato();
			List<Country> listaConfinanti=Graphs.neighborListOf(grafo, stato);	//trovare i vertici adiacenti al vertice passato come paramentro
			
			int migranti=(nPersone/2)/listaConfinanti.size();		//approssimizzazione già troncata per difetto, num migranti per ogni stato confinante
			
			if(migranti>0) {		//le persone si possono muovere
				
				for(Country confinante: listaConfinanti) {
					queue.add(new Evento(e.getTempo(), migranti, confinante));
				}
				
				int stanziati=nPersone-migranti*listaConfinanti.size();		//persone che rimangono in ogni stato, dopo che il 50% di loro è migrato in un altro stato confinante
				this.mappaStanziati.put(stato, this.mappaStanziati.get(stato)+stanziati);
			}
			
			
		}
		
	}



	/**
	 * Restituisco il numero di passaggi necessari per far si che tutti i migranti si stanzino in uno stato (tempo)
	 * @return tempo
	 */

	public int getLastTempo() {
		return this.tempo;
	}




	/**
	 * metodo che ritorna gli stati con il loro numero di migranti stanziati
	 * @return map
	 */
	public Map<Country, Integer> getStanziati() {
		return mappaStanziati;
	}
	
	
	
	
	
	
}
