package de.retest.web.selenium;

import java.util.List;
import java.util.function.Predicate;

import de.retest.ui.descriptors.Element;
import de.retest.ui.descriptors.RootElement;
import de.retest.ui.diff.Alignment;
import de.retest.util.Mapping;

public abstract class By extends org.openqa.selenium.By {

	public static ByRetestId retestId( final String retestId ) {
		return new ByRetestId( retestId );
	}

	public static Mapping<Element, Element> findElement( final RootElement lastExpectedState,
			final RootElement lastActualState, final Predicate<Element> predicate ) {
		if ( lastExpectedState == null ) {
			throw new IllegalArgumentException( "Cannot find element in null state." );
		}
		final Element result = findElement( lastExpectedState.getContainedElements(), predicate );
		if ( result == null ) {
			return null;
		}
		// TODO Use unobfuscated methods with retest 3.1.0
		// final Alignment alignment = Alignment.createAlignment( lastExpectedState, lastActualState );
		final Alignment alignment = Alignment.a( lastExpectedState, lastActualState );
		// return new Mapping<>( result, alignment.get( result ) );
		return new Mapping<>( result, alignment.a( result ) );
	}

	private static Element findElement( final List<Element> children, final Predicate<Element> predicate ) {
		for ( final Element element : children ) {
			if ( predicate.test( element ) ) {
				return element;
			}
			final Element result = findElement( element.getContainedElements(), predicate );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}
}
