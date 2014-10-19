package vinf;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestClass {

	//public static ArrayList<Page> allPages = new ArrayList<Page>();
	
	@Test
	public void testParsedText(){
		String realText = "'''Maniple''' (Latin: ''manipulus'', literally meaning \"a handful\") was a tactical unit of the [[Roman legion]] adopted from the [[Samnites]] during the [[Samnite Wars]] (343–290 BC). It was also the name of the military insignia carried by such unit.Maniple members, seen as each other's brothers in arms, were called ''commanipulares'' (singular, ''commanipularis''), but without the domestic closeness of the much smaller [[contubernium]].==Historical origin==The manipular system was adopted at around 315 BC, during the [[Samnite Wars#Second (or Great) Samnite War (326 to 304 BC)|Second Samnite War]].<ref>[http://history-world.org/samnite_wars.htm Rome, The Samnite Wars<!-- Bot generated title -->]</ref> The rugged terrain of [[Samnium]] where the war was fought highlighted the lack of manoeuvrability inherent in the [[phalanx formation]] which the Romans had inherited from the [[Etruscans]]. The  main battle troops of the Etruscans and Latins of this period comprised Greek-style hoplite phalanxes, inherited from the original Greek military unit, the [[phalanx]].After suffering a series of defeats culminating in the surrender of an entire legion without resistance at [[Caudine Forks]] the Romans abandoned the phalanx altogether, adopting the more flexible manipular system, famously referred to as \"a phalanx with joints\".== The manipular legion ==[[Image:Roman Maniple Top.png|thumb|left|1000px|The maniple typically consisted of 120 soldiers arrayed in 3 ranks of 40 men when engaged in battle.]][[Image:Roman Maniple Spacing.png|thumb|left|Roman soldiers in a maniple had a 6 ft by 6 ft \"fighting square\" around them, giving soldiers ample space to fight with their swords.]]For the next two hundred years (until the [[Marian reforms]] of 107 BC) the Roman army was organized into three lines: the ''[[hastati]]'', the ''[[principes]]'', and the ''[[triarii]]''. These were divided by experience, with the youngest soldiers in the ''hastati'' making the first engagement. Where resistance was strong this rank would dissolve back through the Roman line and allow the more experienced soldiers in the ''principes'' to fight. In turn, the ''principes'' could yield to the hardened ''triarii'' if necessary. The latter situation led to the Roman saying \"ad triarios redisse\", \"to fall back on the triarii\", meaning that things had come to a desperate pass. The maniples in each line generally formed with a one-maniple space between each maniple and its neighbours, and the maniples in each of the forward lines covering the gaps in the line behind, so that retreating troops of the forward lines could withdraw without disrupting those behind them.  Sources disagree on the numbers involved and in all likelihood they varied considerably but a generally accepted number is 20 maniples of ''hastati'' and 20 of ''principes'' of approximately 120 men each and 20 half strength maniples of \"triarii\", for a total of 6,000 men.Attached to a legion were also a number of very light skirmishers called ''[[velites]]'' armed with [[pilum|javelins]] drawn from the poorer sections of Roman society, a handful of [[Equestrian order|Equestrian]] cavalry, [[Auxiliaries (Roman military)#Roman Republic (to 30 BC)|auxiliaries]] (mostly cavalry) drawn from Rome's Italian allies (''socii'') and a large number of non-combatants.==Drill and fighting formations====See also==*[[Structural history of the Roman military]]==References=='''Citations'''<references/>'''Bibliography''':'''Primary sources'''*Primary sources for early Roman military organization include the writings of [[Polybius]] and [[Livy]].*A primary source for later Roman military organization and tactics is ''Epitoma rei militaris'' (also referred to as [[De Re Militari]]), by [[Flavius Vegetius Renatus]]:'''Secondary sources'''*[[Pauly-Wissowa]] (German-language encyclopaedia on everything relating to Classical Antiquity)==External links==* [http://www.brainfly.net/html/books/brn0320.htm The Military Institutions of the Romans (De Re Militari)] Translated from the Latin by Lieutenant John Clarke (1767)[[Category:Infantry units and formations of ancient Rome]][[Category:Military units and formations of the Roman Republic]]";
		
		try{
			Main.singleFileReader("sample",false);
		}
		catch(Exception e){
			System.out.println(e.getLocalizedMessage());
		
		}
		
		String parsedText = Main.allPages.get(0).getTextTag();

		System.out.println("-----------------------------");
		assertEquals("Zhoda stringov", realText, parsedText);
		System.out.println("@Test - testParsedText - prebehol uspesne");
	}
	
	@Test
	public void testNumberOfGoodResults(){
		assertEquals("Pocet dobrych zaznamom", 1, Main.allPages.size());

		System.out.println("@Test - testNumberOfGoodResults - prebehol uspesne");
	}
}
