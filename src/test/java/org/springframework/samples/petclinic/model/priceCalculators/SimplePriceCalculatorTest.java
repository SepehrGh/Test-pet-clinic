package org.springframework.samples.petclinic.model.priceCalculators;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

public class SimplePriceCalculatorTest {

	public Pet dog;
	public Pet cat;
	public PetType canines;
	public PetType felines;
	public UserType newUser;
	public UserType goldUser;
	public ArrayList<Pet> pets;
	public SimplePriceCalculator priceCalculator;


	@Before
	public void setUp() {
		dog = mock(Pet.class);
		canines = mock(PetType.class);
		when(dog.getType()).thenReturn(canines);
		when((canines.getRare())).thenReturn(true);


		cat = mock(Pet.class);
		felines = mock(PetType.class);
		when(cat.getType()).thenReturn(felines);
		when(felines.getRare()).thenReturn(false);

		newUser = UserType.NEW;
		goldUser = UserType.GOLD;
		pets = new ArrayList<>();
		pets.add(dog);
		pets.add(cat);
		priceCalculator = new SimplePriceCalculator();
	}

	@Test
	public void calcPriceTest(){
		assertEquals(1520,priceCalculator.calcPrice(pets,500,500,newUser));
	}

	@After
	public void tearDown(){
		dog = null;
		cat = null;
		canines = null;
		felines = null;
		newUser = null;
		goldUser = null;
		pets = null;
		priceCalculator = null;
	}
}
