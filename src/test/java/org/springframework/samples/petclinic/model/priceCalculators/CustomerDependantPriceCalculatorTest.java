package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDependantPriceCalculatorTest {

	public Pet dog;
	public Pet cat;
	public Pet cheetah;
	public Pet persianCat;
	public Pet pitbull;
	public Pet germanShepard;
	public Pet chihuahua;
	public PetType canines;
	public PetType felines;
	public UserType newUser;
	public UserType goldUser;
	public UserType silverUser;
	public ArrayList<Pet> pets;
	public CustomerDependentPriceCalculator priceCalculator;


	@Before
	public void setUp() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		dog = mock(Pet.class);
		canines = mock(PetType.class);
		when(dog.getType()).thenReturn(canines);
		when((canines.getRare())).thenReturn(true);
		Date dogDate = formatter.parse("04-11-2015");
		when(dog.getBirthDate()).thenReturn(dogDate);

		cat = mock(Pet.class);
		felines = mock(PetType.class);
		when(cat.getType()).thenReturn(felines);
		when(felines.getRare()).thenReturn(false);
		Date catDate = formatter.parse("07-06-2020");
		when(cat.getBirthDate()).thenReturn(catDate);

		cheetah = mock(Pet.class);
		when(cheetah.getType()).thenReturn(canines);
		Date cheetahDate = formatter.parse("14-08-2020");
		when(cheetah.getBirthDate()).thenReturn(cheetahDate);

		persianCat = mock(Pet.class);
		when(persianCat.getType()).thenReturn(felines);
		Date persianCatDate = formatter.parse("03-02-2017");
		when(persianCat.getBirthDate()).thenReturn(persianCatDate);

		pitbull = mock(Pet.class);
		when(pitbull.getType()).thenReturn(canines);
		Date pitbullDate = formatter.parse("14-08-2021");
		when(pitbull.getBirthDate()).thenReturn(pitbullDate);

		germanShepard = mock(Pet.class);
		when(germanShepard.getType()).thenReturn(canines);
		Date germanDate = formatter.parse("14-12-2019");
		when(germanShepard.getBirthDate()).thenReturn(germanDate);

		chihuahua = mock(Pet.class);
		when(chihuahua.getType()).thenReturn(canines);
		Date chihuahuaDate = formatter.parse("14-10-2020");
		when(chihuahua.getBirthDate()).thenReturn(chihuahuaDate);

		newUser = UserType.NEW;
		goldUser = UserType.GOLD;
		silverUser = UserType.SILVER;
		pets = new ArrayList<>();
		priceCalculator = new CustomerDependentPriceCalculator();
	}

	@Test
	public void noDiscountGoldUserTest(){
		pets.add(dog);
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		assertEquals(2532,priceCalculator.calcPrice(pets,500,500,goldUser));
	}

	@Test
	public void discountNewUserTest(){
		pets.add(dog);
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		pets.add(pitbull);
		pets.add(germanShepard);
		pets.add(chihuahua);
		assertEquals(5307,priceCalculator.calcPrice(pets,500,500,newUser));
	}

	@Test
	public void discountNotNewUserTest(){
		pets.add(dog);
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		pets.add(pitbull);
		pets.add(germanShepard);
		pets.add(chihuahua);
		assertEquals(4448,priceCalculator.calcPrice(pets,500,500,goldUser));
	}

	@Test
	public void noDiscountNotGoldUserTest(){
		pets.add(dog);
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		assertEquals(2540,priceCalculator.calcPrice(pets,500,500,silverUser));
	}



	@After
	public void tearDown(){
		dog = null;
		cat = null;
		cheetah = null;
		persianCat = null;
		pitbull = null;
		germanShepard = null;
		chihuahua = null;
		canines = null;
		felines = null;
		newUser = null;
		goldUser = null;
		silverUser = null;
		pets = null;
		priceCalculator = null;

	}

}
