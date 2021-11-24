package org.springframework.samples.petclinic.utility;
import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	// line 15 predicate: t1arr[0] < 0 || t1arr[0] + t1arr[1] < t1arr[2]
	// a: t1arr[0] < 0
	// b: t1arr[0] + t1arr[1] < t1arr[2]
	// predicate: a || b
	// CC coverage
	// a: True b:True
	@ClauseDefinition(
		clause = 'a',
		def = "t1arr[0] < 0"
	)
	@ClauseDefinition(
		clause = 'b',
		def = "t1arr[0] + t1arr[1] < t1arr[2]"
	)
	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a',valuation = true),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void testAreCongruentClauseCoverageArgumentsTrue(){
		// t1arr[a] < 0 a:True
		// t1arr[a] + t1arr[b] < t1arr[c] b:True
		Triangle t1 = new Triangle(-2, 3, 7);

		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	// CC coverage
	// a: False b:False
	@ClauseDefinition(
		clause = 'a',
		def = "t1arr[0] < 0"
	)
	@ClauseDefinition(
		clause = 'b',
		def = "t1arr[0] + t1arr[1] < t1arr[2]"
	)
	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a',valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@Test
	public void testAreCongruentClauseCoverageArgumentsFalse(){
		// t1arr[a] < 0 a:False
		// t1arr[a] + t1arr[b] < t1arr[c] b:False
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@ClauseDefinition(
		clause = 'a',
		def = "t1arr[0] < 0"
	)
	@ClauseDefinition(
		clause = 'b',
		def = "t1arr[0] + t1arr[1] < t1arr[2]"
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a',valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a',valuation = true),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = true
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a',valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a',valuation = false),
			@Valuation(clause = 'b', valuation = true)
		},
		predicateValue = true
	)
	@Test
	public void testCACCOnPredicate15(){
		Triangle t1 = new Triangle(5, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		Triangle t3 = new Triangle(2, 3, 7);
		Assertions.assertEquals(false,TriCongruence.areCongruent(t1,t2));
		// a:true and b:false is impossible
		Assertions.assertEquals(false,TriCongruence.areCongruent(t3,t2));
	}

	//line 14 peridicate: t1arr[0] != t2arr[0] || t1arr[1] != t2arr[1] || t1arr[2] != t2arr[2]
	// a: t1arr[0] != t2arr[0]
	// b: t1arr[1] != t2arr[1]
	// c: t1arr[2] != t2arr[2]
	// peridicate: a+b+c
	@ClauseDefinition(
		clause = 'a',
		def = "t1arr[0] != t2arr[0]"
	)
	@ClauseDefinition(
		clause = 'b',
		def = "t1arr[1] != t2arr[1]"
	)
	@ClauseDefinition(
		clause = 'c',
		def = "t1arr[2] != t2arr[2]"
	)
	@UniqueTruePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@UniqueTruePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@UniqueTruePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@NearFalsePoint(
		predicate = "a+b+c",
		dnf = "a+b+c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testCUTPNFPOnPredicate14(){
		Triangle t1 = new Triangle(2, 4, 6);
		Triangle t2 = new Triangle(4, 3, 6);
		Triangle t3 = new Triangle(5, 3, 6);
		Triangle t4 = new Triangle(5, 3, 7);
		Triangle t5 = new Triangle(5, 3, 7);
		// Unique true points on a
		Assertions.assertEquals(false,TriCongruence.areCongruent(t1,t2));
		// Unique true points on b
		Assertions.assertEquals(false,TriCongruence.areCongruent(t2,t3));
		// Unique true points on c
		Assertions.assertEquals(false,TriCongruence.areCongruent(t3,t4));
		// near false point on a , b , c
		Assertions.assertEquals(true,TriCongruence.areCongruent(t4,t5));
	}

	// f = a + bc
	// utp a: tff, tft, ttf
	// utp bc: ftt
	// nfp a: fff
	// nfp b: fft
	// nfp c: ftf
	// cutpnfp: tff, fff, ftt, fft, ftf
	//
	// not f = ~a~b + ~a~c
	// utp ~a~b: fft
	// utp ~a~c: ftf
	// utpc: --ttf--, ftt, fft, ftf
	//
	// As it can be seen, "ttf" is a member of UTPC but is not a member of CUTPNFP.
	// Therefore, it is clear that CUTPNFP doesn't necessarily subsumes UTPC.
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
		predicate = a || (b&&c);
		return predicate;
	}
}
