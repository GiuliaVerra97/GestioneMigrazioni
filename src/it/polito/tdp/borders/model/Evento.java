package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {

	
	//ci fossero più tipi di evento ci dovrebbe essere in questa classe un metodo enum con i diversi tipi di evento,
	// ma in questo caso c'è solo un tipo di evento
	
	private int tempo;
	private int numMigranti;  //numero di persone che sono arrivate in uno stato e si spostano 
	private Country stato;		//il paese in cui le persone arrivano
	
	
	
	public Evento(int tempo, int numMigranti, Country stato) {
		super();
		this.tempo = tempo;
		this.numMigranti = numMigranti;
		this.stato = stato;
	}
	
	
	
	public int getTempo() {
		return tempo;
	}
	public int getNumMigranti() {
		return numMigranti;
	}
	public Country getStato() {
		return stato;
	}
	
	
	@Override
	public int compareTo(Evento ev) {
		return this.tempo-ev.tempo;
	}
	
	
	
	
}
