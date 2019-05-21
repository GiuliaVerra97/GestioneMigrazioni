package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable<CountryAndNumber> {
	
	private Country country ;			
	private int number ;		//numero degli stati a lui confinanti oppure num di immigrati stanziati nello stato, dipende dall'utilità
	
	public CountryAndNumber(Country country, int number) {
		super();
		this.country = country;
		this.number = number;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int compareTo(CountryAndNumber other) {
		// ordine DECRESCENTE per numero
		return -(this.number-other.number);
	}
	
	

}
