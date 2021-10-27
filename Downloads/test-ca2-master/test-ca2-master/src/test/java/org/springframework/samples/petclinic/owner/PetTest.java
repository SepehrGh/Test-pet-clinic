package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetTest {

	public static Pet dog;

	@BeforeEach
	public void setUp(){
		dog = new Pet();
		dog.setName("dog");
		dog.setId(1);
	}

	@Test
	public void testAddVisitStateVerification(){
		Visit visit = mock(Visit.class);
		when(visit.getDate()).thenReturn(LocalDate.of(2021,9,25));
		when(visit.getId()).thenReturn(874);
		dog.addVisit(visit);
		assertEquals(1,dog.getVisits().size());
		assertEquals(874,dog.getVisits().get(0).getId());
	}

	@Test
	public void testAddVisitBehaviorVerification(){
		Visit visit = mock(Visit.class);
		when(visit.getDate()).thenReturn(LocalDate.of(2021,9,25));
		when(visit.getId()).thenReturn(874);
		dog.addVisit(visit);
		verify(visit).setPetId(1);
	}

	@AfterEach
	public void tearDown(){
		dog = null;
	}
}
