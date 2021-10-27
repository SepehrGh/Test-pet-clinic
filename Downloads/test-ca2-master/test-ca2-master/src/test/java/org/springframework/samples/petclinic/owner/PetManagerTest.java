package org.springframework.samples.petclinic.owner;


import org.junit.jupiter.api.*;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetManagerTest {
	private static PetTimedCache mockCache;
	private static OwnerRepository mockOwners;
	public static PetManager petManager;
	public static Pet dog;
	public static Pet cat;
	public static Pet duck;
	public static Owner owner;

	@BeforeEach
	public void setUp(){
		mockCache = mock(PetTimedCache.class);
		mockOwners = mock(OwnerRepository.class);
		petManager = new PetManager(mockCache,mockOwners, LoggerFactory.getLogger(PetManager.class));
		dog = new Pet();
		dog.setId(1);
		dog.setName("dog");
		cat = new Pet();
		cat.setId(2);
		cat.setName("cat");
		duck = new Pet();
		duck.setId(4);
		duck.setName("duck");
		owner = new Owner();
		owner.setId(1);
		when(mockOwners.findById(1)).thenReturn(owner);
		when(mockCache.get(1)).thenReturn(dog);
		when(mockCache.get(2)).thenReturn(cat);
		when(mockCache.get(4)).thenReturn(duck);
	}

	@Test
	public void testFindOwner(){
		// Mock
		// State verification
		// Classical
		assertEquals(owner,petManager.findOwner(1));
	}

	@Test
	public void testNewPet(){
		// Mock
		// State verification
		// Classical
		assertEquals(0,owner.getPets().size());
		petManager.newPet(owner);
		assertEquals(1,owner.getPets().size());
	}

	@Test
	public void testFindPet(){
		// Mock
		// State verification
		// Classical
		assertEquals(duck,petManager.findPet(4));
	}

	@Test
	public void testSavePet(){ // also state verification for part 3
		// Mock
		// State verification
		// Classical
		assertEquals(0,owner.getPets().size());
		Pet camel = new Pet();
		camel.setName("camel");
		petManager.savePet(camel,owner);
		assertEquals("camel",owner.getPets().get(0).getName());
	}

	@Test
	public void testSavePetBehavior(){
		// behavior verification for part 3
		// Mock
		// Behavior verification
		// Classical
		Pet shark = mock(Pet.class);
		Owner mockOwner = mock(Owner.class);
		petManager.savePet(shark,mockOwner);
		verify(mockOwner).addPet(shark);
		verify(mockCache).save(shark);
	}

	@Test
	public void testGetOwnerPets(){
		// Mock
		// State verification
		// Classical
		Pet camel = new Pet();
		camel.setName("camel");
		Pet monkey = new Pet();
		monkey.setName("monkey");
		Owner mockOwner = mock(Owner.class);
		mockOwner.setId(2);
		when(mockOwner.getPets()).thenReturn(Arrays.asList(camel, monkey));
		when(mockOwners.findById(2)).thenReturn(mockOwner);
		List<Pet> pets = petManager.getOwnerPets(2);
		List<String> petNames = new ArrayList<>();
		for (int i = 0; i < pets.size(); i++){
			petNames.add(pets.get(i).getName());
		}
		assertEquals(2,petManager.getOwnerPets(2).size());
		assertTrue(petNames.contains("monkey"));
		assertTrue(petNames.contains("camel"));
	}

	@Test
	public void testGetOwnerPetTypes(){
		// Mock
		// State verification
		// Classical
		Pet camel = new Pet();
		camel.setName("camel");
		PetType mammalType = new PetType();
		mammalType.setName("mammal");
		camel.setType(mammalType);
		Pet clownFish = new Pet();
		PetType fishType = new PetType();
		fishType.setName("fish");
		clownFish.setName("nemo");
		clownFish.setType(fishType);
		Owner mockOwner = mock(Owner.class);
		mockOwner.setId(2);
		when(mockOwner.getPets()).thenReturn(Arrays.asList(camel, clownFish));
		when(mockOwners.findById(2)).thenReturn(mockOwner);
		Set<PetType> pets = petManager.getOwnerPetTypes(2);
		List<String> petNames = new ArrayList<>();
		for (PetType type: pets){
			petNames.add(type.getName());
		}
		assertEquals(2,pets.size());
		assertTrue(petNames.contains("fish"));
		assertTrue(petNames.contains("mammal"));
	}

	@Test
	public void testGetVisitsBetween(){
		// Mock
		// State verification
		// Classical
		Visit mockVisit1 = mock(Visit.class);
		Visit mockVisit2 = mock(Visit.class);
		Visit mockVisit3 = mock(Visit.class);
		Visit mockVisit4 = mock(Visit.class);
		when(mockVisit1.getDate()).thenReturn(LocalDate.of(2021,9,25));
		when(mockVisit2.getDate()).thenReturn(LocalDate.of(2021,9,29));
		when(mockVisit3.getDate()).thenReturn(LocalDate.of(2018,8,7));
		when(mockVisit4.getDate()).thenReturn(LocalDate.of(2015,11,25));
		LocalDate startDate = LocalDate.of(2016,1,1);
		LocalDate endDate = LocalDate.of(2021,9,27);
		cat.setVisitsInternal(Arrays.asList(mockVisit1,mockVisit2,mockVisit3,mockVisit4));
		List<Visit> catVisits = petManager.getVisitsBetween(2,startDate,endDate);
		assertEquals(2,catVisits.size());
	}

	@AfterEach
	public void tearDown(){
		mockCache = null;
		mockOwners = null;
		petManager = null;
		dog = null;
		cat = null;
		duck = null;
		owner = null;
	}
}
